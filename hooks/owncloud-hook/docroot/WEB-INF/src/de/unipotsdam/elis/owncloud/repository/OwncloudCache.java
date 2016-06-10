package de.unipotsdam.elis.owncloud.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.MultiVMPoolUtil;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.Company;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.util.Encryptor;

import de.unipotsdam.elis.webdav.WebdavModel;

public class OwncloudCache implements Cloneable {

	public static OwncloudCache getInstance() {
		return _owncloudThreadLocal.get();
	}

	@Override
	public OwncloudCache clone() {
		if (_log.isInfoEnabled()) {
			Thread currentThread = Thread.currentThread();
			_log.info("Create " + currentThread.getName());
		}

		try {
			return (OwncloudCache) super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	public WebdavModel getWebdavModel(String webdavFileId) {
		Map<String, WebdavModel> webdavModels = _getWebdavModels();

		WebdavModel googleDriveFile = webdavModels.get(webdavFileId);

		if (googleDriveFile != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Hit " + webdavFileId);
			}
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("Miss " + webdavFileId);
			}
		}

		return googleDriveFile;
	}

	public List<?> getWebdavFiles(String webdavFileId) {
		Map<String, List<?>> webdavFiles = _getWebdavFiles();

		List<?> googleDriveFile = webdavFiles.get(webdavFileId);

		if (googleDriveFile != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Hit " + webdavFileId);
			}
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("Miss " + webdavFileId);
			}
		}

		return googleDriveFile;
	}

	public static String getError() {
		String webdavError = (String)ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.SESSION, _errorCacheName).get(_errorCacheName);
		if (webdavError != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Hit Error");
			}
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("Miss Error");
			}
		}

		return webdavError;
	}

	public String getPassword() {
		String encryptedPassword = (String) MultiVMPoolUtil.getCache(_passwordCacheName).get(
				PrincipalThreadLocal.getUserId());
		if (encryptedPassword != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Hit Password for user with the id " + PrincipalThreadLocal.getUserId());
			}
			try {
				Company company = CompanyLocalServiceUtil.getCompany(PortalUtil.getDefaultCompanyId());
				String decryptedPassword = Encryptor.decrypt(company.getKeyObj(), encryptedPassword);
				return decryptedPassword;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			if (_log.isDebugEnabled()) {
				_log.debug("Miss Password for user with the id " + PrincipalThreadLocal.getUserId());
			}
		}
		return null;
	}

	public void putWebdavModel(WebdavModel webdavModel) {
		Map<String, WebdavModel> webdavFiles = _getWebdavModels();

		String webdavFileId = webdavModel.getExtRepositoryModelKey();

		if (_log.isInfoEnabled()) {
			_log.info("Put " + webdavFileId);
		}

		webdavFiles.put(webdavFileId, webdavModel);
	}

	public void putWebdavFiles(List<?> webdavModel, String webdavFolderId) {
		Map<String, List<?>> webdavFiles = _getWebdavFiles();

		if (_log.isInfoEnabled()) {
			_log.info("Put " + webdavFolderId);
		}

		webdavFiles.put(webdavFolderId, webdavModel);
	}

	public static void putWebdavError(String webdavError) {

		if (_log.isInfoEnabled()) {
			_log.info("Put " + webdavError);
		}

		ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.SESSION, _errorCacheName).put(_errorCacheName, webdavError);
	}

	public void putPassword(String password) {
		if (_log.isInfoEnabled()) {
			_log.info("Put password of user with the " + PrincipalThreadLocal.getUserId());
		}

		try {
			Company company = CompanyLocalServiceUtil.getCompany(PortalUtil.getDefaultCompanyId());
			String encryptedPassword = Encryptor.encrypt(company.getKeyObj(), password);
			MultiVMPoolUtil.getCache(_passwordCacheName).put(PrincipalThreadLocal.getUserId(), encryptedPassword, _passwordCacheTimeToLive);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void removeWebdavModel(String googleDriveFileId) {
		Map<String, WebdavModel> webdavModels = _getWebdavModels();

		if (_log.isInfoEnabled()) {
			_log.info("Remove " + googleDriveFileId);
		}

		webdavModels.remove(googleDriveFileId);
	}

	private Map<String, WebdavModel> _getWebdavModels() {
		if (_webdavModels == null) {
			_webdavModels = new HashMap<String, WebdavModel>();
		}

		return _webdavModels;
	}

	private Map<String, List<?>> _getWebdavFiles() {
		if (_webdavFiles == null) {
			_webdavFiles = new HashMap<String, List<?>>();
		}

		return _webdavFiles;
	}

	private static Log _log = LogFactoryUtil.getLog(OwncloudCache.class);

	private static ThreadLocal<OwncloudCache> _owncloudThreadLocal = new AutoResetThreadLocal<OwncloudCache>(
			OwncloudCache.class.getName(), new OwncloudCache());

	private Map<String, WebdavModel> _webdavModels;
	private Map<String, List<?>> _webdavFiles;
	private String _passwordCacheName = "PasswordCache";
	private int _passwordCacheTimeToLive = 60*60*24;
	
	private static String _errorCacheName = "ErrorCache";

}
