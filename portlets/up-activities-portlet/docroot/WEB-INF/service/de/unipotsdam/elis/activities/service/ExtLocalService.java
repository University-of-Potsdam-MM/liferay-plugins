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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service interface for Ext. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Matthias
 * @see ExtLocalServiceUtil
 * @see de.unipotsdam.elis.activities.service.base.ExtLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.impl.ExtLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ExtLocalService extends BaseLocalService, InvokableLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ExtLocalServiceUtil} to access the ext local service. Add custom service methods to {@link de.unipotsdam.elis.activities.service.impl.ExtLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end);

	public long countSocialActivitySetsByUserIdAndClassNames(long userId,
		java.lang.String[] classNames);

	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end);

	public long countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames);

	public com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK);

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type);

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId);
}