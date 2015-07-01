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

package de.unipotsdam.elis.portfolio.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for PortfolioPermission. This utility wraps
 * {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioPermissionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see PortfolioPermissionLocalService
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioPermissionLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.impl.PortfolioPermissionLocalServiceImpl
 * @generated
 */
public class PortfolioPermissionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioPermissionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the portfolio permission to the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission addPortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolioPermission(portfolioPermission);
	}

	/**
	* Creates a new portfolio permission with the primary key. Does not add the portfolio permission to the database.
	*
	* @param portfolioPermissionPK the primary key for the new portfolio permission
	* @return the new portfolio permission
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission createPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK) {
		return getService().createPortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Deletes the portfolio permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission that was removed
	* @throws PortalException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Deletes the portfolio permission from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolioPermission(portfolioPermission);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Returns the portfolio permission with the primary key.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission
	* @throws PortalException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission getPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPermission(portfolioPermissionPK);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the portfolio permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @return the range of portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPermissions(start, end);
	}

	/**
	* Returns the number of portfolio permissions.
	*
	* @return the number of portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int getPortfolioPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPermissionsCount();
	}

	/**
	* Updates the portfolio permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission updatePortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortfolioPermission(portfolioPermission);
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

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission addPortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolioPermission(plid, userId);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getService().deletePortfolioPermission(plid, userId);
	}

	public static void deletePortfolioPermissionByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortfolioPermissionByPlid(plid);
	}

	public static void deletePortfolioPermissionByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortfolioPermissionByUserId(userId);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission getPortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getService().getPortfolioPermission(plid, userId);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchPortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPortfolioPermission(plid, userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissionByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPermissionByPlid(plid);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissionByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPermissionByUserId(userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static PortfolioPermissionLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PortfolioPermissionLocalService.class.getName());

			if (invokableLocalService instanceof PortfolioPermissionLocalService) {
				_service = (PortfolioPermissionLocalService)invokableLocalService;
			}
			else {
				_service = new PortfolioPermissionLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PortfolioPermissionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PortfolioPermissionLocalService service) {
	}

	private static PortfolioPermissionLocalService _service;
}