package de.unipotsdam.elis.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang.StringUtils;

import com.github.sardine.DavResource;
import com.github.sardine.impl.SardineException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.repository.external.ExtRepositoryModel;
import com.liferay.repository.external.ExtRepositoryObject;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;
import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;
import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class WebdavObjectStore {

	private static Log _log = LogFactoryUtil.getLog(WebdavObjectStore.class);

	private WebdavEndpoint endpoint;

	public WebdavObjectStore(String username, String password) {
		endpoint = new WebdavEndpoint(username, password);
	}

	public void createFile(String documentName, String parentId, InputStream contentStream) {
		String url = endpoint.getEndpoint() + correctRootFolder(parentId + WebdavIdUtil.encode(documentName));
		try {
			url = solveDuplicateFiles(url);
			_log.debug("creating file with url " + url);
			endpoint.getSardine().put(url, contentStream);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: fehler hier richtig?
			OwncloudCache.getInstance().putWebdavError("no-owncloud-connection");
		}
	}

	private String solveDuplicateFiles(String url) throws IOException {
		String newUrl = url;
		String urlWithoutLastSlash = StringUtils.removeEnd(url, StringPool.FORWARD_SLASH);
		_log.debug("urlWithoutLastSlash:" + urlWithoutLastSlash);

		for (int i = 2; endpoint.getSardine().exists(newUrl); i++) {
			_log.debug("newUrl:" + newUrl);
			newUrl = urlWithoutLastSlash
					+ WebdavIdUtil.encode(StringPool.SPACE + StringPool.OPEN_PARENTHESIS + i
							+ StringPool.CLOSE_PARENTHESIS);
		}
		_log.debug("Solved duplicate files. New url:" + newUrl);
		return newUrl;
	}

	public WebdavFolder createFolder(String folderName, String parentId) {
		String id = correctRootFolder(parentId + WebdavIdUtil.encode(folderName));
		return createFolder(id);
	}

	public WebdavFolder createFolder(String id) {
		String folderId = (StringUtils.endsWith(id, StringPool.FORWARD_SLASH)) ? id : (id + StringPool.FORWARD_SLASH);
		_log.debug("creating folder " + folderId);

		try {
			createFolderRec(folderId);
		} catch (Exception e) {
			handleException(e);
			return null;
		}

		return new WebdavFolder(folderId);
	}

	private void createFolderRec(String id) throws IOException {
		String parentId = WebdavIdUtil.getParentIdFromChildId(id);
		if (!parentId.equals(StringPool.FORWARD_SLASH))
			createFolderRec(parentId);

		String webdavPath = endpoint.getEndpoint() + id;
		if (!endpoint.getSardine().exists(webdavPath)) {
			// TODO: exist nÃoetig?
			endpoint.getSardine().createDirectory(webdavPath);
		}
	}

	public void move(String sourceId, String destinationId, boolean correctRoot, boolean solveDuplicates)
			throws IOException {
		String customDestinationId = destinationId;
		String customSourceId = sourceId;
		if (correctRoot) {
			customDestinationId = correctRootFolder(destinationId);
			customSourceId = correctRootFolder(sourceId);
		}
		createFolderRec(WebdavIdUtil.getParentIdFromChildId(destinationId));
		String customDestinationUrl = endpoint.getEndpoint() + customDestinationId;
		if (solveDuplicates)
			customDestinationUrl = solveDuplicateFiles(endpoint.getEndpoint() + customDestinationId);
		_log.debug("moving " + customSourceId + " to " + customDestinationUrl);
		endpoint.getSardine().move(endpoint.getEndpoint() + customSourceId, customDestinationUrl);
	}

	public List<WebdavObject> getChildrenFromId(int maxItems, int skipCount, String id, long groupId) {
		long start = 0;
		if (_log.isDebugEnabled()) {
			_log.debug("Getting childrens from id " + correctRootFolder(id));
			start = System.nanoTime();
		}

		// resultSet contains folders and files
		List<WebdavObject> folderChildren = new ArrayList<WebdavObject>();

		// converts webdav result to CMIS type of files
		List<DavResource> resources;
		try {
			resources = getResourcesFromId(correctRootFolder(id), false);
		} catch (IOException e) {
			if (e instanceof SardineException) {
				int statusCode = ((SardineException) e).getStatusCode();
				_log.debug("Status code of webdav list request: " + statusCode);
				if (statusCode == HttpStatus.SC_NOT_FOUND) {
					String rootFolder = OwncloudRepositoryUtil.getRootFolderIdFromGroupId(groupId);
					if (!OwncloudRepositoryUtil.getWebdavRepositoryAsRoot().exists(rootFolder)) {
						_log.debug("Root folder does not exist");
						OwncloudRepositoryUtil.createRootFolder(groupId);
					}
					OwncloudRepositoryUtil.shareRootFolderWithCurrentUser(groupId);
					return getChildrenFromId(maxItems, skipCount, id, groupId);
				}
			}
			handleException(e);
			return folderChildren;
		}

		Iterator<DavResource> it = resources.iterator();

		while (it.hasNext()) {
			DavResource davResource = it.next();
			String originalId = OwncloudRepositoryUtil.replaceCustomRootWithOriginalRoot(
					WebdavIdUtil.getIdFromDavResource(davResource), groupId);
			_log.debug("iterate childrens " + davResource.getName() + " " + originalId);
			if (davResource.isDirectory()) {
				WebdavFolder folderResult = new WebdavFolder(davResource, originalId);
				folderChildren.add(folderResult);
				_log.debug("name: " + folderResult.getName());
			} else {
				WebdavFile documentImpl = new WebdavFile(davResource, originalId);
				folderChildren.add(documentImpl);
				_log.debug("name: " + documentImpl.getName());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.info("Getting childrens from id " + correctRootFolder(id) + " finished. Time: "
					+ (System.nanoTime() - start));
		}
		return folderChildren;
	}

	private List<DavResource> getResourcesFromId(String id, Boolean getDirectory) throws IOException {
		String url = WebdavIdUtil.getWebdavURIfromId(id);

		long start = 0;
		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
			_log.debug("Getting resources from: " + url);
		}

		List<DavResource> resources = endpoint.getSardine().list(url);

		// the first element is always the directory itself
		if (resources.get(0).isDirectory() && !getDirectory) {
			resources.remove(0);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Getting resources took: " + (System.currentTimeMillis() - start));
		}

		return resources;
	}

	public static final WebdavFolder createRootFolderResult() {
		WebdavFolder result = new WebdavFolder(WebdavIdUtil.LIFERAYROOTID);
		result.setRepositoryId("A1");
		return result;
	}

	public void deleteDirectory(String objectId) {
		try {
			String url = endpoint.getEndpoint() + correctRootFolder(objectId);
			_log.debug("Deleting entry with the url " + url);
			endpoint.getSardine().delete(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rename(String oldId, String newId) {
		String oldUrl = endpoint.getEndpoint() + correctRootFolder(oldId);
		String newUrl = endpoint.getEndpoint() + correctRootFolder(newId);
		try {
			// TODO: exists noetig?
			if (endpoint.getSardine().exists(oldUrl)) {
				if (_log.isDebugEnabled())
					_log.debug("Renaming entry with the url " + oldUrl + " to " + newUrl);
				endpoint.getSardine().move(oldUrl, newUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copy(String oldId, String newId) {
		String oldUrl = endpoint.getEndpoint() + correctRootFolder(oldId);
		String newUrl = endpoint.getEndpoint() + correctRootFolder(newId);
		try {
			if (endpoint.getSardine().exists(oldUrl)) {
				if (_log.isDebugEnabled())
					_log.debug("Copying entry with the url " + oldUrl + " to " + newUrl);
				endpoint.getSardine().copy(oldUrl, newUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean exists(ExtRepositoryObject folder) {
		return exists(folder.getExtRepositoryModelKey());
	}

	public boolean exists(String id) {
		try {
			String url = endpoint.getEndpoint() + correctRootFolder(id);
			_log.debug("Checking whether entry exists with url " + url);
			return endpoint.getSardine().exists(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public InputStream get(ExtRepositoryModel extRepositoryModel) {
		try {
			String url = endpoint.getEndpoint()
					+ correctRootFolder(StringUtils.removeEnd(extRepositoryModel.getExtRepositoryModelKey(), "_v"));
			_log.debug("Getting file with the url " + url);
			return endpoint.getSardine().get(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Caches an exception to allow an appropriate message in the gui. TODO:
	 * maybe there is a better way to detect and communicate problems with the
	 * webDAV connection
	 * 
	 * @param e
	 *            Exception
	 */
	private void handleException(Exception e) {
		_log.debug("Handle Exception");
		e.printStackTrace();
		if (e instanceof SardineException) {
			SardineException sardineException = ((SardineException) e);
			_log.debug("Sardine exception status code: " + sardineException.getStatusCode());
			if (sardineException.getStatusCode() == 404) {
				OwncloudCache.getInstance().putWebdavError("folder-does-not-exist");
				return;
			} else if (sardineException.getStatusCode() == 401) {
				if (OwncloudCache.getInstance().getPassword() == null) {
					OwncloudCache.getInstance().putWebdavError("enter-password");
				} else {
					OwncloudCache.getInstance().putWebdavError("wrong-password");
				}
				return;
			}
		}
		OwncloudCache.getInstance().putWebdavError("no-owncloud-connection");
	}

	private String correctRootFolder(String id) {
		_log.debug("Correct root folder of id " + id);
		if (endpoint.isRoot())
			return id;

		String originalRootPath = WebdavIdUtil.getRootFolder(id);
		String correctedId = id.replace(originalRootPath, OwncloudRepositoryUtil.getCustomRoot(originalRootPath));
		_log.debug("Corrected id: " + correctedId);
		return correctedId;
	}
}
