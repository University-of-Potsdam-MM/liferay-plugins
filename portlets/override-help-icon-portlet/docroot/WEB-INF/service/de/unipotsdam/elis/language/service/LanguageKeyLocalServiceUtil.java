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

package de.unipotsdam.elis.language.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for LanguageKey. This utility wraps
 * {@link de.unipotsdam.elis.language.service.impl.LanguageKeyLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see LanguageKeyLocalService
 * @see de.unipotsdam.elis.language.service.base.LanguageKeyLocalServiceBaseImpl
 * @see de.unipotsdam.elis.language.service.impl.LanguageKeyLocalServiceImpl
 * @generated
 */
public class LanguageKeyLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.language.service.impl.LanguageKeyLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the language key to the database. Also notifies the appropriate model listeners.
	*
	* @param languageKey the language key
	* @return the language key that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey addLanguageKey(
		de.unipotsdam.elis.language.model.LanguageKey languageKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLanguageKey(languageKey);
	}

	/**
	* Creates a new language key with the primary key. Does not add the language key to the database.
	*
	* @param key the primary key for the new language key
	* @return the new language key
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey createLanguageKey(
		java.lang.String key) {
		return getService().createLanguageKey(key);
	}

	/**
	* Deletes the language key with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param key the primary key of the language key
	* @return the language key that was removed
	* @throws PortalException if a language key with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey deleteLanguageKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLanguageKey(key);
	}

	/**
	* Deletes the language key from the database. Also notifies the appropriate model listeners.
	*
	* @param languageKey the language key
	* @return the language key that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey deleteLanguageKey(
		de.unipotsdam.elis.language.model.LanguageKey languageKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteLanguageKey(languageKey);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static de.unipotsdam.elis.language.model.LanguageKey fetchLanguageKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchLanguageKey(key);
	}

	/**
	* Returns the language key with the primary key.
	*
	* @param key the primary key of the language key
	* @return the language key
	* @throws PortalException if a language key with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey getLanguageKey(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLanguageKey(key);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKey> getLanguageKeies(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLanguageKeies(start, end);
	}

	/**
	* Returns the number of language keies.
	*
	* @return the number of language keies
	* @throws SystemException if a system exception occurred
	*/
	public static int getLanguageKeiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLanguageKeiesCount();
	}

	/**
	* Updates the language key in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param languageKey the language key
	* @return the language key that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.language.model.LanguageKey updateLanguageKey(
		de.unipotsdam.elis.language.model.LanguageKey languageKey)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLanguageKey(languageKey);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static de.unipotsdam.elis.language.model.LanguageKey addLanguageKey(
		java.lang.String key, java.lang.String value)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLanguageKey(key, value);
	}

	public static java.util.List<de.unipotsdam.elis.language.model.LanguageKey> search(
		java.lang.String key, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(key, start, end);
	}

	public static void clearService() {
		_service = null;
	}

	public static LanguageKeyLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					LanguageKeyLocalService.class.getName());

			if (invokableLocalService instanceof LanguageKeyLocalService) {
				_service = (LanguageKeyLocalService)invokableLocalService;
			}
			else {
				_service = new LanguageKeyLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(LanguageKeyLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(LanguageKeyLocalService service) {
	}

	private static LanguageKeyLocalService _service;
}