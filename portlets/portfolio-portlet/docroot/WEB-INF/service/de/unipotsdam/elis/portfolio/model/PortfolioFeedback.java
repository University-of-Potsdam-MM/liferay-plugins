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
 * The extended model interface for the PortfolioFeedback service. Represents a row in the &quot;Portfolio_PortfolioFeedback&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthias
 * @see PortfolioFeedbackModel
 * @see de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackImpl
 * @see de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackModelImpl
 * @generated
 */
public interface PortfolioFeedback extends PortfolioFeedbackModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link de.unipotsdam.elis.portfolio.model.impl.PortfolioFeedbackImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Layout getPortfolio()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}