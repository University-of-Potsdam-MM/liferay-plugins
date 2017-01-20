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
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalService;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceWrapper;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lin Cui
 */
public class BookmarksEntryLocalServiceImpl
	extends BookmarksEntryLocalServiceWrapper {

	public BookmarksEntryLocalServiceImpl(
		BookmarksEntryLocalService bookmarksEntryLocalService) {

		super(bookmarksEntryLocalService);
	}

	@Override
	public BookmarksEntry addEntry(
			long userId, long groupId, long folderId, String name, String url,
			String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntry bookmarksEntry = super.addEntry(
			userId, groupId, folderId, name, url, description, serviceContext);

		AssetRenderer assetRenderer = _assetRendererFactory.getAssetRenderer(
			bookmarksEntry.getEntryId());

		String entryURL = NotificationsUtil.getEntryURL(
			assetRenderer, PortletKeys.BOOKMARKS, serviceContext);

		// removed cause campus.UP does not use folders for bookmarks
//		if (Validator.isNotNull(entryURL)) {
//			NotificationsUtil.sendNotificationEvent(
//				bookmarksEntry.getCompanyId(), PortletKeys.BOOKMARKS,
//				_BOOKMARKS_FOLDER_CLASS_NAME, bookmarksEntry.getFolderId(),
//				assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
//				getSubscribersOVPs(
//					bookmarksEntry, _BOOKMARKS_FOLDER_CLASS_NAME,
//					bookmarksEntry.getFolderId()),
//				userId);
//			
//			EmailHelper.prepareEmail(bookmarksEntry.getGroupId(), bookmarksEntry.getCompanyId(), PortletKeys.BOOKMARKS,
//					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//					UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY,
//					getSubscribersOVPs(
//						bookmarksEntry, _BOOKMARKS_FOLDER_CLASS_NAME,
//						bookmarksEntry.getFolderId()),
//					userId, serviceContext, bookmarksEntry.getFolderId()
//					);
//		}

		// send an email to all users that have access to bookmark (same workspace or shared page)
		notifyAllUsers(bookmarksEntry, serviceContext, entryURL, 
				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY, assetRenderer);
		
		return bookmarksEntry;
	}

	@Override
	public BookmarksEntry updateEntry(
			long userId, long entryId, long groupId, long folderId, String name,
			String url, String description, ServiceContext serviceContext)
		throws PortalException, SystemException {

		BookmarksEntry bookmarksEntry = super.updateEntry(
			userId, entryId, groupId, folderId, name, url, description,
			serviceContext);

		AssetRenderer assetRenderer = _assetRendererFactory.getAssetRenderer(
			bookmarksEntry.getEntryId());

		String entryURL = NotificationsUtil.getEntryURL(
			assetRenderer, PortletKeys.BOOKMARKS, serviceContext);

		// removed cause campus.UP does not use folders for bookmarks
//		if (Validator.isNotNull(entryURL)) {
//			NotificationsUtil.sendNotificationEvent(
//				bookmarksEntry.getCompanyId(), PortletKeys.BOOKMARKS,
//				_BOOKMARKS_ENTRY_CLASS_NAME, bookmarksEntry.getEntryId(),
//				assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
//				getSubscribersOVPs(
//					bookmarksEntry, _BOOKMARKS_ENTRY_CLASS_NAME,
//					bookmarksEntry.getEntryId()),
//				userId);
//			
//			// does not work
//			EmailHelper.prepareEmail(bookmarksEntry.getGroupId(), bookmarksEntry.getCompanyId(), PortletKeys.BOOKMARKS,
//					assetRenderer.getTitle(serviceContext.getLocale()), entryURL,
//					UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY,
//					getSubscribersOVPs(
//						bookmarksEntry, _BOOKMARKS_ENTRY_CLASS_NAME,
//						bookmarksEntry.getFolderId()),
//					userId, serviceContext, bookmarksEntry.getFolderId()
//					);
//		}

		// send an email to all users that have access to bookmark (same workspace or shared page)
		notifyAllUsers(bookmarksEntry, serviceContext, entryURL, 
				UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY, assetRenderer);
		
		return bookmarksEntry;
	}

	protected List<ObjectValuePair<String, Long>> getSubscribersOVPs(
			BookmarksEntry bookmarksEntry, String subscriptionClassName,
			long subscriptionClassPK)
		throws PortalException, SystemException {

		List<ObjectValuePair<String, Long>> subscribersOVPs =
			new ArrayList<ObjectValuePair<String, Long>>();

		if (subscriptionClassName.equals(_BOOKMARKS_ENTRY_CLASS_NAME)) {
			subscribersOVPs.add(
				new ObjectValuePair<String, Long>(
					subscriptionClassName, subscriptionClassPK));

			subscriptionClassName = _BOOKMARKS_FOLDER_CLASS_NAME;
			subscriptionClassPK = bookmarksEntry.getFolderId();
		}

		if (subscriptionClassPK <= 0) {
			subscriptionClassPK = bookmarksEntry.getGroupId();
		}

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(subscriptionClassPK);

		BookmarksFolder folder = bookmarksEntry.getFolder();

		if (subscriptionClassPK !=
				BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID) {

			folderIds.addAll(folder.getAncestorFolderIds());
		}

		for (Long folderId : folderIds) {
			subscribersOVPs.add(
				new ObjectValuePair<String, Long>(
					subscriptionClassName, folderId));
		}

		return subscribersOVPs;
	}
	
	private void notifyAllUsers (BookmarksEntry bookmarksEntry, ServiceContext serviceContext,
			String entryURL, int notificationType, AssetRenderer assetRenderer) 
		throws SystemException, PortalException {
		
		// get possible workspace
		Group workspace = GroupLocalServiceUtil.fetchGroup(bookmarksEntry.getGroupId());

		// return if no possible workspace is available
		if (workspace == null) {
			return;
		} 
		
		// identify real workspace, parentgroup of workspace is 0
		while (workspace.getParentGroupId() != 0) {
			workspace = GroupLocalServiceUtil.getGroup(workspace.getParentGroupId());
		}
	
		if (workspace.isSite()) {
			/* Because subscription of bookmark-folders is currently not working
			 * every user in workspace is notified if a new bookmark is created
			 */
			// get users of workspace
			List<User> groupUsers = UserLocalServiceUtil.getGroupUsers(workspace.getGroupId());
			for (User user : groupUsers) {
				// do not notify user that added bookmark
				if (user.getUserId() == bookmarksEntry.getUserId())
					continue;
	
				if (UserNotificationManagerUtil.isDeliver(user.getUserId(),
						PortletKeys.BOOKMARKS, 0,
						notificationType,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {
					try {
						EmailHelper.sendBookmarksMail(bookmarksEntry, serviceContext, 
								user, workspace, entryURL, 
								notificationType);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}
				
				createNotificationEvent(_BOOKMARKS_ENTRY_CLASS_NAME, bookmarksEntry.getEntryId(),
											assetRenderer.getTitle(serviceContext.getLocale()), 
											entryURL, notificationType, user.getUserId(), bookmarksEntry.getUserId());
			}
		} else {
			/* Because subscription of bookmark-folders is currently not working
			 * every user, to whom the page is shared with, is notified if a new bookmark is created
			 */
			// get all users that page is shared with
			List<CustomPageFeedback> customPageFeedbackList = 
					CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(serviceContext.getPlid());
			for (CustomPageFeedback customPageFeedback : customPageFeedbackList) {
				User user = customPageFeedback.getUser();
				
				// do not notify user that added bookmark
				if (user.getUserId() == bookmarksEntry.getUserId())
					continue;
				
				if (UserNotificationManagerUtil.isDeliver(user.getUserId(),
						PortletKeys.BOOKMARKS, 0,
						notificationType,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {
					try {
						EmailHelper.sendBookmarksMail(bookmarksEntry, serviceContext, 
								user, workspace, entryURL, 
								notificationType);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}
				
				createNotificationEvent(_BOOKMARKS_ENTRY_CLASS_NAME, bookmarksEntry.getEntryId(),
						assetRenderer.getTitle(serviceContext.getLocale()), 
						entryURL, notificationType, user.getUserId(), bookmarksEntry.getUserId());
			}
		}
	}
	
	private void createNotificationEvent (String className, long classPK,
			String entryTitle, String entryURL, int notificationType, long userId, 
			long senderUserId) 
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
				PortletKeys.BOOKMARKS, 0, 
				notificationType,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)){
		
			NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
					System.currentTimeMillis(), PortletKeys.BOOKMARKS, jsonObject);
			notificationEvent.setDeliveryRequired(0);
			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(userId, notificationEvent);
		}
	}

	protected AssetRendererFactory _assetRendererFactory =
		AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
			_BOOKMARKS_ENTRY_CLASS_NAME);

	private static final String _BOOKMARKS_ENTRY_CLASS_NAME =
		BookmarksEntry.class.getName();

	private static final String _BOOKMARKS_FOLDER_CLASS_NAME =
		BookmarksFolder.class.getName();

}