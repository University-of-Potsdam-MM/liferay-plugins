package com.liferay.notifications.util;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.mail.service.MailServiceUtil;
import com.liferay.notifications.notifications.portlet.NotificationsPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.service.BookmarksEntryLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * This class contains methods to create of emails for blog, wiki, bookmarks, resources and journals.
 * @author Patrick
 * 
 */
public class EmailHelper {
	
	private static String getConfigURL (ServiceContext serviceContext, User user) 
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
	
	
	public static void prepareEmail(long groupId, long companyId,
			String portletKey, String entryTitle, String entryURL,
			int notificationType,
			List<ObjectValuePair<String, Long>> subscribersOVPs, 
			long userId, ServiceContext serviceContext, long entryId)
			throws PortalException, SystemException {
		
		Set<Long> subscriberUserIds = new HashSet<Long>();

		for (ObjectValuePair<String, Long> ovp : subscribersOVPs) {
			String className = ovp.getKey();
			long classPK = ovp.getValue();

			List<Subscription> subscriptions = SubscriptionLocalServiceUtil
					.getSubscriptions(companyId, className, classPK);

			for (Subscription subscription : subscriptions) {
				long subscriberUserId = subscription.getUserId();

				if (subscriberUserId == userId) {
					continue;
				}

				if (subscriberUserIds.contains(subscriberUserId)) {
					continue;
				}

				subscriberUserIds.add(subscriberUserId);

				if (UserNotificationManagerUtil.isDeliver(subscriberUserId,
						portletKey, 0, notificationType,
						UserNotificationDeliveryConstants.TYPE_EMAIL)) {

					try {
						sendMail(userId, subscriberUserId, companyId, portletKey, groupId,
								notificationType, entryTitle, entryURL,
								serviceContext, entryId);
					} catch (Exception e) {
						throw new SystemException(e);
					}
				}

			}
		}

	}
	
	public static void sendMail (long userId, long subscriberUserId, long companyId, String portletKey,
			long groupId, int notificationTypeInt, String entryTitle, String entryURL,
			ServiceContext serviceContext, long entryId) 
			throws Exception {
		
		User sender = UserLocalServiceUtil.getUser(userId);
		
		User recipient = UserLocalServiceUtil.getUser(subscriberUserId);
		
		Company company = CompanyLocalServiceUtil.getCompany(companyId);
		
		Group workspace = GroupLocalServiceUtil.getGroup(groupId);
		
		Portlet portlet = PortletLocalServiceUtil.getPortletById(serviceContext.getPortletId());
		
		String language = "";
		// check language, default: English
		if (recipient.getLocale().equals(Locale.GERMANY)) {
			language = "_"+Locale.GERMANY.toString();
		} 
		
		String notificationType = "added";
		if (notificationTypeInt == 1)			
			notificationType = "updated";
		
		String subject = "", 
			body = "";
		
		String[] temp;
		

		if (portletKey.equals(PortletKeys.BLOGS)) {
			temp = createBlogMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet, entryId);
			subject = temp[0];
			body = temp[1];
		}

		if (portletKey.equals(PortletKeys.WIKI)) {
			temp = createWikiMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet, entryId);
			subject = temp[0];
			body = temp[1];
		}

		if (portletKey.equals(PortletKeys.MESSAGE_BOARDS)) {
			temp = createMessageBoardsMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet, entryId);
			subject = temp[0];
			body = temp[1];
		}
		// TODO BOOKMARKS method is not called, cause sendmail is not triggered
		if (portletKey.equals(PortletKeys.BOOKMARKS)) {
			System.out.println("BOOKMARKS");
			temp = createBookmarksMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet, entryId);
			subject = temp[0];
			body = temp[1];
		}
		// TODO create journal this method is not called, cause no one can subscribe to journals
		if (portletKey.equals(PortletKeys.JOURNAL)) {
			temp = createJournalMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet, entryId);
			subject = temp[0];
			body = temp[1];
		}

		if(portletKey.equals(PortletKeys.DOCUMENT_LIBRARY)){
			temp = createRessourceMail(sender, recipient, company, notificationType, language, entryTitle, entryURL, workspace, serviceContext, portlet);
			subject = temp[0];
			body = temp[1];
		}
		
		String fromName = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME);
		String fromAddress = PrefsPropsUtil.getString(
			companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
	
		InternetAddress from = new InternetAddress(fromAddress, fromName);
		
		InternetAddress to = new InternetAddress(
				recipient.getEmailAddress());
		
		MailMessage mailMessage = new MailMessage(from, to, subject, body, true);

		MailServiceUtil.sendEmail(mailMessage);
		
	}
		
	protected static String[] createBlogMail (User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet, long entryId) 
			throws Exception {
	
		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.fetchBlogsEntry(entryId);
		
		String blogContent = "n/a";
		if (blogsEntry != null) {
			blogContent = blogsEntry.getContent();
		}
		
		// check if workspace realy is workspace
		String blogname = "";
		if (workspace.getParentGroupId() != 0) {
			System.out.println("workspace != 0");
			Group blog = workspace; 
			
			workspace = GroupLocalServiceUtil.getGroup(blog.getParentGroupId());
			blogname = blog.getDescriptiveName(recipient.getLocale());
		}
		else {
			Group blog = null;
			blogname = "n/a";
		}
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/blog/email_entry_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, new String[] {
						"[$WORKSPACE_NAME$]", "[$BLOGS_ENTRY_NAME$]"
						},
				new String[] {
						workspace.getDescriptiveName(recipient.getLocale()),// blogsEntry.getTitle(),
						entryTitle
						});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/blog/email_entry_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
				body, new String[] {
//						"[$WORKSPACE_NAME$]", "[$BLOGS_NAME$]",  // entfernt bis Breadcrump diskussion abgeschlossen
						"[$TO_NAME$]", "[$BLOGS_ENTRY_USER_NAME$]",
						"[$BLOGS_CONTENT$]", "[$BLOGS_ENTRY_URL$]",
						"[$CONFIG_URL$]"
						},
				new String[] {
//						workspace.getDescriptiveName(recipient.getLocale()), blogname, // entfernt bis Breadcrump diskussion abgeschlossen
						recipient.getFullName(), sender.getFullName(),
						blogContent, entryURL, 
						getConfigURL(serviceContext, recipient)
						});
		
		return new String[] {subject, body};
	}

	protected static String[] createWikiMail(User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet, long entryId) 
			throws Exception {
		
		WikiPage wikiPage = WikiPageLocalServiceUtil.fetchWikiPage(entryId);
		
		String wikiPageContent = "";
		if (wikiPage != null) {
			wikiPageContent = wikiPage.getContent();
		}
		
		String wikiName ="";
		if (workspace.getParentGroupId() != 0) {
			Group wiki = workspace;
			workspace = GroupLocalServiceUtil.getGroup(wiki.getParentGroupId());
			wikiName = wiki.getDescriptiveName();
		}
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/wiki/email_page_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, new String[] {
						"[$WORKSPACE_NAME$]", "[$PAGE_TITLE$]"
					}, new String[] {
						workspace.getDescriptiveName(recipient.getLocale()), entryTitle//notificationEventJSONObject.getString("entryTitle")
					});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/wiki/email_page_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
				body, new String[] {
//						"[$WORKSPACE_NAME$]", "[$WIKI_NAME$]", // entfernt bis Breadcrump diskussion abgeschlossen
						"[$TO_NAME$]", "[$PAGE_USER_NAME$]",
						"[$PAGE_CONTENT$]", "[$PAGE_URL$]",
						"[$CONFIG_URL$]"
					}, new String[] {
//						workspace.getDescriptiveName(recipient.getLocale()), wikiName, // entfernt bis Breadcrump diskussion abgeschlossen
						recipient.getFullName(), sender.getFullName(),
						wikiPageContent, entryURL,
						getConfigURL(serviceContext, recipient)
					});
		
		return new String[] {subject, body};
	}
		
	protected static String[] createMessageBoardsMail (User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet, long entryId) 
			throws Exception {
		
		MBMessage mbMessage = MBMessageLocalServiceUtil.fetchMBMessage(entryId);
		
		String messageBody = "";
		if (mbMessage != null) 
			messageBody = mbMessage.getBody();
		
		String messageBoardName = "";
		if (workspace.getParentGroupId() != 0) {
			Group messageBoard = workspace;
			workspace = GroupLocalServiceUtil.getGroup(messageBoard.getParentGroupId());
			messageBoardName = messageBoard.getDescriptiveName();
		}
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/mb/email_message_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, new String[] {
							"[$WORKSPACE_NAME$]", 
							"[$MESSAGE_SUBJECT$]"
						}, new String[] {
							workspace.getDescriptiveName(recipient.getLocale()), 
							entryTitle
						});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/mb/email_message_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
				body, new String[] {
//							"[$WORKSPACE_NAME$]", // entfernt bis Breadcrump diskussion abgeschlossen
//							"[$CATEGORY_NAME$]", // entfernt bis Breadcrump diskussion abgeschlossen 
							"[$TO_NAME$]", "[$USER_NAME$]",
							"[$MESSAGE_CONTENT$]", 
							"[$MESSAGE_URL$]", 
							"[$CONFIG_URL$]"
						}, new String[] {
//							workspace.getDescriptiveName(recipient.getLocale()), // entfernt bis Breadcrump diskussion abgeschlossen
//							messageBoardName, // entfernt bis Breadcrump diskussion abgeschlossen  
							recipient.getFullName(), sender.getFullName(),
							messageBody, 
							entryURL,
							getConfigURL(serviceContext, recipient)
						});

		
		return new String[] {subject, body};
	}
	
	protected static String[] createBookmarksMail (User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet, long entryId) 
			throws Exception {
		
		// {"entryURL":"http://localhost:8080/c/bookmarks/find_entry?p_l_id=39183&noSuchEntryRedirect=null&entryId=39258","notificationType":0,"userId":29556,"entryTitle":"Google","classPK":0,"className":"com.liferay.portlet.bookmarks.model.BookmarksFolder"}
		
		BookmarksEntry bookmarksEntry = BookmarksEntryLocalServiceUtil.fetchBookmarksEntry(entryId);
		
		if (bookmarksEntry != null) {
			
		}
		
		String bookmarksName = "";
		if (workspace.getParentGroupId() != 0) {
			Group bookmarks = workspace;
			workspace = GroupLocalServiceUtil.getGroup(bookmarks.getParentGroupId());
			bookmarksName = bookmarks.getDescriptiveName();
		}
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/bookmarks/email_entry_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, new String[] {"[$WORKSPACE_NAME$]","[$BOOKMARKS_NAME$]"},
				new String[] {workspace.getDescriptiveName(recipient.getLocale()), entryTitle/*bookmarksEntry.getName()*/});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/bookmarks/email_entry_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
				body, 
				new String[] {
//					"[$WORKSPACE_NAME$]", // entfernt bis Breadcrump diskussion abgeschlossen
//					"[$BOOKMARKS_NAME$]", // entfernt bis Breadcrump diskussion abgeschlossen
					"[$TO_NAME$]", "[$BOOKMARKS_ENTRY_USER_NAME$]", 
					"[$BOOKMARKS_ENTRY_CONTENT$]",
					"[$BOOKMARKS_ENTRY_URL$]", 
					"[$CONFIG_URL$]"
				},
				new String[] {
//					workspace.getDescriptiveName(recipient.getLocale()), // entfernt bis Breadcrump diskussion abgeschlossen
//					bookmarksName, 	// entfernt bis Breadcrump diskussion abgeschlossen
					recipient.getFullName(), sender.getFullName(),
					entryTitle,
					entryURL, 
					getConfigURL(serviceContext, recipient)
				});
		
		if (notificationType.equals("updated")){
			subject = StringUtil.replace(
					subject, new String[] {"[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]"},
					new String[] {sender.getFullName()});
			body = StringUtil.replace(body, "[$BOOKMARKS_ENTRY_STATUS_BY_USER_NAME$]", 
					sender.getFullName());
		}
		
		return new String[] {subject, body};
	}
	
	protected static String[] createRessourceMail (User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet) 
			throws Exception {
		
		String subject = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/documentslibrary/email_file_entry_"+notificationType+"_subject"+language+".tmpl"));
		
		subject = StringUtil.replace(
				subject, 
				new String[] {
					"[$WORKSPACE_NAME$]", 
					"[$DOCUMENT_TITLE$]"
				},
				new String[] {
					workspace.getDescriptiveName(), 
					entryTitle
				});
		
		String body = StringUtil.read(
				NotificationsPortlet.class.getResourceAsStream(
				"dependencies/documentslibrary/email_file_entry_"+notificationType+"_body"+language+".tmpl"));
		
		body = StringUtil.replace(
		body, 
		new String[] {
//			"[$WORKSPACE_NAME$]", "[$FOLDER_TITLE$]", // entfernt bis Breadcrump diskussion abgeschlossen
			"[$TO_NAME$]", "[$USER_NAME$]", 
			"[$CONTENT$]", "[$ENTRY_URL$]",
			"[$CONFIG_URL$]"
		},
		new String[] {
//			workspace.getDescriptiveName(), LanguageUtil.get(recipient.getLocale(), "javax.portlet.title."+portlet.getPortletName()), // entfernt bis Breadcrump diskussion abgeschlossen
			recipient.getFullName(), sender.getFullName(),
			entryTitle, entryURL,
			EmailHelper.getConfigURL(serviceContext, recipient)
		});
		
		return new String[] {subject, body};
	}
	
	protected static String[] createJournalMail (User sender, User recipient, Company company,
			String notificationType, String language, String entryTitle, String entryURL, 
			Group workspace, ServiceContext serviceContext, Portlet portlet, long entryId) 
			throws Exception {
		// TODO update journal Mail
		// {"entryURL":"http://localhost:8080/group/der-geheime-workspace/2","notificationType":1,"userId":20199,"entryTitle":"Gruppenbeschreibung","classPK":41708,"className":"com.liferay.portlet.journal.model.JournalArticle"}
		
		JournalArticle journalArticle = JournalArticleLocalServiceUtil.fetchJournalArticle(entryId);
		
		String subject = StringUtil.read(
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
		
		String body = StringUtil.read(
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
		
		return new String[] {subject, body};
	}
	
}
