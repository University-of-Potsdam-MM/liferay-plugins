import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.LayoutSetPrototypeActionableDynamicQuery;
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
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;


		// if set the script will search for the site template by the id (and
		// set the name). Otherwise the script will search for the site template
		// by the corresponding name.
		long GROUP_WORK_SITE_TEMPLATE_ID = 0;
		// if set the script will search for the site template by the id (and
		// set the name). Otherwise the script will search for the site template
		// by the corresponding name.
		long EMPTY_SITE_TEMPLATE_ID = 0;
		// if set the script will search for the site template by the id (and
		// set the name). Otherwise the script will search for the site template
		// by the corresponding name.
		long PUBLIC_SITE_AREA_TEMPLATE_ID = 0;
		// if set the script will search for the page template by the id (and
		// set the name). Otherwise the script will search for the page template
		// by the corresponding name.
		long BLOG_PAGE_TEMPLATE_ID = 0;
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

		// Properties and layouts for the group work site template
		Map<Locale, String> GROUP_WORK_SITE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_SITE_TEMPLATE_NAME_MAP.put(Locale.US, "Group Work");
		GROUP_WORK_SITE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Gruppenarbeit");
		String GROUP_WORK_SITE_TEMPLATE_DESCRIPTION = "Diese Vorlage stattet den Workspace mit nützlichen Anwendungen für die Zusammenarbeit aus. StandardmäÜ?ig ist eine Dokumentenverwaltung enthalten sowie verschiedene Web2.0-Werkzeuge (Blog, Wiki, Forum). Es können aber auch weitere Seiten und Anwendungen hinzugefügt werden.";

		Map<Locale, String> GROUP_WORK_OVERVIEW_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_OVERVIEW_LAYOUT_NAME_MAP.put(Locale.US, "Overview");
		GROUP_WORK_OVERVIEW_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Ü?bersicht");
		Map<String, String[]> GROUP_WORK_OVERVIEW_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_OVERVIEW_LAYOUT_PORTLETS_MAP.put("column-1", [ "1_WAR_soannouncementsportlet",
				"upactivities_WAR_upactivitiesportlet" ] as String[]);
		GROUP_WORK_OVERVIEW_LAYOUT_PORTLETS_MAP.put("column-2", [ PortletKeys.JOURNAL_CONTENT ] as String[]);
		Object[] GROUP_WORK_OVERVIEW_LAYOUT = [ GROUP_WORK_OVERVIEW_LAYOUT_NAME_MAP, new Boolean(true),
				null, "2_columns_i", GROUP_WORK_OVERVIEW_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_DOCUMENTS_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_DOCUMENTS_LAYOUT_NAME_MAP.put(Locale.US, "Documents & Resources");
		GROUP_WORK_DOCUMENTS_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Dokumente & Ressourcen");
		Map<String, String[]> GROUP_WORK_DOCUMENTS_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_DOCUMENTS_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.DOCUMENT_LIBRARY ] as String[]);
		Object[] GROUP_WORK_DOCUMENTS_LAYOUT = [ GROUP_WORK_DOCUMENTS_LAYOUT_NAME_MAP, new Boolean(true),
				null, "1_column", GROUP_WORK_DOCUMENTS_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_WEB_BOOKMARKS_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_WEB_BOOKMARKS_LAYOUT_NAME_MAP.put(Locale.US, "Web bookmarks");
		GROUP_WORK_WEB_BOOKMARKS_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Weblesezeichen");
		Map<String, String[]> GROUP_WORK_WEB_BOOKMARKS_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_WEB_BOOKMARKS_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.BOOKMARKS ] as String[]);
		Object[] GROUP_WORK_WEB_BOOKMARKS_LAYOUT = [ GROUP_WORK_WEB_BOOKMARKS_LAYOUT_NAME_MAP,
				new Boolean(true), GROUP_WORK_DOCUMENTS_LAYOUT_NAME_MAP.get(Locale.US), "1_column",
				GROUP_WORK_WEB_BOOKMARKS_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_TOOLS_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.put(Locale.US, "Tools");
		GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Werkzeuge");
		Object[] GROUP_WORK_TOOLS_LAYOUT = [ GROUP_WORK_TOOLS_LAYOUT_NAME_MAP, new Boolean(true), null,
				"1_column", new LinkedHashMap<String, String[]>() ] as Object[];

		Map<Locale, String> GROUP_WORK_BLOG_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_BLOG_LAYOUT_NAME_MAP.put(Locale.US, "Blog");
		GROUP_WORK_BLOG_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Blog");
		Map<String, String[]> GROUP_WORK_BLOG_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_BLOG_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.BLOGS ] as String[]);
		Object[] GROUP_WORK_BLOG_LAYOUT = [ GROUP_WORK_BLOG_LAYOUT_NAME_MAP, new Boolean(true),
				GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.get(Locale.US), "1_column", GROUP_WORK_BLOG_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_FORUM_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_FORUM_LAYOUT_NAME_MAP.put(Locale.US, "Forum");
		GROUP_WORK_FORUM_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Forum");
		Map<String, String[]> GROUP_WORK_FORUM_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_FORUM_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.MESSAGE_BOARDS ] as String[]);
		Object[] GROUP_WORK_FORUM_LAYOUT = [ GROUP_WORK_FORUM_LAYOUT_NAME_MAP, new Boolean(true),
				GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.get(Locale.US), "1_column", GROUP_WORK_FORUM_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_WIKI_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_WIKI_LAYOUT_NAME_MAP.put(Locale.US, "Wiki");
		GROUP_WORK_WIKI_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Wiki");
		Map<String, String[]> GROUP_WORK_WIKI_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_WIKI_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.WIKI ] as String[]);
		Object[] GROUP_WORK_WIKI_LAYOUT = [ GROUP_WORK_WIKI_LAYOUT_NAME_MAP, new Boolean(true),
				GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.get(Locale.US), "1_column", GROUP_WORK_WIKI_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_MEMBERS_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_MEMBERS_LAYOUT_NAME_MAP.put(Locale.US, "Members");
		GROUP_WORK_MEMBERS_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Mitglieder");
		Map<String, String[]> GROUP_WORK_MEMBERS_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_MEMBERS_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.SITE_MEMBERSHIPS_ADMIN ] as String[]);
		GROUP_WORK_MEMBERS_LAYOUT_PORTLETS_MAP.put("column-2", [ "2_WAR_soportlet" ] as String[]);
		Object[] GROUP_WORK_MEMBERS_LAYOUT = [ GROUP_WORK_MEMBERS_LAYOUT_NAME_MAP, new Boolean(true),
				null, "2_columns_i", GROUP_WORK_MEMBERS_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> GROUP_WORK_ORGANISATIONAL_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		GROUP_WORK_ORGANISATIONAL_LAYOUT_NAME_MAP.put(Locale.US, "Organisational");
		GROUP_WORK_ORGANISATIONAL_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Organisierung");
		Map<String, String[]> GROUP_WORK_ORGANISATIONAL_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		GROUP_WORK_ORGANISATIONAL_LAYOUT_PORTLETS_MAP.put("column-1", [ "1_WAR_tasksportlet" ] as String[]);
		GROUP_WORK_ORGANISATIONAL_LAYOUT_PORTLETS_MAP.put("column-2", [ PortletKeys.POLLS_DISPLAY ] as String[]);
		Object[] GROUP_WORK_ORGANISATIONAL_LAYOUT = [ GROUP_WORK_ORGANISATIONAL_LAYOUT_NAME_MAP,
				new Boolean(true), null, "2_columns_ii", GROUP_WORK_ORGANISATIONAL_LAYOUT_PORTLETS_MAP ] as Object[];

		Object[][] GROUP_WORK_LAYOUTS = [ GROUP_WORK_OVERVIEW_LAYOUT, GROUP_WORK_DOCUMENTS_LAYOUT,
				GROUP_WORK_WEB_BOOKMARKS_LAYOUT, GROUP_WORK_TOOLS_LAYOUT, GROUP_WORK_BLOG_LAYOUT,
				GROUP_WORK_FORUM_LAYOUT, GROUP_WORK_WIKI_LAYOUT, GROUP_WORK_ORGANISATIONAL_LAYOUT,
				GROUP_WORK_MEMBERS_LAYOUT ] as Object[][];

		Map<Locale, String> WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_TITLE_MAP = new HashMap<Locale, String>();
		WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_TITLE_MAP.put(Locale.GERMANY, "Gruppenbeschreibung");
		WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_TITLE_MAP.put(Locale.US, "Group Description");
		String WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_CONTENT = "<?xml version=\"1.0\"?><root available-locales=\"en_US,de_DE\" default-locale=\"de_DE\"><static-content language-id=\"en_US\"><![CDATA[Here you can place a group description.]]></static-content><static-content language-id=\"de_DE\"><![CDATA[Hier haben Sie Platz für eine Gruppenbeschreibung.]]></static-content></root>";

		// Properties and layouts for the empty site template
		Map<Locale, String> EMPTY_SITE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		EMPTY_SITE_TEMPLATE_NAME_MAP.put(Locale.US, "Empty");
		EMPTY_SITE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Leer");
		String EMPTY_SITE_TEMPLATE_DESCRIPTION = "Wenn Sie diese Option wählen, enthält der Workspace zunächst noch keine Seiten und Anwendungen. Sie können diese mit wenigen Klicks selbst hinzufügen und so Ihren Workspace individuell gestalten.";

		Map<Locale, String> EMPTY_OVERVIEW_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		EMPTY_OVERVIEW_LAYOUT_NAME_MAP.put(Locale.US, "Overview");
		EMPTY_OVERVIEW_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Ü?bersicht");
		Map<String, String[]> EMPTY_OVERVIEW_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		Object[] EMPTY_OVERVIEW_LAYOUT = [ EMPTY_OVERVIEW_LAYOUT_NAME_MAP, new Boolean(true), null,
				"2_columns_i", EMPTY_OVERVIEW_LAYOUT_PORTLETS_MAP ] as Object[];

		Map<Locale, String> EMPTY_MEMBERS_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		EMPTY_MEMBERS_LAYOUT_NAME_MAP.put(Locale.US, "Members");
		EMPTY_MEMBERS_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Mitglieder");
		Map<String, String[]> EMPTY_MEMBERS_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		EMPTY_MEMBERS_LAYOUT_PORTLETS_MAP.put("column-1", [ PortletKeys.SITE_MEMBERSHIPS_ADMIN ] as String[]);
		EMPTY_MEMBERS_LAYOUT_PORTLETS_MAP.put("column-2", [ "2_WAR_soportlet" ] as String[]);
		Object[] EMPTY_MEMBERS_LAYOUT = [ EMPTY_MEMBERS_LAYOUT_NAME_MAP, new Boolean(true), null,
				"2_columns_i", EMPTY_MEMBERS_LAYOUT_PORTLETS_MAP ] as Object[];

		Object[][] EMPTY_SITE_TEMPLATE_LAYOUTS = [ EMPTY_OVERVIEW_LAYOUT, EMPTY_MEMBERS_LAYOUT ] as Object[][];

		// Properties and layouts for public area of sites
		Map<Locale, String> PUBLIC_AREA_SITE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		PUBLIC_AREA_SITE_TEMPLATE_NAME_MAP.put(Locale.US, "Public Workspace Page");
		PUBLIC_AREA_SITE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Öffentlicher Workspace-Bereich");
		String PUBLIC_AREA_SITE_TEMPLATE_DESCRIPTION = "Öffenlicher Workspace-Bereich";

		Map<Locale, String> PUBLIC_AREA_SITE_LAYOUT_NAME_MAP = new HashMap<Locale, String>();
		PUBLIC_AREA_SITE_LAYOUT_NAME_MAP.put(Locale.US, "Overview");
		PUBLIC_AREA_SITE_LAYOUT_NAME_MAP.put(Locale.GERMANY, "Ü?bersicht");
		Map<String, String[]> PUBLIC_AREA_SITE_LAYOUT_PORTLETS_MAP = new LinkedHashMap<String, String[]>();
		PUBLIC_AREA_SITE_LAYOUT_PORTLETS_MAP.put("column-1",
				[ "workspacedescription_WAR_workspacedescriptionportlet" ] as String[]);
		Object[] PUBLIC_AREA_SITE_LAYOUT = [ PUBLIC_AREA_SITE_LAYOUT_NAME_MAP, new Boolean(true),
				GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.get(Locale.US), "1_column", PUBLIC_AREA_SITE_LAYOUT_PORTLETS_MAP ] as Object[];

		Object[][] PUBLIC_AREA_SITE_LAYOUTS = [ PUBLIC_AREA_SITE_LAYOUT ] as Object[][];

		// Properties for the blog page template
		Map<Locale, String> BLOG_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		BLOG_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Blog");
		BLOG_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit einem Blog");
		String BLOG_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Blog, der z.B. als Lerntagebuch oder für Berichte genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		// Properties for the wiki page template
		Map<Locale, String> WIKI_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		WIKI_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Wiki");
		WIKI_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit einem Wiki");
		String WIKI_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet ein Wiki, das z.B. als Glossar oder für Dokumentationen genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		// Properties for the web content page template
		Map<Locale, String> WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP.put(Locale.US, "Page with Text-Image Editor");
		WEB_CONTENT_PAGE_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Seite mit Text-Bild-Editor");
		String WEB_CONTENT_PAGE_TEMPLATE_DESCRIPTION = "Diese Seite beinhaltet einen Editor, der z.B. für Texte mit Grafiken und Weblinks genutzt werden kann. Es können weitere Anwendungen hinzugefügt werden.";

		// Properties for the empty page template
		Map<Locale, String> PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP = new HashMap<Locale, String>();
		PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP.put(Locale.US, "Page without Applications");
		PAGE_WITHOUT_APPLICATIONS_TEMPLATE_NAME_MAP.put(Locale.GERMANY, "Frei gestaltbare Seite");
		String PAGE_WITHOUT_APPLICATIONS_TEMPLATE_DESCRIPTION = "Diese Seite ist leer und kann nach dem Erstellen beliebig mit Anwendungen und Inhalten bestückt werden.";

		// Properties and permissions for the editor role
		Map<Locale, String> EDITOR_ROLE_TITLE_MAP = new HashMap<Locale, String>();
		EDITOR_ROLE_TITLE_MAP.put(Locale.US, "Editor");
		EDITOR_ROLE_TITLE_MAP.put(Locale.GERMANY, "Redakteur");
		Map<Locale, String> EDITOR_ROLE_DESCRIPTION_MAP = new HashMap<Locale, String>();
		EDITOR_ROLE_DESCRIPTION_MAP.put(Locale.ENGLISH, "Editor");
		EDITOR_ROLE_DESCRIPTION_MAP.put(Locale.GERMAN, "Redakteur");
		Map<String, String[]> EDITOR_ROLE_PERMISSIONS = new HashMap<String, String[]>();
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.GROUP_PAGES, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// Resource Permissions
		EDITOR_ROLE_PERMISSIONS.put(Group.class.getName(), [ ActionKeys.ADD_COMMUNITY,
				ActionKeys.ADD_LAYOUT, ActionKeys.ADD_LAYOUT_BRANCH, ActionKeys.ADD_LAYOUT_SET_BRANCH,
				ActionKeys.CONFIGURE_PORTLETS, ActionKeys.EXPORT_IMPORT_LAYOUTS, ActionKeys.EXPORT_IMPORT_PORTLET_INFO,
				ActionKeys.MANAGE_ANNOUNCEMENTS, ActionKeys.MANAGE_ARCHIVED_SETUPS, ActionKeys.MANAGE_LAYOUTS,
				ActionKeys.MANAGE_STAGING, ActionKeys.PERMISSIONS, ActionKeys.PREVIEW_IN_DEVICE,
				ActionKeys.PUBLISH_STAGING, ActionKeys.PUBLISH_TO_REMOTE, ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS,
				ActionKeys.VIEW_SITE_ADMINISTRATION, ActionKeys.VIEW_STAGING ] as String[]);
		// Workspace Administration -> Pages -> Sites of the Workspaces -> Page
		EDITOR_ROLE_PERMISSIONS.put(Layout.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.ADD_LAYOUT, ActionKeys.CONFIGURE_PORTLETS, ActionKeys.CUSTOMIZE, ActionKeys.DELETE,
				ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Recent Content-> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.RECENT_CONTENT, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.journal", [ ActionKeys.ADD_ARTICLE,
				ActionKeys.ADD_FEED, ActionKeys.ADD_FOLDER, ActionKeys.ADD_STRUCTURE, ActionKeys.ADD_TEMPLATE,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Folder
		EDITOR_ROLE_PERMISSIONS.put(JournalFolder.class.getName(), [ ActionKeys.ACCESS,
				ActionKeys.ADD_ARTICLE, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Article
		EDITOR_ROLE_PERMISSIONS.put(JournalArticle.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.EXPIRE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Feed
		EDITOR_ROLE_PERMISSIONS.put(JournalFeed.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Structure
		EDITOR_ROLE_PERMISSIONS.put(JournalStructure.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Template
		EDITOR_ROLE_PERMISSIONS.put(JournalTemplate.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.documentlibrary", [ ActionKeys.ADD_DOCUMENT,
				ActionKeys.ADD_DOCUMENT_TYPE, ActionKeys.ADD_FOLDER, ActionKeys.ADD_REPOSITORY,
				ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_STRUCTURE, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents Folder
		EDITOR_ROLE_PERMISSIONS.put(DLFolder.class.getName(), [ ActionKeys.ACCESS,
				ActionKeys.ADD_DOCUMENT, ActionKeys.ADD_SHORTCUT, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document
		EDITOR_ROLE_PERMISSIONS.put(DLFileEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.OVERRIDE_CHECKOUT, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Shortcut
		EDITOR_ROLE_PERMISSIONS.put(DLFileShortcut.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document Type
		EDITOR_ROLE_PERMISSIONS.put(DLFileEntryType.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Blogs -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BLOGS_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.blogs", [ ActionKeys.ADD_ENTRY,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE ] as String[]);
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entry
		EDITOR_ROLE_PERMISSIONS.put(BlogsEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE,
				ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS_ADMIN, [
				ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
				ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.messageboards", [ ActionKeys.ADD_CATEGORY,
				ActionKeys.ADD_FILE, ActionKeys.ADD_MESSAGE, ActionKeys.BAN_USER, ActionKeys.LOCK_THREAD,
				ActionKeys.MOVE_THREAD, ActionKeys.PERMISSIONS, ActionKeys.REPLY_TO_MESSAGE, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE_THREAD_PRIORITY, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Category
		EDITOR_ROLE_PERMISSIONS.put(MBCategory.class.getName(), [] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Thread
		EDITOR_ROLE_PERMISSIONS.put(MBThread.class.getName(), [ ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Message
		EDITOR_ROLE_PERMISSIONS.put(MBMessage.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Nodes
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.wiki", [ ActionKeys.ADD_NODE,
				ActionKeys.PERMISSIONS ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Node
		EDITOR_ROLE_PERMISSIONS.put(WikiNode.class.getName(), [ ActionKeys.ADD_ATTACHMENT,
				ActionKeys.ADD_PAGE, ActionKeys.DELETE, ActionKeys.IMPORT, ActionKeys.PERMISSIONS,
				ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Page
		EDITOR_ROLE_PERMISSIONS.put(WikiPage.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Bookmarks -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BOOKMARKS, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Bookmarks -> Resource
		// Permissions -> Bookmark Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.bookmarks", [ ActionKeys.ADD_ENTRY,
				ActionKeys.ADD_FOLDER, ActionKeys.PERMISSIONS, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Polls -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.POLLS, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Questions
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.polls", [ ActionKeys.ADD_QUESTION,
				ActionKeys.PERMISSIONS ] as String[]);
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Question
		EDITOR_ROLE_PERMISSIONS.put(PollsQuestion.class.getName(), [ ActionKeys.ADD_VOTE,
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Tags -> General Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.TAGS_ADMIN, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Asset Entries
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.asset", [ ActionKeys.ADD_CATEGORY,
				ActionKeys.ADD_TAG, ActionKeys.ADD_VOCABULARY, ActionKeys.PERMISSIONS ] as String[]);
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Tag
		EDITOR_ROLE_PERMISSIONS.put(AssetTag.class.getName(), [ ActionKeys.DELETE, ActionKeys.PERMISSIONS,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_ADMIN, [
				ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS,
				ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category Vocabulary
		EDITOR_ROLE_PERMISSIONS.put(AssetVocabulary.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category
		EDITOR_ROLE_PERMISSIONS.put(AssetCategory.class.getName(), [ ActionKeys.ADD_CATEGORY,
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Users -> Site Memberships -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.SITE_MEMBERSHIPS_ADMIN, [] as String[]);
		// Workspace Administration -> Users -> Site Memberships -> Resource
		// Permissions -> Team
		EDITOR_ROLE_PERMISSIONS.put(Team.class.getName(), [] as String[]);
		// Workspace Administration -> Users -> Site Teams -> General
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("191", [] as String[]);
		// Workspace Administration -> Applications -> Activities -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("upactivities_WAR_upactivitiesportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ANNOUNCEMENTS, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.ADD_ENTRY, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.portlet.announcements.model.AnnouncementsEntry", [
				ActionKeys.DELETE, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Asset Publisher ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_PUBLISHER, [
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Blogs -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.BLOGS, [ ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Categories Navigation ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_NAVIGATION, [
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Comments and Feedback ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.PAGE_COMMENTS, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Documents and Media
		// Display -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Events Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_eventsdisplayportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Media Gallery ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MEDIA_GALLERY_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Message Boards ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_microblogsportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.microblogs.model.MicroblogsEntry", [ ActionKeys.DELETE,
				ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs Entry
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.microblogs", [ ActionKeys.ADD_ENTRY,
				ActionKeys.PERMISSIONS ] as String[]);
		// Workspace Administration -> Applications -> Microblogs Status Update
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("2_WAR_microblogsportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Polls Display
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.POLLS_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Quick Notest
		// -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("97", [ ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> RSS -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.RSS, [ ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Social Office
		// Announcements -> Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_soannouncementsportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tag Cloud -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.TAGS_CLOUD, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tags Navigation ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.ASSET_TAGS_NAVIGATION, [
				ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE, ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION,
				ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_tasksportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.tasks", [ ActionKeys.ADD_ENTRY, ActionKeys.PERMISSIONS ] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks Entry
		EDITOR_ROLE_PERMISSIONS.put("com.liferay.tasks.model.TasksEntry", [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE_DISCUSSION, ActionKeys.PERMISSIONS, ActionKeys.UPDATE, ActionKeys.UPDATE_DISCUSSION,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> WYSIWYG -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put("1_WAR_wysiwygportlet", [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content List ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_LIST, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content Search ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_SEARCH, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Wiki -> Application
		// Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI, [ ActionKeys.ADD_PORTLET_DISPLAY_TEMPLATE,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Wiki Display ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put(PortletKeys.WIKI_DISPLAY, [ ActionKeys.ADD_TO_PAGE,
				ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Workspace Description ->
		// Application Permissions
		EDITOR_ROLE_PERMISSIONS.put("workspacedescription_WAR_workspacedescriptionportlet", [
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);

		// Properties and permissions for the member role
		Map<Locale, String> MEMBER_ROLE_TITLE_MAP = new HashMap<Locale, String>();
		MEMBER_ROLE_TITLE_MAP.put(Locale.US, "Member");
		MEMBER_ROLE_TITLE_MAP.put(Locale.GERMANY, "Mitglied");
		Map<Locale, String> MEMBER_ROLE_DESCRIPTION_MAP = new HashMap<Locale, String>();
		MEMBER_ROLE_DESCRIPTION_MAP.put(Locale.ENGLISH, "Member");
		MEMBER_ROLE_DESCRIPTION_MAP.put(Locale.GERMAN, "Mitglied");
		Map<String, String[]> MEMBER_ROLE_PERMISSIONS = new HashMap<String, String[]>();
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// General Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.GROUP_PAGES, [ ActionKeys.ACCESS_IN_CONTROL_PANEL,
				ActionKeys.ADD_TO_PAGE, ActionKeys.CONFIGURATION, ActionKeys.PERMISSIONS, ActionKeys.PREFERENCES,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Pages -> Sites of the Workspaces ->
		// Resource Permissions
		MEMBER_ROLE_PERMISSIONS.put(Group.class.getName(), [ ActionKeys.VIEW, ActionKeys.VIEW_MEMBERS ] as String[]);
		// Workspace Administration -> Pages -> Sites of the Workspaces -> Page
		MEMBER_ROLE_PERMISSIONS
				.put(Layout.class.getName(), [ ActionKeys.ADD_DISCUSSION, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Recent Content-> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.RECENT_CONTENT, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.journal", [ ActionKeys.ADD_ARTICLE,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Folder
		MEMBER_ROLE_PERMISSIONS.put(JournalFolder.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Article
		MEMBER_ROLE_PERMISSIONS.put(JournalArticle.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Feed
		MEMBER_ROLE_PERMISSIONS.put(JournalFeed.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Structure
		MEMBER_ROLE_PERMISSIONS.put(JournalStructure.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Web Content-> Resource
		// Permissions -> Web Content Template
		MEMBER_ROLE_PERMISSIONS.put(JournalTemplate.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.documentlibrary", [ ActionKeys.ADD_DOCUMENT,
				ActionKeys.ADD_FOLDER, ActionKeys.ADD_REPOSITORY, ActionKeys.ADD_STRUCTURE,
				ActionKeys.SUBSCRIBE, ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Documents Folder
		MEMBER_ROLE_PERMISSIONS.put(DLFolder.class.getName(), [ ActionKeys.ACCESS,
				ActionKeys.ADD_DOCUMENT, ActionKeys.ADD_SUBFOLDER, ActionKeys.DELETE,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document
		MEMBER_ROLE_PERMISSIONS.put(DLFileEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.OVERRIDE_CHECKOUT, ActionKeys.UPDATE, 				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Shortcut
		MEMBER_ROLE_PERMISSIONS.put(DLFileShortcut.class.getName(), [  ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Document and Media -> Resource
		// Permissions -> Document Type
		MEMBER_ROLE_PERMISSIONS.put(DLFileEntryType.class.getName(), [ ActionKeys.DELETE,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Blogs -> General Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.BLOGS_ADMIN, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entries
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.blogs", [ ActionKeys.ADD_ENTRY,
				ActionKeys.SUBSCRIBE ] as String[]);
		// Workspace Administration -> Content -> Blogs -> Resource Permissions
		// -> Blog Entry
		MEMBER_ROLE_PERMISSIONS.put(BlogsEntry.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS_ADMIN, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.messageboards", [ ActionKeys.ADD_FILE,
				ActionKeys.ADD_MESSAGE, ActionKeys.REPLY_TO_MESSAGE, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Category
		MEMBER_ROLE_PERMISSIONS.put(MBCategory.class.getName(), [] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Thread
		MEMBER_ROLE_PERMISSIONS.put(MBThread.class.getName(), [ ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Message Boards -> Resource
		// Permissions -> Messages Boards Message
		MEMBER_ROLE_PERMISSIONS.put(MBMessage.class.getName(), [ ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> General Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.WIKI_ADMIN, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Nodes
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.wiki", [ ActionKeys.ADD_NODE ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Node
		MEMBER_ROLE_PERMISSIONS.put(WikiNode.class.getName(), [ ActionKeys.ADD_ATTACHMENT,
				ActionKeys.ADD_PAGE, ActionKeys.DELETE, ActionKeys.IMPORT, ActionKeys.SUBSCRIBE, ActionKeys.UPDATE,
				ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Wiki -> Resource Permissions
		// -> Wiki Page
		MEMBER_ROLE_PERMISSIONS.put(WikiPage.class.getName(), [ ActionKeys.ADD_DISCUSSION,
				ActionKeys.DELETE, ActionKeys.SUBSCRIBE,
				ActionKeys.UPDATE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Bookmarks -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.BOOKMARKS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Bookmarks -> Resource
		// Permissions -> Bookmark Entries
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.bookmarks", [ ActionKeys.ADD_ENTRY,
				ActionKeys.ADD_FOLDER, ActionKeys.SUBSCRIBE, ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Polls -> General Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.POLLS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Questions
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.polls", [] as String[]);
		// Workspace Administration -> Content -> Polls -> Resource Permissions
		// -> Poll Question
		MEMBER_ROLE_PERMISSIONS.put(PollsQuestion.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Tags -> General Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.TAGS_ADMIN, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Asset Entries
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.asset", [] as String[]);
		// Workspace Administration -> Content -> Tags -> Resource Permissions
		// -> Tag
		MEMBER_ROLE_PERMISSIONS.put(AssetTag.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_ADMIN, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category Vocabulary
		MEMBER_ROLE_PERMISSIONS.put(AssetVocabulary.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Content -> Categories -> Resource
		// Permissions -> Category
		MEMBER_ROLE_PERMISSIONS.put(AssetCategory.class.getName(), [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Users -> Site Memberships -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.SITE_MEMBERSHIPS_ADMIN, [] as String[]);
		// Workspace Administration -> Users -> Site Memberships -> Resource
		// Permissions -> Team
		MEMBER_ROLE_PERMISSIONS.put(Team.class.getName(), [] as String[]);
		// Workspace Administration -> Users -> Site Teams -> General
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put("191", [] as String[]);
		// Workspace Administration -> Applications -> Activities -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put("upactivities_WAR_upactivitiesportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.ANNOUNCEMENTS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Announcements ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.portlet.announcements.model.AnnouncementsEntry",
				[ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Asset Publisher ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.ASSET_PUBLISHER, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Blogs -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.BLOGS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Categories Navigation ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.ASSET_CATEGORIES_NAVIGATION, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Comments and Feedback ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.PAGE_COMMENTS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Documents and Media
		// Display -> Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.DOCUMENT_LIBRARY_DISPLAY, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Events Display ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("1_WAR_eventsdisplayportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Media Gallery ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.MEDIA_GALLERY_DISPLAY, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Message Boards ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.MESSAGE_BOARDS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put("1_WAR_microblogsportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.microblogs.model.MicroblogsEntry", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Microblogs -> Resource
		// Permissions -> Microblogs Entry
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.microblogs", [] as String[]);
		// Workspace Administration -> Applications -> Microblogs Status Update
		// -> Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("2_WAR_microblogsportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Polls Display
		// -> Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.POLLS_DISPLAY, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Quick Notest
		// -> Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("97", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> RSS -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.RSS, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Social Office
		// Announcements -> Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("1_WAR_soannouncementsportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tag Cloud -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.TAGS_CLOUD, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tags Navigation ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.ASSET_TAGS_NAVIGATION, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put("1_WAR_tasksportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.tasks", [] as String[]);
		// Workspace Administration -> Applications -> Tasks -> Resource
		// Permissions -> Tasks Entry
		MEMBER_ROLE_PERMISSIONS.put("com.liferay.tasks.model.TasksEntry", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> WYSIWYG -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put("1_WAR_wysiwygportlet", [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content Display ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content List ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_LIST, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Web Content Search ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.JOURNAL_CONTENT_SEARCH, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Wiki -> Application
		// Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.WIKI, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Wiki Display ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put(PortletKeys.WIKI_DISPLAY, [ ActionKeys.VIEW ] as String[]);
		// Workspace Administration -> Applications -> Workspace Description ->
		// Application Permissions
		MEMBER_ROLE_PERMISSIONS.put("workspacedescription_WAR_workspacedescriptionportlet",
				[ ActionKeys.VIEW ] as String[]);

		// names of the roles permissions will be removed from
		String[] roleNamesForPermissionDeletion = [ RoleConstants.USER, RoleConstants.POWER_USER,
				RoleConstants.GUEST ] as String[];
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
				PortletKeys.SITE_MEMBERS_DIRECTORY, "mycustompages_WAR_custompagesportlet",
				"othercustompages_WAR_custompagesportlet" ] as String[];
		// action ids for permission deletion
		String[] actionIdsForPermissionDeletion = [ ActionKeys.ADD_TO_PAGE ] as String[];

		// names of the roles permissions will be added to
		String[] roleNamesForPermissionAdditionCompany = [ "User" ] as String[];
		// name ids for permission addition
		String[] namesForPermissionAdditionCompany = [ "com.liferay.portlet.documentlibrary" ] as String[];
		// action ids for permission addition
		String[] actionIdsForPermissionAdditionCompany = [ ActionKeys.VIEW, ActionKeys.ADD_REPOSITORY ] as String[];

		// names of the roles permissions will be added to
		String[] roleNamesForPermissionAdditionPersonal = [ "User" ] as String[];
		// name ids for permission addition
		String[] namesForPermissionAdditionPersonal = [ "mycustompages_WAR_custompagesportlet",
				"othercustompages_WAR_custompagesportlet" ] as String[];
		// action ids for permission addition
		String[] actionIdsForPermissionAdditionPersonal = [ ActionKeys.ADD_TO_PAGE ] as String[];

		try {

			// needed to allow adding of portlets to a layout
			initPermissionChecker();

			// Make predefined pages in users personal area uneditable
			setLayoutSetPrototypeUneditable(0, USER_HOME_LAYOUT_SET_PROTOTYPE_NAME);

			// Make predefined pages in users profile uneditable
			setLayoutSetPrototypeUneditable(0, USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME);

			// Create or update group work site template
			createLayoutSetPrototypeIfNeededAndSetProperties(
					GROUP_WORK_SITE_TEMPLATE_ID, GROUP_WORK_SITE_TEMPLATE_NAME_MAP,
					GROUP_WORK_SITE_TEMPLATE_DESCRIPTION, true);
			createLayoutSetPrototypeLayouts(GROUP_WORK_SITE_TEMPLATE_ID,
					GROUP_WORK_SITE_TEMPLATE_NAME_MAP.get(Locale.US), GROUP_WORK_LAYOUTS);
			setArticleOfJournalContentPortlet(GROUP_WORK_SITE_TEMPLATE_ID,
					GROUP_WORK_SITE_TEMPLATE_NAME_MAP.get(Locale.US), true, "Overview",
					WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_TITLE_MAP, WORKSPACES_OVERVIEW_JOURNAL_ARTICLE_CONTENT);
			setLinkToLayoutId(GROUP_WORK_SITE_TEMPLATE_ID, GROUP_WORK_SITE_TEMPLATE_NAME_MAP.get(Locale.US),
					GROUP_WORK_TOOLS_LAYOUT_NAME_MAP.get(Locale.US), true,
					GROUP_WORK_FORUM_LAYOUT_NAME_MAP.get(Locale.US));

			// Create or update public site area template
			createLayoutSetPrototypeIfNeededAndSetProperties(PUBLIC_SITE_AREA_TEMPLATE_ID,
					PUBLIC_AREA_SITE_TEMPLATE_NAME_MAP, PUBLIC_AREA_SITE_TEMPLATE_DESCRIPTION, false);
			createLayoutSetPrototypeLayouts(PUBLIC_SITE_AREA_TEMPLATE_ID,
					PUBLIC_AREA_SITE_TEMPLATE_NAME_MAP.get(Locale.US), PUBLIC_AREA_SITE_LAYOUTS);

			// Create or update empty site template
			createLayoutSetPrototypeIfNeededAndSetProperties(EMPTY_SITE_TEMPLATE_ID, EMPTY_SITE_TEMPLATE_NAME_MAP,
					EMPTY_SITE_TEMPLATE_DESCRIPTION, true);
			createLayoutSetPrototypeLayouts(EMPTY_SITE_TEMPLATE_ID, EMPTY_SITE_TEMPLATE_NAME_MAP.get(Locale.US),
					EMPTY_SITE_TEMPLATE_LAYOUTS);

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
			Role editorRole = createOrUpdateRole(EDITOR_ROLE_TITLE_MAP, EDITOR_ROLE_DESCRIPTION_MAP, RoleConstants.TYPE_SITE);
			setRolePermissions(editorRole, EDITOR_ROLE_PERMISSIONS, ResourceConstants.SCOPE_GROUP_TEMPLATE);
			
			// Create or update member role and set permissions
			Role memberRole = createOrUpdateRole(MEMBER_ROLE_TITLE_MAP, MEMBER_ROLE_DESCRIPTION_MAP, RoleConstants.TYPE_SITE);
			setRolePermissions(memberRole, MEMBER_ROLE_PERMISSIONS, ResourceConstants.SCOPE_GROUP_TEMPLATE);

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
	void setLayoutSetPrototypeUneditable(long layoutSetPrototypeId, String layoutSetPrototypeName) throws Exception {
		out.println("Try to make layouts of the LayoutSetPrototype " + layoutSetPrototypeName + " uneditable");

		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototype(layoutSetPrototypeId, layoutSetPrototypeName);

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
	LayoutSetPrototype createLayoutSetPrototypeIfNeededAndSetProperties(long layoutSetPrototypeId,
			Map<Locale, String> layoutSetPrototypeNameMap, String layoutSetPrototypeDescription, boolean active)
			throws Exception {
		out.println("Try to set properties of the LayoutSetPrototype with the id " + layoutSetPrototypeId
				+ " or with the name " + layoutSetPrototypeNameMap.get(Locale.US));
		LayoutSetPrototype layoutSetPrototype = getOrCreateLayoutSetPrototype(layoutSetPrototypeId,
				layoutSetPrototypeNameMap, layoutSetPrototypeDescription, new ServiceContext());

		if (layoutSetPrototype != null) {
			layoutSetPrototype.setNameMap(layoutSetPrototypeNameMap);
			layoutSetPrototype.setDescription(layoutSetPrototypeDescription);
			layoutSetPrototype.setActive(active);
			UnicodeProperties settings = layoutSetPrototype.getSettingsProperties();
			settings.setProperty("customJspServletContextName", "so-hook");
			layoutSetPrototype.setSettingsProperties(settings);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set properties of the LayoutSetPrototype with the id "
					+ layoutSetPrototype.getLayoutSetPrototypeId());
		}

		Role userRole = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), RoleConstants.USER);
		ResourcePermissionLocalServiceUtil.setResourcePermissions(PortalUtil.getDefaultCompanyId(),
				LayoutSetPrototype.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layoutSetPrototype.getLayoutSetPrototypeId()), userRole.getRoleId(),
				[ ActionKeys.VIEW ] as String[]);

		UnicodeProperties settings = layoutSetPrototype.getSettingsProperties();
		settings.setProperty("customJspServletContextName", "so-hook");

		out.println();
		return layoutSetPrototype;
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
	 * Tries to find a layout prototype with the given id or name.
	 * 
	 * @param layoutPrototypeId
	 *            id of the layout prototype
	 * @param layoutPrototypeName
	 *            name of the layout prototype
	 * @return layout prototype if existent, else null
	 * @throws Exception
	 */
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

	/**
	 * Returns the layout set prototype with the given id and name. Prints
	 * corresponding messages.
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param layoutSetPrototypeName
	 *            name of the layout set prototype
	 * @return layout set prototype with the given id and name
	 * @throws Exception
	 */
	LayoutSetPrototype getLayoutSetPrototype(long layoutSetPrototypeId, String layoutSetPrototypeName) throws Exception {
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototypeByLayoutSetPrototypeIdOrName(layoutSetPrototypeId,
				layoutSetPrototypeName);
		if (layoutSetPrototype == null) {
			out.println("WARNING: could not find LayoutSetPrototype with the id " + layoutSetPrototypeId
					+ " or with the name " + layoutSetPrototypeName);
		} else {
			out.println("Found LayoutSetPrototype with the name " + layoutSetPrototype.getName(Locale.US)
					+ " and the id " + layoutSetPrototype.getLayoutSetPrototypeId());
		}
		return layoutSetPrototype;
	}

	/**
	 * Returns the layout set with the given english name. If not found it will
	 * be created with the given properties.
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param nameMap
	 *            localized names of the layout set prototype
	 * @param description
	 *            description of the layout set prototype
	 * @param serviceContext
	 *            service Context containing additional properties
	 * @return existing or new layout set prototype
	 * @throws Exception
	 */
	LayoutSetPrototype getOrCreateLayoutSetPrototype(long layoutSetPrototypeId, Map<Locale, String> nameMap,
			String description, ServiceContext serviceContext) throws Exception {
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototypeByLayoutSetPrototypeIdOrName(layoutSetPrototypeId,
				nameMap.get(Locale.US));

		if (layoutSetPrototype == null) {
			out.println("Could not find LayoutSetPrototype with the id " + layoutSetPrototypeId + " or with the name "
					+ nameMap.get(Locale.US));
			layoutSetPrototype = LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
					UserLocalServiceUtil.getDefaultUserId(PortalUtil.getDefaultCompanyId()),
					PortalUtil.getDefaultCompanyId(), nameMap, description, true, true, serviceContext);
			out.println("The LayoutSetPrototype with the name " + nameMap.get(Locale.US) + " was created");
		} else {
			out.println("Found LayoutSetPrototype with the name " + layoutSetPrototype.getName(Locale.US)
					+ " and the id " + layoutSetPrototype.getLayoutSetPrototypeId());
		}
		return layoutSetPrototype;
	}

	/**
	 * Returns the layout set prototype with the given id and name.
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param layoutSetPrototypeName
	 *            name of the layout set prototype
	 * @return layout set prototype with the given id and name
	 * @throws Exception
	 */
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
		}
		return layoutSetPrototype;
	}

	/**
	 * Creates the layouts with the given properties as children of the given
	 * layout set prototype
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param layoutSetPrototypeName
	 *            name of the layout set prototype
	 * @param layouts
	 *            layouts with the corresponding properties
	 * @throws Exception
	 */
	void createLayoutSetPrototypeLayouts(long layoutSetPrototypeId, String layoutSetPrototypeName, Object[][] layouts)
			throws Exception {
		out.println("Try to create or update layouts of the LayoutSetPrototype " + layoutSetPrototypeName);
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototype(layoutSetPrototypeId, layoutSetPrototypeName);
		if (layoutSetPrototype != null) {
			List<String> layoutNames = new ArrayList<String>();
			for (int i = 0; i < layouts.length; i++) {
				createOrUpdateLayout(layoutSetPrototype.getGroupId(), ((Boolean) layouts[i][1]).booleanValue(),
						(String) layouts[i][3], (String) layouts[i][2], (Map<Locale, String>) layouts[i][0],
						(Map<String, String[]>) layouts[i][4], i);
				layoutNames.add(((Map<Locale, String>) layouts[i][0]).get(Locale.US));
			}
			List<Layout> currentLayouts = LayoutLocalServiceUtil.getLayouts(layoutSetPrototype.getGroupId(), true);
			for (Layout layout : currentLayouts) {
				if (!layoutNames.contains(layout.getName(Locale.US))) {
					LayoutLocalServiceUtil.deleteLayout(layout);
					out.println("Removed Layout + " + layout.getName(Locale.US));
				}
			}
			return;
		}
		out.println("Layouts of the LayoutSetPrototype " + layoutSetPrototypeName + " updated or created.");
	}

	/**
	 * Creates or updates a existing layout
	 * 
	 * @param groupId
	 * @param privateLayout
	 * @param layoutTemplateId
	 * @param parentLayoutName
	 * @param nameMap
	 * @param columnPortletMap
	 * @param priority
	 * @throws Exception
	 */
	void createOrUpdateLayout(long groupId, boolean privateLayout, String layoutTemplateId, String parentLayoutName,
			Map<Locale, String> nameMap, Map<String, String[]> columnPortletMap, int priority) throws Exception {
		out.println("Try to create or update layout " + nameMap.get(Locale.US));
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(PortalUtil.getDefaultCompanyId());
		Layout layout = getLayout(nameMap.get(Locale.US), privateLayout, groupId);

		if (layout == null) {
			out.println("Layout " + nameMap.get(Locale.US) + " not found.");
			long parentLayoutId = 0;
			if (parentLayoutName != null) {
				Layout parentLayout = getLayout(parentLayoutName, privateLayout, groupId);
				if (parentLayout != null)
					parentLayoutId = parentLayout.getLayoutId();
			}

			layout = LayoutLocalServiceUtil.addLayout(defaultUserId, groupId, privateLayout, parentLayoutId, nameMap,
					nameMap, null, null, null, LayoutConstants.TYPE_PORTLET, "layout-template-id=" + layoutTemplateId
							+ " privateLayout=" + privateLayout, false, new HashMap<Locale, String>(),
					new ServiceContext());
			out.println("Layout " + nameMap.get(Locale.US) + " created.");
		}

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
		layoutTypePortlet.setLayoutTemplateId(defaultUserId, layoutTemplateId);

		// Add new portlets and record order
		List<String> currentPortletIds = layoutTypePortlet.getPortletIds();
		Map<String, List<String>> orderedPortletIdsMap = new HashMap<String, List<String>>();
		for (Map.Entry<String, String[]> columnPortletEntry : columnPortletMap.entrySet()) {
			List<String> orderedPortletIds = new ArrayList<String>();
			for (String portlet : columnPortletEntry.getValue()) {
				String instancePortletId = removePortletIdFromList(portlet, currentPortletIds);
				if (instancePortletId == null) {
					instancePortletId = layoutTypePortlet.addPortletId(getAdmin().getUserId(), portlet,
							columnPortletEntry.getKey(), 0);
					System.out.println("Added portlet " + portlet + "to the layout " + nameMap.get(Locale.US));
				}
				orderedPortletIds.add(instancePortletId);
			}
			orderedPortletIdsMap.put(columnPortletEntry.getKey(), orderedPortletIds);
		}

		// Remove portlets not in new portlet list
		for (String currentPortletId : currentPortletIds) {
			layoutTypePortlet.removePortletId(getAdmin().getUserId(), currentPortletId);
			out.println("Portlet " + currentPortletId + " removed from layout " + nameMap.get(Locale.US));
		}

		// Set (new) order of the portlets
		for (Map.Entry<String, List<String>> orderedPortletIdsEntry : orderedPortletIdsMap.entrySet()) {
			layoutTypePortlet.setTypeSettingsProperty(orderedPortletIdsEntry.getKey(), orderedPortletIdsEntry
					.getValue().toString().replace("]", "").replace("[", "").replace(" ", ""));
		}

		layout.setPriority(priority);
		LayoutLocalServiceUtil.updateLayout(layout);
		out.println("Layout " + nameMap.get(Locale.US) + " created or updated");
	}

	/**
	 * Sets the article of the journal content portlet placed on the given
	 * layout set prototype
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param layoutSetPrototypeName
	 *            name of the layout set prototype
	 * @param privateLayout
	 *            true if the layout the portlet is placed on is private
	 * @param layoutName
	 *            name of the layout the portlet is placed on
	 * @param articleTitleMap
	 *            localized titles of the article
	 * @param articleContent
	 *            content of the article
	 * @throws Exception
	 */
	void setArticleOfJournalContentPortlet(long layoutSetPrototypeId, String layoutSetPrototypeName,
			boolean privateLayout, String layoutName, Map<Locale, String> articleTitleMap, String articleContent)
			throws Exception {
		out.println("Try to set article of the journal content portlet on the layout " + layoutName);
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototype(layoutSetPrototypeId, layoutSetPrototypeName);
		if (layoutSetPrototype == null)
			return;

		Layout layoutWithJournalPortlet = getLayout(layoutName, privateLayout, layoutSetPrototype.getGroupId());
		if (layoutWithJournalPortlet == null) {
			out.println("WARNING: could not find Layout with the name " + layoutName);
			return;
		}
		out.println("Found layout with the name " + layoutName);

		String portletId = removePortletIdFromList(PortletKeys.JOURNAL_CONTENT,
				((LayoutTypePortlet) layoutWithJournalPortlet.getLayoutType()).getPortletIds());
		PortletPreferences portletPreferences = PortletPreferencesLocalServiceUtil.fetchPreferences(
				layoutWithJournalPortlet.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layoutWithJournalPortlet.getPlid(), portletId);

		if (portletPreferences == null) {
			out.println("WARNING: there is no journal content portlet belonging to the layout with the name "
					+ layoutName);
			return;
		}
		out.println("Found content portlet belonging to the layout with the name " + layoutName);

		String oldArticleId = portletPreferences.getValue("articleId", null);
		if (oldArticleId != null) {
			JournalArticle oldArticle = JournalArticleLocalServiceUtil.fetchLatestArticle(
					layoutSetPrototype.getGroupId(), oldArticleId, 0);
			if (oldArticle != null) {
				oldArticle.setTitleMap(articleTitleMap);
				oldArticle.setContent(articleContent);
				JournalArticleLocalServiceUtil.updateJournalArticle(oldArticle);
				out.println("Updated article with the id " + oldArticle.getArticleId());
				out.println();
				return;
			}
		}
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(layoutSetPrototype.getGroupId());
		JournalArticle journalArticle = JournalArticleLocalServiceUtil.addArticle(getAdmin().getUserId(),
				layoutSetPrototype.getGroupId(), 0, articleTitleMap, null, articleContent, "", "", serviceContext);

		portletPreferences.setValue("articleId", journalArticle.getArticleId());
		portletPreferences.setValue("groupId", String.valueOf(layoutSetPrototype.getGroupId()));
		PortletPreferencesLocalServiceUtil.updatePreferences(PortletKeys.PREFS_OWNER_ID_DEFAULT,
				PortletKeys.PREFS_OWNER_TYPE_LAYOUT, layoutWithJournalPortlet.getPlid(), portletId, portletPreferences);
		out.println("Set article with the id " + journalArticle.getArticleId());
		out.println();
	}

	/**
	 * Removes the given portlet id from the list. Ignores instance portlet ids.
	 * 
	 * @param portletId
	 *            portlet id wich has to be removed
	 * @param portletIdList
	 *            list of portlet ids
	 * @return
	 */
	String removePortletIdFromList(String portletId, List<String> portletIdList) {
		String idToRemove = null;
		for (String id : portletIdList) {
			if (id.replaceFirst("_INSTANCE_.*", "").equals(portletId)) {
				idToRemove = id;
				break;
			}
		}
		if (idToRemove != null)
			portletIdList.remove(idToRemove);
		return idToRemove;
	}

	/**
	 * Links a layout to an other layout.
	 * 
	 * @param layoutSetPrototypeId
	 *            id of the layout set prototype
	 * @param layoutSetPrototypeName
	 *            name of the layout set prototype
	 * @param layoutName
	 *            name of the linking layout
	 * @param privateLayout
	 *            true if the linking layout is private
	 * @param linkedLayoutName
	 *            name of the linked layout
	 * @throws Exception
	 */
	void setLinkToLayoutId(long layoutSetPrototypeId, String layoutSetPrototypeName, String layoutName,
			boolean privateLayout, String linkedLayoutName) throws Exception {
		out.println("Try to link the layout " + layoutName + " to " + linkedLayoutName);
		LayoutSetPrototype layoutSetPrototype = getLayoutSetPrototype(layoutSetPrototypeId, layoutSetPrototypeName);
		if (layoutSetPrototype != null) {
			Layout layout = getLayout(layoutName, privateLayout, layoutSetPrototype.getGroupId());

			if (layout != null) {
				Layout linkedLayout = getLayout(linkedLayoutName, privateLayout, layoutSetPrototype.getGroupId());
				if (linkedLayout != null) {
					LayoutType layoutType = (LayoutType) layout.getLayoutType();
					layout.setType(LayoutConstants.TYPE_LINK_TO_LAYOUT);
					layoutType.setTypeSettingsProperty("linkToLayoutId", String.valueOf(linkedLayout.getLayoutId()));
					LayoutLocalServiceUtil.updateLayout(layout);
					out.println("Linked the layout " + layoutName + " to " + linkedLayoutName);
					out.println();
					return;
				}
				out.println("WARNING: linked layout " + linkedLayoutName + "not found!");
			}
			out.println("WARNING: linking layout " + layoutName + "not found!");
		}
		out.println();
	}

	/**
	 * Returns the layout with the given properties if existent
	 * 
	 * @param name
	 *            name of the layout
	 * @param private Layout true if the layout is private
	 * @param groupId
	 *            group id of the layout
	 * @return layout if existent, else null
	 * @throws Exception
	 */
	Layout getLayout(String name, boolean privateLayout, long groupId) throws Exception {
		List<Layout> currentLayouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		for (Layout currentLayout : currentLayouts) {
			if (currentLayout.getName(Locale.US).equals(name)) {
				return currentLayout;
			}
		}
		return null;
	}

	/**
	 * Initializes the permission checker with the admin user
	 * 
	 * @throws Exception
	 */
	void initPermissionChecker() throws Exception {
		PrincipalThreadLocal.setName(getAdmin().getUserId());
		PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(getAdmin());
		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	/**
	 * Returns the first admin user found.
	 * 
	 * @return admin user
	 * @throws Exception
	 */
	User getAdmin() throws Exception {
		final long companyId = PortalUtil.getDefaultCompanyId();
		Role role = null;
		role = RoleLocalServiceUtil.getRole(companyId, RoleConstants.ADMINISTRATOR);
		for (final User admin : UserLocalServiceUtil.getRoleUsers(role.getRoleId())) {
			return admin;
		}
		return null;
	}
	