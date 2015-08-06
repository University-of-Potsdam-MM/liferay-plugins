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

package de.unipotsdam.elis.activities.service.impl;

import java.util.List;

import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.service.base.ExtLocalServiceBaseImpl;
import de.unipotsdam.elis.activities.service.persistence.ExtFinderUtil;

/**
 * The implementation of the ext local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.activities.service.ExtLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.activities.service.base.ExtLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.ExtLocalServiceUtil
 */
public class ExtLocalServiceImpl extends ExtLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.unipotsdam.elis.activities.service.ExtLocalServiceUtil} to access the ext local service.
	 */
	
	public List<SocialActivitySet> findSocialActivitySetsByUserIdAndActivityTypes(long userId, int[] activityTypes, int begin, int end){
		return ExtFinderUtil.findSocialActivitySetsByUserIdAndActivityTypes(userId, activityTypes, begin, end);
	}
	
	public int countSocialActivitySetsByUserIdAndActivityTypes(long userId, int[] activityTypes){
		return ExtFinderUtil.countSocialActivitySetsByUserIdAndActivityTypes(userId, activityTypes);
	}
	
	public List<SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(long userId, int[] activityTypes, int begin, int end){
		return ExtFinderUtil.findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId, activityTypes, begin, end);
	}
	
	public int countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(long userId, int[] activityTypes){
		return ExtFinderUtil.countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(userId, activityTypes);
	}
}