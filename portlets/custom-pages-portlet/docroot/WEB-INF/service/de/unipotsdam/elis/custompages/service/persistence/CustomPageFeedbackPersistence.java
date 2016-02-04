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

package de.unipotsdam.elis.custompages.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;

/**
 * The persistence interface for the custom page feedback service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see CustomPageFeedbackPersistenceImpl
 * @see CustomPageFeedbackUtil
 * @generated
 */
public interface CustomPageFeedbackPersistence extends BasePersistence<CustomPageFeedback> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CustomPageFeedbackUtil} to access the custom page feedback persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the custom page feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the custom page feedbacks where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @return the range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the custom page feedbacks where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the custom page feedbacks before and after the current custom page feedback in the ordered set where plid = &#63;.
	*
	* @param customPageFeedbackPK the primary key of the current custom page feedback
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByPlid_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Removes all the custom page feedbacks where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of custom page feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the custom page feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the custom page feedbacks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @return the range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the custom page feedbacks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the first custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the last custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the custom page feedbacks before and after the current custom page feedback in the ordered set where userId = &#63;.
	*
	* @param customPageFeedbackPK the primary key of the current custom page feedback
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByUserId_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Removes all the custom page feedbacks where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of custom page feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @return the range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the custom page feedbacks before and after the current custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param customPageFeedbackPK the primary key of the current custom page feedback
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByPlidAndFeedbackStatus_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Removes all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63; from the database.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the custom page feedback in the entity cache if it is enabled.
	*
	* @param customPageFeedback the custom page feedback
	*/
	public void cacheResult(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback);

	/**
	* Caches the custom page feedbacks in the entity cache if it is enabled.
	*
	* @param customPageFeedbacks the custom page feedbacks
	*/
	public void cacheResult(
		java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> customPageFeedbacks);

	/**
	* Creates a new custom page feedback with the primary key. Does not add the custom page feedback to the database.
	*
	* @param customPageFeedbackPK the primary key for the new custom page feedback
	* @return the new custom page feedback
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback create(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK);

	/**
	* Removes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback that was removed
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback remove(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	public de.unipotsdam.elis.custompages.model.CustomPageFeedback updateImpl(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the custom page feedback with the primary key or throws a {@link de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException} if it could not be found.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPrimaryKey(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;

	/**
	* Returns the custom page feedback with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback, or <code>null</code> if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPrimaryKey(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the custom page feedbacks.
	*
	* @return the custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the custom page feedbacks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of custom page feedbacks
	* @param end the upper bound of the range of custom page feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the custom page feedbacks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of custom page feedbacks.
	*
	* @return the number of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}