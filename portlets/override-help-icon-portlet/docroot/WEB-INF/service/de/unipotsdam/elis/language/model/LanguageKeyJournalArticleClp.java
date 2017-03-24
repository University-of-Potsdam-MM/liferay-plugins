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
import de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil;
import de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Matthias
 */
public class LanguageKeyJournalArticleClp extends BaseModelImpl<LanguageKeyJournalArticle>
	implements LanguageKeyJournalArticle {
	public LanguageKeyJournalArticleClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return LanguageKeyJournalArticle.class;
	}

	@Override
	public String getModelClassName() {
		return LanguageKeyJournalArticle.class.getName();
	}

	@Override
	public LanguageKeyJournalArticlePK getPrimaryKey() {
		return new LanguageKeyJournalArticlePK(_key, _articleId);
	}

	@Override
	public void setPrimaryKey(LanguageKeyJournalArticlePK primaryKey) {
		setKey(primaryKey.key);
		setArticleId(primaryKey.articleId);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return new LanguageKeyJournalArticlePK(_key, _articleId);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey((LanguageKeyJournalArticlePK)primaryKeyObj);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("key", getKey());
		attributes.put("articleId", getArticleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String key = (String)attributes.get("key");

		if (key != null) {
			setKey(key);
		}

		String articleId = (String)attributes.get("articleId");

		if (articleId != null) {
			setArticleId(articleId);
		}
	}

	@Override
	public String getKey() {
		return _key;
	}

	@Override
	public void setKey(String key) {
		_key = key;

		if (_languageKeyJournalArticleRemoteModel != null) {
			try {
				Class<?> clazz = _languageKeyJournalArticleRemoteModel.getClass();

				Method method = clazz.getMethod("setKey", String.class);

				method.invoke(_languageKeyJournalArticleRemoteModel, key);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getArticleId() {
		return _articleId;
	}

	@Override
	public void setArticleId(String articleId) {
		_articleId = articleId;

		if (_languageKeyJournalArticleRemoteModel != null) {
			try {
				Class<?> clazz = _languageKeyJournalArticleRemoteModel.getClass();

				Method method = clazz.getMethod("setArticleId", String.class);

				method.invoke(_languageKeyJournalArticleRemoteModel, articleId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getLanguageKeyJournalArticleRemoteModel() {
		return _languageKeyJournalArticleRemoteModel;
	}

	public void setLanguageKeyJournalArticleRemoteModel(
		BaseModel<?> languageKeyJournalArticleRemoteModel) {
		_languageKeyJournalArticleRemoteModel = languageKeyJournalArticleRemoteModel;
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

		Class<?> remoteModelClass = _languageKeyJournalArticleRemoteModel.getClass();

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

		Object returnValue = method.invoke(_languageKeyJournalArticleRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			LanguageKeyJournalArticleLocalServiceUtil.addLanguageKeyJournalArticle(this);
		}
		else {
			LanguageKeyJournalArticleLocalServiceUtil.updateLanguageKeyJournalArticle(this);
		}
	}

	@Override
	public LanguageKeyJournalArticle toEscapedModel() {
		return (LanguageKeyJournalArticle)ProxyUtil.newProxyInstance(LanguageKeyJournalArticle.class.getClassLoader(),
			new Class[] { LanguageKeyJournalArticle.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		LanguageKeyJournalArticleClp clone = new LanguageKeyJournalArticleClp();

		clone.setKey(getKey());
		clone.setArticleId(getArticleId());

		return clone;
	}

	@Override
	public int compareTo(LanguageKeyJournalArticle languageKeyJournalArticle) {
		LanguageKeyJournalArticlePK primaryKey = languageKeyJournalArticle.getPrimaryKey();

		return getPrimaryKey().compareTo(primaryKey);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LanguageKeyJournalArticleClp)) {
			return false;
		}

		LanguageKeyJournalArticleClp languageKeyJournalArticle = (LanguageKeyJournalArticleClp)obj;

		LanguageKeyJournalArticlePK primaryKey = languageKeyJournalArticle.getPrimaryKey();

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
		sb.append(", articleId=");
		sb.append(getArticleId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append("de.unipotsdam.elis.language.model.LanguageKeyJournalArticle");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>key</column-name><column-value><![CDATA[");
		sb.append(getKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>articleId</column-name><column-value><![CDATA[");
		sb.append(getArticleId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private String _key;
	private String _articleId;
	private BaseModel<?> _languageKeyJournalArticleRemoteModel;
	private Class<?> _clpSerializerClass = de.unipotsdam.elis.language.service.ClpSerializer.class;
}