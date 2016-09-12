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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryModel;
import com.liferay.repository.external.ExtRepositoryObject;

import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

/**
 * Contains methods to handle files on a webdav server
 *
 */
public class WebdavObjectStore {

	private static Log _log = LogFactoryUtil.getLog(WebdavObjectStore.class);

	private WebdavEndpoint endpoint;
	private long groupId;

	public WebdavObjectStore(long groupId, String username, String password) {
		this.endpoint = new WebdavEndpoint(username, password);
		this.groupId = groupId;
	}

	public void createFile(String id, InputStream inputStream) {
		try {
			endpoint.getSardine().put(
					endpoint.getEndpoint()
							+ OwncloudRepositoryUtil.correctRootFolder(id,
									groupId, endpoint.isRoot()), inputStream);
		} catch (IOException e) {
			handleException(e);
		}
	}

	public WebdavFolder createFolder(String folderName, String parentId) {
		return createFolder(parentId + WebdavIdUtil.encode(folderName));
	}

	public WebdavFolder createFolder(String id) {
		String folderId = (StringUtils.endsWith(id, StringPool.FORWARD_SLASH)) ? id
				: (id + StringPool.FORWARD_SLASH);
		_log.debug("creating folder " + folderId);

		try {
			createFolderRec(OwncloudRepositoryUtil.correctRootFolder(folderId,
					groupId, endpoint.isRoot()));
		} catch (Exception e) {
			handleException(e);
			return null;
		}

		return new WebdavFolder(folderId);
	}

	/**
	 * Recursively creates the folder with the given id and the parent folders.
	 * 
	 */
	private void createFolderRec(String id) throws IOException {
		String webdavPath = endpoint.getEndpoint() + id;
		if (id.equals(StringPool.FORWARD_SLASH)
				|| endpoint.getSardine().exists(webdavPath))
			return;

		createFolderRec(WebdavIdUtil.getParentIdFromChildId(id));
		endpoint.getSardine().createDirectory(webdavPath);
	}

	public void move(String sourceId, String destinationId, boolean correctRoot)
			throws IOException {
		String customDestinationId = destinationId;
		String customSourceId = sourceId;

		if (correctRoot) {
			customDestinationId = OwncloudRepositoryUtil.correctRootFolder(
					destinationId, groupId, endpoint.isRoot());
			customSourceId = OwncloudRepositoryUtil.correctRootFolder(sourceId,
					groupId, endpoint.isRoot());
		}

		createFolderRec(WebdavIdUtil
				.getParentIdFromChildId(customDestinationId));
		String customDestinationUrl = endpoint.getEndpoint()
				+ customDestinationId;
		_log.debug("moving " + customSourceId + " to " + customDestinationUrl);
		endpoint.getSardine().move(endpoint.getEndpoint() + customSourceId,
				customDestinationUrl);
	}

	public List<WebdavObject> getChildrenFromId(int maxItems, int skipCount,
			String id, long groupId) throws PortalException, SystemException {
		long start = 0;
		if (_log.isDebugEnabled()) {
			_log.debug("Getting childrens from id "
					+ OwncloudRepositoryUtil.correctRootFolder(id, groupId,
							endpoint.isRoot()));
			start = System.nanoTime();
		}

		// resultSet contains folders and files
		List<WebdavObject> folderChildren = new ArrayList<WebdavObject>();

		// converts webdav result to CMIS type of files
		List<DavResource> resources;
		String correctedId = OwncloudRepositoryUtil.correctRootFolder(id,
				groupId, endpoint.isRoot());
		try {
			resources = getResourcesFromId(correctedId, false);
		} catch (IOException e) {
			if (e instanceof SardineException) {
				int statusCode = ((SardineException) e).getStatusCode();
				_log.debug("Status code of webdav list request: " + statusCode);
				if (statusCode == HttpStatus.SC_NOT_FOUND) {
					String rootFolder = OwncloudRepositoryUtil
							.getRootFolderIdFromGroupId(groupId);
					if (!OwncloudRepositoryUtil.getWebdavRepositoryAsRoot(
							groupId).exists(rootFolder)) {
						_log.debug("Root folder does not exist");
						OwncloudRepositoryUtil.createRootFolder(groupId);
					}
					if (!OwncloudRepositoryUtil
							.shareRootFolderWithCurrentUser(groupId)) {
						_log.debug("Unsolvable error");
						OwncloudCache.putError("unsolvable-error");
						return folderChildren;
					}
					if (!OwncloudRepositoryUtil.getWebdavRepositoryAsRoot(
							groupId).exists(id)) {
						_log.debug("Folder doesn't exist: " + id);
						OwncloudCache.putError("folder-does-not-exist");
						return folderChildren;
					}

					return getChildrenFromId(maxItems, skipCount, id, groupId);
				}
			}
			handleException(e);
			return folderChildren;
		}

		Iterator<DavResource> it = resources.iterator();

		while (it.hasNext()) {
			DavResource davResource = it.next();
			String originalId = OwncloudRepositoryUtil.correctRootForLiferay(
					WebdavIdUtil.getIdFromDavResource(davResource), groupId);
			_log.debug("iterate childrens " + davResource.getName() + " "
					+ originalId);
			if (davResource.isDirectory()) {
				WebdavFolder folderResult = new WebdavFolder(davResource,
						originalId);
				folderChildren.add(folderResult);
				_log.debug("name: " + folderResult.getName());
			} else {
				WebdavFile documentImpl = new WebdavFile(davResource,
						originalId);
				folderChildren.add(documentImpl);
				_log.debug("name: " + documentImpl.getName());
			}
		}

		if (_log.isDebugEnabled()) {
			_log.info("Getting childrens from id "
					+ OwncloudRepositoryUtil.correctRootFolder(id, groupId,
							endpoint.isRoot()) + " finished. Time: "
					+ (System.nanoTime() - start));
		}
		return folderChildren;
	}

	private List<DavResource> getResourcesFromId(String id, Boolean getDirectory)
			throws IOException {
		String url = WebdavIdUtil.getWebdavURLfromId(id);

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
			_log.debug("Getting resources took: "
					+ (System.currentTimeMillis() - start));
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
			String url = endpoint.getEndpoint()
					+ OwncloudRepositoryUtil.correctRootFolder(objectId,
							groupId, endpoint.isRoot());
			_log.debug("Deleting entry with the url " + url);
			endpoint.getSardine().delete(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void rename(String oldId, String newId) {
		String oldUrl = endpoint.getEndpoint()
				+ OwncloudRepositoryUtil.correctRootFolder(oldId, groupId,
						endpoint.isRoot());
		String newUrl = endpoint.getEndpoint()
				+ OwncloudRepositoryUtil.correctRootFolder(newId, groupId,
						endpoint.isRoot());
		try {
			if (endpoint.getSardine().exists(oldUrl)) {
				if (_log.isDebugEnabled())
					_log.debug("Renaming entry with the url " + oldUrl + " to "
							+ newUrl);
				endpoint.getSardine().move(oldUrl, newUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copy(String oldId, String newId) {
		String oldUrl = endpoint.getEndpoint()
				+ OwncloudRepositoryUtil.correctRootFolder(oldId, groupId,
						endpoint.isRoot());
		String newUrl = endpoint.getEndpoint()
				+ OwncloudRepositoryUtil.correctRootFolder(newId, groupId,
						endpoint.isRoot());
		try {
			if (endpoint.getSardine().exists(oldUrl)) {
				if (_log.isDebugEnabled())
					_log.debug("Copying entry with the url " + oldUrl + " to "
							+ newUrl);
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
			String url = endpoint.getEndpoint()
					+ OwncloudRepositoryUtil.correctRootFolder(id, groupId,
							endpoint.isRoot());
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
					+ OwncloudRepositoryUtil.correctRootFolder(StringUtils
							.removeEnd(extRepositoryModel
									.getExtRepositoryModelKey(), "_v"),
							groupId, endpoint.isRoot());
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
			_log.debug("Sardine exception status code: "
					+ sardineException.getStatusCode());
			if (sardineException.getStatusCode() == 404) {
				OwncloudCache.putError("folder-does-not-exist");
				return;
			} else if (sardineException.getStatusCode() == 401) {
				if (OwncloudCache.getInstance().getPassword() == null) {
					OwncloudCache.putError("enter-password");
				} else {
					OwncloudCache.putError("wrong-password");
				}
				return;
			}
		}
		OwncloudCache.putError("no-owncloud-connection");
	}

}
