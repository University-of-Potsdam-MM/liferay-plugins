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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.unipotsdam.elis.portfolio.NoSuchPortfolioException;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioImpl;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the portfolio service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioPersistence
 * @see PortfolioUtil
 * @generated
 */
public class PortfolioPersistenceImpl extends BasePersistenceImpl<Portfolio>
	implements PortfolioPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortfolioUtil} to access the portfolio persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortfolioImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, PortfolioImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, PortfolioImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_PLID = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, PortfolioImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByPlid",
			new String[] { Long.class.getName() },
			PortfolioModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });

	/**
	 * Returns the portfolio where plid = &#63; or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPortfolioException} if it could not be found.
	 *
	 * @param plid the plid
	 * @return the matching portfolio
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio findByPlid(long plid)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = fetchByPlid(plid);

		if (portfolio == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchPortfolioException(msg.toString());
		}

		return portfolio;
	}

	/**
	 * Returns the portfolio where plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @return the matching portfolio, or <code>null</code> if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPlid(long plid) throws SystemException {
		return fetchByPlid(plid, true);
	}

	/**
	 * Returns the portfolio where plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching portfolio, or <code>null</code> if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPlid(long plid, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { plid };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_PLID,
					finderArgs, this);
		}

		if (result instanceof Portfolio) {
			Portfolio portfolio = (Portfolio)result;

			if ((plid != portfolio.getPlid())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_PORTFOLIO_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				List<Portfolio> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PLID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"PortfolioPersistenceImpl.fetchByPlid(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					Portfolio portfolio = list.get(0);

					result = portfolio;

					cacheResult(portfolio);

					if ((portfolio.getPlid() != plid)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PLID,
							finderArgs, portfolio);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PLID,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (Portfolio)result;
		}
	}

	/**
	 * Removes the portfolio where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @return the portfolio that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio removeByPlid(long plid)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = findByPlid(plid);

		return remove(portfolio);
	}

	/**
	 * Returns the number of portfolios where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPlid(long plid) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PLID;

		Object[] finderArgs = new Object[] { plid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTFOLIO_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PLID_PLID_2 = "portfolio.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PUBLISHMENTTYPE =
		new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, PortfolioImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPublishmentType",
			new String[] {
				Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHMENTTYPE =
		new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, PortfolioImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPublishmentType",
			new String[] { Integer.class.getName() },
			PortfolioModelImpl.PUBLISHMENTTYPE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PUBLISHMENTTYPE = new FinderPath(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPublishmentType", new String[] { Integer.class.getName() });

	/**
	 * Returns all the portfolios where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @return the matching portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Portfolio> findByPublishmentType(int publishmentType)
		throws SystemException {
		return findByPublishmentType(publishmentType, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Portfolio> findByPublishmentType(int publishmentType,
		int start, int end) throws SystemException {
		return findByPublishmentType(publishmentType, start, end, null);
	}

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
	@Override
	public List<Portfolio> findByPublishmentType(int publishmentType,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHMENTTYPE;
			finderArgs = new Object[] { publishmentType };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PUBLISHMENTTYPE;
			finderArgs = new Object[] {
					publishmentType,
					
					start, end, orderByComparator
				};
		}

		List<Portfolio> list = (List<Portfolio>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (Portfolio portfolio : list) {
				if ((publishmentType != portfolio.getPublishmentType())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_PORTFOLIO_WHERE);

			query.append(_FINDER_COLUMN_PUBLISHMENTTYPE_PUBLISHMENTTYPE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(publishmentType);

				if (!pagination) {
					list = (List<Portfolio>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Portfolio>(list);
				}
				else {
					list = (List<Portfolio>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first portfolio in the ordered set where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio findByPublishmentType_First(int publishmentType,
		OrderByComparator orderByComparator)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = fetchByPublishmentType_First(publishmentType,
				orderByComparator);

		if (portfolio != null) {
			return portfolio;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("publishmentType=");
		msg.append(publishmentType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortfolioException(msg.toString());
	}

	/**
	 * Returns the first portfolio in the ordered set where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio, or <code>null</code> if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPublishmentType_First(int publishmentType,
		OrderByComparator orderByComparator) throws SystemException {
		List<Portfolio> list = findByPublishmentType(publishmentType, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last portfolio in the ordered set where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio findByPublishmentType_Last(int publishmentType,
		OrderByComparator orderByComparator)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = fetchByPublishmentType_Last(publishmentType,
				orderByComparator);

		if (portfolio != null) {
			return portfolio;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("publishmentType=");
		msg.append(publishmentType);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPortfolioException(msg.toString());
	}

	/**
	 * Returns the last portfolio in the ordered set where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio, or <code>null</code> if a matching portfolio could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPublishmentType_Last(int publishmentType,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPublishmentType(publishmentType);

		if (count == 0) {
			return null;
		}

		List<Portfolio> list = findByPublishmentType(publishmentType,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

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
	@Override
	public Portfolio[] findByPublishmentType_PrevAndNext(long plid,
		int publishmentType, OrderByComparator orderByComparator)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = findByPrimaryKey(plid);

		Session session = null;

		try {
			session = openSession();

			Portfolio[] array = new PortfolioImpl[3];

			array[0] = getByPublishmentType_PrevAndNext(session, portfolio,
					publishmentType, orderByComparator, true);

			array[1] = portfolio;

			array[2] = getByPublishmentType_PrevAndNext(session, portfolio,
					publishmentType, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected Portfolio getByPublishmentType_PrevAndNext(Session session,
		Portfolio portfolio, int publishmentType,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIO_WHERE);

		query.append(_FINDER_COLUMN_PUBLISHMENTTYPE_PUBLISHMENTTYPE_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(PortfolioModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(publishmentType);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolio);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<Portfolio> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolios where publishmentType = &#63; from the database.
	 *
	 * @param publishmentType the publishment type
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPublishmentType(int publishmentType)
		throws SystemException {
		for (Portfolio portfolio : findByPublishmentType(publishmentType,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolio);
		}
	}

	/**
	 * Returns the number of portfolios where publishmentType = &#63;.
	 *
	 * @param publishmentType the publishment type
	 * @return the number of matching portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPublishmentType(int publishmentType)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PUBLISHMENTTYPE;

		Object[] finderArgs = new Object[] { publishmentType };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTFOLIO_WHERE);

			query.append(_FINDER_COLUMN_PUBLISHMENTTYPE_PUBLISHMENTTYPE_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(publishmentType);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PUBLISHMENTTYPE_PUBLISHMENTTYPE_2 =
		"portfolio.publishmentType = ?";

	public PortfolioPersistenceImpl() {
		setModelClass(Portfolio.class);
	}

	/**
	 * Caches the portfolio in the entity cache if it is enabled.
	 *
	 * @param portfolio the portfolio
	 */
	@Override
	public void cacheResult(Portfolio portfolio) {
		EntityCacheUtil.putResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioImpl.class, portfolio.getPrimaryKey(), portfolio);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PLID,
			new Object[] { portfolio.getPlid() }, portfolio);

		portfolio.resetOriginalValues();
	}

	/**
	 * Caches the portfolios in the entity cache if it is enabled.
	 *
	 * @param portfolios the portfolios
	 */
	@Override
	public void cacheResult(List<Portfolio> portfolios) {
		for (Portfolio portfolio : portfolios) {
			if (EntityCacheUtil.getResult(
						PortfolioModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioImpl.class, portfolio.getPrimaryKey()) == null) {
				cacheResult(portfolio);
			}
			else {
				portfolio.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portfolios.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortfolioImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortfolioImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portfolio.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Portfolio portfolio) {
		EntityCacheUtil.removeResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioImpl.class, portfolio.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(portfolio);
	}

	@Override
	public void clearCache(List<Portfolio> portfolios) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Portfolio portfolio : portfolios) {
			EntityCacheUtil.removeResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioImpl.class, portfolio.getPrimaryKey());

			clearUniqueFindersCache(portfolio);
		}
	}

	protected void cacheUniqueFindersCache(Portfolio portfolio) {
		if (portfolio.isNew()) {
			Object[] args = new Object[] { portfolio.getPlid() };

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PLID, args, portfolio);
		}
		else {
			PortfolioModelImpl portfolioModelImpl = (PortfolioModelImpl)portfolio;

			if ((portfolioModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] { portfolio.getPlid() };

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_PLID, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_PLID, args,
					portfolio);
			}
		}
	}

	protected void clearUniqueFindersCache(Portfolio portfolio) {
		PortfolioModelImpl portfolioModelImpl = (PortfolioModelImpl)portfolio;

		Object[] args = new Object[] { portfolio.getPlid() };

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PLID, args);

		if ((portfolioModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_PLID.getColumnBitmask()) != 0) {
			args = new Object[] { portfolioModelImpl.getOriginalPlid() };

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_PLID, args);
		}
	}

	/**
	 * Creates a new portfolio with the primary key. Does not add the portfolio to the database.
	 *
	 * @param plid the primary key for the new portfolio
	 * @return the new portfolio
	 */
	@Override
	public Portfolio create(long plid) {
		Portfolio portfolio = new PortfolioImpl();

		portfolio.setNew(true);
		portfolio.setPrimaryKey(plid);

		return portfolio;
	}

	/**
	 * Removes the portfolio with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param plid the primary key of the portfolio
	 * @return the portfolio that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio remove(long plid)
		throws NoSuchPortfolioException, SystemException {
		return remove((Serializable)plid);
	}

	/**
	 * Removes the portfolio with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portfolio
	 * @return the portfolio that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio remove(Serializable primaryKey)
		throws NoSuchPortfolioException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Portfolio portfolio = (Portfolio)session.get(PortfolioImpl.class,
					primaryKey);

			if (portfolio == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPortfolioException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portfolio);
		}
		catch (NoSuchPortfolioException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Portfolio removeImpl(Portfolio portfolio)
		throws SystemException {
		portfolio = toUnwrappedModel(portfolio);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portfolio)) {
				portfolio = (Portfolio)session.get(PortfolioImpl.class,
						portfolio.getPrimaryKeyObj());
			}

			if (portfolio != null) {
				session.delete(portfolio);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portfolio != null) {
			clearCache(portfolio);
		}

		return portfolio;
	}

	@Override
	public Portfolio updateImpl(
		de.unipotsdam.elis.portfolio.model.Portfolio portfolio)
		throws SystemException {
		portfolio = toUnwrappedModel(portfolio);

		boolean isNew = portfolio.isNew();

		PortfolioModelImpl portfolioModelImpl = (PortfolioModelImpl)portfolio;

		Session session = null;

		try {
			session = openSession();

			if (portfolio.isNew()) {
				session.save(portfolio);

				portfolio.setNew(false);
			}
			else {
				session.merge(portfolio);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortfolioModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portfolioModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHMENTTYPE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioModelImpl.getOriginalPublishmentType()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PUBLISHMENTTYPE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHMENTTYPE,
					args);

				args = new Object[] { portfolioModelImpl.getPublishmentType() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PUBLISHMENTTYPE,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PUBLISHMENTTYPE,
					args);
			}
		}

		EntityCacheUtil.putResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioImpl.class, portfolio.getPrimaryKey(), portfolio);

		clearUniqueFindersCache(portfolio);
		cacheUniqueFindersCache(portfolio);

		return portfolio;
	}

	protected Portfolio toUnwrappedModel(Portfolio portfolio) {
		if (portfolio instanceof PortfolioImpl) {
			return portfolio;
		}

		PortfolioImpl portfolioImpl = new PortfolioImpl();

		portfolioImpl.setNew(portfolio.isNew());
		portfolioImpl.setPrimaryKey(portfolio.getPrimaryKey());

		portfolioImpl.setPlid(portfolio.getPlid());
		portfolioImpl.setPublishmentType(portfolio.getPublishmentType());

		return portfolioImpl;
	}

	/**
	 * Returns the portfolio with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio
	 * @return the portfolio
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = fetchByPrimaryKey(primaryKey);

		if (portfolio == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPortfolioException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portfolio;
	}

	/**
	 * Returns the portfolio with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPortfolioException} if it could not be found.
	 *
	 * @param plid the primary key of the portfolio
	 * @return the portfolio
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPortfolioException if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio findByPrimaryKey(long plid)
		throws NoSuchPortfolioException, SystemException {
		return findByPrimaryKey((Serializable)plid);
	}

	/**
	 * Returns the portfolio with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio
	 * @return the portfolio, or <code>null</code> if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Portfolio portfolio = (Portfolio)EntityCacheUtil.getResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioImpl.class, primaryKey);

		if (portfolio == _nullPortfolio) {
			return null;
		}

		if (portfolio == null) {
			Session session = null;

			try {
				session = openSession();

				portfolio = (Portfolio)session.get(PortfolioImpl.class,
						primaryKey);

				if (portfolio != null) {
					cacheResult(portfolio);
				}
				else {
					EntityCacheUtil.putResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioImpl.class, primaryKey, _nullPortfolio);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortfolioModelImpl.ENTITY_CACHE_ENABLED,
					PortfolioImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portfolio;
	}

	/**
	 * Returns the portfolio with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param plid the primary key of the portfolio
	 * @return the portfolio, or <code>null</code> if a portfolio with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Portfolio fetchByPrimaryKey(long plid) throws SystemException {
		return fetchByPrimaryKey((Serializable)plid);
	}

	/**
	 * Returns all the portfolios.
	 *
	 * @return the portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Portfolio> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

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
	@Override
	public List<Portfolio> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

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
	@Override
	public List<Portfolio> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Portfolio> list = (List<Portfolio>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTFOLIO);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTFOLIO;

				if (pagination) {
					sql = sql.concat(PortfolioModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Portfolio>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Portfolio>(list);
				}
				else {
					list = (List<Portfolio>)QueryUtil.list(q, getDialect(),
							start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the portfolios from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Portfolio portfolio : findAll()) {
			remove(portfolio);
		}
	}

	/**
	 * Returns the number of portfolios.
	 *
	 * @return the number of portfolios
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_PORTFOLIO);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the portfolio persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.portfolio.model.Portfolio")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Portfolio>> listenersList = new ArrayList<ModelListener<Portfolio>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Portfolio>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(PortfolioImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTFOLIO = "SELECT portfolio FROM Portfolio portfolio";
	private static final String _SQL_SELECT_PORTFOLIO_WHERE = "SELECT portfolio FROM Portfolio portfolio WHERE ";
	private static final String _SQL_COUNT_PORTFOLIO = "SELECT COUNT(portfolio) FROM Portfolio portfolio";
	private static final String _SQL_COUNT_PORTFOLIO_WHERE = "SELECT COUNT(portfolio) FROM Portfolio portfolio WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portfolio.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Portfolio exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Portfolio exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PortfolioPersistenceImpl.class);
	private static Portfolio _nullPortfolio = new PortfolioImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Portfolio> toCacheModel() {
				return _nullPortfolioCacheModel;
			}
		};

	private static CacheModel<Portfolio> _nullPortfolioCacheModel = new CacheModel<Portfolio>() {
			@Override
			public Portfolio toEntityModel() {
				return _nullPortfolio;
			}
		};
}