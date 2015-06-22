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

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link PortfolioPermissionLocalService}.
 *
 * @author Matthias
 * @see PortfolioPermissionLocalService
 * @generated
 */
public class PortfolioPermissionLocalServiceWrapper
	implements PortfolioPermissionLocalService,
		ServiceWrapper<PortfolioPermissionLocalService> {
	public PortfolioPermissionLocalServiceWrapper(
		PortfolioPermissionLocalService portfolioPermissionLocalService) {
		_portfolioPermissionLocalService = portfolioPermissionLocalService;
	}

	/**
	* Adds the portfolio permission to the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission addPortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.addPortfolioPermission(portfolioPermission);
	}

	/**
	* Creates a new portfolio permission with the primary key. Does not add the portfolio permission to the database.
	*
	* @param portfolioPermissionPK the primary key for the new portfolio permission
	* @return the new portfolio permission
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission createPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK) {
		return _portfolioPermissionLocalService.createPortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Deletes the portfolio permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission that was removed
	* @throws PortalException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.deletePortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Deletes the portfolio permission from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.deletePortfolioPermission(portfolioPermission);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _portfolioPermissionLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.fetchPortfolioPermission(portfolioPermissionPK);
	}

	/**
	* Returns the portfolio permission with the primary key.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission
	* @throws PortalException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission getPortfolioPermission(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPortfolioPermission(portfolioPermissionPK);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPersistedModel(primaryKeyObj);
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
	@Override
	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPortfolioPermissions(start,
			end);
	}

	/**
	* Returns the number of portfolio permissions.
	*
	* @return the number of portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getPortfolioPermissionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPortfolioPermissionsCount();
	}

	/**
	* Updates the portfolio permission in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermission the portfolio permission
	* @return the portfolio permission that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission updatePortfolioPermission(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.updatePortfolioPermission(portfolioPermission);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _portfolioPermissionLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_portfolioPermissionLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _portfolioPermissionLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission addPortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.addPortfolioPermission(plid,
			userId);
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission deletePortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return _portfolioPermissionLocalService.deletePortfolioPermission(plid,
			userId);
	}

	@Override
	public void deletePortfolioPermissionByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portfolioPermissionLocalService.deletePortfolioPermissionByPlid(plid);
	}

	@Override
	public void deletePortfolioPermissionByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_portfolioPermissionLocalService.deletePortfolioPermissionByUserId(userId);
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission getPortfolioPermission(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return _portfolioPermissionLocalService.getPortfolioPermission(plid,
			userId);
	}

	@Override
	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissionByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPortfolioPermissionByPlid(plid);
	}

	@Override
	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> getPortfolioPermissionByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermissionLocalService.getPortfolioPermissionByUserId(userId);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public PortfolioPermissionLocalService getWrappedPortfolioPermissionLocalService() {
		return _portfolioPermissionLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedPortfolioPermissionLocalService(
		PortfolioPermissionLocalService portfolioPermissionLocalService) {
		_portfolioPermissionLocalService = portfolioPermissionLocalService;
	}

	@Override
	public PortfolioPermissionLocalService getWrappedService() {
		return _portfolioPermissionLocalService;
	}

	@Override
	public void setWrappedService(
		PortfolioPermissionLocalService portfolioPermissionLocalService) {
		_portfolioPermissionLocalService = portfolioPermissionLocalService;
	}

	private PortfolioPermissionLocalService _portfolioPermissionLocalService;
}