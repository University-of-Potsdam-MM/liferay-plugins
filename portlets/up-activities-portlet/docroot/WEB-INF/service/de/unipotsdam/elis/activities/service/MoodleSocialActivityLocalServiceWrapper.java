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

package de.unipotsdam.elis.activities.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link MoodleSocialActivityLocalService}.
 *
 * @author Matthias
 * @see MoodleSocialActivityLocalService
 * @generated
 */
public class MoodleSocialActivityLocalServiceWrapper
	implements MoodleSocialActivityLocalService,
		ServiceWrapper<MoodleSocialActivityLocalService> {
	public MoodleSocialActivityLocalServiceWrapper(
		MoodleSocialActivityLocalService moodleSocialActivityLocalService) {
		_moodleSocialActivityLocalService = moodleSocialActivityLocalService;
	}

	/**
	* Adds the moodle social activity to the database. Also notifies the appropriate model listeners.
	*
	* @param moodleSocialActivity the moodle social activity
	* @return the moodle social activity that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity addMoodleSocialActivity(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.addMoodleSocialActivity(moodleSocialActivity);
	}

	/**
	* Creates a new moodle social activity with the primary key. Does not add the moodle social activity to the database.
	*
	* @param extSocialActivityId the primary key for the new moodle social activity
	* @return the new moodle social activity
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity createMoodleSocialActivity(
		long extSocialActivityId) {
		return _moodleSocialActivityLocalService.createMoodleSocialActivity(extSocialActivityId);
	}

	/**
	* Deletes the moodle social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param extSocialActivityId the primary key of the moodle social activity
	* @return the moodle social activity that was removed
	* @throws PortalException if a moodle social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity deleteMoodleSocialActivity(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.deleteMoodleSocialActivity(extSocialActivityId);
	}

	/**
	* Deletes the moodle social activity from the database. Also notifies the appropriate model listeners.
	*
	* @param moodleSocialActivity the moodle social activity
	* @return the moodle social activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity deleteMoodleSocialActivity(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.deleteMoodleSocialActivity(moodleSocialActivity);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _moodleSocialActivityLocalService.dynamicQuery();
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
		return _moodleSocialActivityLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _moodleSocialActivityLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
		return _moodleSocialActivityLocalService.dynamicQuery(dynamicQuery,
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
		return _moodleSocialActivityLocalService.dynamicQueryCount(dynamicQuery);
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
		return _moodleSocialActivityLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity fetchMoodleSocialActivity(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.fetchMoodleSocialActivity(extSocialActivityId);
	}

	/**
	* Returns the moodle social activity with the primary key.
	*
	* @param extSocialActivityId the primary key of the moodle social activity
	* @return the moodle social activity
	* @throws PortalException if a moodle social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity getMoodleSocialActivity(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.getMoodleSocialActivity(extSocialActivityId);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the moodle social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of moodle social activities
	* @param end the upper bound of the range of moodle social activities (not inclusive)
	* @return the range of moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<de.unipotsdam.elis.activities.model.MoodleSocialActivity> getMoodleSocialActivities(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.getMoodleSocialActivities(start,
			end);
	}

	/**
	* Returns the number of moodle social activities.
	*
	* @return the number of moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getMoodleSocialActivitiesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.getMoodleSocialActivitiesCount();
	}

	/**
	* Updates the moodle social activity in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param moodleSocialActivity the moodle social activity
	* @return the moodle social activity that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity updateMoodleSocialActivity(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.updateMoodleSocialActivity(moodleSocialActivity);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _moodleSocialActivityLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_moodleSocialActivityLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _moodleSocialActivityLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity getMostRecentMoodleSocialActivity(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.getMostRecentMoodleSocialActivity(userId);
	}

	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity addMoodleSocialActivity(
		long userId, int type, java.lang.String extServiceActivityType,
		java.lang.String extServiceContext, java.lang.String data,
		long published)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivityLocalService.addMoodleSocialActivity(userId,
			type, extServiceActivityType, extServiceContext, data, published);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public MoodleSocialActivityLocalService getWrappedMoodleSocialActivityLocalService() {
		return _moodleSocialActivityLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedMoodleSocialActivityLocalService(
		MoodleSocialActivityLocalService moodleSocialActivityLocalService) {
		_moodleSocialActivityLocalService = moodleSocialActivityLocalService;
	}

	@Override
	public MoodleSocialActivityLocalService getWrappedService() {
		return _moodleSocialActivityLocalService;
	}

	@Override
	public void setWrappedService(
		MoodleSocialActivityLocalService moodleSocialActivityLocalService) {
		_moodleSocialActivityLocalService = moodleSocialActivityLocalService;
	}

	private MoodleSocialActivityLocalService _moodleSocialActivityLocalService;
}