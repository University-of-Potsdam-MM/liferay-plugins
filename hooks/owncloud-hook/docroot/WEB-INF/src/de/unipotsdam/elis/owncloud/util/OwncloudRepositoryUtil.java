package de.unipotsdam.elis.owncloud.util;

import java.io.IOException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;
import de.unipotsdam.elis.webdav.WebdavObjectStore;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class OwncloudRepositoryUtil {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepositoryUtil.class);

	public static String getRootFolderIdFromGroupId(long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);
			String groupName = WebdavIdUtil.encode(group.getDescriptiveName());
			return "/" + groupName + "/";
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.BLANK;
	}
	
	public static void createAndShareRootfolder(long groupId){
		try {
			String folderId = getRootFolderIdFromGroupId(groupId);
			getWebdavRepository().createFolder(folderId);
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			OwncloudShareCreator.createShare(user, groupId, folderId);
		} catch (Exception e) {
			e.printStackTrace();
			OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR, OwncloudCacheManager.WEBDAV_ERROR, e);
		}
	}

	public static synchronized WebdavObjectStore getWebdavRepository() {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = getWebdavRepository(WebdavConfigurationLoader.getRootUsername(),
				WebdavConfigurationLoader.getRootPassword());
		_log.debug("end getWebdavRepository");
		return result;
	}

	public static synchronized WebdavObjectStore getWebdavRepository(String username, String password) {
		_log.debug("start getWebdavRepository");
		WebdavObjectStore result = new WebdavObjectStore(username, password);
		_log.debug("end getWebdavRepository");
		return result;
	}
}
