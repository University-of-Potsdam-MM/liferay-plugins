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

package de.unipotsdam.elis.activities.service.persistence;

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

import de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException;
import de.unipotsdam.elis.activities.model.MoodleSocialActivity;
import de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityImpl;
import de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The persistence implementation for the moodle social activity service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Matthias
 * @see MoodleSocialActivityPersistence
 * @see MoodleSocialActivityUtil
 * @generated
 */
public class MoodleSocialActivityPersistenceImpl extends BasePersistenceImpl<MoodleSocialActivity>
	implements MoodleSocialActivityPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link MoodleSocialActivityUtil} to access the moodle social activity persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = MoodleSocialActivityImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityModelImpl.FINDER_CACHE_ENABLED,
			MoodleSocialActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityModelImpl.FINDER_CACHE_ENABLED,
			MoodleSocialActivityImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID = new FinderPath(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityModelImpl.FINDER_CACHE_ENABLED,
			MoodleSocialActivityImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByextSocialActivityId",
			new String[] { Long.class.getName() },
			MoodleSocialActivityModelImpl.EXTSOCIALACTIVITYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID = new FinderPath(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByextSocialActivityId", new String[] { Long.class.getName() });

	/**
	 * Returns the moodle social activity where extSocialActivityId = &#63; or throws a {@link de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException} if it could not be found.
	 *
	 * @param extSocialActivityId the ext social activity ID
	 * @return the matching moodle social activity
	 * @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a matching moodle social activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity findByextSocialActivityId(
		long extSocialActivityId)
		throws NoSuchMoodleSocialActivityException, SystemException {
		MoodleSocialActivity moodleSocialActivity = fetchByextSocialActivityId(extSocialActivityId);

		if (moodleSocialActivity == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("extSocialActivityId=");
			msg.append(extSocialActivityId);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchMoodleSocialActivityException(msg.toString());
		}

		return moodleSocialActivity;
	}

	/**
	 * Returns the moodle social activity where extSocialActivityId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param extSocialActivityId the ext social activity ID
	 * @return the matching moodle social activity, or <code>null</code> if a matching moodle social activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity fetchByextSocialActivityId(
		long extSocialActivityId) throws SystemException {
		return fetchByextSocialActivityId(extSocialActivityId, true);
	}

	/**
	 * Returns the moodle social activity where extSocialActivityId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param extSocialActivityId the ext social activity ID
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching moodle social activity, or <code>null</code> if a matching moodle social activity could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity fetchByextSocialActivityId(
		long extSocialActivityId, boolean retrieveFromCache)
		throws SystemException {
		Object[] finderArgs = new Object[] { extSocialActivityId };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
					finderArgs, this);
		}

		if (result instanceof MoodleSocialActivity) {
			MoodleSocialActivity moodleSocialActivity = (MoodleSocialActivity)result;

			if ((extSocialActivityId != moodleSocialActivity.getExtSocialActivityId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_MOODLESOCIALACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_EXTSOCIALACTIVITYID_EXTSOCIALACTIVITYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(extSocialActivityId);

				List<MoodleSocialActivity> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
						finderArgs, list);
				}
				else {
					if ((list.size() > 1) && _log.isWarnEnabled()) {
						_log.warn(
							"MoodleSocialActivityPersistenceImpl.fetchByextSocialActivityId(long, boolean) with parameters (" +
							StringUtil.merge(finderArgs) +
							") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
					}

					MoodleSocialActivity moodleSocialActivity = list.get(0);

					result = moodleSocialActivity;

					cacheResult(moodleSocialActivity);

					if ((moodleSocialActivity.getExtSocialActivityId() != extSocialActivityId)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
							finderArgs, moodleSocialActivity);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
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
			return (MoodleSocialActivity)result;
		}
	}

	/**
	 * Removes the moodle social activity where extSocialActivityId = &#63; from the database.
	 *
	 * @param extSocialActivityId the ext social activity ID
	 * @return the moodle social activity that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity removeByextSocialActivityId(
		long extSocialActivityId)
		throws NoSuchMoodleSocialActivityException, SystemException {
		MoodleSocialActivity moodleSocialActivity = findByextSocialActivityId(extSocialActivityId);

		return remove(moodleSocialActivity);
	}

	/**
	 * Returns the number of moodle social activities where extSocialActivityId = &#63;.
	 *
	 * @param extSocialActivityId the ext social activity ID
	 * @return the number of matching moodle social activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countByextSocialActivityId(long extSocialActivityId)
		throws SystemException {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID;

		Object[] finderArgs = new Object[] { extSocialActivityId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_MOODLESOCIALACTIVITY_WHERE);

			query.append(_FINDER_COLUMN_EXTSOCIALACTIVITYID_EXTSOCIALACTIVITYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(extSocialActivityId);

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

	private static final String _FINDER_COLUMN_EXTSOCIALACTIVITYID_EXTSOCIALACTIVITYID_2 =
		"moodleSocialActivity.extSocialActivityId = ?";

	public MoodleSocialActivityPersistenceImpl() {
		setModelClass(MoodleSocialActivity.class);
	}

	/**
	 * Caches the moodle social activity in the entity cache if it is enabled.
	 *
	 * @param moodleSocialActivity the moodle social activity
	 */
	@Override
	public void cacheResult(MoodleSocialActivity moodleSocialActivity) {
		EntityCacheUtil.putResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityImpl.class,
			moodleSocialActivity.getPrimaryKey(), moodleSocialActivity);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
			new Object[] { moodleSocialActivity.getExtSocialActivityId() },
			moodleSocialActivity);

		moodleSocialActivity.resetOriginalValues();
	}

	/**
	 * Caches the moodle social activities in the entity cache if it is enabled.
	 *
	 * @param moodleSocialActivities the moodle social activities
	 */
	@Override
	public void cacheResult(List<MoodleSocialActivity> moodleSocialActivities) {
		for (MoodleSocialActivity moodleSocialActivity : moodleSocialActivities) {
			if (EntityCacheUtil.getResult(
						MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
						MoodleSocialActivityImpl.class,
						moodleSocialActivity.getPrimaryKey()) == null) {
				cacheResult(moodleSocialActivity);
			}
			else {
				moodleSocialActivity.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all moodle social activities.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(MoodleSocialActivityImpl.class.getName());
		}

		EntityCacheUtil.clearCache(MoodleSocialActivityImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the moodle social activity.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(MoodleSocialActivity moodleSocialActivity) {
		EntityCacheUtil.removeResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityImpl.class, moodleSocialActivity.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(moodleSocialActivity);
	}

	@Override
	public void clearCache(List<MoodleSocialActivity> moodleSocialActivities) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (MoodleSocialActivity moodleSocialActivity : moodleSocialActivities) {
			EntityCacheUtil.removeResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
				MoodleSocialActivityImpl.class,
				moodleSocialActivity.getPrimaryKey());

			clearUniqueFindersCache(moodleSocialActivity);
		}
	}

	protected void cacheUniqueFindersCache(
		MoodleSocialActivity moodleSocialActivity) {
		if (moodleSocialActivity.isNew()) {
			Object[] args = new Object[] {
					moodleSocialActivity.getExtSocialActivityId()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID,
				args, Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
				args, moodleSocialActivity);
		}
		else {
			MoodleSocialActivityModelImpl moodleSocialActivityModelImpl = (MoodleSocialActivityModelImpl)moodleSocialActivity;

			if ((moodleSocialActivityModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						moodleSocialActivity.getExtSocialActivityId()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID,
					args, Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
					args, moodleSocialActivity);
			}
		}
	}

	protected void clearUniqueFindersCache(
		MoodleSocialActivity moodleSocialActivity) {
		MoodleSocialActivityModelImpl moodleSocialActivityModelImpl = (MoodleSocialActivityModelImpl)moodleSocialActivity;

		Object[] args = new Object[] {
				moodleSocialActivity.getExtSocialActivityId()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID,
			args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
			args);

		if ((moodleSocialActivityModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID.getColumnBitmask()) != 0) {
			args = new Object[] {
					moodleSocialActivityModelImpl.getOriginalExtSocialActivityId()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_EXTSOCIALACTIVITYID,
				args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_EXTSOCIALACTIVITYID,
				args);
		}
	}

	/**
	 * Creates a new moodle social activity with the primary key. Does not add the moodle social activity to the database.
	 *
	 * @param extSocialActivityId the primary key for the new moodle social activity
	 * @return the new moodle social activity
	 */
	@Override
	public MoodleSocialActivity create(long extSocialActivityId) {
		MoodleSocialActivity moodleSocialActivity = new MoodleSocialActivityImpl();

		moodleSocialActivity.setNew(true);
		moodleSocialActivity.setPrimaryKey(extSocialActivityId);

		return moodleSocialActivity;
	}

	/**
	 * Removes the moodle social activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param extSocialActivityId the primary key of the moodle social activity
	 * @return the moodle social activity that was removed
	 * @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity remove(long extSocialActivityId)
		throws NoSuchMoodleSocialActivityException, SystemException {
		return remove((Serializable)extSocialActivityId);
	}

	/**
	 * Removes the moodle social activity with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the moodle social activity
	 * @return the moodle social activity that was removed
	 * @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity remove(Serializable primaryKey)
		throws NoSuchMoodleSocialActivityException, SystemException {
		Session session = null;

		try {
			session = openSession();

			MoodleSocialActivity moodleSocialActivity = (MoodleSocialActivity)session.get(MoodleSocialActivityImpl.class,
					primaryKey);

			if (moodleSocialActivity == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchMoodleSocialActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(moodleSocialActivity);
		}
		catch (NoSuchMoodleSocialActivityException nsee) {
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
	protected MoodleSocialActivity removeImpl(
		MoodleSocialActivity moodleSocialActivity) throws SystemException {
		moodleSocialActivity = toUnwrappedModel(moodleSocialActivity);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(moodleSocialActivity)) {
				moodleSocialActivity = (MoodleSocialActivity)session.get(MoodleSocialActivityImpl.class,
						moodleSocialActivity.getPrimaryKeyObj());
			}

			if (moodleSocialActivity != null) {
				session.delete(moodleSocialActivity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (moodleSocialActivity != null) {
			clearCache(moodleSocialActivity);
		}

		return moodleSocialActivity;
	}

	@Override
	public MoodleSocialActivity updateImpl(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity)
		throws SystemException {
		moodleSocialActivity = toUnwrappedModel(moodleSocialActivity);

		boolean isNew = moodleSocialActivity.isNew();

		Session session = null;

		try {
			session = openSession();

			if (moodleSocialActivity.isNew()) {
				session.save(moodleSocialActivity);

				moodleSocialActivity.setNew(false);
			}
			else {
				session.merge(moodleSocialActivity);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !MoodleSocialActivityModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
			MoodleSocialActivityImpl.class,
			moodleSocialActivity.getPrimaryKey(), moodleSocialActivity);

		clearUniqueFindersCache(moodleSocialActivity);
		cacheUniqueFindersCache(moodleSocialActivity);

		return moodleSocialActivity;
	}

	protected MoodleSocialActivity toUnwrappedModel(
		MoodleSocialActivity moodleSocialActivity) {
		if (moodleSocialActivity instanceof MoodleSocialActivityImpl) {
			return moodleSocialActivity;
		}

		MoodleSocialActivityImpl moodleSocialActivityImpl = new MoodleSocialActivityImpl();

		moodleSocialActivityImpl.setNew(moodleSocialActivity.isNew());
		moodleSocialActivityImpl.setPrimaryKey(moodleSocialActivity.getPrimaryKey());

		moodleSocialActivityImpl.setExtSocialActivityId(moodleSocialActivity.getExtSocialActivityId());
		moodleSocialActivityImpl.setUserId(moodleSocialActivity.getUserId());
		moodleSocialActivityImpl.setType(moodleSocialActivity.getType());
		moodleSocialActivityImpl.setExtServiceActivityType(moodleSocialActivity.getExtServiceActivityType());
		moodleSocialActivityImpl.setExtServiceContext(moodleSocialActivity.getExtServiceContext());
		moodleSocialActivityImpl.setData(moodleSocialActivity.getData());
		moodleSocialActivityImpl.setPublished(moodleSocialActivity.getPublished());

		return moodleSocialActivityImpl;
	}

	/**
	 * Returns the moodle social activity with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the moodle social activity
	 * @return the moodle social activity
	 * @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity findByPrimaryKey(Serializable primaryKey)
		throws NoSuchMoodleSocialActivityException, SystemException {
		MoodleSocialActivity moodleSocialActivity = fetchByPrimaryKey(primaryKey);

		if (moodleSocialActivity == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchMoodleSocialActivityException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return moodleSocialActivity;
	}

	/**
	 * Returns the moodle social activity with the primary key or throws a {@link de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException} if it could not be found.
	 *
	 * @param extSocialActivityId the primary key of the moodle social activity
	 * @return the moodle social activity
	 * @throws de.unipotsdam.elis.activities.NoSuchMoodleSocialActivityException if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity findByPrimaryKey(long extSocialActivityId)
		throws NoSuchMoodleSocialActivityException, SystemException {
		return findByPrimaryKey((Serializable)extSocialActivityId);
	}

	/**
	 * Returns the moodle social activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the moodle social activity
	 * @return the moodle social activity, or <code>null</code> if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		MoodleSocialActivity moodleSocialActivity = (MoodleSocialActivity)EntityCacheUtil.getResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
				MoodleSocialActivityImpl.class, primaryKey);

		if (moodleSocialActivity == _nullMoodleSocialActivity) {
			return null;
		}

		if (moodleSocialActivity == null) {
			Session session = null;

			try {
				session = openSession();

				moodleSocialActivity = (MoodleSocialActivity)session.get(MoodleSocialActivityImpl.class,
						primaryKey);

				if (moodleSocialActivity != null) {
					cacheResult(moodleSocialActivity);
				}
				else {
					EntityCacheUtil.putResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
						MoodleSocialActivityImpl.class, primaryKey,
						_nullMoodleSocialActivity);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(MoodleSocialActivityModelImpl.ENTITY_CACHE_ENABLED,
					MoodleSocialActivityImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return moodleSocialActivity;
	}

	/**
	 * Returns the moodle social activity with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param extSocialActivityId the primary key of the moodle social activity
	 * @return the moodle social activity, or <code>null</code> if a moodle social activity with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public MoodleSocialActivity fetchByPrimaryKey(long extSocialActivityId)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)extSocialActivityId);
	}

	/**
	 * Returns all the moodle social activities.
	 *
	 * @return the moodle social activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<MoodleSocialActivity> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the moodle social activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of moodle social activities
	 * @param end the upper bound of the range of moodle social activities (not inclusive)
	 * @return the range of moodle social activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<MoodleSocialActivity> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the moodle social activities.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link de.unipotsdam.elis.activities.model.impl.MoodleSocialActivityModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of moodle social activities
	 * @param end the upper bound of the range of moodle social activities (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of moodle social activities
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<MoodleSocialActivity> findAll(int start, int end,
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

		List<MoodleSocialActivity> list = (List<MoodleSocialActivity>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_MOODLESOCIALACTIVITY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_MOODLESOCIALACTIVITY;

				if (pagination) {
					sql = sql.concat(MoodleSocialActivityModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<MoodleSocialActivity>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<MoodleSocialActivity>(list);
				}
				else {
					list = (List<MoodleSocialActivity>)QueryUtil.list(q,
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
	 * Removes all the moodle social activities from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (MoodleSocialActivity moodleSocialActivity : findAll()) {
			remove(moodleSocialActivity);
		}
	}

	/**
	 * Returns the number of moodle social activities.
	 *
	 * @return the number of moodle social activities
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

				Query q = session.createQuery(_SQL_COUNT_MOODLESOCIALACTIVITY);

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
	 * Initializes the moodle social activity persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.de.unipotsdam.elis.activities.model.MoodleSocialActivity")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<MoodleSocialActivity>> listenersList = new ArrayList<ModelListener<MoodleSocialActivity>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<MoodleSocialActivity>)InstanceFactory.newInstance(
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
		EntityCacheUtil.removeCache(MoodleSocialActivityImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_MOODLESOCIALACTIVITY = "SELECT moodleSocialActivity FROM MoodleSocialActivity moodleSocialActivity";
	private static final String _SQL_SELECT_MOODLESOCIALACTIVITY_WHERE = "SELECT moodleSocialActivity FROM MoodleSocialActivity moodleSocialActivity WHERE ";
	private static final String _SQL_COUNT_MOODLESOCIALACTIVITY = "SELECT COUNT(moodleSocialActivity) FROM MoodleSocialActivity moodleSocialActivity";
	private static final String _SQL_COUNT_MOODLESOCIALACTIVITY_WHERE = "SELECT COUNT(moodleSocialActivity) FROM MoodleSocialActivity moodleSocialActivity WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "moodleSocialActivity.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No MoodleSocialActivity exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No MoodleSocialActivity exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(MoodleSocialActivityPersistenceImpl.class);
	private static Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type", "data"
			});
	private static MoodleSocialActivity _nullMoodleSocialActivity = new MoodleSocialActivityImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<MoodleSocialActivity> toCacheModel() {
				return _nullMoodleSocialActivityCacheModel;
			}
		};

	private static CacheModel<MoodleSocialActivity> _nullMoodleSocialActivityCacheModel =
		new CacheModel<MoodleSocialActivity>() {
			@Override
			public MoodleSocialActivity toEntityModel() {
				return _nullMoodleSocialActivity;
			}
		};
}