package de.unipotsdam.elis.webdav.util;

import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;
import org.apache.http.client.utils.URIBuilder;

import com.github.sardine.DavResource;
import com.liferay.compat.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;

public class WebdavIdUtil {
	private static Log _log = LogFactoryUtil.getLog(WebdavIdUtil.class);

	public static final String LIFERAYROOTID = "100";
	public static final String LIFERAYROOTSYMBOL = "/";

	public static String getWebdavURIfromId(String id) {
		return WebdavConfigurationLoader.getOwnCloudWebdavAddress() + id;
	}

	/**
	 * Root Directory is also listed (first)!!
	 * 
	 * @param resource
	 * @return
	 */
	public static String getIdFromDavResource(DavResource resource) {
		String path = null;
		if (resource.isDirectory()) {
			_log.debug("webdavResourceToDecodedId resource: " + resource.toString());
			path = resource.toString().replace(WebdavConfigurationLoader.getEndpointPath(), "");
		} else {
			_log.debug("webdavResourceToDecodedId resource: " + resource.getPath());
			path = resource.getPath().replace(WebdavConfigurationLoader.getEndpointPath(), "");
		}
		_log.debug("getIdFromDavResource: " + encode(path));
		return encode(path);
	}

	public static String getDownloadLinkFromId(String id) {
		// TODO: was wenn datei im root verzeichnis liegt?
		String directoryName = getParentNameFromChildId(id);
		String fileName = getNameFromId(id);
		URIBuilder url = null;
		try {
			url = new URIBuilder("http://localhost/owncloud/index.php/apps/files/ajax/download.php");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.addParameter("dir", HttpUtil.encodeURL(directoryName));
		url.addParameter("files", HttpUtil.encodeURL(fileName));
		return url.toString();
	}

	public static String getNameFromId(String id) {
		_log.debug("start getNameFromId:" + id);
		String nameWithoutEndingSlash = decode(StringUtils.removeEnd(id, "/"));
		_log.debug("encodedIdToName: " + nameWithoutEndingSlash.substring(nameWithoutEndingSlash.lastIndexOf("/") + 1));
		return nameWithoutEndingSlash.substring(nameWithoutEndingSlash.lastIndexOf("/") + 1);
	}

	public static String getParentNameFromChildId(String childId) {
		String parentId = getParentIdFromChildId(childId);
		if (parentId == null)
			return null;
		_log.debug("getParentNameFromChildId: " + getNameFromId(parentId));
		return getNameFromId(parentId);
	}

	public static String getParentIdFromChildId(String childId) {
		if (childId.equals("/")) {
			return null;
		}
		String withoutEndingSlash = StringUtils.removeEnd(childId, "/");
		_log.debug(withoutEndingSlash.substring(0, withoutEndingSlash.lastIndexOf("/") + 1));
		return childId.substring(0, withoutEndingSlash.lastIndexOf("/") + 1);
	}

	public static String encode(String s) {
		_log.debug("encode: " + HttpUtil.encodePath(s));
		return HttpUtil.encodePath(s);
	}

	public static String decode(String s) {
		_log.debug("decode: " + HttpUtil.decodePath(s));
		return HttpUtil.decodePath(s);
	}
}
