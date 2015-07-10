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

import com.liferay.compat.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.portfolio.NoSuchPortfolioException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.base.PortfolioLocalServiceBaseImpl;

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
		LayoutLocalServiceUtil.deleteLayout(LayoutLocalServiceUtil.getLayout(plid),true, new ServiceContext());
		return portfolioPersistence.remove(plid);
	}

	public List<Portfolio> getPortfoliosByLayoutUserId(long userId) throws SystemException {
		List<Object> userPlids = LayoutLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(Layout.class)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.setProjection(ProjectionFactoryUtil.property("plid")));

		return PortfolioLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(Portfolio.class).add(
				PropertyFactoryUtil.forName("plid").in(userPlids)));

		// TODO: warum findet der die Klasse Layout nicht bei subquery?
		/*
		 * DynamicQuery dynamicQuery =
		 * DynamicQueryFactoryUtil.forClass(Portfolio.class,
		 * PortalClassLoaderUtil.getClassLoader());
		 * dynamicQuery.add(PropertyFactoryUtil.forName("plid").in(
		 * DynamicQueryFactoryUtil.forClass(Layout.class, "layout",
		 * PortalClassLoaderUtil.getClassLoader())
		 * .add(PropertyFactoryUtil.forName("userId").eq(userId))
		 * .setProjection(ProjectionFactoryUtil.property("plid"))));
		 * 
		 * 
		 * return PortfolioLocalServiceUtil.dynamicQuery(dynamicQuery);
		 */
	}

	public List<Portfolio> getPortfoliosByPortfolioFeedbackUserId(long userId) throws SystemException {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portfolio.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("plid").in(
				DynamicQueryFactoryUtil.forClass(PortfolioFeedback.class)
						.add(PropertyFactoryUtil.forName("primaryKey.userId").eq(userId))
						.setProjection(ProjectionFactoryUtil.property("primaryKey.plid"))));
		return PortfolioLocalServiceUtil.dynamicQuery(dynamicQuery);
	}

	public List<Portfolio> getPortfolioByPublishmentTypeAndNoPortfolioFeedback(int publishmentType, long userId)
			throws SystemException {
		List<Object> userPlids = LayoutLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(Layout.class)
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.setProjection(ProjectionFactoryUtil.property("plid")));
		
		// TODO: warum findet der die Klasse Layout nicht bei subquery?
		
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Portfolio.class);
		dynamicQuery.add(PropertyFactoryUtil.forName("publishmentType").eq(publishmentType));
		dynamicQuery.add(PropertyFactoryUtil.forName("plid").notIn(
				DynamicQueryFactoryUtil.forClass(PortfolioFeedback.class)
						.add(PropertyFactoryUtil.forName("primaryKey.userId").eq(userId))
						.setProjection(ProjectionFactoryUtil.property("primaryKey.plid"))));
		dynamicQuery.add(RestrictionsFactoryUtil.not(PropertyFactoryUtil.forName("plid").in(userPlids)));
		return PortfolioFeedbackLocalServiceUtil.dynamicQuery(dynamicQuery);
	}
}