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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for CustomPageFeedback. This utility wraps
 * {@link de.unipotsdam.elis.custompages.service.impl.CustomPageFeedbackLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see CustomPageFeedbackLocalService
 * @see de.unipotsdam.elis.custompages.service.base.CustomPageFeedbackLocalServiceBaseImpl
 * @see de.unipotsdam.elis.custompages.service.impl.CustomPageFeedbackLocalServiceImpl
 * @generated
 */
public class CustomPageFeedbackLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.custompages.service.impl.CustomPageFeedbackLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the custom page feedback to the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was added
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCustomPageFeedback(customPageFeedback);
	}

	/**
	* Creates a new custom page feedback with the primary key. Does not add the custom page feedback to the database.
	*
	* @param customPageFeedbackPK the primary key for the new custom page feedback
	* @return the new custom page feedback
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback createCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK) {
		return getService().createCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Deletes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback that was removed
	* @throws PortalException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Deletes the custom page feedback from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteCustomPageFeedback(customPageFeedback);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCustomPageFeedback(customPageFeedbackPK);
	}

	/**
	* Returns the custom page feedback with the primary key.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback
	* @throws PortalException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback getCustomPageFeedback(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPageFeedback(customPageFeedbackPK);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbacks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPageFeedbacks(start, end);
	}

	/**
	* Returns the number of custom page feedbacks.
	*
	* @return the number of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int getCustomPageFeedbacksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPageFeedbacksCount();
	}

	/**
	* Updates the custom page feedback in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedback the custom page feedback
	* @return the custom page feedback that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback updateCustomPageFeedback(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateCustomPageFeedback(customPageFeedback);
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

	public static java.util.List<com.liferay.portal.model.Layout> getCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCustomPagesByPageTypeAndLayoutUserId(pageType, userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getCustomPagesByLayoutUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPagesByLayoutUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getCustomPagesByLayoutUserIdAndCustomPageFeedback(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCustomPagesByLayoutUserIdAndCustomPageFeedback(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType,
			userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId) {
		return getService()
				   .getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType,
			userId);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		long plid, long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCustomPageFeedback(plid, userId, feedbackStatus);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback addCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addCustomPageFeedback(plid, userId);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback updateCustomPageFeedbackStatus(
		long plid, long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getService()
				   .updateCustomPageFeedbackStatus(plid, userId, feedbackStatus);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback deleteCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getService().deleteCustomPageFeedback(plid, userId);
	}

	public static void deleteCustomPageFeedbackByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCustomPageFeedbackByPlid(plid);
	}

	public static void deleteCustomPageFeedbackByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteCustomPageFeedbackByUserId(userId);
	}

	public static void deleteCustomPageFeedbackByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService()
			.deleteCustomPageFeedbackByPlidAndFeedbackStatus(plid,
			feedbackStatus);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback getCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getService().getCustomPageFeedback(plid, userId);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchCustomPageFeedback(
		long plid, long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchCustomPageFeedback(plid, userId);
	}

	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPageFeedbackByPlid(plid);
	}

	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPageFeedbackByUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> getCustomPageFeedbackByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCustomPageFeedbackByPlidAndFeedbackStatus(plid,
			feedbackStatus);
	}

	public static java.util.List<java.lang.Object> getCustomPagePlidsByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCustomPagePlidsByUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.User> getUsersByCustomPageFeedback(
		long plid) {
		return getService().getUsersByCustomPageFeedback(plid);
	}

	public static void clearService() {
		_service = null;
	}

	public static CustomPageFeedbackLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					CustomPageFeedbackLocalService.class.getName());

			if (invokableLocalService instanceof CustomPageFeedbackLocalService) {
				_service = (CustomPageFeedbackLocalService)invokableLocalService;
			}
			else {
				_service = new CustomPageFeedbackLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(CustomPageFeedbackLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(CustomPageFeedbackLocalService service) {
	}

	private static CustomPageFeedbackLocalService _service;
}