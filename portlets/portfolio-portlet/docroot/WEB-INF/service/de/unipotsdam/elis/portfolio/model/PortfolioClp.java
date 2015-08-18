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

import de.unipotsdam.elis.portfolio.service.ClpSerializer;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class PortfolioClp extends BaseModelImpl<Portfolio> implements Portfolio {
	public PortfolioClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Portfolio.class;
	}

	@Override
	public String getModelClassName() {
		return Portfolio.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _plid;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPlid(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _plid;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("publishmentType", getPublishmentType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Integer publishmentType = (Integer)attributes.get("publishmentType");

		if (publishmentType != null) {
			setPublishmentType(publishmentType);
		}
	}

	@Override
	public long getPlid() {
		return _plid;
	}

	@Override
	public void setPlid(long plid) {
		_plid = plid;

		if (_portfolioRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioRemoteModel.getClass();

				Method method = clazz.getMethod("setPlid", long.class);

				method.invoke(_portfolioRemoteModel, plid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getPublishmentType() {
		return _publishmentType;
	}

	@Override
	public void setPublishmentType(int publishmentType) {
		_publishmentType = publishmentType;

		if (_portfolioRemoteModel != null) {
			try {
				Class<?> clazz = _portfolioRemoteModel.getClass();

				Method method = clazz.getMethod("setPublishmentType", int.class);

				method.invoke(_portfolioRemoteModel, publishmentType);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public void setGlobal() {
		try {
			String methodName = "setGlobal";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void removeUser(long userId) {
		try {
			String methodName = "removeUser";

			Class<?>[] parameterTypes = new Class<?>[] { long.class };

			Object[] parameterValues = new Object[] { userId };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean feedbackRequested() {
		try {
			String methodName = "feedbackRequested";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean publishToUser(long userId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		try {
			String methodName = "publishToUser";

			Class<?>[] parameterTypes = new Class<?>[] {
					long.class, com.liferay.portal.service.ServiceContext.class
				};

			Object[] parameterValues = new Object[] { userId, serviceContext };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean userHasViewPermission(long userId) {
		try {
			String methodName = "userHasViewPermission";

			Class<?>[] parameterTypes = new Class<?>[] { long.class };

			Object[] parameterValues = new Object[] { userId };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public com.liferay.portal.model.Layout getLayout() {
		try {
			String methodName = "getLayout";

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
	public java.util.List<com.liferay.portal.model.User> getUsers() {
		try {
			String methodName = "getUsers";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.util.List<com.liferay.portal.model.User> returnObj = (java.util.List<com.liferay.portal.model.User>)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void rename(java.lang.String newTitle) {
		try {
			String methodName = "rename";

			Class<?>[] parameterTypes = new Class<?>[] { java.lang.String.class };

			Object[] parameterValues = new Object[] { newTitle };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean feedbackRequested(long userId) {
		try {
			String methodName = "feedbackRequested";

			Class<?>[] parameterTypes = new Class<?>[] { long.class };

			Object[] parameterValues = new Object[] { userId };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void updateFeedbackStatus(long userId, int feedbackStatus) {
		try {
			String methodName = "updateFeedbackStatus";

			Class<?>[] parameterTypes = new Class<?>[] { long.class, int.class };

			Object[] parameterValues = new Object[] { userId, feedbackStatus };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioFeedback getPortfolioFeedback(
		long userId) {
		try {
			String methodName = "getPortfolioFeedback";

			Class<?>[] parameterTypes = new Class<?>[] { long.class };

			Object[] parameterValues = new Object[] { userId };

			de.unipotsdam.elis.portfolio.model.PortfolioFeedback returnObj = (de.unipotsdam.elis.portfolio.model.PortfolioFeedback)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setPrivate() {
		try {
			String methodName = "setPrivate";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean publishToUserAndRequestFeedback(long userId,
		com.liferay.portal.service.ServiceContext serviceContext) {
		try {
			String methodName = "publishToUserAndRequestFeedback";

			Class<?>[] parameterTypes = new Class<?>[] {
					long.class, com.liferay.portal.service.ServiceContext.class
				};

			Object[] parameterValues = new Object[] { userId, serviceContext };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbacks() {
		try {
			String methodName = "getPortfolioFeedbacks";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> returnObj =
				(java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback>)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getPortfolioRemoteModel() {
		return _portfolioRemoteModel;
	}

	public void setPortfolioRemoteModel(BaseModel<?> portfolioRemoteModel) {
		_portfolioRemoteModel = portfolioRemoteModel;
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

		Class<?> remoteModelClass = _portfolioRemoteModel.getClass();

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

		Object returnValue = method.invoke(_portfolioRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			PortfolioLocalServiceUtil.addPortfolio(this);
		}
		else {
			PortfolioLocalServiceUtil.updatePortfolio(this);
		}
	}

	@Override
	public Portfolio toEscapedModel() {
		return (Portfolio)ProxyUtil.newProxyInstance(Portfolio.class.getClassLoader(),
			new Class[] { Portfolio.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		PortfolioClp clone = new PortfolioClp();

		clone.setPlid(getPlid());
		clone.setPublishmentType(getPublishmentType());

		return clone;
	}

	@Override
	public int compareTo(Portfolio portfolio) {
		long primaryKey = portfolio.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioClp)) {
			return false;
		}

		PortfolioClp portfolio = (PortfolioClp)obj;

		long primaryKey = portfolio.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
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
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{plid=");
		sb.append(getPlid());
		sb.append(", publishmentType=");
		sb.append(getPublishmentType());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.portfolio.model.Portfolio");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>plid</column-name><column-value><![CDATA[");
		sb.append(getPlid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>publishmentType</column-name><column-value><![CDATA[");
		sb.append(getPublishmentType());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _plid;
	private int _publishmentType;
	private BaseModel<?> _portfolioRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.portfolio.service.ClpSerializer.class;
}