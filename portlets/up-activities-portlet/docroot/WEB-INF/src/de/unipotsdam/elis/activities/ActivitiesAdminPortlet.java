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
 
	private static final String _moodleServiceEndpoint = "http://localhost",
			_moodleServiceUsername = "test",
			_moodleServicePassword = "test",
			_moodleServiceSchedulerActive = StringPool.FALSE;

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
		
			param = ParamUtil.getString(actionRequest, "moodleServiceUsername");
			if (!param.equals(StringPool.BLANK))
				prefs.setValue("moodle.service.username", param);
			
			param = ParamUtil.getString(actionRequest, "moodleServicePassword");
			if (!param.equals(StringPool.BLANK))
				prefs.setValue("moodle.service.password", param);
			
			param = ParamUtil.getString(actionRequest, "moodleSerivceSchedulerActive");
			if (!param.equals(StringPool.BLANK))
				prefs.setValue("moodle.service.schedulerActive", param);
			
			prefs.store();
		} catch (Exception e) {
			
		}
		
		super.processAction(actionRequest, actionResponse);
	}
	
	public static final String getMoodleServiceEndpoint () {
		String result = determinePortletPreferencesValue("moodle.service.endpoint");
		return (result != null ? result : _moodleServiceEndpoint);
	}
	
	public static final String getMoodleServiceUsername () {
		String result = determinePortletPreferencesValue("moodle.service.username");
		return (result != null ? result : _moodleServiceUsername);
	}
	
	public static final String getMoodleServicePassword () {
		String result = determinePortletPreferencesValue("moodle.service.password");
		return (result != null ? result : _moodleServicePassword);
	}
	
	public static final String getMoodleServiceSchedulerActive () {
		String result = determinePortletPreferencesValue("moodle.service.schedulerActive");
		return (result != null ? result : _moodleServiceSchedulerActive);
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
