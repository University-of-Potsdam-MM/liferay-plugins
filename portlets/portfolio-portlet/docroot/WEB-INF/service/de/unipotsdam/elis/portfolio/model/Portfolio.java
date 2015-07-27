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

package de.unipotsdam.elis.portfolio.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the Portfolio service. Represents a row in the &quot;Portfolio_Portfolio&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthias
 * @see PortfolioModel
 * @see de.unipotsdam.elis.portfolio.model.impl.PortfolioImpl
 * @see de.unipotsdam.elis.portfolio.model.impl.PortfolioModelImpl
 * @generated
 */
public interface Portfolio extends PortfolioModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.model.Layout getLayout()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Publishes a portfolio to a user.
	*
	* @param plid
	plid of the portfolio
	* @param userId
	id of the user
	* @return true if the user was added, else false
	* @throws PortalException
	* @throws SystemException
	*/
	public boolean publishToUser(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Retracts the publishment of a portfolio to a user.
	*
	* @param plid
	plid of the portfolio
	* @param userId
	id of the user
	* @throws NoSuchPermissionException
	* @throws SystemException
	* @throws NoSuchFeedbackException
	*/
	public void removeUser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException;

	/**
	* Returns the users having access to the portfolio with the given plid.
	*
	* @param plid
	plid of the portfolio
	* @return list containing the users
	* @throws PortalException
	* @throws SystemException
	*/
	public java.util.List<com.liferay.portal.model.User> getUsers()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbacks()
		throws com.liferay.portal.kernel.exception.SystemException;

	public de.unipotsdam.elis.portfolio.model.PortfolioFeedback getPortfolioFeedback(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	public boolean userHasViewPermission(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void setGlobal()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void setPrivate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void updateFeedbackStatus(long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException;

	public boolean publishToUserAndRequestFeedback(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean feedbackRequested(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean feedbackRequested()
		throws com.liferay.portal.kernel.exception.SystemException;
}