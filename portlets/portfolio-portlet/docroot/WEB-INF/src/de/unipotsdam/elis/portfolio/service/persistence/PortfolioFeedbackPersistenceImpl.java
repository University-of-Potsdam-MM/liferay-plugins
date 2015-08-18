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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.unipotsdam.elis.portfolio.NoSuchFeedbackException;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackImpl;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the portfolio feedback service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see PortfolioFeedbackPersistence
 * @see PortfolioFeedbackUtil
 * @generated
 */
public class PortfolioFeedbackPersistenceImpl extends BasePersistenceImpl<PortfolioFeedback>
	implements PortfolioFeedbackPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link PortfolioFeedbackUtil} to access the portfolio feedback persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = PortfolioFeedbackImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] { Long.class.getName() },
			PortfolioFeedbackModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portfolio feedbacks where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching portfolio feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioFeedback> findByPlid(long plid)
		throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioFeedback> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
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
	@Override
	public List<PortfolioFeedback> findByPlid(long plid, int start, int end,
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

		List<PortfolioFeedback> list = (List<PortfolioFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortfolioFeedback portfolioFeedback : list) {
				if ((plid != portfolioFeedback.getPlid())) {
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

			query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (!pagination) {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioFeedback>(list);
				}
				else {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
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
	 * Returns the first portfolio feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio feedback
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByPlid_First(plid,
				orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
	}

	/**
	 * Returns the first portfolio feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByPlid_First(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortfolioFeedback> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByPlid_Last(plid,
				orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
	}

	/**
	 * Returns the last portfolio feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByPlid_Last(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<PortfolioFeedback> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback[] findByPlid_PrevAndNext(
		PortfolioFeedbackPK portfolioFeedbackPK, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = findByPrimaryKey(portfolioFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			PortfolioFeedback[] array = new PortfolioFeedbackImpl[3];

			array[0] = getByPlid_PrevAndNext(session, portfolioFeedback, plid,
					orderByComparator, true);

			array[1] = portfolioFeedback;

			array[2] = getByPlid_PrevAndNext(session, portfolioFeedback, plid,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortfolioFeedback getByPlid_PrevAndNext(Session session,
		PortfolioFeedback portfolioFeedback, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

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
			query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolioFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortfolioFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolio feedbacks where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPlid(long plid) throws SystemException {
		for (PortfolioFeedback portfolioFeedback : findByPlid(plid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolioFeedback);
		}
	}

	/**
	 * Returns the number of portfolio feedbacks where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching portfolio feedbacks
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

			query.append(_SQL_COUNT_PORTFOLIOFEEDBACK_WHERE);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 = "portfolioFeedback.id.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			PortfolioFeedbackModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the portfolio feedbacks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching portfolio feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioFeedback> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioFeedback> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
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
	@Override
	public List<PortfolioFeedback> findByUserId(long userId, int start,
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

		List<PortfolioFeedback> list = (List<PortfolioFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortfolioFeedback portfolioFeedback : list) {
				if ((userId != portfolioFeedback.getUserId())) {
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

			query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioFeedback>(list);
				}
				else {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
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
	 * Returns the first portfolio feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio feedback
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByUserId_First(userId,
				orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
	}

	/**
	 * Returns the first portfolio feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<PortfolioFeedback> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByUserId_Last(userId,
				orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
	}

	/**
	 * Returns the last portfolio feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching portfolio feedback, or <code>null</code> if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<PortfolioFeedback> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback[] findByUserId_PrevAndNext(
		PortfolioFeedbackPK portfolioFeedbackPK, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = findByPrimaryKey(portfolioFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			PortfolioFeedback[] array = new PortfolioFeedbackImpl[3];

			array[0] = getByUserId_PrevAndNext(session, portfolioFeedback,
					userId, orderByComparator, true);

			array[1] = portfolioFeedback;

			array[2] = getByUserId_PrevAndNext(session, portfolioFeedback,
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

	protected PortfolioFeedback getByUserId_PrevAndNext(Session session,
		PortfolioFeedback portfolioFeedback, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

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
			query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolioFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortfolioFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolio feedbacks where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUserId(long userId) throws SystemException {
		for (PortfolioFeedback portfolioFeedback : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolioFeedback);
		}
	}

	/**
	 * Returns the number of portfolio feedbacks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching portfolio feedbacks
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

			query.append(_SQL_COUNT_PORTFOLIOFEEDBACK_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "portfolioFeedback.id.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS =
		new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlidAndFeedbackStatus",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS =
		new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED,
			PortfolioFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPlidAndFeedbackStatus",
			new String[] { Long.class.getName(), Integer.class.getName() },
			PortfolioFeedbackModelImpl.PLID_COLUMN_BITMASK |
			PortfolioFeedbackModelImpl.FEEDBACKSTATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS = new FinderPath(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPlidAndFeedbackStatus",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @return the matching portfolio feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioFeedback> findByPlidAndFeedbackStatus(long plid,
		int feedbackStatus) throws SystemException {
		return findByPlidAndFeedbackStatus(plid, feedbackStatus,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioFeedback> findByPlidAndFeedbackStatus(long plid,
		int feedbackStatus, int start, int end) throws SystemException {
		return findByPlidAndFeedbackStatus(plid, feedbackStatus, start, end,
			null);
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
	@Override
	public List<PortfolioFeedback> findByPlidAndFeedbackStatus(long plid,
		int feedbackStatus, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS;
			finderArgs = new Object[] { plid, feedbackStatus };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS;
			finderArgs = new Object[] {
					plid, feedbackStatus,
					
					start, end, orderByComparator
				};
		}

		List<PortfolioFeedback> list = (List<PortfolioFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortfolioFeedback portfolioFeedback : list) {
				if ((plid != portfolioFeedback.getPlid()) ||
						(feedbackStatus != portfolioFeedback.getFeedbackStatus())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(feedbackStatus);

				if (!pagination) {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioFeedback>(list);
				}
				else {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
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
	 * Returns the first portfolio feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching portfolio feedback
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a matching portfolio feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback findByPlidAndFeedbackStatus_First(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByPlidAndFeedbackStatus_First(plid,
				feedbackStatus, orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", feedbackStatus=");
		msg.append(feedbackStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
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
	@Override
	public PortfolioFeedback fetchByPlidAndFeedbackStatus_First(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws SystemException {
		List<PortfolioFeedback> list = findByPlidAndFeedbackStatus(plid,
				feedbackStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback findByPlidAndFeedbackStatus_Last(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByPlidAndFeedbackStatus_Last(plid,
				feedbackStatus, orderByComparator);

		if (portfolioFeedback != null) {
			return portfolioFeedback;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", feedbackStatus=");
		msg.append(feedbackStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchFeedbackException(msg.toString());
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
	@Override
	public PortfolioFeedback fetchByPlidAndFeedbackStatus_Last(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByPlidAndFeedbackStatus(plid, feedbackStatus);

		if (count == 0) {
			return null;
		}

		List<PortfolioFeedback> list = findByPlidAndFeedbackStatus(plid,
				feedbackStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public PortfolioFeedback[] findByPlidAndFeedbackStatus_PrevAndNext(
		PortfolioFeedbackPK portfolioFeedbackPK, long plid, int feedbackStatus,
		OrderByComparator orderByComparator)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = findByPrimaryKey(portfolioFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			PortfolioFeedback[] array = new PortfolioFeedbackImpl[3];

			array[0] = getByPlidAndFeedbackStatus_PrevAndNext(session,
					portfolioFeedback, plid, feedbackStatus, orderByComparator,
					true);

			array[1] = portfolioFeedback;

			array[2] = getByPlidAndFeedbackStatus_PrevAndNext(session,
					portfolioFeedback, plid, feedbackStatus, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected PortfolioFeedback getByPlidAndFeedbackStatus_PrevAndNext(
		Session session, PortfolioFeedback portfolioFeedback, long plid,
		int feedbackStatus, OrderByComparator orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_PORTFOLIOFEEDBACK_WHERE);

		query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2);

		query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2);

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
			query.append(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		qPos.add(feedbackStatus);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(portfolioFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<PortfolioFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the portfolio feedbacks where plid = &#63; and feedbackStatus = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws SystemException {
		for (PortfolioFeedback portfolioFeedback : findByPlidAndFeedbackStatus(
				plid, feedbackStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(portfolioFeedback);
		}
	}

	/**
	 * Returns the number of portfolio feedbacks where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @return the number of matching portfolio feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS;

		Object[] finderArgs = new Object[] { plid, feedbackStatus };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_PORTFOLIOFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(feedbackStatus);

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

	private static final String _FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2 = "portfolioFeedback.id.plid = ? AND ";
	private static final String _FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2 =
		"portfolioFeedback.feedbackStatus = ?";

	public PortfolioFeedbackPersistenceImpl() {
		setModelClass(PortfolioFeedback.class);
	}

	/**
	 * Caches the portfolio feedback in the entity cache if it is enabled.
	 *
	 * @param portfolioFeedback the portfolio feedback
	 */
	@Override
	public void cacheResult(PortfolioFeedback portfolioFeedback) {
		EntityCacheUtil.putResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackImpl.class, portfolioFeedback.getPrimaryKey(),
			portfolioFeedback);

		portfolioFeedback.resetOriginalValues();
	}

	/**
	 * Caches the portfolio feedbacks in the entity cache if it is enabled.
	 *
	 * @param portfolioFeedbacks the portfolio feedbacks
	 */
	@Override
	public void cacheResult(List<PortfolioFeedback> portfolioFeedbacks) {
		for (PortfolioFeedback portfolioFeedback : portfolioFeedbacks) {
			if (EntityCacheUtil.getResult(
						PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioFeedbackImpl.class,
						portfolioFeedback.getPrimaryKey()) == null) {
				cacheResult(portfolioFeedback);
			}
			else {
				portfolioFeedback.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all portfolio feedbacks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(PortfolioFeedbackImpl.class.getName());
		}

		EntityCacheUtil.clearCache(PortfolioFeedbackImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the portfolio feedback.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(PortfolioFeedback portfolioFeedback) {
		EntityCacheUtil.removeResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackImpl.class, portfolioFeedback.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<PortfolioFeedback> portfolioFeedbacks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (PortfolioFeedback portfolioFeedback : portfolioFeedbacks) {
			EntityCacheUtil.removeResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioFeedbackImpl.class, portfolioFeedback.getPrimaryKey());
		}
	}

	/**
	 * Creates a new portfolio feedback with the primary key. Does not add the portfolio feedback to the database.
	 *
	 * @param portfolioFeedbackPK the primary key for the new portfolio feedback
	 * @return the new portfolio feedback
	 */
	@Override
	public PortfolioFeedback create(PortfolioFeedbackPK portfolioFeedbackPK) {
		PortfolioFeedback portfolioFeedback = new PortfolioFeedbackImpl();

		portfolioFeedback.setNew(true);
		portfolioFeedback.setPrimaryKey(portfolioFeedbackPK);

		return portfolioFeedback;
	}

	/**
	 * Removes the portfolio feedback with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param portfolioFeedbackPK the primary key of the portfolio feedback
	 * @return the portfolio feedback that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback remove(PortfolioFeedbackPK portfolioFeedbackPK)
		throws NoSuchFeedbackException, SystemException {
		return remove((Serializable)portfolioFeedbackPK);
	}

	/**
	 * Removes the portfolio feedback with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the portfolio feedback
	 * @return the portfolio feedback that was removed
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback remove(Serializable primaryKey)
		throws NoSuchFeedbackException, SystemException {
		Session session = null;

		try {
			session = openSession();

			PortfolioFeedback portfolioFeedback = (PortfolioFeedback)session.get(PortfolioFeedbackImpl.class,
					primaryKey);

			if (portfolioFeedback == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFeedbackException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(portfolioFeedback);
		}
		catch (NoSuchFeedbackException nsee) {
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
	protected PortfolioFeedback removeImpl(PortfolioFeedback portfolioFeedback)
		throws SystemException {
		portfolioFeedback = toUnwrappedModel(portfolioFeedback);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(portfolioFeedback)) {
				portfolioFeedback = (PortfolioFeedback)session.get(PortfolioFeedbackImpl.class,
						portfolioFeedback.getPrimaryKeyObj());
			}

			if (portfolioFeedback != null) {
				session.delete(portfolioFeedback);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (portfolioFeedback != null) {
			clearCache(portfolioFeedback);
		}

		return portfolioFeedback;
	}

	@Override
	public PortfolioFeedback updateImpl(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback)
		throws SystemException {
		portfolioFeedback = toUnwrappedModel(portfolioFeedback);

		boolean isNew = portfolioFeedback.isNew();

		PortfolioFeedbackModelImpl portfolioFeedbackModelImpl = (PortfolioFeedbackModelImpl)portfolioFeedback;

		Session session = null;

		try {
			session = openSession();

			if (portfolioFeedback.isNew()) {
				session.save(portfolioFeedback);

				portfolioFeedback.setNew(false);
			}
			else {
				session.merge(portfolioFeedback);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !PortfolioFeedbackModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((portfolioFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioFeedbackModelImpl.getOriginalPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);

				args = new Object[] { portfolioFeedbackModelImpl.getPlid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);
			}

			if ((portfolioFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioFeedbackModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { portfolioFeedbackModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((portfolioFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						portfolioFeedbackModelImpl.getOriginalPlid(),
						portfolioFeedbackModelImpl.getOriginalFeedbackStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS,
					args);

				args = new Object[] {
						portfolioFeedbackModelImpl.getPlid(),
						portfolioFeedbackModelImpl.getFeedbackStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS,
					args);
			}
		}

		EntityCacheUtil.putResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			PortfolioFeedbackImpl.class, portfolioFeedback.getPrimaryKey(),
			portfolioFeedback);

		return portfolioFeedback;
	}

	protected PortfolioFeedback toUnwrappedModel(
		PortfolioFeedback portfolioFeedback) {
		if (portfolioFeedback instanceof PortfolioFeedbackImpl) {
			return portfolioFeedback;
		}

		PortfolioFeedbackImpl portfolioFeedbackImpl = new PortfolioFeedbackImpl();

		portfolioFeedbackImpl.setNew(portfolioFeedback.isNew());
		portfolioFeedbackImpl.setPrimaryKey(portfolioFeedback.getPrimaryKey());

		portfolioFeedbackImpl.setPlid(portfolioFeedback.getPlid());
		portfolioFeedbackImpl.setUserId(portfolioFeedback.getUserId());
		portfolioFeedbackImpl.setFeedbackStatus(portfolioFeedback.getFeedbackStatus());
		portfolioFeedbackImpl.setHidden(portfolioFeedback.isHidden());
		portfolioFeedbackImpl.setCreateDate(portfolioFeedback.getCreateDate());
		portfolioFeedbackImpl.setModifiedDate(portfolioFeedback.getModifiedDate());

		return portfolioFeedbackImpl;
	}

	/**
	 * Returns the portfolio feedback with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio feedback
	 * @return the portfolio feedback
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFeedbackException, SystemException {
		PortfolioFeedback portfolioFeedback = fetchByPrimaryKey(primaryKey);

		if (portfolioFeedback == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFeedbackException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return portfolioFeedback;
	}

	/**
	 * Returns the portfolio feedback with the primary key or throws a {@link de.unipotsdam.elis.portfolio.NoSuchFeedbackException} if it could not be found.
	 *
	 * @param portfolioFeedbackPK the primary key of the portfolio feedback
	 * @return the portfolio feedback
	 * @throws de.unipotsdam.elis.portfolio.NoSuchFeedbackException if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback findByPrimaryKey(
		PortfolioFeedbackPK portfolioFeedbackPK)
		throws NoSuchFeedbackException, SystemException {
		return findByPrimaryKey((Serializable)portfolioFeedbackPK);
	}

	/**
	 * Returns the portfolio feedback with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the portfolio feedback
	 * @return the portfolio feedback, or <code>null</code> if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		PortfolioFeedback portfolioFeedback = (PortfolioFeedback)EntityCacheUtil.getResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
				PortfolioFeedbackImpl.class, primaryKey);

		if (portfolioFeedback == _nullPortfolioFeedback) {
			return null;
		}

		if (portfolioFeedback == null) {
			Session session = null;

			try {
				session = openSession();

				portfolioFeedback = (PortfolioFeedback)session.get(PortfolioFeedbackImpl.class,
						primaryKey);

				if (portfolioFeedback != null) {
					cacheResult(portfolioFeedback);
				}
				else {
					EntityCacheUtil.putResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
						PortfolioFeedbackImpl.class, primaryKey,
						_nullPortfolioFeedback);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(PortfolioFeedbackModelImpl.ENTITY_CACHE_ENABLED,
					PortfolioFeedbackImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return portfolioFeedback;
	}

	/**
	 * Returns the portfolio feedback with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param portfolioFeedbackPK the primary key of the portfolio feedback
	 * @return the portfolio feedback, or <code>null</code> if a portfolio feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public PortfolioFeedback fetchByPrimaryKey(
		PortfolioFeedbackPK portfolioFeedbackPK) throws SystemException {
		return fetchByPrimaryKey((Serializable)portfolioFeedbackPK);
	}

	/**
	 * Returns all the portfolio feedbacks.
	 *
	 * @return the portfolio feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<PortfolioFeedback> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<PortfolioFeedback> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	@Override
	public List<PortfolioFeedback> findAll(int start, int end,
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

		List<PortfolioFeedback> list = (List<PortfolioFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_PORTFOLIOFEEDBACK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_PORTFOLIOFEEDBACK;

				if (pagination) {
					sql = sql.concat(PortfolioFeedbackModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<PortfolioFeedback>(list);
				}
				else {
					list = (List<PortfolioFeedback>)QueryUtil.list(q,
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
	 * Removes all the portfolio feedbacks from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (PortfolioFeedback portfolioFeedback : findAll()) {
			remove(portfolioFeedback);
		}
	}

	/**
	 * Returns the number of portfolio feedbacks.
	 *
	 * @return the number of portfolio feedbacks
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

				Query q = session.createQuery(_SQL_COUNT_PORTFOLIOFEEDBACK);

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

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the portfolio feedback persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.portfolio.model.PortfolioFeedback")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<PortfolioFeedback>> listenersList = new ArrayList<ModelListener<PortfolioFeedback>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<PortfolioFeedback>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(PortfolioFeedbackImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_PORTFOLIOFEEDBACK = "SELECT portfolioFeedback FROM PortfolioFeedback portfolioFeedback";
	private static final String _SQL_SELECT_PORTFOLIOFEEDBACK_WHERE = "SELECT portfolioFeedback FROM PortfolioFeedback portfolioFeedback WHERE ";
	private static final String _SQL_COUNT_PORTFOLIOFEEDBACK = "SELECT COUNT(portfolioFeedback) FROM PortfolioFeedback portfolioFeedback";
	private static final String _SQL_COUNT_PORTFOLIOFEEDBACK_WHERE = "SELECT COUNT(portfolioFeedback) FROM PortfolioFeedback portfolioFeedback WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "portfolioFeedback.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No PortfolioFeedback exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No PortfolioFeedback exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(PortfolioFeedbackPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"hidden"
			});
	private static PortfolioFeedback _nullPortfolioFeedback = new PortfolioFeedbackImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<PortfolioFeedback> toCacheModel() {
				return _nullPortfolioFeedbackCacheModel;
			}
		};

	private static CacheModel<PortfolioFeedback> _nullPortfolioFeedbackCacheModel =
		new CacheModel<PortfolioFeedback>() {
			@Override
			public PortfolioFeedback toEntityModel() {
				return _nullPortfolioFeedback;
			}
		};
}