package de.unipotsdam.elis.owncloud.util;

import java.io.FileNotFoundException;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;
import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.repository.OwncloudConfigurationLoader;
import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;
import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;
import de.unipotsdam.elis.webdav.WebdavObjectStore;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

/**
 * Provides utility functions
 * 
 * @author Matthias
 *
 */
public class OwncloudRepositoryUtil {

	private static Log _log = LogFactoryUtil
			.getLog(OwncloudRepositoryUtil.class);

	/**
	 * Derives the root folder id from the group id of a site.
	 * 
	 */
	public static String getRootFolderIdFromGroupId(long groupId) {
		try {
			Group group = getCorrectGroup(groupId);
			if (group.isUser()) {
				User user = UserLocalServiceUtil.getUser(group.getClassPK());
				return StringPool.FORWARD_SLASH
						+ WebdavIdUtil.encode(user.getLogin())
						+ StringPool.FORWARD_SLASH;
			}
			return StringPool.FORWARD_SLASH
					+ WebdavIdUtil.encode(group.getDescriptiveName())
					+ StringPool.FORWARD_SLASH;
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.BLANK;
	}

	/**
	 * Creates the root folder for a site with the given group id.
	 * 
	 */
	public static void createRootFolder(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		getWebdavRepositoryAsRoot(groupId).createFolder(folderId);
	}

	/**
	 * Shares the root folder of the site with the given group id with the
	 * current user.
	 * 
	 */
	public static boolean shareRootFolderWithCurrentUser(long groupId) {
		String folderId = getRootFolderIdFromGroupId(groupId);
		int statusCode = OwncloudShareCreator.createShareForCurrentUser(
				groupId, WebdavIdUtil.decode(folderId));
		_log.debug("Status code of share request: " + statusCode);
		if (statusCode == HttpStatus.SC_FORBIDDEN)
			// Folder is already shared - this indicates that the user has
			// renamed or moved the folder
			return setCustomFolder(folderId);
		else {
			try {
				// the folder will be moved to the default root folder for
				// workspace folders
				OwncloudRepositoryUtil.getWebdavRepositoryAsUser(groupId).move(
						folderId,
						StringPool.FORWARD_SLASH
								+ WebdavIdUtil
										.encode(OwncloudConfigurationLoader
												.getRootFolder()) + folderId,
						false);
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

	}

	/**
	 * Creates a repository connecting the document & media portlet to owncloud
	 * 
	 */
	public static void createRepository(long groupId) {
		try {
			// add repository
			RepositoryServiceUtil.addRepository(groupId, PortalUtil
					.getClassNameId(OwncloudRepository.class.getName()), 0,
					OwncloudConfigurationLoader.getRepositoryName(),
					StringPool.BLANK, PortletKeys.DOCUMENT_LIBRARY,
					new UnicodeProperties(), new ServiceContext());

			// creates the root folder with the help of the root user if the
			// group id belongs to a site
			if (!getCorrectGroup(groupId).isUser())
				createRootFolder(groupId);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sometimes a wrong group id is returned. This is related to public and
	 * private pages. We need the main group id of a site.
	 * 
	 */
	public static Group getCorrectGroup(long groupId) throws PortalException,
			SystemException {
		Group group = GroupLocalServiceUtil.getGroup(groupId);
		if (group.getParentGroup() != null)
			return group.getParentGroup();
		return group;
	}

	/**
	 * Enables the webdav connection to owncloud as the root user.
	 * 
	 */
	public static synchronized WebdavObjectStore getWebdavRepositoryAsRoot(
			long groupId) {
		_log.debug("start getWebdavRepositoryAsRoot");
		WebdavObjectStore result = getWebdavRepository(groupId,
				OwncloudConfigurationLoader.getRootUsername(),
				OwncloudConfigurationLoader.getRootPassword());
		_log.debug("end getWebdavRepositoryAsRoot");
		return result;
	}

	/**
	 * Enables the webdav connection to owncloud as the current user.
	 * 
	 */
	public static synchronized WebdavObjectStore getWebdavRepositoryAsUser(
			long groupId) {
		_log.debug("start getWebdavRepositoryAsUser");
		try {
			String userLogin = UserLocalServiceUtil.getUser(
					PrincipalThreadLocal.getUserId()).getLogin();
			WebdavObjectStore result = getWebdavRepository(groupId, userLogin,
					OwncloudCache.getInstance().getPassword());
			_log.debug("end getWebdavRepositoryAsUser");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Enables the webdav connection to owncloud as the current user.
	 *
	 */
	private static synchronized WebdavObjectStore getWebdavRepository(
			long groupId, String username, String password) {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = new WebdavObjectStore(groupId, username,
				password);
		_log.debug("end getWebdavRepository");
		return result;
	}

	/**
	 * Adds (or modifies a existing) entry in the database representing the
	 * custom path of a shared folder when the user renamed or moved the folder.
	 * 
	 */
	public static boolean setCustomFolder(String originalPath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal
					.getUserId());
			String userPath = WebdavIdUtil.encode(OwncloudShareCreator
					.getSharedFolderName(user.getLogin(), originalPath));
			CustomSiteRootFolder oldUserOwncloudDirectory = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(
							PrincipalThreadLocal.getUserId(), originalPath));
			if (userPath == null)
				return false;
			else {
				// new path equals default path
				if (oldUserOwncloudDirectory != null
						&& userPath.equals(StringPool.FORWARD_SLASH
								+ WebdavIdUtil
										.encode(OwncloudConfigurationLoader
												.getRootFolder())
								+ originalPath)) {
					CustomSiteRootFolderLocalServiceUtil
							.deleteCustomSiteRootFolder(oldUserOwncloudDirectory);
					_log.debug("Removed custom path for folder " + originalPath);
					// update custom path
				} else {
					CustomSiteRootFolderLocalServiceUtil
							.updateCustomSiteRootFolder(user.getUserId(),
									originalPath, userPath);
					if (_log.isDebugEnabled()) {
						String oldUserOwncloudDirectoryPath = (oldUserOwncloudDirectory == null) ? "null"
								: oldUserOwncloudDirectory.getCustomPath();
						_log.debug("Set custom path for the folder "
								+ originalPath + " and the user "
								+ user.getLogin() + " from "
								+ oldUserOwncloudDirectoryPath + " to "
								+ userPath);
					}
				}
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Corrects the root folder of a webdav id to have a uniform and distinct id
	 * on liferay side.
	 * 
	 */
	public static String correctRootForLiferay(String id, long groupId)
			throws PortalException, SystemException {
		if (getCorrectGroup(groupId).isUser())
			return StringUtils.removeEnd(getRootFolderIdFromGroupId(groupId),
					StringPool.FORWARD_SLASH) + id;
		String originalRoot = getRootFolderIdFromGroupId(groupId);
		return id.replace(getCustomRoot(originalRoot), originalRoot);
	}

	/**
	 * Corrects the root folder of a webdav id (coming from the liferay
	 * database) to match the path of a file on the webdav server.
	 */
	public static String correctRootFolder(String id, long groupId,
			boolean isRoot) {
		_log.debug("Correct root folder of id " + id);
		if (isRoot)
			return id;

		try {
			String originalRootPath = WebdavIdUtil.getRootFolder(id);
			Group group = getCorrectGroup(groupId);
			String correctRoot;

			if (group.isUser())
				correctRoot = StringPool.FORWARD_SLASH;
			else
				correctRoot = getCustomRoot(originalRootPath);

			String correctedId = id.replace(originalRootPath, correctRoot);
			_log.debug("Corrected id: " + correctedId);
			return correctedId;
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Derives the custom root folder from the original root
	 * 
	 */
	public static String getCustomRoot(String originalRoot) {
		try {
			CustomSiteRootFolder customSiteRootFolder = CustomSiteRootFolderLocalServiceUtil
					.fetchCustomSiteRootFolder(new CustomSiteRootFolderPK(
							PrincipalThreadLocal.getUserId(), originalRoot));
			if (customSiteRootFolder != null)
				return customSiteRootFolder.getCustomPath();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.FORWARD_SLASH
				+ WebdavIdUtil.encode(OwncloudConfigurationLoader
						.getRootFolder()) + originalRoot;
	}

	/**
	 * The path in the database is headed by the user name. This method corrects
	 * the path differently for the user pages and sites.
	 * 
	 */
	public static String liferayIdToWebdavId(long groupId, String liferayId)
			throws PortalException, SystemException {
		if (getCorrectGroup(groupId).isUser())
			return StringPool.FORWARD_SLASH
					+ StringUtil.replaceFirst(liferayId,
							WebdavIdUtil.getRootFolder(liferayId), "");
		else
			return correctRootForLiferay(liferayId, groupId);
	}

	/**
	 * Evaluates whether the current folder belongs to an owncloud repository
	 * and sets the isOwncloudRepository attribute. This attribute is used in
	 * the view to adopt the user interface accordingly.
	 * 
	 */
	public static void setProperties(PortletRequest request,
			PortletResponse response) throws PortalException, SystemException,
			FileNotFoundException {
		boolean isOwncloudRepository = false;
		long folderId = ParamUtil.getLong(request, "folderId");
		Repository rep = null;

		RepositoryEntry repEntry = RepositoryEntryLocalServiceUtil
				.fetchRepositoryEntry(folderId);
		if (repEntry != null) {
			rep = RepositoryLocalServiceUtil.getRepository(repEntry
					.getRepositoryId());
		} else {
			DLFolder dlFolder = DLFolderLocalServiceUtil
					.fetchDLFolder(folderId);
			if (dlFolder != null)
				rep = RepositoryLocalServiceUtil.fetchRepository(dlFolder
						.getRepositoryId());
		}

		if (rep != null) {
			if (rep.getClassName().equals(OwncloudRepository.class.getName())) {
				isOwncloudRepository = true;
			}
		}

		request.setAttribute("isOwncloudRepository", isOwncloudRepository);
	}
}
