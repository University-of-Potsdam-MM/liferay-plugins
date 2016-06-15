package de.unipotsdam.elis.webdav;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryFileEntry;

import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class WebdavFile extends WebdavObject implements ExtRepositoryFileEntry{
	
	private String _mimetype; 
    
	private static Log log = LogFactoryUtil
			.getLog(WebdavFile.class);

	public WebdavFile(DavResource davResource, String originalId) {
		super(davResource,originalId);	
		
	}

	public WebdavFile(String encodedId) {
		super(encodedId);
	}

	@Deprecated
	public boolean canWrite() {		
		return true;
	}
	
	@Override
	public String getName() {
		log.debug("start getName");
		String name = WebdavIdUtil.getNameFromId(getExtRepositoryModelKey());;
		log.debug("end getName");
		return name;
	}

	@Override
	public String getCheckedOutBy() {
		return StringPool.BLANK;
	}

	@Override
	public String getMimeType() {
		return _mimetype;
	}

	@Override
	public String getTitle() {
		// TODO 
		return getName();
	}
	
	public String getDownloadURL(){
		return WebdavIdUtil.getDownloadLinkFromId(getExtRepositoryModelKey());
	}
}
