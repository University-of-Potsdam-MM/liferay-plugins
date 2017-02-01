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
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

/**
 * @author Matthew Kong
 */
public class ActivitiesUtil {

	/**
	 * This method returns the userPortraitURL of the corresponding campus.UP user to a given Moodle actor id.
	 * If no corresponding user is found, the default male portrait is returned.
	 * @param data : JsonObject that contains moodle data
	 * @param themeDisplay
	 * @return
	 */
	public static String getMoodleUserPortraitURL (JSONObject data, ThemeDisplay themeDisplay) {
		String loginName = data.getJSONObject("actor").getString("id");

		User activitySetUser = null;
		String userPortraitURL = StringPool.BLANK;
		
		try {
			activitySetUser = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), loginName);
			if (activitySetUser != null) {
				userPortraitURL = activitySetUser.getPortraitURL(themeDisplay);
				
			} else {
				
				userPortraitURL = "/image/user_male_portrait";
			}
		} catch (Exception e) {}
		
		return userPortraitURL;
	}
	
	private static boolean isClassNameValueContaining(long classNameId, String s) {
		
		String className = PortalUtil.getClassName(classNameId);
		
		if (className.contains(s))
			return true;
		
		return false;
	}
	
	/**
	 * Get css class for campus.UP activities.
	 * @param classNameId
	 * @return
	 */
	public static String getActivityCssClass (long classNameId) {
		
		if (ActivitiesUtil.isClassNameValueContaining(classNameId, "TasksEntry"))
			return "task";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "Calendar")) 
			return "calendar";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "Journal"))
			return "text";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "BlogsEntry")) // contains is case sensitive so MircoblogsEntry should not be a problem here
			return "blog";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "DLFile"))
			return "upload";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "BookmarksEntry"))
			return "bookmark";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "Polls"))
			return "poll";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "WikiPage"))
			return "wiki";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "MBDiscussion")) // comments are illustrated as forum discussions
			return "comment";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "MicroblogsEntry")) 
			return "microblog";
		else if (ActivitiesUtil.isClassNameValueContaining(classNameId, "MBMessage") 
				|| (ActivitiesUtil.isClassNameValueContaining(classNameId, "MBThread")))
			return "forum";
		
		return "";
	}
	
	/**
	 * Get css class for moodle activities
	 * @param moodleSocialActivityType
	 * @return
	 */
	public static String getMoodleSocialActivityCssClass (String moodleSocialActivityType) {
		
		// some replacements to group or rename moodle activities
		if (moodleSocialActivityType.equals("assign"))
			moodleSocialActivityType = "task";
		if (moodleSocialActivityType.equals("scheduler"))
			moodleSocialActivityType = "calender";
		if ((moodleSocialActivityType.equals("survey")) || (moodleSocialActivityType.equals("choice")))
			moodleSocialActivityType = "poll";
		if (moodleSocialActivityType.equals("workshop"))
			moodleSocialActivityType = "feedback";
		if (moodleSocialActivityType.equals("scrom"))
			moodleSocialActivityType = "packet";
		if (moodleSocialActivityType.equals("adobeconnect"))
			moodleSocialActivityType = "adobe";
		if (moodleSocialActivityType.equals("lesson"))
			moodleSocialActivityType = "lecture";
		if (moodleSocialActivityType.equals("quiz"))
			moodleSocialActivityType = "test";
		
		return moodleSocialActivityType;
	}
	
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
		return SocialActivityLocalServiceUtil.dynamicQuery(getDynamicQueryforSocialActivity(userId, activityType),
				start, end);
	}

	public static long getSocialActivityByTypeCount(long userId, int activityType) throws SystemException {
		return SocialActivityLocalServiceUtil.dynamicQueryCount(getDynamicQueryforSocialActivity(userId, activityType));
	}

	private static DynamicQuery getDynamicQueryforSocialActivity(long userId, int activityType) {
		return DynamicQueryFactoryUtil
				.forClass(SocialActivity.class,
						(ClassLoader) PortletBeanLocatorUtil.locate("so-portlet", "portletClassLoader"))
				.add(PropertyFactoryUtil.forName("userId").eq(userId))
				.add(PropertyFactoryUtil.forName("type").eq(activityType));
	}

}