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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.portfolio.NoSuchPortfolioException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.base.PortfolioLocalServiceBaseImpl;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFinderUtil;

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
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByPlid(plid);
		LayoutLocalServiceUtil.deleteLayout(LayoutLocalServiceUtil.getLayout(plid), true, new ServiceContext());
		return portfolioPersistence.remove(plid);
	}

	public List<Portfolio> getPortfoliosByLayoutUserId(long userId) throws SystemException {
		return PortfolioFinderUtil.findByLayoutUserId(userId);
	}

	public List<Portfolio> getPortfoliosByLayoutUserId(long userId, String titleFilter, Locale locale) throws SystemException, PortalException {
		List<Portfolio> portfolios = PortfolioFinderUtil.findByLayoutUserId(userId);
		return filterPortfolio(portfolios, titleFilter, locale);
	}

	public List<Portfolio> getPortfoliosByPortfolioFeedbackUserId(long userId) throws SystemException {
		return PortfolioFinderUtil.findByPortfolioFeedbackUserId(userId);
	}

	public List<Portfolio> getPortfoliosByPortfolioFeedbackUserId(long userId, String titleFilter, Locale locale)
			throws SystemException, PortalException {
		List<Portfolio> portfolios = PortfolioFinderUtil.findByPortfolioFeedbackUserId(userId, titleFilter);
		return filterPortfolio(portfolios, titleFilter, locale);
	}

	public List<Portfolio> getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(int publishmentType, long userId)
			throws SystemException {
		return PortfolioFinderUtil.findByPublishmentTypeAndNoPortfolioFeedback(publishmentType, userId);
	}

	public List<Portfolio> getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(int publishmentType, long userId,
			String titleFilter, Locale locale) throws SystemException, PortalException {
		List<Portfolio> portfolios = PortfolioFinderUtil.findByPublishmentTypeAndNoPortfolioFeedback(publishmentType, userId, titleFilter);
		return filterPortfolio(portfolios, titleFilter, locale);
	}
	
	private List<Portfolio> filterPortfolio(List<Portfolio> portfolios, String titleFilter, Locale locale) throws PortalException, SystemException{
		List<Portfolio> result = new ArrayList<Portfolio>();
		for (Portfolio p : portfolios){
			if (p.getLayout().getTitle(locale).matches(".*" + titleFilter + ".*"))
				result.add(p);
		}
		return result;
	}
}