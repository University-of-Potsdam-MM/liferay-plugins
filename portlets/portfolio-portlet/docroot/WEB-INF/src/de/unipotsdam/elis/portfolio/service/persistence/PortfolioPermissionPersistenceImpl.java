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

import de.unipotsdam.elis.portfolio.NoSuchPermissionException;
import de.unipotsdam.elis.portfolio.model.PortfolioPermission;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionImpl;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioPermissionModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the portfolio permission service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioPermissionPersistence
 * @see PortfolioPermissionUtil
 * @generated
 */
public class PortfolioPermissionPersistenceImpl extends BasePersistenceImpl<PortfolioPermission>
	implements PortfolioPermissionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortfolioPermissionUtil} to access the portfolio permission persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortfolioPermissionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] { Long.class.getName() },
			PortfolioPermissionModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portfolio permissions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching portfolio permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioPermission> findByPlid(long plid)
		throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioPermission> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
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
	@Override
	public List<PortfolioPermission> findByPlid(long plid, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID;
			finderArgs = new Object[] { plid, start, end, orderByComparator };
		}

		List<PortfolioPermission> list = (List<PortfolioPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortfolioPermission portfolioPermission : list) {
				if ((plid != portfolioPermission.getPlid())) {
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

			query.append(_SQL_SELECT_PORTFOLIOPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (!pagination) {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioPermission>(list);
				}
				else {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portfolio permission in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio permission
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = fetchByPlid_First(plid,
				orderByComparator);

		if (portfolioPermission != null) {
			return portfolioPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPermissionException(msg.toString());
	}

	/**
	 * Returns the first portfolio permission in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByPlid_First(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortfolioPermission> list = findByPlid(plid, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioPermission findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = fetchByPlid_Last(plid,
				orderByComparator);

		if (portfolioPermission != null) {
			return portfolioPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPermissionException(msg.toString());
	}

	/**
	 * Returns the last portfolio permission in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByPlid_Last(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<PortfolioPermission> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioPermission[] findByPlid_PrevAndNext(
		PortfolioPermissionPK portfolioPermissionPK, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = findByPrimaryKey(portfolioPermissionPK);

		Session session = null;

		try {
			session = openSession();

			PortfolioPermission[] array = new PortfolioPermissionImpl[3];

			array[0] = getByPlid_PrevAndNext(session, portfolioPermission,
					plid, orderByComparator, true);

			array[1] = portfolioPermission;

			array[2] = getByPlid_PrevAndNext(session, portfolioPermission,
					plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortfolioPermission getByPlid_PrevAndNext(Session session,
		PortfolioPermission portfolioPermission, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIOPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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
			query.append(PortfolioPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolioPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortfolioPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolio permissions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPlid(long plid) throws SystemException {
		for (PortfolioPermission portfolioPermission : findByPlid(plid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolioPermission);
		}
	}

	/**
	 * Returns the number of portfolio permissions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching portfolio permissions
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

			query.append(_SQL_COUNT_PORTFOLIOPERMISSION_WHERE);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 = "portfolioPermission.id.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED,
			PortfolioPermissionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			PortfolioPermissionModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portfolio permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching portfolio permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioPermission> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioPermission> findByUserId(long userId, int start,
		int end) throws SystemException {
		return findByUserId(userId, start, end, null);
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
	@Override
	public List<PortfolioPermission> findByUserId(long userId, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID;
			finderArgs = new Object[] { userId, start, end, orderByComparator };
		}

		List<PortfolioPermission> list = (List<PortfolioPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortfolioPermission portfolioPermission : list) {
				if ((userId != portfolioPermission.getUserId())) {
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

			query.append(_SQL_SELECT_PORTFOLIOPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioPermissionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioPermission>(list);
				}
				else {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Returns the first portfolio permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio permission
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = fetchByUserId_First(userId,
				orderByComparator);

		if (portfolioPermission != null) {
			return portfolioPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPermissionException(msg.toString());
	}

	/**
	 * Returns the first portfolio permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortfolioPermission> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioPermission findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = fetchByUserId_Last(userId,
				orderByComparator);

		if (portfolioPermission != null) {
			return portfolioPermission;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchPermissionException(msg.toString());
	}

	/**
	 * Returns the last portfolio permission in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio permission, or <code>null</code> if a matching portfolio permission could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<PortfolioPermission> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioPermission[] findByUserId_PrevAndNext(
		PortfolioPermissionPK portfolioPermissionPK, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = findByPrimaryKey(portfolioPermissionPK);

		Session session = null;

		try {
			session = openSession();

			PortfolioPermission[] array = new PortfolioPermissionImpl[3];

			array[0] = getByUserId_PrevAndNext(session, portfolioPermission,
					userId, orderByComparator, true);

			array[1] = portfolioPermission;

			array[2] = getByUserId_PrevAndNext(session, portfolioPermission,
					userId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortfolioPermission getByUserId_PrevAndNext(Session session,
		PortfolioPermission portfolioPermission, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIOPERMISSION_WHERE);

		query.append(_FINDER_COLUMN_USERID_USERID_2);

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
			query.append(PortfolioPermissionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolioPermission);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortfolioPermission> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolio permissions where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUserId(long userId) throws SystemException {
		for (PortfolioPermission portfolioPermission : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolioPermission);
		}
	}

	/**
	 * Returns the number of portfolio permissions where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching portfolio permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByUserId(long userId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_USERID;

		Object[] finderArgs = new Object[] { userId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_PORTFOLIOPERMISSION_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "portfolioPermission.id.userId = ?";

	public PortfolioPermissionPersistenceImpl() {
		setModelClass(PortfolioPermission.class);
	}

	/**
	 * Caches the portfolio permission in the entity cache if it is enabled.
	 *
	 * @param portfolioPermission the portfolio permission
	 */
	@Override
	public void cacheResult(PortfolioPermission portfolioPermission) {
		EntityCacheUtil.putResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionImpl.class, portfolioPermission.getPrimaryKey(),
			portfolioPermission);

		portfolioPermission.resetOriginalValues();
	}

	/**
	 * Caches the portfolio permissions in the entity cache if it is enabled.
	 *
	 * @param portfolioPermissions the portfolio permissions
	 */
	@Override
	public void cacheResult(List<PortfolioPermission> portfolioPermissions) {
		for (PortfolioPermission portfolioPermission : portfolioPermissions) {
			if (EntityCacheUtil.getResult(
						PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioPermissionImpl.class,
						portfolioPermission.getPrimaryKey()) == null) {
				cacheResult(portfolioPermission);
			}
			else {
				portfolioPermission.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portfolio permissions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortfolioPermissionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortfolioPermissionImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portfolio permission.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortfolioPermission portfolioPermission) {
		EntityCacheUtil.removeResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionImpl.class, portfolioPermission.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PortfolioPermission> portfolioPermissions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortfolioPermission portfolioPermission : portfolioPermissions) {
			EntityCacheUtil.removeResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioPermissionImpl.class,
				portfolioPermission.getPrimaryKey());
		}
	}

	/**
	 * Creates a new portfolio permission with the primary key. Does not add the portfolio permission to the database.
	 *
	 * @param portfolioPermissionPK the primary key for the new portfolio permission
	 * @return the new portfolio permission
	 */
	@Override
	public PortfolioPermission create(
		PortfolioPermissionPK portfolioPermissionPK) {
		PortfolioPermission portfolioPermission = new PortfolioPermissionImpl();

		portfolioPermission.setNew(true);
		portfolioPermission.setPrimaryKey(portfolioPermissionPK);

		return portfolioPermission;
	}

	/**
	 * Removes the portfolio permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portfolioPermissionPK the primary key of the portfolio permission
	 * @return the portfolio permission that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission remove(
		PortfolioPermissionPK portfolioPermissionPK)
		throws NoSuchPermissionException, SystemException {
		return remove((Serializable)portfolioPermissionPK);
	}

	/**
	 * Removes the portfolio permission with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portfolio permission
	 * @return the portfolio permission that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission remove(Serializable primaryKey)
		throws NoSuchPermissionException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortfolioPermission portfolioPermission = (PortfolioPermission)session.get(PortfolioPermissionImpl.class,
					primaryKey);

			if (portfolioPermission == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portfolioPermission);
		}
		catch (NoSuchPermissionException nsee) {
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
	protected PortfolioPermission removeImpl(
		PortfolioPermission portfolioPermission) throws SystemException {
		portfolioPermission = toUnwrappedModel(portfolioPermission);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portfolioPermission)) {
				portfolioPermission = (PortfolioPermission)session.get(PortfolioPermissionImpl.class,
						portfolioPermission.getPrimaryKeyObj());
			}

			if (portfolioPermission != null) {
				session.delete(portfolioPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portfolioPermission != null) {
			clearCache(portfolioPermission);
		}

		return portfolioPermission;
	}

	@Override
	public PortfolioPermission updateImpl(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission)
		throws SystemException {
		portfolioPermission = toUnwrappedModel(portfolioPermission);

		boolean isNew = portfolioPermission.isNew();

		PortfolioPermissionModelImpl portfolioPermissionModelImpl = (PortfolioPermissionModelImpl)portfolioPermission;

		Session session = null;

		try {
			session = openSession();

			if (portfolioPermission.isNew()) {
				session.save(portfolioPermission);

				portfolioPermission.setNew(false);
			}
			else {
				session.merge(portfolioPermission);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortfolioPermissionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portfolioPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioPermissionModelImpl.getOriginalPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);

				args = new Object[] { portfolioPermissionModelImpl.getPlid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);
			}

			if ((portfolioPermissionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioPermissionModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { portfolioPermissionModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}
		}

		EntityCacheUtil.putResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioPermissionImpl.class, portfolioPermission.getPrimaryKey(),
			portfolioPermission);

		return portfolioPermission;
	}

	protected PortfolioPermission toUnwrappedModel(
		PortfolioPermission portfolioPermission) {
		if (portfolioPermission instanceof PortfolioPermissionImpl) {
			return portfolioPermission;
		}

		PortfolioPermissionImpl portfolioPermissionImpl = new PortfolioPermissionImpl();

		portfolioPermissionImpl.setNew(portfolioPermission.isNew());
		portfolioPermissionImpl.setPrimaryKey(portfolioPermission.getPrimaryKey());

		portfolioPermissionImpl.setPlid(portfolioPermission.getPlid());
		portfolioPermissionImpl.setUserId(portfolioPermission.getUserId());

		return portfolioPermissionImpl;
	}

	/**
	 * Returns the portfolio permission with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio permission
	 * @return the portfolio permission
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPermissionException, SystemException {
		PortfolioPermission portfolioPermission = fetchByPrimaryKey(primaryKey);

		if (portfolioPermission == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPermissionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portfolioPermission;
	}

	/**
	 * Returns the portfolio permission with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchPermissionException} if it could not be found.
	 *
	 * @param portfolioPermissionPK the primary key of the portfolio permission
	 * @return the portfolio permission
	 * @throws de.unipotsdam.elis.portfolio.NoSuchPermissionException if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission findByPrimaryKey(
		PortfolioPermissionPK portfolioPermissionPK)
		throws NoSuchPermissionException, SystemException {
		return findByPrimaryKey((Serializable)portfolioPermissionPK);
	}

	/**
	 * Returns the portfolio permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio permission
	 * @return the portfolio permission, or <code>null</code> if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PortfolioPermission portfolioPermission = (PortfolioPermission)EntityCacheUtil.getResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioPermissionImpl.class, primaryKey);

		if (portfolioPermission == _nullPortfolioPermission) {
			return null;
		}

		if (portfolioPermission == null) {
			Session session = null;

			try {
				session = openSession();

				portfolioPermission = (PortfolioPermission)session.get(PortfolioPermissionImpl.class,
						primaryKey);

				if (portfolioPermission != null) {
					cacheResult(portfolioPermission);
				}
				else {
					EntityCacheUtil.putResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioPermissionImpl.class, primaryKey,
						_nullPortfolioPermission);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortfolioPermissionModelImpl.ENTITY_CACHE_ENABLED,
					PortfolioPermissionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portfolioPermission;
	}

	/**
	 * Returns the portfolio permission with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portfolioPermissionPK the primary key of the portfolio permission
	 * @return the portfolio permission, or <code>null</code> if a portfolio permission with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioPermission fetchByPrimaryKey(
		PortfolioPermissionPK portfolioPermissionPK) throws SystemException {
		return fetchByPrimaryKey((Serializable)portfolioPermissionPK);
	}

	/**
	 * Returns all the portfolio permissions.
	 *
	 * @return the portfolio permissions
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioPermission> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioPermission> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	@Override
	public List<PortfolioPermission> findAll(int start, int end,
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

		List<PortfolioPermission> list = (List<PortfolioPermission>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTFOLIOPERMISSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTFOLIOPERMISSION;

				if (pagination) {
					sql = sql.concat(PortfolioPermissionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioPermission>(list);
				}
				else {
					list = (List<PortfolioPermission>)QueryUtil.list(q,
							getDialect(), start, end);
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
	 * Removes all the portfolio permissions from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PortfolioPermission portfolioPermission : findAll()) {
			remove(portfolioPermission);
		}
	}

	/**
	 * Returns the number of portfolio permissions.
	 *
	 * @return the number of portfolio permissions
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

				Query q = session.createQuery(_SQL_COUNT_PORTFOLIOPERMISSION);

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
	 * Initializes the portfolio permission persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.portfolio.model.PortfolioPermission")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortfolioPermission>> listenersList = new ArrayList<ModelListener<PortfolioPermission>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortfolioPermission>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PortfolioPermissionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTFOLIOPERMISSION = "SELECT portfolioPermission FROM PortfolioPermission portfolioPermission";
	private static final String _SQL_SELECT_PORTFOLIOPERMISSION_WHERE = "SELECT portfolioPermission FROM PortfolioPermission portfolioPermission WHERE ";
	private static final String _SQL_COUNT_PORTFOLIOPERMISSION = "SELECT COUNT(portfolioPermission) FROM PortfolioPermission portfolioPermission";
	private static final String _SQL_COUNT_PORTFOLIOPERMISSION_WHERE = "SELECT COUNT(portfolioPermission) FROM PortfolioPermission portfolioPermission WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portfolioPermission.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortfolioPermission exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortfolioPermission exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PortfolioPermissionPersistenceImpl.class);
	private static PortfolioPermission _nullPortfolioPermission = new PortfolioPermissionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortfolioPermission> toCacheModel() {
				return _nullPortfolioPermissionCacheModel;
			}
		};

	private static CacheModel<PortfolioPermission> _nullPortfolioPermissionCacheModel =
		new CacheModel<PortfolioPermission>() {
			@Override
			public PortfolioPermission toEntityModel() {
				return _nullPortfolioPermission;
			}
		};
}