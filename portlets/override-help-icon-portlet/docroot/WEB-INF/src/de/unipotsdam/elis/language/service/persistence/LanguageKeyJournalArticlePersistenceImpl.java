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

package de.unipotsdam.elis.language.service.persistence;

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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.unipotsdam.elis.language.NoSuchKeyJournalArticleException;
import de.unipotsdam.elis.language.model.LanguageKeyJournalArticle;
import de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleImpl;
import de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the language key journal article service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see LanguageKeyJournalArticlePersistence
 * @see LanguageKeyJournalArticleUtil
 * @generated
 */
public class LanguageKeyJournalArticlePersistenceImpl
	extends BasePersistenceImpl<LanguageKeyJournalArticle>
	implements LanguageKeyJournalArticlePersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LanguageKeyJournalArticleUtil} to access the language key journal article persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LanguageKeyJournalArticleImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_KEY = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKey",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKey",
			new String[] { String.class.getName() },
			LanguageKeyJournalArticleModelImpl.KEY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_KEY = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKey", new String[] { String.class.getName() });

	/**
	 * Returns all the language key journal articles where key = &#63;.
	 *
	 * @param key the key
	 * @return the matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByKey(String key)
		throws SystemException {
		return findByKey(key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the language key journal articles where key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param key the key
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @return the range of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByKey(String key, int start,
		int end) throws SystemException {
		return findByKey(key, start, end, null);
	}

	/**
	 * Returns an ordered range of all the language key journal articles where key = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param key the key
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByKey(String key, int start,
		int end, OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY;
			finderArgs = new Object[] { key };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_KEY;
			finderArgs = new Object[] { key, start, end, orderByComparator };
		}

		List<LanguageKeyJournalArticle> list = (List<LanguageKeyJournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LanguageKeyJournalArticle languageKeyJournalArticle : list) {
				if (!Validator.equals(key, languageKeyJournalArticle.getKey())) {
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

			query.append(_SQL_SELECT_LANGUAGEKEYJOURNALARTICLE_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LanguageKeyJournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

				if (!pagination) {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LanguageKeyJournalArticle>(list);
				}
				else {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
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
	 * Returns the first language key journal article in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByKey_First(String key,
		OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = fetchByKey_First(key,
				orderByComparator);

		if (languageKeyJournalArticle != null) {
			return languageKeyJournalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchKeyJournalArticleException(msg.toString());
	}

	/**
	 * Returns the first language key journal article in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByKey_First(String key,
		OrderByComparator orderByComparator) throws SystemException {
		List<LanguageKeyJournalArticle> list = findByKey(key, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last language key journal article in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByKey_Last(String key,
		OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = fetchByKey_Last(key,
				orderByComparator);

		if (languageKeyJournalArticle != null) {
			return languageKeyJournalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("key=");
		msg.append(key);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchKeyJournalArticleException(msg.toString());
	}

	/**
	 * Returns the last language key journal article in the ordered set where key = &#63;.
	 *
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByKey_Last(String key,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByKey(key);

		if (count == 0) {
			return null;
		}

		List<LanguageKeyJournalArticle> list = findByKey(key, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the language key journal articles before and after the current language key journal article in the ordered set where key = &#63;.
	 *
	 * @param languageKeyJournalArticlePK the primary key of the current language key journal article
	 * @param key the key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle[] findByKey_PrevAndNext(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK, String key,
		OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = findByPrimaryKey(languageKeyJournalArticlePK);

		Session session = null;

		try {
			session = openSession();

			LanguageKeyJournalArticle[] array = new LanguageKeyJournalArticleImpl[3];

			array[0] = getByKey_PrevAndNext(session, languageKeyJournalArticle,
					key, orderByComparator, true);

			array[1] = languageKeyJournalArticle;

			array[2] = getByKey_PrevAndNext(session, languageKeyJournalArticle,
					key, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LanguageKeyJournalArticle getByKey_PrevAndNext(Session session,
		LanguageKeyJournalArticle languageKeyJournalArticle, String key,
		OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LANGUAGEKEYJOURNALARTICLE_WHERE);

		boolean bindKey = false;

		if (key == null) {
			query.append(_FINDER_COLUMN_KEY_KEY_1);
		}
		else if (key.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_KEY_KEY_3);
		}
		else {
			bindKey = true;

			query.append(_FINDER_COLUMN_KEY_KEY_2);
		}

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
			query.append(LanguageKeyJournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindKey) {
			qPos.add(key);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(languageKeyJournalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LanguageKeyJournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the language key journal articles where key = &#63; from the database.
	 *
	 * @param key the key
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByKey(String key) throws SystemException {
		for (LanguageKeyJournalArticle languageKeyJournalArticle : findByKey(
				key, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(languageKeyJournalArticle);
		}
	}

	/**
	 * Returns the number of language key journal articles where key = &#63;.
	 *
	 * @param key the key
	 * @return the number of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByKey(String key) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_KEY;

		Object[] finderArgs = new Object[] { key };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LANGUAGEKEYJOURNALARTICLE_WHERE);

			boolean bindKey = false;

			if (key == null) {
				query.append(_FINDER_COLUMN_KEY_KEY_1);
			}
			else if (key.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_KEY_KEY_3);
			}
			else {
				bindKey = true;

				query.append(_FINDER_COLUMN_KEY_KEY_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindKey) {
					qPos.add(key);
				}

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

	private static final String _FINDER_COLUMN_KEY_KEY_1 = "languageKeyJournalArticle.id.key IS NULL";
	private static final String _FINDER_COLUMN_KEY_KEY_2 = "languageKeyJournalArticle.id.key = ?";
	private static final String _FINDER_COLUMN_KEY_KEY_3 = "(languageKeyJournalArticle.id.key IS NULL OR languageKeyJournalArticle.id.key = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEID =
		new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByArticleId",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID =
		new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByArticleId",
			new String[] { String.class.getName() },
			LanguageKeyJournalArticleModelImpl.ARTICLEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ARTICLEID = new FinderPath(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByArticleId", new String[] { String.class.getName() });

	/**
	 * Returns all the language key journal articles where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByArticleId(String articleId)
		throws SystemException {
		return findByArticleId(articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the language key journal articles where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @return the range of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByArticleId(String articleId,
		int start, int end) throws SystemException {
		return findByArticleId(articleId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the language key journal articles where articleId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param articleId the article ID
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findByArticleId(String articleId,
		int start, int end, OrderByComparator orderByComparator)
		throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID;
			finderArgs = new Object[] { articleId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ARTICLEID;
			finderArgs = new Object[] { articleId, start, end, orderByComparator };
		}

		List<LanguageKeyJournalArticle> list = (List<LanguageKeyJournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (LanguageKeyJournalArticle languageKeyJournalArticle : list) {
				if (!Validator.equals(articleId,
							languageKeyJournalArticle.getArticleId())) {
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

			query.append(_SQL_SELECT_LANGUAGEKEYJOURNALARTICLE_WHERE);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LanguageKeyJournalArticleModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindArticleId) {
					qPos.add(articleId);
				}

				if (!pagination) {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LanguageKeyJournalArticle>(list);
				}
				else {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
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
	 * Returns the first language key journal article in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByArticleId_First(String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = fetchByArticleId_First(articleId,
				orderByComparator);

		if (languageKeyJournalArticle != null) {
			return languageKeyJournalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchKeyJournalArticleException(msg.toString());
	}

	/**
	 * Returns the first language key journal article in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByArticleId_First(String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		List<LanguageKeyJournalArticle> list = findByArticleId(articleId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last language key journal article in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByArticleId_Last(String articleId,
		OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = fetchByArticleId_Last(articleId,
				orderByComparator);

		if (languageKeyJournalArticle != null) {
			return languageKeyJournalArticle;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("articleId=");
		msg.append(articleId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchKeyJournalArticleException(msg.toString());
	}

	/**
	 * Returns the last language key journal article in the ordered set where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching language key journal article, or <code>null</code> if a matching language key journal article could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByArticleId_Last(String articleId,
		OrderByComparator orderByComparator) throws SystemException {
		int count = countByArticleId(articleId);

		if (count == 0) {
			return null;
		}

		List<LanguageKeyJournalArticle> list = findByArticleId(articleId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the language key journal articles before and after the current language key journal article in the ordered set where articleId = &#63;.
	 *
	 * @param languageKeyJournalArticlePK the primary key of the current language key journal article
	 * @param articleId the article ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle[] findByArticleId_PrevAndNext(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK,
		String articleId, OrderByComparator orderByComparator)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = findByPrimaryKey(languageKeyJournalArticlePK);

		Session session = null;

		try {
			session = openSession();

			LanguageKeyJournalArticle[] array = new LanguageKeyJournalArticleImpl[3];

			array[0] = getByArticleId_PrevAndNext(session,
					languageKeyJournalArticle, articleId, orderByComparator,
					true);

			array[1] = languageKeyJournalArticle;

			array[2] = getByArticleId_PrevAndNext(session,
					languageKeyJournalArticle, articleId, orderByComparator,
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

	protected LanguageKeyJournalArticle getByArticleId_PrevAndNext(
		Session session, LanguageKeyJournalArticle languageKeyJournalArticle,
		String articleId, OrderByComparator orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LANGUAGEKEYJOURNALARTICLE_WHERE);

		boolean bindArticleId = false;

		if (articleId == null) {
			query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
		}
		else if (articleId.equals(StringPool.BLANK)) {
			query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
		}
		else {
			bindArticleId = true;

			query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
		}

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
			query.append(LanguageKeyJournalArticleModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindArticleId) {
			qPos.add(articleId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(languageKeyJournalArticle);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<LanguageKeyJournalArticle> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the language key journal articles where articleId = &#63; from the database.
	 *
	 * @param articleId the article ID
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeByArticleId(String articleId) throws SystemException {
		for (LanguageKeyJournalArticle languageKeyJournalArticle : findByArticleId(
				articleId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(languageKeyJournalArticle);
		}
	}

	/**
	 * Returns the number of language key journal articles where articleId = &#63;.
	 *
	 * @param articleId the article ID
	 * @return the number of matching language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByArticleId(String articleId) throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ARTICLEID;

		Object[] finderArgs = new Object[] { articleId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LANGUAGEKEYJOURNALARTICLE_WHERE);

			boolean bindArticleId = false;

			if (articleId == null) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_1);
			}
			else if (articleId.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_3);
			}
			else {
				bindArticleId = true;

				query.append(_FINDER_COLUMN_ARTICLEID_ARTICLEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindArticleId) {
					qPos.add(articleId);
				}

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

	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_1 = "languageKeyJournalArticle.id.articleId IS NULL";
	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_2 = "languageKeyJournalArticle.id.articleId = ?";
	private static final String _FINDER_COLUMN_ARTICLEID_ARTICLEID_3 = "(languageKeyJournalArticle.id.articleId IS NULL OR languageKeyJournalArticle.id.articleId = '')";

	public LanguageKeyJournalArticlePersistenceImpl() {
		setModelClass(LanguageKeyJournalArticle.class);
	}

	/**
	 * Caches the language key journal article in the entity cache if it is enabled.
	 *
	 * @param languageKeyJournalArticle the language key journal article
	 */
	@Override
	public void cacheResult(LanguageKeyJournalArticle languageKeyJournalArticle) {
		EntityCacheUtil.putResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			languageKeyJournalArticle.getPrimaryKey(), languageKeyJournalArticle);

		languageKeyJournalArticle.resetOriginalValues();
	}

	/**
	 * Caches the language key journal articles in the entity cache if it is enabled.
	 *
	 * @param languageKeyJournalArticles the language key journal articles
	 */
	@Override
	public void cacheResult(
		List<LanguageKeyJournalArticle> languageKeyJournalArticles) {
		for (LanguageKeyJournalArticle languageKeyJournalArticle : languageKeyJournalArticles) {
			if (EntityCacheUtil.getResult(
						LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						LanguageKeyJournalArticleImpl.class,
						languageKeyJournalArticle.getPrimaryKey()) == null) {
				cacheResult(languageKeyJournalArticle);
			}
			else {
				languageKeyJournalArticle.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all language key journal articles.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LanguageKeyJournalArticleImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LanguageKeyJournalArticleImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the language key journal article.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LanguageKeyJournalArticle languageKeyJournalArticle) {
		EntityCacheUtil.removeResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			languageKeyJournalArticle.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<LanguageKeyJournalArticle> languageKeyJournalArticles) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LanguageKeyJournalArticle languageKeyJournalArticle : languageKeyJournalArticles) {
			EntityCacheUtil.removeResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				LanguageKeyJournalArticleImpl.class,
				languageKeyJournalArticle.getPrimaryKey());
		}
	}

	/**
	 * Creates a new language key journal article with the primary key. Does not add the language key journal article to the database.
	 *
	 * @param languageKeyJournalArticlePK the primary key for the new language key journal article
	 * @return the new language key journal article
	 */
	@Override
	public LanguageKeyJournalArticle create(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK) {
		LanguageKeyJournalArticle languageKeyJournalArticle = new LanguageKeyJournalArticleImpl();

		languageKeyJournalArticle.setNew(true);
		languageKeyJournalArticle.setPrimaryKey(languageKeyJournalArticlePK);

		return languageKeyJournalArticle;
	}

	/**
	 * Removes the language key journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param languageKeyJournalArticlePK the primary key of the language key journal article
	 * @return the language key journal article that was removed
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle remove(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws NoSuchKeyJournalArticleException, SystemException {
		return remove((Serializable)languageKeyJournalArticlePK);
	}

	/**
	 * Removes the language key journal article with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the language key journal article
	 * @return the language key journal article that was removed
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle remove(Serializable primaryKey)
		throws NoSuchKeyJournalArticleException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LanguageKeyJournalArticle languageKeyJournalArticle = (LanguageKeyJournalArticle)session.get(LanguageKeyJournalArticleImpl.class,
					primaryKey);

			if (languageKeyJournalArticle == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchKeyJournalArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(languageKeyJournalArticle);
		}
		catch (NoSuchKeyJournalArticleException nsee) {
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
	protected LanguageKeyJournalArticle removeImpl(
		LanguageKeyJournalArticle languageKeyJournalArticle)
		throws SystemException {
		languageKeyJournalArticle = toUnwrappedModel(languageKeyJournalArticle);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(languageKeyJournalArticle)) {
				languageKeyJournalArticle = (LanguageKeyJournalArticle)session.get(LanguageKeyJournalArticleImpl.class,
						languageKeyJournalArticle.getPrimaryKeyObj());
			}

			if (languageKeyJournalArticle != null) {
				session.delete(languageKeyJournalArticle);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (languageKeyJournalArticle != null) {
			clearCache(languageKeyJournalArticle);
		}

		return languageKeyJournalArticle;
	}

	@Override
	public LanguageKeyJournalArticle updateImpl(
		de.unipotsdam.elis.language.model.LanguageKeyJournalArticle languageKeyJournalArticle)
		throws SystemException {
		languageKeyJournalArticle = toUnwrappedModel(languageKeyJournalArticle);

		boolean isNew = languageKeyJournalArticle.isNew();

		LanguageKeyJournalArticleModelImpl languageKeyJournalArticleModelImpl = (LanguageKeyJournalArticleModelImpl)languageKeyJournalArticle;

		Session session = null;

		try {
			session = openSession();

			if (languageKeyJournalArticle.isNew()) {
				session.save(languageKeyJournalArticle);

				languageKeyJournalArticle.setNew(false);
			}
			else {
				session.merge(languageKeyJournalArticle);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew ||
				!LanguageKeyJournalArticleModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((languageKeyJournalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						languageKeyJournalArticleModelImpl.getOriginalKey()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY,
					args);

				args = new Object[] { languageKeyJournalArticleModelImpl.getKey() };

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_KEY, args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_KEY,
					args);
			}

			if ((languageKeyJournalArticleModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						languageKeyJournalArticleModelImpl.getOriginalArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID,
					args);

				args = new Object[] {
						languageKeyJournalArticleModelImpl.getArticleId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_ARTICLEID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ARTICLEID,
					args);
			}
		}

		EntityCacheUtil.putResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyJournalArticleImpl.class,
			languageKeyJournalArticle.getPrimaryKey(), languageKeyJournalArticle);

		return languageKeyJournalArticle;
	}

	protected LanguageKeyJournalArticle toUnwrappedModel(
		LanguageKeyJournalArticle languageKeyJournalArticle) {
		if (languageKeyJournalArticle instanceof LanguageKeyJournalArticleImpl) {
			return languageKeyJournalArticle;
		}

		LanguageKeyJournalArticleImpl languageKeyJournalArticleImpl = new LanguageKeyJournalArticleImpl();

		languageKeyJournalArticleImpl.setNew(languageKeyJournalArticle.isNew());
		languageKeyJournalArticleImpl.setPrimaryKey(languageKeyJournalArticle.getPrimaryKey());

		languageKeyJournalArticleImpl.setKey(languageKeyJournalArticle.getKey());
		languageKeyJournalArticleImpl.setArticleId(languageKeyJournalArticle.getArticleId());

		return languageKeyJournalArticleImpl;
	}

	/**
	 * Returns the language key journal article with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the language key journal article
	 * @return the language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByPrimaryKey(Serializable primaryKey)
		throws NoSuchKeyJournalArticleException, SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = fetchByPrimaryKey(primaryKey);

		if (languageKeyJournalArticle == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchKeyJournalArticleException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return languageKeyJournalArticle;
	}

	/**
	 * Returns the language key journal article with the primary key or throws a {@link de.unipotsdam.elis.language.NoSuchKeyJournalArticleException} if it could not be found.
	 *
	 * @param languageKeyJournalArticlePK the primary key of the language key journal article
	 * @return the language key journal article
	 * @throws de.unipotsdam.elis.language.NoSuchKeyJournalArticleException if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle findByPrimaryKey(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws NoSuchKeyJournalArticleException, SystemException {
		return findByPrimaryKey((Serializable)languageKeyJournalArticlePK);
	}

	/**
	 * Returns the language key journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the language key journal article
	 * @return the language key journal article, or <code>null</code> if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		LanguageKeyJournalArticle languageKeyJournalArticle = (LanguageKeyJournalArticle)EntityCacheUtil.getResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
				LanguageKeyJournalArticleImpl.class, primaryKey);

		if (languageKeyJournalArticle == _nullLanguageKeyJournalArticle) {
			return null;
		}

		if (languageKeyJournalArticle == null) {
			Session session = null;

			try {
				session = openSession();

				languageKeyJournalArticle = (LanguageKeyJournalArticle)session.get(LanguageKeyJournalArticleImpl.class,
						primaryKey);

				if (languageKeyJournalArticle != null) {
					cacheResult(languageKeyJournalArticle);
				}
				else {
					EntityCacheUtil.putResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
						LanguageKeyJournalArticleImpl.class, primaryKey,
						_nullLanguageKeyJournalArticle);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(LanguageKeyJournalArticleModelImpl.ENTITY_CACHE_ENABLED,
					LanguageKeyJournalArticleImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return languageKeyJournalArticle;
	}

	/**
	 * Returns the language key journal article with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param languageKeyJournalArticlePK the primary key of the language key journal article
	 * @return the language key journal article, or <code>null</code> if a language key journal article with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKeyJournalArticle fetchByPrimaryKey(
		LanguageKeyJournalArticlePK languageKeyJournalArticlePK)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)languageKeyJournalArticlePK);
	}

	/**
	 * Returns all the language key journal articles.
	 *
	 * @return the language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the language key journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @return the range of language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the language key journal articles.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyJournalArticleModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of language key journal articles
	 * @param end the upper bound of the range of language key journal articles (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of language key journal articles
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKeyJournalArticle> findAll(int start, int end,
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

		List<LanguageKeyJournalArticle> list = (List<LanguageKeyJournalArticle>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LANGUAGEKEYJOURNALARTICLE);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LANGUAGEKEYJOURNALARTICLE;

				if (pagination) {
					sql = sql.concat(LanguageKeyJournalArticleModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LanguageKeyJournalArticle>(list);
				}
				else {
					list = (List<LanguageKeyJournalArticle>)QueryUtil.list(q,
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
	 * Removes all the language key journal articles from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (LanguageKeyJournalArticle languageKeyJournalArticle : findAll()) {
			remove(languageKeyJournalArticle);
		}
	}

	/**
	 * Returns the number of language key journal articles.
	 *
	 * @return the number of language key journal articles
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

				Query q = session.createQuery(_SQL_COUNT_LANGUAGEKEYJOURNALARTICLE);

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
	 * Initializes the language key journal article persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.language.model.LanguageKeyJournalArticle")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LanguageKeyJournalArticle>> listenersList = new ArrayList<ModelListener<LanguageKeyJournalArticle>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LanguageKeyJournalArticle>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LanguageKeyJournalArticleImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LANGUAGEKEYJOURNALARTICLE = "SELECT languageKeyJournalArticle FROM LanguageKeyJournalArticle languageKeyJournalArticle";
	private static final String _SQL_SELECT_LANGUAGEKEYJOURNALARTICLE_WHERE = "SELECT languageKeyJournalArticle FROM LanguageKeyJournalArticle languageKeyJournalArticle WHERE ";
	private static final String _SQL_COUNT_LANGUAGEKEYJOURNALARTICLE = "SELECT COUNT(languageKeyJournalArticle) FROM LanguageKeyJournalArticle languageKeyJournalArticle";
	private static final String _SQL_COUNT_LANGUAGEKEYJOURNALARTICLE_WHERE = "SELECT COUNT(languageKeyJournalArticle) FROM LanguageKeyJournalArticle languageKeyJournalArticle WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "languageKeyJournalArticle.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LanguageKeyJournalArticle exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LanguageKeyJournalArticle exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LanguageKeyJournalArticlePersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key"
			});
	private static LanguageKeyJournalArticle _nullLanguageKeyJournalArticle = new LanguageKeyJournalArticleImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LanguageKeyJournalArticle> toCacheModel() {
				return _nullLanguageKeyJournalArticleCacheModel;
			}
		};

	private static CacheModel<LanguageKeyJournalArticle> _nullLanguageKeyJournalArticleCacheModel =
		new CacheModel<LanguageKeyJournalArticle>() {
			@Override
			public LanguageKeyJournalArticle toEntityModel() {
				return _nullLanguageKeyJournalArticle;
			}
		};
}