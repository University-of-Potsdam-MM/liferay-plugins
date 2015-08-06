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
 * Provides the local service utility for Ext. This utility wraps
 * {@link de.unipotsdam.elis.activities.service.impl.ExtLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Matthias
 * @see ExtLocalService
 * @see de.unipotsdam.elis.activities.service.base.ExtLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.impl.ExtLocalServiceImpl
 * @generated
 */
public class ExtLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link de.unipotsdam.elis.activities.service.impl.ExtLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
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

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return getService()
				   .findSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	public static int countSocialActivitySetsByUserIdAndActivityTypes(
		long userId, int[] activityTypes) {
		return getService()
				   .countSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return getService()
				   .findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	public static int countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes) {
		return getService()
				   .countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes);
	}

	public static void clearService() {
		_service = null;
	}

	public static ExtLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ExtLocalService.class.getName());

			if (invokableLocalService instanceof ExtLocalService) {
				_service = (ExtLocalService)invokableLocalService;
			}
			else {
				_service = new ExtLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(ExtLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ExtLocalService service) {
	}

	private static ExtLocalService _service;
}