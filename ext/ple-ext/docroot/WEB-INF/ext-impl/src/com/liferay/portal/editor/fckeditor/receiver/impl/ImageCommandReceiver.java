/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.editor.fckeditor.receiver.impl;

import com.liferay.portal.editor.fckeditor.command.CommandArgument;
import com.liferay.portal.editor.fckeditor.exception.FCKException;
import com.liferay.portal.kernel.io.ByteArrayFileInputStream;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.repository.liferayrepository.model.LiferayFolder;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.upload.LiferayFileItem;
import com.liferay.portal.upload.LiferayFileItemFactory;
import com.liferay.portal.upload.LiferayFileUpload;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.impl.DLFolderImpl;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.portlet.PortletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * @author Ivica Cardic
 */
public class ImageCommandReceiver extends BaseCommandReceiver {

	@Override
	protected String createFolder(CommandArgument commandArgument) {
		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(group.getGroupId(), StringPool.SLASH
					+ commandArgument.getCurrentFolder());

			long repositoryId = folder.getRepositoryId();
			long parentFolderId = folder.getFolderId();
			String name = commandArgument.getNewFolder();
			String description = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFolder(repositoryId, parentFolderId, name,
					description, serviceContext);
		} catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	@Override
	public void fileUpload(CommandArgument commandArgument,
			HttpServletRequest request, HttpServletResponse response) {
		ThemeDisplay themeDisplay = (ThemeDisplay) request
				.getAttribute(WebKeys.THEME_DISPLAY);

		InputStream inputStream = null;

		String returnValue = null;

		// indicates that the image is added by drag and drop
		boolean droppedImage = false;

		try {
			ServletFileUpload servletFileUpload = new LiferayFileUpload(
					new LiferayFileItemFactory(
							UploadServletRequestImpl.getTempDir()), request);

			servletFileUpload.setFileSizeMax(PrefsPropsUtil
					.getLong(PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_MAX_SIZE));

			LiferayServletRequest liferayServletRequest = new LiferayServletRequest(
					request);

			List<FileItem> fileItems = servletFileUpload
					.parseRequest(liferayServletRequest);

			Map<String, Object> fields = new HashMap<String, Object>();

			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					fields.put(fileItem.getFieldName(), fileItem.getString());
				} else {
					fields.put(fileItem.getFieldName(), fileItem);
				}
			}

			DiskFileItem diskFileItem = (DiskFileItem) fields.get("NewFile");

			if (diskFileItem == null){
				diskFileItem = (DiskFileItem) fields.get("upload");
				droppedImage = true;
			}

			String fileName = StringUtil.replace(diskFileItem.getName(),
					CharPool.BACK_SLASH, CharPool.SLASH);
			String[] fileNameArray = StringUtil.split(fileName, '/');
			fileName = fileNameArray[fileNameArray.length - 1];

			String contentType = diskFileItem.getContentType();

			if (Validator.isNull(contentType)
					|| contentType
							.equals(ContentTypes.APPLICATION_OCTET_STREAM)) {

				contentType = MimeTypesUtil.getContentType(diskFileItem
						.getStoreLocation());
			}

			if (diskFileItem.isInMemory()) {
				inputStream = diskFileItem.getInputStream();
			} else {
				inputStream = new ByteArrayFileInputStream(
						diskFileItem.getStoreLocation(),
						LiferayFileItem.THRESHOLD_SIZE);
			}

			long size = diskFileItem.getSize();

			FileEntry newFileEntry = customFileUpload(commandArgument,
					fileName, inputStream, contentType, size);
			Folder folder = _getFolder(commandArgument.getCurrentGroup()
					.getGroupId(), commandArgument.getCurrentFolder());
			returnValue = "0";
			String fileURL = HttpUtil.encodePath("/documents/"
					+ newFileEntry.getGroupId() + StringPool.SLASH
					+ folder.getFolderId() + StringPool.SLASH
					+ newFileEntry.getTitle() + StringPool.SLASH
					+ newFileEntry.getUuid());
			if (droppedImage)
				_writeUploadResponseForDroppedImage(fileName, fileURL, returnValue,
						request.getParameter("CKEditorFuncNum"), response,
						themeDisplay);
			else
				_writeUploadResponse(fileName, fileURL, returnValue, response,
						themeDisplay);
		} catch (Exception e) {
			e.printStackTrace();
			FCKException fcke = null;

			if (e instanceof FCKException) {
				fcke = (FCKException) e;
			} else {
				fcke = new FCKException(e);
			}

			Throwable cause = fcke.getCause();

			returnValue = "203";

			if (cause != null) {
				String causeString = GetterUtil.getString(cause.toString());

				if (causeString.contains("DuplicateFileException")) {
					returnValue = "201";
				} else if (causeString.contains("NoSuchFolderException")
						|| causeString.contains("NoSuchGroupException")) {

					returnValue = "204";
				} else if (causeString.contains("ImageNameException")) {
					returnValue = "205";
				} else if (causeString.contains("FileExtensionException")
						|| causeString.contains("FileNameException")) {

					returnValue = "206";
				} else if (causeString.contains("PrincipalException")) {
					returnValue = "207";
				} else if (causeString.contains("FileSizeException")
						|| causeString.contains("ImageSizeException")
						|| causeString.contains("SizeLimitExceededException")) {

					returnValue = "208";
				} else if (causeString.contains("SystemException")) {
					returnValue = "209";
				} else if (causeString.contains("AssetCategoryException")) {
					returnValue = "212";
				} else if (causeString.contains("AntivirusScannerException")) {
					returnValue = "211";
				} else {
					throw fcke;
				}
			}
			if (droppedImage)
				_writeUploadResponseForDroppedImage("", "", returnValue,
						request.getParameter("CKEditorFuncNum"), response,
						themeDisplay);
			else
				_writeUploadResponse("", "", returnValue, response,
						themeDisplay);
		} finally {
			StreamUtil.cleanUp(inputStream);
		}
	}

	@Override
	protected String fileUpload(CommandArgument commandArgument,
			String fileName, InputStream inputStream, String contentType,
			long size) {

		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(group.getGroupId(),
					commandArgument.getCurrentFolder());

			long repositoryId = folder.getRepositoryId();
			long folderId = folder.getFolderId();
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			DLAppServiceUtil.addFileEntry(repositoryId, folderId, title,
					contentType, title, description, changeLog, inputStream,
					size, serviceContext);
		} catch (Exception e) {
			throw new FCKException(e);
		}

		return "0";
	}

	protected FileEntry customFileUpload(CommandArgument commandArgument,
			String fileName, InputStream inputStream, String contentType,
			long size) {

		try {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(group.getGroupId(),
					commandArgument.getCurrentFolder());

			long repositoryId = folder.getRepositoryId();
			long folderId = folder.getFolderId();
			String title = fileName;
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;

			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);
			serviceContext.setAttribute("imageupload", true);

			return DLAppServiceUtil.addFileEntry(repositoryId, folderId, title,
					contentType, title, description, changeLog, inputStream,
					size, serviceContext);
		} catch (Exception e) {
			throw new FCKException(e);
		}
	}

	protected Element getFileElement(CommandArgument commandArgument,
			Element fileElement, FileEntry fileEntry) throws Exception {

		String name = fileEntry.getTitle();

		String extension = fileEntry.getExtension();

		if (Validator.isNotNull(extension)) {
			String periodAndExtension = StringPool.PERIOD.concat(extension);

			if (!name.endsWith(periodAndExtension)) {
				name = name.concat(periodAndExtension);
			}
		}

		fileElement.setAttribute("name", name);

		String description = fileEntry.getDescription();

		fileElement.setAttribute("desc",
				Validator.isNotNull(description) ? description : name);

		fileElement.setAttribute("size", getSize(fileEntry.getSize()));

		String url = DLUtil.getPreviewURL(fileEntry,
				fileEntry.getFileVersion(), commandArgument.getThemeDisplay(),
				StringPool.BLANK, false, false);

		fileElement.setAttribute("url", url);

		return fileElement;
	}

	protected List<Element> getFileElements(CommandArgument commandArgument,
			Document document, Folder folder) throws Exception {

		List<FileEntry> fileEntries = DLAppServiceUtil.getFileEntries(
				folder.getRepositoryId(), folder.getFolderId());

		List<Element> fileElements = new ArrayList<Element>(fileEntries.size());

		for (FileEntry fileEntry : fileEntries) {
			Element fileElement = document.createElement("File");

			fileElement = getFileElement(commandArgument, fileElement,
					fileEntry);

			fileElements.add(fileElement);
		}

		return fileElements;
	}

	@Override
	protected void getFolders(CommandArgument commandArgument,
			Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
		} catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected void getFoldersAndFiles(CommandArgument commandArgument,
			Document document, Node rootNode) {

		try {
			_getFolders(commandArgument, document, rootNode);
			_getFiles(commandArgument, document, rootNode);
		} catch (PrincipalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}
		} catch (Exception e) {
			throw new FCKException(e);
		}
	}

	@Override
	protected boolean isStagedData(Group group) {
		return group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY);
	}

	private void _getFiles(CommandArgument commandArgument, Document document,
			Node rootNode) throws Exception {

		Element filesElement = document.createElement("Files");

		rootNode.appendChild(filesElement);

		if (Validator.isNull(commandArgument.getCurrentGroupName())) {
			return;
		}

		Group group = commandArgument.getCurrentGroup();

		Folder folder = _getFolder(group.getGroupId(),
				commandArgument.getCurrentFolder());

		List<Element> fileElements = getFileElements(commandArgument, document,
				folder);

		for (Element fileElement : fileElements) {
			filesElement.appendChild(fileElement);
		}
	}

	private Folder _getFolder(long groupId, String folderName) throws Exception {

		DLFolder dlFolder = new DLFolderImpl();

		dlFolder.setFolderId(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		dlFolder.setGroupId(groupId);
		dlFolder.setRepositoryId(groupId);

		Folder folder = new LiferayFolder(dlFolder);

		if (folderName.equals(StringPool.SLASH)) {
			return folder;
		}

		StringTokenizer st = new StringTokenizer(folderName, StringPool.SLASH);

		while (st.hasMoreTokens()) {
			String curFolderName = st.nextToken();

			List<Folder> folders = DLAppServiceUtil.getFolders(
					folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				if (curFolder.getName().equals(curFolderName)) {
					folder = curFolder;

					break;
				}
			}
		}

		return folder;
	}

	private void _getFolders(CommandArgument commandArgument,
			Document document, Node rootNode) throws Exception {

		Element foldersElement = document.createElement("Folders");

		rootNode.appendChild(foldersElement);

		if (commandArgument.getCurrentFolder().equals(StringPool.SLASH)) {
			getRootFolders(commandArgument, document, foldersElement);
		} else {
			Group group = commandArgument.getCurrentGroup();

			Folder folder = _getFolder(group.getGroupId(),
					commandArgument.getCurrentFolder());

			List<Folder> folders = DLAppServiceUtil.getFolders(
					folder.getRepositoryId(), folder.getFolderId());

			for (Folder curFolder : folders) {
				Element folderElement = document.createElement("Folder");

				foldersElement.appendChild(folderElement);

				folderElement.setAttribute("name", curFolder.getName());
			}
		}
	}

	private void _writeUploadResponse(String fileName, String url,
			String returnValue, HttpServletResponse response,
			ThemeDisplay themeDisplay) {

		try {
			JSONObject result = JSONFactoryUtil.createJSONObject();

			if (!returnValue.equals("0")) {
				JSONObject error = JSONFactoryUtil.createJSONObject();
				if (returnValue.equals("207"))
					error.put("message",
							themeDisplay.translate("authentification-failed"));
				else
					error.put("message", themeDisplay.translate("upload-error"));
				result.put("error", error);
				result.put("uploaded", false);
				_log.debug("Upload failed for " + fileName
						+ " with the error code " + returnValue);
			} else {
				result.put("uploaded", true);
				result.put("fileName", fileName);
				result.put("url", url);
			}

			response.setContentType("application/json; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter printWriter = null;

			printWriter = response.getWriter();

			printWriter.print(result.toString());

			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private void _writeUploadResponseForDroppedImage(String fileName, String url,
			String returnValue, String funcNum, HttpServletResponse response,
			ThemeDisplay themeDisplay) {

		try {
			StringBundler sb = new StringBundler(8);
			String message = "";
			String fileUrl = url;
			sb.append("<script type=\"text/javascript\">");
			if (!returnValue.equals("0")) {
				if (returnValue.equals("207"))
					message = themeDisplay.translate("authentification-failed");
				else
					message = themeDisplay.translate("upload-error");
				fileUrl = "";
				_log.debug("Upload failed for " + fileName
						+ " with the error code " + returnValue);
			}

			sb.append("window.parent.CKEDITOR.tools.callFunction('");
			sb.append(funcNum);
			sb.append("','");
			sb.append(fileUrl);
			sb.append("','");
			sb.append(message);
			sb.append("');");
			sb.append("</script>");

			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");

			PrintWriter printWriter = null;

			printWriter = response.getWriter();

			printWriter.print(sb.toString());

			printWriter.flush();
			printWriter.close();
		} catch (Exception e) {
			throw new FCKException(e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ImageCommandReceiver.class);

}