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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;

import java.util.List;

/**
 * The persistence utility for the custom page feedback service. This utility wraps {@link CustomPageFeedbackPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see CustomPageFeedbackPersistence
 * @see CustomPageFeedbackPersistenceImpl
 * @generated
 */
public class CustomPageFeedbackUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(CustomPageFeedback customPageFeedback) {
		getPersistence().clearCache(customPageFeedback);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CustomPageFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CustomPageFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CustomPageFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static CustomPageFeedback update(
		CustomPageFeedback customPageFeedback) throws SystemException {
		return getPersistence().update(customPageFeedback);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static CustomPageFeedback update(
		CustomPageFeedback customPageFeedback, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(customPageFeedback, serviceContext);
	}

	/**
	* Returns all the custom page feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

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
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByPlid_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence()
				   .findByPlid_PrevAndNext(customPageFeedbackPK, plid,
			orderByComparator);
	}

	/**
	* Removes all the custom page feedbacks where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Returns the number of custom page feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Returns all the custom page feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last custom page feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

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
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByUserId_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence()
				   .findByUserId_PrevAndNext(customPageFeedbackPK, userId,
			orderByComparator);
	}

	/**
	* Removes all the custom page feedbacks where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of custom page feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus(plid, feedbackStatus, start, end);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus(plid, feedbackStatus, start,
			end, orderByComparator);
	}

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
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_First(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the first custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPlidAndFeedbackStatus_First(plid, feedbackStatus,
			orderByComparator);
	}

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
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_Last(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the last custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPlidAndFeedbackStatus_Last(plid, feedbackStatus,
			orderByComparator);
	}

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
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback[] findByPlidAndFeedbackStatus_PrevAndNext(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK,
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_PrevAndNext(customPageFeedbackPK,
			plid, feedbackStatus, orderByComparator);
	}

	/**
	* Removes all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63; from the database.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPlidAndFeedbackStatus(long plid,
		int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	/**
	* Returns the number of custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the number of matching custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	/**
	* Caches the custom page feedback in the entity cache if it is enabled.
	*
	* @param customPageFeedback the custom page feedback
	*/
	public static void cacheResult(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback) {
		getPersistence().cacheResult(customPageFeedback);
	}

	/**
	* Caches the custom page feedbacks in the entity cache if it is enabled.
	*
	* @param customPageFeedbacks the custom page feedbacks
	*/
	public static void cacheResult(
		java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> customPageFeedbacks) {
		getPersistence().cacheResult(customPageFeedbacks);
	}

	/**
	* Creates a new custom page feedback with the primary key. Does not add the custom page feedback to the database.
	*
	* @param customPageFeedbackPK the primary key for the new custom page feedback
	* @return the new custom page feedback
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback create(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK) {
		return getPersistence().create(customPageFeedbackPK);
	}

	/**
	* Removes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback that was removed
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback remove(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().remove(customPageFeedbackPK);
	}

	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback updateImpl(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(customPageFeedback);
	}

	/**
	* Returns the custom page feedback with the primary key or throws a {@link de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException} if it could not be found.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback
	* @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback findByPrimaryKey(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException {
		return getPersistence().findByPrimaryKey(customPageFeedbackPK);
	}

	/**
	* Returns the custom page feedback with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param customPageFeedbackPK the primary key of the custom page feedback
	* @return the custom page feedback, or <code>null</code> if a custom page feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.custompages.model.CustomPageFeedback fetchByPrimaryKey(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK customPageFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(customPageFeedbackPK);
	}

	/**
	* Returns all the custom page feedbacks.
	*
	* @return the custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

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
	public static java.util.List<de.unipotsdam.elis.custompages.model.CustomPageFeedback> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the custom page feedbacks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of custom page feedbacks.
	*
	* @return the number of custom page feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static CustomPageFeedbackPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (CustomPageFeedbackPersistence)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.custompages.service.ClpSerializer.getServletContextName(),
					CustomPageFeedbackPersistence.class.getName());

			ReferenceRegistry.registerReference(CustomPageFeedbackUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(CustomPageFeedbackPersistence persistence) {
	}

	private static CustomPageFeedbackPersistence _persistence;
}