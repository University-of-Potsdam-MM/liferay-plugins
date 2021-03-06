/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.sites.action;

import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.NoSuchGroupException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.membershippolicy.MembershipPolicyException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserGroupGroupRoleServiceUtil;
import com.liferay.portal.service.UserGroupRoleServiceUtil;
import com.liferay.portal.service.UserGroupServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.service.UserServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.ContentUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class EditGroupAssignmentsAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("group_organizations")) {
				updateGroupOrganizations(actionRequest);
			}
			else if (cmd.equals("group_user_groups")) {
				updateGroupUserGroups(actionRequest);
			}
			else if (cmd.equals("group_users")) {
				updateGroupUsers(actionRequest);
			}
			else if (cmd.equals("user_group_group_role")) {
				updateUserGroupGroupRole(actionRequest);
			}
			else if (cmd.equals("user_group_role")) {
				updateUserGroupRole(actionRequest);
			}

			if (Validator.isNotNull(cmd)) {
				String redirect = ParamUtil.getString(
					actionRequest, "assignmentsRedirect");

				if (Validator.isNull(redirect)) {
					redirect = ParamUtil.getString(actionRequest, "redirect");
				}

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
		catch (Exception e) {
			if (e instanceof MembershipPolicyException) {
				SessionErrors.add(actionRequest, e.getClass(), e);
			}
			else if (e instanceof NoSuchGroupException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getGroup(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchGroupException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward("portlet.sites_admin.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.sites_admin.edit_site_assignments"));
	}

	protected long[] filterAddUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<Long>(userIds.length);

		for (long userId : userIds) {
			if (!UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	protected long[] filterRemoveUserIds(long groupId, long[] userIds)
		throws Exception {

		Set<Long> filteredUserIds = new HashSet<Long>(userIds.length);

		for (long userId : userIds) {
			if (UserLocalServiceUtil.hasGroupUser(groupId, userId)) {
				filteredUserIds.add(userId);
			}
		}

		return ArrayUtil.toArray(
			filteredUserIds.toArray(new Long[filteredUserIds.size()]));
	}

	protected void updateGroupOrganizations(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addOrganizationIds"), 0L);
		long[] removeOrganizationIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeOrganizationIds"), 0L);

		OrganizationServiceUtil.addGroupOrganizations(
			groupId, addOrganizationIds);
		OrganizationServiceUtil.unsetGroupOrganizations(
			groupId, removeOrganizationIds);
	}

	protected void updateGroupUserGroups(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserGroupIds"), 0L);
		long[] removeUserGroupIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserGroupIds"), 0L);

		UserGroupServiceUtil.addGroupUserGroups(groupId, addUserGroupIds);
		UserGroupServiceUtil.unsetGroupUserGroups(groupId, removeUserGroupIds);
	}

	protected void updateGroupUsers(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addUserIds"), 0L);

		addUserIds = filterAddUserIds(groupId, addUserIds);

		long[] removeUserIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeUserIds"), 0L);

		removeUserIds = filterRemoveUserIds(groupId, removeUserIds);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		UserServiceUtil.addGroupUsers(groupId, addUserIds, serviceContext);
		UserServiceUtil.unsetGroupUsers(groupId, removeUserIds, serviceContext);

		LiveUsers.joinGroup(themeDisplay.getCompanyId(), groupId, addUserIds);
		LiveUsers.leaveGroup(
			themeDisplay.getCompanyId(), groupId, removeUserIds);
		
		// BEGIN CHANGE
		// added email method to notify user, that she has been added to a workspace
		for (long userId : addUserIds) {
			if (UserNotificationManagerUtil.isDeliver(userId,
					PortletKeys.SITE_MEMBERSHIPS_ADMIN, 0,
					3, // constants 0..2 already in use, so 3 is used for this case
					UserNotificationDeliveryConstants.TYPE_EMAIL)) {
				
				sendMail(userId, groupId, themeDisplay);
			}
			
			if (UserNotificationManagerUtil.isDeliver(userId,
					PortletKeys.SITE_MEMBERSHIPS_ADMIN, 0,
					3, // constants 0..2 already in use, so 3 is used for this case
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {
				
				sendNotificationEvent(userId, groupId, themeDisplay);
			}
		}
		// END CHANGE
	}

	protected void updateUserGroupGroupRole(ActionRequest actionRequest)
		throws Exception {

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long userGroupId = ParamUtil.getLong(actionRequest, "userGroupId");

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		UserGroupGroupRoleServiceUtil.addUserGroupGroupRoles(
			userGroupId, groupId, addRoleIds);
		UserGroupGroupRoleServiceUtil.deleteUserGroupGroupRoles(
			userGroupId, groupId, removeRoleIds);
	}

	protected void updateUserGroupRole(ActionRequest actionRequest)
		throws Exception {

		User user = PortalUtil.getSelectedUser(actionRequest, false);

		if (user == null) {
			return;
		}

		long groupId = ParamUtil.getLong(actionRequest, "groupId");

		long[] addRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "addRoleIds"), 0L);
		long[] removeRoleIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "removeRoleIds"), 0L);

		UserGroupRoleServiceUtil.addUserGroupRoles(
			user.getUserId(), groupId, addRoleIds);
		UserGroupRoleServiceUtil.deleteUserGroupRoles(
			user.getUserId(), groupId, removeRoleIds);
	}

	private void sendMail(long userId, long groupId, ThemeDisplay themeDisplay) 
		throws SystemException, PortalException, UnsupportedEncodingException, AddressException, WindowStateException, PortletModeException {
		
		User user = UserLocalServiceUtil.fetchUser(userId);
		if (user == null) {
			System.err.println("user is null");
			return;
		}
		
		Group workspace = GroupLocalServiceUtil.fetchGroup(groupId);
		if (workspace == null) {
			System.err.println("workspace is null");
			return;
		}
		
		// check language, default: English
		String language = "";
		if (user.getLocale().equals(Locale.GERMANY)) {
			language = "_"+Locale.GERMANY.toString();
		} 
		
		// load templates
		String subject = ContentUtil.get("de/unipotsdam/elis/templates/membership_user_added_email_subject"+language+".tmpl");
		String body = ContentUtil.get("de/unipotsdam/elis/templates/membership_user_added_email_body"+language+".tmpl");
		
		// replace placeholders
		subject = StringUtil.replace(
			subject, new String[] { 
						"[$MEMBER_REQUEST_GROUP$]"
					}, new String[] {
						workspace.getDescriptiveName()
					});
		
		body = StringUtil.replace(
			body, new String[] {
						"[$TO_NAME$]", 
						"[$MEMBER_REQUEST_GROUP$]", 
						"[$WORKSPACE_URL$]",
						"[$CONFIG_URL$]"
					}, new String[] {  
						user.getFullName(), 
						workspace.getDescriptiveName(), // translation needed? 
						getWorkspaceURL(workspace, themeDisplay),
						getConfigURL(themeDisplay, user)
					});
		
		String fromName = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			user.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		
		InternetAddress from = new InternetAddress(fromAddress, fromName);
		
		InternetAddress to = new InternetAddress(
			user.getEmailAddress());
	
		MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
		
		MailServiceUtil.sendEmail(mailMessage);
	}
	
	/**
	 * send website notification to user that she was added to workspace
	 * @param userId
	 * @param groupId - id of workspace
	 * @param themeDisplay 
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void sendNotificationEvent(long userId, long groupId, ThemeDisplay themeDisplay)
			throws PortalException, SystemException {
			
			Group workspace = GroupLocalServiceUtil.fetchGroup(groupId);
			if (workspace == null) {
				System.err.println("workspace is null");
				return;
			}
			
			JSONObject notificationEventJSONObject =
				JSONFactoryUtil.createJSONObject();

			notificationEventJSONObject.put(
				"userId", userId);
			notificationEventJSONObject.put(
				"workspaceName", workspace.getDescriptiveName());
			notificationEventJSONObject.put(
					"workspaceURL", getWorkspaceURL(workspace, themeDisplay));
			notificationEventJSONObject.put(
					"workspaceDeletion", false);
			/* 
			 * same notificationhandler is used for membershipRequests, 
			 * deleting workspaces and adding users to workspaces. So it
			 * is necessary to know which kind of event will be interpreted. 
			 */
			notificationEventJSONObject.put("membershipRequest", false); 
			
			NotificationEvent notificationEvent =
				NotificationEventFactoryUtil.createNotificationEvent(
					System.currentTimeMillis(), PortletKeys.SITE_MEMBERSHIPS_ADMIN,
					notificationEventJSONObject);

			notificationEvent.setDeliveryRequired(0);

			UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
				userId, notificationEvent);
		}
	
	/**
	 * This method returns the link to the workspace
	 * for a given workspace.
	 * @param themeDisplay 
	 */
	private String getWorkspaceURL (Group workspace, ThemeDisplay themeDisplay) 
			throws PortalException, SystemException {
		
		String portalURL = themeDisplay.getPortalURL();
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(workspace.getGroupId(), true);
		
		Layout layout = null;
		
		if (!layouts.isEmpty())
			layout = layouts.get(0);
		
		if (layout != null)
			return portalURL + PortalUtil.getLayoutActualURL(layout);
		
		return "";
	}
	
	/**
	 * This method returns the link to configuration tab of notifications-portlet
	 * for a given user.
	 */
	private String getConfigURL (ThemeDisplay themeDisplay, User user) 
			throws WindowStateException, PortletModeException, SystemException, PortalException {
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				user.getGroupId(), true);
		
		Layout layout  = null;
		
		if (!layouts.isEmpty())
			layout = layouts.get(0);

		if (layout == null) {
			// Notifications Portlet was not found, so display on first page
			// if there is a page, else no link can be created
			if (!layouts.isEmpty())
				layout = layouts.get(0);
			else
				return "";

		} 
		
		PortletURL myUrl = PortletURLFactoryUtil.create(
				themeDisplay.getRequest(), "1_WAR_notificationsportlet",
				layout.getPlid(), PortletRequest.RENDER_PHASE);
		myUrl.setWindowState(WindowState.MAXIMIZED);
		myUrl.setPortletMode(PortletMode.VIEW);
		myUrl.setParameter("actionable", "false");
		myUrl.setParameter("mvcPath", "/notifications/configuration.jsp");

		return myUrl.toString();
	}
}