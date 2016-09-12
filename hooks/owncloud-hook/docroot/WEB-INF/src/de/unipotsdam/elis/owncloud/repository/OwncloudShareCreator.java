package de.unipotsdam.elis.owncloud.repository;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;

/**
 * Uses the OCS Share API of owncloud to share folders with other users. A root
 * user owns all site folders. He shares the site folder with the corresponding
 * site members.
 *
 */
public class OwncloudShareCreator {

	private final static int READ_PERMISSION = 1;
	private final static int UPDATE_PERMISSION = 2;
	private final static int CREATE_PERMISSION = 4;
	private final static int DELETE_PERMISSION = 8;
	// private final static int SHARE_PERMISSION = 16;

	private static Log log = LogFactoryUtil.getLog(OwncloudShareCreator.class);

	/**
	 * Shares a folder owned by the root user with a currently logged in user.
	 * 
	 */
	public static int createShareForCurrentUser(long siteGroupId,
			final String filePath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal
					.getUserId());
			return createShare(user.getLogin(), filePath,
					deriveOwncloudPermissions(user, siteGroupId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * Shares a folder ownd by the root user with the user with the given user
	 * name.
	 * 
	 */
	private static int createShare(String userName, String filePath,
			int permissions) {
		HttpClient client = new HttpClient();
		String auth = OwncloudConfigurationLoader.getRootUsername() + ":"
				+ OwncloudConfigurationLoader.getRootPassword();
		String encoding = Base64.encodeBase64String(auth.getBytes());

		PostMethod method = new PostMethod(
				OwncloudConfigurationLoader.getOwncloudShareAddress());

		method.setRequestHeader("Authorization", "Basic " + encoding);

		method.addParameter("path", filePath);
		method.addParameter("shareType", "0"); // share to a user
		method.addParameter("shareWith", userName);
		method.addParameter("permissions", String.valueOf(permissions));

		try {
			client.executeMethod(method);

			if (log.isDebugEnabled()) {
				log.debug("Create share for folder " + filePath + " and user "
						+ userName + ". Response:");
				log.debug(method.getResponseBodyAsString());
			}
			Document document = SAXReaderUtil.read(method
					.getResponseBodyAsString());

			return Integer.parseInt(document.selectSingleNode(
					"/ocs/meta/statuscode").getText());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return 0;
	}

	/**
	 * Returns the path where the user with the given user name put the folder
	 * with the given path. The given path describes the place of the folder in
	 * the root users root folder.
	 * 
	 */
	public static String getSharedFolderName(String userName, String path) {
		HttpClient client = new HttpClient();
		String auth = OwncloudConfigurationLoader.getRootUsername() + ":"
				+ OwncloudConfigurationLoader.getRootPassword();
		String encoding = Base64.encodeBase64String(auth.getBytes());

		GetMethod method = new GetMethod(
				OwncloudConfigurationLoader.getOwncloudShareAddress());
		method.setRequestHeader("Authorization", "Basic " + encoding);
		method.setQueryString("path=" + path);

		try {
			int returnCode = client.executeMethod(method);
			String response = method.getResponseBodyAsString();

			if (log.isDebugEnabled()) {
				log.debug("Getting shares for folder " + path + ". Response:");
				log.debug(response);
			}

			if (returnCode == HttpStatus.SC_OK) {
				// contains information about all shares of the folder
				Document document = SAXReaderUtil.read(response);
				List<Node> elementNodes = document
						.selectNodes("/ocs/data/element/share_with");
				// go through all shares
				for (Node elementNode : elementNodes) {
					log.debug("shared with: " + elementNode.getText());
					// return path of the folder for the searched user
					if (elementNode.getText().equals(userName)) {
						if (log.isInfoEnabled()) {
							log.info("file target: "
									+ elementNode.getParent()
											.element("file_target").getText());
						}
						return elementNode.getParent().element("file_target")
								.getText()
								+ StringPool.FORWARD_SLASH;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
	}

	/**
	 * Checks the users site permissions and derives the corresponding owncloud
	 * permissions.
	 * 
	 */
	private static int deriveOwncloudPermissions(User user, long siteGroupId) {
		int result = 0;
		try {
			PermissionChecker permissionChecker = PermissionCheckerFactoryUtil
					.create(user);
			if (permissionChecker.hasPermission(siteGroupId,
					DLFileEntry.class.getName(), 0, ActionKeys.VIEW))
				result += READ_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId,
					DLFileEntry.class.getName(), 0, ActionKeys.UPDATE))
				result += UPDATE_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId,
					DLFolder.class.getName(), 0, ActionKeys.ADD_DOCUMENT))
				result += CREATE_PERMISSION;
			if (permissionChecker.hasPermission(siteGroupId,
					DLFileEntry.class.getName(), 0, ActionKeys.DELETE))
				result += DELETE_PERMISSION;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
