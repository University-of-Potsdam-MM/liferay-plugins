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

import java.util.Date;
import java.util.List;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.language.model.LanguageKey;
import de.unipotsdam.elis.language.service.base.LanguageKeyLocalServiceBaseImpl;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK;

/**
 * The implementation of the language key local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.language.service.LanguageKeyLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.language.service.base.LanguageKeyLocalServiceBaseImpl
 * @see de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil
 */
public class LanguageKeyLocalServiceImpl extends LanguageKeyLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil} to access the language key local service.
	 */
	
	public LanguageKey addLanguageKey(String key, String value) throws SystemException{

		LanguageKey languageKey = languageKeyPersistence.create(key);
		
		languageKey.setValue(value);

		super.addLanguageKey(languageKey);

		return languageKey;
	}
	
	public List<LanguageKey> search(String key, int start, int end) throws SystemException{
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(LanguageKey.class).add(RestrictionsFactoryUtil.like("key", "%" + key + "%"));
		return languageKeyLocalService.dynamicQuery(query, start, end);
	}
}