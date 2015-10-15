package de.unipotsdam.elis.portlet.eventlisting.action;


import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import de.unipotsdam.elis.workspacegrid.WorkspaceGridPortlet;

public class ConfigurationActionImpl extends DefaultConfigurationAction {

    @Override
    public void processAction(
        PortletConfig portletConfig, ActionRequest actionRequest,
        ActionResponse actionResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletPreferences portletPreferences = actionRequest.getPreferences();
		
		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, 
				ParamUtil.get(actionRequest, WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, WorkspaceGridPortlet.DEFAULT_COLOR));
    	
    	for (LayoutSetPrototype prototype : LayoutSetPrototypeServiceUtil.search(themeDisplay.getCompanyId(), true, null)){
    		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), 
    				ParamUtil.get(actionRequest, WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), WorkspaceGridPortlet.DEFAULT_COLOR));
    	}
    	
    	portletPreferences.store();

        super.processAction(portletConfig, actionRequest, actionResponse);

        
    }
}
