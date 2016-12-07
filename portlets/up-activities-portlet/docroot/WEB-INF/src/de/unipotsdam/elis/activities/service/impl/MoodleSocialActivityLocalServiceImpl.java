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

package de.unipotsdam.elis.activities.service.impl;

import java.util.List;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.OrderFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

import de.unipotsdam.elis.activities.model.MoodleSocialActivity;
import de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil;
import de.unipotsdam.elis.activities.service.base.MoodleSocialActivityLocalServiceBaseImpl;

/**
 * The implementation of the moodle social activity local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.activities.service.base.MoodleSocialActivityLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil
 */
public class MoodleSocialActivityLocalServiceImpl extends MoodleSocialActivityLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.unipotsdam.elis
	 * .activities.service.MoodleSocialActivityLocalServiceUtil} to access the
	 * moodle social activity local service.
	 */
	
	public MoodleSocialActivity getMostRecentMoodleSocialActivity(long userId) throws SystemException {
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(MoodleSocialActivity.class)
				.add(PropertyFactoryUtil.forName("userId").eq(new Long(userId)))
				.addOrder(OrderFactoryUtil.desc("published"));

		List<?> result = MoodleSocialActivityLocalServiceUtil.dynamicQuery(query, 0, 1);
		if (result.size() > 0)
			return (MoodleSocialActivity) result.get(0);
		return null;
	}

	public MoodleSocialActivity addMoodleSocialActivity(long userId, int type, String extServiceActivityType,
			String extServiceContext, String data, long published) throws PortalException, SystemException {

		long extMoodleActivityId = counterLocalService.increment(MoodleSocialActivity.class.getName());

		MoodleSocialActivity moodleSocialActivity = moodleSocialActivityPersistence.create(extMoodleActivityId);
		moodleSocialActivity.setUserId(userId);
		moodleSocialActivity.setType(type);
		moodleSocialActivity.setExtServiceActivityType(extServiceActivityType);
		moodleSocialActivity.setExtServiceContext(extServiceContext);
		moodleSocialActivity.setData(data);
		moodleSocialActivity.setPublished(published);

		super.addMoodleSocialActivity(moodleSocialActivity);

		return moodleSocialActivity;
	}
}