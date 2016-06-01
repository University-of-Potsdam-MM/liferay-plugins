/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package de.unipotsdam.elis.owncloud.service.impl;

import com.liferay.portal.kernel.exception.SystemException;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;
import de.unipotsdam.elis.owncloud.service.base.CustomSiteRootFolderLocalServiceBaseImpl;
import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;

/**
 * The implementation of the custom site root folder local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.owncloud.service.base.CustomSiteRootFolderLocalServiceBaseImpl
 * @see de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil
 */
public class CustomSiteRootFolderLocalServiceImpl extends CustomSiteRootFolderLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil}
	 * to access the custom site root folder local service.
	 */
	public CustomSiteRootFolder addCustomSiteRootFolder(long userId, String originalPath, String customPath)
			throws SystemException {
		CustomSiteRootFolder customSiteRootFolder = customSiteRootFolderPersistence.create(new CustomSiteRootFolderPK(
				userId, originalPath));
		customSiteRootFolder.setCustomPath(customPath);
		super.addCustomSiteRootFolder(customSiteRootFolder);
		return customSiteRootFolder;
	}

	public CustomSiteRootFolder updateCustomSiteRootFolder(long userId, String originalPath, String customPath)
			throws SystemException {
		CustomSiteRootFolder customSiteRootFolder = customSiteRootFolderPersistence
				.fetchByPrimaryKey(new CustomSiteRootFolderPK(userId, originalPath));
		if (customSiteRootFolder == null)
			customSiteRootFolder = customSiteRootFolderPersistence.create(new CustomSiteRootFolderPK(userId,
					originalPath));
		customSiteRootFolder.setCustomPath(customPath);
		super.updateCustomSiteRootFolder(customSiteRootFolder);
		return customSiteRootFolder;
	}
}