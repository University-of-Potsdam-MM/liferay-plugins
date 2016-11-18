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
import com.liferay.notifications.notifications.portlet.NotificationsPortlet;
import com.liferay.notifications.util.EmailHelper;
import com.liferay.notifications.util.NotificationsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceWrapper;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Lin Cui
 */
public class DLAppHelperLocalServiceImpl
	extends DLAppHelperLocalServiceWrapper {

	public DLAppHelperLocalServiceImpl(
		DLAppHelperLocalService dlAppHelperLocalService) {

		super(dlAppHelperLocalService);
	}

	@Override
	public void updateStatus(
			long userId, FileEntry fileEntry, FileVersion latestFileVersion,
			int oldStatus, int newStatus,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		super.updateStatus(
			userId, fileEntry, latestFileVersion, oldStatus, newStatus,
			workflowContext, serviceContext);

		int notificationType =
			UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY;

		if (serviceContext.isCommandUpdate()) {
			notificationType =
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY;
		}

		AssetRenderer assetRenderer = _assetRendererFactory.getAssetRenderer(
			latestFileVersion.getFileEntryId());

		String entryURL = (String)serviceContext.getAttribute("entryURL");

		if (Validator.isNull(entryURL)) {
			entryURL = NotificationsUtil.getEntryURL(
				assetRenderer, PortletKeys.DOCUMENT_LIBRARY, serviceContext);
		}

		if ((newStatus == WorkflowConstants.STATUS_APPROVED) &&
			Validator.isNotNull(entryURL)) {

			NotificationsUtil.sendNotificationEvent(
				latestFileVersion.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
				_DL_FILE_ENTRY_CLASS_NAME, latestFileVersion.getFileEntryId(),
				assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
				notificationType, getSubscribersOVPs(latestFileVersion),
				userId);
			
			System.out.println("Group: "+fileEntry.getGroupId());
			System.out.println("FolderGroup: "+fileEntry.getFolder().getGroupId());
			// BEGIN CHANGE
			// TODO: use email helper
			EmailHelper.prepareEmail(fileEntry.getGroupId(), latestFileVersion.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
					notificationType, getSubscribersOVPs(latestFileVersion),
					userId, serviceContext, latestFileVersion.getFileEntryId());
			
//			prepareMail(fileEntry.getGroupId(), latestFileVersion.getCompanyId(), PortletKeys.DOCUMENT_LIBRARY,
//					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//					notificationType, getSubscribersOVPs(latestFileVersion),
//					userId, fileEntry, serviceContext);
			// END CHANGE
		}
		else {
			serviceContext.setAttribute("entryURL", entryURL);
		}
	}

	protected List<ObjectValuePair<String, Long>> getSubscribersOVPs(
			FileVersion latestFileVersion)
		throws PortalException, SystemException {

		List<ObjectValuePair<String, Long>> subscribersOVPs =
			new ArrayList<ObjectValuePair<String, Long>>();

		subscribersOVPs.add(
			new ObjectValuePair<String, Long>(
				_FOLDER_CLASS_NAME, latestFileVersion.getGroupId()));

		List<Long> folderIds = new ArrayList<Long>();

		FileEntry fileEntry = latestFileVersion.getFileEntry();

		Folder folder = fileEntry.getFolder();

		if (folder != null) {
			folderIds.add(folder.getFolderId());

			folderIds.addAll(folder.getAncestorFolderIds());
		}

		for (long folderId : folderIds) {
			subscribersOVPs.add(
				new ObjectValuePair<String, Long>(
					_FOLDER_CLASS_NAME, folderId));
		}

		return subscribersOVPs;
	}
	
	private void prepareMail(long groupId,
			long companyId, String portletKey,
			String entryTitle, String entryURL, int notificationType,
			List<ObjectValuePair<String, Long>> subscribersOVPs, long userId, 
			FileEntry fileEntry, ServiceContext serviceContext)
		throws PortalException, SystemException {
		
		Set<Long> subscriberUserIds = new HashSet<Long>();

		for (ObjectValuePair<String, Long> ovp : subscribersOVPs) {
			String className = ovp.getKey();
			long classPK = ovp.getValue();

			List<Subscription> subscriptions =
				SubscriptionLocalServiceUtil.getSubscriptions(
					companyId, className, classPK);

			for (Subscription subscription : subscriptions) {
				long subscriberUserId = subscription.getUserId();

				if (subscriberUserId == userId) {
					continue;
				}

				if (subscriberUserIds.contains(subscriberUserId)) {
					continue;
				}

				subscriberUserIds.add(subscriberUserId);
				
				if (UserNotificationManagerUtil.isDeliver(
						subscriberUserId, portletKey, 0, notificationType,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {
					
					try {
						sendMail(userId, subscriberUserId, companyId, 
								portletKey, groupId, notificationType,
								entryTitle, entryURL, fileEntry,
								serviceContext);
					}
					catch (Exception e) {
						throw new SystemException(e);
					}
				}
				
			}
		}
		
	}

	private void sendMail (long userId, long subscriberUserId, long companyId, String portletKey,
			long groupId, int notificationTypeInt, String entryTitle, String entryURL,
			FileEntry fileEntry, ServiceContext serviceContext) 
			throws Exception {
		
		User sender = UserLocalServiceUtil.getUser(userId);
		
		User recipient = UserLocalServiceUtil.getUser(subscriberUserId);
		
		Company company = CompanyLocalServiceUtil.getCompany(companyId);
		
		Group workspace = GroupLocalServiceUtil.getGroup(groupId);
		
		Portlet portlet = PortletLocalServiceUtil.getPortletById(serviceContext.getPortletId());
		
		String language = "";
		// check language, default: English
		if (recipient.getLocale().equals(Locale.GERMANY)) {
			language = "_"+Locale.GERMANY.toString();
		} 
		
		String notificationType = "added";
		if (notificationTypeInt == 1)			
			notificationType = "updated";
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/documentslibrary/email_file_entry_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, 
				new String[] {
					"[$WORKSPACE_NAME$]", 
					"[$DOCUMENT_TITLE$]"
				},
				new String[] {
					workspace.getDescriptiveName(), 
					entryTitle
				});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/documentslibrary/email_file_entry_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
		body, 
		new String[] {
			"[$WORKSPACE_NAME$]", "[$FOLDER_TITLE$]",
			"[$TO_NAME$]", "[$USER_NAME$]", 
			"[$CONTENT$]", "[$ENTRY_URL$]",
			"[$CONFIG_URL$]"
		},
		new String[] {
			workspace.getDescriptiveName(), LanguageUtil.get(recipient.getLocale(), "javax.portlet.title."+portlet.getPortletName()),
			recipient.getFullName(), sender.getFullName(),
			entryTitle, entryURL,
			EmailHelper.getConfigURL(serviceContext)
		});
		
		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
	
		InternetAddress from = new InternetAddress(fromAddress, fromName);
		
		InternetAddress to = new InternetAddress(
				recipient.getEmailAddress());
		
		MailMessage mailMessage = new MailMessage(from, to, subject, body, true);

		System.out.println("***************************");
		System.out.println("From: "+from.getAddress()+" ,To: "+to.getAddress());
		System.out.println(subject);
		System.out.println(body);
		System.out.println("***************************");
		
//		MailServiceUtil.sendEmail(mailMessage);
	}
	
	protected AssetRendererFactory _assetRendererFactory =
		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			_DL_FILE_ENTRY_CLASS_NAME);

	private static final String _DL_FILE_ENTRY_CLASS_NAME =
		DLFileEntry.class.getName();

	private static final String _FOLDER_CLASS_NAME = Folder.class.getName();

}