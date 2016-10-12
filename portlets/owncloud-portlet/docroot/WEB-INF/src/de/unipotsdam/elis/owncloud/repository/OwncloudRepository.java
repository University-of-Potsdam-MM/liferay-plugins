package de.unipotsdam.elis.owncloud.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpStatus;

import com.github.sardine.impl.SardineException;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.DuplicateFolderNameException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.repository.external.CredentialsProvider;
import com.liferay.repository.external.ExtRepository;
import com.liferay.repository.external.ExtRepositoryAdapter;
import com.liferay.repository.external.ExtRepositoryAdapterCache;
import com.liferay.repository.external.ExtRepositoryFileEntry;
import com.liferay.repository.external.ExtRepositoryFileVersion;
import com.liferay.repository.external.ExtRepositoryFileVersionDescriptor;
import com.liferay.repository.external.ExtRepositoryFolder;
import com.liferay.repository.external.ExtRepositoryObject;
import com.liferay.repository.external.ExtRepositoryObjectType;
import com.liferay.repository.external.ExtRepositorySearchResult;
import com.liferay.repository.external.model.ExtRepositoryFileEntryAdapter;
import com.liferay.repository.external.model.ExtRepositoryFolderAdapter;
import com.liferay.repository.external.model.ExtRepositoryObjectAdapter;
import com.liferay.repository.external.model.ExtRepositoryObjectAdapterType;
import com.liferay.repository.external.search.ExtRepositoryQueryMapper;

import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.WebdavFile;
import de.unipotsdam.elis.webdav.WebdavFileVersion;
import de.unipotsdam.elis.webdav.WebdavFolder;
import de.unipotsdam.elis.webdav.WebdavObject;
import de.unipotsdam.elis.webdav.util.WebdavIdUtil;

/**
 * Integrates owncloud in the document and media portlet.
 * 
 * @author Matthias
 *
 */
public class OwncloudRepository extends ExtRepositoryAdapter implements
		ExtRepository {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepository.class);

	public OwncloudRepository() {
		super(null);
	}

	@Override
	public void initRepository(UnicodeProperties typeSettingsProperties,
			CredentialsProvider credentialsProvider) throws PortalException,
			SystemException {
	}

	/**
	 * Needs to be overwritten to allow fast upload of an image with the help of
	 * the ckeditor. Allows renaming of a file when duplicate file is uploaded
	 * through the ckeditor.
	 */
	@Override
	public FileEntry addFileEntry(long folderId, String sourceFileName,
			String mimeType, String title, String description,
			String changeLog, InputStream inputStream, long size,
			ServiceContext serviceContext) throws PortalException,
			SystemException {
		String fileName = null;

		if (Validator.isNull(title)) {
			fileName = sourceFileName;
		} else {
			fileName = title;
		}

		// indicates, that the file is an image and was uploaded by the
		// CKEditor
		boolean imageUpload = serviceContext.getAttribute("imageupload") == null ? false
				: true;

		String extRepositoryFolderKey = getExtRepositoryObjectKey(folderId);

		if (imageUpload)
			extRepositoryFolderKey += StringPool.SLASH
					+ OwncloudConfigurationLoader.getImageUploadFolder()
					+ StringPool.SLASH;

		ExtRepositoryFileEntry extRepositoryFileEntry = addExtRepositoryFileEntry(
				extRepositoryFolderKey, mimeType, fileName, description,
				changeLog, inputStream, imageUpload);
		if (imageUpload
				&& OwncloudCache.getError() != null
				&& (OwncloudCache.getError().equals("enter-password") || OwncloudCache
						.getError().equals("wrong-password")))
			throw new PrincipalException();

		return _toExtRepositoryObjectAdapter(
				ExtRepositoryObjectAdapterType.FILE, extRepositoryFileEntry);
	}

	@Override
	public ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream)
			throws PortalException, SystemException {

		return addExtRepositoryFileEntry(extRepositoryParentFolderKey,
				mimeType, title, description, changeLog, inputStream, false);
	}

	/**
	 * Normal file uploads (in the document and media portlet) will call the
	 * normal addFileEntry method. For uploads through the CKEditor duplicates
	 * will be solved.
	 * 
	 */
	private ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream,
			boolean uploadImage) throws PortalException, SystemException {

		String id = extRepositoryParentFolderKey + WebdavIdUtil.encode(title);
		_log.debug("Add new FileEntry:" + id);

		if (uploadImage) {
			id = solveDuplicateFileName(id);
		} else {
			if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
					.exists(id)) {
				_log.debug("Destination file does already exist: " + id);
				throw new DuplicateFileException(
						"Destination file does already exist: " + id);
			}
		}

		return createFileWithInputStream(title, inputStream, new WebdavFile(id));
	}

	/**
	 * Solves duplicate file entries.
	 * 
	 */
	private String solveDuplicateFileName(String id) {
		int i = 1;
		String newId = id;
		String fileExtension = StringPool.PERIOD + FileUtil.getExtension(id);
		String idWithoutFileExtension = StringUtil.replaceLast(id,
				fileExtension, "");
		while (OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.exists(newId)) {
			newId = idWithoutFileExtension + "%20"
					+ StringPool.OPEN_PARENTHESIS + i
					+ StringPool.CLOSE_PARENTHESIS + fileExtension;
			i++;
		}
		return newId;
	}

	/**
	 * Creates the file with an input stream.
	 * 
	 */
	private WebdavFile createFileWithInputStream(String title,
			InputStream inputStream, WebdavFile dstFile) {
		_log.debug("start createFileWithInputStream " + title + " "
				+ dstFile.getExtRepositoryModelKey());
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.createFolder(
						dstFile.getParentFolder().getExtRepositoryModelKey());
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.createFile(dstFile.getExtRepositoryModelKey(), inputStream);
		_log.debug("end createFileWithInputStream");
		return dstFile;
	}

	@Override
	public ExtRepositoryFolder addExtRepositoryFolder(
			String extRepositoryParentFolderKey, String name, String description)
			throws PortalException, SystemException {
		WebdavFolder directory = new WebdavFolder(extRepositoryParentFolderKey
				+ WebdavIdUtil.encode(name));

		if (directory.exists(OwncloudRepositoryUtil
				.getWebdavRepositoryAsUser(getGroupId()))) {
			_log.debug("Folder with the name already exists. Id: "
					+ directory.getExtRepositoryModelKey());
			// Sub exception is needed to catch exception in
			// CustomEditFolderAction
			throw new SystemException(new DuplicateFolderNameException(
					"Folder with the name already exists: "
							+ directory.getExtRepositoryModelKey()));
		}

		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.createFolder(name, extRepositoryParentFolderKey);
	}

	@Override
	public void checkInExtRepositoryFileEntry(String extRepositoryFileEntryKey,
			boolean createMajorVersion, String changeLog) {
	}

	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(
			String extRepositoryFileEntryKey) {

		return new WebdavFile(extRepositoryFileEntryKey);
	}

	/**
	 * NOT IN USE
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
			String newTitle) throws PortalException, SystemException {

		WebdavObject srcFile = (WebdavFile) new WebdavObject(
				extRepositoryFileEntryKey);
		WebdavFolder destDir = (WebdavFolder) new WebdavFolder(
				newExtRepositoryFolderKey);

		if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.exists(srcFile)) {
			throw new SystemException("Source file " + srcFile
					+ " cannot be read!");
		}

		if (!destDir.exists(OwncloudRepositoryUtil
				.getWebdavRepositoryAsUser(getGroupId()))) {
			throw new SystemException(
					"Cannot write into destination directory " + destDir);
		}

		String newFileId;
		try {
			newFileId = destDir.getExtRepositoryModelKey() + srcFile.getName();
			OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
					.copy(srcFile.getExtRepositoryModelKey(), newFileId);
		} catch (Exception ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}

		T extRepositoryObject = null;
		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER)) {
			extRepositoryObject = (T) new WebdavFolder(newFileId);
		} else {
			extRepositoryObject = (T) new WebdavFile(newFileId);
		}
		return extRepositoryObject;
	}

	@Override
	public void deleteExtRepositoryObject(
			ExtRepositoryObjectType<? extends ExtRepositoryObject> extRepositoryObjectType,
			String extRepositoryObjectKey) throws PortalException,
			SystemException {

		WebdavObject webdavObject = new WebdavObject(extRepositoryObjectKey);
		if (!OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.exists(webdavObject)) {
			throw new SystemException(
					"File doesn't exist or cannot be modified " + webdavObject);
		}
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.deleteDirectory(webdavObject.getExtRepositoryModelKey());
	}

	@Override
	public String getAuthType() {
		return null;
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileEntry extRepositoryFileEntry)
			throws PortalException, SystemException {
		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.get(extRepositoryFileEntry);
	}

	@Override
	public InputStream getContentStream(
			ExtRepositoryFileVersion extRepositoryFileVersion)
			throws PortalException, SystemException {
		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.get(extRepositoryFileVersion);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey) {

		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER))
			return (T) new WebdavFolder(extRepositoryObjectKey);
		else
			return (T) new WebdavFile(extRepositoryObjectKey);
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
			ExtRepositoryObject extRepositoryObject) throws PortalException,
			SystemException {

		WebdavObject webdavObject = (WebdavObject) extRepositoryObject;
		_log.debug("extRepositoryObjectname:"
				+ webdavObject.getExtRepositoryModelKey());
		WebdavFolder parent = webdavObject.getParentFolder();
		_log.debug("parentname:" + parent.getName());

		if (!parent.getName().equals(""))
			return parent;

		// there is no parent
		return null;
	}

	@Override
	public String getRootFolderKey() {
		return OwncloudRepositoryUtil.getRootFolderIdFromGroupId(getGroupId());
	}

	@Override
	public String[] getSupportedConfigurations() {
		return new String[0];
	}

	@Override
	public String[][] getSupportedParameters() {
		return new String[0][];
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle) throws PortalException, SystemException {

		_log.debug("start moveExtRepositoryObject / newTitle = " + newTitle
				+ "newExtRepositoryFolderKey = " + newExtRepositoryFolderKey);

		WebdavFolder parentFolder = new WebdavFolder(newExtRepositoryFolderKey);
		ExtRepositoryObject dstObject = null;

		if (extRepositoryObjectType == ExtRepositoryObjectType.FOLDER)
			dstObject = new WebdavFolder(
					parentFolder.getExtRepositoryModelKey()
							+ WebdavIdUtil.encode(newTitle));
		else
			dstObject = new WebdavFile(parentFolder.getExtRepositoryModelKey()
					+ WebdavIdUtil.encode(newTitle));

		if (!parentFolder.exists(OwncloudRepositoryUtil
				.getWebdavRepositoryAsUser(getGroupId()))) {
			_log.debug("Target parent folder does not exist. Id: "
					+ newExtRepositoryFolderKey);
			throw new NoSuchFolderException(
					"Target parent folder doesn't exist: "
							+ newExtRepositoryFolderKey);
		}

		if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.exists(dstObject)) {
			_log.debug("Destination file does already exist: "
					+ extRepositoryObjectKey);
			throw new DuplicateFileException(
					"Destination file does already exist: "
							+ dstObject.getExtRepositoryModelKey());
		}

		try {
			OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
					.move(extRepositoryObjectKey,
							dstObject.getExtRepositoryModelKey(), true);
		} catch (IOException e) {
			if (e instanceof SardineException) {
				int statusCode = ((SardineException) e).getStatusCode();
				_log.debug("Status code of webdav move request: " + statusCode);
				if (statusCode == HttpStatus.SC_NOT_FOUND) {
					_log.debug("File does not exist. Id: "
							+ extRepositoryObjectKey);
					throw new NoSuchFileEntryException(e);
				}
			}
		}

		return (T) dstObject;
	}

	@Override
	public ExtRepositoryFileVersion cancelCheckOut(String arg0)
			throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(
			ExtRepositoryFileEntry extRepositoryFileEntry, String version)
			throws PortalException, SystemException {
		return new WebdavFileVersion(
				((WebdavFile) extRepositoryFileEntry)
						.getExtRepositoryModelKey());
	}

	@Override
	public ExtRepositoryFileVersionDescriptor getExtRepositoryFileVersionDescriptor(
			String extRepositoryFileVersionKey) {

		String[] extRepositoryFileVersionKeyParts = StringUtil.split(
				extRepositoryFileVersionKey, StringPool.COLON);

		String extRepositoryFileEntryKey = extRepositoryFileVersionKeyParts[0];
		String version = extRepositoryFileVersionKeyParts[2];

		return new ExtRepositoryFileVersionDescriptor(
				extRepositoryFileEntryKey, version);
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(
			ExtRepositoryFileEntry extRepositoryFileEntry)
			throws PortalException, SystemException {

		WebdavFile webdavDocument = (WebdavFile) extRepositoryFileEntry;
		List<ExtRepositoryFileVersion> extRepositoryFileVersions = new ArrayList<ExtRepositoryFileVersion>();
		extRepositoryFileVersions.add(new WebdavFileVersion(webdavDocument
				.getExtRepositoryModelKey()));
		return extRepositoryFileVersions;
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey) throws PortalException,
			SystemException {

		_log.debug("start getFoldersAndFileEntries");
		long startTime = System.nanoTime();

		List<T> childrens = (List<T>) OwncloudCache.getInstance()
				.getWebdavFiles(extRepositoryFolderKey);

		if (childrens == null) {
			childrens = (List<T>) OwncloudRepositoryUtil
					.getWebdavRepositoryAsUser(getGroupId()).getChildrenFromId(
							1000, 0, extRepositoryFolderKey, getGroupId());

			OwncloudCache.getInstance().putWebdavFiles(childrens,
					extRepositoryFolderKey);
		}

		_log.debug("end getFoldersAndFileEntries time:"
				+ (System.nanoTime() - startTime));

		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.OBJECT))
			return childrens;

		List<T> result = new ArrayList<T>();

		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER)) {
			for (T child : childrens) {
				if (child instanceof WebdavFolder)
					result.add(child);
			}
		} else {
			for (T child : childrens) {
				if (child instanceof WebdavFile)
					result.add(child);
			}
		}

		return result;
	}

	@Override
	public int getExtRepositoryObjectsCount(
			ExtRepositoryObjectType<? extends ExtRepositoryObject> extRepositoryObjectType,
			String extRepositoryFolderKey) throws PortalException,
			SystemException {
		return getExtRepositoryObjects(extRepositoryObjectType,
				extRepositoryFolderKey).size();
	}

	@Override
	public List<String> getSubfolderKeys(String arg0, boolean arg1)
			throws PortalException, SystemException {
		List<String> result = new ArrayList<String>();
		List<ExtRepositoryFolder> subfolders = getExtRepositoryObjects(
				ExtRepositoryObjectType.FOLDER, arg0);
		for (ExtRepositoryFolder subfolder : subfolders) {
			result.add(subfolder.getExtRepositoryModelKey());
		}
		return result;
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(SearchContext arg0,
			Query arg1, ExtRepositoryQueryMapper arg2) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(
			String extRepositoryFileEntryKey, String mimeType,
			InputStream inputStream) throws PortalException, SystemException {
		String webdavId = OwncloudRepositoryUtil.liferayIdToWebdavId(
				getGroupId(), extRepositoryFileEntryKey);
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser(getGroupId())
				.createFile(webdavId, inputStream);
		return new WebdavFile(extRepositoryFileEntryKey);
	}

	public String getDownloadLink(long fileEntryId) throws PortalException,
			SystemException {
		ExtRepositoryFileEntry file = getExtRepositoryObject(
				ExtRepositoryObjectType.FILE,
				getExtRepositoryObjectKey(fileEntryId));
		return ((WebdavFile) file).getDownloadURL();
	}

	@Override
	public List<Folder> getFolders(long parentFolderId,
			boolean includeMountFolders, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		List<Folder> result = super.getFolders(parentFolderId,
				includeMountFolders, start, end, obc);

		return result;
	}

	/**
	 * Needed to allow to overwrite of addFileEntry (copy from
	 * ExtRepositoryAdapter)
	 */
	@SuppressWarnings("unchecked")
	private <T extends ExtRepositoryObjectAdapter<?>> T _toExtRepositoryObjectAdapter(
			ExtRepositoryObjectAdapterType<T> extRepositoryObjectAdapterType,
			ExtRepositoryObject extRepositoryObject) throws PortalException,
			SystemException {

		ExtRepositoryAdapterCache extRepositoryAdapterCache = ExtRepositoryAdapterCache
				.getInstance();

		String extRepositoryModelKey = extRepositoryObject
				.getExtRepositoryModelKey();

		ExtRepositoryObjectAdapter<?> extRepositoryObjectAdapter = extRepositoryAdapterCache
				.get(extRepositoryModelKey);

		if (extRepositoryObjectAdapter == null) {
			Object[] repositoryEntryIds = getRepositoryEntryIds(extRepositoryModelKey);

			long extRepositoryObjectId = (Long) repositoryEntryIds[0];

			String uuid = (String) repositoryEntryIds[1];

			if (extRepositoryObject instanceof ExtRepositoryFolder) {
				ExtRepositoryFolder extRepositoryFolder = (ExtRepositoryFolder) extRepositoryObject;

				extRepositoryObjectAdapter = new ExtRepositoryFolderAdapter(
						this, extRepositoryObjectId, uuid, extRepositoryFolder);
			} else {
				ExtRepositoryFileEntry extRepositoryFileEntry = (ExtRepositoryFileEntry) extRepositoryObject;

				extRepositoryObjectAdapter = new ExtRepositoryFileEntryAdapter(
						this, extRepositoryObjectId, uuid,
						extRepositoryFileEntry);

				_forceGetFileVersions((ExtRepositoryFileEntryAdapter) extRepositoryObjectAdapter);

				_checkAssetEntry((ExtRepositoryFileEntryAdapter) extRepositoryObjectAdapter);
			}

			extRepositoryAdapterCache.put(extRepositoryObjectAdapter);
		}

		if (extRepositoryObjectAdapterType == ExtRepositoryObjectAdapterType.FILE) {

			if (!(extRepositoryObjectAdapter instanceof ExtRepositoryFileEntryAdapter)) {

				throw new NoSuchFileEntryException(
						"External repository object is not a file "
								+ extRepositoryObject);
			}
		} else if (extRepositoryObjectAdapterType == ExtRepositoryObjectAdapterType.FOLDER) {

			if (!(extRepositoryObjectAdapter instanceof ExtRepositoryFolderAdapter)) {

				throw new NoSuchFolderException(
						"External repository object is not a folder "
								+ extRepositoryObject);
			}
		} else if (extRepositoryObjectAdapterType != ExtRepositoryObjectAdapterType.OBJECT) {

			throw new IllegalArgumentException(
					"Unsupported repository object type "
							+ extRepositoryObjectAdapterType);
		}

		return (T) extRepositoryObjectAdapter;
	}

	/**
	 * Needed to allow to overwrite of addFileEntry (copy from
	 * ExtRepositoryAdapter)
	 */
	private void _forceGetFileVersions(
			ExtRepositoryFileEntryAdapter extRepositoryFileEntryAdapter)
			throws SystemException {

		extRepositoryFileEntryAdapter
				.getFileVersions(WorkflowConstants.STATUS_ANY);
	}

	/**
	 * Needed to allow to overwrite of addFileEntry (copy from
	 * ExtRepositoryAdapter)
	 */
	private void _checkAssetEntry(
			ExtRepositoryFileEntryAdapter extRepositoryFileEntryAdapter)
			throws PortalException, SystemException {

		dlAppHelperLocalService.checkAssetEntry(
				PrincipalThreadLocal.getUserId(),
				extRepositoryFileEntryAdapter,
				extRepositoryFileEntryAdapter.getFileVersion());
	}
}
