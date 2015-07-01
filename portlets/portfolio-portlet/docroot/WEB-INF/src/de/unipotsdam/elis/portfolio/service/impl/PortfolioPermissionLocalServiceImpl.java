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

import com.liferay.portal.kernel.exception.SystemException;

import de.unipotsdam.elis.portfolio.NoSuchPermissionException;
import de.unipotsdam.elis.portfolio.model.PortfolioPermission;
import de.unipotsdam.elis.portfolio.service.base.PortfolioPermissionLocalServiceBaseImpl;
import de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK;

/**
 * The implementation of the portfolio permission local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * {@link de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalService}
 * interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security
 * checks based on the propagated JAAS credentials because this service can only
 * be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.base.PortfolioPermissionLocalServiceBaseImpl
 * @see de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalServiceUtil
 */
public class PortfolioPermissionLocalServiceImpl extends PortfolioPermissionLocalServiceBaseImpl {
	public PortfolioPermission addPortfolioPermission(long plid, long userId) throws SystemException {

		PortfolioPermission portfolioPermission = portfolioPermissionPersistence.create(new PortfolioPermissionPK(plid,
				userId));

		super.addPortfolioPermission(portfolioPermission);

		return portfolioPermission;
	}

	public PortfolioPermission deletePortfolioPermission(PortfolioPermission portfolioPermission)
			throws SystemException {

		return portfolioPermissionPersistence.remove(portfolioPermission);
	}

	public PortfolioPermission deletePortfolioPermission(long plid, long userId) throws SystemException,
			NoSuchPermissionException {

		PortfolioPermission portfolioPermission = portfolioPermissionPersistence
				.findByPrimaryKey(new PortfolioPermissionPK(plid, userId));

		return deletePortfolioPermission(portfolioPermission);
	}

	public void deletePortfolioPermissionByPlid(long plid) throws SystemException {
		portfolioPermissionPersistence.removeByPlid(plid);
	}

	public void deletePortfolioPermissionByUserId(long userId) throws SystemException {
		portfolioPermissionPersistence.removeByUserId(userId);
	}

	public PortfolioPermission getPortfolioPermission(long plid, long userId) throws NoSuchPermissionException,
			SystemException {

		return portfolioPermissionPersistence.findByPrimaryKey(new PortfolioPermissionPK(plid, userId));
	}

	public PortfolioPermission fetchPortfolioPermission(long plid, long userId) throws SystemException {
		return portfolioPermissionPersistence.fetchByPrimaryKey(new PortfolioPermissionPK(plid, userId));
	}

	public List<PortfolioPermission> getPortfolioPermissionByPlid(long plid) throws SystemException {

		return portfolioPermissionPersistence.findByPlid(plid);
	}

	public List<PortfolioPermission> getPortfolioPermissionByUserId(long userId) throws SystemException {

		return portfolioPermissionPersistence.findByUserId(userId);
	}

}