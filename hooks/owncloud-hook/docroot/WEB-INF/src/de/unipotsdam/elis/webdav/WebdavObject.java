package de.unipotsdam.elis.webdav;

import java.util.Date;

import com.github.sardine.DavResource;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.repository.external.ExtRepositoryObject;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;

public class WebdavObject extends WebdavModel implements ExtRepositoryObject{
	private static Log _log = LogFactoryUtil.getLog(WebdavObject.class);

    private String _name;
    private String _parentId;
    private Date _modifiedDate;
	private String _description = StringPool.BLANK;
	private String _extension;
    
    public WebdavObject(DavResource davResource) {
		super(davResource);
		_name = davResource.getName();
		_parentId = WebdavIdDecoderAndEncoder.decodedIdToEncodedParentName(getExtRepositoryModelKey());
		_modifiedDate = davResource.getModified();
		_extension = FileUtil.getExtension(davResource.getName());
	}
    
    public WebdavObject(String encodedId){
    	super(WebdavIdDecoderAndEncoder.decode(encodedId));
    	_name = WebdavIdDecoderAndEncoder.encodedIdToName(encodedId);
		_parentId = WebdavIdDecoderAndEncoder.decodedIdToEncodedParentName(WebdavIdDecoderAndEncoder.decode(encodedId));
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
		String decodedId = getExtRepositoryModelKey();
		if (decodedId == null || decodedId.equals("/")
				|| WebdavIdDecoderAndEncoder.LIFERAYROOTID.equals(decodedId)) {
			return WebdavObjectStore.createRootFolderResult();
		}
		_log.debug("decodedId: " + decodedId);
			String parentIdEncoded = WebdavIdDecoderAndEncoder
					.decodedIdToEncodedParentName(decodedId);			
			WebdavFolder documentImpl = new WebdavFolder(
					parentIdEncoded);
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

	public boolean exists() {
		return OwncloudRepository.getWebdavRepository().exists(this);		
	}

	public void delete() {
		OwncloudRepository.getWebdavRepository().deleteDirectory(this.getExtRepositoryModelKey());
	}
	

	public void renameTo(WebdavObject dstFile) {
		OwncloudRepository.getWebdavRepository().rename(this.getExtRepositoryModelKey(), dstFile.getExtRepositoryModelKey());
	}

}
