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
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return _extLocalService.findSocialActivitySetsByUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	@Override
	public long countSocialActivitySetsByUserIdAndClassNames(long userId,
		java.lang.String[] classNames) {
		return _extLocalService.countSocialActivitySetsByUserIdAndClassNames(userId,
			classNames);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return _extLocalService.findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	@Override
	public long countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames) {
		return _extLocalService.countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK) {
		return _extLocalService.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,
			classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type) {
		return _extLocalService.findSocialActivitiesByActivitySetIdAndType(activitySetId,
			type);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId) {
		return _extLocalService.findSocialActivitiesByActivitySetId(activitySetId);
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