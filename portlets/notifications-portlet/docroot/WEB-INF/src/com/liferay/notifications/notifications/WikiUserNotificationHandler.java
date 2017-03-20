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
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;

/**
 * @author Lin Cui
 */
public class WikiUserNotificationHandler
	extends BaseModelUserNotificationHandler {

	public WikiUserNotificationHandler() {
		setPortletId(PortletKeys.WIKI);
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
			message = "x-added-a-new-wikipage-in-workspace-x";
			break;
		case UserNotificationDefinition.NOTIFICATION_TYPE_UPDATE_ENTRY:
			message = "x-updated-a-wikipage-in-workspace-x";
			break;	
		case NotificationsConstants.NOTIFICATION_TYPE_ADD_COMMENT:
			message = "x-added-a-comment-on-your-wikipage-in-workspace-x";
			break;
		case NotificationsConstants.NOTIFICATION_TYPE_UPDATE_COMMENT:
			message = "x-updated-a-comment-on-your-wikipage-in-workspace-x";
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
}