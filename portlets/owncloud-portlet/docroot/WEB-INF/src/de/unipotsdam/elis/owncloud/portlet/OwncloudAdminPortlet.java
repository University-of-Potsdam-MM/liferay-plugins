package de.unipotsdam.elis.owncloud.portlet;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PreferencesValidator;

import org.apache.commons.lang.StringUtils;

import com.liferay.compat.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PrefsParamUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.UserConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudConfigurationLoader;

public class OwncloudAdminPortlet extends MVCPortlet {

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {

		try {
			PortletPreferences prefs = PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					LayoutLocalServiceUtil.getLayout(PortalUtil.getControlPanelPlid(PortalUtil.getDefaultCompanyId())),
					"owncloud_WAR_owncloudportlet");

			String param = ParamUtil.getString(actionRequest, "ownCloudWebdavAddress");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.webdav.adress", param);
			
			param = ParamUtil.getString(actionRequest, "ownCloudDownloadAddress");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.download.adress", param);
			
			param = ParamUtil.getString(actionRequest, "ownCloudShareAddress");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.share.adress", param);
			
			param = ParamUtil.getString(actionRequest, "ownCloudRootFolder");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.root.folder", param);
			
			param = ParamUtil.getString(actionRequest, "ownCloudRootUsername");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.root.username", param);
			
			param = ParamUtil.getString(actionRequest, "ownCloudRootPassword");
			if (StringUtils.isNotBlank(param))
				prefs.setValue("owncloud.root.password", param);
			
			prefs.store();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}

		super.processAction(actionRequest, actionResponse);
	}

}
