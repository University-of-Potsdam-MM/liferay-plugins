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

package de.unipotsdam.elis.portfolio.model.impl;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.portfolio.NoSuchFeedbackException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

/**
 * The extended model implementation for the Portfolio service. Represents a row
 * in the &quot;Portfolio_Portfolio&quot; database table, with each column
 * mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class.
 * Whenever methods are added, rerun ServiceBuilder to copy their definitions
 * into the {@link de.unipotsdam.elis.portfolio.model.Portfolio} interface.
 * </p>
 *
 * @author Matthias
 */
public class PortfolioImpl extends PortfolioBaseImpl {

	private Layout _layout;

	/*
	 * NOTE FOR DEVELOPERS:
	 * 
	 * Never reference this class directly. All methods that expect a portfolio
	 * model instance should use the {@link
	 * de.unipotsdam.elis.portfolio.model.Portfolio} interface instead.
	 */
	public PortfolioImpl() {
	}

	public Layout getLayout() throws PortalException, SystemException {
		if (_layout == null)
			_layout = LayoutLocalServiceUtil.getLayout(getPlid());
		return _layout;
	}

	/**
	 * Publishes a portfolio to a user.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @param userId
	 *            id of the user
	 * @return true if the user was added, else false
	 * @throws PortalException
	 * @throws SystemException
	 */
	public boolean publishToUser(long userId, ServiceContext serviceContext) throws SystemException {
		// TODO: verhindern, dass man sich selbst hinzuf�gen kann
		// TODO: doppelte eintr�ge verhindern
		PortfolioFeedback pf = PortfolioFeedbackLocalServiceUtil
				.addPortfolioFeedback(getPlid(), userId, serviceContext);
		return pf != null;
	}

	/**
	 * Retracts the publishment of a portfolio to a user.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @param userId
	 *            id of the user
	 * @throws NoSuchPermissionException
	 * @throws SystemException
	 * @throws NoSuchFeedbackException
	 */
	public void removeUser(long userId) throws SystemException, NoSuchFeedbackException {
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(getPlid(), userId);
	}

	/**
	 * Returns the users having access to the portfolio with the given plid.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @return list containing the users
	 * @throws PortalException
	 * @throws SystemException
	 */
	public List<User> getUsers() throws PortalException, SystemException {
		// TODO: Nutzer existiert nicht abfangen
		// TODO: Als dynamic query
		List<User> result = new ArrayList<User>();
		List<PortfolioFeedback> pfs = PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlid(getPlid());
		for (PortfolioFeedback pf : pfs) {
			User user = UserLocalServiceUtil.fetchUser(pf.getUserId());
			if (user != null)
				result.add(user);
		}
		return result;
	}

	public List<PortfolioFeedback> getPortfolioFeedbacks() throws SystemException {
		return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlid(getPlid());
	}

	public PortfolioFeedback getPortfolioFeedback(long userId) throws SystemException {
		return PortfolioFeedbackLocalServiceUtil.fetchPortfolioFeedback(getPlid(), userId);
	}

	public boolean userHasViewPermission(long userId) throws SystemException {
		if (this.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL)
			return true;
		PortfolioFeedback pf = PortfolioFeedbackLocalServiceUtil.fetchPortfolioFeedback(getPlid(), userId);
		return pf != null;
	}

	public void setGlobal() throws PortalException, SystemException {
		 PortfolioLocalServiceUtil.updatePortfolio(getPlid(), PortfolioStatics.PUBLISHMENT_GLOBAL);
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByPlidAndFeedbackStatus(getPlid(),
				PortfolioStatics.FEEDBACK_UNREQUESTED);
	}

	public void setPrivate() throws PortalException, SystemException {
		 PortfolioLocalServiceUtil.updatePortfolio(getPlid(), PortfolioStatics.PUBLISHMENT_INDIVIDUAL);
	}

	public void updateFeedbackStatus(long userId, int feedbackStatus) throws NoSuchFeedbackException, SystemException {
		if (feedbackStatus == PortfolioStatics.FEEDBACK_UNREQUESTED) {
			if (getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL) {
				PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(getPlid(), userId);
				return;
			}
		}
		PortfolioFeedbackLocalServiceUtil.updatePortfolioFeedback(getPlid(), userId, feedbackStatus);
	}

	public boolean publishToUserAndRequestFeedback(long userId, ServiceContext serviceContext) throws SystemException {
		// TODO: verhindern, dass man sich selbst hinzuf�gen kann
		// TODO: doppelte eintr�ge verhindern
		PortfolioFeedback pf = PortfolioFeedbackLocalServiceUtil.addPortfolioFeedback(getPlid(), userId,
				PortfolioStatics.FEEDBACK_REQUESTED, serviceContext);
		return pf != null;
	}

	public boolean feedbackRequested(long userId) throws SystemException {
		PortfolioFeedback pf = PortfolioFeedbackLocalServiceUtil.fetchPortfolioFeedback(getPlid(), userId);
		if (pf != null)
			return (pf.getFeedbackStatus() != PortfolioStatics.FEEDBACK_UNREQUESTED);
		return false;
	}
	
	public boolean feedbackRequested() throws SystemException{
		return (PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlidAndFeedbackStatus(getPlid(), PortfolioStatics.FEEDBACK_REQUESTED).size() != 0);
	}
}