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

package de.unipotsdam.elis.custompages.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CustomPageFeedbackLocalService}.
 *
 * @author Matthias
 * @see CustomPageFeedbackLocalService
 * @generated
 */
public class CustomPageFeedbackLocalServiceWrapper
	implements CustomPageFeedbackLocalService,
		ServiceWrapper<CustomPageFeedbackLocalService> {
	public CustomPageFeedbackLocalServiceWrapper(
		CustomPageFeedbackLocalService customPageFeedbackLocalService) {
		_customPageFeedbackLocalService = customPageFeedbackLocalService;
	}

	/**
	* Adds the custom page feedback to the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.addCustomPageFeedback(customPageFeedback);
	}

	/**
	* Creates a new custom page feedback with the primary key. Does not add the custom page feedback to the database.
	*
	* @param customPageFeedbackPK the primary key for the new custom page feedback
	* @return the new custom page feedback
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback createCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK) {
		return _customPageFeedbackLocalService.createCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Deletes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback that was removed
	* @throws PortalException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.deleteCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Deletes the custom page feedback from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.deleteCustomPageFeedback(customPageFeedback);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _customPageFeedbackLocalService.dynamicQuery();
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
		return _customPageFeedbackLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _customPageFeedbackLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _customPageFeedbackLocalService.dynamicQuery(dynamicQuery,
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
		return _customPageFeedbackLocalService.dynamicQueryCount(dynamicQuery);
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
		return _customPageFeedbackLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.fetchCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Returns the custom page feedback with the primary key.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback
	* @throws PortalException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback getCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedback(customPageFeedbackPK);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the custom page feedbacks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @return the range of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbacks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedbacks(start, end);
	}

	/**
	* Returns the number of custom page feedbacks.
	*
	* @return the number of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getCustomPageFeedbacksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedbacksCount();
	}

	/**
	* Updates the custom page feedback in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback updateCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.updateCustomPageFeedback(customPageFeedback);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _customPageFeedbackLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_customPageFeedbackLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _customPageFeedbackLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPagesByPageTypeAndLayoutUserId(pageType,
			userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getCustomPagesByLayoutUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPagesByLayoutUserId(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType,
			userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Layout> getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId) {
		return _customPageFeedbackLocalService.getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType,
			userId);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		long plid, long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.addCustomPageFeedback(plid,
			userId, feedbackStatus);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.addCustomPageFeedback(plid,
			userId);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback updateCustomPageFeedbackStatus(
		long plid, long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return _customPageFeedbackLocalService.updateCustomPageFeedbackStatus(plid,
			userId, feedbackStatus);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return _customPageFeedbackLocalService.deleteCustomPageFeedback(plid,
			userId);
	}

	@Override
	public void deleteCustomPageFeedbackByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		_customPageFeedbackLocalService.deleteCustomPageFeedbackByPlid(plid);
	}

	@Override
	public void deleteCustomPageFeedbackByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_customPageFeedbackLocalService.deleteCustomPageFeedbackByUserId(userId);
	}

	@Override
	public void deleteCustomPageFeedbackByPlidAndFeedbackStatus(long plid,
		int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		_customPageFeedbackLocalService.deleteCustomPageFeedbackByPlidAndFeedbackStatus(plid,
			feedbackStatus);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback getCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return _customPageFeedbackLocalService.getCustomPageFeedback(plid,
			userId);
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.fetchCustomPageFeedback(plid,
			userId);
	}

	@Override
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedbackByPlid(plid);
	}

	@Override
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedbackByUserId(userId);
	}

	@Override
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPageFeedbackByPlidAndFeedbackStatus(plid,
			feedbackStatus);
	}

	@Override
	public java.util.List<java.lang.Object> getCustomPagePlidsByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedbackLocalService.getCustomPagePlidsByUserId(userId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.User> getUsersByCustomPageFeedback(
		long plid) {
		return _customPageFeedbackLocalService.getUsersByCustomPageFeedback(plid);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public CustomPageFeedbackLocalService getWrappedCustomPageFeedbackLocalService() {
		return _customPageFeedbackLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedCustomPageFeedbackLocalService(
		CustomPageFeedbackLocalService customPageFeedbackLocalService) {
		_customPageFeedbackLocalService = customPageFeedbackLocalService;
	}

	@Override
	public CustomPageFeedbackLocalService getWrappedService() {
		return _customPageFeedbackLocalService;
	}

	@Override
	public void setWrappedService(
		CustomPageFeedbackLocalService customPageFeedbackLocalService) {
		_customPageFeedbackLocalService = customPageFeedbackLocalService;
	}

	private CustomPageFeedbackLocalService _customPageFeedbackLocalService;
}