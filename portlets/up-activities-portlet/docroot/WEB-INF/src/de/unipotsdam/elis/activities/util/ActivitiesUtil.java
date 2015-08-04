/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */

package de.unipotsdam.elis.activities.util;

import java.util.List;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

/**
 * @author Matthew Kong
 */
public class ActivitiesUtil {

	public static Object[] getCommentsClassNameAndClassPK(SocialActivitySet activitySet) throws Exception {

		String className = activitySet.getClassName();
		long classPK = activitySet.getClassPK();

		if (className.equals(DLFileEntry.class.getName()) && (activitySet.getActivityCount() > 1)
				&& (activitySet.getType() == 1)) {

			className = SocialActivitySet.class.getName();
			classPK = activitySet.getActivitySetId();
		}

		return new Object[] { className, classPK };
	}

	public static List<SocialActivity> getSocialActivityByType(long userId, int activityType, int start, int end)
			throws SystemException {
		return SocialActivityLocalServiceUtil.dynamicQuery(getDynamicQueryByTypeCount(userId, activityType), start, end);
	}
	
	public static long getSocialActivityByTypeCount(long userId, int activityType)
			throws SystemException {
		return SocialActivityLocalServiceUtil.dynamicQueryCount(getDynamicQueryByTypeCount(userId, activityType));
	}
	
	private static DynamicQuery getDynamicQueryByTypeCount(long userId, int activityType){
		return DynamicQueryFactoryUtil.forClass(SocialActivity.class,(ClassLoader)PortletBeanLocatorUtil.locate("so-portlet","portletClassLoader"))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("type").eq(activityType));
	}

}