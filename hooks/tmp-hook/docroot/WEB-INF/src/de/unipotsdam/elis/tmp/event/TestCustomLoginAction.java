package de.unipotsdam.elis.tmp.event;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;

public class TestCustomLoginAction extends Action {
	/*
	 * (non-Java-doc)
	 * 
	 * @see com.liferay.portal.kernel.events.SimpleAction#SimpleAction()
	 */
	public TestCustomLoginAction() {
		super();
	}

	PrintStream out = System.out;

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {

		System.out.println("TestCustomLoginAction Active");

		// if set the script will search for the site template by the id (and
		// set the name). Otherwise the script will search for the site template
		// by the corresponding name.
		long GROUP_WORK_SITE_TEMPLATE_ID = 0;
		// if set the script will search for the page template by the id (and
		// set the name). Otherwise the script will search for the page template
		// by the corresponding name.
		long BLOG_PAGE_TEMPLATE_ID = 20317;
		// if set the script will search for the page template by the id (and
		// set the name). Otherwise the script will search for the page template
		// by the corresponding name.
		long WIKI_PAGE_TEMPLATE_ID = 0;
		// if set the script will search for the page template by the id (and
		// set the name). Otherwise the script will search for the page template
		// by the corresponding name.
		long WEB_CONTENT_PAGE_TEMPLATE_ID = 0;
		// if set the script will search for the page template by the id (and
		// set the name). Otherwise the script will search for the page template
		// by the corresponding name.
		long PAGE_WITHOUT_APPLICATIONS_PAGE_TEMPLATE_ID = 0;

		String USER_HOME_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Home";
		String USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Profile";

		Map<Locale, String> GROUP_WORK_SITE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_SITE_TEMPLATE_NAME_MAP.put(Locale.US, "Group Work");
		GROUP_WORK_SITE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Gruppenarbeit");
		String GROUP_WORK_SITE_TEMPLATE_DESCRIPTION = "Diese Vorlage stattet den Workspace mit nützlichen Anwendungen für die Zusammenarbeit aus. Standardmäßig ist eine Dokumentenverwaltung enthalten sowie verschiedene Web2.0-Werkzeuge (Blog, Wiki, Forum). Es können aber auch weitere Seiten und Anwendungen hinzugefügt werden.";

		Map<Locale, String> BLOG_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		BLOG_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Blog");
		BLOG_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit einem Blog");
		String BLOG_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Blog, der z.B. als Lerntagebuch oder für Berichte genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		Map<Locale, String> WIKI_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		WIKI_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Wiki");
		WIKI_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit einem Wiki");
		String WIKI_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet ein Wiki, das z.B. als Glossar oder für Dokumentationen genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		Map<Locale, String> WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Text-Image Editor");
		WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit Text-Bild-Editor");
		String WEB_CONTENT_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Editor, der z.B. für Texte mit Grafiken und Weblinks genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		Map<Locale, String> PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP.put(Locale.US, "Page without Applications");
		PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Frei gestaltbare Seite");
		String PAGE_WITHOUT_APPLICATIONS_TEMPLATE_DESCRIPTION = "Diese Seite ist leer und kann nach dem Erstellen beliebig mit Anwendungen und Inhalten bestückt werden.";

		Map<Locale, String> EDITOR_ROLE_TITLE_MAP = new HashMap<Locale, String>();
		EDITOR_ROLE_TITLE_MAP.put(Locale.US, "Editor");
		EDITOR_ROLE_TITLE_MAP.put(Locale.GERMANY, "Redakteur");
		Map<Locale, String> EDITOR_ROLE_DESCRIPTION_MAP = new HashMap<Locale, String>();
		EDITOR_ROLE_DESCRIPTION_MAP.put(Locale.ENGLISH, "Editor");
		EDITOR_ROLE_DESCRIPTION_MAP.put(Locale.GERMAN, "Redakteur");
		Map<String, String[]> EDITOR_ROLE_PERMISSIONS = new HashMap<String, String[]>();
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.GROUP_PAGES, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// Resource Permissions
		EDITOR_ROLE_PERMISSIONS.put(Group.class.getName(), new String[] { ActionKeys.ADD_COMMUNITY,
				ActionKeys.ADD_LAYOUT, ActionKeys.ADD_LAYOUT_BRANCH, ActionKeys.ADD_LAYOUT_SET_BRANCH,
				ActionKeys.CONFIGURE_PORTLETS, ActionKeys.EXPORT_IMPORT_LAYOUTS,
				ActionKeys.EXPORT_IMPORT_PORTLET_INFO, ActionKeys.MANAGE_ANNOUNCEMENTS,
				ActionKeys.MANAGE_ARCHIVED_SETUPS, ActionKeys.MANAGE_LAYOUTS, ActionKeys.MANAGE_STAGING,
				ActionKeys.PERMISSIONS, ActionKeys.PREVIEW_IN_DEVICE, ActionKeys.PUBLISH_STAGING,
				ActionKeys.PUBLISH_TO_REMOTE, ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS,
				ActionKeys.VIEW_SITE_ADMINISTRATION, ActionKeys.VIEW_STAGING });
		// Workspace Administration -> Pages -> Sites of the Workspaces -> Page
		EDITOR_ROLE_PERMISSIONS.put(Layout.class.getName(), new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.ADD_LAYOUT, ActionKeys.CONFIGURE_PORTLETS, ActionKeys.CUSTOMIZE, ActionKeys.DELETE,
				ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
				ActionKeys.VIEW });
		// Workspace Administration -> Content -> Recent Content-> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.RECENT_CONTENT, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.journal", new String[] { ActionKeys.ADD_ARTICLE,
				ActionKeys.ADD_FEED, ActionKeys.ADD_FOLDER, ActionKeys.ADD_STRUCTURE, ActionKeys.ADD_TEMPLATE,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Folder
		EDITOR_ROLE_PERMISSIONS.put(JournalFolder.class.getName(), new String[] { ActionKeys.ACCESS,
				ActionKeys.ADD_ARTICLE, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Article
		EDITOR_ROLE_PERMISSIONS.put(JournalArticle.class.getName(), new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.EXPIRE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Feed
		EDITOR_ROLE_PERMISSIONS.put(JournalFeed.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Structure
		EDITOR_ROLE_PERMISSIONS.put(JournalStructure.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Template
		EDITOR_ROLE_PERMISSIONS.put(JournalTemplate.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.documentlibrary", new String[] { ActionKeys.ADD_DOCUMENT,
				ActionKeys.ADD_DOCUMENT_TYPE, ActionKeys.ADD_FOLDER, ActionKeys.ADD_REPOSITORY,
				ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_STRUCTURE, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents Folder
		EDITOR_ROLE_PERMISSIONS.put(DLFolder.class.getName(), new String[] { ActionKeys.ACCESS,
				ActionKeys.ADD_DOCUMENT, ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document
		EDITOR_ROLE_PERMISSIONS.put(DLFileEntry.class.getName(), new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.OVERRIDE_CHECKOUT, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Shortcut
		EDITOR_ROLE_PERMISSIONS.put(DLFileShortcut.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document Type
		EDITOR_ROLE_PERMISSIONS.put(DLFileEntryType.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Blogs -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BLOGS_ADMIN, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.blogs", new String[] { ActionKeys.ADD_ENTRY,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE });
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entry
		EDITOR_ROLE_PERMISSIONS.put(BlogsEntry.class.getName(), new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Message Boards -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS_ADMIN, new String[] {
				ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
				ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.messageboards", new String[] { ActionKeys.ADD_CATEGORY,
				ActionKeys.ADD_FILE, ActionKeys.ADD_MESSAGE, ActionKeys.BAN_USER, ActionKeys.LOCK_THREAD,
				ActionKeys.MOVE_THREAD, ActionKeys.PERMISSIONS, ActionKeys.REPLY_TO_MESSAGE, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE_THREAD_PRIORITY, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Category
		EDITOR_ROLE_PERMISSIONS.put(MBCategory.class.getName(), new String[] {});
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Thread
		EDITOR_ROLE_PERMISSIONS.put(MBThread.class.getName(), new String[] { ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Message
		EDITOR_ROLE_PERMISSIONS.put(MBMessage.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Wiki -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI_ADMIN, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Nodes
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.wiki", new String[] { ActionKeys.ADD_NODE,
				ActionKeys.PERMISSIONS });
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Node
		EDITOR_ROLE_PERMISSIONS.put(WikiNode.class.getName(), new String[] { ActionKeys.ADD_ATTACHMENT,
				ActionKeys.ADD_PAGE, ActionKeys.DELETE, ActionKeys.IMPORT, ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Page
		EDITOR_ROLE_PERMISSIONS.put(WikiPage.class.getName(), new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Bookmarks -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BOOKMARKS, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Content -> Bookmarks -> Resource
		// Permissions -> Bookmark Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.bookmarks", new String[] { ActionKeys.ADD_ENTRY,
				ActionKeys.ADD_FOLDER, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Polls -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.POLLS, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Questions
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.polls", new String[] { ActionKeys.ADD_QUESTION,
				ActionKeys.PERMISSIONS });
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Question
		EDITOR_ROLE_PERMISSIONS.put(PollsQuestion.class.getName(), new String[] { ActionKeys.ADD_VOTE,
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Tags -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.TAGS_ADMIN, new String[] { ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Asset Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.asset", new String[] { ActionKeys.ADD_CATEGORY,
				ActionKeys.ADD_TAG, ActionKeys.ADD_VOCABULARY, ActionKeys.PERMISSIONS });
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Tag
		EDITOR_ROLE_PERMISSIONS.put(AssetTag.class.getName(), new String[] { ActionKeys.DELETE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Categories -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_ADMIN, new String[] {
				ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
				ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category Vocabulary
		EDITOR_ROLE_PERMISSIONS.put(AssetVocabulary.class.getName(), new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category
		EDITOR_ROLE_PERMISSIONS.put(AssetCategory.class.getName(), new String[] { ActionKeys.ADD_CATEGORY,
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Users -> Site Memberships -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.SITE_MEMBERSHIPS_ADMIN, new String[] {});
		// Workspace Administration -> Users -> Site Memberships -> Resource
		// Permissions -> Team
		EDITOR_ROLE_PERMISSIONS.put(Team.class.getName(), new String[] {});
		// Workspace Administration -> Users -> Site Teams -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("191", new String[] {});
		// Workspace Administration -> Applications -> Activities -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("upactivities_WAR_upactivitiesportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ANNOUNCEMENTS, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.ADD_ENTRY, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.announcements.model.AnnouncementsEntry", new String[] {
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Asset Publisher ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_PUBLISHER, new String[] {
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.SUBSCRIBE, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Blogs -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BLOGS, new String[] { ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Categories Navigation ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_NAVIGATION, new String[] {
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Comments and Feedback ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.PAGE_COMMENTS, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Documents and Media
		// Display -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY_DISPLAY, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Events Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_eventsdisplayportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Media Gallery ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MEDIA_GALLERY_DISPLAY, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Message Boards ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Microblogs -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_microblogsportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.microblogs.model.MicroblogsEntry", new String[] { ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs Entry
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.microblogs", new String[] { ActionKeys.ADD_ENTRY,
				ActionKeys.PERMISSIONS });
		// Workspace Administration -> Applications -> Microblogs Status Update
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("2_WAR_microblogsportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Polls Display
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.POLLS_DISPLAY, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Quick Notest
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("97", new String[] { ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> RSS -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.RSS, new String[] { ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Social Office
		// Announcements -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_soannouncementsportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Tag Cloud -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.TAGS_CLOUD, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Tags Navigation ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_TAGS_NAVIGATION, new String[] {
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Tasks -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_tasksportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.tasks", new String[] { ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS });
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks Entry
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.tasks.model.TasksEntry", new String[] { ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
				ActionKeys.VIEW });
		// Workspace Administration -> Applications -> WYSIWYG -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_wysiwygportlet", new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Web Content Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Web Content List ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_LIST, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Web Content Search ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_SEARCH, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Wiki -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI, new String[] { ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Wiki Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI_DISPLAY, new String[] { ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW });
		// Workspace Administration -> Applications -> Workspace Description ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("workspacedescription_WAR_workspacedescriptionportlet", new String[] {
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW });

		// names of the roles permissions will be removed from
		String[] roleNamesForPermissionDeletion = new String[] { RoleConstants.USER, RoleConstants.POWER_USER,
				RoleConstants.GUEST };
		// name ids for permission deletion
		String[] namesForPermissionDeletion = new String[] { PortletKeys.REQUESTS, PortletKeys.ANNOUNCEMENTS,
				PortletKeys.ASSET_PUBLISHER, "4_WAR_contactsportlet", "3_WAR_contactsportlet", "2_WAR_contactsportlet",
				PortletKeys.LOGIN, PortletKeys.IFRAME, PortletKeys.ASSET_TAGS_NAVIGATION, "143", PortletKeys.ALERTS,
				"2_WAR_tasksportlet", "mycustompages_WAR_custompagesportlet",
				"othercustompages_WAR_custompagesportlet", PortletKeys.ACTIVITIES,
				"upactivities_WAR_upactivitiesportlet", "1_WAR_notificationsportlet", "1_WAR_eventsdisplayportlet",
				"2_WAR_microblogsportlet", PortletKeys.RSS, "97", PortletKeys.MEDIA_GALLERY_DISPLAY,
				"upiframe_WAR_upiframeportlet", "1_WAR_wysiwygportlet",
				"workspacedescription_WAR_workspacedescriptionportlet", "1_WAR_privatemessagingportlet",
				PortletKeys.SITE_MEMBERS_DIRECTORY };
		// action ids for permission deletion
		String[] actionIdsForPermissionDeletion = new String[] { ActionKeys.ADD_TO_PAGE };

		// names of the roles permissions will be added to
		String[] roleNamesForPermissionAdditionCompany = new String[] { "User" };
		// name ids for permission addition
		String[] namesForPermissionAdditionCompany = new String[] { "com.liferay.portlet.documentlibrary" };
		// action ids for permission addition
		String[] actionIdsForPermissionAdditionCompany = new String[] { ActionKeys.VIEW };

		// names of the roles permissions will be added to
		String[] roleNamesForPermissionAdditionPersonal = new String[] { "User" };
		// name ids for permission addition
		String[] namesForPermissionAdditionPersonal = new String[] { "mycustompages_WAR_custompagesportlet",
				"othercustompages_WAR_custompagesportlet" };
		// action ids for permission addition
		String[] actionIdsForPermissionAdditionPersonal = new String[] { ActionKeys.ADD_TO_PAGE };

		try {

			// Make predefined pages in users personal area uneditable
			setLayoutSetPrototypeUneditable(GROUP_WORK_SITE_TEMPLATE_ID, USER_HOME_LAYOUT_SET_PROTOTYPE_NAME);

			// Set description of the group work workspace
			setLayoutSetPrototypeProperties(GROUP_WORK_SITE_TEMPLATE_ID, GROUP_WORK_SITE_TEMPLATE_NAME_MAP,
					GROUP_WORK_SITE_TEMPLATE_DESCRIPTION);

			// Make predefined pages in users profile uneditable
			setLayoutSetPrototypeUneditable(GROUP_WORK_SITE_TEMPLATE_ID, USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME);

			// Set description of the blog page layout prototype
			setLayoutPrototypeProperties(BLOG_PAGE_TEMPLATE_ID, BLOG_PAGE_TEMPLATE_NAME_MAP,
					BLOG_PAGE_TEMPLATE_DESCRIPTION);

			// Set description of the wiki page layout prototype
			setLayoutPrototypeProperties(WIKI_PAGE_TEMPLATE_ID, WIKI_PAGE_TEMPLATE_NAME_MAP,
					WIKI_PAGE_TEMPLATE_DESCRIPTION);

			// Set description of the web content page layout prototype
			setLayoutPrototypeProperties(WEB_CONTENT_PAGE_TEMPLATE_ID, WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP,
					WEB_CONTENT_PAGE_TEMPLATE_DESCRIPTION);

			// Set description of the page without applications layout prototype
			setLayoutPrototypeProperties(PAGE_WITHOUT_APPLICATIONS_PAGE_TEMPLATE_ID,
					PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP, PAGE_WITHOUT_APPLICATIONS_TEMPLATE_DESCRIPTION);

			// Create or update editor role and set permissions
			Role role = createOrUpdateRole(EDITOR_ROLE_TITLE_MAP, EDITOR_ROLE_DESCRIPTION_MAP, RoleConstants.TYPE_SITE);
			setRolePermissions(role, EDITOR_ROLE_PERMISSIONS, ResourceConstants.SCOPE_GROUP_TEMPLATE);

			// Removes given permissions
			removePermissions(roleNamesForPermissionDeletion, namesForPermissionDeletion,
					actionIdsForPermissionDeletion);

			// Adds given permissions
			addPermissions(roleNamesForPermissionAdditionCompany, namesForPermissionAdditionCompany,
					actionIdsForPermissionAdditionCompany, ResourceConstants.SCOPE_COMPANY,
					String.valueOf(PortalUtil.getDefaultCompanyId()));

			// Adds given permissions
			addPermissions(roleNamesForPermissionAdditionPersonal, namesForPermissionAdditionPersonal,
					actionIdsForPermissionAdditionPersonal, ResourceConstants.SCOPE_GROUP,
					String.valueOf(GroupLocalServiceUtil.getUserPersonalSiteGroup(PortalUtil.getDefaultCompanyId())
							.getGroupId()));

		} catch (Exception e) {
			e.printStackTrace(out);
		}
	}

	/**
	 * Makes the pages of LayoutSets inheriting from the given
	 * LayoutSetPrototype uneditable by the user. NOTE: layoutUpdateable needs
	 * to be set to true. Otherwise pages will loose the permission CUSTOMIZE
	 * (important for the custom-pages-portlet).
	 * 
	 * @param layoutSetPrototypeName
	 *            Name of the LayoutSetPrototype
	 * @throws Exception
	 */
	void setLayoutSetPrototypeUneditable(long layoutSetPrototypeId, String layoutSetPrototypeName) throws Exception {
		out.println("Try to make layouts of the LayoutSetPrototype " + layoutSetPrototypeName + " uneditable");

		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototypeByLayoutSetPrototypeIdOrName(layoutSetPrototypeId,
				layoutSetPrototypeName);

		if (layoutSetPrototype != null) {
			UnicodeProperties properties = layoutSetPrototype.getSettingsProperties();
			properties.setProperty("layoutsUpdateable", "true");
			layoutSetPrototype.setSettingsProperties(properties);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set property layoutsUpdateable of the LayoutSetPrototype with the id " + layoutSetPrototypeId
					+ " to true");

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(layoutSetPrototype.getGroupId(), true);
			for (Layout layout : layouts) {
				layout.getLayoutType().setTypeSettingsProperty("layoutUpdateable", "false");
				LayoutLocalServiceUtil.updateLayout(layout);
				out.println("Set property layoutUpdateable of the layout " + layout.getName(Locale.US) + " to false");
			}
		}

		out.println();
	}

	/**
	 * Sets the description of a LayoutSetPrototype
	 * 
	 * @param layoutSetPrototypeName
	 *            Name of the LayoutSetProtoype
	 * @param layoutSetPrototypeDescription
	 *            Description of the LayoutSetPrototype
	 * @throws Exception
	 */
	void setLayoutSetPrototypeProperties(long layoutSetPrototypeId, Map<Locale, String> layoutSetPrototypeNameMap,
			String layoutSetPrototypeDescription) throws Exception {
		out.println("Try to set properties of the LayoutSetPrototype with the id " + layoutSetPrototypeId
				+ " or with the name " + layoutSetPrototypeNameMap.get(Locale.US));
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototypeByLayoutSetPrototypeIdOrName(layoutSetPrototypeId,
				layoutSetPrototypeNameMap.get(Locale.US));

		if (layoutSetPrototype != null) {
			layoutSetPrototype.setNameMap(layoutSetPrototypeNameMap);
			layoutSetPrototype.setDescription(layoutSetPrototypeDescription);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set properties of the LayoutSetPrototype with the id "
					+ layoutSetPrototype.getLayoutSetPrototypeId());

		}
		out.println();
	}

	/**
	 * Sets the description of a LayoutPrototype
	 * 
	 * @param layoutPrototypeName
	 *            Name of the LayoutProtoype
	 * @param layoutPrototypeDescription
	 *            Description of the LayoutPrototype
	 * @throws Exception
	 */
	void setLayoutPrototypeProperties(long layoutPrototypeId, Map<Locale, String> layoutPrototypeNameMap,
			String layoutPrototypeDescription) throws Exception {
		out.println("Try to set properties of the LayoutPrototype with the id " + layoutPrototypeId
				+ " or with the name " + layoutPrototypeNameMap.get(Locale.US));
		LayoutPrototype layoutPrototype = getLayoutPrototypeByLayoutPrototypeIdOrName(layoutPrototypeId,
				layoutPrototypeNameMap.get(Locale.US));

		if (layoutPrototype != null) {
			layoutPrototype.setNameMap(layoutPrototypeNameMap);
			layoutPrototype.setDescription(layoutPrototypeDescription);
			LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(layoutPrototype);
			out.println("Set properties of the LayoutPrototype with the id " + layoutPrototypeId + " to "
					+ layoutPrototypeDescription);
		}

		out.println();
	}

	/**
	 * Creates a role with the given localized titles and descriptions. If the
	 * role already exists only the localized titles and descriptions will be
	 * set.
	 * 
	 * @param titleMap
	 *            localized titles
	 * @param descriptionMap
	 *            localized descriptions
	 * @return created or existing role
	 * @throws Exception
	 */
	Role createOrUpdateRole(Map<Locale, String> titleMap, Map<Locale, String> descriptionMap, int type)
			throws Exception {
		Role role = RoleLocalServiceUtil.fetchRole(PortalUtil.getDefaultCompanyId(), titleMap.get(Locale.US));

		if (role != null) {
			out.println("Found role " + role.getName());
			RoleLocalServiceUtil.updateRole(role.getRoleId(), role.getName(), titleMap, descriptionMap, null, null);
		} else {
			role = RoleLocalServiceUtil.addRole(
					UserLocalServiceUtil.getDefaultUserId(PortalUtil.getDefaultCompanyId()), null, 0,
					titleMap.get(Locale.US), titleMap, descriptionMap, type, null, null);
			out.println("Created role " + role.getName());
		}
		return role;
	}

	/**
	 * Sets the permissions for the role with the given name.
	 * 
	 * @param roleName
	 *            name of the role
	 * @param permissions
	 *            permissions
	 * @throws Exception
	 */
	void setRolePermissions(Role role, Map<String, String[]> permissions, int scope) throws Exception {
		for (Map.Entry<String, String[]> entry : permissions.entrySet()) {
			ResourcePermissionLocalServiceUtil.setResourcePermissions(PortalUtil.getDefaultCompanyId(), entry.getKey(),
					scope, String.valueOf(0), role.getRoleId(), entry.getValue());
		}

		out.println("Set the permissions for the role " + role.getName());
		out.println();
	}

	/**
	 * Removes the given permissions
	 * 
	 * @param roleNames
	 *            role names
	 * @param names
	 *            name ids
	 * @param actionIds
	 *            action ids
	 * @throws Exception
	 */
	void removePermissions(String[] roleNames, String[] names, String[] actionIds) throws Exception {
		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.fetchRole(PortalUtil.getDefaultCompanyId(), roleName);
			if (role != null) {
				for (String name : names) {
					for (String actionId : actionIds) {
						ResourcePermissionLocalServiceUtil.removeResourcePermissions(PortalUtil.getDefaultCompanyId(),
								name, ResourceConstants.SCOPE_COMPANY, role.getRoleId(), actionId);
					}
				}
				out.println("Removed permissions for the role " + roleName);
			} else {
				out.println("WARNING: Couldn't find role " + roleName);
			}
		}
		out.println();
	}

	/**
	 * Adds the given permissions
	 * 
	 * @param roleNames
	 *            role names
	 * @param names
	 *            names
	 * @param actionIds
	 *            action ids
	 * @param scope
	 *            scope
	 * @param primKey
	 *            prim key
	 * @throws Exception
	 */
	void addPermissions(String[] roleNames, String[] names, String[] actionIds, int scope, String primKey)
			throws Exception {
		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.fetchRole(PortalUtil.getDefaultCompanyId(), roleName);
			if (role != null) {
				for (String name : names) {
					for (String actionId : actionIds) {
						ResourcePermissionLocalServiceUtil.addResourcePermission(PortalUtil.getDefaultCompanyId(),
								name, scope, primKey, role.getRoleId(), actionId);
					}
				}
				out.println("Added permissions for the role " + roleName);
			} else {
				out.println("WARNING: Couldn't find role " + roleName);
			}
		}
		out.println();
	}

	LayoutSetPrototype getLayoutSetPrototypeByLayoutSetPrototypeIdOrName(long layoutSetPrototypeId,
			String layoutSetPrototypeName) throws Exception {
		LayoutSetPrototype layoutSetPrototype = null;

		if (layoutSetPrototypeId != 0) {
			layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.fetchLayoutSetPrototype(layoutSetPrototypeId);
		}

		if (layoutSetPrototype == null) {
			List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeLocalServiceUtil
					.getLayoutSetPrototypes(PortalUtil.getDefaultCompanyId());

			for (LayoutSetPrototype lsp : layoutSetPrototypes) {
				if (lsp.getName(Locale.US).equals(layoutSetPrototypeName)) {
					layoutSetPrototype = lsp;
					break;
				}
			}

			if (layoutSetPrototype == null) {
				out.println("WARNING: could not find LayoutSetPrototype with the id " + layoutSetPrototypeId
						+ " or with the name " + layoutSetPrototypeName);
			} else {
				out.println("Found LayoutSetPrototype with the name " + layoutSetPrototype.getName(Locale.US)
						+ " and the id " + layoutSetPrototype.getLayoutSetPrototypeId());
			}
		}
		return layoutSetPrototype;
	}

	LayoutPrototype getLayoutPrototypeByLayoutPrototypeIdOrName(long layoutPrototypeId, String layoutPrototypeName)
			throws Exception {
		LayoutPrototype layoutPrototype = null;

		if (layoutPrototypeId != 0) {
			layoutPrototype = LayoutPrototypeLocalServiceUtil.fetchLayoutPrototype(layoutPrototypeId);
		}

		if (layoutPrototype == null) {
			List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (LayoutPrototype lsp : layoutPrototypes) {
				if (lsp.getName(Locale.US).equals(layoutPrototypeName)) {
					layoutPrototype = lsp;
					break;
				}
			}

			if (layoutPrototype == null) {
				out.println("WARNING: could not find LayoutPrototype with the id " + layoutPrototypeId
						+ " or with the name " + layoutPrototypeName);
			} else {
				out.println("Found LayoutPrototype with the name " + layoutPrototype.getName(Locale.US)
						+ " and the id " + layoutPrototype.getLayoutPrototypeId());
			}
		}
		return layoutPrototype;
	}
}