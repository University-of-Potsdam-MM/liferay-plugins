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

package de.unipotsdam.elis.custompages.service.impl;

import java.util.Date;
import java.util.List;

import com.liferay.compat.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.base.CustomPageFeedbackLocalServiceBaseImpl;
import de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackFinderUtil;
import de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK;

/**
 * The implementation of the custom page feedback local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.custompages.service.base.CustomPageFeedbackLocalServiceBaseImpl
 * @see de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil
 */
public class CustomPageFeedbackLocalServiceImpl extends CustomPageFeedbackLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.unipotsdam.elis.custompage.service.CustomPageFeedbackLocalServiceUtil}
	 * to access the custom page feedback local service.
	 */

	public List<Layout> getCustomPagesByPageTypeAndLayoutUserId(int pageType, long userId) throws SystemException {
		return CustomPageFeedbackFinderUtil.findCustomPagesByPageTypeAndLayoutUserId(pageType, userId);
	}
	
	public List<Layout> getCustomPagesByLayoutUserId(long userId) throws SystemException {
		return CustomPageFeedbackFinderUtil.findCustomPagesByLayoutUserId(userId);
	}
	
	public List<Layout> getCustomPagesByLayoutUserIdAndCustomPageFeedback(long userId) throws SystemException {
		return CustomPageFeedbackFinderUtil.findCustomPagesByLayoutUserIdAndCustomPageFeedback(userId);
	}
	
	public List<Layout> getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(long userId) throws SystemException {
		return CustomPageFeedbackFinderUtil.findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId);
	}

	public List<Layout> getCustomPagesByPageTypeAndCustomPageFeedbackUserId(int pageType, long userId)
			throws SystemException {
		return CustomPageFeedbackFinderUtil.findCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType, userId);
	}

	public List<Layout> getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(int pageType, long userId) {
		return CustomPageFeedbackFinderUtil.findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType, userId);
	}

	public CustomPageFeedback addCustomPageFeedback(long plid, long userId, int feedbackStatus) throws SystemException {
		ServiceContext serviceContext = new ServiceContext();

		CustomPageFeedback customPageFeedback = customPageFeedbackPersistence.create(new CustomPageFeedbackPK(plid,
				userId));

		Date now = new Date();
		customPageFeedback.setFeedbackStatus(feedbackStatus);
		customPageFeedback.setCreateDate(serviceContext.getCreateDate(now));
		customPageFeedback.setModifiedDate(serviceContext.getModifiedDate(now));

		super.addCustomPageFeedback(customPageFeedback);

		return customPageFeedback;
	}

	public CustomPageFeedback addCustomPageFeedback(long plid, long userId) throws SystemException {

		return addCustomPageFeedback(plid, userId, CustomPageStatics.FEEDBACK_UNREQUESTED);
	}

	public CustomPageFeedback updateCustomPageFeedbackStatus(long plid, long userId, int feedbackStatus)
			throws NoSuchCustomPageFeedbackException, SystemException {
		CustomPageFeedback customPageFeedback = customPageFeedbackPersistence
				.findByPrimaryKey(new CustomPageFeedbackPK(plid, userId));

		customPageFeedback.setFeedbackStatus(feedbackStatus);

		return super.updateCustomPageFeedback(customPageFeedback);
	}

	public CustomPageFeedback deleteCustomPageFeedback(CustomPageFeedback customPageFeedback) throws SystemException {

		return customPageFeedbackPersistence.remove(customPageFeedback);
	}

	public CustomPageFeedback deleteCustomPageFeedback(long plid, long userId) throws SystemException,
	NoSuchCustomPageFeedbackException {

		CustomPageFeedback customPageFeedback = customPageFeedbackPersistence
				.findByPrimaryKey(new CustomPageFeedbackPK(plid, userId));

		return deleteCustomPageFeedback(customPageFeedback);
	}

	public void deleteCustomPageFeedbackByPlid(long plid) throws SystemException {
		customPageFeedbackPersistence.removeByPlid(plid);
	}

	public void deleteCustomPageFeedbackByUserId(long userId) throws SystemException {
		customPageFeedbackPersistence.removeByUserId(userId);
	}

	public void deleteCustomPageFeedbackByPlidAndFeedbackStatus(long plid, int feedbackStatus) throws SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CustomPageFeedback.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.plid").eq(new Long(plid)));
		dynamicQuery.add(PropertyFactoryUtil.forName("feedbackStatus").eq(new Integer(feedbackStatus)));
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.userId").ne(new Long(0)));
		for (CustomPageFeedback customPageFeedback : (List<CustomPageFeedback>) customPageFeedbackPersistence
				.findWithDynamicQuery(dynamicQuery))
			customPageFeedbackPersistence.remove(customPageFeedback);
	}

	public CustomPageFeedback getCustomPageFeedback(long plid, long userId) throws SystemException,
			NoSuchCustomPageFeedbackException {

		return customPageFeedbackPersistence.findByPrimaryKey(new CustomPageFeedbackPK(plid, userId));
	}

	public CustomPageFeedback fetchCustomPageFeedback(long plid, long userId) throws SystemException {
		return customPageFeedbackPersistence.fetchByPrimaryKey(new CustomPageFeedbackPK(plid, userId));
	}

	public List<CustomPageFeedback> getCustomPageFeedbackByPlid(long plid) throws SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CustomPageFeedback.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.plid").eq(new Long(plid)));
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.userId").ne(new Long(0)));
		return customPageFeedbackPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<CustomPageFeedback> getCustomPageFeedbackByUserId(long userId) throws SystemException {

		return customPageFeedbackPersistence.findByUserId(userId);
	}

	public List<CustomPageFeedback> getCustomPageFeedbackByPlidAndFeedbackStatus(long plid, int feedbackStatus)
			throws SystemException {
		return customPageFeedbackPersistence.findByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	public List<Object> getCustomPagePlidsByUserId(long userId) throws SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(CustomPageFeedback.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.userId").eq(new Long(userId)));
		dynamicQuery.setProjection(ProjectionFactoryUtil.property("primaryKey.plid"));
		return customPageFeedbackPersistence.findWithDynamicQuery(dynamicQuery);
	}

	public List<User> getUsersByCustomPageFeedback(long plid) {
		return CustomPageFeedbackFinderUtil.findUsersByCustomPageFeedback(plid);
	}
}