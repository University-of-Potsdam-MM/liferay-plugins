package de.unipotsdam.elis.webdav;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.github.sardine.DavResource;
import com.github.sardine.SardineFactory;
import com.github.sardine.impl.SardineException;
import com.github.sardine.util.SardineUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletRequestDispatcher;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.repository.external.ExtRepositoryModel;

import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class WebdavObjectStore {

	private static Log log = LogFactoryUtil.getLog(WebdavObjectStore.class);

	private WebdavEndpoint endpoint;

	// private static LoadingCache<WebdavResourceKey, List<DavResource>> cache =
	// CacheBuilder.newBuilder().maximumSize(10000).recordStats().expireAfterWrite(10,
	// TimeUnit.MINUTES).expireAfterAccess(1, TimeUnit.MINUTES).build(new
	// WebdavCacheLoader());

	public WebdavObjectStore(String username, String password) {
		// TODO init endpoint
		endpoint = new WebdavEndpoint(username, password);
	}

	public String createFile(String documentName, String parentId, InputStream contentStream) {
		String newFileId = parentId + WebdavIdUtil.encode(documentName);
		String completePath = endpoint.getEndpoint() + newFileId;
		try {
			completePath = solveDuplicateFiles(endpoint, completePath);
			endpoint.getSardine().put(completePath, contentStream);
		} catch (IOException e) {
			e.printStackTrace();
			// TODO: fehler hier richtig?
			OwncloudCache.getInstance().putWebdavError("no-owncloud-connection");
			//OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
			//		OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME, e);
		}

		return newFileId;
	}

	private String solveDuplicateFiles(WebdavEndpoint endpoint, String completePath) throws IOException {
		int i = 1;
		while (endpoint.getSardine().exists(completePath)) {
			completePath += "(" + i + ")";
			i++;
		}
		return completePath;
	}

	public WebdavFolder createFolder(String folderName, String parentId) {
		log.debug("createFolder " + parentId + WebdavIdUtil.encode(folderName));
		return createFolder(parentId + WebdavIdUtil.encode(folderName));
	}

	public WebdavFolder createFolder(String id) {
		log.debug("start create Folder " + id);

		try {
			createFolderRec(id + "/");
		} catch (Exception e) {
			handleException(e);
			return null;
		}

		WebdavFolder result = new WebdavFolder(id + "/");

		log.debug("finish create Folder");
		return result;
	}

	private void createFolderRec(String id) throws IOException {
		String parentId = WebdavIdUtil.getParentIdFromChildId(id);
		if (!parentId.equals("/"))
			createFolderRec(parentId);

		String webdavPath = endpoint.getEndpoint() + id;
		if (!endpoint.getSardine().exists(webdavPath)) {
			// TODO: exist nÃ¶tig?
			endpoint.getSardine().createDirectory(webdavPath);
		}
	}

	public void move(String sourceId, String destinationId) {
		log.debug("move " + sourceId + " to " + destinationId);
		try {
			createFolderRec(WebdavIdUtil.getParentIdFromChildId(destinationId));
			endpoint.getSardine().move(endpoint.getEndpoint() + sourceId, endpoint.getEndpoint() + destinationId);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Boolean exists(String id) {
		try {
			return endpoint.getSardine().exists(endpoint.getEndpoint() + id);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	/*
	 * public List<WebdavObject> getChildren(WebdavFolder folder, int maxItems,
	 * int skipCount, String user, boolean usePwc) {
	 * 
	 * // hack root folder String name = folder.getName(); // String path =
	 * folder.getPathSegment(); String id = folder.getExtRepositoryModelKey();
	 * if (name.equals("RootFolder") || name.equals("Liferay%20Home")) { id =
	 * "/"; } return getChildrenFromId(maxItems, skipCount, id); }
	 * 
	 * public List<WebdavObject> getFolderChildren(WebdavFolder folder, int
	 * maxItems, int skipCount, String user) { return getChildren(folder,
	 * maxItems, skipCount, user, false); }
	 */

	public List<WebdavObject> getChildrenFromId(int maxItems, int skipCount, String id, long groupId) {
		/*
		 * for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
		 * System.out.println(s.toString()); } System.out.println(" ");
		 */
		log.info("start getChildrenForName " + id);
		long start = System.nanoTime();

		// resultSet contains folders and files
		List<WebdavObject> folderChildren = new ArrayList<WebdavObject>();

		// converts webdav result to CMIS type of files
		List<DavResource> resources;
		try {
			resources = getResourcesFromId(id, false);
		} catch (IOException e) {
			if (e instanceof SardineException) {
				int statusCode = ((SardineException) e).getStatusCode();
				if (statusCode == 404) {
					String rootFolder = OwncloudRepositoryUtil.getRootFolderIdFromGroupId(groupId);
					if (!OwncloudRepositoryUtil.getWebdavRepositoryAsRoot().exists(rootFolder)) {
						OwncloudRepositoryUtil.createAndShareRootfolder(groupId, false);
						// TODO: hier m�sste auch getChildrenFromId nochmal
						// aufgerufen werden oder?
					} else {
						OwncloudShareCreator.createShare(groupId, rootFolder);
						if (OwncloudRepositoryUtil.getWebdavRepositoryAsRoot().exists(id))
							return getChildrenFromId(maxItems, skipCount, id, groupId);
					}
				}
			}
			handleException(e);
			return folderChildren;
		}
		// List<DavResource> resources = getResourcesForIDintern(path,
		// false);
		Iterator<DavResource> it = resources.iterator();

		while (it.hasNext()) {
			DavResource davResource = it.next();
			log.info("iterate getChildrenForName " + davResource.getName());
			if (davResource.isDirectory()) {
				WebdavFolder folderResult = new WebdavFolder(davResource);
				folderChildren.add(folderResult);
				log.info("name: " + folderResult.getName());
			} else {
				WebdavFile documentImpl = new WebdavFile(davResource);
				folderChildren.add(documentImpl);
				log.info("name: " + documentImpl.getName());
			}
			log.info("end iterate getChildrenForName " + davResource.getName());
		}

		/*
		 * catch (ExecutionException e) { e.printStackTrace(); }
		 */

		log.info("end getChildrenForName " + id + " time:" + (System.nanoTime() - start));
		return folderChildren;
	}

	/**
	 * we assume that objectId is the URLEncoded path after the
	 * owncloud-server-path or 100 for root
	 */
	/*
	 * public WebdavObject getObjectById(String id) { if (id == null ||
	 * id.equals(WebdavIdUtil.LIFERAYROOTID)) { WebdavFolder result =
	 * createRootFolderResult(); return result; } else { try { // entweder ist
	 * es ein folder oder ein document if (id.endsWith("/")) { WebdavFolder
	 * result = new WebdavFolder(id); return result; } else { WebdavFile result
	 * = new WebdavFile(id);
	 * 
	 * // endpoint.getSardine().shutdown(); return result; } } catch (Exception
	 * e) { log.error("error occurred whilst getting the resource for: " + id);
	 * e.printStackTrace(); }
	 * 
	 * } return null; }
	 */

	public static final WebdavFolder createRootFolderResult() {
		// objectId = "/" ??
		WebdavFolder result = new WebdavFolder(WebdavIdUtil.LIFERAYROOTID);
		result.setRepositoryId("A1");
		return result;
	}

	/*
	 * private List<DavResource> getResourcesForIdCached(String path, Boolean
	 * isDirectory) throws IOException, ExecutionException {
	 * 
	 * log.info("start getResourcesForIdCached " +path); String encodedPath =
	 * WebdavIdDecoderAndEncoder.encode(path); WebdavResourceKey key = new
	 * WebdavResourceKey(encodedPath, isDirectory, endpoint.getUser());
	 * List<DavResource> result = cache.get(key);
	 * 
	 * if (isDirectory) { for (DavResource davResource : result) { final String
	 * encodedId = WebdavIdDecoderAndEncoder .webdavToIdEncoded(davResource);
	 * final WebdavResourceKey webdavResourceKey = new WebdavResourceKey(
	 * encodedId, davResource.isDirectory(), endpoint.getUser()); Thread t = new
	 * Thread(new Runnable() {
	 * 
	 * @Override public void run() { try { cache.get(webdavResourceKey); } catch
	 * (ExecutionException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } } }); t.start(); } } log.debug(cache.stats());
	 * log.info("end getResourcesForIdCached " +path); return result; }
	 */

	private List<DavResource> getResourcesFromId(String id, Boolean getDirectory) throws IOException {

		String listedPath = WebdavIdUtil.getWebdavURIfromId(id);
		long before = System.currentTimeMillis();
		log.debug("showing resources for: " + listedPath);

		List<DavResource> resources = endpoint.getSardine().list(listedPath);
		// the first element is always the directory itself
		if (resources.get(0).isDirectory() && !getDirectory) {
			resources.remove(0);
		}
		long now = System.currentTimeMillis();
		log.debug("getting resource listing took: " + (now - before));
		// endpoint.getSardine().shutdown();
		return resources;
	}

	private void handleStartUpErrors(IOException e) {
		if (endpoint.isValidCredentialinDebug()) {
			log.error("problems with webdav authentication at owncloud", e);
		} else {
			log.debug("the user credentials are not valid");
		}
	}

	public void deleteDirectory(String objectId) {
		try {
			String finalPath = endpoint.getEndpoint() + objectId;
			// endpoint.getSardine().exists(finalPath);
			endpoint.getSardine().delete(finalPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// cache.invalidateAll();
		}
	}

	public void rename(String oldId, String newId) {

		String oldUrl = endpoint.getEndpoint() + oldId;
		String newUrl = endpoint.getEndpoint() + newId;
		try {
			if (endpoint.getSardine().exists(oldUrl)) {
				endpoint.getSardine().move(oldUrl, newUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copy(String oldId, String newId) {
		String oldNameUrl = endpoint.getEndpoint() + oldId;
		String newNameUrl = endpoint.getEndpoint() + newId;
		try {
			if (endpoint.getSardine().exists(oldNameUrl)) {
				endpoint.getSardine().copy(oldNameUrl, newNameUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// cache.invalidateAll();
		}
	}

	public boolean exists(WebdavObject folder) {
		try {
			return endpoint.getSardine().exists(endpoint.getEndpoint() + folder.getExtRepositoryModelKey());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	public InputStream get(ExtRepositoryModel extRepositoryModel) {
		try {
			return endpoint.getSardine()
					.get(endpoint.getEndpoint()
							+ StringUtils.removeEnd(extRepositoryModel.getExtRepositoryModelKey(), "_v"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Caches an exception to allow an appropriate message in the gui. TODO:
	 * maybe is there is a better way to dedect and communicate problems with
	 * the webDAV connection
	 * 
	 * @param e
	 *            Exception
	 */
	private void handleException(Exception e) {
		System.out.println("jo exception");
		e.printStackTrace();
		if (e instanceof SardineException) {
			SardineException sardineException = ((SardineException) e);
			if (sardineException.getStatusCode() == 404) {
				OwncloudCache.getInstance().putWebdavError("folder-does-not-exist");
				//OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
				//		OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME, "folder-does-not-exist");
				return;
			} else if (sardineException.getStatusCode() == 401) {
				System.out.println("test");
				if (OwncloudCache.getInstance().getPassword() == null) {
					OwncloudCache.getInstance().putWebdavError("enter-password");
					//OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
				//			OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME, "enter-password");
				} else {
					OwncloudCache.getInstance().putWebdavError("wrong-password");
					//OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
				//			OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME, "wrong-password");
				}
				return;
			}
		}
		OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME,
				OwncloudCacheManager.WEBDAV_ERROR_CACHE_NAME, "no-owncloud-connection");
	}
}
