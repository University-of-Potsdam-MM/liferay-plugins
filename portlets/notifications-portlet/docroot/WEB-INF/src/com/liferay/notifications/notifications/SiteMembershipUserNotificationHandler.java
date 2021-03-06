package com.liferay.notifications.notifications;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;

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
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.MembershipRequest;
import com.liferay.portal.model.MembershipRequestConstants;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.MembershipRequestLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;

/**
 * This class handles all website notifications for site membership requests.
 * Including:
 * 	- workspace was deleted
 *  - user was added to a workspace
 *  - user wants to join a workspace
 *  - membership request to join a workspace was approved or denied
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
		
		Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				com.liferay.notifications.util.PortletKeys.NOTIFICATIONS);
		ServletContext servletContext =
				(ServletContext)serviceContext.getAttribute(WebKeys.CTX);
		PortletConfig portletConfig =  PortletConfigFactoryUtil
				.create(portlet, servletContext);
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());
		
		boolean isMembershipRequest = jsonObject.getBoolean("membershipRequest");
		
		// check if no membershipRequest is going to be interpreted
		if (!isMembershipRequest) {
			
			boolean isWorkspaceDeletion = jsonObject.getBoolean("workspaceDeletion");
			// check if noitfication is going to inform of a deleted workspace
			if (isWorkspaceDeletion) {
				String workspaceName = jsonObject.getString("workspaceName"); // get workspacename form request
				
				String message = LanguageUtil.format(portletConfig, serviceContext.getLocale(), "workspace-x-was-deleted", 
						new String[] { 
							"<span class=\"user-notification-workspacename\">"+workspaceName+"</span>" 
						});
				
				return StringUtil.replace(
						"<div class=\"title\">[$TITLE$]</div>", 
						new String[] { "[$TITLE$]" }, 
						new String[] { message });
			}
			else {
				// notification informs user that she is added to a workspace
				
				String workspaceName = jsonObject.getString("workspaceName"); //get workspacename form request
				
				String message = LanguageUtil.format(portletConfig, serviceContext.getLocale(), "you-were-added-to-workspace-x", 
						new String[] { 
							"<span class=\"user-notification-workspacename\">"+workspaceName+"</span>"
						});
				
				return StringUtil.replace(
						"<div class=\"title\">[$TITLE$]</div>", 
						new String[] { "[$TITLE$]" }, 
						new String[] { message });
			}
			
		}
		
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
			
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(), "notification-request-x-wants-to-join-x", 
					new Object[] { 
						getUserNameLink(membershipRequest.getUserId(), serviceContext),
						getWorkspaceNameLink(membershipRequest.getGroupId(), serviceContext) 
					});
		} 
		else if (membershipRequest.getStatusId() ==
				MembershipRequestConstants.STATUS_APPROVED) {
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(), "notification-request-x-approved-your-request-to-join-x", 
					new Object[] { 
						getUserName(membershipRequest.getReplierUserId(), serviceContext),
						getWorkspaceName(membershipRequest.getGroupId(), serviceContext) 
					});
			return StringUtil.replace(
					"<div class=\"title\">[$TITLE$]</div>", 
					new String[] { "[$TITLE$]" }, 
					new String[] { title });
		}
		else if (membershipRequest.getStatusId() ==
				MembershipRequestConstants.STATUS_DENIED) {
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(), "notification-request-x-denied-your-request-to-join-x", 
					new Object[] { 
						getUserName(membershipRequest.getReplierUserId(), serviceContext),
						getWorkspaceName(membershipRequest.getGroupId(), serviceContext) 
					});
			
			return StringUtil.replace(
					"<div class=\"title\">[$TITLE$]</div>", 
					new String[] { "[$TITLE$]" }, 
					new String[] { title });
		}
		
		LiferayPortletResponse liferayPortletResponse =
			serviceContext.getLiferayPortletResponse();

		// confirm button: user is free to enter
		PortletURL confirmURL = liferayPortletResponse.createActionURL(
				"1_WAR_notificationsportlet");	// Portlet whose method is called

		confirmURL.setParameter(
			ActionRequest.ACTION_NAME, "updateMembershipRequest");	// the called method
		confirmURL.setParameter(
			"membershipRequestId", String.valueOf(membershipRequestId)); // method parameter
		confirmURL.setParameter(
			"statusId",
			String.valueOf(MembershipRequestConstants.STATUS_APPROVED)); // method parameter
		confirmURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId())); // method parameter
		confirmURL.setParameter(
				"replyComments",
				"");
		confirmURL.setWindowState(WindowState.NORMAL);

		// ignore button: user is not allowed to enter
		PortletURL ignoreURL = liferayPortletResponse.createActionURL(
				"1_WAR_notificationsportlet");

		ignoreURL.setParameter(
			ActionRequest.ACTION_NAME, "updateMembershipRequest");	// the called method
		ignoreURL.setParameter(
			"membershipRequestId", String.valueOf(membershipRequestId));	// method parameter
		ignoreURL.setParameter(
			"statusId", String.valueOf(MembershipRequestConstants.STATUS_DENIED));	// method parameter
		ignoreURL.setParameter(
			"userNotificationEventId",
			String.valueOf(userNotificationEvent.getUserNotificationEventId()));	// method parameter
		ignoreURL.setParameter(
				"replyComments",
				"");
		ignoreURL.setWindowState(WindowState.NORMAL);
		
		
		return StringUtil.replace(
				getBodyTemplate(),
				new String[] {
					"[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
					"[$IGNORE_URL$]", "[$TITLE$]"
				},
				new String[] {
					serviceContext.translate("confirm"), confirmURL.toString(),
					serviceContext.translate("deny"), ignoreURL.toString(), title
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

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				userNotificationEvent.getPayload());
		
		// check type of notificationEvent 
		boolean isWorkspaceDeletion = jsonObject.getBoolean("workspaceDeletion");
		boolean isMembershipRequest = jsonObject.getBoolean("membershipRequest");
		
		if (!isWorkspaceDeletion) {

			String workspaceURL = jsonObject.getString("workspaceURL");
			
			if (!isMembershipRequest) {
				// notificationEvent: user added to workspace
				return workspaceURL;
			}
			
			long membershipRequestId = jsonObject.getLong("classPK");
			MembershipRequest membershipRequest = MembershipRequestLocalServiceUtil.fetchMembershipRequest(membershipRequestId);
			
			if (membershipRequest.getStatusId() == MembershipRequestConstants.STATUS_DENIED) {
				
				// request was denied, so get public layout
				workspaceURL = getWorkspaceURL(membershipRequest.getGroupId(), serviceContext, false);
				
			} else {
				
				// get workspaceURL if not done yet
				if (StringPool.BLANK.equals(workspaceURL) || workspaceURL == null) 
					workspaceURL = getWorkspaceURL(membershipRequest.getGroupId(), serviceContext);
			}
			
			// use workspaceURL as link
			return workspaceURL;
		}

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
	
	/**
	 * Get workspaceName wrapped in a link.
	 * @param groupId
	 * @param serviceContext
	 * @return BLANK if an exception occurs.
	 */
	protected String getWorkspaceNameLink(long groupId,
			ServiceContext serviceContext) {

		try {
			String descriptiveName = getWorkspaceDescriptiveName(groupId,
					serviceContext);
			
			String workspaceURL = getWorkspaceURL(groupId, serviceContext);
			
			return "<a href=\"" + workspaceURL + "\">" +
				HtmlUtil.escape(descriptiveName) + "</a>";
			
		} catch (Exception e) {
			return StringPool.BLANK;
		}
		
	}
	
	protected String getUserName(
			long userId, ServiceContext serviceContext) {

			try {
				if (userId <= 0) {
					return StringPool.BLANK;
				}

				User user = UserLocalServiceUtil.getUserById(userId);

				String userName = user.getFullName();

				return "<span class=\"user-notification-username\">"
					+ HtmlUtil.escape(userName) + "</span>";
			}
			catch (Exception e) {
				return StringPool.BLANK;
			}
		}
		
		/**
		 * Get workspaceName.
		 * @param groupId
		 * @param serviceContext
		 * @return BLANK if an exception occurs.
		 */
		protected String getWorkspaceName(long groupId,
				ServiceContext serviceContext) {

			try {
				String descriptiveName = getWorkspaceDescriptiveName(groupId,
						serviceContext);
				
				return "<span class=\"user-notification-workspacename\">"
					+ HtmlUtil.escape(descriptiveName) + "</span>";
				
			} catch (Exception e) {
				return StringPool.BLANK;
			}
			
		}
	
	/**
	 * This method returns the link to the workspace
	 * for a given id.
	 * @param serviceContext 
	 */
	private String getWorkspaceURL (long groupId, ServiceContext serviceContext) 
			throws PortalException, SystemException {
		
//		String portalURL = serviceContext.getPortalURL();
//		
//		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, true);
//		
//		Layout layout = null;
//		
//		if (!layouts.isEmpty())
//			layout = layouts.get(0);
//		
//		if (layout != null)
//			return portalURL + PortalUtil.getLayoutActualURL(layout);
//		
//		return "";
		
		return getWorkspaceURL(groupId, serviceContext, true);
	}
	
	private String getWorkspaceURL (long groupId, ServiceContext serviceContext, boolean privateLayout) 
			throws PortalException, SystemException {
		
		String portalURL = serviceContext.getPortalURL();
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		
		Layout layout = null;
		
		if (!layouts.isEmpty())
			layout = layouts.get(0);
		
		if (layout != null)
			return portalURL + PortalUtil.getLayoutActualURL(layout);
		
		return "";
	}
}
