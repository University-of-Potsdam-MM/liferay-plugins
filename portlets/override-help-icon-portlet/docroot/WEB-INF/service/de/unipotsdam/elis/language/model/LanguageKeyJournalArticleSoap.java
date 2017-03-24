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

import de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthias
 * @generated
 */
public class LanguageKeyJournalArticleSoap implements Serializable {
	public static LanguageKeyJournalArticleSoap toSoapModel(
		LanguageKeyJournalArticle model) {
		LanguageKeyJournalArticleSoap soapModel = new LanguageKeyJournalArticleSoap();

		soapModel.setKey(model.getKey());
		soapModel.setArticleId(model.getArticleId());

		return soapModel;
	}

	public static LanguageKeyJournalArticleSoap[] toSoapModels(
		LanguageKeyJournalArticle[] models) {
		LanguageKeyJournalArticleSoap[] soapModels = new LanguageKeyJournalArticleSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LanguageKeyJournalArticleSoap[][] toSoapModels(
		LanguageKeyJournalArticle[][] models) {
		LanguageKeyJournalArticleSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LanguageKeyJournalArticleSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LanguageKeyJournalArticleSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LanguageKeyJournalArticleSoap[] toSoapModels(
		List<LanguageKeyJournalArticle> models) {
		List<LanguageKeyJournalArticleSoap> soapModels = new ArrayList<LanguageKeyJournalArticleSoap>(models.size());

		for (LanguageKeyJournalArticle model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LanguageKeyJournalArticleSoap[soapModels.size()]);
	}

	public LanguageKeyJournalArticleSoap() {
	}

	public LanguageKeyJournalArticlePK getPrimaryKey() {
		return new LanguageKeyJournalArticlePK(_key, _articleId);
	}

	public void setPrimaryKey(LanguageKeyJournalArticlePK pk) {
		setKey(pk.key);
		setArticleId(pk.articleId);
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getArticleId() {
		return _articleId;
	}

	public void setArticleId(String articleId) {
		_articleId = articleId;
	}

	private String _key;
	private String _articleId;
}