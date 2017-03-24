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

package de.unipotsdam.elis.language.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.language.model.LanguageKeyJournalArticle;

import java.util.List;

/**
 * The persistence utility for the language key journal article service. This utility wraps {@link LanguageKeyJournalArticlePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see LanguageKeyJournalArticlePersistence
 * @see LanguageKeyJournalArticlePersistenceImpl
 * @generated
 */
public class LanguageKeyJournalArticleUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(
		LanguageKeyJournalArticle languageKeyJournalArticle) {
		getPersistence().clearCache(languageKeyJournalArticle);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<LanguageKeyJournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LanguageKeyJournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LanguageKeyJournalArticle> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static LanguageKeyJournalArticle update(
		LanguageKeyJournalArticle languageKeyJournalArticle)
		throws SystemException {
		return getPersistence().update(languageKeyJournalArticle);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static LanguageKeyJournalArticle update(
		LanguageKeyJournalArticle languageKeyJournalArticle,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(languageKeyJournalArticle, serviceContext);
	}

	/**
	* Returns all the language key journal articles where key = &#63;.
	*
	* @param key the key
	* @return the matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByKey(key);
	}

	/**
	* Returns a range of all the language key journal articles where key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param key the key
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @return the range of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByKey(
		java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByKey(key, start, end);
	}

	/**
	* Returns an ordered range of all the language key journal articles where key = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param key the key
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByKey(
		java.lang.String key, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByKey(key, start, end, orderByComparator);
	}

	/**
	* Returns the first language key journal article in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle findByKey_First(
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence().findByKey_First(key, orderByComparator);
	}

	/**
	* Returns the first language key journal article in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle fetchByKey_First(
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByKey_First(key, orderByComparator);
	}

	/**
	* Returns the last language key journal article in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle findByKey_Last(
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence().findByKey_Last(key, orderByComparator);
	}

	/**
	* Returns the last language key journal article in the ordered set where key = &#63;.
	*
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle fetchByKey_Last(
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByKey_Last(key, orderByComparator);
	}

	/**
	* Returns the language key journal articles before and after the current language key journal article in the ordered set where key = &#63;.
	*
	* @param languageKeyJournalArticlePK the primary key of the current language key journal article
	* @param key the key
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle[] findByKey_PrevAndNext(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK,
		java.lang.String key,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence()
				   .findByKey_PrevAndNext(languageKeyJournalArticlePK, key,
			orderByComparator);
	}

	/**
	* Removes all the language key journal articles where key = &#63; from the database.
	*
	* @param key the key
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByKey(java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByKey(key);
	}

	/**
	* Returns the number of language key journal articles where key = &#63;.
	*
	* @param key the key
	* @return the number of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByKey(java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByKey(key);
	}

	/**
	* Returns all the language key journal articles where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByArticleId(
		java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByArticleId(articleId);
	}

	/**
	* Returns a range of all the language key journal articles where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @return the range of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByArticleId(
		java.lang.String articleId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByArticleId(articleId, start, end);
	}

	/**
	* Returns an ordered range of all the language key journal articles where articleId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param articleId the article ID
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findByArticleId(
		java.lang.String articleId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByArticleId(articleId, start, end, orderByComparator);
	}

	/**
	* Returns the first language key journal article in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle findByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence()
				   .findByArticleId_First(articleId, orderByComparator);
	}

	/**
	* Returns the first language key journal article in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle fetchByArticleId_First(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByArticleId_First(articleId, orderByComparator);
	}

	/**
	* Returns the last language key journal article in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle findByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence()
				   .findByArticleId_Last(articleId, orderByComparator);
	}

	/**
	* Returns the last language key journal article in the ordered set where articleId = &#63;.
	*
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle fetchByArticleId_Last(
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByArticleId_Last(articleId, orderByComparator);
	}

	/**
	* Returns the language key journal articles before and after the current language key journal article in the ordered set where articleId = &#63;.
	*
	* @param languageKeyJournalArticlePK the primary key of the current language key journal article
	* @param articleId the article ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle[] findByArticleId_PrevAndNext(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK,
		java.lang.String articleId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence()
				   .findByArticleId_PrevAndNext(languageKeyJournalArticlePK,
			articleId, orderByComparator);
	}

	/**
	* Removes all the language key journal articles where articleId = &#63; from the database.
	*
	* @param articleId the article ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByArticleId(articleId);
	}

	/**
	* Returns the number of language key journal articles where articleId = &#63;.
	*
	* @param articleId the article ID
	* @return the number of matching language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countByArticleId(java.lang.String articleId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByArticleId(articleId);
	}

	/**
	* Caches the language key journal article in the entity cache if it is enabled.
	*
	* @param languageKeyJournalArticle the language key journal article
	*/
	public static void cacheResult(
		de.unipotsdam.elis.language.model.LanguageKeyJournalArticle languageKeyJournalArticle) {
		getPersistence().cacheResult(languageKeyJournalArticle);
	}

	/**
	* Caches the language key journal articles in the entity cache if it is enabled.
	*
	* @param languageKeyJournalArticles the language key journal articles
	*/
	public static void cacheResult(
		java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> languageKeyJournalArticles) {
		getPersistence().cacheResult(languageKeyJournalArticles);
	}

	/**
	* Creates a new language key journal article with the primary key. Does not add the language key journal article to the database.
	*
	* @param languageKeyJournalArticlePK the primary key for the new language key journal article
	* @return the new language key journal article
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle create(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK) {
		return getPersistence().create(languageKeyJournalArticlePK);
	}

	/**
	* Removes the language key journal article with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param languageKeyJournalArticlePK the primary key of the language key journal article
	* @return the language key journal article that was removed
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle remove(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence().remove(languageKeyJournalArticlePK);
	}

	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle updateImpl(
		de.unipotsdam.elis.language.model.LanguageKeyJournalArticle languageKeyJournalArticle)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(languageKeyJournalArticle);
	}

	/**
	* Returns the language key journal article with the primary key or throws a {@link de.unipotsdam.elis.language.NoSuchKeyJournalArticleException} if it could not be found.
	*
	* @param languageKeyJournalArticlePK the primary key of the language key journal article
	* @return the language key journal article
	* @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle findByPrimaryKey(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyJournalArticleException {
		return getPersistence().findByPrimaryKey(languageKeyJournalArticlePK);
	}

	/**
	* Returns the language key journal article with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param languageKeyJournalArticlePK the primary key of the language key journal article
	* @return the language key journal article, or <code>null</code> if a language key journal article with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKeyJournalArticle fetchByPrimaryKey(
		de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(languageKeyJournalArticlePK);
	}

	/**
	* Returns all the language key journal articles.
	*
	* @return the language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the language key journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @return the range of language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the language key journal articles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of language key journal articles
	* @param end the upper bound of the range of language key journal articles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKeyJournalArticle> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the language key journal articles from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of language key journal articles.
	*
	* @return the number of language key journal articles
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LanguageKeyJournalArticlePersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LanguageKeyJournalArticlePersistence)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.language.service.ClpSerializer.getServletContextName(),
					LanguageKeyJournalArticlePersistence.class.getName());

			ReferenceRegistry.registerReference(LanguageKeyJournalArticleUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(LanguageKeyJournalArticlePersistence persistence) {
	}

	private static LanguageKeyJournalArticlePersistence _persistence;
}