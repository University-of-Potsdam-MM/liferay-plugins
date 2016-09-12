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

/**
 * Allows to cache webdav objects, the user password and error messages. Caching
 * error messages is kind of a work around because there is no possible to get
 * error messages from the execution layer to the user interface. User passwords
 * need to be saved because of the currently missing SSO for owncloud at the
 * university of potsdam.
 * 
 * @author Matthias
 *
 */
public class OwncloudCache implements Cloneable {

	public static OwncloudCache getInstance() {
		return _owncloudThreadLocal.get();
	}

	@Override
	public OwncloudCache clone() {
		if (_log.isDebugEnabled()) {
			Thread currentThread = Thread.currentThread();
			_log.debug("Create " + currentThread.getName());
		}

		try {
			return (OwncloudCache) super.clone();
		} catch (CloneNotSupportedException cnse) {
			throw new RuntimeException(cnse);
		}
	}

	/**
	 * Caches a webdav object.
	 */
	public void putWebdavModel(WebdavModel webdavModel) {
		Map<String, WebdavModel> webdavFiles = _getWebdavModels();

		String webdavFileId = webdavModel.getExtRepositoryModelKey();
		_log.debug("Put " + webdavFileId);
		webdavFiles.put(webdavFileId, webdavModel);
	}

	/**
	 * Returns a cached webdav object.
	 * 
	 */
	public WebdavModel getWebdavModel(String webdavFileId) {
		Map<String, WebdavModel> webdavModels = _getWebdavModels();
		WebdavModel googleDriveFile = webdavModels.get(webdavFileId);

		if (_log.isDebugEnabled())
			_log.debug(((googleDriveFile != null) ? "Hit " : "Miss ")
					+ webdavFileId);

		return googleDriveFile;
	}

	/**
	 * Caches the files of a webdav folder.
	 * 
	 */
	public void putWebdavFiles(List<?> webdavModels, String webdavFolderId) {
		Map<String, List<?>> webdavFiles = _getWebdavFiles();

		_log.debug("Put " + webdavFolderId);

		webdavFiles.put(webdavFolderId, webdavModels);
	}

	/**
	 * Returns cached files of a directory.
	 *
	 */
	public List<?> getWebdavFiles(String folderId) {
		List<?> webdavFiles = _getWebdavFiles().get(folderId);

		if (_log.isDebugEnabled())
			_log.debug(((webdavFiles != null) ? "Hit " : "Miss ") + folderId);

		return webdavFiles;
	}

	/**
	 * Caches a error.
	 *
	 */
	public static void putError(String webdavError) {
		_log.debug("Put " + webdavError);

		ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.SESSION,
				_errorCacheName).put(_errorCacheName, webdavError);
	}

	/**
	 * Returns a cached error.
	 *
	 */
	public static String getError() {
		String webdavError = (String) ThreadLocalCacheManager
				.getThreadLocalCache(Lifecycle.SESSION, _errorCacheName).get(
						_errorCacheName);

		if (_log.isDebugEnabled())
			_log.debug((webdavError != null) ? "Hit Error" : "Miss Error");

		return webdavError;
	}

	/**
	 * Caches a user password.
	 *
	 */
	public void putPassword(String password) {
		_log.debug("Put password of user with the "
				+ PrincipalThreadLocal.getUserId());

		try {
			Company company = CompanyLocalServiceUtil.getCompany(PortalUtil
					.getDefaultCompanyId());
			String encryptedPassword = Encryptor.encrypt(company.getKeyObj(),
					password);
			MultiVMPoolUtil.getCache(_passwordCacheName).put(
					PrincipalThreadLocal.getUserId(), encryptedPassword,
					_passwordCacheTimeToLive);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns a cached user password
	 *
	 */
	public String getPassword() {
		String encryptedPassword = (String) MultiVMPoolUtil.getCache(
				_passwordCacheName).get(PrincipalThreadLocal.getUserId());
		if (encryptedPassword != null) {
			_log.debug("Hit Password for user with the id "
					+ PrincipalThreadLocal.getUserId());
			try {
				Company company = CompanyLocalServiceUtil.getCompany(PortalUtil
						.getDefaultCompanyId());
				String decryptedPassword = Encryptor.decrypt(
						company.getKeyObj(), encryptedPassword);
				return decryptedPassword;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			_log.debug("Miss Password for user with the id "
					+ PrincipalThreadLocal.getUserId());
		}
		return null;
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
	private int _passwordCacheTimeToLive = 60 * 60 * 24;

	private static String _errorCacheName = "ErrorCache";

}
