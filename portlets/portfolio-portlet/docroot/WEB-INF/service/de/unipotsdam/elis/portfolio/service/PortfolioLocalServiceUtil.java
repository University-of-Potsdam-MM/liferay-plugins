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
 * Provides the local service utility for Portfolio. This utility wraps
 * {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see PortfolioLocalService
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.impl.PortfolioLocalServiceImpl
 * @generated
 */
public class PortfolioLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the portfolio to the database. Also notifies the appropriate model listeners.
	*
	* @param portfolio the portfolio
	* @return the portfolio that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio addPortfolio(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolio(portfolio);
	}

	/**
	* Creates a new portfolio with the primary key. Does not add the portfolio to the database.
	*
	* @param plid the primary key for the new portfolio
	* @return the new portfolio
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio createPortfolio(
		long plid) {
		return getService().createPortfolio(plid);
	}

	/**
	* Deletes the portfolio with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the portfolio
	* @return the portfolio that was removed
	* @throws PortalException if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio deletePortfolio(
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolio(plid);
	}

	/**
	* Deletes the portfolio from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolio the portfolio
	* @return the portfolio that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio deletePortfolio(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolio(portfolio);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static de.unipotsdam.elis.portfolio.model.Portfolio fetchPortfolio(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPortfolio(plid);
	}

	/**
	* Returns the portfolio with the primary key.
	*
	* @param plid the primary key of the portfolio
	* @return the portfolio
	* @throws PortalException if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio getPortfolio(
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolio(plid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the portfolios.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolios
	* @param end the upper bound of the range of portfolios (not inclusive)
	* @return the range of portfolios
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> getPortfolios(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolios(start, end);
	}

	/**
	* Returns the number of portfolios.
	*
	* @return the number of portfolios
	* @throws SystemException if a system exception occurred
	*/
	public static int getPortfoliosCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfoliosCount();
	}

	/**
	* Updates the portfolio in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portfolio the portfolio
	* @return the portfolio that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.Portfolio updatePortfolio(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortfolio(portfolio);
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

	public static de.unipotsdam.elis.portfolio.model.Portfolio addPortfolio(
		long plid, int publishmentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolio(plid, publishmentType);
	}

	public static de.unipotsdam.elis.portfolio.model.Portfolio addPortfolio(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolio(plid);
	}

	public static de.unipotsdam.elis.portfolio.model.Portfolio updatePortfolio(
		long plid, int publishmentType)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException {
		return getService().updatePortfolio(plid, publishmentType);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> getPortfoliosByLayoutUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfoliosByLayoutUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> getPortfoliosByPortfolioFeedbackUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfoliosByPortfolioFeedbackUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> getPortfolioByPublishmentTypeAndNoPortfolioFeedback(
		int publishmentType, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPortfolioByPublishmentTypeAndNoPortfolioFeedback(publishmentType,
			userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static PortfolioLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PortfolioLocalService.class.getName());

			if (invokableLocalService instanceof PortfolioLocalService) {
				_service = (PortfolioLocalService)invokableLocalService;
			}
			else {
				_service = new PortfolioLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PortfolioLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PortfolioLocalService service) {
	}

	private static PortfolioLocalService _service;
}