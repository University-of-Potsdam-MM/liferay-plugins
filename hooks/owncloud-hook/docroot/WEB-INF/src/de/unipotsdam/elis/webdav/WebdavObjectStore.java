package de.unipotsdam.elis.webdav;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;
import org.apache.commons.io.IOUtils;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
public class WebdavObjectStore  {

	private static Log log = LogFactoryUtil.getLog(WebdavObjectStore.class);

	private WebdavEndpoint endpoint;
	
	//private static LoadingCache<WebdavResourceKey, List<DavResource>> cache = CacheBuilder.newBuilder().maximumSize(10000).recordStats().expireAfterWrite(10, TimeUnit.MINUTES).expireAfterAccess(1, TimeUnit.MINUTES).build(new WebdavCacheLoader());

	public WebdavObjectStore(String username, String password) {
		// TODO init endpoint
		endpoint = new WebdavEndpoint(username, password);
	}
	

	public String createFile(String documentNameDecoded,
			String parentIdEncoded, InputStream contentStream) {

		String parentIdDecoded = WebdavIdDecoderAndEncoder.decode(parentIdEncoded).replace(" ", "%20");
		String path = (parentIdDecoded + documentNameDecoded.replace(" ", "%20"));
		String completePath = endpoint.getEndpoint() + path;
		try {
			completePath = solveDuplicateFiles(endpoint, completePath);
			endpoint.getSardine().put(completePath, contentStream);
		} catch (IOException e) {
			e.printStackTrace();
		} 

		return WebdavIdDecoderAndEncoder.encode(path);
	}

	private String solveDuplicateFiles(WebdavEndpoint endpoint,
			String completePath) throws IOException {
		int i = 1;
		while (endpoint.getSardine().exists(completePath)) {
			completePath += "(" + i + ")";
			i++;
		}
		return completePath;
	}

	public WebdavFolder createFolder(String folderName, String parentIdEncoded) {
		String parentIdDecoded = WebdavIdDecoderAndEncoder.decode(parentIdEncoded);
		String path = parentIdDecoded + folderName;
		return createFolder(path);
	}
	
	public WebdavFolder createFolder(String path) {
		log.debug("start create Folder " + path);
		String webdavpath = endpoint.getEndpoint() + path;
		try {
			if (!endpoint.getSardine().exists(webdavpath)) {
				// TODO: exist nötig?
				endpoint.getSardine().createDirectory(webdavpath);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 	
		WebdavFolder result = new WebdavFolder(WebdavIdDecoderAndEncoder.encode(path));

		log.debug("finish create Folder");
		return result;
	}

	public Boolean exists(String parentNameDecoded, String folderName) {

		String path = parentNameDecoded + folderName;
		try {
			return endpoint.getSardine().exists(endpoint.getEndpoint() + path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public List<WebdavObject> getChildren(WebdavFolder folder, int maxItems,
			int skipCount, String user, boolean usePwc) {

		// hack root folder
		String name = folder.getName();
		// String path = folder.getPathSegment();
		String path = folder.getExtRepositoryModelKey();
		if (name.equals("RootFolder") || name.equals("Liferay%20Home")) {
			path = "/";
		}
		return getChildrenForName(maxItems, skipCount, path);
	}
	
	public List<WebdavObject> getFolderChildren(WebdavFolder folder, int maxItems,
			int skipCount, String user) {
		return getChildren(folder, maxItems, skipCount, user, false);
	}

	public List<WebdavObject> getChildrenForName(int maxItems, int skipCount,
			String path) {
		/*
		for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
				  System.out.println(s.toString()); } System.out.println(" ");*/
		log.info("start getChildrenForName " +path);
		long start = System.nanoTime();

		path = WebdavIdDecoderAndEncoder.decode(path);

		// resultSet contains folders and files
		List<WebdavObject> folderChildren = new ArrayList<WebdavObject>();

		// converts webdav result to CMIS type of files
		try {
			List<DavResource> resources = getResourcesForIDintern(WebdavIdDecoderAndEncoder.encode(path), false);
			// List<DavResource> resources = getResourcesForIDintern(path,
			// false);
			Iterator<DavResource> it = resources.iterator();

			while (it.hasNext()) {
				DavResource davResource = it.next();
				log.info("iterate getChildrenForName " +davResource.getName());
				if (davResource.isDirectory()) {
					WebdavFolder folderResult = new WebdavFolder(davResource);
					folderChildren.add(folderResult);
					log.info("name: " +folderResult.getName());
				} else {
					WebdavFile documentImpl = new WebdavFile(
							davResource);
					folderChildren.add(documentImpl);
					log.info("name: " +documentImpl.getName());
				}
				log.info("end iterate getChildrenForName " +davResource.getName());
			}

		} catch (IOException e) {
			handleStartUpErrors(e);
			return folderChildren;

		}/* catch (ExecutionException e) {
			e.printStackTrace();
		}*/

		log.info("end getChildrenForName " +path + " time:" + (System.nanoTime()-start));
		return folderChildren;
	}

	/**
	 * we assume that objectId is the URLEncoded path after the
	 * owncloud-server-path or 100 for root
	 */
	public WebdavObject getObjectById(String objectId) {
		if (objectId == null
				|| objectId.equals(WebdavIdDecoderAndEncoder.LIFERAYROOTID)) {
			WebdavFolder result = createRootFolderResult();
			return result;
		} else {
			try {
				String decodedPath = WebdavIdDecoderAndEncoder.decode(objectId);
				// entweder ist es ein folder oder ein document
				if (decodedPath.endsWith("/")) {
					WebdavFolder result = new WebdavFolder(objectId);
					return result;
				} else {
					WebdavFile result = new WebdavFile(objectId);
					
					// endpoint.getSardine().shutdown();
					return result;
				}
			} catch (Exception e) {
				log.error("error occurred whilst getting the resource for: "
						+ objectId);
				e.printStackTrace();
			}

		}
		return null;
	}

	public static final WebdavFolder createRootFolderResult() {
		// objectId = "/" ??
		WebdavFolder result = new WebdavFolder(WebdavIdDecoderAndEncoder.LIFERAYROOTID);
		result.setRepositoryId("A1");
		return result;
	}
/*
	private List<DavResource> getResourcesForIdCached(String path,
			Boolean isDirectory) throws IOException, ExecutionException {

		log.info("start getResourcesForIdCached " +path);
		String encodedPath = WebdavIdDecoderAndEncoder.encode(path);
		WebdavResourceKey key = new WebdavResourceKey(encodedPath, isDirectory,
				endpoint.getUser());
		List<DavResource> result = cache.get(key);

		if (isDirectory) {
			for (DavResource davResource : result) {
				final String encodedId = WebdavIdDecoderAndEncoder
						.webdavToIdEncoded(davResource);
				final WebdavResourceKey webdavResourceKey = new WebdavResourceKey(
						encodedId, davResource.isDirectory(),
						endpoint.getUser());
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							cache.get(webdavResourceKey);
						} catch (ExecutionException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				t.start();
			}
		}
		log.debug(cache.stats());
		log.info("end getResourcesForIdCached " +path);
		return result;
	}*/

	public List<DavResource> getResourcesForIDintern(String encodedId,
			Boolean getDirectory) throws IOException {

		String listedPath = WebdavIdDecoderAndEncoder
				.encodedIdToWebdavURI(encodedId);
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

	public void deleteDirectory(String objectIdEncoded) {

		String objectIdDecoded = WebdavIdDecoderAndEncoder
				.decode(objectIdEncoded);
		try {
			String finalPath = endpoint.getEndpoint() + objectIdDecoded;
			// endpoint.getSardine().exists(finalPath);
			endpoint.getSardine().delete(finalPath);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//cache.invalidateAll();
		}
	}

	public void rename(String oldNameEncoded, String newNameEncoded) {

		String oldNameUrl = endpoint.getEndpoint()
				+ WebdavIdDecoderAndEncoder.decode(oldNameEncoded);
		String newNameUrl = endpoint.getEndpoint()
				+ WebdavIdDecoderAndEncoder.decode(newNameEncoded);
		try {
			if (endpoint.getSardine().exists(oldNameUrl)) {
				endpoint.getSardine().move(oldNameUrl, newNameUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void copy(String oldNameEncoded, String newNameEncoded) {
		String oldNameUrl = endpoint.getEndpoint()
				+ WebdavIdDecoderAndEncoder.decode(oldNameEncoded);
		String newNameUrl = endpoint.getEndpoint()
				+ WebdavIdDecoderAndEncoder.decode(newNameEncoded);
		try {
			if (endpoint.getSardine().exists(oldNameUrl)) {
				endpoint.getSardine().copy(oldNameUrl, newNameUrl);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//cache.invalidateAll();
		}
	}

	public boolean exists(WebdavObject folder) {
		try {
			return endpoint.getSardine().exists(
					endpoint.getEndpoint()
							+ WebdavIdDecoderAndEncoder.decode(folder.getExtRepositoryModelKey()).replace(" ", "%20"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

}
