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

/**
 * @author Matthias
 */
public interface ExtSocialActivitySetFinder {
	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserIdAndClassNameIds(
		long userId, long[] classNameIds, int begin, int end);

	public long countSocialActivitySetsByUserIdAndClassNameIds(long userId,
		long[] classNameIds);

	public java.util.List<com.liferay.portlet.social.model.SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(
		long userId, long[] classNameIds, int begin, int end);

	public long countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(
		long userId, long[] classNameIds);

	public com.liferay.portlet.social.model.SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
		long userId, long classNameId, long classPK);

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetIdAndType(
		long activitySetId, int type);

	public java.util.List<com.liferay.portlet.social.model.SocialActivity> findSocialActivitiesByActivitySetId(
		long activitySetId);

	public void deleteActivitySetsByClassPK(long classPK);
}