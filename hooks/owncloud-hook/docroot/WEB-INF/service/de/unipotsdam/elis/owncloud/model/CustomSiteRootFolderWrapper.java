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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CustomSiteRootFolder}.
 * </p>
 *
 * @author Matthias
 * @see CustomSiteRootFolder
 * @generated
 */
public class CustomSiteRootFolderWrapper implements CustomSiteRootFolder,
	ModelWrapper<CustomSiteRootFolder> {
	public CustomSiteRootFolderWrapper(
		CustomSiteRootFolder customSiteRootFolder) {
		_customSiteRootFolder = customSiteRootFolder;
	}

	@Override
	public Class<?> getModelClass() {
		return CustomSiteRootFolder.class;
	}

	@Override
	public String getModelClassName() {
		return CustomSiteRootFolder.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("userId", getUserId());
		attributes.put("originalPath", getOriginalPath());
		attributes.put("customPath", getCustomPath());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String originalPath = (String)attributes.get("originalPath");

		if (originalPath != null) {
			setOriginalPath(originalPath);
		}

		String customPath = (String)attributes.get("customPath");

		if (customPath != null) {
			setCustomPath(customPath);
		}
	}

	/**
	* Returns the primary key of this custom site root folder.
	*
	* @return the primary key of this custom site root folder
	*/
	@Override
	public de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK getPrimaryKey() {
		return _customSiteRootFolder.getPrimaryKey();
	}

	/**
	* Sets the primary key of this custom site root folder.
	*
	* @param primaryKey the primary key of this custom site root folder
	*/
	@Override
	public void setPrimaryKey(
		de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK primaryKey) {
		_customSiteRootFolder.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the user ID of this custom site root folder.
	*
	* @return the user ID of this custom site root folder
	*/
	@Override
	public long getUserId() {
		return _customSiteRootFolder.getUserId();
	}

	/**
	* Sets the user ID of this custom site root folder.
	*
	* @param userId the user ID of this custom site root folder
	*/
	@Override
	public void setUserId(long userId) {
		_customSiteRootFolder.setUserId(userId);
	}

	/**
	* Returns the user uuid of this custom site root folder.
	*
	* @return the user uuid of this custom site root folder
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customSiteRootFolder.getUserUuid();
	}

	/**
	* Sets the user uuid of this custom site root folder.
	*
	* @param userUuid the user uuid of this custom site root folder
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_customSiteRootFolder.setUserUuid(userUuid);
	}

	/**
	* Returns the original path of this custom site root folder.
	*
	* @return the original path of this custom site root folder
	*/
	@Override
	public java.lang.String getOriginalPath() {
		return _customSiteRootFolder.getOriginalPath();
	}

	/**
	* Sets the original path of this custom site root folder.
	*
	* @param originalPath the original path of this custom site root folder
	*/
	@Override
	public void setOriginalPath(java.lang.String originalPath) {
		_customSiteRootFolder.setOriginalPath(originalPath);
	}

	/**
	* Returns the custom path of this custom site root folder.
	*
	* @return the custom path of this custom site root folder
	*/
	@Override
	public java.lang.String getCustomPath() {
		return _customSiteRootFolder.getCustomPath();
	}

	/**
	* Sets the custom path of this custom site root folder.
	*
	* @param customPath the custom path of this custom site root folder
	*/
	@Override
	public void setCustomPath(java.lang.String customPath) {
		_customSiteRootFolder.setCustomPath(customPath);
	}

	@Override
	public boolean isNew() {
		return _customSiteRootFolder.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_customSiteRootFolder.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _customSiteRootFolder.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_customSiteRootFolder.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _customSiteRootFolder.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _customSiteRootFolder.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_customSiteRootFolder.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _customSiteRootFolder.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_customSiteRootFolder.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_customSiteRootFolder.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_customSiteRootFolder.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CustomSiteRootFolderWrapper((CustomSiteRootFolder)_customSiteRootFolder.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder customSiteRootFolder) {
		return _customSiteRootFolder.compareTo(customSiteRootFolder);
	}

	@Override
	public int hashCode() {
		return _customSiteRootFolder.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder> toCacheModel() {
		return _customSiteRootFolder.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder toEscapedModel() {
		return new CustomSiteRootFolderWrapper(_customSiteRootFolder.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder toUnescapedModel() {
		return new CustomSiteRootFolderWrapper(_customSiteRootFolder.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _customSiteRootFolder.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _customSiteRootFolder.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_customSiteRootFolder.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CustomSiteRootFolderWrapper)) {
			return false;
		}

		CustomSiteRootFolderWrapper customSiteRootFolderWrapper = (CustomSiteRootFolderWrapper)obj;

		if (Validator.equals(_customSiteRootFolder,
					customSiteRootFolderWrapper._customSiteRootFolder)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public CustomSiteRootFolder getWrappedCustomSiteRootFolder() {
		return _customSiteRootFolder;
	}

	@Override
	public CustomSiteRootFolder getWrappedModel() {
		return _customSiteRootFolder;
	}

	@Override
	public void resetOriginalValues() {
		_customSiteRootFolder.resetOriginalValues();
	}

	private CustomSiteRootFolder _customSiteRootFolder;
}