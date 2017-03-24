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

package de.unipotsdam.elis.language.service.impl;

import java.util.List;

import com.liferay.portal.kernel.exception.SystemException;

import de.unipotsdam.elis.language.model.LanguageKeyJournalArticle;
import de.unipotsdam.elis.language.service.base.LanguageKeyJournalArticleLocalServiceBaseImpl;
import de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK;

/**
 * The implementation of the language key journal article local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.language.service.base.LanguageKeyJournalArticleLocalServiceBaseImpl
 * @see de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil
 */
public class LanguageKeyJournalArticleLocalServiceImpl
	extends LanguageKeyJournalArticleLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil} to access the language key journal article local service.
	 */
	
	public void removeLanguageKeyJournalArticleByKey(String key) throws SystemException{
		languageKeyJournalArticlePersistence.removeByKey(key);
	}
	
	public void addLanguageKeyJournalArticle(String key, String articleId) throws SystemException{
		LanguageKeyJournalArticlePK primKey = new LanguageKeyJournalArticlePK(key, articleId);
		if (languageKeyJournalArticlePersistence.fetchByPrimaryKey(primKey) == null){
			LanguageKeyJournalArticle languageKeyJournalArticle = languageKeyJournalArticlePersistence.create(primKey);
			super.addLanguageKeyJournalArticle(languageKeyJournalArticle);
		}
	}
	
	public List<LanguageKeyJournalArticle> getLanguageKeyJournalArticlesByArticleId(String articleId) throws SystemException{
		return languageKeyJournalArticlePersistence.findByArticleId(articleId);
	}
}