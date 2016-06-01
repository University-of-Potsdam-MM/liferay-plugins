package de.unipotsdam.elis.owncloud.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.GroupUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;
import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;
import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;
import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;
import de.unipotsdam.elis.webdav.WebdavObjectStore;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class OwncloudRepositoryUtil {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepositoryUtil.class);

	public static String getRootFolderIdFromGroupId(long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);
			String groupName = WebdavIdUtil.encode(group.getDescriptiveName());
			return StringPool.FORWARD_SLASH + groupName + StringPool.FORWARD_SLASH;
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.BLANK;
	}

	public static String getCustomRootFolderFromOriginalRoot(String originalPath) {
		try {
			CustomSiteRootFolder userOwncloudDirectory = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
							originalPath));

			if (userOwncloudDirectory != null) {
				return userOwncloudDirectory.getCustomPath();
			}
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return originalPath;
	}

	public static void createAndShareRootfolder(long groupId, boolean shareWithMembers) {
		try {
			String folderId = getRootFolderIdFromGroupId(groupId);
			getWebdavRepositoryAsRoot().createFolder(folderId);
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			OwncloudShareCreator.createShare(user, groupId, folderId);
			if (shareWithMembers)
				OwncloudShareCreator.createShare(GroupUtil.getUsers(groupId), groupId, folderId, true);
		} catch (Exception e) {
			e.printStackTrace();
			OwncloudCache.getInstance().putWebdavError("no-owncloud-connection");
			// OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
			// OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
			// "no-owncloud-connection");
		}
	}

	public static void createRepository(long groupId, boolean shareWithMembers) {
		try {
			RepositoryServiceUtil.addRepository(groupId, PortalUtil.getClassNameId(OwncloudRepository.class.getName()),
					0, WebdavConfigurationLoader.getRepositoryName(), StringPool.BLANK, PortletKeys.DOCUMENT_LIBRARY,
					new UnicodeProperties(), new ServiceContext());
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		createAndShareRootfolder(groupId, shareWithMembers);
	}

	public static synchronized WebdavObjectStore getWebdavRepositoryAsRoot() {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = getWebdavRepository(WebdavConfigurationLoader.getRootUsername(),
				WebdavConfigurationLoader.getRootPassword());
		_log.debug("end getWebdavRepository");
		return result;
	}

	public static synchronized WebdavObjectStore getWebdavRepositoryAsUser() {
		_log.debug("start getWebdavRepository");
		try {
			String userLogin = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId()).getLogin();
			System.out.println("passoooord: " + OwncloudCache.getInstance().getPassword());
			WebdavObjectStore result = getWebdavRepository(userLogin, OwncloudCache.getInstance().getPassword());
			_log.debug("end getWebdavRepository");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized WebdavObjectStore getWebdavRepository(String username, String password) {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = new WebdavObjectStore(username, password);
		_log.debug("end getWebdavRepository");
		return result;
	}

	public static void setUserFolderName(String originalPath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			String userPath = OwncloudShareCreator.getSharedFolder(user.getLogin(), originalPath);

			if (_log.isInfoEnabled()) {
				CustomSiteRootFolder oldUserOwncloudDirectory = CustomSiteRootFolderLocalServiceUtil
						.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
								originalPath));
				String oldUserOwncloudDirectoryPath = (oldUserOwncloudDirectory == null) ? "null"
						: oldUserOwncloudDirectory.getCustomPath();
				_log.info("Set custom path for the folder " + originalPath + " and the user " + user.getLogin()
						+ " from " + oldUserOwncloudDirectoryPath + " to " + userPath);
			}
			if (userPath != null)
				CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder(user.getUserId(), originalPath, userPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String replaceCustomRootWithOriginalRoot(String id, long groupId) {
		try {
			String originalRoot = getRootFolderIdFromGroupId(groupId);
			CustomSiteRootFolder customSiteRootFolder = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
							originalRoot));
			if (customSiteRootFolder != null)
				return id.replace(customSiteRootFolder.getCustomPath(), originalRoot);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
}
