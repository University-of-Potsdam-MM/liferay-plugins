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


String USER_HOME_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Home";
String USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Profile";

String GROUP_WORK_WORKSPACE_TEMPLATE_NAME = "Group Work";
String GROUP_WORK_WORKSPACE_TEMPLATE_DESCRIPTION = "Diese Vorlage stattet den Workspace mit nützlichen Anwendungen für die Zusammenarbeit aus. Standardmäßig ist eine Dokumentenverwaltung enthalten sowie verschiedene Web2.0-Werkzeuge (Blog, Wiki, Forum). Es können aber auch weitere Seiten und Anwendungen hinzugefügt werden.";

String BLOG_PAGE_TEMPLATE_NAME = "Page with Blog";
String BLOG_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Blog, der z.B. als Lerntagebuch oder für Berichte genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

String WIKI_PAGE_TEMPLATE_NAME = "Page with Wiki";
String WIKI_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet ein Wiki, das z.B. als Glossar oder für Dokumentationen genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

String WEB_CONTENT_PAGE_TEMPLATE_NAME = "Page with Text-Image Editor";
String WEB_CONTENT_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Editor, der z.B. für Texte mit Grafiken und Weblinks genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

String PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME = "Page without Applications";
String PAGE_WITHOUT_APPLICATIONS_TEMPLATE_DESCRIPTION = "Diese Seite ist leer und kann nach dem Erstellen beliebig mit Anwendungen und Inhalten bestückt werden.";

Map<Locale, String> editorRoleTitleMap = new HashMap<Locale, String>();
editorRoleTitleMap.put(Locale.US, "Editor");
editorRoleTitleMap.put(Locale.GERMANY, "Redakteur");
Map<Locale, String> editorRoleDescriptionMap = new HashMap<Locale, String>();
editorRoleDescriptionMap.put(Locale.ENGLISH, "Editor");
editorRoleDescriptionMap.put(Locale.GERMAN, "Redakteur");
Map<String, String[]> editorRolePermissions = new HashMap<String, String[]>();
// Workspace Administration -> Pages -> Sites of the Workspaces ->
// General Permissions
editorRolePermissions.put(PortletKeys.GROUP_PAGES, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Pages -> Sites of the Workspaces ->
// Resource Permissions
editorRolePermissions.put(Group.class.getName(), [ ActionKeys.ADD_COMMUNITY,
		ActionKeys.ADD_LAYOUT, ActionKeys.ADD_LAYOUT_BRANCH, ActionKeys.ADD_LAYOUT_SET_BRANCH,
		ActionKeys.CONFIGURE_PORTLETS, ActionKeys.DELETE, ActionKeys.EXPORT_IMPORT_LAYOUTS,
		ActionKeys.EXPORT_IMPORT_PORTLET_INFO, ActionKeys.MANAGE_ANNOUNCEMENTS,
		ActionKeys.MANAGE_ARCHIVED_SETUPS, ActionKeys.MANAGE_LAYOUTS, ActionKeys.MANAGE_STAGING,
		ActionKeys.PERMISSIONS, ActionKeys.PREVIEW_IN_DEVICE, ActionKeys.PUBLISH_STAGING,
		ActionKeys.PUBLISH_TO_REMOTE, ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS,
		ActionKeys.VIEW_SITE_ADMINISTRATION, ActionKeys.VIEW_STAGING ] as String[] );
// Workspace Administration -> Pages -> Sites of the Workspaces -> Page
editorRolePermissions.put(Layout.class.getName(), [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.ADD_LAYOUT, ActionKeys.CONFIGURE_PORTLETS, ActionKeys.CUSTOMIZE, ActionKeys.DELETE,
		ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Recent Content-> General
// Permissions
editorRolePermissions.put(PortletKeys.RECENT_CONTENT, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> General
// Permissions
editorRolePermissions.put(PortletKeys.JOURNAL, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content
editorRolePermissions.put("com.liferay.portlet.journal", [ ActionKeys.ADD_ARTICLE,
		ActionKeys.ADD_FEED, ActionKeys.ADD_FOLDER, ActionKeys.ADD_STRUCTURE, ActionKeys.ADD_TEMPLATE,
		ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content Folder
editorRolePermissions.put(JournalFolder.class.getName(), [ ActionKeys.ACCESS,
		ActionKeys.ADD_ARTICLE, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content Article
editorRolePermissions.put(JournalArticle.class.getName(), [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.EXPIRE, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content Feed
editorRolePermissions.put(JournalFeed.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content Structure
editorRolePermissions.put(JournalStructure.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Web Content-> Resource
// Permissions -> Web Content Template
editorRolePermissions.put(JournalTemplate.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> General
// Permissions
editorRolePermissions.put(PortletKeys.DOCUMENT_LIBRARY, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> Resource
// Permissions -> Documents
editorRolePermissions.put("com.liferay.portlet.documentlibrary", [ ActionKeys.ADD_DOCUMENT,
		ActionKeys.ADD_DOCUMENT_TYPE, ActionKeys.ADD_FOLDER, ActionKeys.ADD_REPOSITORY,
		ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_STRUCTURE, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
		ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> Resource
// Permissions -> Documents Folder
editorRolePermissions.put(DLFolder.class.getName(), [ ActionKeys.ACCESS, ActionKeys.ADD_DOCUMENT,
		ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> Resource
// Permissions -> Document
editorRolePermissions.put(DLFileEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.OVERRIDE_CHECKOUT, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> Resource
// Permissions -> Shortcut
editorRolePermissions.put(DLFileShortcut.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Document and Media -> Resource
// Permissions -> Document Type
editorRolePermissions.put(DLFileEntryType.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Blogs -> General Permissions
editorRolePermissions.put(PortletKeys.BLOGS_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Blogs -> Resource Permissions
// -> Blog Entries
editorRolePermissions.put("com.liferay.portlet.blogs", [ ActionKeys.ADD_ENTRY,
		ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE ] as String[] );
// Workspace Administration -> Content -> Blogs -> Resource Permissions
// -> Blog Entry
editorRolePermissions.put(BlogsEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
		ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Message Boards -> General
// Permissions
editorRolePermissions.put(PortletKeys.MESSAGE_BOARDS_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Message Boards -> Resource
// Permissions -> Messages
editorRolePermissions.put("com.liferay.portlet.messageboards", [ ActionKeys.ADD_CATEGORY,
		ActionKeys.ADD_FILE, ActionKeys.ADD_MESSAGE, ActionKeys.BAN_USER, ActionKeys.LOCK_THREAD,
		ActionKeys.MOVE_THREAD, ActionKeys.PERMISSIONS, ActionKeys.REPLY_TO_MESSAGE, ActionKeys.SUBSCRIBE,
		ActionKeys.UPDATE_THREAD_PRIORITY, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Message Boards -> Resource
// Permissions -> Messages Boards Category
editorRolePermissions.put(MBCategory.class.getName(), [] as String[] );
// Workspace Administration -> Content -> Message Boards -> Resource
// Permissions -> Messages Boards Thread
editorRolePermissions.put(MBThread.class.getName(), [ ActionKeys.PERMISSIONS,
		ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Message Boards -> Resource
// Permissions -> Messages Boards Message
editorRolePermissions.put(MBMessage.class.getName(), [ ActionKeys.DELETE, ActionKeys.PERMISSIONS,
		ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Wiki -> General Permissions
editorRolePermissions.put(PortletKeys.WIKI_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Wiki -> Resource Permissions
// -> Wiki Nodes
editorRolePermissions.put("com.liferay.portlet.wiki", [ ActionKeys.ADD_NODE,
		ActionKeys.PERMISSIONS ] as String[] );
// Workspace Administration -> Content -> Wiki -> Resource Permissions
// -> Wiki Node
editorRolePermissions.put(WikiNode.class.getName(), [ ActionKeys.ADD_ATTACHMENT,
		ActionKeys.ADD_PAGE, ActionKeys.DELETE, ActionKeys.IMPORT, ActionKeys.PERMISSIONS,
		ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Wiki -> Resource Permissions
// -> Wiki Page
editorRolePermissions.put(WikiPage.class.getName(), [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
		ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Bookmarks -> General
// Permissions
editorRolePermissions.put(PortletKeys.BOOKMARKS, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Bookmarks -> Resource
// Permissions -> Bookmark Entries
editorRolePermissions.put("com.liferay.portlet.bookmarks", [ ActionKeys.ADD_ENTRY,
		ActionKeys.ADD_FOLDER, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Polls -> General Permissions
editorRolePermissions.put(PortletKeys.POLLS, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Polls -> Resource Permissions
// -> Poll Questions
editorRolePermissions.put("com.liferay.portlet.polls", [ ActionKeys.ADD_QUESTION,
		ActionKeys.PERMISSIONS ] as String[] );
// Workspace Administration -> Content -> Polls -> Resource Permissions
// -> Poll Question
editorRolePermissions.put(PollsQuestion.class.getName(), [ ActionKeys.ADD_VOTE, ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Tags -> General Permissions
editorRolePermissions.put(PortletKeys.TAGS_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Tags -> Resource Permissions
// -> Asset Entries
editorRolePermissions.put("com.liferay.portlet.asset", [ ActionKeys.ADD_CATEGORY,
		ActionKeys.ADD_TAG, ActionKeys.ADD_VOCABULARY, ActionKeys.PERMISSIONS ] as String[] );
// Workspace Administration -> Content -> Tags -> Resource Permissions
// -> Tag
editorRolePermissions.put(AssetTag.class.getName(), [ ActionKeys.DELETE, ActionKeys.PERMISSIONS,
		ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Categories -> General
// Permissions
editorRolePermissions.put(PortletKeys.ASSET_CATEGORIES_ADMIN, [
		ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
		ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Categories -> Resource
// Permissions -> Category Vocabulary
editorRolePermissions.put(AssetVocabulary.class.getName(), [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Content -> Categories -> Resource
// Permissions -> Category
editorRolePermissions.put(AssetCategory.class.getName(), [ ActionKeys.ADD_CATEGORY,
		ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Users -> Site Memberships -> General
// Permissions
editorRolePermissions.put(PortletKeys.SITE_MEMBERSHIPS_ADMIN, [] as String[] );
// Workspace Administration -> Users -> Site Memberships -> Resource
// Permissions -> Team
editorRolePermissions.put(Team.class.getName(), [] as String[] );
// Workspace Administration -> Users -> Site Teams -> General
// Permissions
editorRolePermissions.put("191", [] as String[] );
// Workspace Administration -> Applications -> Activities -> Application
// Permissions
editorRolePermissions.put("upactivities_WAR_upactivitiesportlet", [ ActionKeys.ADD_TO_PAGE,
ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Announcements ->
// Application Permissions
editorRolePermissions.put(PortletKeys.ANNOUNCEMENTS, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.ADD_ENTRY, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Announcements ->
// Application Permissions
editorRolePermissions.put("com.liferay.portlet.announcements.model.AnnouncementsEntry", [
		ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Asset Publisher ->
// Application Permissions
editorRolePermissions.put(PortletKeys.ASSET_PUBLISHER, [ ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Blogs -> Application
// Permissions
editorRolePermissions.put(PortletKeys.BLOGS, [ ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Categories Navigation ->
// Application Permissions
editorRolePermissions.put(PortletKeys.ASSET_CATEGORIES_NAVIGATION, [
		ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Comments and Feedback ->
// Application Permissions
editorRolePermissions.put(PortletKeys.PAGE_COMMENTS, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Documents and Media
// Display -> Application Permissions
editorRolePermissions.put(PortletKeys.DOCUMENT_LIBRARY_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Events Display ->
// Application Permissions
editorRolePermissions.put("1_WAR_eventsdisplayportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Media Gallery ->
// Application Permissions
editorRolePermissions.put(PortletKeys.MEDIA_GALLERY_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Message Boards ->
// Application Permissions
editorRolePermissions.put(PortletKeys.MESSAGE_BOARDS, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Microblogs -> Application
// Permissions
editorRolePermissions.put("1_WAR_microblogsportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Microblogs -> Resource
// Permissions -> Microblogs
editorRolePermissions.put("com.liferay.microblogs.model.MicroblogsEntry", [ ActionKeys.DELETE,
		ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Microblogs -> Resource
// Permissions -> Microblogs Entry
editorRolePermissions.put("com.liferay.microblogs",
		[ ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS ] as String[] );
// Workspace Administration -> Applications -> Microblogs Status Update
// -> Application Permissions
editorRolePermissions.put("2_WAR_microblogsportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Polls Display
// -> Application Permissions
editorRolePermissions.put(PortletKeys.POLLS_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Quick Notest
// -> Application Permissions
editorRolePermissions.put("97", [ ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> RSS -> Application
// Permissions
editorRolePermissions.put(PortletKeys.RSS, [ ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Social Office
// Announcements -> Application Permissions
editorRolePermissions.put("1_WAR_soannouncementsportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Tag Cloud -> Application
// Permissions
editorRolePermissions.put(PortletKeys.TAGS_CLOUD, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Tags Navigation ->
// Application Permissions
editorRolePermissions.put(PortletKeys.ASSET_TAGS_NAVIGATION, [
		ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
		ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Tasks -> Application
// Permissions
editorRolePermissions.put("1_WAR_tasksportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Tasks -> Resource
// Permissions -> Tasks
editorRolePermissions.put("com.liferay.tasks", [ ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS ] as String[] );
// Workspace Administration -> Applications -> Tasks -> Resource
// Permissions -> Tasks Entry
editorRolePermissions.put("com.liferay.tasks.model.TasksEntry", [ ActionKeys.ADD_DISCUSSION,
		ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> WYSIWYG -> Application Permissions
editorRolePermissions.put("1_WAR_wysiwygportlet", [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Web Content Display ->
// Application Permissions
editorRolePermissions.put(PortletKeys.JOURNAL_CONTENT, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Web Content List ->
// Application Permissions
editorRolePermissions.put(PortletKeys.JOURNAL_CONTENT_LIST, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Web Content Search ->
// Application Permissions
editorRolePermissions.put(PortletKeys.JOURNAL_CONTENT_SEARCH, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Wiki -> Application
// Permissions
editorRolePermissions.put(PortletKeys.WIKI, [ ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Wiki Display ->
// Application Permissions
editorRolePermissions.put(PortletKeys.WIKI_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
		ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[] );
// Workspace Administration -> Applications -> Workspace Description ->
// Application Permissions
editorRolePermissions.put("workspacedescription_WAR_workspacedescriptionportlet", [
		ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
		ActionKeys.VIEW ] as String[] );
		
// names of the roles permissions will be removed from
String[] roleNamesForPermissionDeletion = ["User", "Power User", "Guest" ] as String[];
// name ids for permission deletion
String[] namesForPermissionDeletion = [ PortletKeys.REQUESTS, PortletKeys.ANNOUNCEMENTS,
		PortletKeys.ASSET_PUBLISHER, "4_WAR_contactsportlet", "3_WAR_contactsportlet", "2_WAR_contactsportlet",
		PortletKeys.LOGIN, PortletKeys.IFRAME, PortletKeys.ASSET_TAGS_NAVIGATION, "143", PortletKeys.ALERTS,
		"2_WAR_tasksportlet", "mycustompages_WAR_custompagesportlet",
		"othercustompages_WAR_custompagesportlet", PortletKeys.ACTIVITIES,
		"upactivities_WAR_upactivitiesportlet", "1_WAR_notificationsportlet", "1_WAR_eventsdisplayportlet",
		"2_WAR_microblogsportlet", PortletKeys.RSS, "97", PortletKeys.MEDIA_GALLERY_DISPLAY,
		"upiframe_WAR_upiframeportlet", "1_WAR_wysiwygportlet",
		"workspacedescription_WAR_workspacedescriptionportlet", "1_WAR_privatemessagingportlet",
		PortletKeys.SITE_MEMBERS_DIRECTORY ] as String[];
// action ids for permission deletion
String[] actionIdsForPermissionDeletion = [ ActionKeys.ADD_TO_PAGE ] as String[];

// names of the roles permissions will be added to
String[] roleNamesForPermissionAdditionCompany = [ "User" ] as String[];
// name ids for permission addition
String[] namesForPermissionAdditionCompany = [ "com.liferay.portlet.documentlibrary" ] as String[];
// action ids for permission addition
String[] actionIdsForPermissionAdditionCompany = [ ActionKeys.VIEW ] as String[];

// names of the roles permissions will be added to
String[] roleNamesForPermissionAdditionPersonal = [ "User" ] as String[];
// name ids for permission addition
String[] namesForPermissionAdditionPersonal = [ "mycustompages_WAR_custompagesportlet",
		"othercustompages_WAR_custompagesportlet" ] as String[];
// action ids for permission addition
String[] actionIdsForPermissionAdditionPersonal = [ ActionKeys.ADD_TO_PAGE ] as String[];

try {

	// Make predefined pages in users personal area uneditable
	setLayoutSetPrototypeUneditable(USER_HOME_LAYOUT_SET_PROTOTYPE_NAME);

	// Make predefined pages in users profile uneditable
	setLayoutSetPrototypeUneditable(USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME);

	// Set description of the group work workspace
	setLayoutSetPrototypeDescription(GROUP_WORK_WORKSPACE_TEMPLATE_NAME,
			GROUP_WORK_WORKSPACE_TEMPLATE_DESCRIPTION);

	// Set description of the blog page layout prototype
	setLayoutPrototypeDescription(BLOG_PAGE_TEMPLATE_NAME, BLOG_PAGE_TEMPLATE_DESCRIPTION);

	// Set description of the wiki page layout prototype
	setLayoutPrototypeDescription(WIKI_PAGE_TEMPLATE_NAME, WIKI_PAGE_TEMPLATE_DESCRIPTION);

	// Set description of the web content page layout prototype
	setLayoutPrototypeDescription(WEB_CONTENT_PAGE_TEMPLATE_NAME, WEB_CONTENT_PAGE_TEMPLATE_DESCRIPTION);

	// Set description of the page without applications layout prototype
	setLayoutPrototypeDescription(PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME,
			PAGE_WITHOUT_APPLICATIONS_TEMPLATE_DESCRIPTION);

	// Create or update editor role and set permissions
	Role role = createOrUpdateRole(editorRoleTitleMap, editorRoleDescriptionMap, RoleConstants.TYPE_SITE);
	setRolePermissions(role, editorRolePermissions, ResourceConstants.SCOPE_GROUP_TEMPLATE);
	
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
void setLayoutSetPrototypeUneditable(String layoutSetPrototypeName) throws Exception {
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeLocalServiceUtil
			.getLayoutSetPrototypes(PortalUtil.getDefaultCompanyId());

	out.println("Try to make layouts of the LayoutSetPrototype " + layoutSetPrototypeName + " uneditable");
	for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
		if (layoutSetPrototype.getName(Locale.US).equals(layoutSetPrototypeName)) {
			out.println("Found LayoutSetPrototype " + layoutSetPrototypeName + ". layoutSetPrototypeId: "
					+ layoutSetPrototype.getLayoutSetPrototypeId());

			UnicodeProperties properties = layoutSetPrototype.getSettingsProperties();
			properties.setProperty("layoutsUpdateable", "true");
			layoutSetPrototype.setSettingsProperties(properties);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set property layoutsUpdateable of the LayoutSetPrototype " + layoutSetPrototypeName
					+ " to true");

			List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(layoutSetPrototype.getGroupId(), true);
			for (Layout layout : layouts) {
				layout.getLayoutType().setTypeSettingsProperty("layoutUpdateable", "false");
				LayoutLocalServiceUtil.updateLayout(layout);
				out.println("Set property layoutUpdateable of the layout " + layout.getName(Locale.US)
						+ " to false");
			}
			out.println();
			return;
		}
	}
	out.println("WARNING: could not find LayoutSetPrototype " + layoutSetPrototypeName);
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
void setLayoutSetPrototypeDescription(String layoutSetPrototypeName, String layoutSetPrototypeDescription)
		throws Exception {
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeLocalServiceUtil
			.getLayoutSetPrototypes(PortalUtil.getDefaultCompanyId());

	out.println("Try to set description of the LayoutSetPrototype " + layoutSetPrototypeName);
	for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
		if (layoutSetPrototype.getName(Locale.US).equals(layoutSetPrototypeName)) {
			out.println("Found LayoutSetPrototype " + layoutSetPrototypeName + ". layoutSetPrototypeId: "
					+ layoutSetPrototype.getLayoutSetPrototypeId());

			layoutSetPrototype.setDescription(layoutSetPrototypeDescription);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set description of the LayoutSetPrototype " + layoutSetPrototypeName + " to: "
					+ layoutSetPrototypeDescription);

			out.println();
			return;
		}
	}
	out.println("WARNING: could not find LayoutSetPrototype " + layoutSetPrototypeName);
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
void setLayoutPrototypeDescription(String layoutPrototypeName, String layoutPrototypeDescription) throws Exception {
	List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);

	out.println("Try to set description of the LayoutPrototype " + layoutPrototypeName);
	for (LayoutPrototype layoutPrototype : layoutPrototypes) {
		if (layoutPrototype.getName(Locale.US).equals(layoutPrototypeName)) {
			out.println("Found LayoutPrototype " + layoutPrototypeName + ". layoutPrototypeId: "
					+ layoutPrototype.getLayoutPrototypeId());

			layoutPrototype.setDescription(layoutPrototypeDescription);
			LayoutPrototypeLocalServiceUtil.updateLayoutPrototype(layoutPrototype);
			out.println("Set description of the LayoutPrototype " + layoutPrototypeName + " to: "
					+ layoutPrototypeDescription);

			out.println();
			return;
		}
	}
	out.println("WARNING: could not find LayoutPrototype " + layoutPrototypeName);
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
		Role role = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), roleName);
		for (String name : names) {
			for (String actionId : actionIds) {
				ResourcePermissionLocalServiceUtil.removeResourcePermissions(PortalUtil.getDefaultCompanyId(),
						name, ResourceConstants.SCOPE_COMPANY, role.getRoleId(), actionId);
			}
		}
		out.println("Removed permissions for the role " + roleName);
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