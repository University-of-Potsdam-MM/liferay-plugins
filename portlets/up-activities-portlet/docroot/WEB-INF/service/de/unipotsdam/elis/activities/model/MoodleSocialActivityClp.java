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

package de.unipotsdam.elis.activities.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import de.unipotsdam.elis.activities.service.ClpSerializer;
import de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class MoodleSocialActivityClp extends BaseModelImpl<MoodleSocialActivity>
	implements MoodleSocialActivity {
	public MoodleSocialActivityClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return MoodleSocialActivity.class;
	}

	@Override
	public String getModelClassName() {
		return MoodleSocialActivity.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _extSocialActivityId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setExtSocialActivityId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _extSocialActivityId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("extSocialActivityId", getExtSocialActivityId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("extServiceActivityType", getExtServiceActivityType());
		attributes.put("extServiceContext", getExtServiceContext());
		attributes.put("data", getData());
		attributes.put("published", getPublished());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long extSocialActivityId = (Long)attributes.get("extSocialActivityId");

		if (extSocialActivityId != null) {
			setExtSocialActivityId(extSocialActivityId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extServiceActivityType = (String)attributes.get(
				"extServiceActivityType");

		if (extServiceActivityType != null) {
			setExtServiceActivityType(extServiceActivityType);
		}

		String extServiceContext = (String)attributes.get("extServiceContext");

		if (extServiceContext != null) {
			setExtServiceContext(extServiceContext);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		Long published = (Long)attributes.get("published");

		if (published != null) {
			setPublished(published);
		}
	}

	@Override
	public long getExtSocialActivityId() {
		return _extSocialActivityId;
	}

	@Override
	public void setExtSocialActivityId(long extSocialActivityId) {
		_extSocialActivityId = extSocialActivityId;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setExtSocialActivityId",
						long.class);

				method.invoke(_moodleSocialActivityRemoteModel,
					extSocialActivityId);
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

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_moodleSocialActivityRemoteModel, userId);
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
	public int getType() {
		return _type;
	}

	@Override
	public void setType(int type) {
		_type = type;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setType", int.class);

				method.invoke(_moodleSocialActivityRemoteModel, type);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getExtServiceActivityType() {
		return _extServiceActivityType;
	}

	@Override
	public void setExtServiceActivityType(String extServiceActivityType) {
		_extServiceActivityType = extServiceActivityType;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setExtServiceActivityType",
						String.class);

				method.invoke(_moodleSocialActivityRemoteModel,
					extServiceActivityType);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getExtServiceContext() {
		return _extServiceContext;
	}

	@Override
	public void setExtServiceContext(String extServiceContext) {
		_extServiceContext = extServiceContext;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setExtServiceContext",
						String.class);

				method.invoke(_moodleSocialActivityRemoteModel,
					extServiceContext);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getData() {
		return _data;
	}

	@Override
	public void setData(String data) {
		_data = data;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setData", String.class);

				method.invoke(_moodleSocialActivityRemoteModel, data);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getPublished() {
		return _published;
	}

	@Override
	public void setPublished(long published) {
		_published = published;

		if (_moodleSocialActivityRemoteModel != null) {
			try {
				Class<?> clazz = _moodleSocialActivityRemoteModel.getClass();

				Method method = clazz.getMethod("setPublished", long.class);

				method.invoke(_moodleSocialActivityRemoteModel, published);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getMoodleSocialActivityRemoteModel() {
		return _moodleSocialActivityRemoteModel;
	}

	public void setMoodleSocialActivityRemoteModel(
		BaseModel<?> moodleSocialActivityRemoteModel) {
		_moodleSocialActivityRemoteModel = moodleSocialActivityRemoteModel;
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

		Class<?> remoteModelClass = _moodleSocialActivityRemoteModel.getClass();

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

		Object returnValue = method.invoke(_moodleSocialActivityRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			MoodleSocialActivityLocalServiceUtil.addMoodleSocialActivity(this);
		}
		else {
			MoodleSocialActivityLocalServiceUtil.updateMoodleSocialActivity(this);
		}
	}

	@Override
	public MoodleSocialActivity toEscapedModel() {
		return (MoodleSocialActivity)ProxyUtil.newProxyInstance(MoodleSocialActivity.class.getClassLoader(),
			new Class[] { MoodleSocialActivity.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		MoodleSocialActivityClp clone = new MoodleSocialActivityClp();

		clone.setExtSocialActivityId(getExtSocialActivityId());
		clone.setUserId(getUserId());
		clone.setType(getType());
		clone.setExtServiceActivityType(getExtServiceActivityType());
		clone.setExtServiceContext(getExtServiceContext());
		clone.setData(getData());
		clone.setPublished(getPublished());

		return clone;
	}

	@Override
	public int compareTo(MoodleSocialActivity moodleSocialActivity) {
		long primaryKey = moodleSocialActivity.getPrimaryKey();

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

		if (!(obj instanceof MoodleSocialActivityClp)) {
			return false;
		}

		MoodleSocialActivityClp moodleSocialActivity = (MoodleSocialActivityClp)obj;

		long primaryKey = moodleSocialActivity.getPrimaryKey();

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
		StringBundler sb = new StringBundler(15);

		sb.append("{extSocialActivityId=");
		sb.append(getExtSocialActivityId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", type=");
		sb.append(getType());
		sb.append(", extServiceActivityType=");
		sb.append(getExtServiceActivityType());
		sb.append(", extServiceContext=");
		sb.append(getExtServiceContext());
		sb.append(", data=");
		sb.append(getData());
		sb.append(", published=");
		sb.append(getPublished());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.activities.model.MoodleSocialActivity");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>extSocialActivityId</column-name><column-value><![CDATA[");
		sb.append(getExtSocialActivityId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>type</column-name><column-value><![CDATA[");
		sb.append(getType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extServiceActivityType</column-name><column-value><![CDATA[");
		sb.append(getExtServiceActivityType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>extServiceContext</column-name><column-value><![CDATA[");
		sb.append(getExtServiceContext());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>data</column-name><column-value><![CDATA[");
		sb.append(getData());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>published</column-name><column-value><![CDATA[");
		sb.append(getPublished());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _extSocialActivityId;
	private long _userId;
	private String _userUuid;
	private int _type;
	private String _extServiceActivityType;
	private String _extServiceContext;
	private String _data;
	private long _published;
	private BaseModel<?> _moodleSocialActivityRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.activities.service.ClpSerializer.class;
}