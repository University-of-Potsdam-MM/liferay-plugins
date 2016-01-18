package de.unipotsdam.elis.webdav;

import java.util.Date;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryModel;

public class WebdavModel implements ExtRepositoryModel{
	
	private Date _createDate;
	private String _extRepositoryModelKey;
	private String _owner = StringPool.BLANK;
	private long _size;
	
	public WebdavModel(String _extRepositoryModelKey) {
		this._createDate = new Date();
		this._extRepositoryModelKey = _extRepositoryModelKey;
	}

	public WebdavModel(DavResource davResource){
		_createDate = davResource.getCreation();
		_size = davResource.getContentLength();
		_extRepositoryModelKey = WebdavIdDecoderAndEncoder.webdavResourceToDecodedId(davResource);
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public String getExtRepositoryModelKey() {
		return _extRepositoryModelKey;
	}

	@Override
	public String getOwner() {
		return _owner;
	}

	@Override
	public long getSize() {
		return _size;
	}

}
