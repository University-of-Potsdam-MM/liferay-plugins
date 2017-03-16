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

package com.liferay.notifications.notifications;

import javax.portlet.PortletConfig;
import javax.servlet.ServletContext;

import com.liferay.compat.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.compat.portal.kernel.notifications.UserNotificationDefinition;
import com.liferay.notifications.util.NotificationsConstants;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.BookmarksFolderLocalServiceUtil;

/**
 * @author Lin Cui
 */
public class BookmarksUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public BookmarksUserNotificationHandler() {
		setPortletId(PortletKeys.BOOKMARKS);
	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());
		
		String body = jsonObject.getString("entryTitle");
		
		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(
					StringUtil.shorten(body, 70)),
				getTitle(jsonObject, serviceContext)
			});
	}
	
	@Override
	protected String getLink(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {

		return super.getLink(userNotificationEvent, serviceContext);
	}
	
	private String getTitle(JSONObject jsonObject,
			ServiceContext serviceContext)
			throws Exception {
		
		String message = StringPool.BLANK;
		
		int notificationType = jsonObject.getInt("notificationType");
		
		// check notification type to display correct message
		switch (notificationType) {
		case UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY:
			message = "x-added-a-new-bookmark-in-workspace-x";
			break;
		case UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY:
			message = "x-updated-a-bookmark-in-workspace-x";
			break;	
		case NotificationsConstants.NOTIFICATION_TYPE_ADD_COMMENT:
			message = "x-added-a-comment-on-your-post";
			break;
		case NotificationsConstants.NOTIFICATION_TYPE_UPDATE_COMMENT:
			message = "x-updated-a-comment-on-your-post";
			break;	
		}
		
		Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				com.liferay.notifications.util.PortletKeys.NOTIFICATIONS);
		ServletContext servletContext =
				(ServletContext)serviceContext.getAttribute(WebKeys.CTX);
		PortletConfig portletConfig =  PortletConfigFactoryUtil
				.create(portlet, servletContext);
		
		return LanguageUtil
				.format(portletConfig, serviceContext.getLocale(),
						message,
						new String[] {
								HtmlUtil.escape(PortalUtil.getUserName(
										jsonObject.getLong("userId"), StringPool.BLANK)), 
								serviceContext.getScopeGroup().getDescriptiveName(serviceContext.getLocale()) 
						}, 
						false);
	}
	
//	@Override
//	protected String getTitle(
//			JSONObject jsonObject, AssetRenderer assetRenderer,
//			ServiceContext serviceContext)
//		throws Exception {
//
//		String message = StringPool.BLANK;
//
//		String typeName = ResourceActionsUtil.getModelResource(
//			serviceContext.getLocale(), assetRenderer.getClassName());
//
//		int notificationType = jsonObject.getInt("notificationType");
//
//		if (notificationType ==
//				UserNotificationDefinition.NOTIFICATION_TYPE_ADD_ENTRY) {
//
//			message = "x-added-a-new-bookmarks-in-folder-x";
//
//			long folderId = jsonObject.getLong("classPK");
//
//			BookmarksFolder bookmarksFolder =
//				BookmarksFolderLocalServiceUtil.getBookmarksFolder(folderId);
//
//			typeName = bookmarksFolder.getName();
//		}
//		else if (notificationType ==
//					UserNotificationDefinition.
//						NOTIFICATION_TYPE_UPDATE_ENTRY) {
//
//			message = "x-updated-a-x";
//		}
//
//		String userName = assetRenderer.getUserName();
//
//		long userId = jsonObject.getLong("userId");
//
//		if (userId > 0) {
//			User user = UserLocalServiceUtil.getUser(userId);
//
//			userName = user.getFullName();
//		}
//
//		return serviceContext.translate(
//			message, HtmlUtil.escape(userName),
//			StringUtil.toLowerCase(HtmlUtil.escape(typeName)));
//	}

}