package de.unipotsdam.elis.activities.configuration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import de.unipotsdam.elis.activities.ActivitiesPortlet;

public class ConfigurationActionImpl extends DefaultConfigurationAction {
	
	
	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {

		PortletPreferences portletPreferences = actionRequest.getPreferences();
		boolean oneIsSet = false;
		for (String tabName : ActivitiesPortlet.ACTIVITY_TABS){
			String value = ParamUtil.getString(actionRequest, tabName);
			portletPreferences.setValue(tabName, value);
			oneIsSet = oneIsSet || value.equals("true");
		}
		if (oneIsSet)
		portletPreferences.store();
		else
			SessionErrors.add(actionRequest, "at-least-one-activity-needs-to-be-selected");
			
		
		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}
