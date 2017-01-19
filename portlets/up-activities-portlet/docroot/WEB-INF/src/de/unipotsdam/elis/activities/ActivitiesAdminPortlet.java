package de.unipotsdam.elis.activities;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.portlet.PortletProps;

/**
 * Portlet implementation class ActivitiesAdminPortlet
 */
public class ActivitiesAdminPortlet extends MVCPortlet {
 
	private static final String _moodleServiceEndpoint = "http://localhost";

	@Override
	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		
		try {
			PortletPreferences prefs = PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					LayoutLocalServiceUtil.getLayout(PortalUtil.getControlPanelPlid(PortalUtil.getDefaultCompanyId())),
					"upactivities_WAR_upactivitiesportlet"); 
			
			String param = ParamUtil.getString(actionRequest, "moodleServiceEndpoint");
			if (!param.equals(StringPool.BLANK))
				prefs.setValue("moodle.service.endpoint", param);
		
			prefs.store();
		} catch (Exception e) {
			
		}
		
		super.processAction(actionRequest, actionResponse);
	}
	
	public static final String getMoodleServiceEndpoint () {
		String result = determinePortletPreferencesValue("moodle.service.endpoint");
		return (result != null ? result : _moodleServiceEndpoint);
	}
	
	
	private static String determinePortletPreferencesValue(String key) {

		try {
			PortletPreferences prefs = PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					LayoutLocalServiceUtil.getLayout(PortalUtil.getControlPanelPlid(PortalUtil.getDefaultCompanyId())),
					"upactivities_WAR_upactivitiesportlet");
			return prefs.getValue(key, PortletProps.get(key));
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		return PortletProps.get(key);
	}
}
