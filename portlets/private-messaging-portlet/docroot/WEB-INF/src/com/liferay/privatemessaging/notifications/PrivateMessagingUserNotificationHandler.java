/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */

package com.liferay.privatemessaging.notifications;

import com.liferay.compat.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.privatemessaging.model.UserThread;
import com.liferay.privatemessaging.service.UserThreadLocalServiceUtil;
import com.liferay.privatemessaging.util.PortletKeys;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;

/**
 * @author Jonathan Lee
 */
public class PrivateMessagingUserNotificationHandler
	extends BaseUserNotificationHandler {

	public PrivateMessagingUserNotificationHandler() {
		setPortletId(PortletKeys.PRIVATE_MESSAGING);
	}

	@Override
	protected String getBody(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		String body = null;
		long userId = 0;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(classPK);

		if (mbMessage == null) {
			body = jsonObject.getString("body");

			if (Validator.isNull(body)) {
				UserNotificationEventLocalServiceUtil.
					deleteUserNotificationEvent(
						userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			userId = jsonObject.getLong("userId");
		}
		else {
			UserThread userThread = UserThreadLocalServiceUtil.fetchUserThread(
				serviceContext.getUserId(), mbMessage.getThreadId());

			if ((userThread == null) || userThread.isDeleted()) {
				UserNotificationEventLocalServiceUtil.
					deleteUserNotificationEvent(
						userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			body = mbMessage.getBody();
			userId = mbMessage.getUserId();
		}

		// BEGIN CHANGE
		// changed from serviceContext to LanguageUtil + portletConfig to force use of Language Property of Private Messaging Portlet.
//		String title = serviceContext.translate(
//			"x-sent-you-a-message",
//			HtmlUtil.escape(PortalUtil.getUserName(userId, StringPool.BLANK)));

		Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				PortletKeys.PRIVATE_MESSAGING);
		ServletContext servletContext =
				(ServletContext)serviceContext.getAttribute(WebKeys.CTX);
		PortletConfig portletConfig =  PortletConfigFactoryUtil
				.create(portlet, servletContext);
		
		String title = LanguageUtil.format(
				portletConfig,
				serviceContext.getLocale(),
				"x-sent-you-a-message",
				"<span class=\"user-notification-username\">"
						+ HtmlUtil.escape(PortalUtil.getUserName(userId,
								StringPool.BLANK)) + "</span>");
		// END CHANGE
		
		return StringUtil.replace(
			getBodyTemplate(), new String[] {"[$BODY$]", "[$TITLE$]"},
			new String[] {
				HtmlUtil.escape(StringUtil.shorten(body, 50)), title
			});
	}

	@Override
	protected String getLink(
			UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext)
		throws Exception {

		long mbThreadId = 0;

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			userNotificationEvent.getPayload());

		long classPK = jsonObject.getLong("classPK");

		MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(classPK);

		if (mbMessage == null) {
			MBThread mbThread = MBThreadLocalServiceUtil.fetchMBThread(classPK);

			if (mbThread == null) {
				UserNotificationEventLocalServiceUtil.
					deleteUserNotificationEvent(
						userNotificationEvent.getUserNotificationEventId());

				return null;
			}

			mbThreadId = classPK;
		}
		else {
			mbThreadId = mbMessage.getThreadId();
		}

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		User user = themeDisplay.getUser();

		Group group = user.getGroup();

		long portletPlid = PortalUtil.getPlidFromPortletId(
			group.getGroupId(), true, PortletKeys.PRIVATE_MESSAGING);

		PortletURL portletURL = null;

		if (portletPlid != 0) {
			portletURL = PortletURLFactoryUtil.create(
				serviceContext.getLiferayPortletRequest(),
				PortletKeys.PRIVATE_MESSAGING, portletPlid,
				PortletRequest.RENDER_PHASE);

			portletURL.setParameter("mbThreadId", String.valueOf(mbThreadId));
		}
		else {
			LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

			portletURL = liferayPortletResponse.createRenderURL(
				PortletKeys.PRIVATE_MESSAGING);

			portletURL.setParameter("mvcPath", "/view.jsp");
			portletURL.setParameter("mbThreadId", String.valueOf(mbThreadId));
			portletURL.setWindowState(WindowState.MAXIMIZED);
		}

		return portletURL.toString();
	}

}