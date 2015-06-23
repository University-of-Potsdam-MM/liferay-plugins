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

import de.unipotsdam.elis.portfolio.model.PortfolioPermission;

import java.util.List;

/**
 * The persistence utility for the portfolio permission service. This utility wraps {@link PortfolioPermissionPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioPermissionPersistence
 * @see PortfolioPermissionPersistenceImpl
 * @generated
 */
public class PortfolioPermissionUtil {
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
	public static void clearCache(PortfolioPermission portfolioPermission) {
		getPersistence().clearCache(portfolioPermission);
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
	public static List<PortfolioPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<PortfolioPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<PortfolioPermission> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static PortfolioPermission update(
		PortfolioPermission portfolioPermission) throws SystemException {
		return getPersistence().update(portfolioPermission);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static PortfolioPermission update(
		PortfolioPermission portfolioPermission, ServiceContext serviceContext)
		throws SystemException {
		return getPersistence().update(portfolioPermission, serviceContext);
	}

	/**
	* Returns all the portfolio permissions where plid = &#63;.
	*
	* @param plid the plid
	* @return the matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByPlid(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid);
	}

	/**
	* Returns a range of all the portfolio permissions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @return the range of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByPlid(
		long plid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end);
	}

	/**
	* Returns an ordered range of all the portfolio permissions where plid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param plid the plid
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByPlid(
		long plid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByPlid(plid, start, end, orderByComparator);
	}

	/**
	* Returns the first portfolio permission in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission findByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().findByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the first portfolio permission in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchByPlid_First(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_First(plid, orderByComparator);
	}

	/**
	* Returns the last portfolio permission in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission findByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().findByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the last portfolio permission in the ordered set where plid = &#63;.
	*
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchByPlid_Last(
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPlid_Last(plid, orderByComparator);
	}

	/**
	* Returns the portfolio permissions before and after the current portfolio permission in the ordered set where plid = &#63;.
	*
	* @param portfolioPermissionPK the primary key of the current portfolio permission
	* @param plid the plid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission[] findByPlid_PrevAndNext(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK,
		long plid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence()
				   .findByPlid_PrevAndNext(portfolioPermissionPK, plid,
			orderByComparator);
	}

	/**
	* Removes all the portfolio permissions where plid = &#63; from the database.
	*
	* @param plid the plid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByPlid(plid);
	}

	/**
	* Returns the number of portfolio permissions where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByPlid(plid);
	}

	/**
	* Returns all the portfolio permissions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId);
	}

	/**
	* Returns a range of all the portfolio permissions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @return the range of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByUserId(userId, start, end);
	}

	/**
	* Returns an ordered range of all the portfolio permissions where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param userId the user ID
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByUserId(userId, start, end, orderByComparator);
	}

	/**
	* Returns the first portfolio permission in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission findByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().findByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the first portfolio permission in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchByUserId_First(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_First(userId, orderByComparator);
	}

	/**
	* Returns the last portfolio permission in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission findByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().findByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the last portfolio permission in the ordered set where userId = &#63;.
	*
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchByUserId_Last(
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByUserId_Last(userId, orderByComparator);
	}

	/**
	* Returns the portfolio permissions before and after the current portfolio permission in the ordered set where userId = &#63;.
	*
	* @param portfolioPermissionPK the primary key of the current portfolio permission
	* @param userId the user ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission[] findByUserId_PrevAndNext(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK,
		long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence()
				   .findByUserId_PrevAndNext(portfolioPermissionPK, userId,
			orderByComparator);
	}

	/**
	* Removes all the portfolio permissions where userId = &#63; from the database.
	*
	* @param userId the user ID
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByUserId(userId);
	}

	/**
	* Returns the number of portfolio permissions where userId = &#63;.
	*
	* @param userId the user ID
	* @return the number of matching portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByUserId(userId);
	}

	/**
	* Caches the portfolio permission in the entity cache if it is enabled.
	*
	* @param portfolioPermission the portfolio permission
	*/
	public static void cacheResult(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission) {
		getPersistence().cacheResult(portfolioPermission);
	}

	/**
	* Caches the portfolio permissions in the entity cache if it is enabled.
	*
	* @param portfolioPermissions the portfolio permissions
	*/
	public static void cacheResult(
		java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> portfolioPermissions) {
		getPersistence().cacheResult(portfolioPermissions);
	}

	/**
	* Creates a new portfolio permission with the primary key. Does not add the portfolio permission to the database.
	*
	* @param portfolioPermissionPK the primary key for the new portfolio permission
	* @return the new portfolio permission
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission create(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK) {
		return getPersistence().create(portfolioPermissionPK);
	}

	/**
	* Removes the portfolio permission with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission that was removed
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission remove(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().remove(portfolioPermissionPK);
	}

	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission updateImpl(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(portfolioPermission);
	}

	/**
	* Returns the portfolio permission with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPermissionException} if it could not be found.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission
	* @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission findByPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPermissionException {
		return getPersistence().findByPrimaryKey(portfolioPermissionPK);
	}

	/**
	* Returns the portfolio permission with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param portfolioPermissionPK the primary key of the portfolio permission
	* @return the portfolio permission, or <code>null</code> if a portfolio permission with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static de.unipotsdam.elis.portfolio.model.PortfolioPermission fetchByPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK portfolioPermissionPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(portfolioPermissionPK);
	}

	/**
	* Returns all the portfolio permissions.
	*
	* @return the portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the portfolio permissions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolio permissions
	* @param end the upper bound of the range of portfolio permissions (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioPermission> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the portfolio permissions from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of portfolio permissions.
	*
	* @return the number of portfolio permissions
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static PortfolioPermissionPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (PortfolioPermissionPersistence)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.portfolio.service.ClpSerializer.getServletContextName(),
					PortfolioPermissionPersistence.class.getName());

			ReferenceRegistry.registerReference(PortfolioPermissionUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(PortfolioPermissionPersistence persistence) {
	}

	private static PortfolioPermissionPersistence _persistence;
}