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

package de.unipotsdam.elis.owncloud.model.impl;

import com.liferay.portal.kernel.exception.SystemException;

import de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder;
import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;

/**
 * The extended model base implementation for the CustomSiteRootFolder service. Represents a row in the &quot;Owncloud_CustomSiteRootFolder&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This class exists only as a container for the default extended model level methods generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CustomSiteRootFolderImpl}.
 * </p>
 *
 * @author Matthias
 * @see CustomSiteRootFolderImpl
 * @see de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder
 * @generated
 */
public abstract class CustomSiteRootFolderBaseImpl
	extends CustomSiteRootFolderModelImpl implements CustomSiteRootFolder {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a custom site root folder model instance should use the {@link CustomSiteRootFolder} interface instead.
	 */
	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			CustomSiteRootFolderLocalServiceUtil.addCustomSiteRootFolder(this);
		}
		else {
			CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder(this);
		}
	}
}