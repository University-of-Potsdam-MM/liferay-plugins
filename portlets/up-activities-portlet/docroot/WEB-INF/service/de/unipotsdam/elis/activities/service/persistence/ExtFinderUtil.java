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

package de.unipotsdam.elis.activities.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Matthias
 */
public class ExtFinderUtil {
	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return getFinder()
				   .findSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	public static int countSocialActivitySetsByUserIdAndActivityTypes(
		long userId, int[] activityTypes) {
		return getFinder()
				   .countSocialActivitySetsByUserIdAndActivityTypes(userId,
			activityTypes);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes, int begin, int end) {
		return getFinder()
				   .findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes, begin, end);
	}

	public static int countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(
		long userId, int[] activityTypes) {
		return getFinder()
				   .countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId,
			activityTypes);
	}

	public static ExtFinder getFinder() {
		if (_finder == null) {
			_finder = (ExtFinder)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.activities.service.ClpSerializer.getServletContextName(),
					ExtFinder.class.getName());

			ReferenceRegistry.registerReference(ExtFinderUtil.class, "_finder");
		}

		return _finder;
	}

	public void setFinder(ExtFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ExtFinderUtil.class, "_finder");
	}

	private static ExtFinder _finder;
}