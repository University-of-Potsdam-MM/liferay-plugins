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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import de.unipotsdam.elis.owncloud.service.ClpSerializer;
import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;
import de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class CustomSiteRootFolderClp extends BaseModelImpl<CustomSiteRootFolder>
	implements CustomSiteRootFolder {
	public CustomSiteRootFolderClp() {
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
	public CustomSiteRootFolderPK getPrimaryKey() {
		return new CustomSiteRootFolderPK(_userId, _originalPath);
	}

	@Override
	public void setPrimaryKey(CustomSiteRootFolderPK primaryKey) {
		setUserId(primaryKey.userId);
		setOriginalPath(primaryKey.originalPath);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new CustomSiteRootFolderPK(_userId, _originalPath);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((CustomSiteRootFolderPK)primaryKeyObj);
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

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_customSiteRootFolderRemoteModel != null) {
			try {
				Class<?> clazz = _customSiteRootFolderRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_customSiteRootFolderRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public String getOriginalPath() {
		return _originalPath;
	}

	@Override
	public void setOriginalPath(String originalPath) {
		_originalPath = originalPath;

		if (_customSiteRootFolderRemoteModel != null) {
			try {
				Class<?> clazz = _customSiteRootFolderRemoteModel.getClass();

				Method method = clazz.getMethod("setOriginalPath", String.class);

				method.invoke(_customSiteRootFolderRemoteModel, originalPath);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getCustomPath() {
		return _customPath;
	}

	@Override
	public void setCustomPath(String customPath) {
		_customPath = customPath;

		if (_customSiteRootFolderRemoteModel != null) {
			try {
				Class<?> clazz = _customSiteRootFolderRemoteModel.getClass();

				Method method = clazz.getMethod("setCustomPath", String.class);

				method.invoke(_customSiteRootFolderRemoteModel, customPath);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getCustomSiteRootFolderRemoteModel() {
		return _customSiteRootFolderRemoteModel;
	}

	public void setCustomSiteRootFolderRemoteModel(
		BaseModel<?> customSiteRootFolderRemoteModel) {
		_customSiteRootFolderRemoteModel = customSiteRootFolderRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _customSiteRootFolderRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_customSiteRootFolderRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			CustomSiteRootFolderLocalServiceUtil.addCustomSiteRootFolder(this);
		}
		else {
			CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder(this);
		}
	}

	@Override
	public CustomSiteRootFolder toEscapedModel() {
		return (CustomSiteRootFolder)ProxyUtil.newProxyInstance(CustomSiteRootFolder.class.getClassLoader(),
			new Class[] { CustomSiteRootFolder.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		CustomSiteRootFolderClp clone = new CustomSiteRootFolderClp();

		clone.setUserId(getUserId());
		clone.setOriginalPath(getOriginalPath());
		clone.setCustomPath(getCustomPath());

		return clone;
	}

	@Override
	public int compareTo(CustomSiteRootFolder customSiteRootFolder) {
		CustomSiteRootFolderPK primaryKey = customSiteRootFolder.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CustomSiteRootFolderClp)) {
			return false;
		}

		CustomSiteRootFolderClp customSiteRootFolder = (CustomSiteRootFolderClp)obj;

		CustomSiteRootFolderPK primaryKey = customSiteRootFolder.getPrimaryKey();

		if (getPrimaryKey().equals(primaryKey)) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{userId=");
		sb.append(getUserId());
		sb.append(", originalPath=");
		sb.append(getOriginalPath());
		sb.append(", customPath=");
		sb.append(getCustomPath());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(13);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>originalPath</column-name><column-value><![CDATA[");
		sb.append(getOriginalPath());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>customPath</column-name><column-value><![CDATA[");
		sb.append(getCustomPath());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _userId;
	private String _userUuid;
	private String _originalPath;
	private String _customPath;
	private BaseModel<?> _customSiteRootFolderRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.owncloud.service.ClpSerializer.class;
}