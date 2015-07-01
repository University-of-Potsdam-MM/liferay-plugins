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

package de.unipotsdam.elis.portfolio.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import de.unipotsdam.elis.portfolio.service.ClpSerializer;
import de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class PortfolioPermissionClp extends BaseModelImpl<PortfolioPermission>
	implements PortfolioPermission {
	public PortfolioPermissionClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return PortfolioPermission.class;
	}

	@Override
	public String getModelClassName() {
		return PortfolioPermission.class.getName();
	}

	@Override
	public PortfolioPermissionPK getPrimaryKey() {
		return new PortfolioPermissionPK(_plid, _userId);
	}

	@Override
	public void setPrimaryKey(PortfolioPermissionPK primaryKey) {
		setPlid(primaryKey.plid);
		setUserId(primaryKey.userId);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new PortfolioPermissionPK(_plid, _userId);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((PortfolioPermissionPK)primaryKeyObj);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("userId", getUserId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}
	}

	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		_plid = plid;

		if (_portfolioPermissionRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioPermissionRemoteModel.getClass();

				Method method = clazz.getMethod("setPlid", long.class);

				method.invoke(_portfolioPermissionRemoteModel, plid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_portfolioPermissionRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioPermissionRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_portfolioPermissionRemoteModel, userId);
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

	public BaseModel<?> getPortfolioPermissionRemoteModel() {
		return _portfolioPermissionRemoteModel;
	}

	public void setPortfolioPermissionRemoteModel(
		BaseModel<?> portfolioPermissionRemoteModel) {
		_portfolioPermissionRemoteModel = portfolioPermissionRemoteModel;
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

		Class<?> remoteModelClass = _portfolioPermissionRemoteModel.getClass();

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

		Object returnValue = method.invoke(_portfolioPermissionRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			PortfolioPermissionLocalServiceUtil.addPortfolioPermission(this);
		}
		else {
			PortfolioPermissionLocalServiceUtil.updatePortfolioPermission(this);
		}
	}

	@Override
	public PortfolioPermission toEscapedModel() {
		return (PortfolioPermission)ProxyUtil.newProxyInstance(PortfolioPermission.class.getClassLoader(),
			new Class[] { PortfolioPermission.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PortfolioPermissionClp clone = new PortfolioPermissionClp();

		clone.setPlid(getPlid());
		clone.setUserId(getUserId());

		return clone;
	}

	@Override
	public int compareTo(PortfolioPermission portfolioPermission) {
		PortfolioPermissionPK primaryKey = portfolioPermission.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioPermissionClp)) {
			return false;
		}

		PortfolioPermissionClp portfolioPermission = (PortfolioPermissionClp)obj;

		PortfolioPermissionPK primaryKey = portfolioPermission.getPrimaryKey();

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
		StringBundler sb = new StringBundler(5);

		sb.append("{plid=");
		sb.append(getPlid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.portfolio.model.PortfolioPermission");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _plid;
	private long _userId;
	private String _userUuid;
	private BaseModel<?> _portfolioPermissionRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.portfolio.service.ClpSerializer.class;
}