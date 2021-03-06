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

package com.liferay.notifications.notifications.portlet;

import com.liferay.compat.portal.kernel.util.ArrayUtil;
import com.liferay.compat.portal.kernel.util.HtmlUtil;
import com.liferay.compat.portal.kernel.util.HttpUtil;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.compat.portal.kernel.util.Time;
import com.liferay.notifications.model.UserNotificationEvent;
import com.liferay.notifications.util.NotificationsConstants;
import com.liferay.notifications.util.NotificationsUtil;
import com.liferay.notifications.util.PortletKeys;
import com.liferay.notifications.util.PortletPropsValues;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.UserNotificationFeedEntry;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationDeliveryLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.util.ContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.text.DateFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;

/**
 * @author Jonathan Lee
 */
public class NotificationsPortlet extends MVCPortlet {

	public void deleteUserNotificationEvent(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			com.liferay.portal.model.UserNotificationEvent
				userNotificationEvent =
					UserNotificationEventLocalServiceUtil.
						fetchUserNotificationEvent(userNotificationEventId);

			if (userNotificationEvent != null) {
				UserNotificationEventLocalServiceUtil.
					deleteUserNotificationEvent(userNotificationEvent);
			}

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void markAllAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long[] userNotificationEventIds = ParamUtil.getLongValues(
			actionRequest, "userNotificationEventIds");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			for (long userNotificationEventId : userNotificationEventIds) {
				updateArchived(userNotificationEventId);
			}

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void markAsRead(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			updateArchived(userNotificationEventId);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	public void processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(
				actionRequest, ActionRequest.ACTION_NAME);

			if (actionName.equals("deleteUserNotificationEvent")) {
				deleteUserNotificationEvent(actionRequest, actionResponse);
			}
			else if (actionName.equals("markAllAsRead")) {
				markAllAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("markAsRead")) {
				markAsRead(actionRequest, actionResponse);
			}
			else if (actionName.equals("setDelivered")) {
				setDelivered(actionRequest, actionResponse);
			}
			else if (actionName.equals("updateUserNotificationDelivery")) {
				updateUserNotificationDelivery(actionRequest, actionResponse);
			}
			else if (actionName.equals("updateMembershipRequest")) {
				handleMembershipRequest(actionRequest, actionResponse);
			}
			else {
				super.processAction(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("getNotificationsCount")) {
				getNotificationsCount(resourceRequest, resourceResponse);
			}
			else if (resourceID.equals("getUserNotificationEvents")) {
				getUserNotificationEvents(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void setDelivered(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			List<com.liferay.portal.model.UserNotificationEvent>
				userNotificationEvents =
					UserNotificationEventLocalServiceUtil.
						getDeliveredUserNotificationEvents(
							themeDisplay.getUserId(), false);

			for (com.liferay.portal.model.UserNotificationEvent
					userNotificationEvent : userNotificationEvents) {

				userNotificationEvent.setDelivered(true);

				UserNotificationEventLocalServiceUtil.
					updateUserNotificationEvent(userNotificationEvent);
			}

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void updateUserNotificationDelivery(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long userNotificationDeliveryId = ParamUtil.getLong(
			actionRequest, "userNotificationDeliveryId");

		boolean deliver = ParamUtil.getBoolean(actionRequest, "deliver", true);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			UserNotificationDeliveryLocalServiceUtil.
				updateUserNotificationDelivery(
					userNotificationDeliveryId, deliver);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	protected void getNotificationsCount(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			int newUserNotificationsCount =
				NotificationsUtil.getDeliveredUserNotificationEventsCount(
					themeDisplay.getUserId(), false);

			jsonObject.put(
				"newUserNotificationsCount", newUserNotificationsCount);

			jsonObject.put(
				"timestamp", String.valueOf(System.currentTimeMillis()));

			int unreadUserNotificationsCount =
				NotificationsUtil.getArchivedUserNotificationEventsCount(
					themeDisplay.getUserId(), false);

			jsonObject.put(
				"unreadUserNotificationsCount", unreadUserNotificationsCount);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected void getUserNotificationEvents(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		boolean fullView = ParamUtil.getBoolean(resourceRequest, "fullView");
		boolean actionable = ParamUtil.getBoolean(
			resourceRequest, "actionable");
		int start = ParamUtil.getInteger(resourceRequest, "start");
		int end = ParamUtil.getInteger(resourceRequest, "end");

		List<com.liferay.notifications.model.UserNotificationEvent>
			userNotificationEvents = null;
		int total = 0;

		if (fullView) {
			userNotificationEvents =
				NotificationsUtil.getUserNotificationEvents(
					themeDisplay.getUserId(), actionable, start, end);

			total = NotificationsUtil.getUserNotificationEventsCount(
				themeDisplay.getUserId(), actionable);
		}
		else {
			userNotificationEvents =
				NotificationsUtil.getArchivedUserNotificationEvents(
					themeDisplay.getUserId(), actionable, false, start, end);

			total = NotificationsUtil.getArchivedUserNotificationEventsCount(
				themeDisplay.getUserId(), actionable, false);
		}

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		List<Long> newUserNotificationEventIds = new ArrayList<Long>();

		for (com.liferay.notifications.model.UserNotificationEvent
				userNotificationEvent : userNotificationEvents) {

			String entry = renderEntry(
				resourceRequest, resourceResponse, userNotificationEvent);

			if (Validator.isNotNull(entry)) {
				jsonArray.put(entry);

				if (!userNotificationEvent.isArchived()) {
					newUserNotificationEventIds.add(
						userNotificationEvent.getUserNotificationEventId());
				}
			}
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("entries", jsonArray);

		int newTotalUuserNotificationEventsCount =
			NotificationsUtil.getArchivedUserNotificationEventsCount(
				themeDisplay.getUserId(), actionable, false);

		jsonObject.put(
			"newTotalUuserNotificationEventsCount",
			newTotalUuserNotificationEventsCount);

		jsonObject.put(
			"newUserNotificationEventIds",
			StringUtil.merge(newUserNotificationEventIds));
		jsonObject.put(
			"newUserNotificationEventsCount",
			newUserNotificationEventIds.size());

		jsonObject.put("total", total);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	protected String renderEntry(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse,
			UserNotificationEvent userNotificationEvent)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		com.liferay.portal.model.UserNotificationEvent
			portalUserNotificationEvent =
				userNotificationEvent.getUserNotificationEvent();

		String actionDiv = _ACTION_DIV_DEFAULT;

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			themeDisplay.getCompanyId(), userNotificationEvent.getType());

		String portletName = portlet.getDisplayName();
		String portletIcon = portlet.getContextPath() + portlet.getIcon();

		JSONObject userNotificationEventJSONObject =
			JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());

		long userId = userNotificationEventJSONObject.getLong("userId");

		String userFullName = HtmlUtil.escape(
			PortalUtil.getUserName(userId, StringPool.BLANK));

		String userPortraitURL = StringPool.BLANK;

		User user = UserLocalServiceUtil.fetchUserById(userId);

		if (user != null) {
			userPortraitURL = user.getPortraitURL(themeDisplay);
		}

		Format dateFormatDate = FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		Format dateTimeFormat = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.FULL, DateFormat.SHORT, themeDisplay.getLocale(),
			themeDisplay.getTimeZone());

		String timestamp = Time.getRelativeTimeDescription(
			userNotificationEvent.getTimestamp(), themeDisplay.getLocale(),
			themeDisplay.getTimeZone(), dateFormatDate);

		String timeTitle = dateTimeFormat.format(
			userNotificationEvent.getTimestamp());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			resourceRequest);

		UserNotificationFeedEntry userNotificationFeedEntry =
			UserNotificationManagerUtil.interpret(
				StringPool.BLANK, portalUserNotificationEvent, serviceContext);

		if (userNotificationFeedEntry == null) {
			String body = StringUtil.replace(
				_BODY_TEMPLATE_DEFAULT, new String[] {"[$BODY$]", "[$TITLE$]"},
				new String[] {
					serviceContext.translate(
						"unable-to-display-notification-for-x",
						portlet.getDisplayName()),
					serviceContext.translate(
						"notification-no-longer-applies")
				});

			return StringUtil.replace(
				ContentUtil.get(PortletPropsValues.USER_NOTIFICATION_ENTRY),
				new String[] {
					"[$ACTION_DIV$]", "[$BODY$]", "[$CSS_CLASS$]",
					"[$MARK_AS_READ_ICON$]", "[$PORTLET_ICON$]",
					"[$PORTLET_NAME$]", "[$TIMESTAMP$]", "[$TIMETITLE$]",
					"[$USER_FULL_NAME$]", "[$USER_PORTRAIT_URL$]"
				},
				new String[] {
					actionDiv, body, StringPool.BLANK, StringPool.BLANK,
					portletIcon, portletName, timestamp, timeTitle,
					userFullName, userPortraitURL
				});
		}

		// BEGIN CHANGE
		// some portlet can have both kinds of notifications, 
		// so an additional check is necessary
//		boolean actionable = ArrayUtil.contains(
//			NotificationsConstants.ACTIONABLE_TYPES,
//			userNotificationFeedEntry.getPortletId());
		
		boolean actionable = ArrayUtil.contains(
				NotificationsConstants.ACTIONABLE_TYPES,
				userNotificationFeedEntry.getPortletId())
				&& isActionableOperation(userNotificationFeedEntry,
						userNotificationEventJSONObject);
		// END CHANGE

		LiferayPortletResponse liferayPortletResponse =
			(LiferayPortletResponse)resourceResponse;

		PortletURL actionURL = liferayPortletResponse.createActionURL(
			PortletKeys.NOTIFICATIONS);

		actionURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));

		actionURL.setWindowState(WindowState.NORMAL);

		String cssClass = StringPool.BLANK;
		String markAsReadIcon = StringPool.BLANK;

		if (actionable) {
			actionURL.setParameter(
				"javax.portlet.action", "deleteUserNotificationEvent");

			actionDiv =
				StringUtil.replace(
					_DELETE_DIV, "[$DELETE_URL$]", actionURL.toString());
		}
		else {
			actionURL.setParameter("javax.portlet.action", "markAsRead");

			String link = userNotificationFeedEntry.getLink();

			String value = HttpUtil.getParameter(
				link, "controlPanelCategory", false);

			if (Validator.equals(PortletCategoryKeys.MY, value)) {
				link = HttpUtil.addParameter(
					link, "doAsGroupId", themeDisplay.getScopeGroupId());
			}

			actionDiv =
				StringUtil.replace(
					_MARK_AS_READ_DIV,
					new String[] {"[$LINK$]", "[$MARK_AS_READ_URL$]"},
					new String[] {link, actionURL.toString()});

			if (userNotificationEvent.isArchived()) {
				cssClass = "archived";
			}
			else {
				markAsReadIcon = StringUtil.replace(
					_MARK_AS_READ_ICON, "[$TITLE_MESSAGE$]",
					LanguageUtil.get(themeDisplay.getLocale(), "mark-as-read"));
			}
		}

		return StringUtil.replace(
			ContentUtil.get(PortletPropsValues.USER_NOTIFICATION_ENTRY),
			new String[] {
				"[$ACTION_DIV$]", "[$BODY$]", "[$CSS_CLASS$]",
				"[$MARK_AS_READ_ICON$]", "[$PORTLET_ICON$]", "[$PORTLET_NAME$]",
				"[$TIMESTAMP$]", "[$TIMETITLE$]", "[$USER_FULL_NAME$]",
				"[$USER_PORTRAIT_URL$]"},
			new String[] {
				actionDiv, userNotificationFeedEntry.getBody(), cssClass,
				markAsReadIcon, portletIcon, portletName, timestamp, timeTitle,
				userFullName, userPortraitURL
			});
	}

	protected void updateArchived(long userNotificationEventId)
		throws Exception {

		com.liferay.portal.model.UserNotificationEvent userNotificationEvent =
			UserNotificationEventLocalServiceUtil.getUserNotificationEvent(
				userNotificationEventId);

		userNotificationEvent.setArchived(true);

		UserNotificationEventLocalServiceUtil.updateUserNotificationEvent(
			userNotificationEvent);
	}
	
	/**
	 * This method is called, if a user replys to membership requests via notification request.
	 * It updates status of membership request according to user action.
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void handleMembershipRequest (
			ActionRequest actionRequest, ActionResponse actionResponse)
	throws Exception {
		
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		long membershipRequestId = ParamUtil.getLong(
			actionRequest, "membershipRequestId");

		int statusId = ParamUtil.getInteger(actionRequest, "statusId");
		String replyComments = ParamUtil.getString(
			actionRequest, "replyComments");
		
		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			MembershipRequestServiceUtil.updateStatus(
				membershipRequestId, replyComments, statusId, serviceContext);
			jsonObject.put("success", Boolean.TRUE);
		} catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);

		SessionMessages.add(actionRequest, "membershipReplySent");

		sendRedirect(actionRequest, actionResponse);
	}
	
	/**
	 * This method is used to check whether the operation is really actionable. 
	 * In the NotificationsConstants, actionable-cases are identified by portletId. 
	 * But in some cases portlets support both types of notifications. 
	 * @param userNotificationFeedEntry
	 * @param userNotificationEventJSONObject
	 * @return - true per default, if no routine for given portlet id is defined
	 * @throws SystemException
	 */
	private boolean isActionableOperation(UserNotificationFeedEntry userNotificationFeedEntry, JSONObject userNotificationEventJSONObject) 
			throws SystemException{
		
		/*
		 * SITE_MEMBERSHIPS_ADMIN has notifications for:
		 * 	- user is added to workspace
		 *  - worksapce is deleted
		 *  - membershiprequest response (deny, agree)
		 *  and request for:
		 *  - membershiprequests (user wants to join a workspace)
		 */
		if (PortletKeys.SITE_MEMBERSHIPS_ADMIN.equals(userNotificationFeedEntry.getPortletId())) {
			
			boolean isMembershipRequest = userNotificationEventJSONObject.getBoolean("membershipRequest");
			
			// membershipRequest is not true, so it's not a request (not actionable)
			if (!isMembershipRequest)
				return false;
			
			long membershipRequestId = userNotificationEventJSONObject.getLong("classPK");
			
			MembershipRequest membershipRequest = 
				MembershipRequestLocalServiceUtil.fetchMembershipRequest(membershipRequestId);
			
			if (membershipRequest == null)
				return true;
			
			// only actionable case for requests is a pending status
			if (membershipRequest.getStatusId() ==
					MembershipRequestConstants.STATUS_PENDING)
				return true;
			else
				return false;
		}
		
		/*
		 *  SO_INVITE_MEMBERS has notification for: user approved invitation
		 *  and request for workspace-invitations
		 */
		if (PortletKeys.SO_INVITE_MEMBERS.equals(userNotificationFeedEntry.getPortletId())) {
			
			// status flag was added later, so some events might not have it
			// Moreover STATUS_PENDING is 0, which is the default value for getInt(), that could lead to miss-interpretations 
			if (!userNotificationEventJSONObject.has("status"))
				return true;
			
			int status = userNotificationEventJSONObject.getInt("status");
			
			// getting memberRequest would be a better solution, but methods can not be resolved.
//			long memberRequestId = userNotificationEventJSONObject.getLong("classPK");
//			MemberRequest memberRequest =
//					MemberRequestLocalServiceUtil.fetchMemberRequest(memberRequestId);
			if (status == MembershipRequestConstants.STATUS_PENDING)
				return true;
			else
				return false;
		}
		
		if ("mycustompages_WAR_custompagesportlet".equals(userNotificationFeedEntry.getPortletId())) {

			// requests for custompagesportlet are not available atm due to #747
			return false;
		}			
		
		// return true if none of the above cases can be applied
		return true; 
	}

	private static final String _ACTION_DIV_DEFAULT =
		"<div class=\"clearfix\">";

	private static final String _BODY_TEMPLATE_DEFAULT =
		"<div class=\"title\">[$TITLE$]</div><div class=\"body\">[$BODY$]" +
			"</div>";

	private static final String _DELETE_DIV =
		"<div class=\"clearfix user-notification-delete\" data-deleteURL=\"" +
			"[$DELETE_URL$]\">";

	private static final String _MARK_AS_READ_DIV =
		"<div class=\"clearfix user-notification-link\" data-href=\"" +
			"[$LINK$]\" data-markAsReadURL=\"[$MARK_AS_READ_URL$]\">";

	private static final String _MARK_AS_READ_ICON =
		"<div class=\"mark-as-read\" title=\"[$TITLE_MESSAGE$]\"><i class=\"" +
			"icon-remove\"></i></div>";

}