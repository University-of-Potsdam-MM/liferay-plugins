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

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.mail.internet.InternetAddress;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.notifications.notifications.portlet.NotificationsPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalService;
import com.liferay.portlet.announcements.service.AnnouncementsEntryLocalServiceWrapper;
import com.liferay.portlet.announcements.service.persistence.AnnouncementsEntryFinderUtil;
import com.liferay.so.util.PortletKeys;

/**
 * @author Jonathan Lee
 * @author Evan Thibodeau
 */
public class AnnouncementsEntryLocalServiceImpl
	extends AnnouncementsEntryLocalServiceWrapper {

	public AnnouncementsEntryLocalServiceImpl(
		AnnouncementsEntryLocalService announcementsEntryLocalService) {

		super(announcementsEntryLocalService);
	}

	@Override
	public AnnouncementsEntry addEntry(
			long userId, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean displayImmediately,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, int priority, boolean alert)
		throws PortalException, SystemException {

		AnnouncementsEntry announcementEntry = super.addEntry(
			userId, classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayImmediately, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);

		if (announcementEntry != null) {
			Date displayDate = announcementEntry.getDisplayDate();

			if (!displayDate.after(announcementEntry.getCreateDate())) {
				sendNotificationEvent(announcementEntry);
			}
		}

		return announcementEntry;
	}

	@Override
	public void checkEntries() throws PortalException, SystemException {
		super.checkEntries();

		sendNotificationEvent();
	}

	protected void sendNotificationEvent()
		throws PortalException, SystemException {

		Date now = new Date();

		if (_previousCheckDate == null) {
			_previousCheckDate = new Date(
				now.getTime() - _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL);
		}

		List<AnnouncementsEntry> announcementEntries =
			AnnouncementsEntryFinderUtil.findByDisplayDate(
				now, _previousCheckDate);

		if (_log.isDebugEnabled()) {
			_log.debug("Processing " + announcementEntries.size() + " entries");
		}

		for (AnnouncementsEntry announcementEntry : announcementEntries) {
			Date displayDate = announcementEntry.getDisplayDate();

			if (displayDate.after(announcementEntry.getCreateDate())) {
				sendNotificationEvent(announcementEntry);
			}
		}

		_previousCheckDate = now;
	}

	protected void sendNotificationEvent(AnnouncementsEntry announcementEntry)
		throws PortalException, SystemException {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put(
			"classPK", announcementEntry.getEntryId());
		notificationEventJSONObject.put(
			"userId", announcementEntry.getUserId());

		MessageBusUtil.sendMessage(
			DestinationNames.ASYNC_SERVICE,
			new NotificationProcessCallable(
				announcementEntry, notificationEventJSONObject));
	}

	private static final long _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL =
		GetterUtil.getInteger(
			PropsUtil.get(
				PropsKeys.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL)) * Time.MINUTE;

	private static Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryLocalServiceImpl.class);

	private Date _previousCheckDate;

	private static class NotificationProcessCallable
		implements ProcessCallable<Serializable> {

		public NotificationProcessCallable(
			AnnouncementsEntry announcementEntry,
			JSONObject notificationEventJSONObject) {

			_announcementEntry = announcementEntry;
			_notificationEventJSONObject = notificationEventJSONObject;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				sendUserNotifications(
					_announcementEntry, _notificationEventJSONObject);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		protected void sendUserNotifications(
				AnnouncementsEntry announcementEntry,
				JSONObject notificationEventJSONObject)
			throws PortalException, SystemException {

			int count = 0;
			long teamId = 0;

			LinkedHashMap<String, Object> params =
				new LinkedHashMap<String, Object>();

			if (announcementEntry.getClassNameId() == 0) {
				count = UserLocalServiceUtil.getUsersCount();
			}
			else {
				String className = announcementEntry.getClassName();

				long classPK = announcementEntry.getClassPK();

				if (classPK <= 0) {
					return;
				}

				if (className.equals(Group.class.getName())) {
					params.put("inherit", Boolean.TRUE);
					params.put("usersGroups", classPK);
				}
				else if (className.equals(Organization.class.getName())) {
					Organization organization =
						OrganizationLocalServiceUtil.fetchOrganization(classPK);

					if (organization == null) {
						return;
					}

					params.put(
						"usersOrgsTree",
						ListUtil.fromArray(new Organization[]{organization}));
				}
				else if (className.equals(Role.class.getName())) {
					Role role = RoleLocalServiceUtil.fetchRole(classPK);

					if (role == null) {
						return;
					}

					if (role.getType() == RoleConstants.TYPE_REGULAR) {
						params.put("inherit", Boolean.TRUE);
						params.put("usersRoles", classPK);
					}
					else if (role.isTeam()) {
						teamId = role.getClassPK();

						count = UserLocalServiceUtil.getTeamUsersCount(teamId);
					}
					else {
						params.put(
							"userGroupRole",
							new Long[] {Long.valueOf(0), classPK});
					}
				}
				else if (className.equals(UserGroup.class.getName())) {
					params.put("usersUserGroups", classPK);
				}

				if (!params.isEmpty()) {
					count = UserLocalServiceUtil.searchCount(
						announcementEntry.getCompanyId(), null,
						WorkflowConstants.STATUS_APPROVED, params);
				}
			}

			int pages = count / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= pages; i++) {
				List<User> users = null;

				int start = (i * Indexer.DEFAULT_INTERVAL);
				int end = start + Indexer.DEFAULT_INTERVAL;

				if (announcementEntry.getClassNameId() == 0) {
					users = UserLocalServiceUtil.getUsers(start, end);
				}
				else if (teamId > 0) {
					users = UserLocalServiceUtil.getTeamUsers(
						teamId, start, end);
				}
				else {
					users = UserLocalServiceUtil.search(
						announcementEntry.getCompanyId(), null,
						WorkflowConstants.STATUS_APPROVED, params, start, end,
						(OrderByComparator)null);
				}

				for (User user : users) {
					if (UserNotificationManagerUtil.isDeliver(
							user.getUserId(), PortletKeys.SO_ANNOUNCEMENTS, 0,
							0,
							UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

						NotificationEvent notificationEvent =
							NotificationEventFactoryUtil.
								createNotificationEvent(
									System.currentTimeMillis(),
									PortletKeys.SO_ANNOUNCEMENTS,
									notificationEventJSONObject);

						notificationEvent.setDeliveryRequired(0);

						UserNotificationEventLocalServiceUtil.
							addUserNotificationEvent(
								user.getUserId(), notificationEvent);
					}
					
					// BEGIN CHANGE
					// send mail from notifications portlet
					// after checking for notifications, mails will be checked too
					if (UserNotificationManagerUtil.isDeliver(
							user.getUserId(), PortletKeys.SO_ANNOUNCEMENTS, 0,
							0,
							UserNotificationDeliveryConstants.TYPE_EMAIL)) {
						
						try {
							sendMail(announcementEntry, user.getUserId());
						} catch (Exception e) {
							throw new SystemException(e);
						}
						
					}
					// END CHANGE
				}
			}
		}

		private void sendMail(AnnouncementsEntry announcementEntry, long userId) 
			throws Exception{
			
//			User sender = UserLocalServiceUtil.getUser(announcementEntry.getUserId());
			
			User recipient = UserLocalServiceUtil.getUser(userId);
			
			Company company = CompanyLocalServiceUtil.getCompany(announcementEntry.getCompanyId());
			
			Group workspace = GroupLocalServiceUtil.getGroup(announcementEntry.getGroupId());
			
			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(workspace.getGroupId(), true);
			
			Layout layout = null;
			
			// iterate over all layouts of workspace too get all portlets
			for (Layout possibleLayout : layouts) {
				LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)possibleLayout.getLayoutType();
				List<String> actualPortletList = layoutTypePortlet.getPortletIds();
				
				// iterate over all portlets to find announcement portlet
				for (String portletId : actualPortletList) {
					if (PortletKeys.SO_ANNOUNCEMENTS.equals(portletId)) {
						layout = possibleLayout;
						break;
					}
				}
			}
			
			String portalURL = PortalUtil.getPortalURL(
					company.getVirtualHostname(), PortalUtil.getPortalPort(false), false);
			
			if (!portalURL.contains("localhost"))
				portalURL = company.getPortalURL(recipient.getGroupId());
			
			String notificationConfigURL = portalURL + NOTIFICATION_CONFIG_URL;
			notificationConfigURL = StringUtil.replace(notificationConfigURL, 
					"[$LOGIN_NAME$]", recipient.getLogin());
			
			String layoutURL; 
			
			if (layout == null) {
				layoutURL = portalURL+"/user/"+recipient.getLogin(); // falls layout nicht gefunden wird, verlinke eigene Seite
			} else {
				layoutURL = portalURL+PortalUtil.getLayoutActualURL(layout);
			}
			
			String language = "";
			
			if (recipient.getLocale().equals(Locale.GERMANY))
				language = "_"+Locale.GERMANY.toString();
			
			String subject = "";
			String body = "";
			
			subject = StringUtil.read(
					NotificationsPortlet.class.getResourceAsStream(
					"dependencies/email_subject"+language+".tmpl"));
			
			subject = StringUtil.replace(
					subject, new String[] { 
							"[$WORKSPACE_NAME$]", 
							"[$ENTRY_TITLE$]",
							"[$ENTRY_USER_NAME$]"
					},
					new String[] {
							workspace.getName(),
							announcementEntry.getTitle(),
							announcementEntry.getUserName()
					});
			
			body = StringUtil.read(
					NotificationsPortlet.class.getResourceAsStream(
					"dependencies/email_body"+language+".tmpl"));
			
			body = StringUtil.replace(
					body, 
					new String[] {
						"[$WORKSPACE_NAME$]", "[$TO_NAME$]",
						"[$ENTRY_USER_NAME$]", "[$ENTRY_CONTENT$]", 
						"[$ENTRY_URL$]", 
						"[$CONFIG_URL$]"
					},
					new String[] {
						workspace.getName(), recipient.getFullName(),
						announcementEntry.getUserName(), announcementEntry.getContent(),
						layoutURL, notificationConfigURL
					});
				
			String fromName = PrefsPropsUtil.getString(
					announcementEntry.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
			String fromAddress = PrefsPropsUtil.getString(
					announcementEntry.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			
			InternetAddress to = new InternetAddress(
					recipient.getEmailAddress());
			
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
			
			MailServiceUtil.sendEmail(mailMessage);
		}
		
		private static String NOTIFICATION_CONFIG_URL = "/user/[$LOGIN_NAME$]?p_p_id=1_WAR_notificationsportlet&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&_1_WAR_notificationsportlet_actionable=false&_1_WAR_notificationsportlet_mvcPath=%2Fnotifications%2Fconfiguration.jsp";
		private static final long serialVersionUID = 1L;

		private AnnouncementsEntry _announcementEntry;
		private JSONObject _notificationEventJSONObject;

	}

}