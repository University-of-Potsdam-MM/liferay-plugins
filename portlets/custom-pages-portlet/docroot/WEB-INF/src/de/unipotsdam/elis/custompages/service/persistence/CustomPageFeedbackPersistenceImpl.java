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

import de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackImpl;
import de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the custom page feedback service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see CustomPageFeedbackPersistence
 * @see CustomPageFeedbackUtil
 * @generated
 */
public class CustomPageFeedbackPersistenceImpl extends BasePersistenceImpl<CustomPageFeedback>
	implements CustomPageFeedbackPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CustomPageFeedbackUtil} to access the custom page feedback persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CustomPageFeedbackImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLID = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] { Long.class.getName() },
			CustomPageFeedbackModelImpl.PLID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLID = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the custom page feedbacks where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching custom page feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CustomPageFeedback> findByPlid(long plid)
		throws SystemException {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CustomPageFeedback> findByPlid(long plid, int start, int end)
		throws SystemException {
		return findByPlid(plid, start, end, null);
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
	@Override
	public List<CustomPageFeedback> findByPlid(long plid, int start, int end,
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

		List<CustomPageFeedback> list = (List<CustomPageFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CustomPageFeedback customPageFeedback : list) {
				if ((plid != customPageFeedback.getPlid())) {
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

			query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (!pagination) {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CustomPageFeedback>(list);
				}
				else {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
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
	 * Returns the first custom page feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom page feedback
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback findByPlid_First(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByPlid_First(plid,
				orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
	}

	/**
	 * Returns the first custom page feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByPlid_First(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		List<CustomPageFeedback> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback findByPlid_Last(long plid,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByPlid_Last(plid,
				orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
	}

	/**
	 * Returns the last custom page feedback in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByPlid_Last(long plid,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<CustomPageFeedback> list = findByPlid(plid, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback[] findByPlid_PrevAndNext(
		CustomPageFeedbackPK customPageFeedbackPK, long plid,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = findByPrimaryKey(customPageFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			CustomPageFeedback[] array = new CustomPageFeedbackImpl[3];

			array[0] = getByPlid_PrevAndNext(session, customPageFeedback, plid,
					orderByComparator, true);

			array[1] = customPageFeedback;

			array[2] = getByPlid_PrevAndNext(session, customPageFeedback, plid,
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

	protected CustomPageFeedback getByPlid_PrevAndNext(Session session,
		CustomPageFeedback customPageFeedback, long plid,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

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
			query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(customPageFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CustomPageFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom page feedbacks where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPlid(long plid) throws SystemException {
		for (CustomPageFeedback customPageFeedback : findByPlid(plid,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(customPageFeedback);
		}
	}

	/**
	 * Returns the number of custom page feedbacks where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching custom page feedbacks
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

			query.append(_SQL_COUNT_CUSTOMPAGEFEEDBACK_WHERE);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 = "customPageFeedback.id.plid = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_USERID = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID =
		new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUserId",
			new String[] { Long.class.getName() },
			CustomPageFeedbackModelImpl.USERID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_USERID = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the custom page feedbacks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the matching custom page feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CustomPageFeedback> findByUserId(long userId)
		throws SystemException {
		return findByUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CustomPageFeedback> findByUserId(long userId, int start, int end)
		throws SystemException {
		return findByUserId(userId, start, end, null);
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
	@Override
	public List<CustomPageFeedback> findByUserId(long userId, int start,
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

		List<CustomPageFeedback> list = (List<CustomPageFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CustomPageFeedback customPageFeedback : list) {
				if ((userId != customPageFeedback.getUserId())) {
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

			query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_USERID_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(userId);

				if (!pagination) {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CustomPageFeedback>(list);
				}
				else {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
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
	 * Returns the first custom page feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom page feedback
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback findByUserId_First(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByUserId_First(userId,
				orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
	}

	/**
	 * Returns the first custom page feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByUserId_First(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		List<CustomPageFeedback> list = findByUserId(userId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback findByUserId_Last(long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByUserId_Last(userId,
				orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("userId=");
		msg.append(userId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
	}

	/**
	 * Returns the last custom page feedback in the ordered set where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching custom page feedback, or <code>null</code> if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByUserId_Last(long userId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByUserId(userId);

		if (count == 0) {
			return null;
		}

		List<CustomPageFeedback> list = findByUserId(userId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback[] findByUserId_PrevAndNext(
		CustomPageFeedbackPK customPageFeedbackPK, long userId,
		OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = findByPrimaryKey(customPageFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			CustomPageFeedback[] array = new CustomPageFeedbackImpl[3];

			array[0] = getByUserId_PrevAndNext(session, customPageFeedback,
					userId, orderByComparator, true);

			array[1] = customPageFeedback;

			array[2] = getByUserId_PrevAndNext(session, customPageFeedback,
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

	protected CustomPageFeedback getByUserId_PrevAndNext(Session session,
		CustomPageFeedback customPageFeedback, long userId,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

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
			query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(userId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(customPageFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CustomPageFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom page feedbacks where userId = &#63; from the database.
	 *
	 * @param userId the user ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByUserId(long userId) throws SystemException {
		for (CustomPageFeedback customPageFeedback : findByUserId(userId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(customPageFeedback);
		}
	}

	/**
	 * Returns the number of custom page feedbacks where userId = &#63;.
	 *
	 * @param userId the user ID
	 * @return the number of matching custom page feedbacks
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

			query.append(_SQL_COUNT_CUSTOMPAGEFEEDBACK_WHERE);

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

	private static final String _FINDER_COLUMN_USERID_USERID_2 = "customPageFeedback.id.userId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS =
		new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlidAndFeedbackStatus",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS =
		new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED,
			CustomPageFeedbackImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPlidAndFeedbackStatus",
			new String[] { Long.class.getName(), Integer.class.getName() },
			CustomPageFeedbackModelImpl.PLID_COLUMN_BITMASK |
			CustomPageFeedbackModelImpl.FEEDBACKSTATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS = new FinderPath(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByPlidAndFeedbackStatus",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @return the matching custom page feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CustomPageFeedback> findByPlidAndFeedbackStatus(long plid,
		int feedbackStatus) throws SystemException {
		return findByPlidAndFeedbackStatus(plid, feedbackStatus,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CustomPageFeedback> findByPlidAndFeedbackStatus(long plid,
		int feedbackStatus, int start, int end) throws SystemException {
		return findByPlidAndFeedbackStatus(plid, feedbackStatus, start, end,
			null);
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
	@Override
	public List<CustomPageFeedback> findByPlidAndFeedbackStatus(long plid,
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

		List<CustomPageFeedback> list = (List<CustomPageFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (CustomPageFeedback customPageFeedback : list) {
				if ((plid != customPageFeedback.getPlid()) ||
						(feedbackStatus != customPageFeedback.getFeedbackStatus())) {
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

			query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2);

			query.append(_FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
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
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CustomPageFeedback>(list);
				}
				else {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
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
	 * Returns the first custom page feedback in the ordered set where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching custom page feedback
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a matching custom page feedback could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback findByPlidAndFeedbackStatus_First(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByPlidAndFeedbackStatus_First(plid,
				feedbackStatus, orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", feedbackStatus=");
		msg.append(feedbackStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
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
	@Override
	public CustomPageFeedback fetchByPlidAndFeedbackStatus_First(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws SystemException {
		List<CustomPageFeedback> list = findByPlidAndFeedbackStatus(plid,
				feedbackStatus, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback findByPlidAndFeedbackStatus_Last(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByPlidAndFeedbackStatus_Last(plid,
				feedbackStatus, orderByComparator);

		if (customPageFeedback != null) {
			return customPageFeedback;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", feedbackStatus=");
		msg.append(feedbackStatus);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchCustomPageFeedbackException(msg.toString());
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
	@Override
	public CustomPageFeedback fetchByPlidAndFeedbackStatus_Last(long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws SystemException {
		int count = countByPlidAndFeedbackStatus(plid, feedbackStatus);

		if (count == 0) {
			return null;
		}

		List<CustomPageFeedback> list = findByPlidAndFeedbackStatus(plid,
				feedbackStatus, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public CustomPageFeedback[] findByPlidAndFeedbackStatus_PrevAndNext(
		CustomPageFeedbackPK customPageFeedbackPK, long plid,
		int feedbackStatus, OrderByComparator orderByComparator)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = findByPrimaryKey(customPageFeedbackPK);

		Session session = null;

		try {
			session = openSession();

			CustomPageFeedback[] array = new CustomPageFeedbackImpl[3];

			array[0] = getByPlidAndFeedbackStatus_PrevAndNext(session,
					customPageFeedback, plid, feedbackStatus,
					orderByComparator, true);

			array[1] = customPageFeedback;

			array[2] = getByPlidAndFeedbackStatus_PrevAndNext(session,
					customPageFeedback, plid, feedbackStatus,
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

	protected CustomPageFeedback getByPlidAndFeedbackStatus_PrevAndNext(
		Session session, CustomPageFeedback customPageFeedback, long plid,
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

		query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE);

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
			query.append(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		qPos.add(feedbackStatus);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(customPageFeedback);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CustomPageFeedback> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the custom page feedbacks where plid = &#63; and feedbackStatus = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByPlidAndFeedbackStatus(long plid, int feedbackStatus)
		throws SystemException {
		for (CustomPageFeedback customPageFeedback : findByPlidAndFeedbackStatus(
				plid, feedbackStatus, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(customPageFeedback);
		}
	}

	/**
	 * Returns the number of custom page feedbacks where plid = &#63; and feedbackStatus = &#63;.
	 *
	 * @param plid the plid
	 * @param feedbackStatus the feedback status
	 * @return the number of matching custom page feedbacks
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

			query.append(_SQL_COUNT_CUSTOMPAGEFEEDBACK_WHERE);

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

	private static final String _FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_PLID_2 = "customPageFeedback.id.plid = ? AND ";
	private static final String _FINDER_COLUMN_PLIDANDFEEDBACKSTATUS_FEEDBACKSTATUS_2 =
		"customPageFeedback.feedbackStatus = ?";

	public CustomPageFeedbackPersistenceImpl() {
		setModelClass(CustomPageFeedback.class);
	}

	/**
	 * Caches the custom page feedback in the entity cache if it is enabled.
	 *
	 * @param customPageFeedback the custom page feedback
	 */
	@Override
	public void cacheResult(CustomPageFeedback customPageFeedback) {
		EntityCacheUtil.putResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackImpl.class, customPageFeedback.getPrimaryKey(),
			customPageFeedback);

		customPageFeedback.resetOriginalValues();
	}

	/**
	 * Caches the custom page feedbacks in the entity cache if it is enabled.
	 *
	 * @param customPageFeedbacks the custom page feedbacks
	 */
	@Override
	public void cacheResult(List<CustomPageFeedback> customPageFeedbacks) {
		for (CustomPageFeedback customPageFeedback : customPageFeedbacks) {
			if (EntityCacheUtil.getResult(
						CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
						CustomPageFeedbackImpl.class,
						customPageFeedback.getPrimaryKey()) == null) {
				cacheResult(customPageFeedback);
			}
			else {
				customPageFeedback.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all custom page feedbacks.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(CustomPageFeedbackImpl.class.getName());
		}

		EntityCacheUtil.clearCache(CustomPageFeedbackImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the custom page feedback.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CustomPageFeedback customPageFeedback) {
		EntityCacheUtil.removeResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackImpl.class, customPageFeedback.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CustomPageFeedback> customPageFeedbacks) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CustomPageFeedback customPageFeedback : customPageFeedbacks) {
			EntityCacheUtil.removeResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
				CustomPageFeedbackImpl.class, customPageFeedback.getPrimaryKey());
		}
	}

	/**
	 * Creates a new custom page feedback with the primary key. Does not add the custom page feedback to the database.
	 *
	 * @param customPageFeedbackPK the primary key for the new custom page feedback
	 * @return the new custom page feedback
	 */
	@Override
	public CustomPageFeedback create(CustomPageFeedbackPK customPageFeedbackPK) {
		CustomPageFeedback customPageFeedback = new CustomPageFeedbackImpl();

		customPageFeedback.setNew(true);
		customPageFeedback.setPrimaryKey(customPageFeedbackPK);

		return customPageFeedback;
	}

	/**
	 * Removes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param customPageFeedbackPK the primary key of the custom page feedback
	 * @return the custom page feedback that was removed
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback remove(CustomPageFeedbackPK customPageFeedbackPK)
		throws NoSuchCustomPageFeedbackException, SystemException {
		return remove((Serializable)customPageFeedbackPK);
	}

	/**
	 * Removes the custom page feedback with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the custom page feedback
	 * @return the custom page feedback that was removed
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback remove(Serializable primaryKey)
		throws NoSuchCustomPageFeedbackException, SystemException {
		Session session = null;

		try {
			session = openSession();

			CustomPageFeedback customPageFeedback = (CustomPageFeedback)session.get(CustomPageFeedbackImpl.class,
					primaryKey);

			if (customPageFeedback == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCustomPageFeedbackException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(customPageFeedback);
		}
		catch (NoSuchCustomPageFeedbackException nsee) {
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
	protected CustomPageFeedback removeImpl(
		CustomPageFeedback customPageFeedback) throws SystemException {
		customPageFeedback = toUnwrappedModel(customPageFeedback);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(customPageFeedback)) {
				customPageFeedback = (CustomPageFeedback)session.get(CustomPageFeedbackImpl.class,
						customPageFeedback.getPrimaryKeyObj());
			}

			if (customPageFeedback != null) {
				session.delete(customPageFeedback);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (customPageFeedback != null) {
			clearCache(customPageFeedback);
		}

		return customPageFeedback;
	}

	@Override
	public CustomPageFeedback updateImpl(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback)
		throws SystemException {
		customPageFeedback = toUnwrappedModel(customPageFeedback);

		boolean isNew = customPageFeedback.isNew();

		CustomPageFeedbackModelImpl customPageFeedbackModelImpl = (CustomPageFeedbackModelImpl)customPageFeedback;

		Session session = null;

		try {
			session = openSession();

			if (customPageFeedback.isNew()) {
				session.save(customPageFeedback);

				customPageFeedback.setNew(false);
			}
			else {
				session.merge(customPageFeedback);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !CustomPageFeedbackModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((customPageFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						customPageFeedbackModelImpl.getOriginalPlid()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);

				args = new Object[] { customPageFeedbackModelImpl.getPlid() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLID,
					args);
			}

			if ((customPageFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						customPageFeedbackModelImpl.getOriginalUserId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);

				args = new Object[] { customPageFeedbackModelImpl.getUserId() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_USERID, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_USERID,
					args);
			}

			if ((customPageFeedbackModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						customPageFeedbackModelImpl.getOriginalPlid(),
						customPageFeedbackModelImpl.getOriginalFeedbackStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS,
					args);

				args = new Object[] {
						customPageFeedbackModelImpl.getPlid(),
						customPageFeedbackModelImpl.getFeedbackStatus()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_PLIDANDFEEDBACKSTATUS,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_PLIDANDFEEDBACKSTATUS,
					args);
			}
		}

		EntityCacheUtil.putResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
			CustomPageFeedbackImpl.class, customPageFeedback.getPrimaryKey(),
			customPageFeedback);

		return customPageFeedback;
	}

	protected CustomPageFeedback toUnwrappedModel(
		CustomPageFeedback customPageFeedback) {
		if (customPageFeedback instanceof CustomPageFeedbackImpl) {
			return customPageFeedback;
		}

		CustomPageFeedbackImpl customPageFeedbackImpl = new CustomPageFeedbackImpl();

		customPageFeedbackImpl.setNew(customPageFeedback.isNew());
		customPageFeedbackImpl.setPrimaryKey(customPageFeedback.getPrimaryKey());

		customPageFeedbackImpl.setPlid(customPageFeedback.getPlid());
		customPageFeedbackImpl.setUserId(customPageFeedback.getUserId());
		customPageFeedbackImpl.setFeedbackStatus(customPageFeedback.getFeedbackStatus());
		customPageFeedbackImpl.setHidden(customPageFeedback.isHidden());
		customPageFeedbackImpl.setCreateDate(customPageFeedback.getCreateDate());
		customPageFeedbackImpl.setModifiedDate(customPageFeedback.getModifiedDate());

		return customPageFeedbackImpl;
	}

	/**
	 * Returns the custom page feedback with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the custom page feedback
	 * @return the custom page feedback
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = fetchByPrimaryKey(primaryKey);

		if (customPageFeedback == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCustomPageFeedbackException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return customPageFeedback;
	}

	/**
	 * Returns the custom page feedback with the primary key or throws a {@link de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException} if it could not be found.
	 *
	 * @param customPageFeedbackPK the primary key of the custom page feedback
	 * @return the custom page feedback
	 * @throws de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback findByPrimaryKey(
		CustomPageFeedbackPK customPageFeedbackPK)
		throws NoSuchCustomPageFeedbackException, SystemException {
		return findByPrimaryKey((Serializable)customPageFeedbackPK);
	}

	/**
	 * Returns the custom page feedback with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the custom page feedback
	 * @return the custom page feedback, or <code>null</code> if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		CustomPageFeedback customPageFeedback = (CustomPageFeedback)EntityCacheUtil.getResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
				CustomPageFeedbackImpl.class, primaryKey);

		if (customPageFeedback == _nullCustomPageFeedback) {
			return null;
		}

		if (customPageFeedback == null) {
			Session session = null;

			try {
				session = openSession();

				customPageFeedback = (CustomPageFeedback)session.get(CustomPageFeedbackImpl.class,
						primaryKey);

				if (customPageFeedback != null) {
					cacheResult(customPageFeedback);
				}
				else {
					EntityCacheUtil.putResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
						CustomPageFeedbackImpl.class, primaryKey,
						_nullCustomPageFeedback);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(CustomPageFeedbackModelImpl.ENTITY_CACHE_ENABLED,
					CustomPageFeedbackImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return customPageFeedback;
	}

	/**
	 * Returns the custom page feedback with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param customPageFeedbackPK the primary key of the custom page feedback
	 * @return the custom page feedback, or <code>null</code> if a custom page feedback with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public CustomPageFeedback fetchByPrimaryKey(
		CustomPageFeedbackPK customPageFeedbackPK) throws SystemException {
		return fetchByPrimaryKey((Serializable)customPageFeedbackPK);
	}

	/**
	 * Returns all the custom page feedbacks.
	 *
	 * @return the custom page feedbacks
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<CustomPageFeedback> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CustomPageFeedback> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
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
	@Override
	public List<CustomPageFeedback> findAll(int start, int end,
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

		List<CustomPageFeedback> list = (List<CustomPageFeedback>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_CUSTOMPAGEFEEDBACK);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CUSTOMPAGEFEEDBACK;

				if (pagination) {
					sql = sql.concat(CustomPageFeedbackModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<CustomPageFeedback>(list);
				}
				else {
					list = (List<CustomPageFeedback>)QueryUtil.list(q,
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
	 * Removes all the custom page feedbacks from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (CustomPageFeedback customPageFeedback : findAll()) {
			remove(customPageFeedback);
		}
	}

	/**
	 * Returns the number of custom page feedbacks.
	 *
	 * @return the number of custom page feedbacks
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

				Query q = session.createQuery(_SQL_COUNT_CUSTOMPAGEFEEDBACK);

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
	 * Initializes the custom page feedback persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.custompages.model.CustomPageFeedback")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<CustomPageFeedback>> listenersList = new ArrayList<ModelListener<CustomPageFeedback>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<CustomPageFeedback>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(CustomPageFeedbackImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_CUSTOMPAGEFEEDBACK = "SELECT customPageFeedback FROM CustomPageFeedback customPageFeedback";
	private static final String _SQL_SELECT_CUSTOMPAGEFEEDBACK_WHERE = "SELECT customPageFeedback FROM CustomPageFeedback customPageFeedback WHERE ";
	private static final String _SQL_COUNT_CUSTOMPAGEFEEDBACK = "SELECT COUNT(customPageFeedback) FROM CustomPageFeedback customPageFeedback";
	private static final String _SQL_COUNT_CUSTOMPAGEFEEDBACK_WHERE = "SELECT COUNT(customPageFeedback) FROM CustomPageFeedback customPageFeedback WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "customPageFeedback.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CustomPageFeedback exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CustomPageFeedback exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(CustomPageFeedbackPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"hidden"
			});
	private static CustomPageFeedback _nullCustomPageFeedback = new CustomPageFeedbackImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<CustomPageFeedback> toCacheModel() {
				return _nullCustomPageFeedbackCacheModel;
			}
		};

	private static CacheModel<CustomPageFeedback> _nullCustomPageFeedbackCacheModel =
		new CacheModel<CustomPageFeedback>() {
			@Override
			public CustomPageFeedback toEntityModel() {
				return _nullCustomPageFeedback;
			}
		};
}