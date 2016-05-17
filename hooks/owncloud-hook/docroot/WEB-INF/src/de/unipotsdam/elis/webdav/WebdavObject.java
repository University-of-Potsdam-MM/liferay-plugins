package de.unipotsdam.elis.webdav;

import java.util.Date;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryObject;

import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

public class WebdavObject extends WebdavModel implements ExtRepositoryObject {
	private static Log _log = LogFactoryUtil.getLog(WebdavObject.class);

	private String _name;
	private String _parentId;
	private Date _modifiedDate;
	private String _description = StringPool.BLANK;
	private String _extension;

	public WebdavObject(DavResource davResource) {
		super(davResource);
		_name = davResource.getName();
		_parentId = WebdavIdUtil.getParentIdFromChildId(getExtRepositoryModelKey());
		_modifiedDate = davResource.getModified();
		_extension = FileUtil.getExtension(davResource.getName());
	}

	public WebdavObject(String id) {
		super(id);
		_name = WebdavIdUtil.getNameFromId(id);
		_parentId = WebdavIdUtil.getParentIdFromChildId(id);
		_modifiedDate = new Date();
		_extension = FileUtil.getExtension(getName());
	}

	public String getName() {
		return _name;
	}

	public String getParentId() {
		return _parentId;
	}

	public WebdavFolder getParentFolder() {
		_log.debug("getParendFolder decodedid: " + getExtRepositoryModelKey());
		String id = getExtRepositoryModelKey();
		if (id == null || id.equals("/") || WebdavIdUtil.LIFERAYROOTID.equals(id)) {
			return WebdavObjectStore.createRootFolderResult();
		}
		_log.debug("decodedId: " + id);
		String parentId = WebdavIdUtil.getParentIdFromChildId(id);
		WebdavFolder documentImpl = new WebdavFolder(parentId);
		return documentImpl;
	}

	@Override
	public boolean containsPermission(ExtRepositoryPermission arg0) {
		// TODO
		return true;
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public String getExtension() {
		return _extension;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void renameTo(WebdavObject dstFile) {
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser().rename(this.getExtRepositoryModelKey(),
				dstFile.getExtRepositoryModelKey());
	}

}
