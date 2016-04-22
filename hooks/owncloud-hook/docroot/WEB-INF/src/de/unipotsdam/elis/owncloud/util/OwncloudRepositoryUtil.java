package de.unipotsdam.elis.owncloud.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;

import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;
import de.unipotsdam.elis.webdav.WebdavObjectStore;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class OwncloudRepositoryUtil {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepositoryUtil.class);

	public static String getRootFolderFromGroupId(long groupId) {
		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);
			if (group.getParentGroupId() != 0)
				group = group.getParentGroup();
			String groupName = WebdavIdUtil.encode(group.getDescriptiveName());
			return "/" + WebdavConfigurationLoader.getRootFolder() + "/" + groupName + "/";
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.BLANK;
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
