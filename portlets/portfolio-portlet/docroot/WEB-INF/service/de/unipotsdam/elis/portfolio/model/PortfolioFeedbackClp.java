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
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class PortfolioFeedbackClp extends BaseModelImpl<PortfolioFeedback>
	implements PortfolioFeedback {
	public PortfolioFeedbackClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return PortfolioFeedback.class;
	}

	@Override
	public String getModelClassName() {
		return PortfolioFeedback.class.getName();
	}

	@Override
	public PortfolioFeedbackPK getPrimaryKey() {
		return new PortfolioFeedbackPK(_plid, _userId);
	}

	@Override
	public void setPrimaryKey(PortfolioFeedbackPK primaryKey) {
		setPlid(primaryKey.plid);
		setUserId(primaryKey.userId);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new PortfolioFeedbackPK(_plid, _userId);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((PortfolioFeedbackPK)primaryKeyObj);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("userId", getUserId());
		attributes.put("feedbackStatus", getFeedbackStatus());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

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

		Integer feedbackStatus = (Integer)attributes.get("feedbackStatus");

		if (feedbackStatus != null) {
			setFeedbackStatus(feedbackStatus);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		_plid = plid;

		if (_portfolioFeedbackRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioFeedbackRemoteModel.getClass();

				Method method = clazz.getMethod("setPlid", long.class);

				method.invoke(_portfolioFeedbackRemoteModel, plid);
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

		if (_portfolioFeedbackRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioFeedbackRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_portfolioFeedbackRemoteModel, userId);
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
	public int getFeedbackStatus() {
		return _feedbackStatus;
	}

	@Override
	public void setFeedbackStatus(int feedbackStatus) {
		_feedbackStatus = feedbackStatus;

		if (_portfolioFeedbackRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioFeedbackRemoteModel.getClass();

				Method method = clazz.getMethod("setFeedbackStatus", int.class);

				method.invoke(_portfolioFeedbackRemoteModel, feedbackStatus);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_portfolioFeedbackRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioFeedbackRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_portfolioFeedbackRemoteModel, createDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;

		if (_portfolioFeedbackRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioFeedbackRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_portfolioFeedbackRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public com.liferay.portal.model.Layout getPortfolio() {
		try {
			String methodName = "getPortfolio";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			com.liferay.portal.model.Layout returnObj = (com.liferay.portal.model.Layout)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public com.liferay.portal.model.User getUser() {
		try {
			String methodName = "getUser";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			com.liferay.portal.model.User returnObj = (com.liferay.portal.model.User)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getPortfolioFeedbackRemoteModel() {
		return _portfolioFeedbackRemoteModel;
	}

	public void setPortfolioFeedbackRemoteModel(
		BaseModel<?> portfolioFeedbackRemoteModel) {
		_portfolioFeedbackRemoteModel = portfolioFeedbackRemoteModel;
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

		Class<?> remoteModelClass = _portfolioFeedbackRemoteModel.getClass();

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

		Object returnValue = method.invoke(_portfolioFeedbackRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			PortfolioFeedbackLocalServiceUtil.addPortfolioFeedback(this);
		}
		else {
			PortfolioFeedbackLocalServiceUtil.updatePortfolioFeedback(this);
		}
	}

	@Override
	public PortfolioFeedback toEscapedModel() {
		return (PortfolioFeedback)ProxyUtil.newProxyInstance(PortfolioFeedback.class.getClassLoader(),
			new Class[] { PortfolioFeedback.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PortfolioFeedbackClp clone = new PortfolioFeedbackClp();

		clone.setPlid(getPlid());
		clone.setUserId(getUserId());
		clone.setFeedbackStatus(getFeedbackStatus());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());

		return clone;
	}

	@Override
	public int compareTo(PortfolioFeedback portfolioFeedback) {
		PortfolioFeedbackPK primaryKey = portfolioFeedback.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioFeedbackClp)) {
			return false;
		}

		PortfolioFeedbackClp portfolioFeedback = (PortfolioFeedbackClp)obj;

		PortfolioFeedbackPK primaryKey = portfolioFeedback.getPrimaryKey();

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
		StringBundler sb = new StringBundler(11);

		sb.append("{plid=");
		sb.append(getPlid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", feedbackStatus=");
		sb.append(getFeedbackStatus());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.portfolio.model.PortfolioFeedback");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>feedbackStatus</column-name><column-value><![CDATA[");
		sb.append(getFeedbackStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _plid;
	private long _userId;
	private String _userUuid;
	private int _feedbackStatus;
	private Date _createDate;
	private Date _modifiedDate;
	private BaseModel<?> _portfolioFeedbackRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.portfolio.service.ClpSerializer.class;
}