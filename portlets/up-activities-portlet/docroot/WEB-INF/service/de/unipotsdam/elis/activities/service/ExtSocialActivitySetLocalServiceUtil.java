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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for ExtSocialActivitySet. This utility wraps
 * {@link de.unipotsdam.elis.activities.service.impl.ExtSocialActivitySetLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see ExtSocialActivitySetLocalService
 * @see de.unipotsdam.elis.activities.service.base.ExtSocialActivitySetLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.impl.ExtSocialActivitySetLocalServiceImpl
 * @generated
 */
public class ExtSocialActivitySetLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.activities.service.impl.ExtSocialActivitySetLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return getService()
				   .findSocialActivitySetsByUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	public static long countSocialActivitySetsByUserIdAndClassNames(
		long userId, java.lang.String[] classNames) {
		return getService()
				   .countSocialActivitySetsByUserIdAndClassNames(userId,
			classNames);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames, int begin, int end) {
		return getService()
				   .findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames, begin, end);
	}

	public static long countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(
		long userId, java.lang.String[] classNames) {
		return getService()
				   .countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(userId,
			classNames);
	}

	public static com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK) {
		return getService()
				   .findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,
			classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type) {
		return getService()
				   .findSocialActivitiesByActivitySetIdAndType(activitySetId,
			type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId) {
		return getService().findSocialActivitiesByActivitySetId(activitySetId);
	}

	public static void deleteActivitySetsByClassPK(long classPK) {
		getService().deleteActivitySetsByClassPK(classPK);
	}

	public static void clearService() {
		_service = null;
	}

	public static ExtSocialActivitySetLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ExtSocialActivitySetLocalService.class.getName());

			if (invokableLocalService instanceof ExtSocialActivitySetLocalService) {
				_service = (ExtSocialActivitySetLocalService)invokableLocalService;
			}
			else {
				_service = new ExtSocialActivitySetLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(ExtSocialActivitySetLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ExtSocialActivitySetLocalService service) {
	}

	private static ExtSocialActivitySetLocalService _service;
}