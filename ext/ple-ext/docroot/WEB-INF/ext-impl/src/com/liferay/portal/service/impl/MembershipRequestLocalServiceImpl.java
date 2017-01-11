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

package com.liferay.portal.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.MembershipRequestCommentsException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UniqueList;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroupRole;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.service.base.MembershipRequestLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.ResourcePermissionUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.util.ContentUtil;

/**
 * @author Jorge Ferrer
 */
public class MembershipRequestLocalServiceImpl
	extends MembershipRequestLocalServiceBaseImpl {

	@Override
	public MembershipRequest addMembershipRequest(
			long userId, long groupId, String comments,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(comments);

		long membershipRequestId = counterLocalService.increment();

		MembershipRequest membershipRequest =
			membershipRequestPersistence.create(membershipRequestId);

		membershipRequest.setCompanyId(user.getCompanyId());
		membershipRequest.setUserId(userId);
		membershipRequest.setCreateDate(now);
		membershipRequest.setGroupId(groupId);
		membershipRequest.setComments(comments);
		membershipRequest.setStatusId(
			MembershipRequestConstants.STATUS_PENDING);

		membershipRequestPersistence.update(membershipRequest);

		notifyGroupAdministrators(membershipRequest, serviceContext);

		return membershipRequest;
	}

	@Override
	public void deleteMembershipRequests(long groupId) throws SystemException {
		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByGroupId(groupId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequests(long groupId, int statusId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByG_S(groupId, statusId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public void deleteMembershipRequestsByUserId(long userId)
		throws SystemException {

		List<MembershipRequest> membershipRequests =
			membershipRequestPersistence.findByUserId(userId);

		for (MembershipRequest membershipRequest : membershipRequests) {
			deleteMembershipRequest(membershipRequest);
		}
	}

	@Override
	public List<MembershipRequest> getMembershipRequests(
			long userId, long groupId, int statusId)
		throws SystemException {

		return membershipRequestPersistence.findByG_U_S(
			groupId, userId, statusId);
	}

	@Override
	public boolean hasMembershipRequest(long userId, long groupId, int statusId)
		throws SystemException {

		List<MembershipRequest> membershipRequests = getMembershipRequests(
			userId, groupId, statusId);

		if (membershipRequests.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}

	@Override
	public List<MembershipRequest> search(
			long groupId, int status, int start, int end)
		throws SystemException {

		return membershipRequestPersistence.findByG_S(
			groupId, status, start, end);
	}

	@Override
	public int searchCount(long groupId, int status) throws SystemException {
		return membershipRequestPersistence.countByG_S(groupId, status);
	}

	@Override
	public void updateStatus(
			long replierUserId, long membershipRequestId, String replyComments,
			int statusId, boolean addUserToGroup, ServiceContext serviceContext)
		throws PortalException, SystemException {

		validate(replyComments);

		MembershipRequest membershipRequest =
			membershipRequestPersistence.findByPrimaryKey(membershipRequestId);

		membershipRequest.setReplyComments(replyComments);
		membershipRequest.setReplyDate(new Date());

		if (replierUserId != 0) {
			membershipRequest.setReplierUserId(replierUserId);
		}
		else {
			long defaultUserId = userLocalService.getDefaultUserId(
				membershipRequest.getCompanyId());

			membershipRequest.setReplierUserId(defaultUserId);
		}

		membershipRequest.setStatusId(statusId);

		membershipRequestPersistence.update(membershipRequest);

		if ((statusId == MembershipRequestConstants.STATUS_APPROVED) &&
			addUserToGroup) {

			long[] addUserIds = new long[] {membershipRequest.getUserId()};

			userLocalService.addGroupUsers(
				membershipRequest.getGroupId(), addUserIds);
		}

		if (replierUserId != 0) {
			notify(
				membershipRequest.getUserId(), membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REPLY_BODY, serviceContext);
		}
	}

	protected List<Long> getGroupAdministratorUserIds(long groupId)
		throws PortalException, SystemException {

		List<Long> userIds = new UniqueList<Long>();

		Group group = groupLocalService.getGroup(groupId);
		String modelResource = Group.class.getName();

		List<Role> roles = ListUtil.copy(
			ResourceActionsUtil.getRoles(
				group.getCompanyId(), group, modelResource, null));

		List<Role> teamRoles = roleLocalService.getTeamRoles(groupId);

		roles.addAll(teamRoles);

		Resource resource = resourceLocalService.getResource(
			group.getCompanyId(), modelResource,
			ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(groupId));

		List<String> actions = ResourceActionsUtil.getResourceActions(
			Group.class.getName());

		for (Role role : roles) {
			String roleName = role.getName();

			if (roleName.equals(RoleConstants.OWNER)) {
				continue;
			}

			if ((roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				 roleName.equals(RoleConstants.ORGANIZATION_OWNER)) &&
				!group.isOrganization()) {

				continue;
			}

			if (roleName.equals(RoleConstants.SITE_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.SITE_OWNER) ||
				roleName.equals(RoleConstants.ORGANIZATION_ADMINISTRATOR) ||
				roleName.equals(RoleConstants.ORGANIZATION_OWNER)) {

				Role curRole = roleLocalService.getRole(
					group.getCompanyId(), roleName);

				List<UserGroupRole> userGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, curRole.getRoleId());

				for (UserGroupRole userGroupRole : userGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}

			List<String> currentIndividualActions = new ArrayList<String>();
			List<String> currentGroupActions = new ArrayList<String>();
			List<String> currentGroupTemplateActions = new ArrayList<String>();
			List<String> currentCompanyActions = new ArrayList<String>();

			ResourcePermissionUtil.populateResourcePermissionActionIds(
				groupId, role, resource, actions, currentIndividualActions,
				currentGroupActions, currentGroupTemplateActions,
				currentCompanyActions);

			if (currentIndividualActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupActions.contains(ActionKeys.ASSIGN_MEMBERS) ||
				currentGroupTemplateActions.contains(
					ActionKeys.ASSIGN_MEMBERS) ||
				currentCompanyActions.contains(ActionKeys.ASSIGN_MEMBERS)) {

				List<UserGroupRole> currentUserGroupRoles =
					userGroupRoleLocalService.getUserGroupRolesByGroupAndRole(
						groupId, role.getRoleId());

				for (UserGroupRole userGroupRole : currentUserGroupRoles) {
					userIds.add(userGroupRole.getUserId());
				}
			}
		}

		return userIds;
	}

	protected void notify(
			long userId, MembershipRequest membershipRequest,
			String subjectProperty, String bodyProperty,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		// BEGIN CHANGE
		// replaced original code of notify with own mail method
		// also added check if mails are enabled by user
		
//		User user = userPersistence.findByPrimaryKey(userId);
//		User requestUser = userPersistence.findByPrimaryKey(
//			membershipRequest.getUserId());
//
//		String fromName = PrefsPropsUtil.getStringFromNames(
//			membershipRequest.getCompanyId(), PropsKeys.SITES_EMAIL_FROM_NAME,
//			PropsKeys.ADMIN_EMAIL_FROM_NAME);
//
//		String fromAddress = PrefsPropsUtil.getStringFromNames(
//			membershipRequest.getCompanyId(),
//			PropsKeys.SITES_EMAIL_FROM_ADDRESS,
//			PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
//
//		String toName = user.getFullName();
//		String toAddress = user.getEmailAddress();
//
//		String subject = PrefsPropsUtil.getContent(
//			membershipRequest.getCompanyId(), subjectProperty);
//
//		String body = PrefsPropsUtil.getContent(
//			membershipRequest.getCompanyId(), bodyProperty);
//
//		String statusKey = null;
//
//		if (membershipRequest.getStatusId() ==
//				MembershipRequestConstants.STATUS_APPROVED) {
//
//			statusKey = "approved";
//		}
//		else if (membershipRequest.getStatusId() ==
//					MembershipRequestConstants.STATUS_DENIED) {
//
//			statusKey = "denied";
//		}
//		else {
//			statusKey = "pending";
//		}
//
//		SubscriptionSender subscriptionSender = new SubscriptionSender();
//
//		subscriptionSender.setBody(body);
//		subscriptionSender.setCompanyId(membershipRequest.getCompanyId());
//		subscriptionSender.setContextAttributes(
//			"[$COMMENTS$]", membershipRequest.getComments(),
//			"[$REPLY_COMMENTS$]", membershipRequest.getReplyComments(),
//			"[$REQUEST_USER_ADDRESS$]", requestUser.getEmailAddress(),
//			"[$REQUEST_USER_NAME$]", requestUser.getFullName(), "[$STATUS$]",
//			LanguageUtil.get(user.getLocale(), statusKey), "[$USER_ADDRESS$]",
//			user.getEmailAddress(), "[$USER_NAME$]", user.getFullName());
//		subscriptionSender.setFrom(fromAddress, fromName);
//		subscriptionSender.setHtmlFormat(true);
//		subscriptionSender.setMailId(
//			"membership_request", membershipRequest.getMembershipRequestId());
//		subscriptionSender.setScopeGroupId(membershipRequest.getGroupId());
//		subscriptionSender.setServiceContext(serviceContext);
//		subscriptionSender.setSubject(subject);
//		subscriptionSender.setUserId(userId);
//
//		subscriptionSender.addRuntimeSubscribers(toAddress, toName);
//		
//		subscriptionSender.flushNotificationsAsync();
//		
		// check request status
		// pending: user asks to join workspace
		// denied/approved: workspace member replies to request
		if (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_PENDING) {
			// isDeliver checks if user enabled mails for pending in notifications 
			if (UserNotificationManagerUtil.isDeliver(userId,
					PortletKeys.SITE_MEMBERSHIPS_ADMIN, 0,
					MembershipRequestConstants.STATUS_PENDING,
					UserNotificationDeliveryConstants.TYPE_EMAIL)) {
				// send mail for pending request
				sendMail(userId, membershipRequest, serviceContext);
			}
			
			if (UserNotificationManagerUtil.isDeliver(userId,
					PortletKeys.SITE_MEMBERSHIPS_ADMIN, 0,
					MembershipRequestConstants.STATUS_PENDING,
					UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

				sendNotificationEvent(userId, membershipRequest, serviceContext);
			}
			
		} else {
			// isDeliver checks if user enabled mails for reply in notifications			
			if ((UserNotificationManagerUtil.isDeliver(userId,
							PortletKeys.SITE_MEMBERSHIPS_ADMIN, 0,
							MembershipRequestConstants.STATUS_DENIED,
							UserNotificationDeliveryConstants.TYPE_EMAIL))) {
				// send mail for approved or denied request
				sendMail(userId, membershipRequest, serviceContext);
			}
		}
		// END CHANGE
		
	}

	protected void notifyGroupAdministrators(
			MembershipRequest membershipRequest, ServiceContext serviceContext)
		throws PortalException, SystemException {

		List<Long> userIds = getGroupAdministratorUserIds(
			membershipRequest.getGroupId());

		for (Long userId : userIds) {
			notify(
				userId, membershipRequest,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_SUBJECT,
				PropsKeys.SITES_EMAIL_MEMBERSHIP_REQUEST_BODY, serviceContext);
		}
	}

	protected void validate(String comments) throws PortalException {
		if (Validator.isNull(comments) || Validator.isNumber(comments)) {
			throw new MembershipRequestCommentsException();
		}
	}
	
	private void sendNotificationEvent(long userId, MembershipRequest membershipRequest, 
			ServiceContext serviceContext)
		throws PortalException, SystemException {
		
		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put("actionRequired", true);
		notificationEventJSONObject.put(
			"classPK", membershipRequest.getMembershipRequestId());
		notificationEventJSONObject.put(
			"userId", membershipRequest.getUserId());
		
		NotificationEvent notificationEvent =
			NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), PortletKeys.SITE_MEMBERSHIPS_ADMIN,
				notificationEventJSONObject);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(
			userId, notificationEvent);
	}
	
	/**
	 * This method creates and sends email notification. Including: <br>
	 * 	- load templates<br>
	 *  - replace placeholders<br>
	 *  - set sender and recipient<br> 
	 * @param userId
	 * @param membershipRequest
	 * @param serviceContext
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void sendMail(long userId, MembershipRequest membershipRequest, 
			ServiceContext serviceContext) 
		throws SystemException, PortalException {
		
		// email recipient
		User recipient = UserLocalServiceUtil.fetchUser(userId);
		
		if (recipient == null)
			return;
		
		// user who wants to join workspace
		User requestUser = UserLocalServiceUtil.fetchUser(membershipRequest.getUserId());
		
		Group workspace = GroupLocalServiceUtil.getGroup(membershipRequest.getGroupId());
		
		// check status of request to load fitting template 
		String statusKey = "pending";
		String emailType = "";
		if (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_DENIED) {
			statusKey = "denied";
			emailType = "_response";
		}
		
		if (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_APPROVED) {
			statusKey = "approved";
			emailType = "_response";
		}
		
		// check language, default: English
		String language = "";
		if (recipient.getLocale().equals(Locale.GERMANY)) {
			language = "_"+Locale.GERMANY.toString();
		} 
		
		// load templates
		String subject = ContentUtil.get("de/unipotsdam/elis/templates/membership_request"+emailType+"_email_subject"+language+".tmpl");
		String body = ContentUtil.get("de/unipotsdam/elis/templates/membership_request"+emailType+"_email_body"+language+".tmpl");
		
		// get Link to notification configuration
		String configURL = "";
		try {
			configURL = getConfigURL(serviceContext, recipient);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		
		// get link to Workspace Member Management
		String memberManagementURL = "";
		try {
			memberManagementURL = getMemberManagementURL(serviceContext, recipient, workspace);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		
		// replace placeholders
		subject = StringUtil.replace(
			subject, new String[] {
						"[$MEMBER_REQUEST_USER$]", 
						"[$MEMBER_REQUEST_GROUP$]"
					}, new String[] {
						requestUser.getFullName(), 
						workspace.getDescriptiveName()
					});
		
		body = StringUtil.replace(
			body, new String[] {
						"[$TO_NAME$]", 
						"[$MEMBER_REQUEST_USER$]",
						"[$MEMBER_REQUEST_GROUP$]", 
						"[$MEMBERSHIP_URL$]",
						"[$STATUS$]",
						"[$COMMENT$]",
						"[$CONFIG_URL$]"
					}, new String[] {  
						recipient.getFullName(), 
						requestUser.getFullName(),
						workspace.getDescriptiveName(), // translation needed? 
						memberManagementURL,
						LanguageUtil.get(recipient.getLocale(), statusKey),
						membershipRequest.getReplyComments(),
						configURL//getConfigURL(serviceContext, recipient)
					});
		
		String fromName = PrefsPropsUtil.getString(
			recipient.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
				recipient.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
	
		try {

			InternetAddress from = new InternetAddress(fromAddress, fromName);
		
			InternetAddress to = new InternetAddress(
				recipient.getEmailAddress());
		
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
		
			MailServiceUtil.sendEmail(mailMessage);
		
		} catch (Exception e) {
			throw new SystemException(e);
		}
	}
	
	/**
	 * This method returns a link to the members page of a given workspace.
	 * @param serviceContext
	 * @param user
	 * @param workspace
	 * @return
	 * @throws WindowStateException
	 * @throws PortletModeException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private String getMemberManagementURL (ServiceContext serviceContext, User user, Group workspace) 
			throws WindowStateException, PortletModeException, SystemException, PortalException {
		
		Company company = CompanyLocalServiceUtil.getCompany(user.getCompanyId());
		
		String portalURL = serviceContext.getPortalURL();
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(workspace.getGroupId(), true);
		
		Layout layout = null;
		
		// iterate over all layouts of workspace too get all portlets
		for (Layout possibleLayout : layouts) {
			LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)possibleLayout.getLayoutType();
			List<String> actualPortletList = layoutTypePortlet.getPortletIds();
			
			// iterate over all portlets to find site admin portlet
			for (String portletId : actualPortletList) {
				if ("174".equals(portletId)) {
					layout = possibleLayout;
					break;
				}
			}
		}
		
		if (layout != null)
			return portalURL + PortalUtil.getLayoutActualURL(layout);
		
		return "";
	}
	
	/**
	 * This method returns the link to configuration tab of notifications-portlet
	 * for a given user.
	 * @param serviceContext
	 * @param user
	 * @return
	 * @throws WindowStateException
	 * @throws PortletModeException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private String getConfigURL (ServiceContext serviceContext, User user) 
			throws WindowStateException, PortletModeException, SystemException, PortalException {
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				user.getGroupId(), true);
		
		Layout layout  = null;
		
		if (!layouts.isEmpty())
			layout = layouts.get(0);

		if (layout != null) {
			
			PortletURL myUrl = PortletURLFactoryUtil.create(
					serviceContext.getRequest(), "1_WAR_notificationsportlet",
					layout.getPlid(), PortletRequest.RENDER_PHASE);
			myUrl.setWindowState(WindowState.MAXIMIZED);
			myUrl.setPortletMode(PortletMode.VIEW);
			myUrl.setParameter("actionable", "false");
			myUrl.setParameter("mvcPath", "/notifications/configuration.jsp");
	
			return myUrl.toString();
		}
		return "";
	}

}