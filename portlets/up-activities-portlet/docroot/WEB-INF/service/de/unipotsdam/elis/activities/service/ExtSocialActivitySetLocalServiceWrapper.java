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
 * Provides a wrapper for {@link ExtSocialActivitySetLocalService}.
 *
 * @author Matthias
 * @see ExtSocialActivitySetLocalService
 * @generated
 */
public class ExtSocialActivitySetLocalServiceWrapper
	implements ExtSocialActivitySetLocalService,
		ServiceWrapper<ExtSocialActivitySetLocalService> {
	public ExtSocialActivitySetLocalServiceWrapper(
		ExtSocialActivitySetLocalService extSocialActivitySetLocalService) {
		_extSocialActivitySetLocalService = extSocialActivitySetLocalService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _extSocialActivitySetLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_extSocialActivitySetLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _extSocialActivitySetLocalService.invokeMethod(name,
			parameterTypes, arguments);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return _extSocialActivitySetLocalService.findSocialActivitySetsByUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	@Override
	public long countSocialActivitySetsByUserIdAndClassNames(long userId,
		java.lang.String[] classNames) {
		return _extSocialActivitySetLocalService.countSocialActivitySetsByUserIdAndClassNames(userId,
			classNames);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return _extSocialActivitySetLocalService.findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	@Override
	public long countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames) {
		return _extSocialActivitySetLocalService.countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames);
	}

	@Override
	public com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK) {
		return _extSocialActivitySetLocalService.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,
			classNameId, classPK);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type) {
		return _extSocialActivitySetLocalService.findSocialActivitiesByActivitySetIdAndType(activitySetId,
			type);
	}

	@Override
	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId) {
		return _extSocialActivitySetLocalService.findSocialActivitiesByActivitySetId(activitySetId);
	}

	@Override
	public void deleteActivitySetsByClassPK(long classPK) {
		_extSocialActivitySetLocalService.deleteActivitySetsByClassPK(classPK);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public ExtSocialActivitySetLocalService getWrappedExtSocialActivitySetLocalService() {
		return _extSocialActivitySetLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedExtSocialActivitySetLocalService(
		ExtSocialActivitySetLocalService extSocialActivitySetLocalService) {
		_extSocialActivitySetLocalService = extSocialActivitySetLocalService;
	}

	@Override
	public ExtSocialActivitySetLocalService getWrappedService() {
		return _extSocialActivitySetLocalService;
	}

	@Override
	public void setWrappedService(
		ExtSocialActivitySetLocalService extSocialActivitySetLocalService) {
		_extSocialActivitySetLocalService = extSocialActivitySetLocalService;
	}

	private ExtSocialActivitySetLocalService _extSocialActivitySetLocalService;
}