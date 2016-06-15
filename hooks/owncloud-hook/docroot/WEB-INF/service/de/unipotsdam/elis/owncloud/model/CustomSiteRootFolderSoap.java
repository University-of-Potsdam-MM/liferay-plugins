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

package de.unipotsdam.elis.owncloud.model;

import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthias
 * @generated
 */
public class CustomSiteRootFolderSoap implements Serializable {
	public static CustomSiteRootFolderSoap toSoapModel(
		CustomSiteRootFolder model) {
		CustomSiteRootFolderSoap soapModel = new CustomSiteRootFolderSoap();

		soapModel.setUserId(model.getUserId());
		soapModel.setOriginalPath(model.getOriginalPath());
		soapModel.setCustomPath(model.getCustomPath());

		return soapModel;
	}

	public static CustomSiteRootFolderSoap[] toSoapModels(
		CustomSiteRootFolder[] models) {
		CustomSiteRootFolderSoap[] soapModels = new CustomSiteRootFolderSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CustomSiteRootFolderSoap[][] toSoapModels(
		CustomSiteRootFolder[][] models) {
		CustomSiteRootFolderSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CustomSiteRootFolderSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CustomSiteRootFolderSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CustomSiteRootFolderSoap[] toSoapModels(
		List<CustomSiteRootFolder> models) {
		List<CustomSiteRootFolderSoap> soapModels = new ArrayList<CustomSiteRootFolderSoap>(models.size());

		for (CustomSiteRootFolder model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CustomSiteRootFolderSoap[soapModels.size()]);
	}

	public CustomSiteRootFolderSoap() {
	}

	public CustomSiteRootFolderPK getPrimaryKey() {
		return new CustomSiteRootFolderPK(_userId, _originalPath);
	}

	public void setPrimaryKey(CustomSiteRootFolderPK pk) {
		setUserId(pk.userId);
		setOriginalPath(pk.originalPath);
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getOriginalPath() {
		return _originalPath;
	}

	public void setOriginalPath(String originalPath) {
		_originalPath = originalPath;
	}

	public String getCustomPath() {
		return _customPath;
	}

	public void setCustomPath(String customPath) {
		_customPath = customPath;
	}

	private long _userId;
	private String _originalPath;
	private String _customPath;
}