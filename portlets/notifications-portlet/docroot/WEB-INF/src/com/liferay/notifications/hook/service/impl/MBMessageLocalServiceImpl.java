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
import com.liferay.notifications.util.NotificationsConstants;
import com.liferay.notifications.util.NotificationsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceWrapper;
import com.liferay.portlet.wiki.model.WikiPage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Cui
 */
public class MBMessageLocalServiceImpl extends MBMessageLocalServiceWrapper {

	public MBMessageLocalServiceImpl(
		MBMessageLocalService mbMessagePageLocalService) {

		super(mbMessagePageLocalService);
	}

	@Override
	public MBMessage updateStatus(
			long userId, long messageId, int status,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage mbMessage = super.updateStatus(
			userId, messageId, status, serviceContext);

		if (mbMessage.getCategoryId() ==
				MBCategoryConstants.DISCUSSION_CATEGORY_ID) {
			
			List<ObjectValuePair<String, Long>> subscribersOVPs =
					new ArrayList<ObjectValuePair<String, Long>>();

			String className = (String)serviceContext.getAttribute("className");
			long classPK = ParamUtil.getLong(serviceContext, "classPK");
			
			ObjectValuePair<String, Long> ovp = new ObjectValuePair<String, Long>(
					className, classPK);
			subscribersOVPs.add(ovp);
				
			EmailHelper.sendCommentsMail(mbMessage, serviceContext, subscribersOVPs);
			
			String portletKey = StringPool.BLANK;
			// notification events for comments
			if (mbMessage.getClassNameId() == ClassNameLocalServiceUtil.getClassNameId(BlogsEntry.class.getName()))
				portletKey = PortletKeys.BLOGS; 
			
			if (mbMessage.getClassNameId() == ClassNameLocalServiceUtil.getClassNameId(WikiPage.class.getName()))
				portletKey = PortletKeys.WIKI; 
			
			if (mbMessage.getClassNameId() == ClassNameLocalServiceUtil.getClassNameId(JournalArticle.class.getName()))
				portletKey = PortletKeys.JOURNAL; 
			
			if (portletKey.equals(StringPool.BLANK))
				return mbMessage;
			
			NotificationsUtil.sendNotificationEvent(
				mbMessage.getCompanyId(), portletKey, 
				className, classPK, mbMessage.getBody(), 
				(String)serviceContext.getAttribute("contentURL"), 
				serviceContext.isCommandUpdate() ? NotificationsConstants.NOTIFICATION_TYPE_UPDATE_COMMENT : NotificationsConstants.NOTIFICATION_TYPE_ADD_COMMENT,
				subscribersOVPs, userId);
			
			return mbMessage;
		}

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		AssetRenderer assetRenderer = _assetRendererFactory.getAssetRenderer(
			mbMessage.getMessageId());

		String entryURL = (String)serviceContext.getAttribute("entryURL");

		if (Validator.isNull(entryURL)) {
			entryURL = NotificationsUtil.getEntryURL(
				assetRenderer, PortletKeys.MESSAGE_BOARDS, serviceContext);
		}

		if ((status == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNotNull(entryURL)) {

			NotificationsUtil.sendNotificationEvent(
				mbMessage.getCompanyId(), PortletKeys.MESSAGE_BOARDS,
				_MB_MESSAGE_CLASS_NAME, mbMessage.getMessageId(),
				assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
				notificationType, getSubscribersOVPs(mbMessage), userId);
			
			// BEGIN CHANGE
			// added method to send mail
			EmailHelper.prepareEmail(mbMessage.getGroupId(), mbMessage.getCompanyId(), PortletKeys.MESSAGE_BOARDS,
					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
					notificationType, getSubscribersOVPs(mbMessage), 
					userId, serviceContext, mbMessage.getMessageId());
			// END CHANGE
		}
		else {
			serviceContext.setAttribute("entryURL", entryURL);
		}

		return mbMessage;
	}

	protected List<ObjectValuePair<String, Long>> getSubscribersOVPs(
			MBMessage mbMessage)
		throws PortalException, SystemException {

		List<ObjectValuePair<String, Long>> subscribersOVPs =
			new ArrayList<ObjectValuePair<String, Long>>();

		subscribersOVPs.add(
			new ObjectValuePair<String, Long>(
				_MB_THREAD_CLASS_NAME, mbMessage.getThreadId()));

		List<Long> categoryIds = new ArrayList<Long>();

		categoryIds.add(mbMessage.getGroupId());

		long categoryId = mbMessage.getCategoryId();

		if (categoryId > 0) {
			categoryIds.add(categoryId);
		}

		if (categoryId != MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			MBCategory category = mbMessage.getCategory();

			categoryIds.addAll(category.getAncestorCategoryIds());
		}

		for (long curCategoryId : categoryIds) {
			subscribersOVPs.add(
				new ObjectValuePair<String, Long>(
					_MB_CATEGORY_CLASS_NAME, curCategoryId));
		}

		return subscribersOVPs;
	}

	protected AssetRendererFactory _assetRendererFactory =
		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			_MB_MESSAGE_CLASS_NAME);

	private static final String _MB_CATEGORY_CLASS_NAME =
		MBCategory.class.getName();

	private static final String _MB_MESSAGE_CLASS_NAME =
		MBMessage.class.getName();

	private static final String _MB_THREAD_CLASS_NAME =
		MBThread.class.getName();

}