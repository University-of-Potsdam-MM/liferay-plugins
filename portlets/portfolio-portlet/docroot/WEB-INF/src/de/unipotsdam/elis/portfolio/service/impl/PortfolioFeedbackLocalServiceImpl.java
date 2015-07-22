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

package de.unipotsdam.elis.portfolio.service.impl;

import java.util.Date;
import java.util.List;

import com.liferay.compat.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.portfolio.NoSuchFeedbackException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.base.PortfolioFeedbackLocalServiceBaseImpl;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK;

/**
 * The implementation of the portfolio feedback local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioFeedbackLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil
 */
public class PortfolioFeedbackLocalServiceImpl
	extends PortfolioFeedbackLocalServiceBaseImpl {
	public PortfolioFeedback addPortfolioFeedback(long plid, long userId, int feedbackStatus, ServiceContext serviceContext) throws SystemException{

		PortfolioFeedback portfolioPermission = portfolioFeedbackPersistence.create(new PortfolioFeedbackPK(plid,
				userId));
		
		Date now = new Date();
		portfolioPermission.setFeedbackStatus(feedbackStatus);
		portfolioPermission.setCreateDate(serviceContext.getCreateDate(now));
		portfolioPermission.setModifiedDate(serviceContext.getModifiedDate(now));

		super.addPortfolioFeedback(portfolioPermission);

		return portfolioPermission;
	}
	
	public PortfolioFeedback addPortfolioFeedback(long plid, long userId, ServiceContext serviceContext) throws SystemException{

		return addPortfolioFeedback(plid,userId,PortfolioStatics.FEEDBACK_UNREQUESTED,serviceContext);
	}
	
	public PortfolioFeedback updatePortfolioFeedback(long plid, long userId, int feedbackStatus)
			throws SystemException, NoSuchFeedbackException{
		PortfolioFeedback portfolioPermission = portfolioFeedbackPersistence.findByPrimaryKey(new PortfolioFeedbackPK(plid, userId));
		
		portfolioPermission.setFeedbackStatus(feedbackStatus);
		
		return super.updatePortfolioFeedback(portfolioPermission);
	}

	public PortfolioFeedback deletePortfolioFeedback(PortfolioFeedback portfolioPermission)
			throws SystemException {

		return portfolioFeedbackPersistence.remove(portfolioPermission);
	}

	public PortfolioFeedback deletePortfolioFeedback(long plid, long userId) throws SystemException,
			NoSuchFeedbackException {

		PortfolioFeedback portfolioPermission = portfolioFeedbackPersistence
				.findByPrimaryKey(new PortfolioFeedbackPK(plid, userId));

		return deletePortfolioFeedback(portfolioPermission);
	}

	public void deletePortfolioFeedbackByPlid(long plid) throws SystemException {
		portfolioFeedbackPersistence.removeByPlid(plid);
	}

	public void deletePortfolioFeedbackByUserId(long userId) throws SystemException {
		portfolioFeedbackPersistence.removeByUserId(userId);
	}
	
	public void deletePortfolioFeedbackByPlidAndFeedbackStatus(long plid, int feedbackStatus) throws SystemException {
		portfolioFeedbackPersistence.removeByPlidAndFeedbackStatus(plid, feedbackStatus);
	}

	public PortfolioFeedback getPortfolioFeedback(long plid, long userId) throws SystemException, NoSuchFeedbackException {

		return portfolioFeedbackPersistence.findByPrimaryKey(new PortfolioFeedbackPK(plid, userId));
	}

	public PortfolioFeedback fetchPortfolioFeedback(long plid, long userId) throws SystemException {
		return portfolioFeedbackPersistence.fetchByPrimaryKey(new PortfolioFeedbackPK(plid, userId));
	}

	public List<PortfolioFeedback> getPortfolioFeedbackByPlid(long plid) throws SystemException {

		return portfolioFeedbackPersistence.findByPlid(plid);
	}

	public List<PortfolioFeedback> getPortfolioFeedbackByUserId(long userId) throws SystemException {

		return portfolioFeedbackPersistence.findByUserId(userId);
	}
	
	public List<PortfolioFeedback> getPortfolioFeedbackByPlidAndFeedbackStatus(long plid, int feedbackStatus) throws SystemException{
		return portfolioFeedbackPersistence.findByPlidAndFeedbackStatus(plid, feedbackStatus);
	}
	
	public List<Object> getPortfolioPlidsByUserId(long userId) throws SystemException{
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(PortfolioFeedback.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("primaryKey.userId").eq(new Long(userId)));
		dynamicQuery.setProjection(ProjectionFactoryUtil.property("primaryKey.plid"));
		return portfolioFeedbackPersistence.findWithDynamicQuery(dynamicQuery);
	}
}