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

package de.unipotsdam.elis.portfolio.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;

import java.util.List;

/**
 * The persistence utility for the portfolio feedback service. This utility wraps {@link PortfolioFeedbackPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioFeedbackPersistence
 * @see PortfolioFeedbackPersistenceImpl
 * @generated
 */
public class PortfolioFeedbackUtil {
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
	public static void clearCache(PortfolioFeedback portfolioFeedback) {
		getPersistence().clearCache(portfolioFeedback);
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
	public static List<PortfolioFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortfolioFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortfolioFeedback> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PortfolioFeedback update(PortfolioFeedback portfolioFeedback)
		throws SystemException {
		return getPersistence().update(portfolioFeedback);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PortfolioFeedback update(
		PortfolioFeedback portfolioFeedback, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(portfolioFeedback, serviceContext);
	}

	/**
	* Returns all the portfolio feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid);
	}

	/**
	* Returns a range of all the portfolio feedbacks where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @return the range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	* Returns an ordered range of all the portfolio feedbacks where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the portfolio feedbacks before and after the current portfolio feedback in the ordered set where plid = &#63;.
	*
	* @param portfolioFeedbackPK the primary key of the current portfolio feedback
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback[] findByPlid_PrevAndNext(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence()
				   .findByPlid_PrevAndNext(portfolioFeedbackPK, plid,
			orderByComparator);
	}

	/**
	* Removes all the portfolio feedbacks where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Returns the number of portfolio feedbacks where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Returns all the portfolio feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the portfolio feedbacks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @return the range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the portfolio feedbacks where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the portfolio feedbacks before and after the current portfolio feedback in the ordered set where userId = &#63;.
	*
	* @param portfolioFeedbackPK the primary key of the current portfolio feedback
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback[] findByUserId_PrevAndNext(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence()
				   .findByUserId_PrevAndNext(portfolioFeedbackPK, userId,
			orderByComparator);
	}

	/**
	* Removes all the portfolio feedbacks where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of portfolio feedbacks where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Returns all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	/**
	* Returns a range of all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @return the range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus(plid, feedbackStatus, start, end);
	}

	/**
	* Returns an ordered range of all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findByPlidAndFeedbackStatus(
		long plid, int feedbackStatus, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus(plid, feedbackStatus, start,
			end, orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_First(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the first portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByPlidAndFeedbackStatus_First(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPlidAndFeedbackStatus_First(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_Last(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the last portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByPlidAndFeedbackStatus_Last(
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByPlidAndFeedbackStatus_Last(plid, feedbackStatus,
			orderByComparator);
	}

	/**
	* Returns the portfolio feedbacks before and after the current portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param portfolioFeedbackPK the primary key of the current portfolio feedback
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback[] findByPlidAndFeedbackStatus_PrevAndNext(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK,
		long plid, int feedbackStatus,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence()
				   .findByPlidAndFeedbackStatus_PrevAndNext(portfolioFeedbackPK,
			plid, feedbackStatus, orderByComparator);
	}

	/**
	* Removes all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63; from the database.
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
	* Returns the number of portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	*
	* @param plid the plid
	* @param feedbackStatus the feedback status
	* @return the number of matching portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	/**
	* Caches the portfolio feedback in the entity cache if it is enabled.
	*
	* @param portfolioFeedback the portfolio feedback
	*/
	public static void cacheResult(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback) {
		getPersistence().cacheResult(portfolioFeedback);
	}

	/**
	* Caches the portfolio feedbacks in the entity cache if it is enabled.
	*
	* @param portfolioFeedbacks the portfolio feedbacks
	*/
	public static void cacheResult(
		java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> portfolioFeedbacks) {
		getPersistence().cacheResult(portfolioFeedbacks);
	}

	/**
	* Creates a new portfolio feedback with the primary key. Does not add the portfolio feedback to the database.
	*
	* @param portfolioFeedbackPK the primary key for the new portfolio feedback
	* @return the new portfolio feedback
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback create(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK) {
		return getPersistence().create(portfolioFeedbackPK);
	}

	/**
	* Removes the portfolio feedback with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioFeedbackPK the primary key of the portfolio feedback
	* @return the portfolio feedback that was removed
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback remove(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().remove(portfolioFeedbackPK);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback updateImpl(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portfolioFeedback);
	}

	/**
	* Returns the portfolio feedback with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchFeedbackException} if it could not be found.
	*
	* @param portfolioFeedbackPK the primary key of the portfolio feedback
	* @return the portfolio feedback
	* @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback findByPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		return getPersistence().findByPrimaryKey(portfolioFeedbackPK);
	}

	/**
	* Returns the portfolio feedback with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portfolioFeedbackPK the primary key of the portfolio feedback
	* @return the portfolio feedback, or <code>null</code> if a portfolio feedback with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioFeedback fetchByPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK portfolioFeedbackPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(portfolioFeedbackPK);
	}

	/**
	* Returns all the portfolio feedbacks.
	*
	* @return the portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the portfolio feedbacks.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolio feedbacks
	* @param end the upper bound of the range of portfolio feedbacks (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the portfolio feedbacks from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of portfolio feedbacks.
	*
	* @return the number of portfolio feedbacks
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortfolioFeedbackPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortfolioFeedbackPersistence)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.portfolio.service.ClpSerializer.getServletContextName(),
					PortfolioFeedbackPersistence.class.getName());

			ReferenceRegistry.registerReference(PortfolioFeedbackUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(PortfolioFeedbackPersistence persistence) {
	}

	private static PortfolioFeedbackPersistence _persistence;
}