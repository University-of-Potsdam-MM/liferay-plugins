package de.unipotsdam.elis.webdav;

import org.apache.commons.lang.StringUtils;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.repository.external.ExtRepositoryFolder;

public class WebdavFolder extends WebdavObject implements ExtRepositoryFolder {
	private static Log _log = LogFactoryUtil.getLog(WebdavFolder.class);

	private String repositoryId;

	public WebdavFolder(DavResource davResource, String originalId) {
		super(davResource, originalId);
	}

	public WebdavFolder(String id) {
		super(id);
	}

	public String getAbsolutePath() {
		return getExtRepositoryModelKey();
	}

	public Boolean exists(WebdavObjectStore objectStore) {
		return objectStore.exists(this);
	}

	@Override
	public boolean isRoot() {
		// TODO
		_log.debug("isroot: " + getExtRepositoryModelKey() + " "
				+ (StringUtils.countMatches(getExtRepositoryModelKey(), "/") == 2));
		return StringUtils.countMatches(getExtRepositoryModelKey(), "/") == 2;
	}

	public String getRepositoryId() {
		return repositoryId;
	}

	public void setRepositoryId(String repositoryId) {
		this.repositoryId = repositoryId;
	}

}
