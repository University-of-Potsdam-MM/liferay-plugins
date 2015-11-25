package de.unipotsdam.elis.webdav;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.URIParameter;

import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;

import com.github.sardine.DavResource;
import com.liferay.compat.portal.kernel.util.HtmlUtil;
import com.liferay.compat.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.URLUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;

public class WebdavIdDecoderAndEncoder {
	private static Log _log = LogFactoryUtil.getLog(WebdavIdDecoderAndEncoder.class);
	
	public static final String LIFERAYROOTID = "100";
	public static final String LIFERAYROOTSYMBOL = "/";

	public static String encodedIdToWebdavURI(String id) {
		return WebdavConfigurationLoader.getOwnCloudWebdavAddress() + decode(id).replace(" ", "%20");
	}

	/**
	 * Root Directory is also listed (first)!!
	 * 
	 * @param resource
	 * @return
	 */
	public static String webdavResourceToDecodedId(DavResource resource) {
		if (resource.isDirectory()) {
			_log.debug("webdavResourceToDecodedId resource: " + resource.toString());
			_log.debug("webdavResourceToDecodedId endpoint: " + WebdavConfigurationLoader.getEndpointPath());
			String path = resource.toString().replace(WebdavConfigurationLoader.getEndpointPath(), "");	
			_log.debug("webdavResourceToDecodedId: " + path);
			return path;
		} else {
			String path = resource.getPath().replace(
					WebdavConfigurationLoader.getEndpointPath(), "");
			_log.debug("webdavResourceToDecodedId: " + path);
			return path;
		}
	}
	
	public static String webdavResourceToEncodedId(DavResource resource) {
		String result = encode(webdavResourceToDecodedId(resource));	
				_log.debug("webdavResourceToEncodedId: " + result);
		return result;	
	}
	
	public static String createDownloadlink(String decodedId) {
		// TODO: schöner
		String directoryId = decodedIdToDecodedParentName(decodedId);
		String fileId = encodedIdToName(encode(decodedId));
		URIBuilder url = null;
		try {
			url = new URIBuilder("http://localhost/owncloud/index.php/apps/files/ajax/download.php");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		url.addParameter("dir", directoryId.replace("/", "%2").replace(" ", "%20"));
		url.addParameter("files", fileId.replace("/", "%2").replace(" ", "%20"));
		return url.toString();
	}
	

	public static String encodedIdToName(String id) {
		String unencodedId = decode(id);
		String result;
		if (unencodedId.endsWith("/")) {
			String idWithoutSlash =  unencodedId.substring(0, unencodedId.lastIndexOf("/"));
			result = idWithoutSlash.substring(idWithoutSlash.lastIndexOf("/")+1);
					_log.debug("encodedIdToName: " + result);
			return result;
		} else {
			result = unencodedId.substring(unencodedId.lastIndexOf("/")+1);
			_log.debug("encodedIdToName: " + result);			
			return result;
		}
	}
	
	public static String decodedIdToDecodedParentName(String id)  {
		if (id.equals("/")) {
			return null;
		} 
		
		if (id.endsWith("/")) {
			String tmp = id.substring(0, id.lastIndexOf("/"));
			String result = id.substring(0, tmp.lastIndexOf("/")+1);
			_log.debug("decodedIdToDecodedParentName: " + result);			
			return result;
		} else {
			String result = id.substring(0, id.lastIndexOf("/")+1);
			_log.debug("decodedIdToDecodedParentName: " + result);	
			return result;
		}		
	}
	
	public static String decodedIdToEncodedParentName(String id)  {
		_log.debug("decodedIdToEncodedParentName: " + WebdavIdDecoderAndEncoder.encode(decodedIdToDecodedParentName(id)));	
		return WebdavIdDecoderAndEncoder.encode(decodedIdToDecodedParentName(id));
	}

	public static String encode(String s) {
		_log.debug("encode: " + HttpUtil.encodeURL(s));	
		return HttpUtil.encodeURL(s);
	}

	public static String decode(String s) {
		_log.debug("decode: " + HttpUtil.decodeURL(s));	
		return HttpUtil.decodeURL(s);
	}
}
