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

package com.liferay.so.invitemembers.portlet;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.notifications.util.PortletKeys;
import com.liferay.notifications.util.UserNotificationHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.so.invitemembers.util.InviteMembersConstants;
import com.liferay.so.invitemembers.util.InviteMembersUtil;
import com.liferay.so.model.MemberRequest;
import com.liferay.so.service.MemberRequestLocalServiceUtil;
import com.liferay.util.ContentUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ryan Park
 */
public class InviteMembersPortlet extends MVCPortlet {

	public void getAvailableUsers(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		int end = ParamUtil.getInteger(resourceRequest, "end");
		String keywords = ParamUtil.getString(resourceRequest, "keywords");
		int start = ParamUtil.getInteger(resourceRequest, "start");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put(
			"count",
			InviteMembersUtil.getAvailableUsersCount(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords));

		JSONObject optionsJSONObject = JSONFactoryUtil.createJSONObject();

		optionsJSONObject.put("end", end);
		optionsJSONObject.put("keywords", keywords);
		optionsJSONObject.put("start", start);

		jsonObject.put("options", optionsJSONObject);

		List<User> users =
			InviteMembersUtil.getAvailableUsers(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId(),
				keywords, start, end);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

			userJSONObject.put(
				"hasPendingMemberRequest",
				MemberRequestLocalServiceUtil.hasPendingMemberRequest(
					themeDisplay.getScopeGroupId(), user.getUserId()));
			userJSONObject.put("userEmailAddress", user.getEmailAddress());
			userJSONObject.put("userFullName", user.getFullName());
			userJSONObject.put("userId", user.getUserId());

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	public void sendInvites(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			doSendInvite(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}
	}

	@Override
	public void serveResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		try {
			String resourceID = resourceRequest.getResourceID();

			if (resourceID.equals("getAvailableUsers")) {
				getAvailableUsers(resourceRequest, resourceResponse);
			}
			else {
				super.serveResource(resourceRequest, resourceResponse);
			}
		}
		catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void updateMemberRequest(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long memberRequestId = ParamUtil.getLong(
			actionRequest, "memberRequestId");
		int status = ParamUtil.getInteger(actionRequest, "status");
		long userNotificationEventId = ParamUtil.getLong(
			actionRequest, "userNotificationEventId");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		try {
			MemberRequestLocalServiceUtil.updateMemberRequest(
				themeDisplay.getUserId(), memberRequestId, status);

			jsonObject.put("success", Boolean.TRUE);
		}
		catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
		
		// BEGIN CHANGE
		// send mail to invitor, if invitation is confirmed
		if (status == InviteMembersConstants.STATUS_ACCEPTED) {
			MemberRequest memberRequest = MemberRequestLocalServiceUtil.getMemberRequest(
					memberRequestId);
			
			if (UserNotificationManagerUtil.isDeliver(
					memberRequest.getUserId(),
					PortletKeys.SO_INVITE_MEMBERS, 0,
					MembershipRequestConstants.STATUS_APPROVED,
					UserNotificationDeliveryConstants.TYPE_EMAIL)) {
				sendInvitationApprovedMail(memberRequest, themeDisplay);
			}
			
			if (UserNotificationManagerUtil.isDeliver(
					memberRequest.getReceiverUserId(),
					PortletKeys.SO_INVITE_MEMBERS, 0,
					MembershipRequestConstants.STATUS_APPROVED,
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {
				
				createNotificationEvent(memberRequest);
			}
			
		}
		// END CHANGE
	}

	protected void doSendInvite(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");
		long[] receiverUserIds = getLongArray(actionRequest, "receiverUserIds");
		String[] receiverEmailAddresses = getStringArray(
			actionRequest, "receiverEmailAddresses");
		long invitedRoleId = ParamUtil.getLong(actionRequest, "invitedRoleId");
		long invitedTeamId = ParamUtil.getLong(actionRequest, "invitedTeamId");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!UserLocalServiceUtil.hasGroupUser(
				groupId, themeDisplay.getUserId())) {

			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		long plid = themeDisplay.getPlid();

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPrivateLayout()) {
			Group guestGroup = GroupLocalServiceUtil.getGroup(
				themeDisplay.getCompanyId(), GroupConstants.GUEST);

			plid = guestGroup.getDefaultPublicPlid();
		}

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, PortletKeys.NOTIFICATIONS, plid,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("mvcPath", "/notifications/view.jsp");
		portletURL.setParameter("actionable", StringPool.TRUE);
		portletURL.setWindowState(WindowState.MAXIMIZED);

		serviceContext.setAttribute("redirectURL", portletURL.toString());

		String createAccountURL = getCreateAccountURL(
			PortalUtil.getHttpServletRequest(actionRequest), themeDisplay);

		serviceContext.setAttribute("createAccountURL", createAccountURL);

		serviceContext.setAttribute("loginURL", themeDisplay.getURLSignIn());

		MemberRequestLocalServiceUtil.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverUserIds, invitedRoleId,
			invitedTeamId, serviceContext);

		MemberRequestLocalServiceUtil.addMemberRequests(
			themeDisplay.getUserId(), groupId, receiverEmailAddresses,
			invitedRoleId, invitedTeamId, serviceContext);
	}

	protected String getCreateAccountURL(
			HttpServletRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(themeDisplay.getDefaultUser());

		if (LayoutPermissionUtil.contains(
				permissionChecker, themeDisplay.getLayout(),
				themeDisplay.getControlPanelCategory(), true,
				ActionKeys.VIEW) &&
			LayoutPermissionUtil.contains(
				permissionChecker, themeDisplay.getLayout(), false,
				ActionKeys.VIEW)) {

			return PortalUtil.getCreateAccountURL(request, themeDisplay);
		}

		Group group = GroupLocalServiceUtil.getGroup(
			themeDisplay.getCompanyId(), GroupConstants.GUEST);

		PortletURL createAccountURL = PortletURLFactoryUtil.create(
			request, com.liferay.portal.util.PortletKeys.LOGIN,
			group.getDefaultPublicPlid(), PortletRequest.RENDER_PHASE);

		createAccountURL.setParameter("struts_action", "/login/create_account");
		createAccountURL.setParameter("saveLastPath", Boolean.FALSE.toString());
		createAccountURL.setPortletMode(PortletMode.VIEW);
		createAccountURL.setWindowState(WindowState.MAXIMIZED);

		return createAccountURL.toString();
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	protected String[] getStringArray(
		PortletRequest portletRequest, String name) {

		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value));
	}

	/*
	 * This method sends a mail if an invited user confirms her invitation.
	 */
	private void sendInvitationApprovedMail (MemberRequest memberRequest, ThemeDisplay themeDisplay) 
			throws PortalException, SystemException, WindowStateException, PortletModeException, IOException {
		
		// user who got invitation
		User user = UserLocalServiceUtil.getUser(memberRequest.getReceiverUserId());
		
		long companyId = memberRequest.getCompanyId();

		Group workspace = GroupLocalServiceUtil.getGroup(memberRequest.getGroupId());

		// user who get's the mail
		User receiverUser = null; 

		if (memberRequest.getReceiverUserId() > 0) {
			receiverUser = UserLocalServiceUtil.getUser(memberRequest.getUserId());
		}
		
		if (receiverUser == null)
			return;
		
		// check language, default: English
		String language = "";
		if (receiverUser.getLocale().equals(Locale.GERMANY)) {
			language = "_"+Locale.GERMANY.toString();
		} 
		
		// load template
		String subject = ContentUtil.get(
				"com/liferay/so/invitemembers/dependencies/" +"invitation_approved_mail_subject"+language+".tmpl");
		String body = ContentUtil.get(
				"com/liferay/so/invitemembers/dependencies/" +"invitation_approved_mail_body"+language+".tmpl");
		
		// replace placeholders
		subject = StringUtil.replace(
			subject, new String[] {
						"[$MEMBER_REQUEST_USER$]", 
						"[$MEMBER_REQUEST_GROUP$]"
					}, new String[] {
						user.getFullName(), 
						workspace.getDescriptiveName()
					});
		
		body = StringUtil.replace(
			body, new String[] {
						"[$TO_NAME$]", 
						"[$MEMBER_REQUEST_USER$]",
						"[$MEMBER_REQUEST_GROUP$]", 
						"[$CONFIG_URL$]"
					}, new String[] {  
						receiverUser.getFullName(), 
						user.getFullName(),
						workspace.getDescriptiveName(), // translation needed? 
						UserNotificationHelper.getConfigURL(themeDisplay, receiverUser)
					});
		
		// send mail
		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);

		String toName = StringPool.BLANK;
		String toAddress = receiverUser.getEmailAddress();
		
		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage mailMessage = new MailMessage(
			from, to, subject, body, true);
		
		MailServiceUtil.sendEmail(mailMessage);
	}
	
	private void createNotificationEvent (MemberRequest memberRequest) 
			throws PortalException, SystemException {
		// [$MEMBER_REQUEST_USER$] hat Ihre Einladung [$MEMBER_REQUEST_GROUP$] beizutreten angenommen.
		
		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put(
			"classPK", memberRequest.getMemberRequestId());
		notificationEventJSONObject.put(
			"userId", memberRequest.getReceiverUserId());

		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), PortletKeys.SO_INVITE_MEMBERS,
				notificationEventJSONObject);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
				memberRequest.getUserId(), notificationEvent);
		
	}
	
	private static Log _log = LogFactoryUtil.getLog(InviteMembersPortlet.class);

}