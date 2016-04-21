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

package de.unipotsdam.elis.custompages.model;

import com.liferay.portal.model.PersistedModel;

/**
 * The extended model interface for the CustomPageFeedback service. Represents a row in the &quot;CustomPages_CustomPageFeedback&quot; database table, with each column mapped to a property of this class.
 *
 * @author Matthias
 * @see CustomPageFeedbackModel
 * @see de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackImpl
 * @see de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackModelImpl
 * @generated
 */
public interface CustomPageFeedback extends CustomPageFeedbackModel,
	PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link de.unipotsdam.elis.custompages.model.impl.CustomPageFeedbackImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Layout getCustomPage()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void changeVisibility()
		throws com.liferay.portal.kernel.exception.SystemException;
}