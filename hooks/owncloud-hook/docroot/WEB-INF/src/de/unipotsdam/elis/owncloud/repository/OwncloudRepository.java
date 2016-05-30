/**
 * Copyright (c) 2012 TomÃƒÆ’Ã‚Â¡Ãƒâ€¦Ã‚Â¡ PoleÃƒâ€¦Ã‚Â¡ovskÃƒÆ’Ã‚Â½
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package de.unipotsdam.elis.owncloud.repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.repository.external.CredentialsProvider;
import com.liferay.repository.external.ExtRepository;
import com.liferay.repository.external.ExtRepositoryAdapter;
import com.liferay.repository.external.ExtRepositoryFileEntry;
import com.liferay.repository.external.ExtRepositoryFileVersion;
import com.liferay.repository.external.ExtRepositoryFileVersionDescriptor;
import com.liferay.repository.external.ExtRepositoryFolder;
import com.liferay.repository.external.ExtRepositoryObject;
import com.liferay.repository.external.ExtRepositoryObjectType;
import com.liferay.repository.external.ExtRepositorySearchResult;
import com.liferay.repository.external.model.ExtRepositoryFolderAdapter;
import com.liferay.repository.external.model.ExtRepositoryObjectAdapterType;
import com.liferay.repository.external.search.ExtRepositoryQueryMapper;

import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.WebdavFile;
import de.unipotsdam.elis.webdav.WebdavFileVersion;
import de.unipotsdam.elis.webdav.WebdavFolder;
import de.unipotsdam.elis.webdav.WebdavObject;

/**
 * @author Tomas Polesovsky
 */
// TODO: PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE - check ACCESS + VIEW
// on the parent folder
public class OwncloudRepository extends ExtRepositoryAdapter implements ExtRepository {

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepository.class);

	public OwncloudRepository() {
		super(null);
	}

	@Override
	public void initRepository(UnicodeProperties typeSettingsProperties, CredentialsProvider credentialsProvider)
			throws PortalException, SystemException {
		try {
			_log.debug("initRepository: " + getRootFolderKey());
			// OwncloudRepositoryUtil.getWebdavRepository().createFolder(getRootFolderKey());
			_log.debug("initRepository finished");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ExtRepositoryFileEntry addExtRepositoryFileEntry(String extRepositoryParentFolderKey, String mimeType,
			String title, String description, String changeLog, InputStream inputStream) throws PortalException,
			SystemException {

		WebdavFolder directory = new WebdavFolder(extRepositoryParentFolderKey);

		if (directory.exists(OwncloudRepositoryUtil.getWebdavRepositoryAsUser())) {
			// Fileable file = new File(directory, sourceFileName);
			WebdavFile documentImpl = new WebdavFile(directory.getExtRepositoryModelKey() + title);
			try {
				documentImpl = createFileWithInputStream(title, inputStream, documentImpl);
				_log.debug("end addFileEntry");
				return documentImpl;
				// StreamUtil.transfer(is, new FileOutputStream(file), true);
				// return fileToFileEntry(file);
			} catch (Exception ex) {
				_log.error(ex);
				throw new SystemException(ex);
			}
		} else {
			throw new SystemException("Directory " + directory + " cannot be read!");
		}
	}

	@Override
	public ExtRepositoryFolder addExtRepositoryFolder(String extRepositoryParentFolderKey, String name,
			String description) throws PortalException, SystemException {
		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser().createFolder(name, extRepositoryParentFolderKey);
	}

	@Override
	public void checkInExtRepositoryFileEntry(String extRepositoryFileEntryKey, boolean createMajorVersion,
			String changeLog) {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(String extRepositoryFileEntryKey) {

		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType, String extRepositoryFileEntryKey,
			String newExtRepositoryFolderKey, String newTitle) throws PortalException, SystemException {
		WebdavObject srcFile = (WebdavFile) new WebdavObject(extRepositoryFileEntryKey);
		WebdavFolder destDir = (WebdavFolder) new WebdavFolder(newExtRepositoryFolderKey);
		if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser().exists(srcFile)) {
			throw new SystemException("Source file " + srcFile + " cannot be read!");
		}
		if (!destDir.exists(OwncloudRepositoryUtil.getWebdavRepositoryAsUser())) {
			throw new SystemException("Cannot write into destination directory " + destDir);
		}
		String newFileId;
		try {
			newFileId = destDir.getExtRepositoryModelKey() + srcFile.getName();
			OwncloudRepositoryUtil.getWebdavRepositoryAsUser().copy(srcFile.getExtRepositoryModelKey(), newFileId);
			// StreamUtil.transfer(new FileInputStream(srcFile),
			// new FileOutputStream(dstFile), true);
			// return fileToFileEntry(dstFile);
		} catch (Exception ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}
		T extRepositoryObject = null;
		// Fileable dstFile = new File(destDir, srcFile.getName());
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
			String extRepositoryObjectKey) throws PortalException, SystemException {

		WebdavObject webdavObject = new WebdavObject(extRepositoryObjectKey);
		if (!OwncloudRepositoryUtil.getWebdavRepositoryAsUser().exists(webdavObject)) {
			throw new SystemException("File doesn't exist or cannot be modified " + webdavObject);
		}
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser().deleteDirectory(webdavObject.getExtRepositoryModelKey());
	}

	@Override
	public String getAuthType() {
		return null;
	}

	@Override
	public InputStream getContentStream(ExtRepositoryFileEntry extRepositoryFileEntry) throws PortalException,
			SystemException {
		/*
		 * for (StackTraceElement s : Thread.currentThread().getStackTrace())
		 * System.out.println("   " + s);
		 */
		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser().get(extRepositoryFileEntry);
	}

	@Override
	public InputStream getContentStream(ExtRepositoryFileVersion extRepositoryFileVersion) throws PortalException,
			SystemException {
		/*
		 * for (StackTraceElement s : Thread.currentThread().getStackTrace())
		 * System.out.println("   " + s);
		 */
		return OwncloudRepositoryUtil.getWebdavRepositoryAsUser().get(extRepositoryFileVersion);
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey) {

		T extRepositoryObject = null;

		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER)) {
			extRepositoryObject = (T) new WebdavFolder(extRepositoryObjectKey);
		} else {
			extRepositoryObject = (T) new WebdavFile(extRepositoryObjectKey);
		}
		return extRepositoryObject;
	}

	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(ExtRepositoryObject extRepositoryObject)
			throws PortalException, SystemException {
		WebdavObject webdavObject = (WebdavObject) extRepositoryObject;
		_log.debug("extRepositoryObjectname:" + webdavObject.getName());
		WebdavFolder parent = webdavObject.getParentFolder();
		_log.debug("parentname:" + parent.getName());
		if (!parent.getName().equals(""))
			return parent;
		return null;
	}

	@Override
	public String getRootFolderKey() {
		return OwncloudRepositoryUtil.getRootFolderIdFromGroupId(getGroupId());
	}

	@Override
	public long getGroupId() {
		try {
			Group group = GroupLocalServiceUtil.getGroup(super.getGroupId());
			if (group.getParentGroupId() != 0)
				group = group.getParentGroup();
			return group.getGroupId();
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		return 0;
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
	public <T extends ExtRepositoryObject> T moveExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType, String extRepositoryObjectKey,
			String newExtRepositoryFolderKey, String newTitle) throws PortalException, SystemException {
		_log.debug("start moveFileEntry");
		WebdavObject fileToMove = new WebdavObject(extRepositoryObjectKey);
		WebdavFolder parentFolder = new WebdavFolder(newExtRepositoryFolderKey);
		WebdavFile dstFile = new WebdavFile(parentFolder.getExtRepositoryModelKey() + fileToMove.getName());

		if (!OwncloudRepositoryUtil.getWebdavRepositoryAsUser().exists(fileToMove)) {
			throw new SystemException("Source file doesn't exist: " + fileToMove);
		}
		if (!parentFolder.exists(OwncloudRepositoryUtil.getWebdavRepositoryAsUser())) {
			throw new SystemException("Destination parent folder doesn't exist: " + parentFolder);
		}
		if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser().exists(dstFile)) {
			throw new SystemException("Destination file does exist: " + dstFile);
		}
		fileToMove.renameTo(dstFile);

		return (T) dstFile;
	}

	private WebdavFile createFileWithInputStream(String title, InputStream is, WebdavFile dstFile)
			throws PortalException, SystemException {
		_log.debug("start createFileWithInputStream");
		OwncloudRepositoryUtil.getWebdavRepositoryAsUser().createFile(title,
				dstFile.getParentFolder().getExtRepositoryModelKey(), is);
		_log.debug("end createFileWithInputStream");
		return dstFile;
	}

	/*
	 * private synchronized void createShareInOwncloud() throws SystemException,
	 * PortalException { _log.debug("start createShareInOwncloud");
	 * 
	 * final List<User> users =
	 * UserLocalServiceUtil.getGroupUsers(this.getGroupId()); final Set<String>
	 * userStrings = new HashSet<String>();
	 * 
	 * for (User user : users) { userStrings.add(user.getLogin()); }
	 * 
	 * //final String username =
	 * UserLocalServiceUtil.getUser(PrincipalThreadLocal
	 * .getUserId()).getLogin(); //final String password =
	 * PrincipalThreadLocal.getPassword(); final WebdavObjectStore objectStore =
	 * getWebdavRepository();
	 * 
	 * Thread shareCreatorthread = new Thread(new Runnable() {
	 * 
	 * @Override public void run() {
	 * 
	 * _log.debug("start runnable"); OwncloudShareCreator owncloudService = new
	 * OwncloudShareCreator(); owncloudService.createShare(users, siteGroupId,
	 * WebdavConfigurationLoader.getRootUsername(),
	 * WebdavConfigurationLoader.getRootPassword(), filePath);
	 * .createShare(getRepositoryId() + "", getRootFolderKey(), userStrings,
	 * username, password); _log.debug("end runnable"); } });
	 * shareCreatorthread.start();
	 * 
	 * _log.debug("stop createShareInOwncloud"); }
	 */

	@Override
	public ExtRepositoryFileVersion cancelCheckOut(String arg0) throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(ExtRepositoryFileEntry extRepositoryFileEntry,
			String version) throws PortalException, SystemException {
		_log.debug("getExtRepositoryFileVersion 1");
		WebdavFile webdavDocument = (WebdavFile) extRepositoryFileEntry;
		return new WebdavFileVersion(webdavDocument.getExtRepositoryModelKey());
	}

	@Override
	public ExtRepositoryFileVersionDescriptor getExtRepositoryFileVersionDescriptor(String extRepositoryFileVersionKey) {
		_log.debug("getExtRepositoryFileVersion 2");
		String[] extRepositoryFileVersionKeyParts = StringUtil.split(extRepositoryFileVersionKey, StringPool.COLON);

		String extRepositoryFileEntryKey = extRepositoryFileVersionKeyParts[0];
		String version = extRepositoryFileVersionKeyParts[2];

		return new ExtRepositoryFileVersionDescriptor(extRepositoryFileEntryKey, version);
	}

	@Override
	public Object[] getRepositoryEntryIds(String objectId) throws SystemException {
		_log.debug("objectId: " + objectId);
		boolean newRepositoryEntry = false;
		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByR_M(getRepositoryId(), objectId);

		if (repositoryEntry == null) {
			long repositoryEntryId = counterLocalService.increment();

			repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);

			repositoryEntry.setGroupId(getGroupId());
			repositoryEntry.setRepositoryId(getRepositoryId());
			repositoryEntry.setMappedId(objectId);

			RepositoryEntryUtil.update(repositoryEntry);

			newRepositoryEntry = true;
		}

		return new Object[] { repositoryEntry.getRepositoryEntryId(), repositoryEntry.getUuid(), newRepositoryEntry };
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(ExtRepositoryFileEntry extRepositoryFileEntry)
			throws PortalException, SystemException {
		_log.debug("getExtRepositoryFileVersions");

		WebdavFile webdavDocument = (WebdavFile) extRepositoryFileEntry;
		List<ExtRepositoryFileVersion> extRepositoryFileVersions = new ArrayList<ExtRepositoryFileVersion>();
		extRepositoryFileVersions.add(new WebdavFileVersion(webdavDocument.getExtRepositoryModelKey()));
		return extRepositoryFileVersions;
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(
			ExtRepositoryObjectType<T> extRepositoryObjectType, String extRepositoryFolderKey) throws PortalException,
			SystemException {
		_log.debug("start getFoldersAndFileEntries");

		//List<T> childrens = (List<T>) OwncloudCacheManager.getFromCache(
			//	OwncloudCacheManager.WEBDAV_CHILDREN_CACHE_NAME, extRepositoryFolderKey);
		List<T> childrens = (List<T>) OwncloudCache.getInstance().getWebdavFiles(extRepositoryFolderKey);
		

		if (childrens == null) {
			childrens = (List<T>) OwncloudRepositoryUtil.getWebdavRepositoryAsUser().getChildrenFromId(1000, 0,
					extRepositoryFolderKey, getGroupId());
			

			OwncloudCache.getInstance().putWebdavFiles(childrens, extRepositoryFolderKey);
			//OwncloudCacheManager.putToCache(OwncloudCacheManager.WEBDAV_CHILDREN_CACHE_NAME, extRepositoryFolderKey,
			//		childrens);
		}

		long startTime = System.nanoTime();
		_log.debug("end getFoldersAndFileEntries time:" + (System.nanoTime() - startTime));

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
			String extRepositoryFolderKey) throws PortalException, SystemException {
		return getExtRepositoryObjects(extRepositoryObjectType, extRepositoryFolderKey).size();
	}

	@Override
	public List<String> getSubfolderKeys(String arg0, boolean arg1) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<ExtRepositorySearchResult<?>> search(SearchContext arg0, Query arg1, ExtRepositoryQueryMapper arg2)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	public ExtRepositoryFileEntry updateExtRepositoryFileEntry(String arg0, String arg1, InputStream arg2)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public String getDownloadLink(long fileEntryId) throws PortalException, SystemException {
		ExtRepositoryFileEntry file = getExtRepositoryObject(ExtRepositoryObjectType.FILE,
				getExtRepositoryObjectKey(fileEntryId));
		return ((WebdavFile) file).getDownloadURL();
	}

	@Override
	public List<Folder> getFolders(long parentFolderId, boolean includeMountFolders, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		System.out.println(getExtRepositoryObjectKey(parentFolderId));
		List<Folder> result = super.getFolders(parentFolderId, includeMountFolders, start, end, obc);

		return result;
	}

}
