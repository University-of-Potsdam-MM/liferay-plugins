package de.unipotsdam.elis.owncloud.repository;

import java.net.MalformedURLException;
import java.net.URL;

import javax.portlet.PortletPreferences;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.portlet.PortletProps;

public class OwncloudConfigurationLoader {

	private static final String _ownCloudWebdavAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/remote.php/webdav";
	private static final String _ownCloudDownloadAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/index.php/apps/files/ajax/download.php";
	private static final String _ownCloudShareAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/ocs/v1.php/apps/files_sharing/api/v1/shares";
	private static final String _ownCloudRootFolder = "Campus.UP";
	private static final String _ownCloudRootUsername = "campus.up";
	private static final String _ownCloudRootPassword = "test";
	private static final String _ownCloudRepositoryName = "Box.UP";
	private static final String _imageUploadFolder = "Uploads";

	public static final String getOwnCloudWebdavAddress() {
		String result = determinePortletPreferencesValue("owncloud.webdav.adress");
		return (result != null ? result : _ownCloudWebdavAddress);
	}

	public static final String getOwncloudDownloadAddress() {
		String result = determinePortletPreferencesValue("owncloud.download.adress");
		return (result != null ? result : _ownCloudDownloadAddress);
	}

	public static final String getOwncloudShareAddress() {
		String result = determinePortletPreferencesValue("owncloud.share.adress");
		return (result != null ? result : _ownCloudShareAddress);
	}

	public static final String getRootFolder() {
		String result = determinePortletPreferencesValue("owncloud.root.folder");
		return (result != null ? result : _ownCloudRootFolder);
	}

	public static final String getRootUsername() {
		String result = determinePortletPreferencesValue("owncloud.root.username");
		return (result != null ? result : _ownCloudRootUsername);
	}

	public static final String getRootPassword() {
		String result = determinePortletPreferencesValue("owncloud.root.password");
		return (result != null ? result : _ownCloudRootPassword);
	}

	public static final String getRepositoryName() {
		String result = determinePortletPreferencesValue("owncloud.repository.name");
		return (result != null ? result : _ownCloudRepositoryName);
	}

	public static final String getImageUploadFolder() {
		String result = determinePortletPreferencesValue("imageupload.folder");
		return (result != null ? result : _imageUploadFolder);
	}

	public static final String getEndpointPath() {
		try {
			return new URL(getOwnCloudWebdavAddress()).getPath();
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}

	private static String determinePortletPreferencesValue(String key) {

		try {
			PortletPreferences prefs = PortletPreferencesFactoryUtil.getLayoutPortletSetup(
					LayoutLocalServiceUtil.getLayout(PortalUtil.getControlPanelPlid(PortalUtil.getDefaultCompanyId())),
					"owncloud_WAR_owncloudportlet");
			return prefs.getValue(key, PortletProps.get(key));
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		return PortletProps.get(key);
	}
}
