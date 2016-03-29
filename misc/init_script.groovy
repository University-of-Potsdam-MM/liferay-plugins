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


// Make pages in users personal area uneditable
setLayoutSetPrototypeUneditable(USER_HOME_LAYOUT_SET_PROTOTYPE_NAME);

// Make pages in users profile uneditable
setLayoutSetPrototypeUneditable(USER_PROFILE_LAYOUT_SET_PROTOTYPE_NAME);


/**
* Makes LayoutSets inheriting from the given LayoutSetPrototype and the
* corresponding Layouts uneditable by the user
* 
* @param layoutSetPrototypeName
*            Name of the LayoutSetPrototype
*/
void setLayoutSetPrototypeUneditable(String layoutSetPrototypeName) throws Exception {
  List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeLocalServiceUtil
  .getLayoutSetPrototypes(PortalUtil.getDefaultCompanyId());
  
  // Make pages in users personal area not editable
  for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
    if (layoutSetPrototype.getName(Locale.ENGLISH).equals(layoutSetPrototypeName)) {
      out.println("Found User Home LayoutSetPrototype " + layoutSetPrototypeName + ". layoutSetPrototypeId: "
                  + layoutSetPrototype.getLayoutSetPrototypeId());
      
      UnicodeProperties properties = layoutSetPrototype.getSettingsProperties();
      properties.setProperty("layoutsUpdateable", "false");
      layoutSetPrototype.setSettingsProperties(properties);
      LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(layoutSetPrototype);
      out.println("Set property layoutsUpdateable of the LayoutSetPrototype " + layoutSetPrototypeName
                  + " to false");
      
      List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(layoutSetPrototype.getGroupId(), true);
      for (Layout layout : layouts) {
        layout.getLayoutType().setTypeSettingsProperty("layoutUpdateable", "false");
        LayoutLocalServiceUtil.updateLayout(layout);
        out.println("Set property layoutUpdateable of the layout " + layout.getName(Locale.ENGLISH)
                    + " to false");
      }
      out.println();
      break;
    }
  }
}