package de.unipotsdam.elis.webdav;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryFileEntry;

import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

/**
 * Represents a webdav file.
 * @author Matthias
 *
 */
public class WebdavFile extends WebdavObject implements ExtRepositoryFileEntry {

	private String _mimetype;

	public WebdavFile(DavResource davResource, String originalId) {
		super(davResource, originalId);

	}

	public WebdavFile(String encodedId) {
		super(encodedId);
	}

	@Override
	public String getName() {
		return WebdavIdUtil.getNameFromId(getExtRepositoryModelKey());
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
		return getName();
	}

	public String getDownloadURL() {
		return WebdavIdUtil.getDownloadLinkFromId(getExtRepositoryModelKey());
	}
}
