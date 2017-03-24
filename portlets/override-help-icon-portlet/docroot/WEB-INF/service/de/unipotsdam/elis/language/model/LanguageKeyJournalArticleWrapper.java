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
 * This class is a wrapper for {@link LanguageKeyJournalArticle}.
 * </p>
 *
 * @author Matthias
 * @see LanguageKeyJournalArticle
 * @generated
 */
public class LanguageKeyJournalArticleWrapper
	implements LanguageKeyJournalArticle,
		ModelWrapper<LanguageKeyJournalArticle> {
	public LanguageKeyJournalArticleWrapper(
		LanguageKeyJournalArticle languageKeyJournalArticle) {
		_languageKeyJournalArticle = languageKeyJournalArticle;
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

	/**
	* Returns the primary key of this language key journal article.
	*
	* @return the primary key of this language key journal article
	*/
	@Override
	public de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK getPrimaryKey() {
		return _languageKeyJournalArticle.getPrimaryKey();
	}

	/**
	* Sets the primary key of this language key journal article.
	*
	* @param primaryKey the primary key of this language key journal article
	*/
	@Override
	public void setPrimaryKey(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK primaryKey) {
		_languageKeyJournalArticle.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the key of this language key journal article.
	*
	* @return the key of this language key journal article
	*/
	@Override
	public java.lang.String getKey() {
		return _languageKeyJournalArticle.getKey();
	}

	/**
	* Sets the key of this language key journal article.
	*
	* @param key the key of this language key journal article
	*/
	@Override
	public void setKey(java.lang.String key) {
		_languageKeyJournalArticle.setKey(key);
	}

	/**
	* Returns the article ID of this language key journal article.
	*
	* @return the article ID of this language key journal article
	*/
	@Override
	public java.lang.String getArticleId() {
		return _languageKeyJournalArticle.getArticleId();
	}

	/**
	* Sets the article ID of this language key journal article.
	*
	* @param articleId the article ID of this language key journal article
	*/
	@Override
	public void setArticleId(java.lang.String articleId) {
		_languageKeyJournalArticle.setArticleId(articleId);
	}

	@Override
	public boolean isNew() {
		return _languageKeyJournalArticle.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_languageKeyJournalArticle.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _languageKeyJournalArticle.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_languageKeyJournalArticle.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _languageKeyJournalArticle.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _languageKeyJournalArticle.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_languageKeyJournalArticle.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _languageKeyJournalArticle.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_languageKeyJournalArticle.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_languageKeyJournalArticle.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_languageKeyJournalArticle.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new LanguageKeyJournalArticleWrapper((LanguageKeyJournalArticle)_languageKeyJournalArticle.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.language.model.LanguageKeyJournalArticle languageKeyJournalArticle) {
		return _languageKeyJournalArticle.compareTo(languageKeyJournalArticle);
	}

	@Override
	public int hashCode() {
		return _languageKeyJournalArticle.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> toCacheModel() {
		return _languageKeyJournalArticle.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.language.model.LanguageKeyJournalArticle toEscapedModel() {
		return new LanguageKeyJournalArticleWrapper(_languageKeyJournalArticle.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.language.model.LanguageKeyJournalArticle toUnescapedModel() {
		return new LanguageKeyJournalArticleWrapper(_languageKeyJournalArticle.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _languageKeyJournalArticle.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _languageKeyJournalArticle.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_languageKeyJournalArticle.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LanguageKeyJournalArticleWrapper)) {
			return false;
		}

		LanguageKeyJournalArticleWrapper languageKeyJournalArticleWrapper = (LanguageKeyJournalArticleWrapper)obj;

		if (Validator.equals(_languageKeyJournalArticle,
					languageKeyJournalArticleWrapper._languageKeyJournalArticle)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public LanguageKeyJournalArticle getWrappedLanguageKeyJournalArticle() {
		return _languageKeyJournalArticle;
	}

	@Override
	public LanguageKeyJournalArticle getWrappedModel() {
		return _languageKeyJournalArticle;
	}

	@Override
	public void resetOriginalValues() {
		_languageKeyJournalArticle.resetOriginalValues();
	}

	private LanguageKeyJournalArticle _languageKeyJournalArticle;
}