import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;


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

} catch (Exception e) {
	e.printStackTrace();
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