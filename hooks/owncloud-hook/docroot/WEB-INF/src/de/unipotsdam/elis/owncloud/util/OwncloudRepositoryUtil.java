package de.unipotsdam.elis.owncloud.util;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

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
			Group group = getCorrectGroup(groupId);
			if (group.isUser()) {
				User user = UserLocalServiceUtil.getUser(group.getClassPK());
				return StringPool.FORWARD_SLASH + WebdavIdUtil.encode(user.getLogin()) + StringPool.FORWARD_SLASH;
			}
			return StringPool.FORWARD_SLASH + WebdavIdUtil.encode(group.getDescriptiveName())
					+ StringPool.FORWARD_SLASH;
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

	public static void createRootFolder(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		getWebdavRepositoryAsRoot(groupId).createFolder(folderId);
	}

	public static void shareRootFolderWithCurrentUser(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		int statusCode = OwncloudShareCreator.createShareForCurrentUser(groupId, WebdavIdUtil.decode(folderId));
		_log.debug("Status code of share request: " + statusCode);
		if (statusCode == HttpStatus.SC_FORBIDDEN)
			setCustomFolder(folderId);
		else {
			try {

				OwncloudRepositoryUtil.getWebdavRepositoryAsUser(groupId).move(
						folderId,
						StringPool.FORWARD_SLASH + WebdavIdUtil.encode(WebdavConfigurationLoader.getRootFolder())
								+ folderId, false, true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public static void createRepository(long groupId) {
		try {
			RepositoryServiceUtil.addRepository(groupId, PortalUtil.getClassNameId(OwncloudRepository.class.getName()),
					0, WebdavConfigurationLoader.getRepositoryName(), StringPool.BLANK, PortletKeys.DOCUMENT_LIBRARY,
					new UnicodeProperties(), new ServiceContext());
			if (!getCorrectGroup(groupId).isUser())
				createRootFolder(groupId);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	public static Group getCorrectGroup(long groupId) throws PortalException, SystemException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);
		if (group.getParentGroup() != null)
			return group.getParentGroup();
		return group;
	}

	public static synchronized WebdavObjectStore getWebdavRepositoryAsRoot(long groupId) {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = getWebdavRepository(groupId, WebdavConfigurationLoader.getRootUsername(),
				WebdavConfigurationLoader.getRootPassword());
		_log.debug("end getWebdavRepository");
		return result;
	}

	public static synchronized WebdavObjectStore getWebdavRepositoryAsUser(long groupId) {
		_log.debug("start getWebdavRepository");
		try {
			String userLogin = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId()).getLogin();
			System.out.println("passoooord: " + OwncloudCache.getInstance().getPassword());
			WebdavObjectStore result = getWebdavRepository(groupId, userLogin, OwncloudCache.getInstance()
					.getPassword());
			_log.debug("end getWebdavRepository");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static synchronized WebdavObjectStore getWebdavRepository(long groupId, String username, String password) {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = new WebdavObjectStore(groupId, username, password);
		_log.debug("end getWebdavRepository");
		return result;
	}

	public static void setCustomFolder(String originalPath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			String userPath = OwncloudShareCreator.getSharedFolder(user.getLogin(), originalPath);
			CustomSiteRootFolder oldUserOwncloudDirectory = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
							originalPath));

			if (userPath != null) {
				if (oldUserOwncloudDirectory != null
						&& userPath.equals(StringPool.FORWARD_SLASH
								+ WebdavIdUtil.encode(WebdavConfigurationLoader.getRootFolder()) + originalPath)) {
					CustomSiteRootFolderLocalServiceUtil.deleteCustomSiteRootFolder(oldUserOwncloudDirectory);
					if (_log.isInfoEnabled())
						_log.info("Removed custom path for folder " + originalPath);
				} else {
					CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder(user.getUserId(), originalPath,
							userPath);
					if (_log.isInfoEnabled()) {
						String oldUserOwncloudDirectoryPath = (oldUserOwncloudDirectory == null) ? "null"
								: oldUserOwncloudDirectory.getCustomPath();
						_log.info("Set custom path for the folder " + originalPath + " and the user " + user.getLogin()
								+ " from " + oldUserOwncloudDirectoryPath + " to " + userPath);
					}
				}
			} else if (oldUserOwncloudDirectory != null) {
				CustomSiteRootFolderLocalServiceUtil.deleteCustomSiteRootFolder(oldUserOwncloudDirectory);
				if (_log.isInfoEnabled())
					_log.info("Removed custom path for folder " + originalPath);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String correctRoot(String id, long groupId) throws PortalException, SystemException {
		if (getCorrectGroup(groupId).isUser())
			return StringUtils.removeEnd(getRootFolderIdFromGroupId(groupId), StringPool.FORWARD_SLASH) + id;
		String originalRoot = getRootFolderIdFromGroupId(groupId);
		return id.replace(getCustomRoot(originalRoot), originalRoot);
	}

	public static String getCustomRoot(String originalRoot) {
		try {
			CustomSiteRootFolder customSiteRootFolder = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
							originalRoot));
			if (customSiteRootFolder != null)
				return customSiteRootFolder.getCustomPath();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.FORWARD_SLASH + WebdavIdUtil.encode(WebdavConfigurationLoader.getRootFolder()) + originalRoot;

	}
}
