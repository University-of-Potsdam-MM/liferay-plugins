/**
 * Copyright (c) 2012 TomÃƒÂ¡Ã…Â¡ PoleÃ…Â¡ovskÃƒÂ½
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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.RepositoryEntryUtil;
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
import com.liferay.repository.external.model.ExtRepositoryFileVersionAdapter;
import com.liferay.repository.external.model.ExtRepositoryFolderAdapter;
import com.liferay.repository.external.model.ExtRepositoryObjectAdapter;
import com.liferay.repository.external.search.ExtRepositoryQueryMapper;

import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;
import de.unipotsdam.elis.webdav.WebdavFile;
import de.unipotsdam.elis.webdav.WebdavFileVersion;
import de.unipotsdam.elis.webdav.WebdavObject;
import de.unipotsdam.elis.webdav.WebdavFolder;
import de.unipotsdam.elis.webdav.WebdavObjectStore;

/**
 * @author Tomas Polesovsky
 */
// TODO: PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE - check ACCESS + VIEW
// on the parent folder
public class OwncloudRepository extends ExtRepositoryAdapter implements ExtRepository{
	
	//public final static String CACHE_NAME = "WebDavCache";

	private static Log _log = LogFactoryUtil.getLog(OwncloudRepository.class);

	public OwncloudRepository() {
		super(null);
	}

	public static synchronized WebdavObjectStore getWebdavRepository()  {
		_log.debug("start getWebdavRepository");
		
		String username = WebdavConfigurationLoader.getRootUsername();
		String password = WebdavConfigurationLoader.getRootPassword();
		WebdavObjectStore result = new WebdavObjectStore(username, password);
		_log.debug("end getWebdavRepository");
		return result;
	}

	@Override
	public void initRepository(UnicodeProperties typeSettingsProperties,
			CredentialsProvider credentialsProvider) throws PortalException, SystemException {
		_log.debug("initRepository started. folder to create: +" + getRootFolderKey());
		getWebdavRepository().createFolder(getRootFolderKey());
		createShareInOwncloud();
		_log.debug("initRepository finished");
	}
/*
	public FileSystemRepositoryEnvironment getEnvironment() {
		return environment;
	}

	@Override
	public List<Object> getFoldersAndFileEntries(long folderId, int start, int end, OrderByComparator obc)
			throws SystemException {
		_log.debug("start getFoldersAndFileEntries");
		long startTime = System.nanoTime();
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;

		List<Object> result = new ArrayList<Object>();
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
			WebdavFolderImpl systemFolder = (WebdavFolderImpl) folderIdToFile(folderId);
			if (systemFolder.canRead()) { 
				for (WebdavObject file : loadFilesFromDisk(systemFolder, 0)) {
					// if (file.canRead()) {
					if (file instanceof WebdavFolderImpl) {
						Folder f = fileToFolder(file);
						if (f != null) {
							result.add(f);
						}
					} else {
						FileEntry f = fileToFileEntry(file);
						if (f != null) {
							result.add(f);
						}
					}
					// }
					if (obc == null && result.size() > end) {
						_log.debug("end getFoldersAndFileEntries time:" + (System.nanoTime() - startTime));
						return result.subList(start < 0 ? 0 : start, end > result.size() ? result.size() : end);
					}
				}
			}

		} catch (PortalException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}

		if (obc != null) {
			Collections.sort(result, obc);
		}
		result = result.subList(start < 0 ? 0 : start, end > result.size() ? result.size() : end);

		_log.debug("end getFoldersAndFileEntries time:" + (System.nanoTime() - startTime));
		return result;
	}

	@Override
	public List<Object> getFoldersAndFileEntries(long folderId, String[] mimeTypes, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		return getFoldersAndFileEntries(folderId, start, end, obc);
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId) throws SystemException {
		try {
			_log.debug("start getFoldersAndFileEntriesCount: " + folderId);
			WebdavObject dir = folderIdToFile(folderId);
			if (dir == null) {
				return 0;
			}
			int result = loadFilesFromDisk(dir, 0).size();
			_log.debug("end getFoldersAndFileEntriesCount: " + folderId + " size: " + result);
			return result;
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	@Override
	public int getFoldersAndFileEntriesCount(long folderId, String[] mimeTypes) throws PortalException, SystemException {
		return getFoldersAndFileEntriesCount(folderId);
	}	*/

	public ExtRepositoryFileEntry addExtRepositoryFileEntry(
			String extRepositoryParentFolderKey, String mimeType, String title,
			String description, String changeLog, InputStream inputStream)
		throws PortalException, SystemException { 
		
		WebdavFolder directory = new WebdavFolder(extRepositoryParentFolderKey);
		
		if (directory.exists(getWebdavRepository()) ) {
			// Fileable file = new File(directory, sourceFileName);
			WebdavFile documentImpl = new WebdavFile(directory.getExtRepositoryModelKey() + title);
			try {
				documentImpl = createFileWithInputStream(title, inputStream,  documentImpl);
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
	public ExtRepositoryFolder addExtRepositoryFolder(
			String extRepositoryParentFolderKey, String name,
			String description) throws PortalException,
			SystemException {
		 getWebdavRepository().createFolder(name, extRepositoryParentFolderKey);
		// TODO Auto-generated method stub
		return null;
	}
/*
	public Folder addExtRepositoryFolder(long parentFolderId, String title, String description, ServiceContext serviceContext)
			throws PortalException, SystemException {
		_log.debug("start addFolder");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), parentFolderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), parentFolderId, ActionKeys.ADD_SUBFOLDER);
		WebdavFolderImpl subDir = (WebdavFolderImpl) folderIdToFile(parentFolderId);
		if (subDir.exists(getWebdavRepository()) && subDir.canWrite()) {
			// folder.mkdir();
			WebdavFolderImpl folder = getWebdavRepository().createFolder(title, subDir.getExtRepositoryModelKey());
			Folder result = fileToFolder(folder);
			;
			_log.debug("end addFolder");
			return result;
		} else {
			throw new SystemException("Parent directory " + subDir + " cannot be read!");
		}
	}*/
	
	@Override
	public void checkInExtRepositoryFileEntry(
		String extRepositoryFileEntryKey, boolean createMajorVersion,
		String changeLog) {

		throw new UnsupportedOperationException();
	}
	


	@Override
	public ExtRepositoryFileEntry checkOutExtRepositoryFileEntry(
		String extRepositoryFileEntryKey) {

		throw new UnsupportedOperationException();
	}
	
	@Override
	public <T extends ExtRepositoryObject> T copyExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFileEntryKey, String newExtRepositoryFolderKey,
			String newTitle) throws PortalException, SystemException{
		WebdavObject srcFile = (WebdavFile) new WebdavObject(extRepositoryFileEntryKey);
		WebdavFolder destDir = (WebdavFolder) new WebdavFolder(newExtRepositoryFolderKey);
		if (!srcFile.exists()) {
			throw new SystemException("Source file " + srcFile + " cannot be read!");
		}
		if (!destDir.exists(getWebdavRepository()) ) {
			throw new SystemException("Cannot write into destination directory " + destDir);
		}
		String newFileId;
		try {
			newFileId = destDir.getExtRepositoryModelKey() + srcFile.getName();
			getWebdavRepository().copy(srcFile.getExtRepositoryModelKey(), newFileId);
			// StreamUtil.transfer(new FileInputStream(srcFile),
			// new FileOutputStream(dstFile), true);
			// return fileToFileEntry(dstFile);
		} catch (Exception ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}
		T extRepositoryObject = null;
		// Fileable dstFile = new File(destDir, srcFile.getName());
		if (extRepositoryObjectType.equals(
				ExtRepositoryObjectType.FOLDER)){
			extRepositoryObject = (T) new WebdavFolder(newFileId);
		}
		else {
			extRepositoryObject = (T) new WebdavFile(newFileId);
		}
		return extRepositoryObject;
	}	
	
	@Override
	public void deleteExtRepositoryObject(
			ExtRepositoryObjectType<? extends ExtRepositoryObject>
				extRepositoryObjectType,
			String extRepositoryObjectKey)
		throws PortalException, SystemException {
		
		WebdavObject webdavObject = new WebdavObject(extRepositoryObjectKey);
		if (!webdavObject.exists()) {
			throw new SystemException("File doesn't exist or cannot be modified " + webdavObject);
		}
		webdavObject.delete();
	}
	
	@Override
	public String getAuthType() {
		return null;
	}
	
	@Override
	public InputStream getContentStream(
			ExtRepositoryFileEntry extRepositoryFileEntry)
		throws PortalException, SystemException {
		try {
			return new URL(((WebdavFile)extRepositoryFileEntry).getDownloadURL()).openStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public InputStream getContentStream(ExtRepositoryFileVersion extRepositoryFileVersion) throws PortalException, SystemException {
		try {
			return new URL(((WebdavFileVersion)extRepositoryFileVersion).getDownloadURL()).openStream();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey){

		T extRepositoryObject = null;
		
		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER)){
			extRepositoryObject = (T) new WebdavFolder(extRepositoryObjectKey);
		}
		else {
			extRepositoryObject = (T) new WebdavFile(extRepositoryObjectKey);
		}
		return extRepositoryObject;
	}
	
	@Override
	public ExtRepositoryFolder getExtRepositoryParentFolder(
			ExtRepositoryObject extRepositoryObject)
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
		try {
			Group group = GroupLocalServiceUtil.getGroup(getGroupId());
			String groupName = "liferay_workspace_" + group.getDescriptiveName();
			return "/" + groupName + "/";
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return StringPool.BLANK;
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
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryObjectKey, String newExtRepositoryFolderKey,
			String newTitle)
		throws PortalException, SystemException {
		_log.debug("start moveFileEntry");
		WebdavObject fileToMove = new WebdavObject(extRepositoryObjectKey);
		WebdavFolder parentFolder = new WebdavFolder(newExtRepositoryFolderKey);
		WebdavFile dstFile = new WebdavFile(parentFolder.getExtRepositoryModelKey() + fileToMove.getName());

		if (!fileToMove.exists()) {
			throw new SystemException("Source file doesn't exist: " + fileToMove);
		}
		if (!parentFolder.exists(getWebdavRepository())) {
			throw new SystemException("Destination parent folder doesn't exist: " + parentFolder);
		}
		if (dstFile.exists()) {
			throw new SystemException("Destination file does exist: " + dstFile);
		}
			fileToMove.renameTo(dstFile);


			return (T) dstFile;
	}
/*
	public List<FileEntry> getFileEntries(long folderId, int start, int end, OrderByComparator obc)
			throws SystemException {
		_log.debug("start getFileEntries");
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
		} catch (PrincipalException ex) {
			throw new SystemException(ex);
		}
		List<FileEntry> result = new ArrayList<FileEntry>();

		long startTime = System.nanoTime();
		try {
			WebdavFolderImpl systemFolder = (WebdavFolderImpl) folderIdToFile(folderId);
			if (systemFolder.canRead()) {
				for (WebdavObject file : loadFilesFromDisk(systemFolder, 2)) {
					if (!(file instanceof WebdavFolderImpl)) {
						FileEntry f = fileToFileEntry(file);
						if (f != null) {
							result.add(f);
						}
					}
					if (obc == null && result.size() > end) {
						_log.debug("end getFileEntries time:" + (System.nanoTime() - startTime));
						return result.subList(start < 0 ? 0 : start, end > result.size() ? result.size() : end);
					}
				}
			}

		} catch (PortalException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}

		if (obc != null) {
			Collections.sort(result, obc);
		}
		result = result.subList(start < 0 ? 0 : start, end > result.size() ? result.size() : end);
		_log.debug("end getFileEntries time:" + (System.nanoTime() - startTime));
		return result;

	}*/
/*
	public int getFileEntriesCount(long folderId) throws SystemException {
		_log.debug("start getFileEntriesCount");
		try {
			WebdavObject dir = folderIdToFile(folderId);
			if (dir == null) {
				return 0;
			}
			int result = loadFilesFromDisk(dir, 2).size();
			_log.debug("end getFileEntriesCount");
			return result;
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}


	public FileEntry getFileEntry(long fileEntryId) throws PortalException, SystemException {
		_log.debug("start getFileEntry 1");
		 
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileEntryId, ActionKeys.VIEW);
		FileEntry result = getFromCacheFile(String.valueOf(fileEntryId));
		if (result == null){
			result = fileToFileEntry(fileEntryIdToFile(fileEntryId));
			if (result.getContentStream() != null)
				putToCacheFile(String.valueOf(fileEntryId), result);
		}
		_log.debug("end getFileEntry 1");
		return result;
	}

	public FileEntry getFileEntry(long folderId, String title) throws PortalException, SystemException {
		_log.debug("start getFileEntry 2");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
		FileEntry entry = fileToFileEntry(new WebdavDocumentImpl(folderIdToFile(folderId).getExtRepositoryModelKey() + title));
		if (entry == null) {
			throw new PrincipalException();
		}
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), entry.getFileEntryId(), ActionKeys.VIEW);
		_log.debug("end getFileEntry 2");
		return entry;
	}

	public FileEntry getFileEntryByUuid(String uuid) throws PortalException, SystemException {
		try {
			RepositoryEntry repositoryEntry = RepositoryEntryUtil.findByUUID_G(uuid, getGroupId());

			LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), repositoryEntry.getRepositoryEntryId(),
					ActionKeys.VIEW);

			return getFileEntry(repositoryEntry.getRepositoryEntryId());
		} catch (NoSuchRepositoryEntryException nsree) {
			throw new NoSuchFileEntryException(nsree);
		} catch (SystemException se) {
			throw se;
		} catch (Exception e) {
			throw new RepositoryException(e);
		}

	}

	public FileVersion getFileVersion(long fileVersionId) throws PortalException, SystemException {
		_log.debug("start getFileVersion");
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileVersionId, ActionKeys.VIEW);
		FileVersion result = fileToFileVersion(fileVersionIdToFile(fileVersionId));
		_log.debug("end getFileVersion");
		
		return result;
	}

	public Folder getFolder(long folderId) throws PortalException, SystemException {
		_log.debug("start getFolder");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
		Folder result = fileToFolder(folderIdToFile(folderId));
		_log.debug("end getFolder");
		return result;
	}

	public Folder getFolder(long parentFolderId, String title) throws PortalException, SystemException {
		_log.debug("start getFolder with parent");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), parentFolderId, ActionKeys.VIEW);
		Folder f = fileToFolder(new WebdavFolderImpl(folderIdToFile(parentFolderId).getExtRepositoryModelKey() + title));
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), f.getFolderId(), ActionKeys.VIEW);
		_log.debug("end getFolder with parent");
		return f;
	}

	public List<Folder> getFolders(long parentFolderId, boolean includeMountFolders, int start, int end,
			OrderByComparator obc) throws PortalException, SystemException {
		_log.debug("start getFolders");
		start = start == QueryUtil.ALL_POS ? 0 : start;
		end = end == QueryUtil.ALL_POS ? Integer.MAX_VALUE : end;
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), parentFolderId, ActionKeys.VIEW);
		String fileSystemDirectory = folderIdToFile(parentFolderId).getExtRepositoryModelKey();
		WebdavFolderImpl dir = new WebdavFolderImpl(fileSystemDirectory);
		if (dir.canRead()) {
			List<Folder> result = new ArrayList<Folder>();
			for (WebdavObject subDir : loadFilesFromDisk(dir, 1)) {
				Folder f = fileToFolder(subDir);
				if (f != null) {
					result.add(f);
				}
			}
			if (obc != null) {
				Collections.sort(result, obc);
			}
			result = result.subList(start < 0 ? 0 : start, end > result.size() ? result.size() : end);
			_log.debug("end getFolders");
			return result;

		}
		_log.debug("end getFolders");
		return new ArrayList<Folder>();
	}

	public int getFoldersCount(long parentFolderId, boolean includeMountfolders) throws PortalException,
			SystemException {
		_log.debug("start getFoldersCount");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), parentFolderId, ActionKeys.VIEW);
		try {
			WebdavObject dir = folderIdToFile(parentFolderId);
			_log.debug("end getFoldersCount");
			int result = loadFilesFromDisk(dir, 1).size();
			_log.debug("end getFoldersCount");
			return result;
		} catch (PortalException e) {
			throw new SystemException(e);
		}
	}

	public int getFoldersFileEntriesCount(List<Long> folderIds, int status) throws SystemException {
		_log.debug("start getFoldersFileEntriesCount");
		int result = 0;
		for (Long folderId : folderIds) {
			if (LocalFileSystemPermissionsUtil.containsFolder(getGroupId(), folderId, ActionKeys.VIEW)) {
				result += getFileEntriesCount(folderId);
			}
		}
		_log.debug("end getFoldersFileEntriesCount");
		return result;
	}

	public List<Folder> getMountFolders(long parentFolderId, int start, int end, OrderByComparator obc)
			throws SystemException {
		_log.debug("start getMountFolders");
		_log.debug("end getMountFolders");
		return new ArrayList<Folder>();
	}

	public int getMountFoldersCount(long parentFolderId) throws SystemException {
		_log.debug("start getMountFoldersCount");
		_log.debug("end getMountFoldersCount");
		return 0;
	}

	public void getSubfolderIds(List<Long> folderIds, long folderId) throws SystemException {
		// TODO: where is it used?
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<Long> getSubfolderIds(long folderId, boolean recurse) throws SystemException {
		_log.debug("start getSubfolderIds");
		try {
			LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
			List<Long> result = new ArrayList();
			List<Folder> folders = getFolders(folderId, false, 0, Integer.MAX_VALUE, null);
			for (Folder folder : folders) {
				if (LocalFileSystemPermissionsUtil.containsFolder(getGroupId(), folder.getFolderId(), ActionKeys.VIEW)) {
					result.add(folder.getFolderId());
					if (recurse) {
						result.addAll(getSubfolderIds(folder.getFolderId(), recurse));
					}
				}
			}
			_log.debug("end getSubfolderIds");
			return result;
		} catch (PortalException ex) {
			throw new SystemException(ex);
		}
	}

	public Lock lockFolder(long folderId) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public Lock lockFolder(long folderId, String owner, boolean inheritable, long expirationTime)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public FileEntry moveFileEntry(long fileEntryId, long newFolderId, ServiceContext serviceContext)
			throws PortalException, SystemException {
		_log.debug("start moveFileEntry");
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileEntryId, ActionKeys.UPDATE);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newFolderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newFolderId, ActionKeys.ADD_DOCUMENT);
		WebdavDocumentImpl fileToMove = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		WebdavFolderImpl parentFolder = (WebdavFolderImpl) folderIdToFile(newFolderId);
		WebdavDocumentImpl dstFile = new WebdavDocumentImpl(parentFolder.getExtRepositoryModelKey() + fileToMove.getName());

		if (!fileToMove.exists()) {
			throw new SystemException("Source file doesn't exist: " + fileToMove);
		}
		if (!parentFolder.exists(getWebdavRepository())) {
			throw new SystemException("Destination parent folder doesn't exist: " + parentFolder);
		}
		if (dstFile.exists()) {
			throw new SystemException("Destination file does exist: " + dstFile);
		}
		if (fileToMove.canWrite() && parentFolder.canWrite()) {
			if (!fileToMove.renameTo(dstFile)) {
				throw new SystemException("Moving was not successful (don't know why) [from, to]: [" + fileToMove
						+ ", " + dstFile + "]");
			}

			RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(fileEntryId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFile);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}

			_log.debug("end moveFileEntry");
			return fileToFileEntry(dstFile);
		} else {
			throw new SystemException("Doesn't have rights to move the file [src, toParentDir]: [" + fileToMove + ", "
					+ parentFolder + "]");
		}
	}

	public Folder moveFolder(long folderId, long newParentFolderId, ServiceContext serviceContext)
			throws PortalException, SystemException {
		_log.debug("start moveFolder");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.UPDATE);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newParentFolderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), newParentFolderId, ActionKeys.ADD_SUBFOLDER);
		WebdavFolderImpl folderToMove = (WebdavFolderImpl) folderIdToFile(folderId);
		WebdavFolderImpl parentFolder = (WebdavFolderImpl) folderIdToFile(newParentFolderId);
		WebdavFolderImpl dstFolder = new WebdavFolderImpl(parentFolder.getExtRepositoryModelKey() + folderToMove.getName());

		if (!getWebdavRepository().exists(folderToMove)) {
			throw new SystemException("Source folder doesn't exist: " + folderToMove);
		}
		if (!getWebdavRepository().exists(parentFolder)) {
			throw new SystemException("Destination parent folder doesn't exist: " + parentFolder);
		}
		if (!getWebdavRepository().exists(parentFolder)) {
			throw new SystemException("Destination folder does exist: " + dstFolder);
		}
		if (folderToMove.canWrite() && parentFolder.canWrite()) {
			getWebdavRepository().rename(folderToMove.getExtRepositoryModelKey(), dstFolder.getExtRepositoryModelKey());

			RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(folderId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFolder);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
			_log.debug("end moveFolder");

			return fileToFolder(dstFolder);
		} else {
			throw new SystemException("Doesn't have rights to move the directory [srcDir, toParentDir]: ["
					+ folderToMove + ", " + parentFolder + "]");
		}
	}

	public Lock refreshFileEntryLock(String lockUuid, long companyId, long expirationTime) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");

	}

	public Lock refreshFolderLock(String lockUuid, long companyId, long expirationTime) throws PortalException,
			SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Lock refreshFileEntryLock(String lockUuid, long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public Lock refreshFolderLock(String lockUuid, long expirationTime) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public void revertFileEntry(long fileEntryId, String version, ServiceContext serviceContext)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public Hits search(long creatorUserId, int status, int start, int end) throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Hits search(long creatorUserId, long folderId, String[] mimeTypes, int status, int start, int end)
			throws PortalException, SystemException {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Hits search(SearchContext searchContext, Query query) throws SearchException {
		// TODO: implement indexing and add specific FILE_SYSTEM key into the
		// query
		System.out.println("Searched: " + query);
		return SearchEngineUtil.search(searchContext, query);
	}

	public void unlockFolder(long folderId, String lockUuid) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public FileEntry updateFileEntry(long fileEntryId, String sourceFileName, String mimeType, String title,
			String description, String changeLog, boolean majorVersion, InputStream is, long size,
			ServiceContext serviceContext) throws PortalException, SystemException {
		_log.debug("start updateFileEntry");
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileEntryId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFileEntry(getGroupId(), fileEntryId, ActionKeys.UPDATE);
		WebdavDocumentImpl file = (WebdavDocumentImpl) fileEntryIdToFile(fileEntryId);
		WebdavDocumentImpl dstFile = new WebdavDocumentImpl(WebdavIdDecoderAndEncoder.decodedIdToEncodedParentName(file
				.getExtRepositoryModelKey()) + title);
		boolean toRename = false;
		if (!file.canWrite()) {
			throw new SystemException("Cannot modify file: " + file);
		}
		if (Validator.isNotNull(title) && !title.equals(file.getName())) {
			if (getWebdavRepository().exists(dstFile)) {
				throw new SystemException("Destination file already exists: " + dstFile);
			}
			toRename = true;
		}
		if (size > 0) {

			// StreamUtil.transfer(is, new FileOutputStream(file));
			createFileWithInputStream(mimeType, title, is, size, dstFile);

		}
		if (toRename) {
			getWebdavRepository().rename(file.getExtRepositoryModelKey(), dstFile.getExtRepositoryModelKey());

			RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(fileEntryId);
			RepositoryEntryUtil.update(repositoryEntry);
			try {
				saveFileToExpando(repositoryEntry, dstFile);
			} catch (FileNotFoundException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
		}
		FileEntry result = fileToFileEntry(dstFile);
		_log.debug("end updateFileEntry");
		return result;
	}*/

	private WebdavFile createFileWithInputStream(String title, InputStream is, 
			WebdavFile dstFile) throws PortalException, SystemException {
		_log.debug("start createFileWithInputStream");
		getWebdavRepository().createFile(title, dstFile.getParentFolder().getExtRepositoryModelKey(), is);
		_log.debug("end createFileWithInputStream");
		return dstFile;
	}

	/*
	public Folder updateFolder(long folderId, String title, String description, ServiceContext serviceContext)
			throws PortalException, SystemException {
		_log.debug("start updateFolder");
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.VIEW);
		LocalFileSystemPermissionsUtil.checkFolder(getGroupId(), folderId, ActionKeys.UPDATE);
		if (title.contains(File.separator)) {
			throw new SystemException("Invalid character " + File.separator + " in the title! [title]: [" + title + "]");
		}
		WebdavFolderImpl folder = (WebdavFolderImpl) folderIdToFile(folderId);
		if (!getWebdavRepository().exists(folder) || !folder.canWrite()) {
			throw new SystemException("Folder doesn't exist or cannot be changed: " + folder);
		}
		WebdavObject newFolder = new WebdavFolderImpl(folder.getParentId() + title);
		LocalFileSystemRepository.getWebdavRepository().rename(folder.getExtRepositoryModelKey(), newFolder.getExtRepositoryModelKey());
		Folder result = fileToFolder(newFolder);
		_log.debug("end updateFolder");
		return result;
	}

	public boolean verifyFileEntryCheckOut(long fileEntryId, String lockUuid) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	public boolean verifyInheritableLock(long folderId, String lockUuid) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	

	protected RepositoryEntry findEntryFromExpando(WebdavObject file) throws SystemException {
		_log.debug("start findEntryFromExpando");
		String className = RepositoryEntry.class.getName();
		long companyId = getCompanyId();
		ExpandoColumn col = ExpandoColumnLocalServiceUtil.getDefaultTableColumn(companyId, className,
				Constants.ABSOLUTE_PATH);

		DynamicQuery query = DynamicQueryFactoryUtil.forClass(ExpandoValue.class,
				PortalClassLoaderUtil.getClassLoader());
		query.add(RestrictionsFactoryUtil.eq("columnId", col.getColumnId()));

		try {
			query.add(RestrictionsFactoryUtil.eq("data", getCombinedExpandoValue(file)));
		} catch (FileNotFoundException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		List<ExpandoValue> result = (List<ExpandoValue>) ExpandoValueLocalServiceUtil.dynamicQuery(query);
		if (result.size() == 0) {
			_log.debug("end findEntryFromExpando");
			return null;
		}
		long entryId = result.get(0).getClassPK();
		try {
			_log.debug("end findEntryFromExpando");
			return RepositoryEntryUtil.findByPrimaryKey(entryId);
		} catch (NoSuchRepositoryEntryException ex) {
			_log.error(ex);
			throw new SystemException(ex);
		}
	}

	protected RepositoryEntry retrieveRepositoryEntry(WebdavObject file, Class modelClass) throws SystemException {
		_log.debug("start retrieveRepositoryEntry");
		RepositoryEntry repositoryEntry = findEntryFromExpando(file);

		if (repositoryEntry != null) {
			return repositoryEntry;
		}

		long repositoryEntryId = counterLocalService.increment();
		repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);
		repositoryEntry.setGroupId(getGroupId());
		repositoryEntry.setRepositoryId(getRepositoryId());
		repositoryEntry.setMappedId(LocalFileSystemRepository.class.getName() + String.valueOf(repositoryEntryId));
		RepositoryEntryUtil.update(repositoryEntry);
		try {
			saveFileToExpando(repositoryEntry, file);
		} catch (Exception ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		try {
			long userId = UserLocalServiceUtil.getDefaultUserId(getCompanyId());
			if (PermissionThreadLocal.getPermissionChecker() != null) {
				userId = PermissionThreadLocal.getPermissionChecker().getUserId();
			}
			ResourceLocalServiceUtil.addResources(getCompanyId(), getGroupId(), userId, modelClass.getName(),
					repositoryEntryId, false, addGroupPermissions(), addGuestPermissions());
		} catch (PortalException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		_log.debug("end retrieveRepositoryEntry");
		return repositoryEntry;
	}

	public Folder fileToFolder(WebdavObject folder) throws SystemException, PortalException {
		_log.info("fileToFolder started " + folder.getName());
		if (folder.getExtRepositoryModelKey().length() <= getRootFolder(false).getAbsolutePath().length()) {
			Folder mountFolder = DLAppLocalServiceUtil.getMountFolder(getRepositoryId());
			if (!LocalFileSystemPermissionsUtil
					.containsFolder(getGroupId(), mountFolder.getFolderId(), ActionKeys.VIEW)) {
				return null;
			}
			return mountFolder;
		}

		RepositoryEntry entry = retrieveRepositoryEntry(folder, DLFolder.class);
		if (!LocalFileSystemPermissionsUtil.containsFolder(getGroupId(), entry.getRepositoryEntryId(), ActionKeys.VIEW)) {
			return null;
		}

		FileSystemFolder result = new FileSystemFolder(this, entry.getUuid(), entry.getRepositoryEntryId(),
				(WebdavFolderImpl) folder);

		_log.info("fileToFolder finished " + result.getName());

		return result;
	}

	
	public FileVersion fileToFileVersion(WebdavObject file) throws SystemException {
		return fileToFileVersion(file, null);
	}

	public FileVersion fileToFileVersion(WebdavObject file, FileEntry fileEntry) throws SystemException {
		_log.debug("start fileToFileVersion");
		RepositoryEntry entry = retrieveRepositoryEntry(file, DLFileEntry.class);

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(), entry.getRepositoryEntryId(),
				ActionKeys.VIEW)) {
			_log.debug("stop fileToFileVersion");
			return null;
		}

		FileSystemFileVersion fileVersion = new FileSystemFileVersion(this, entry.getRepositoryEntryId(), fileEntry,
				file);
		try {
			//ImageProcessorUtil.generateImages(null, fileVersion);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		_log.debug("end fileToFileVersion");
		return fileVersion;
	}

	public FileEntry fileToFileEntry(WebdavObject file) throws SystemException {
		return fileToFileEntry(file, null);
	}

	public FileEntry fileToFileEntry(WebdavObject file, FileVersion fileVersion) throws SystemException {
		_log.debug("start fileToFileEntry");
		RepositoryEntry entry = retrieveRepositoryEntry(file, DLFileEntry.class);

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(), entry.getRepositoryEntryId(),
				ActionKeys.VIEW)) {
			_log.debug("stop fileToFileEntry");
			return null;
		}
		System.out.println(file.getName());
		FileSystemFileEntry fileEntry = new FileSystemFileEntry(this, entry.getUuid(), entry.getRepositoryEntryId(),
				null, (WebdavDocumentImpl) file, fileVersion);

		try {
			long userId = PrincipalThreadLocal.getUserId();
			if (userId == 0) {
				userId = UserLocalServiceUtil.getDefaultUserId(getCompanyId());
			}
			dlAppHelperLocalService.checkAssetEntry(userId, fileEntry, fileEntry.getFileVersion());
		} catch (Exception e) {
			_log.error("Unable to update asset", e);
		}

		_log.debug("stop fileToFileEntry");
		return fileEntry;
	}

	protected WebdavObject fileEntryIdToFile(long fileEntryId) throws PortalException, SystemException {
		_log.debug("start fileEntryIdToFile");

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(fileEntryId);

		if (repositoryEntry == null) {
			throw new NoSuchFileEntryException("No LocalFileSystem file entry with {fileEntryId=" + fileEntryId + "}");
		}
		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(), repositoryEntry.getRepositoryEntryId(),
				ActionKeys.VIEW)) {
			_log.debug("stop fileEntryIdToFile");
			return null;
		}

		try {
			_log.debug("stop fileEntryIdToFile");
			return getFileFromRepositoryEntry(repositoryEntry);
		} catch (FileNotFoundException ex) {
			RepositoryEntryUtil.remove(repositoryEntry.getRepositoryEntryId());
			throw new NoSuchFolderException("File is no longer present on the file system!", ex);
		}
	}

	protected WebdavObject fileVersionIdToFile(long fileVersionId) throws PortalException, SystemException {
		_log.debug("start fileVersionIdToFile");

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(fileVersionId);

		if (repositoryEntry == null) {
			throw new NoSuchFileVersionException("No LocalFileSystem file version with {fileVersionId=" + fileVersionId
					+ "}");
		}

		if (!LocalFileSystemPermissionsUtil.containsFileEntry(getGroupId(), repositoryEntry.getRepositoryEntryId(),
				ActionKeys.VIEW)) {
			_log.debug("stop fileVersionIdToFile");
			return null;
		}

		try {
			_log.debug("stop fileVersionIdToFile");
			return getFileFromRepositoryEntry(repositoryEntry);
		} catch (FileNotFoundException ex) {
			RepositoryEntryUtil.remove(repositoryEntry.getRepositoryEntryId());
			throw new NoSuchFolderException("File is no longer present on the file system!", ex);
		}
	}

	protected WebdavObject folderIdToFile(long folderId) throws PortalException, SystemException {
		_log.info("folderIdToFile started " + folderId);

		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByPrimaryKey(folderId);

		if (repositoryEntry != null) {

			if (!LocalFileSystemPermissionsUtil.containsFolder(getGroupId(), repositoryEntry.getRepositoryEntryId(),
					ActionKeys.VIEW)) {
				return null;
			}

			try {
				WebdavObject result = getFileFromRepositoryEntry(repositoryEntry);
				_log.debug("RepositoryEntry" + result.getName());
				return result;
			} catch (FileNotFoundException ex) {
				RepositoryEntryUtil.remove(repositoryEntry.getRepositoryEntryId());
				throw new NoSuchFolderException("Folder is no longer present on the file system!", ex);
			}
		}

		DLFolder dlFolder = DLFolderUtil.fetchByPrimaryKey(folderId);

		if (dlFolder == null) {
			throw new NoSuchFolderException("No LocalFileSystem folder with {folderId=" + folderId + "}");
		} else if (!dlFolder.isMountPoint()) {
			throw new RepositoryException("LocalFileSystem repository should not be used with {folderId=" + folderId
					+ "}");
		}
		_log.debug("DLFolder " + dlFolder.getName() + " " + dlFolder.getRepositoryId()+ " " + dlFolder.getParentFolderId()+ " " + getAbsolutePath(dlFolder));

		//repositoryEntry = retrieveRepositoryEntry(getRootFolder(), DLFolder.class);

		WebdavFolderImpl result = getRootFolder(true);
		_log.info("folderIdToFile finished");
		return result;

	}
	
	private String getAbsolutePath(DLFolder dlFolder) throws PortalException, SystemException{
		return getAbsolutePathRec(dlFolder, new StringBuilder());
	}
	
	private String getAbsolutePathRec(DLFolder dlFolder, StringBuilder sb) throws PortalException, SystemException{
		sb.insert(0, "/" + dlFolder.getName());
		DLFolder parent = dlFolder.getParentFolder();
		if (parent == null)
			return sb.toString();
		else
			return getAbsolutePathRec(parent,sb);
			
	}

	protected WebdavObject getFileFromRepositoryEntry(RepositoryEntry entry) throws FileNotFoundException, SystemException,
			PortalException {
		return getFileFromExpando(entry);
	}

	protected String getCombinedExpandoValue(WebdavObject file) throws FileNotFoundException {
		_log.info("getCombinedExpandoValue started");
		String relativePath = file.getExtRepositoryModelKey();
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("repositoryId", getRepositoryId());
		jsonObject.put("relativePath", relativePath);
		if (file instanceof WebdavDocumentImpl){
			jsonObject.put("size", ((WebdavDocumentImpl)file).getSize());
		}
		_log.info("getCombinedExpandoValue finished");
		return jsonObject.toString();
	}

	protected void saveFileToExpando(RepositoryEntry entry, WebdavObject file) throws FileNotFoundException,
			SystemException, PortalException {
		ExpandoValueLocalServiceUtil.addValue(getCompanyId(), expandoColumn.getTableId(), expandoColumn.getColumnId(),
				entry.getPrimaryKey(), getCombinedExpandoValue(file));
	}

	// TODO: remove (moved to RepositoryHelper)
	protected WebdavObject getFileFromExpando(RepositoryEntry entry) throws FileNotFoundException, SystemException,
			PortalException {
		_log.debug("start getFileFromExpando");
		ExpandoValue expandoValue = ExpandoValueLocalServiceUtil.getValue(expandoColumn.getTableId(),
				expandoColumn.getColumnId(), entry.getPrimaryKey());
		if (expandoValue == null) {
			throw new IllegalStateException("Database is corrupted! Please recreate this repository!");
		}
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(expandoValue.getString());
		String file = jsonObject.getString("relativePath","").replace("-2F", "-%2F");
		if (file.equals("")) {
			throw new RuntimeException("There is no absolute path in Expando for Repository Entry [id]: ["
					+ entry.getRepositoryEntryId() + "]");
		}
		// File f = new File(getRootFolder(), file);
		// if (!f.exists()) {
		// throw new
		// FileNotFoundException("File no longer exists on the file system: " +
		// f.getAbsolutePath());
		// }
		// return getRootFolder();
		WebdavObject result = getWebdavRepository().getObjectById(file);
		_log.debug("stop getFileFromExpando");
		return  result;
	}

	public WebdavFolderImpl getRootFolder(boolean update) {
		_log.info("getRootFolder started");*/
		/*
		 * String file = getTypeSettingsProperties().getProperty(ROOT_FOLDER);
		 * if (file == null) { throw new RuntimeException(
		 * "There is no ROOT_FOLDER configured for the repository [repositoryId]: ["
		 * + getRepositoryId() + "]"); }
		 */
		/*
		 * for (StackTraceElement s : Thread.currentThread().getStackTrace()) {
		 * System.out.println(s.toString()); } System.out.println(" ");
		 */
/*
		try {
			WebdavFolderImpl rootFolder = new WebdavFolderImpl(getSiteName());
			//final String rootDefaultPath = getSiteName();
			//rootFolder.setId(WebdavIdDecoderAndEncoder.encode(rootDefaultPath));
			if (update) {
				//getWebdavRepository().createFolder(rootDefaultPath);
				// createShareInOwncloud();
			}
			_log.info("getRootFolder finished");
			return rootFolder;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/

	
	private synchronized void createShareInOwncloud() throws SystemException, PortalException {
		_log.debug("start createShareInOwncloud");

		final List<User> users = UserLocalServiceUtil.getGroupUsers(this.getGroupId());
		final Set<String> userStrings = new HashSet<String>();

		for (User user : users) {
			userStrings.add(user.getLogin());
		}

		final String username = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId()).getLogin();
		final String password = PrincipalThreadLocal.getPassword();
		final WebdavObjectStore objectStore = getWebdavRepository();

		Thread shareCreatorthread = new Thread(new Runnable() {

			@Override
			public void run() {

				_log.debug("start runnable");
				OwncloudService owncloudService = new OwncloudService(objectStore);
				owncloudService.createShare(getRepositoryId() + "", getRootFolderKey(), userStrings, username,
						password);
				_log.debug("end runnable");
			}
		});
		shareCreatorthread.start();
		
		_log.debug("stop createShareInOwncloud");
	}/*

	public boolean addGuestPermissions() {
		return GetterUtil.getBoolean(getTypeSettingsProperties().getProperty(ADD_GUEST_PERMISSIONS), true);
	}

	public boolean addGroupPermissions() {
		return GetterUtil.getBoolean(getTypeSettingsProperties().getProperty(ADD_GROUP_PERMISSIONS), true);
	}

	protected List<WebdavObject> loadFilesFromDisk(WebdavObject dir, final int type) {
		_log.debug("start loadFilesFromDisk");
		List<WebdavObject> result = new ArrayList<WebdavObject>();
		// if(!dir.canRead()){
		// return result;
		// }
		String cacheKey = dir.getExtRepositoryModelKey();
		List<WebdavObject> cached = getFromCache(cacheKey);
		if (cached == null) {
			_log.debug("cache null " + dir.getExtRepositoryModelKey());
			cached = getWebdavRepository().getChildrenForName(1000, 0, dir.getExtRepositoryModelKey());
			putToCache(cacheKey, cached);
		} else {
			_log.debug("cache not null " + dir.getExtRepositoryModelKey());
		}
		for (WebdavObject f : cached) {
			switch (type) {
			case 2: {
				if (!(f instanceof WebdavFolderImpl)) {
					result.add(f);
				}
				break;
			}
			case 1: {
				if ((f instanceof WebdavFolderImpl)) {
					result.add(f);
				}
				break;
			}
			case 0:
			default: {
				result.add(f);
				break;
			}
			}
		}
		_log.debug("stop loadFilesFromDisk");
		return result;
	}*/

	/*
	 * protected List<FileEntry> getFileEntriesFromDisk(File dir) throws
	 * SystemException { List<FileEntry> result = new ArrayList<FileEntry>();
	 * if(dir == null){ return result; } List cached =
	 * getFromCache(dir.getAbsolutePath()); if(cached != null){ return cached; }
	 * 
	 * if (dir.canRead()) { File[] files = loadFilesFromDisk(dir, 2); for (File
	 * file : files) { FileEntry f = fileToFileEntry(file); if (f != null) {
	 * result.add(f); } } }
	 * 
	 * }
	 * 
	 * protected List<Folder> getFoldersFromDisk(File dir) throws
	 * SystemException, PortalException { List<Folder> result = new
	 * ArrayList<Folder>(); if(dir == null){ return result; } List cached =
	 * getFromCache(dir.getAbsolutePath()); if(cached != null){ return cached; }
	 * 
	 * if (dir.canRead()) { File[] subDirectories = loadFilesFromDisk(dir, 1);
	 * for (File subDir : subDirectories) { Folder f = fileToFolder(subDir); if
	 * (f != null) { result.add(f); } } }
	 * 
	 * putToCache(dir.getAbsolutePath(), result); return result; }
	 */
	/*
	protected List<WebdavObject> getFromCache(String cacheKey) {
		ThreadLocalCache<List<WebdavObject>> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, CACHE_NAME);
		return threadLocalCache != null ? threadLocalCache.get(cacheKey) : null;
	}

	protected void putToCache(String cacheKey, List<WebdavObject> value) {
		ThreadLocalCache<List<WebdavObject>> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, CACHE_NAME);
		threadLocalCache.put(cacheKey, value);
	}*/
	

	protected FileEntry getFromCacheFile(String cacheKey) {
		ThreadLocalCache<FileEntry> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, "file");
		return threadLocalCache != null ? threadLocalCache.get(cacheKey) : null;
	}

	protected void putToCacheFile(String cacheKey, FileEntry value) {
		ThreadLocalCache<FileEntry> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(
				Lifecycle.REQUEST, "file");
		threadLocalCache.put(cacheKey, value);
	}


	@Override
	public ExtRepositoryFileVersion cancelCheckOut(String arg0) throws PortalException, SystemException {

		throw new UnsupportedOperationException();
	}

	

	@Override
	public ExtRepositoryFileVersion getExtRepositoryFileVersion(ExtRepositoryFileEntry extRepositoryFileEntry, String version)
			throws PortalException, SystemException {
		WebdavFile webdavDocument = (WebdavFile)extRepositoryFileEntry;
		return new WebdavFileVersion(webdavDocument.getExtRepositoryModelKey());
	}

	@Override
	public ExtRepositoryFileVersionDescriptor getExtRepositoryFileVersionDescriptor(String extRepositoryFileVersionKey) {
		String[] extRepositoryFileVersionKeyParts = StringUtil.split(
				extRepositoryFileVersionKey, StringPool.COLON);

			String extRepositoryFileEntryKey = extRepositoryFileVersionKeyParts[0];
			String version = extRepositoryFileVersionKeyParts[2];

			return new ExtRepositoryFileVersionDescriptor(
				extRepositoryFileEntryKey, version);
	}
	
	@Override
	public Object[] getRepositoryEntryIds(String objectId) throws SystemException {
		_log.debug("objectId: " + objectId);
		boolean newRepositoryEntry = false;
		RepositoryEntry repositoryEntry = RepositoryEntryUtil.fetchByR_M(
			getRepositoryId(), objectId);

		if (repositoryEntry == null) {
			long repositoryEntryId = counterLocalService.increment();

			repositoryEntry = RepositoryEntryUtil.create(repositoryEntryId);

			repositoryEntry.setGroupId(getGroupId());
			repositoryEntry.setRepositoryId(getRepositoryId());
			repositoryEntry.setMappedId(objectId);

			RepositoryEntryUtil.update(repositoryEntry);

			newRepositoryEntry = true;
		}

		return new Object[] {
			repositoryEntry.getRepositoryEntryId(), repositoryEntry.getUuid(),
			newRepositoryEntry
		};
	}

	@Override
	public List<ExtRepositoryFileVersion> getExtRepositoryFileVersions(ExtRepositoryFileEntry extRepositoryFileEntry)
			throws PortalException, SystemException {
		
		WebdavFile webdavDocument = (WebdavFile)extRepositoryFileEntry;
			List<ExtRepositoryFileVersion> extRepositoryFileVersions =
				new ArrayList<ExtRepositoryFileVersion>();
			extRepositoryFileVersions.add(new WebdavFileVersion(webdavDocument.getExtRepositoryModelKey()));
		return extRepositoryFileVersions;
	}

	@Override
	public <T extends ExtRepositoryObject> T getExtRepositoryObject(
			ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey, String title) throws PortalException, SystemException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T extends ExtRepositoryObject> List<T> getExtRepositoryObjects(ExtRepositoryObjectType<T> extRepositoryObjectType,
			String extRepositoryFolderKey)
			throws PortalException, SystemException {
		_log.debug("start getFoldersAndFileEntries");
		
		List<T> childrens = (List<T>) getWebdavRepository().getChildrenForName(1000,0,extRepositoryFolderKey);

		long startTime = System.nanoTime();
		_log.debug("end getFoldersAndFileEntries time:" + (System.nanoTime() - startTime));
		
		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.OBJECT))
			return childrens;
		
		List<T> result = new ArrayList<T>();
		
		if (extRepositoryObjectType.equals(ExtRepositoryObjectType.FOLDER)){
			for (T child : childrens){
				if (child instanceof WebdavFolder)
					result.add(child);
			}
		}
		else {
			for (T child : childrens){
				if (child instanceof WebdavFile)
					result.add(child);
			}
		}

		return result;
	}

	@Override
	public int getExtRepositoryObjectsCount(ExtRepositoryObjectType<? extends ExtRepositoryObject> extRepositoryObjectType, String extRepositoryFolderKey)
			throws PortalException, SystemException {
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

}
