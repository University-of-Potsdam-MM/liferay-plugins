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

package de.unipotsdam.elis.activities.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ExtLocalService}.
 *
 * @author Matthias
 * @see ExtLocalService
 * @generated
 */
public class ExtLocalServiceWrapper implements ExtLocalService,
	ServiceWrapper<ExtLocalService> {
	public ExtLocalServiceWrapper(ExtLocalService extLocalService) {
		_extLocalService = extLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _extLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_extLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _extLocalService.invokeMethod(name, parameterTypes, arguments);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return _extLocalService.findSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	@Override
	public int countSocialActivitySetsByUserIdAndActivityTypes(long userId,
		int[] activityTypes) {
		return _extLocalService.countSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return _extLocalService.findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	@Override
	public int countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes) {
		return _extLocalService.countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ExtLocalService getWrappedExtLocalService() {
		return _extLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedExtLocalService(ExtLocalService extLocalService) {
		_extLocalService = extLocalService;
	}

	@Override
	public ExtLocalService getWrappedService() {
		return _extLocalService;
	}

	@Override
	public void setWrappedService(ExtLocalService extLocalService) {
		_extLocalService = extLocalService;
	}

	private ExtLocalService _extLocalService;
}