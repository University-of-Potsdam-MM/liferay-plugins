package de.unipotsdam.elis.webdav;

import java.math.BigInteger;
import java.util.GregorianCalendar;

import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.impl.dataobjects.ContentStreamImpl;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryFileEntry;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;

public class WebdavFile extends WebdavObject implements ExtRepositoryFileEntry{
	
	private String _mimetype; 
    
	private static Log log = LogFactoryUtil
			.getLog(WebdavFile.class);

	public WebdavFile(DavResource davResource) {
		super(davResource);	
		
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
		return WebdavIdDecoderAndEncoder.encodedIdToName(getExtRepositoryModelKey());
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
		return WebdavIdDecoderAndEncoder.createDownloadlink(getExtRepositoryModelKey());
	}
}
