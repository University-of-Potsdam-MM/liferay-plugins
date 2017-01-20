/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.notifications.hook.service.impl;

import com.liferay.compat.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.notifications.util.EmailHelper;
import com.liferay.notifications.util.NotificationsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalService;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceWrapper;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Lin Cui
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceWrapper {

	public JournalArticleLocalServiceImpl(
		JournalArticleLocalService journalArticleLocalService) {

		super(journalArticleLocalService);
	}

	@Override
	public JournalArticle updateStatus(
			long userId, JournalArticle article, int status, String articleURL,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		JournalArticle journalArticle = super.updateStatus(
			userId, article, status, articleURL, workflowContext,
			serviceContext);

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		AssetRenderer assetRenderer = _assetRendererFactory.getAssetRenderer(
			article.getId());

		String entryURL = (String)serviceContext.getAttribute("entryURL");

		if (Validator.isNull(entryURL)) {
			entryURL = NotificationsUtil.getEntryURL(
				assetRenderer, PortletKeys.JOURNAL, serviceContext);
		}

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNotNull(entryURL)) {

			// does not work, cause nobody can subscribe...
//			NotificationsUtil.sendNotificationEvent(
//				article.getCompanyId(), PortletKeys.JOURNAL,
//				_JOURNAL_ARTICLE_CLASS_NAME, article.getId(),
//				assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//				notificationType, getSubscribersOVPs(article), userId);
			
			// nobody can subscribe to an article, so prepare mail won't work
//			EmailHelper.prepareEmail(article.getGroupId(), article.getCompanyId(), PortletKeys.JOURNAL,
//					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//					notificationType, getSubscribersOVPs(article), userId, serviceContext, article.getId()
//					);
		}
		else {
			serviceContext.setAttribute("entryURL", entryURL);
		}

		// send mail for journal in a workspace
		Group workspace = GroupLocalServiceUtil.fetchGroup(article.getGroupId());

		if (workspace == null)
			return article;

		// identify real workspace, parentgroup of workspace is 0
		while (workspace.getParentGroupId() != 0) {
			workspace = GroupLocalServiceUtil.getGroup(workspace.getParentGroupId());
		}
		
		if (workspace.isSite()) {
			
			List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(workspace.getGroupId());
			for (User user : groupUsers) {
				if (user.getUserId() == article.getUserId())
					continue;

				if (UserNotificationManagerUtil.isDeliver(user.getUserId(),
						PortletKeys.JOURNAL, 0,
						UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {
					try {
						EmailHelper.sendJournalMail(journalArticle, serviceContext, user, workspace);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}
				
				// create Notification Event for Journal Article
				createNotificationEvent(_JOURNAL_ARTICLE_CLASS_NAME, journalArticle.getId(),
						assetRenderer.getTitle(serviceContext.getLocale()), 
						entryURL, UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, user.getUserId(),
						journalArticle.getUserId());
				
			}
		} else {
		
			// Journal befindet sich auf custom page
			// get all users that have access to page (before deleting it)
			List<CustomPageFeedback> customPageFeedbackList = 
					CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(serviceContext.getPlid());
			for (CustomPageFeedback customPageFeedback : customPageFeedbackList) {
				User user = customPageFeedback.getUser();
				if (user.getUserId() == article.getUserId())
					continue;

				if (UserNotificationManagerUtil.isDeliver(user.getUserId(),
						PortletKeys.JOURNAL, 0,
						UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {
					
					try {
						EmailHelper.sendJournalMail(journalArticle, serviceContext, user, null);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}
				
				// create Notification Event for Journal Article
				createNotificationEvent(_JOURNAL_ARTICLE_CLASS_NAME, journalArticle.getId(),
						assetRenderer.getTitle(serviceContext.getLocale()), 
						entryURL, UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, user.getUserId(),
						journalArticle.getUserId());
			}
		}
		return journalArticle;
	}

	protected List<ObjectValuePair<String, Long>> getSubscribersOVPs(
			JournalArticle article)
		throws SystemException {

		List<ObjectValuePair<String, Long>> subscribersOVPs =
			new ArrayList<ObjectValuePair<String, Long>>();

		subscribersOVPs.add(
			new ObjectValuePair<String, Long>(
				_JOURNAL_ARTICLE_CLASS_NAME, article.getGroupId()));

		return subscribersOVPs;
	}

	protected AssetRendererFactory _assetRendererFactory =
		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			_JOURNAL_ARTICLE_CLASS_NAME);

	/**
	 * create NotificationEvent for Journal (Text-Image-Editor).
	 * @param className
	 * @param classPK
	 * @param entryTitle
	 * @param entryURL
	 * @param notificationType
	 * @param userId
	 * @throws PortalException
	 * @throws SystemException
	 * @throws IllegalArgumentException
	 */
	private void createNotificationEvent (String className, long classPK,
			String entryTitle, String entryURL, int notificationType, long userId, long senderUserId) 
		throws PortalException, SystemException, IllegalArgumentException {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		jsonObject.put("className", className);
		jsonObject.put("classPK", classPK);
		jsonObject.put("entryTitle", entryTitle);
		jsonObject.put("entryURL", entryURL);
		jsonObject.put("notificationType", notificationType);
		jsonObject.put("userId", senderUserId);
		
		if(UserNotificationManagerUtil.isDeliver(
				userId,
				PortletKeys.JOURNAL, 0, 
				notificationType,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)){
		
			NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
					System.currentTimeMillis(), PortletKeys.JOURNAL, jsonObject);
			notificationEvent.setDeliveryRequired(0);
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(userId, notificationEvent);
		}
	}
	
	private static final String _JOURNAL_ARTICLE_CLASS_NAME =
		JournalArticle.class.getName();

}