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

import de.unipotsdam.elis.activities.service.base.ExtSocialActivitySetLocalServiceBaseImpl;
import de.unipotsdam.elis.activities.service.persistence.ExtSocialActivitySetFinderUtil;

/**
 * The implementation of the ext social activity set local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Matthias
 * @see de.unipotsdam.elis.activities.service.base.ExtSocialActivitySetLocalServiceBaseImpl
 * @see de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil
 */
public class ExtSocialActivitySetLocalServiceImpl
	extends ExtSocialActivitySetLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil} to access the ext social activity set local service.
	 */
	
	public List<SocialActivitySet> findSocialActivitySetsByUserIdAndClassNames(long userId, String[] classNames, int begin, int end){
		return ExtSocialActivitySetFinderUtil.findSocialActivitySetsByUserIdAndClassNameIds(userId, getClassNameIds(classNames), begin, end);
	}
	
	public long countSocialActivitySetsByUserIdAndClassNames(long userId, String[] classNames){
		return ExtSocialActivitySetFinderUtil.countSocialActivitySetsByUserIdAndClassNameIds(userId, getClassNameIds(classNames));
	}
	
	public List<SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(long userId, String[] classNames, int begin, int end){
		return ExtSocialActivitySetFinderUtil.findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId, getClassNameIds(classNames), begin, end);
	}
	
	public long countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(long userId, String[] classNames){
		return ExtSocialActivitySetFinderUtil.countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(userId, getClassNameIds(classNames));
	}
	
	public SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(long userId, long classNameId, long classPK){
		return ExtSocialActivitySetFinderUtil.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(userId,classNameId,classPK);
	}
	
	public List<SocialActivity> findSocialActivitiesByActivitySetIdAndType(long activitySetId, int type){
		return ExtSocialActivitySetFinderUtil.findSocialActivitiesByActivitySetIdAndType(activitySetId,type);
	}
	
	public List<SocialActivity> findSocialActivitiesByActivitySetId(long activitySetId){
		return ExtSocialActivitySetFinderUtil.findSocialActivitiesByActivitySetId(activitySetId);
	}
	
	public void deleteActivitySetsByClassPK(long classPK){
		ExtSocialActivitySetFinderUtil.deleteActivitySetsByClassPK(classPK);
	}
	
	private long[] getClassNameIds(String[] classNames){
		long[] result = new long[classNames.length];
		for (int i = 0; i < result.length; i++)
			result[i] = PortalUtil.getClassNameId(classNames[i]);
		return result;
	}
}