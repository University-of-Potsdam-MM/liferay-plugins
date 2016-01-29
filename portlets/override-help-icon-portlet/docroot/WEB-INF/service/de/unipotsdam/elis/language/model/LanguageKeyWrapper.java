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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link LanguageKey}.
 * </p>
 *
 * @author Matthias
 * @see LanguageKey
 * @generated
 */
public class LanguageKeyWrapper implements LanguageKey,
	ModelWrapper<LanguageKey> {
	public LanguageKeyWrapper(LanguageKey languageKey) {
		_languageKey = languageKey;
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

	/**
	* Returns the primary key of this language key.
	*
	* @return the primary key of this language key
	*/
	@Override
	public java.lang.String getPrimaryKey() {
		return _languageKey.getPrimaryKey();
	}

	/**
	* Sets the primary key of this language key.
	*
	* @param primaryKey the primary key of this language key
	*/
	@Override
	public void setPrimaryKey(java.lang.String primaryKey) {
		_languageKey.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the key of this language key.
	*
	* @return the key of this language key
	*/
	@Override
	public java.lang.String getKey() {
		return _languageKey.getKey();
	}

	/**
	* Sets the key of this language key.
	*
	* @param key the key of this language key
	*/
	@Override
	public void setKey(java.lang.String key) {
		_languageKey.setKey(key);
	}

	/**
	* Returns the value of this language key.
	*
	* @return the value of this language key
	*/
	@Override
	public java.lang.String getValue() {
		return _languageKey.getValue();
	}

	/**
	* Sets the value of this language key.
	*
	* @param value the value of this language key
	*/
	@Override
	public void setValue(java.lang.String value) {
		_languageKey.setValue(value);
	}

	@Override
	public boolean isNew() {
		return _languageKey.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_languageKey.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _languageKey.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_languageKey.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _languageKey.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _languageKey.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_languageKey.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _languageKey.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_languageKey.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_languageKey.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_languageKey.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LanguageKeyWrapper((LanguageKey)_languageKey.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.language.model.LanguageKey languageKey) {
		return _languageKey.compareTo(languageKey);
	}

	@Override
	public int hashCode() {
		return _languageKey.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.language.model.LanguageKey> toCacheModel() {
		return _languageKey.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.language.model.LanguageKey toEscapedModel() {
		return new LanguageKeyWrapper(_languageKey.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.language.model.LanguageKey toUnescapedModel() {
		return new LanguageKeyWrapper(_languageKey.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _languageKey.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _languageKey.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_languageKey.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LanguageKeyWrapper)) {
			return false;
		}

		LanguageKeyWrapper languageKeyWrapper = (LanguageKeyWrapper)obj;

		if (Validator.equals(_languageKey, languageKeyWrapper._languageKey)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public LanguageKey getWrappedLanguageKey() {
		return _languageKey;
	}

	@Override
	public LanguageKey getWrappedModel() {
		return _languageKey;
	}

	@Override
	public void resetOriginalValues() {
		_languageKey.resetOriginalValues();
	}

	private LanguageKey _languageKey;
}