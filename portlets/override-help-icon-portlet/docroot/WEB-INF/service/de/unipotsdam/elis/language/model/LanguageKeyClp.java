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

package de.unipotsdam.elis.language.model;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import de.unipotsdam.elis.language.service.ClpSerializer;
import de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class LanguageKeyClp extends BaseModelImpl<LanguageKey>
	implements LanguageKey {
	public LanguageKeyClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return LanguageKey.class;
	}

	@Override
	public String getModelClassName() {
		return LanguageKey.class.getName();
	}

	@Override
	public String getPrimaryKey() {
		return _key;
	}

	@Override
	public void setPrimaryKey(String primaryKey) {
		setKey(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _key;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((String)primaryKeyObj);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("key", getKey());
		attributes.put("value", getValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String value = (String)attributes.get("value");

		if (value != null) {
			setValue(value);
		}
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public void setKey(String key) {
		_key = key;

		if (_languageKeyRemoteModel != null) {
			try {
				Class<?> clazz = _languageKeyRemoteModel.getClass();

				Method method = clazz.getMethod("setKey", String.class);

				method.invoke(_languageKeyRemoteModel, key);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getValue() {
		return _value;
	}

	@Override
	public void setValue(String value) {
		_value = value;

		if (_languageKeyRemoteModel != null) {
			try {
				Class<?> clazz = _languageKeyRemoteModel.getClass();

				Method method = clazz.getMethod("setValue", String.class);

				method.invoke(_languageKeyRemoteModel, value);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getLanguageKeyRemoteModel() {
		return _languageKeyRemoteModel;
	}

	public void setLanguageKeyRemoteModel(BaseModel<?> languageKeyRemoteModel) {
		_languageKeyRemoteModel = languageKeyRemoteModel;
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

		Class<?> remoteModelClass = _languageKeyRemoteModel.getClass();

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

		Object returnValue = method.invoke(_languageKeyRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			LanguageKeyLocalServiceUtil.addLanguageKey(this);
		}
		else {
			LanguageKeyLocalServiceUtil.updateLanguageKey(this);
		}
	}

	@Override
	public LanguageKey toEscapedModel() {
		return (LanguageKey)ProxyUtil.newProxyInstance(LanguageKey.class.getClassLoader(),
			new Class[] { LanguageKey.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		LanguageKeyClp clone = new LanguageKeyClp();

		clone.setKey(getKey());
		clone.setValue(getValue());

		return clone;
	}

	@Override
	public int compareTo(LanguageKey languageKey) {
		String primaryKey = languageKey.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LanguageKeyClp)) {
			return false;
		}

		LanguageKeyClp languageKey = (LanguageKeyClp)obj;

		String primaryKey = languageKey.getPrimaryKey();

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

		sb.append("{key=");
		sb.append(getKey());
		sb.append(", value=");
		sb.append(getValue());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.language.model.LanguageKey");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append(getKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>value</column-name><column-value><![CDATA[");
		sb.append(getValue());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _key;
	private String _value;
	private BaseModel<?> _languageKeyRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.language.service.ClpSerializer.class;
}