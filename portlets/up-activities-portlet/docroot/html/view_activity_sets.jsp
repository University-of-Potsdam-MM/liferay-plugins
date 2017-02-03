<%--
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
--%>

<%@page import="de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil"%>
<%@page import="de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil"%>
<%@page
	import="de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants"
%>
<%@ include file="/html/init.jsp"%>

<%
List<SocialActivitySet> results = null; 
//ExtSocialActivityUtil.requestNewMoodleActivities(user.getUserId(), user.getScreenName(), PrincipalThreadLocal.getPassword());

int count = 0;
long total = 0;

int start = ParamUtil.getInteger(request, "start");
int end = start + _DELTA;

while ((count < _DELTA) && ((results == null) || !results.isEmpty())) {
	if (group.isUser()) {
		if (layout.isPrivateLayout()) {
			String[] classNames = new String[]{Layout.class.getName()};
			if (tabs1.equals("my-sites")) {
				results = SocialActivitySetLocalServiceUtil.getUserGroupsActivitySets(group.getClassPK(), start, end);
				total = SocialActivitySetLocalServiceUtil.getUserGroupsActivitySetsCount(group.getClassPK());
			}
			else if (tabs1.equals("custom-pages")) {
				results = ExtSocialActivitySetLocalServiceUtil.findSocialActivitySetsByUserIdAndClassNames(group.getClassPK(), classNames, start, end);
			 	total = ExtSocialActivitySetLocalServiceUtil.countSocialActivitySetsByUserIdAndClassNames(group.getClassPK(), classNames);
			}
			else if (tabs1.equals("moodle")){ 
				results = ExtSocialActivitySetLocalServiceUtil.findSocialActivitySetsByUserIdAndClassNames(user.getUserId(), new String[]{MoodleSocialActivity.class.getName()}, start, end);
				total = ExtSocialActivitySetLocalServiceUtil.countSocialActivitySetsByUserIdAndClassNames(user.getUserId(), new String[]{MoodleSocialActivity.class.getName()});
				//results = MoodleSocialActivityLocalServiceUtil.getMoodleSocialActivities(start, end);
			}
			else { 
				results = ExtSocialActivitySetLocalServiceUtil.findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(group.getClassPK(), classNames, start, end);
				total = ExtSocialActivitySetLocalServiceUtil.countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(group.getClassPK(), classNames);
			}
		}
		else {
	results = SocialActivitySetLocalServiceUtil.getUserActivitySets(group.getClassPK(), start, end);
	total = SocialActivitySetLocalServiceUtil.getUserActivitySetsCount(group.getClassPK());
		}
	}
	else {
		results = SocialActivitySetLocalServiceUtil.getGroupActivitySets(group.getGroupId(), start, end);
		total = SocialActivitySetLocalServiceUtil.getGroupActivitySetsCount(group.getGroupId());
	}
%>

<%@ include file="/html/view_activity_sets_feed.jspf"%>

<%
	end = start + _DELTA;
}
%>

<aui:script>
	<portlet:namespace />start = <%= start %>;
</aui:script>
	


<c:if test="<%=(results.isEmpty())%>">
	<div class="no-activities">
		<c:choose>
			<c:when test="<%=total == 0%>">
				<liferay-ui:message key="there-are-no-activities" />
			</c:when>
			<c:otherwise>
				<liferay-ui:message key="there-are-no-more-activities" />
			</c:otherwise>
		</c:choose>
	</div>
</c:if>