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

package de.unipotsdam.elis.activities.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import de.unipotsdam.elis.activities.model.MoodleSocialActivity;

/**
 * The persistence interface for the moodle social activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see MoodleSocialActivityPersistenceImpl
 * @see MoodleSocialActivityUtil
 * @generated
 */
public interface MoodleSocialActivityPersistence extends BasePersistence<MoodleSocialActivity> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MoodleSocialActivityUtil} to access the moodle social activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the moodle social activity where extSocialActivityId = &#63; or throws a {@link de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException} if it could not be found.
	*
	* @param extSocialActivityId the ext social activity ID
	* @return the matching moodle social activity
	* @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a matching moodle social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity findByextSocialActivityId(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException;

	/**
	* Returns the moodle social activity where extSocialActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param extSocialActivityId the ext social activity ID
	* @return the matching moodle social activity, or <code>null</code> if a matching moodle social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity fetchByextSocialActivityId(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the moodle social activity where extSocialActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param extSocialActivityId the ext social activity ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching moodle social activity, or <code>null</code> if a matching moodle social activity could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity fetchByextSocialActivityId(
		long extSocialActivityId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the moodle social activity where extSocialActivityId = &#63; from the database.
	*
	* @param extSocialActivityId the ext social activity ID
	* @return the moodle social activity that was removed
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity removeByextSocialActivityId(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException;

	/**
	* Returns the number of moodle social activities where extSocialActivityId = &#63;.
	*
	* @param extSocialActivityId the ext social activity ID
	* @return the number of matching moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countByextSocialActivityId(long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the moodle social activity in the entity cache if it is enabled.
	*
	* @param moodleSocialActivity the moodle social activity
	*/
	public void cacheResult(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity);

	/**
	* Caches the moodle social activities in the entity cache if it is enabled.
	*
	* @param moodleSocialActivities the moodle social activities
	*/
	public void cacheResult(
		java.util.List<de.unipotsdam.elis.activities.model.MoodleSocialActivity> moodleSocialActivities);

	/**
	* Creates a new moodle social activity with the primary key. Does not add the moodle social activity to the database.
	*
	* @param extSocialActivityId the primary key for the new moodle social activity
	* @return the new moodle social activity
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity create(
		long extSocialActivityId);

	/**
	* Removes the moodle social activity with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param extSocialActivityId the primary key of the moodle social activity
	* @return the moodle social activity that was removed
	* @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity remove(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException;

	public de.unipotsdam.elis.activities.model.MoodleSocialActivity updateImpl(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the moodle social activity with the primary key or throws a {@link de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException} if it could not be found.
	*
	* @param extSocialActivityId the primary key of the moodle social activity
	* @return the moodle social activity
	* @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity findByPrimaryKey(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException;

	/**
	* Returns the moodle social activity with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param extSocialActivityId the primary key of the moodle social activity
	* @return the moodle social activity, or <code>null</code> if a moodle social activity with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity fetchByPrimaryKey(
		long extSocialActivityId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the moodle social activities.
	*
	* @return the moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.activities.model.MoodleSocialActivity> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<de.unipotsdam.elis.activities.model.MoodleSocialActivity> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the moodle social activities.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of moodle social activities
	* @param end the upper bound of the range of moodle social activities (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.activities.model.MoodleSocialActivity> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the moodle social activities from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of moodle social activities.
	*
	* @return the number of moodle social activities
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}