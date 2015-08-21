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

import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil;
import de.unipotsdam.elis.portfolio.NoSuchPortfolioException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.base.PortfolioLocalServiceBaseImpl;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFinderUtil;
import de.unipotsdam.elis.portfolio.util.jsp.JspHelper;

/**
 * The implementation of the portfolio local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.unipotsdam.elis.portfolio.service.PortfolioLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil
 */
public class PortfolioLocalServiceImpl extends PortfolioLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this interface directly. Always use {@link
	 * de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil} to access
	 * the portfolio local service.
	 */

	public Portfolio addPortfolio(long plid, int publishmentType) throws SystemException {
		Portfolio portfolio = portfolioPersistence.create(plid);
		portfolio.setPublishmentType(publishmentType);
		super.addPortfolio(portfolio);
		return portfolio; 
	}

	public Portfolio addPortfolio(long plid) throws SystemException {
		return addPortfolio(plid, PortfolioStatics.PUBLISHMENT_INDIVIDUAL);
	}

	public Portfolio updatePortfolio(long plid, int publishmentType) throws NoSuchPortfolioException, SystemException {
		Portfolio portfolio = portfolioPersistence.findByPrimaryKey(plid);
		portfolio.setPublishmentType(publishmentType);
		return super.updatePortfolio(portfolio);
	}

	public Portfolio deletePortfolio(long plid) throws SystemException, PortalException {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		//ExtSocialActivitySetLocalServiceUtil.deleteActivitySetsByClassPK(plid);
		JspHelper.createPortfolioActivity(layout, layout.getUserId(),0, ExtendedSocialActivityKeyConstants.PORTFOLIO_DELETED);
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByPlid(plid);
		LayoutLocalServiceUtil.deleteLayout(layout, true, new ServiceContext());
		return portfolioPersistence.remove(plid);
	}

	public List<Portfolio> getPortfoliosByLayoutUserId(long userId) throws SystemException {
		return PortfolioFinderUtil.findByLayoutUserId(userId);
	}

	public List<Portfolio> getPortfoliosByPortfolioFeedbackUserId(long userId) throws SystemException {
		return PortfolioFinderUtil.findByPortfolioFeedbackUserId(userId);
	}

	public List<Portfolio> getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(int publishmentType, long userId)
			throws SystemException {
		return PortfolioFinderUtil.findByPublishmentTypeAndNoPortfolioFeedback(publishmentType, userId);
	}
}