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
	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNameIds(
		long userId, long[] classNameIds, int begin, int end) {
		return getFinder()
				   .findSocialActivitySetsByUserIdAndClassNameIds(userId,
			classNameIds, begin, end);
	}

	public static int countSocialActivitySetsByUserIdAndClassNameIds(
		long userId, long[] classNameIds) {
		return getFinder()
				   .countSocialActivitySetsByUserIdAndClassNameIds(userId,
			classNameIds);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(
		long userId, long[] classNameIds, int begin, int end) {
		return getFinder()
				   .findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId,
			classNameIds, begin, end);
	}

	public static int countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(
		long userId, long[] classNameIds) {
		return getFinder()
				   .countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId,
			classNameIds);
	}

	public static com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK) {
		return getFinder()
				   .findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,
			classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type) {
		return getFinder()
				   .findSocialActivitiesByActivitySetIdAndType(activitySetId,
			type);
	}

	public static java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId) {
		return getFinder().findSocialActivitiesByActivitySetId(activitySetId);
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