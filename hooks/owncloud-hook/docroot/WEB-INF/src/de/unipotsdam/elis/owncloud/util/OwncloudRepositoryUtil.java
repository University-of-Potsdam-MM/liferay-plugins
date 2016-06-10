package de.unipotsdam.elis.owncloud.util;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import com.liferay.compat.portal.kernel.util.StringUtil;
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
	
	public static void createRootFolder(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		getWebdavRepositoryAsRoot(groupId).createFolder(folderId);
	}

	public static boolean shareRootFolderWithCurrentUser(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		int statusCode = OwncloudShareCreator.createShareForCurrentUser(groupId, WebdavIdUtil.decode(folderId));
		_log.debug("Status code of share request: " + statusCode);
		if (statusCode == HttpStatus.SC_FORBIDDEN)
			return setCustomFolder(folderId);
		else {
			try {

				OwncloudRepositoryUtil.getWebdavRepositoryAsUser(groupId).move(
						folderId,
						StringPool.FORWARD_SLASH + WebdavIdUtil.encode(WebdavConfigurationLoader.getRootFolder())
								+ folderId, false, true);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
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

	public static boolean setCustomFolder(String originalPath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			String userPath = WebdavIdUtil.encode(OwncloudShareCreator.getSharedFolder(user.getLogin(), originalPath));
			CustomSiteRootFolder oldUserOwncloudDirectory = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(PrincipalThreadLocal.getUserId(),
							originalPath));
			if (userPath == null)
				return false;
			else {
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
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public static String liferayIdToWebdavId(long groupId, String liferayId) throws PortalException, SystemException {
		if (getCorrectGroup(groupId).isUser())
			return StringPool.FORWARD_SLASH
					+ StringUtil.replaceFirst(liferayId, WebdavIdUtil.getRootFolder(liferayId), "");
		else
			return correctRoot(liferayId, groupId);
	}
}
