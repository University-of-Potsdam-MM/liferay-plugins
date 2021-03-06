package de.unipotsdam.elis.webdav;

import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryModel;

import de.unipotsdam.elis.owncloud.repository.OwncloudCache;

/**
 * Represents a webdav model.
 *
 */
public abstract class WebdavModel implements ExtRepositoryModel {

	private static Log _log = LogFactoryUtil.getLog(WebdavModel.class);

	private Date _createDate;
	private String _extRepositoryModelKey;
	private String _owner = StringPool.BLANK;
	private long _size;

	public WebdavModel(String extRepositoryModelKey) {
		_log.debug("create by key: " + extRepositoryModelKey);
		// we don't distinguish between files and file versions
		WebdavModel cachedWebdavModel = (WebdavModel) OwncloudCache.getInstance().getWebdavModel(
				StringUtils.removeEnd(extRepositoryModelKey, "_v"));
		if (cachedWebdavModel != null) {
			_log.debug("create by key jo");
			_createDate = cachedWebdavModel.getCreateDate();
			_size = cachedWebdavModel.getSize();
			_extRepositoryModelKey = extRepositoryModelKey;
		} else {
			_log.debug("create by key nope");
			_createDate = new Date();
			_extRepositoryModelKey = extRepositoryModelKey;
		}
	}

	public WebdavModel(DavResource davResource, String originalId) {
		_log.debug("create by davResource: " + originalId);
		_createDate = davResource.getModified();
		_size = davResource.getContentLength();
		_extRepositoryModelKey = originalId;
		OwncloudCache.getInstance().putWebdavModel(this);
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
