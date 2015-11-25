package de.unipotsdam.elis.webdav;

import org.apache.commons.lang.StringUtils;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryFileVersion;

public class WebdavFileVersion extends WebdavModel implements ExtRepositoryFileVersion{
	
	private String _mimeType;

	public WebdavFileVersion(String extRepositoryModelKey) {
		super(extRepositoryModelKey + "_v");
	}

	@Override
	public String getChangeLog() {
		return StringPool.BLANK;
	}

	@Override
	public String getMimeType() {
		return _mimeType;
	}

	@Override
	public String getVersion() {
		return StringPool.BLANK;
	}

	public String getDownloadURL(){
		return WebdavIdDecoderAndEncoder.createDownloadlink(StringUtils.removeEnd(getExtRepositoryModelKey(), "_v"));
	}

}
