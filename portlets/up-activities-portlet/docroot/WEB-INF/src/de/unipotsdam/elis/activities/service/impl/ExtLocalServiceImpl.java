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

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portlet.social.model.SocialActivity;
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
	
	public List<SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(long userId, String[] classNames, int begin, int end){
		return ExtFinderUtil.findSocialActivitySetsByUserIdAndClassNameIds(userId, getClassNameIds(classNames), begin, end);
	}
	
	public int countSocialActivitySetsByUserIdAndClassNames(long userId, String[] classNames){
		return ExtFinderUtil.countSocialActivitySetsByUserIdAndClassNameIds(userId, getClassNameIds(classNames));
	}
	
	public List<SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(long userId, String[] classNames, int begin, int end){
		return ExtFinderUtil.findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId, getClassNameIds(classNames), begin, end);
	}
	
	public int countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(long userId, String[] classNames){
		return ExtFinderUtil.countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId, getClassNameIds(classNames));
	}
	
	public SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(long userId, long classNameId, long classPK){
		return ExtFinderUtil.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,classNameId,classPK);
		
	}
	
	public List<SocialActivity> findSocialActivitiesByActivitySetIdAndType(long activitySetId, int type){
		return ExtFinderUtil.findSocialActivitiesByActivitySetIdAndType(activitySetId,type);
	}
	
	public List<SocialActivity> findSocialActivitiesByActivitySetId(long activitySetId){
		return ExtFinderUtil.findSocialActivitiesByActivitySetId(activitySetId);
	}
	
	private long[] getClassNameIds(String[] classNames){
		long[] result = new long[classNames.length];
		for (int i = 0; i < result.length; i++)
			result[i] = PortalUtil.getClassNameId(classNames[i]);
		return result;
	}
}