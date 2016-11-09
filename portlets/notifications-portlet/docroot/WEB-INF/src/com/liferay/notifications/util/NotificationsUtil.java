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

package com.liferay.notifications.util;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.notifications.model.UserNotificationEvent;
import com.liferay.notifications.notifications.portlet.NotificationsPortlet;
import com.liferay.notifications.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetRenderer;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryModel;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.mail.internet.InternetAddress;

/**
 * @author Lin Cui
 */
public class NotificationsUtil {

	public static List<UserNotificationEvent>
		getArchivedUserNotificationEvents(
			long userId, boolean actionRequired, boolean archived, int start,
			int end)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getArchivedUserNotificationEvents(
					userId, actionRequired, archived, start, end);
	}

	public static List<UserNotificationEvent>
		getArchivedUserNotificationEvents(
			long userId, boolean archived, int start, int end)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getArchivedUserNotificationEvents(userId, archived, start, end);
	}

	public static int getArchivedUserNotificationEventsCount(
			long userId, boolean archived)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getArchivedUserNotificationEventsCount(userId, archived);
	}

	public static int getArchivedUserNotificationEventsCount(
			long userId, boolean actionRequired, boolean archived)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getArchivedUserNotificationEventsCount(
					userId, actionRequired, archived);
	}

	public static List<UserNotificationEvent>
			getDeliveredUserNotificationEvents(
				long userId, boolean delivered, boolean actionRequired,
				int start, int end)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getDeliveredUserNotificationEvents(
					userId, delivered, actionRequired, start, end);
	}

	public static List<UserNotificationEvent>
		getDeliveredUserNotificationEvents(
			long userId, boolean delivered, int start, int end)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getDeliveredUserNotificationEvents(
					userId, delivered, start, end);
	}

	public static int getDeliveredUserNotificationEventsCount(
			long userId, boolean delivered)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getDeliveredUserNotificationEventsCount(userId, delivered);
	}

	public static int getDeliveredUserNotificationEventsCount(
			long userId, boolean delivered, boolean actionRequired)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getDeliveredUserNotificationEventsCount(
					userId, delivered, actionRequired);
	}

	public static String getEntryURL(
		AssetRenderer assetRenderer, String portletKey,
		ServiceContext serviceContext) {

		try {
			long controlPanelPlid = PortalUtil.getControlPanelPlid(
				serviceContext.getCompanyId());

			if (serviceContext.getPlid() == controlPanelPlid) {
				LiferayPortletRequest liferayPortletRequest =
					serviceContext.getLiferayPortletRequest();

				ThemeDisplay themeDisplay =
					(ThemeDisplay)liferayPortletRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				long plid = PortalUtil.getPlidFromPortletId(
					assetRenderer.getGroupId(), portletKey);

				themeDisplay.setPlid(plid);
			}

			return assetRenderer.getURLViewInContext(
				serviceContext.getLiferayPortletRequest(),
				serviceContext.getLiferayPortletResponse(), null);
		}
		catch (Exception e) {
			return null;
		}
	}

	public static List<UserNotificationEvent>
		getUserNotificationEvents(
			long userId, boolean actionRequired, int start, int end)
		throws PortalException, SystemException {

		return UserNotificationEventLocalServiceUtil.getUserNotificationEvents(
			userId, actionRequired, start, end);
	}

	public static int getUserNotificationEventsCount(
			long userId, boolean actionRequired)
		throws PortalException, SystemException {

		return
			UserNotificationEventLocalServiceUtil.
				getUserNotificationEventsCount(userId, actionRequired);
	}

	public static void sendNotificationEvent(
			long companyId, String portletKey, String className, long classPK,
			String entryTitle, String entryURL, int notificationType,
			List<ObjectValuePair<String, Long>> subscribersOVPs, long userId)
		throws PortalException, SystemException {

		JSONObject notificationEventJSONObject =
			JSONFactoryUtil.createJSONObject();

		notificationEventJSONObject.put("className", className);
		notificationEventJSONObject.put("classPK", classPK);
		notificationEventJSONObject.put("entryTitle", entryTitle);
		notificationEventJSONObject.put("entryURL", entryURL);
		notificationEventJSONObject.put("notificationType", notificationType);
		notificationEventJSONObject.put("userId", userId);

		MessageBusUtil.sendMessage(
			DestinationNames.ASYNC_SERVICE,
			new NotificationProcessCallable(
				companyId, portletKey, notificationEventJSONObject,
				subscribersOVPs));
	}

	private static class NotificationProcessCallable
		implements ProcessCallable<Serializable> {

		public NotificationProcessCallable(
			long companyId, String portletKey,
			JSONObject notificationEventJSONObject,
			List<ObjectValuePair<String, Long>> subscribersOVPs) {

			_companyId = companyId;
			_notificationEventJSONObject = notificationEventJSONObject;
			_portletKey = portletKey;
			_subscribersOVPs = subscribersOVPs;
		}

		@Override
		public Serializable call() throws ProcessException {
			try {
				sendUserNotifications(
					_companyId, _portletKey, _notificationEventJSONObject,
					_subscribersOVPs);
			}
			catch (Exception e) {
				throw new ProcessException(e);
			}

			return null;
		}

		protected void sendUserNotifications(
				long companyId, String portletKey,
				JSONObject notificationEventJSONObject,
				List<ObjectValuePair<String, Long>> subscribersOVPs)
			throws PortalException, SystemException {

			System.out.println("***** Notification: "+notificationEventJSONObject);
			
			int notificationType = notificationEventJSONObject.getInt(
				"notificationType");

			long userId = notificationEventJSONObject.getLong("userId");

			Set<Long> subscriberUserIds = new HashSet<Long>();

			for (ObjectValuePair<String, Long> ovp : subscribersOVPs) {
				String className = ovp.getKey();
				long classPK = ovp.getValue();

				List<Subscription> subscriptions =
					SubscriptionLocalServiceUtil.getSubscriptions(
						companyId, className, classPK);

				for (Subscription subscription : subscriptions) {
					long subscriberUserId = subscription.getUserId();

					if (subscriberUserId == userId) {
						continue;
					}

					if (subscriberUserIds.contains(subscriberUserId)) {
						continue;
					}

					subscriberUserIds.add(subscriberUserId);

					if (UserNotificationManagerUtil.isDeliver(
							subscriberUserId, portletKey, 0, notificationType,
							UserNotificationDeliveryConstants.TYPE_WEBSITE)) {

						NotificationEvent notificationEvent =
							NotificationEventFactoryUtil.
								createNotificationEvent(
									System.currentTimeMillis(), portletKey,
									notificationEventJSONObject);

						com.liferay.portal.service.
							UserNotificationEventLocalServiceUtil.
								addUserNotificationEvent(
									subscriberUserId, notificationEvent);
					}
					
					// BEGIN CHANGE
					// email routine added
					if (UserNotificationManagerUtil.isDeliver(
							subscriberUserId, portletKey, 0, notificationType,
							UserNotificationDeliveryConstants.TYPE_EMAIL)) {
						
						try {
							sendMail(notificationEventJSONObject, userId,
									subscriberUserId, companyId);
						}
						catch (Exception e) {
							throw new SystemException(e);
						}
					}
					// END CHANGE
					
				}
			}
		}
		
		private void sendMail(JSONObject notificationEventJSONObject, 
			long userId, long subscriberUserId, long companyId) throws Exception{
			
			System.out.println("***** NOTIFICATION MAIL SEND *****");
			/**
			 * "className":"com.liferay.portlet.blogs.model.BlogsEntry"
			 * "classPK":37020 
			 * "notificationType":0 - add
			 * "notificationType":1 - update
			 * 
			 * "className":"com.liferay.portlet.messageboards.model.MBMessage"
			 * "classPK":38312 - variiert je nach eintrag
			 * "notificationType":0 - add
			 * "notificationType":1 - update
			 * 
			 * "className":"com.liferay.portlet.wiki.model.WikiPage"
			 * "classPK":38332
			 * "notificationType":0 - add
			 * "notificationType":1 - update
			 */
			
			User sender = UserLocalServiceUtil.getUser(userId);
			
			User recipient = UserLocalServiceUtil.getUser(subscriberUserId);
			
			Company company = CompanyLocalServiceUtil.getCompany(companyId);
			
			String notificationType = "added";
			if (notificationEventJSONObject.getInt("notificationType") == 1)			
				notificationType = "updated";
		
			String className = notificationEventJSONObject.getString("className");
			
			String subject = "";
			String body = "";
			
			// create email for blogentries
			if (className.equals("com.liferay.portlet.blogs.model.BlogsEntry")) {
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/blog/email_entry_"+notificationType+"_subject.tmpl"));
				
				subject = StringUtil.replace(
						subject, new String[] {"[$BLOGS_ENTRY_USER_NAME$]"},
						new String[] {sender.getFullName()});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/blog/email_entry_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, 
						new String[] {
							"[$TO_NAME$]", "[$BLOGS_ENTRY_USER_NAME$]", 
							"[$BLOGS_ENTRY_URL$]", 
							"[$FROM_NAME$]", "[$FROM_ADDRESS$]"
						},
						new String[] {
							recipient.getFullName(), sender.getFullName(),
							notificationEventJSONObject.getString("entryURL"), 
							company.getName(), company.getEmailAddress()
						});
				
				if (notificationEventJSONObject.getInt("notificationType") == 1){
					body = StringUtil.replace(body, "[$BLOGS_ENTRY_STATUS_BY_USER_NAME$]", 
							sender.getFullName());
				}
			}
			
			// create email for wikipages
			if (className.equals("com.liferay.portlet.wiki.model.WikiPage")) {
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/wiki/email_page_"+notificationType+"_subject.tmpl"));
				
				// get Page and Node for detailed information
				WikiPage wikiPage = WikiPageLocalServiceUtil.getPageByPageId(notificationEventJSONObject.getLong("classPK"));
				WikiNode wikiNode = wikiPage.getNode();
				
				subject = StringUtil.replace(
						subject, new String[] {
								"[$NODE_NAME$]", "[$PAGE_TITLE$]"
							}, new String[] {
								wikiNode.getName(), notificationEventJSONObject.getString("entryTitle")
							});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/wiki/email_page_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, new String[] {
								"[$PAGE_USER_NAME$]", "[$PAGE_DATE_UPDATE$]",
								"[$PAGE_TITLE$]", "[$PAGE_CONTENT$]"
							}, new String[] {
								wikiPage.getUserName(), wikiPage.getCreateDate().toString(),
								wikiPage.getTitle(), wikiPage.getContent()
							});
				
				String wikiSignature = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/wiki/email_page_"+notificationType+"_signature.tmpl"));
				
				wikiSignature = StringUtil.replace(
						wikiSignature, new String[] {
								"[$PAGE_URL$]", "[$PAGE_TITLE$]",
								"[$COMPANY_NAME$]", "[$PORTAL_URL$]"
							}, new String[] {
								notificationEventJSONObject.getString("entryURL"), wikiPage.getTitle(),
								company.getName(), company.getPortalURL(sender.getGroupId())
							});
				
				body += "\n<br></br>"+wikiSignature;
			}
			
			// create email for messageboards
			if (className.equals("com.liferay.portlet.messageboards.model.MBMessage")) {
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/mb/email_message_"+notificationType+"_subject.tmpl"));
				
				// get message by PK to get category
				MBMessage mbMessage = MBMessageLocalServiceUtil.getMBMessage(
						notificationEventJSONObject.getLong("classPK"));
				MBCategory mbCategory = mbMessage.getCategory();
				
				subject = StringUtil.replace(
						subject, new String[] {
								"[$CATEGORY_NAME$]", "[$MESSAGE_SUBJECT$]"
								}, new String[] {
								mbCategory.getName(), notificationEventJSONObject.getString("entryTitle")
								});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/mb/email_message_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, new String[] {
									"[$MESSAGE_BODY$]"
								}, new String[] {
									mbMessage.getBody()
								});
				
				String signature = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/mb/email_message_"+notificationType+"_signature.tmpl"));
				
				signature = StringUtil.replace(
						signature, new String[] {
									"[$COMPANY_NAME$]", "[$MESSAGE_URL$]", 
									"[$MAILING_LIST_ADDRESS$]", "[$PORTAL_URL$]"
								}, new String[] {
									company.getName(), notificationEventJSONObject.getString("entryURL"),
									"",company.getPortalURL(sender.getGroupId()) 
								});
				
				body += "\n<br></br>"+signature;
			}
			
			// create mail for bookmarks
			if (className.equals("com.liferay.portlet.bookmarks.model.BookmarksFolder")) {
				// {"entryURL":"http://localhost:8080/c/bookmarks/find_entry?p_l_id=39183&noSuchEntryRedirect=null&entryId=39258","notificationType":0,"userId":29556,"entryTitle":"Google","classPK":0,"className":"com.liferay.portlet.bookmarks.model.BookmarksFolder"}
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/bookmarks/email_entry_"+notificationType+"_subject.tmpl"));
				
				subject = StringUtil.replace(
						subject, new String[] {"[$BOOKMARKS_ENTRY_USER_NAME$]"},
						new String[] {sender.getFullName()});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/bookmarks/email_entry_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, 
						new String[] {
							"[$TO_NAME$]", "[$BOOKMARKS_ENTRY_USER_NAME$]", 
							"[$BOOKMARKS_ENTRY_URL$]", 
							"[$FROM_NAME$]", "[$FROM_ADDRESS$]"
						},
						new String[] {
							recipient.getFullName(), sender.getFullName(),
							notificationEventJSONObject.getString("entryURL"), 
							company.getName(), company.getEmailAddress()
						});
				
				if (notificationEventJSONObject.getInt("notificationType") == 1){
					subject = StringUtil.replace(
							subject, new String[] {"[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]"},
							new String[] {sender.getFullName()});
					body = StringUtil.replace(body, "[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]", 
							sender.getFullName());
				}
			}
			
			// create mail for document-library
			if (className.equals("com.liferay.portlet.documentlibrary.model.DLFileEntry")) {
				// {"entryURL":"http://localhost:8080/c/document_library/find_file_entry?p_l_id=27269&noSuchEntryRedirect=null&fileEntryId=39325","notificationType":0,"userId":20199,"entryTitle":"Liferay","classPK":39325,"className":"com.liferay.portlet.documentlibrary.model.DLFileEntry"}
				
				DLFileEntry dlFileEntry = DLFileEntryLocalServiceUtil.getDLFileEntry(notificationEventJSONObject.getLong("classPK"));
//				DLFileEntryModel dlFileEntryModel = null;
				DLFileEntryType dlFileEntryType = DLFileEntryTypeLocalServiceUtil.getFileEntryType(dlFileEntry.getFileEntryTypeId());
				
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/documentslibrary/email_file_entry_"+notificationType+"_subject.tmpl"));
				
				subject = StringUtil.replace(
						subject, 
						new String[] {
							"[$DOCUMENT_TYPE$]", 
							"[$DOCUMENT_TITLE$]"
						},
						new String[] {
							dlFileEntryType.getName(), 
							dlFileEntry.getTitle()
						});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/documentslibrary/email_file_entry_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, 
						new String[] {
							"[$TO_NAME$]", "[$PORTLET_NAME$]", 
							"[$DOCUMENT_TYPE$]", "[$DOCUMENT_TITLE$]",
							"[$FROM_NAME$]","[$FROM_ADDRESS$]"
						},
						new String[] {
							recipient.getFullName(), "document library",
							dlFileEntryType.getName(), dlFileEntry.getTitle(),
							company.getName(), company.getEmailAddress()
						});
			}
			
			// create mail for journal
			if (className.equals("com.liferay.portlet.journal.model.JournalArticle")) {
				// {"entryURL":"http://localhost:8080/group/der-geheime-workspace/2","notificationType":1,"userId":20199,"entryTitle":"Gruppenbeschreibung","classPK":41708,"className":"com.liferay.portlet.journal.model.JournalArticle"}
				
				JournalArticle journalArticle =JournalArticleLocalServiceUtil.getArticle(notificationEventJSONObject.getLong("classPK"));
				
				subject = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/journal/email_article_"+notificationType+"_subject.tmpl"));
				
				subject = StringUtil.replace(
						subject, 
						new String[] {
							"[$PORTLET_NAME$]", 
							"[$ARTICLE_ID$]", "[$ARTICLE_VERSION$]"
						},
						new String[] {
							"Text-Image-Editor", 
							journalArticle.getId()+"", journalArticle.getVersion()+""
						});
				
				body = StringUtil.read(
						NotificationsPortlet.class.getResourceAsStream(
						"dependencies/journal/email_article_"+notificationType+"_body.tmpl"));
				
				body = StringUtil.replace(
						body, 
						new String[] {
							"[$TO_NAME$]", "[$PORTLET_NAME$]", 
							"[$ARTICLE_ID$]", "[$ARTICLE_VERSIONE$]", "[$ARTICLE_TITLE$]",
							"[$FROM_NAME$]","[$FROM_ADDRESS$]"
						},
						new String[] {
							recipient.getFullName(), "Text-Image-Editor",
							journalArticle.getId()+"", journalArticle.getVersion()+"", journalArticle.getTitle(),
							company.getName(), company.getEmailAddress()
						});
			}
			
			InternetAddress from = new InternetAddress(company.getEmailAddress());
			
			InternetAddress to = new InternetAddress(
					recipient.getEmailAddress());
			
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);

			MailServiceUtil.sendEmail(mailMessage);
		}

		private static final long serialVersionUID = 1L;

		private long _companyId;
		private JSONObject _notificationEventJSONObject;
		private String _portletKey;
		private List<ObjectValuePair<String, Long>> _subscribersOVPs;

	}

}