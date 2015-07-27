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
 * Provides the local service utility for PortfolioFeedback. This utility wraps
 * {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioFeedbackLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see PortfolioFeedbackLocalService
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioFeedbackLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.impl.PortfolioFeedbackLocalServiceImpl
 * @generated
 */
public class PortfolioFeedbackLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.portfolio.service.impl.PortfolioFeedbackLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the portfolio feedback to the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioFeedback the portfolio feedback
	* @return the portfolio feedback that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback addPortfolioFeedback(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolioFeedback(portfolioFeedback);
	}

	/**
	* Creates a new portfolio feedback with the primary key. Does not add the portfolio feedback to the database.
	*
	* @param portfolioFeedbackPK the primary key for the new portfolio feedback
	* @return the new portfolio feedback
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback createPortfolioFeedback(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK) {
		return getService().createPortfolioFeedback(portfolioFeedbackPK);
	}

	/**
	* Deletes the portfolio feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioFeedbackPK the primary key of the portfolio feedback
	* @return the portfolio feedback that was removed
	* @throws PortalException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback deletePortfolioFeedback(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolioFeedback(portfolioFeedbackPK);
	}

	/**
	* Deletes the portfolio feedback from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioFeedback the portfolio feedback
	* @return the portfolio feedback that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback deletePortfolioFeedback(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deletePortfolioFeedback(portfolioFeedback);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchPortfolioFeedback(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPortfolioFeedback(portfolioFeedbackPK);
	}

	/**
	* Returns the portfolio feedback with the primary key.
	*
	* @param portfolioFeedbackPK the primary key of the portfolio feedback
	* @return the portfolio feedback
	* @throws PortalException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback getPortfolioFeedback(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioFeedback(portfolioFeedbackPK);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the portfolio feedbacks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @return the range of portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbacks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioFeedbacks(start, end);
	}

	/**
	* Returns the number of portfolio feedbacks.
	*
	* @return the number of portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int getPortfolioFeedbacksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioFeedbacksCount();
	}

	/**
	* Updates the portfolio feedback in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param portfolioFeedback the portfolio feedback
	* @return the portfolio feedback that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback updatePortfolioFeedback(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePortfolioFeedback(portfolioFeedback);
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

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback addPortfolioFeedback(
		long plid, long userId, int feedbackStatus,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addPortfolioFeedback(plid, userId, feedbackStatus,
			serviceContext);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback addPortfolioFeedback(
		long plid, long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPortfolioFeedback(plid, userId, serviceContext);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback updatePortfolioFeedback(
		long plid, long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getService().updatePortfolioFeedback(plid, userId, feedbackStatus);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback deletePortfolioFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getService().deletePortfolioFeedback(plid, userId);
	}

	public static void deletePortfolioFeedbackByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortfolioFeedbackByPlid(plid);
	}

	public static void deletePortfolioFeedbackByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePortfolioFeedbackByUserId(userId);
	}

	public static void deletePortfolioFeedbackByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deletePortfolioFeedbackByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback getPortfolioFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getService().getPortfolioFeedback(plid, userId);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchPortfolioFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchPortfolioFeedback(plid, userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbackByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioFeedbackByPlid(plid);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbackByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioFeedbackByUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbackByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getPortfolioFeedbackByPlidAndFeedbackStatus(plid,
			feedbackStatus);
	}

	public static java.util.List<java.lang.Object> getPortfolioPlidsByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPortfolioPlidsByUserId(userId);
	}

	public static void clearService() {
		_service = null;
	}

	public static PortfolioFeedbackLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					PortfolioFeedbackLocalService.class.getName());

			if (invokableLocalService instanceof PortfolioFeedbackLocalService) {
				_service = (PortfolioFeedbackLocalService)invokableLocalService;
			}
			else {
				_service = new PortfolioFeedbackLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(PortfolioFeedbackLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(PortfolioFeedbackLocalService service) {
	}

	private static PortfolioFeedbackLocalService _service;
}