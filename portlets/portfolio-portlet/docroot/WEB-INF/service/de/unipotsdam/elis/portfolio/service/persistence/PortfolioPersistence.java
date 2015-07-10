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

import com.liferay.portal.service.persistence.BasePersistence;

import de.unipotsdam.elis.portfolio.model.Portfolio;

/**
 * The persistence interface for the portfolio service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioPersistenceImpl
 * @see PortfolioUtil
 * @generated
 */
public interface PortfolioPersistence extends BasePersistence<Portfolio> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortfolioUtil} to access the portfolio persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns the portfolio where plid = &#63; or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPortfolioException} if it could not be found.
	*
	* @param plid the plid
	* @return the matching portfolio
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio findByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Returns the portfolio where plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param plid the plid
	* @return the matching portfolio, or <code>null</code> if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio fetchByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portfolio where plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param plid the plid
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching portfolio, or <code>null</code> if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio fetchByPlid(long plid,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the portfolio where plid = &#63; from the database.
	*
	* @param plid the plid
	* @return the portfolio that was removed
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio removeByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Returns the number of portfolios where plid = &#63;.
	*
	* @param plid the plid
	* @return the number of matching portfolios
	* @throws SystemException if a system exception occurred
	*/
	public int countByPlid(long plid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portfolios where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @return the matching portfolios
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentType(
		int publishmentType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the portfolios where publishmentType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishmentType the publishment type
	* @param start the lower bound of the range of portfolios
	* @param end the upper bound of the range of portfolios (not inclusive)
	* @return the range of matching portfolios
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentType(
		int publishmentType, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portfolios where publishmentType = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param publishmentType the publishment type
	* @param start the lower bound of the range of portfolios
	* @param end the upper bound of the range of portfolios (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching portfolios
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentType(
		int publishmentType, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first portfolio in the ordered set where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio findByPublishmentType_First(
		int publishmentType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Returns the first portfolio in the ordered set where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching portfolio, or <code>null</code> if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio fetchByPublishmentType_First(
		int publishmentType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last portfolio in the ordered set where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio findByPublishmentType_Last(
		int publishmentType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Returns the last portfolio in the ordered set where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching portfolio, or <code>null</code> if a matching portfolio could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio fetchByPublishmentType_Last(
		int publishmentType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portfolios before and after the current portfolio in the ordered set where publishmentType = &#63;.
	*
	* @param plid the primary key of the current portfolio
	* @param publishmentType the publishment type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next portfolio
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio[] findByPublishmentType_PrevAndNext(
		long plid, int publishmentType,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Removes all the portfolios where publishmentType = &#63; from the database.
	*
	* @param publishmentType the publishment type
	* @throws SystemException if a system exception occurred
	*/
	public void removeByPublishmentType(int publishmentType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portfolios where publishmentType = &#63;.
	*
	* @param publishmentType the publishment type
	* @return the number of matching portfolios
	* @throws SystemException if a system exception occurred
	*/
	public int countByPublishmentType(int publishmentType)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Caches the portfolio in the entity cache if it is enabled.
	*
	* @param portfolio the portfolio
	*/
	public void cacheResult(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio);

	/**
	* Caches the portfolios in the entity cache if it is enabled.
	*
	* @param portfolios the portfolios
	*/
	public void cacheResult(
		java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> portfolios);

	/**
	* Creates a new portfolio with the primary key. Does not add the portfolio to the database.
	*
	* @param plid the primary key for the new portfolio
	* @return the new portfolio
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio create(long plid);

	/**
	* Removes the portfolio with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param plid the primary key of the portfolio
	* @return the portfolio that was removed
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio remove(long plid)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	public de.unipotsdam.elis.portfolio.model.Portfolio updateImpl(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the portfolio with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPortfolioException} if it could not be found.
	*
	* @param plid the primary key of the portfolio
	* @return the portfolio
	* @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio findByPrimaryKey(
		long plid)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchPortfolioException;

	/**
	* Returns the portfolio with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param plid the primary key of the portfolio
	* @return the portfolio, or <code>null</code> if a portfolio with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public de.unipotsdam.elis.portfolio.model.Portfolio fetchByPrimaryKey(
		long plid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the portfolios.
	*
	* @return the portfolios
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the portfolios.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of portfolios
	* @param end the upper bound of the range of portfolios (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of portfolios
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portfolios from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of portfolios.
	*
	* @return the number of portfolios
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}