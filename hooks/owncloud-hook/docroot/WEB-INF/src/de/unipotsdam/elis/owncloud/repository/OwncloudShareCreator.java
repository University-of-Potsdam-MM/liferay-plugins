package de.unipotsdam.elis.owncloud.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;

public class OwncloudShareCreator {

	private final static int READ_PERMISSION = 1;
	private final static int UPDATE_PERMISSION = 2;
	private final static int CREATE_PERMISSION = 4;
	private final static int DELETE_PERMISSION = 8;
	// private final static int SHARE_PERMISSION = 16;

	private static Log log = LogFactoryUtil.getLog(OwncloudShareCreator.class);

	public static void createShare(List<User> users, long siteGroupId, final String filePath) throws PortalException, SystemException {
		// users.remove(authorName);
		// users.remove("anyone");
		// ssssusers.remove("test");
		for (User user : users) {
			// store.rename(sharepath, sharepath+"backup"+new
			// Date(System.currentTimeMillis()));
			final String userName = user.getLogin();
			final int permissions = deriveOwncloudPermissions(user, siteGroupId);
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					createShare(userName, filePath, permissions);
					log.debug("finished creating shares" + filePath + "User: " + userName);
				}
			});
			t.start();
		}
	}

	public static void createShare(User user, long siteGroupId,	final String filePath) throws PortalException, SystemException {
		createShare(user.getLogin(), filePath, deriveOwncloudPermissions(user, siteGroupId));
	}

	private static void createShare(String userName, String filePath,
			int permissions) {
		HttpClient client = new HttpClient();
		String auth = WebdavConfigurationLoader.getRootUsername()  + ":" + WebdavConfigurationLoader.getRootPassword();
		String encoding = Base64.encodeBase64String(auth.getBytes());

		BufferedReader br = null;

		PostMethod method = new PostMethod(WebdavConfigurationLoader.getOwncloudShareAddress());

		method.setRequestHeader("Authorization", "Basic " + encoding);

		method.addParameter("path", filePath);
		method.addParameter("shareType", "0"); // share to a user
		method.addParameter("shareWith", userName);
		method.addParameter("permissions", String.valueOf(permissions));

		try {
			int returnCode = client.executeMethod(method);

			if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
				log.error("The Post method is not implemented by this URI");
				// still consume the response body
				method.getResponseBodyAsString();
			} else {
				br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream()));
				String readLine;
				while (((readLine = br.readLine()) != null)) {
					log.warn(readLine);
				}
			}
		} catch (Exception e) {
			System.err.println(e);
		} finally {
			method.releaseConnection();
			if (br != null)
				try {
					br.close();
				} catch (Exception fe) {
					log.error("The reader could not be closed after creating share");
				}
		}
	}

	private static int deriveOwncloudPermissions(User user, long siteGroupId) {
		int result = 0;
		try {
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(user);
			if (permissionChecker.hasPermission(siteGroupId, DLFileEntry.class.getName(), 0, ActionKeys.VIEW))
				result += READ_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId, DLFileEntry.class.getName(), 0, ActionKeys.UPDATE))
				result += UPDATE_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId, DLFolder.class.getName(), 0, ActionKeys.ADD_DOCUMENT))
				result += CREATE_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId, DLFileEntry.class.getName(), 0, ActionKeys.DELETE))
				result += DELETE_PERMISSION;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
