import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;


String USER_HOME_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Home";
String USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME = "Social Office User Profile";
String GROUP_WORK_WORKSPACE_NAME = "Group Work";
String GROUP_WORK_DESCRIPTION = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.";

try {

	// Make predefined pages in users personal area uneditable
	setLayoutSetPrototypeUneditable(USER_HOME_LAYOUT_SET_PROTOTYPE_NAME);

	// Make predefined pages in users profile uneditable
	setLayoutSetPrototypeUneditable(USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME);

	// Set description of the group work workspace
	setLayoutSetPrototypeDescription(GROUP_WORK_WORKSPACE_NAME, GROUP_WORK_DESCRIPTION);

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
 */
void setLayoutSetPrototypeUneditable(String layoutSetPrototypeName) throws Exception {
	List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeLocalServiceUtil
			.getLayoutSetPrototypes(PortalUtil.getDefaultCompanyId());

	out.println("Try to make layouts of the LayoutSetPrototype " + layoutSetPrototypeName + " uneditable");
	for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
		if (layoutSetPrototype.getName(Locale.ENGLISH).equals(layoutSetPrototypeName)) {
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
				out.println("Set property layoutUpdateable of the layout " + layout.getName(Locale.ENGLISH)
						+ " to false");
			}
			out.println();
			return;
		}
	}
	out.println("WARNING: could not find LayoutSetPrototype " + layoutSetPrototypeName);
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
		if (layoutSetPrototype.getName(Locale.ENGLISH).equals(layoutSetPrototypeName)) {
			out.println("Found LayoutSetPrototype " + layoutSetPrototypeName + ". layoutSetPrototypeId: "
					+ layoutSetPrototype.getLayoutSetPrototypeId());

			layoutSetPrototype.setDescription(layoutSetPrototypeDescription);
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
			out.println("Set description of the LayoutSetPrototype " + layoutSetPrototypeName + " to: "
					+ layoutSetPrototypeDescription);

			return;
		}
	}
	out.println("WARNING: could not find LayoutSetPrototype " + layoutSetPrototypeName);
}