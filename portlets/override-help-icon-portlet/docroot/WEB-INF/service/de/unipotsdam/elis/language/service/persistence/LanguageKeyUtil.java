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

import de.unipotsdam.elis.language.model.LanguageKey;

import java.util.List;

/**
 * The persistence utility for the language key service. This utility wraps {@link LanguageKeyPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see LanguageKeyPersistence
 * @see LanguageKeyPersistenceImpl
 * @generated
 */
public class LanguageKeyUtil {
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
	public static void clearCache(LanguageKey languageKey) {
		getPersistence().clearCache(languageKey);
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
	public static List<LanguageKey> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<LanguageKey> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<LanguageKey> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static LanguageKey update(LanguageKey languageKey)
		throws SystemException {
		return getPersistence().update(languageKey);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static LanguageKey update(LanguageKey languageKey,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(languageKey, serviceContext);
	}

	/**
	* Caches the language key in the entity cache if it is enabled.
	*
	* @param languageKey the language key
	*/
	public static void cacheResult(
		de.unipotsdam.elis.language.model.LanguageKey languageKey) {
		getPersistence().cacheResult(languageKey);
	}

	/**
	* Caches the language keies in the entity cache if it is enabled.
	*
	* @param languageKeies the language keies
	*/
	public static void cacheResult(
		java.util.List<de.unipotsdam.elis.language.model.LanguageKey> languageKeies) {
		getPersistence().cacheResult(languageKeies);
	}

	/**
	* Creates a new language key with the primary key. Does not add the language key to the database.
	*
	* @param key the primary key for the new language key
	* @return the new language key
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey create(
		java.lang.String key) {
		return getPersistence().create(key);
	}

	/**
	* Removes the language key with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param key the primary key of the language key
	* @return the language key that was removed
	* @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey remove(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyException {
		return getPersistence().remove(key);
	}

	public static de.unipotsdam.elis.language.model.LanguageKey updateImpl(
		de.unipotsdam.elis.language.model.LanguageKey languageKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(languageKey);
	}

	/**
	* Returns the language key with the primary key or throws a {@link de.unipotsdam.elis.language.NoSuchKeyException} if it could not be found.
	*
	* @param key the primary key of the language key
	* @return the language key
	* @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey findByPrimaryKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.language.NoSuchKeyException {
		return getPersistence().findByPrimaryKey(key);
	}

	/**
	* Returns the language key with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param key the primary key of the language key
	* @return the language key, or <code>null</code> if a language key with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey fetchByPrimaryKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(key);
	}

	/**
	* Returns all the language keies.
	*
	* @return the language keies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKey> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the language keies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of language keies
	* @param end the upper bound of the range of language keies (not inclusive)
	* @return the range of language keies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKey> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the language keies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of language keies
	* @param end the upper bound of the range of language keies (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of language keies
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKey> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the language keies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of language keies.
	*
	* @return the number of language keies
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static LanguageKeyPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (LanguageKeyPersistence)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.language.service.ClpSerializer.getServletContextName(),
					LanguageKeyPersistence.class.getName());

			ReferenceRegistry.registerReference(LanguageKeyUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(LanguageKeyPersistence persistence) {
	}

	private static LanguageKeyPersistence _persistence;
}