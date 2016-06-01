package de.unipotsdam.elis.owncloud.repository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
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
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import de.unipotsdam.elis.owncloud.tasks.MoveFolderTaskExecutor;
import de.unipotsdam.elis.owncloud.tasks.ShareFolderTaskExecutor;
import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;

public class OwncloudShareCreator {

	private final static int READ_PERMISSION = 1;
	private final static int UPDATE_PERMISSION = 2;
	private final static int CREATE_PERMISSION = 4;
	private final static int DELETE_PERMISSION = 8;
	// private final static int SHARE_PERMISSION = 16;

	private static Log log = LogFactoryUtil.getLog(OwncloudShareCreator.class);

	public static void createShare(List<User> users, long siteGroupId, final String filePath, boolean excludeCurrentUser) {
		List<String> userIds = new ArrayList<String>();
		for (User user : users) {
			userIds.add(String.valueOf(user.getUserId()));
		}

		Map<String, Serializable> taskContextMap = new HashMap<String, Serializable>();
		taskContextMap.put("userIds", (Serializable) userIds);
		taskContextMap.put("excludeCurrentUser", excludeCurrentUser);
		taskContextMap.put("siteGroupId", String.valueOf(siteGroupId));
		taskContextMap.put("filePath", filePath);

		try {
			BackgroundTaskLocalServiceUtil.addBackgroundTask(PrincipalThreadLocal.getUserId(), siteGroupId,
					StringPool.BLANK, new String[] { "owncloud-hook" }, ShareFolderTaskExecutor.class, taskContextMap,
					new ServiceContext());
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	public static int createShareForCurrentUser(long siteGroupId, final String filePath) {
		try {
			User user = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId());
			return createShare(user.getLogin(), filePath, deriveOwncloudPermissions(user, siteGroupId));
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return 0;
	}

	public static int createShare(User user, long siteGroupId, final String filePath) {
		try {
			return createShare(user.getLogin(), filePath, deriveOwncloudPermissions(user, siteGroupId));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	private static int createShare(String userName, String filePath, int permissions) {
		HttpClient client = new HttpClient();
		String auth = WebdavConfigurationLoader.getRootUsername() + ":" + WebdavConfigurationLoader.getRootPassword();
		String encoding = Base64.encodeBase64String(auth.getBytes());

		PostMethod method = new PostMethod(WebdavConfigurationLoader.getOwncloudShareAddress());

		method.setRequestHeader("Authorization", "Basic " + encoding);

		method.addParameter("path", filePath);
		method.addParameter("shareType", "0"); // share to a user
		method.addParameter("shareWith", userName);
		method.addParameter("permissions", String.valueOf(permissions));

		try {
			client.executeMethod(method);
			
			if (log.isInfoEnabled()){
				log.info("Create share for folder " + filePath + " and user " + userName + ". Response:");
				log.info(method.getResponseBodyAsString());
			}
			Document document = SAXReaderUtil.read(method.getResponseBodyAsString());
			
			return Integer.parseInt(document.selectSingleNode("/ocs/meta/statuscode").getText());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return 0;
	}
	
	public static String getSharedFolder(String userName, String path){
		HttpClient client = new HttpClient();
		String auth = WebdavConfigurationLoader.getRootUsername() + ":" + WebdavConfigurationLoader.getRootPassword();
		String encoding = Base64.encodeBase64String(auth.getBytes());

		GetMethod method = new GetMethod(WebdavConfigurationLoader.getOwncloudShareAddress());

		method.setRequestHeader("Authorization", "Basic " + encoding);
		
		method.setQueryString("path=" + path);
		
		System.out.println("method: " + method.getQueryString());
		
		try {
			int returnCode = client.executeMethod(method);
			String response = method.getResponseBodyAsString();
			
			if (log.isInfoEnabled()){
				log.info("Getting shares for folder " + path + ". Response:");
				log.info(response);
			}
			
			if (returnCode == HttpStatus.SC_OK){
				Document document = SAXReaderUtil.read(response);
				List<Node> elementNodes = document.selectNodes("/ocs/data/element");
				System.out.println(elementNodes.size());
				for(Node elementNode : elementNodes){
					Node shareWithNode = elementNode.selectSingleNode("/ocs/data/element/share_with");
					System.out.println(shareWithNode.getText());
					if (shareWithNode.getText().equals(userName))
						return elementNode.selectSingleNode("/ocs/data/element/file_target").getText() + StringPool.FORWARD_SLASH;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			method.releaseConnection();
		}
		return null;
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
