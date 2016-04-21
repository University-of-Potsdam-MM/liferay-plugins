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

package de.unipotsdam.elis.custompages.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

/**
 * The extended model implementation for the CustomPageFeedback service. Represents a row in the &quot;CustomPage_CustomPageFeedback&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.custompages.model.CustomPageFeedback} interface.
 * </p>
 *
 * @author Matthias
 */
public class CustomPageFeedbackImpl extends CustomPageFeedbackBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. All methods that expect a custom page feedback model instance should use the {@link de.unipotsdam.elis.custompage.model.CustomPageFeedback} interface instead.
	 */
	public CustomPageFeedbackImpl() {
	}
	
	private User _user = null;
	private Layout _customPage = null;
	
	public User getUser() throws PortalException, SystemException{
		if (_user == null)
			_user = UserLocalServiceUtil.getUserById(getUserId());
		return _user;
	}
	
	public Layout getCustomPage() throws PortalException, SystemException{
		if (_customPage == null)
			_customPage = LayoutLocalServiceUtil.getLayout(getPlid());
		return _customPage;
	}
	
	public void changeVisibility() throws SystemException{
		setHidden(!isHidden());
		CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedback(this);
	}
}