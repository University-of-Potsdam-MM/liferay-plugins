package com.liferay.notifications.notifications;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;

import com.liferay.compat.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

/**
 * 
 * @author Patrick Wolfien
 *
 */
public class SiteMembershipUserNotificationHandler extends
		BaseUserNotificationHandler {

	public SiteMembershipUserNotificationHandler() {
		setActionable(true); // use request template
		setPortletId(PortletKeys.SITE_MEMBERSHIPS_ADMIN);
	}
	
	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());
		
		long membershipRequestId = jsonObject.getLong("classPK");
		
		MembershipRequest membershipRequest = 
			MembershipRequestLocalServiceUtil.fetchMembershipRequest(membershipRequestId);
		
		Group group = null;

		if (membershipRequest != null) {
			group = GroupLocalServiceUtil.fetchGroup(
				membershipRequest.getGroupId());
		}

		if ((group == null) || (membershipRequest == null)) {
			UserNotificationEventLocalServiceUtil.deleteUserNotificationEvent(
				userNotificationEvent.getUserNotificationEventId());

			return null;
		}
		
		String title = StringPool.BLANK;
		
		if (membershipRequest.getStatusId() ==
				MembershipRequestConstants.STATUS_PENDING) {
			
			title = LanguageUtil.format(serviceContext.getLocale(), "notification-request-x-wants-to-join-x", 
					new Object[] { 
						getUserNameLink(membershipRequest.getUserId(), serviceContext),
						getWorkspaceDescriptiveName(membershipRequest.getGroupId(), serviceContext) 
					});
		}
		
		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		// confirm button: nutzer darf beitreten
		PortletURL confirmURL = liferayPortletResponse.createActionURL(
				"1_WAR_notificationsportlet");	// Portlet dessen Methode verwendet wird

		confirmURL.setParameter(
			ActionRequest.ACTION_NAME, "updateMembershipRequest");	// Methode die aufgerufen wird
		confirmURL.setParameter(
			"membershipRequestId", String.valueOf(membershipRequestId)); // Parameter der Methode
		confirmURL.setParameter(
			"statusId",
			String.valueOf(MembershipRequestConstants.STATUS_APPROVED)); // Parameter der Methode
		confirmURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId())); // Parameter der Methode
		confirmURL.setParameter(
				"replyComments",
				"YES");
		confirmURL.setWindowState(WindowState.NORMAL);

		// ignore button: nutzer darf nicht beitreten
		PortletURL ignoreURL = liferayPortletResponse.createActionURL(
				"1_WAR_notificationsportlet");

		ignoreURL.setParameter(
			ActionRequest.ACTION_NAME, "updateMembershipRequest");	// Methode die aufgerufen wird
		ignoreURL.setParameter(
			"membershipRequestId", String.valueOf(membershipRequestId));	// Parameter der Methode
		ignoreURL.setParameter(
			"statusId", String.valueOf(MembershipRequestConstants.STATUS_DENIED));	// Parameter der Methode
		ignoreURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));	// Parameter der Methode
		ignoreURL.setParameter(
				"replyComments",
				"NO");
		ignoreURL.setWindowState(WindowState.NORMAL);
		
		
		return StringUtil.replace(
				getBodyTemplate(),
				new String[] {
					"[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
					"[$IGNORE_URL$]", "[$TITLE$]"
				},
				new String[] {
					serviceContext.translate("confirm"), confirmURL.toString(),
					serviceContext.translate("ignore"), ignoreURL.toString(), title
				});
		
	}
	
//	@Override
//	protected String getTitle(JSONObject jsonObject,
//			AssetRenderer assetRenderer, ServiceContext serviceContext)
//			throws Exception {
//
//		return super.getTitle(jsonObject, assetRenderer, serviceContext);
//	}
	
	@Override
	protected String getLink(UserNotificationEvent userNotificationEvent,
			ServiceContext serviceContext) throws Exception {

		return StringPool.BLANK;//super.getLink(userNotificationEvent, serviceContext);
	}
	
	protected String getWorkspaceDescriptiveName (
			long groupId, ServiceContext serviceContext) 
		throws PortalException, SystemException {
		
		Group group = GroupLocalServiceUtil.fetchGroup(groupId);
		
		if (group == null)
			return StringPool.BLANK;
		else
			return group.getDescriptiveName(serviceContext.getLocale());
	}
	
	protected String getUserNameLink(
		long userId, ServiceContext serviceContext) {

		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			String userName = user.getFullName();

			String userDisplayURL = user.getDisplayURL(
				serviceContext.getThemeDisplay());

			return "<a href=\"" + userDisplayURL + "\">" +
				HtmlUtil.escape(userName) + "</a>";
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}
}
