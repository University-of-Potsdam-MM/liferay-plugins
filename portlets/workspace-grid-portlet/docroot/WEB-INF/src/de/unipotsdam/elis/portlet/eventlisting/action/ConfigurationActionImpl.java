package de.unipotsdam.elis.portlet.eventlisting.action;


import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.Constants;
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
		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals("saveColors"))
			saveColors(portletConfig, actionRequest, actionResponse);
		else if (cmd.equals("resetColors"))
			resetColors(portletConfig, actionRequest, actionResponse);
		
        super.processAction(portletConfig, actionRequest, actionResponse);
    }
    
    private void saveColors(PortletConfig portletConfig, ActionRequest actionRequest,
            ActionResponse actionResponse) throws Exception{
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletPreferences portletPreferences = actionRequest.getPreferences();
		
		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, 
				ParamUtil.get(actionRequest, WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, WorkspaceGridPortlet.DEFAULT_COLOR));
    	
    	for (LayoutSetPrototype prototype : LayoutSetPrototypeServiceUtil.search(themeDisplay.getCompanyId(), true, null)){
    		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), 
    				ParamUtil.get(actionRequest, WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), WorkspaceGridPortlet.DEFAULT_COLOR));
    	}
    	
    	portletPreferences.setValue(WorkspaceGridPortlet.NUMBER_OF_VISIBLE_WORKSPACES, ParamUtil.get(actionRequest, WorkspaceGridPortlet.NUMBER_OF_VISIBLE_WORKSPACES, "10"));
    	
    	portletPreferences.store();
    }

    
    private void resetColors(PortletConfig portletConfig, ActionRequest actionRequest,
            ActionResponse actionResponse) throws Exception{
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletPreferences portletPreferences = actionRequest.getPreferences();
		
		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, WorkspaceGridPortlet.INIT_COLORS[0]);
		List<LayoutSetPrototype> prototypes = LayoutSetPrototypeServiceUtil.search(themeDisplay.getCompanyId(),
				true, null);
    	for (int i = 0; i < prototypes.size(); i++){
    		portletPreferences.setValue(WorkspaceGridPortlet.WORKSPACE_COLOR + prototypes.get(i).getUuid(), WorkspaceGridPortlet.INIT_COLORS[(i + 1) % (WorkspaceGridPortlet.INIT_COLORS.length-1)]);
    	}
    	
    	portletPreferences.store();
    }
}
