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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import de.unipotsdam.elis.language.NoSuchKeyException;
import de.unipotsdam.elis.language.model.LanguageKey;
import de.unipotsdam.elis.language.model.impl.LanguageKeyImpl;
import de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the language key service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see LanguageKeyPersistence
 * @see LanguageKeyUtil
 * @generated
 */
public class LanguageKeyPersistenceImpl extends BasePersistenceImpl<LanguageKey>
	implements LanguageKeyPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LanguageKeyUtil} to access the language key persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LanguageKeyImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyModelImpl.FINDER_CACHE_ENABLED, LanguageKeyImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyModelImpl.FINDER_CACHE_ENABLED, LanguageKeyImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public LanguageKeyPersistenceImpl() {
		setModelClass(LanguageKey.class);
	}

	/**
	 * Caches the language key in the entity cache if it is enabled.
	 *
	 * @param languageKey the language key
	 */
	@Override
	public void cacheResult(LanguageKey languageKey) {
		EntityCacheUtil.putResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyImpl.class, languageKey.getPrimaryKey(), languageKey);

		languageKey.resetOriginalValues();
	}

	/**
	 * Caches the language keies in the entity cache if it is enabled.
	 *
	 * @param languageKeies the language keies
	 */
	@Override
	public void cacheResult(List<LanguageKey> languageKeies) {
		for (LanguageKey languageKey : languageKeies) {
			if (EntityCacheUtil.getResult(
						LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
						LanguageKeyImpl.class, languageKey.getPrimaryKey()) == null) {
				cacheResult(languageKey);
			}
			else {
				languageKey.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all language keies.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(LanguageKeyImpl.class.getName());
		}

		EntityCacheUtil.clearCache(LanguageKeyImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the language key.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LanguageKey languageKey) {
		EntityCacheUtil.removeResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyImpl.class, languageKey.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<LanguageKey> languageKeies) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LanguageKey languageKey : languageKeies) {
			EntityCacheUtil.removeResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
				LanguageKeyImpl.class, languageKey.getPrimaryKey());
		}
	}

	/**
	 * Creates a new language key with the primary key. Does not add the language key to the database.
	 *
	 * @param key the primary key for the new language key
	 * @return the new language key
	 */
	@Override
	public LanguageKey create(String key) {
		LanguageKey languageKey = new LanguageKeyImpl();

		languageKey.setNew(true);
		languageKey.setPrimaryKey(key);

		return languageKey;
	}

	/**
	 * Removes the language key with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param key the primary key of the language key
	 * @return the language key that was removed
	 * @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey remove(String key)
		throws NoSuchKeyException, SystemException {
		return remove((Serializable)key);
	}

	/**
	 * Removes the language key with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the language key
	 * @return the language key that was removed
	 * @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey remove(Serializable primaryKey)
		throws NoSuchKeyException, SystemException {
		Session session = null;

		try {
			session = openSession();

			LanguageKey languageKey = (LanguageKey)session.get(LanguageKeyImpl.class,
					primaryKey);

			if (languageKey == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchKeyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(languageKey);
		}
		catch (NoSuchKeyException nsee) {
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
	protected LanguageKey removeImpl(LanguageKey languageKey)
		throws SystemException {
		languageKey = toUnwrappedModel(languageKey);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(languageKey)) {
				languageKey = (LanguageKey)session.get(LanguageKeyImpl.class,
						languageKey.getPrimaryKeyObj());
			}

			if (languageKey != null) {
				session.delete(languageKey);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (languageKey != null) {
			clearCache(languageKey);
		}

		return languageKey;
	}

	@Override
	public LanguageKey updateImpl(
		de.unipotsdam.elis.language.model.LanguageKey languageKey)
		throws SystemException {
		languageKey = toUnwrappedModel(languageKey);

		boolean isNew = languageKey.isNew();

		Session session = null;

		try {
			session = openSession();

			if (languageKey.isNew()) {
				session.save(languageKey);

				languageKey.setNew(false);
			}
			else {
				session.merge(languageKey);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
			LanguageKeyImpl.class, languageKey.getPrimaryKey(), languageKey);

		return languageKey;
	}

	protected LanguageKey toUnwrappedModel(LanguageKey languageKey) {
		if (languageKey instanceof LanguageKeyImpl) {
			return languageKey;
		}

		LanguageKeyImpl languageKeyImpl = new LanguageKeyImpl();

		languageKeyImpl.setNew(languageKey.isNew());
		languageKeyImpl.setPrimaryKey(languageKey.getPrimaryKey());

		languageKeyImpl.setKey(languageKey.getKey());
		languageKeyImpl.setValue(languageKey.getValue());

		return languageKeyImpl;
	}

	/**
	 * Returns the language key with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the language key
	 * @return the language key
	 * @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey findByPrimaryKey(Serializable primaryKey)
		throws NoSuchKeyException, SystemException {
		LanguageKey languageKey = fetchByPrimaryKey(primaryKey);

		if (languageKey == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchKeyException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return languageKey;
	}

	/**
	 * Returns the language key with the primary key or throws a {@link de.unipotsdam.elis.language.NoSuchKeyException} if it could not be found.
	 *
	 * @param key the primary key of the language key
	 * @return the language key
	 * @throws de.unipotsdam.elis.language.NoSuchKeyException if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey findByPrimaryKey(String key)
		throws NoSuchKeyException, SystemException {
		return findByPrimaryKey((Serializable)key);
	}

	/**
	 * Returns the language key with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the language key
	 * @return the language key, or <code>null</code> if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		LanguageKey languageKey = (LanguageKey)EntityCacheUtil.getResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
				LanguageKeyImpl.class, primaryKey);

		if (languageKey == _nullLanguageKey) {
			return null;
		}

		if (languageKey == null) {
			Session session = null;

			try {
				session = openSession();

				languageKey = (LanguageKey)session.get(LanguageKeyImpl.class,
						primaryKey);

				if (languageKey != null) {
					cacheResult(languageKey);
				}
				else {
					EntityCacheUtil.putResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
						LanguageKeyImpl.class, primaryKey, _nullLanguageKey);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(LanguageKeyModelImpl.ENTITY_CACHE_ENABLED,
					LanguageKeyImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return languageKey;
	}

	/**
	 * Returns the language key with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param key the primary key of the language key
	 * @return the language key, or <code>null</code> if a language key with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public LanguageKey fetchByPrimaryKey(String key) throws SystemException {
		return fetchByPrimaryKey((Serializable)key);
	}

	/**
	 * Returns all the language keies.
	 *
	 * @return the language keies
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKey> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the language keies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of language keies
	 * @param end the upper bound of the range of language keies (not inclusive)
	 * @return the range of language keies
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKey> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the language keies.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.language.model.impl.LanguageKeyModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of language keies
	 * @param end the upper bound of the range of language keies (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of language keies
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<LanguageKey> findAll(int start, int end,
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

		List<LanguageKey> list = (List<LanguageKey>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_LANGUAGEKEY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LANGUAGEKEY;

				if (pagination) {
					sql = sql.concat(LanguageKeyModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LanguageKey>)QueryUtil.list(q, getDialect(),
							start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<LanguageKey>(list);
				}
				else {
					list = (List<LanguageKey>)QueryUtil.list(q, getDialect(),
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
	 * Removes all the language keies from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (LanguageKey languageKey : findAll()) {
			remove(languageKey);
		}
	}

	/**
	 * Returns the number of language keies.
	 *
	 * @return the number of language keies
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

				Query q = session.createQuery(_SQL_COUNT_LANGUAGEKEY);

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
	 * Initializes the language key persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.language.model.LanguageKey")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<LanguageKey>> listenersList = new ArrayList<ModelListener<LanguageKey>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<LanguageKey>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(LanguageKeyImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LANGUAGEKEY = "SELECT languageKey FROM LanguageKey languageKey";
	private static final String _SQL_COUNT_LANGUAGEKEY = "SELECT COUNT(languageKey) FROM LanguageKey languageKey";
	private static final String _ORDER_BY_ENTITY_ALIAS = "languageKey.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LanguageKey exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(LanguageKeyPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"key"
			});
	private static LanguageKey _nullLanguageKey = new LanguageKeyImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<LanguageKey> toCacheModel() {
				return _nullLanguageKeyCacheModel;
			}
		};

	private static CacheModel<LanguageKey> _nullLanguageKeyCacheModel = new CacheModel<LanguageKey>() {
			@Override
			public LanguageKey toEntityModel() {
				return _nullLanguageKey;
			}
		};
}