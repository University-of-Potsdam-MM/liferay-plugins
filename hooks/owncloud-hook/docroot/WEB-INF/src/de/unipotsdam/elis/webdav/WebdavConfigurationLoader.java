package de.unipotsdam.elis.webdav;

import java.net.MalformedURLException;
import java.net.URL;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;
import com.liferay.util.portlet.PortletProps;

import de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager;

public class WebdavConfigurationLoader {

	private static final String _ownCloudWebdavAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/remote.php/webdav";
	private static final String _ownCloudDownloadAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/index.php/apps/files/ajax/download.php";
	private static final String _ownCloudShareAddress = "http://erdmaennchen.soft.cs.uni-potsdam.de/owncloud/ocs/v1.php/apps/files_sharing/api/v1/shares";
	private static final String _ownCloudRootFolder = "Campus.UP";
	private static final String _ownCloudRootUsername = "campus.up";
	private static final String _ownCloudRootPassword = "test";
	private static final String _ownCloudRepositoryName = "Box.UP";

	public static final String getOwnCloudWebdavAddress() {
		String result = PortletProps.get("owncloud.webdav.adress");
		return (result != null ? result : _ownCloudWebdavAddress);
	}

	public static final String getOwncloudDownloadAddress() {
		String result = PortletProps.get("owncloud.download.adress");
		return (result != null ? result : _ownCloudDownloadAddress);
	}

	public static final String getOwncloudShareAddress() {
		String result = PortletProps.get("owncloud.share.adress");
		return (result != null ? result : _ownCloudShareAddress);
	}

	public static final String getRootFolder() {
		String result = PortletProps.get("owncloud.root.folder");
		return (result != null ? result : _ownCloudRootFolder);
	}

	public static final String getRootUsername() {
		String result = PortletProps.get("owncloud.root.username");
		return (result != null ? result : _ownCloudRootUsername);
	}

	public static final String getRootPassword() {
		String result = PortletProps.get("owncloud.root.password");
		return (result != null ? result : _ownCloudRootPassword);
	}

	public static final String getRepositoryName() {
		String result = PortletProps.get("owncloud.repository.name");
		return (result != null ? result : _ownCloudRepositoryName);
	}

	public static final String getEndpointPath() {
		try {
			return new URL(getOwnCloudWebdavAddress()).getPath();
		} catch (MalformedURLException e) {
			throw new Error(e);
		}
	}
}
