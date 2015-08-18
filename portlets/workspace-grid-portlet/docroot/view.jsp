<%
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
%>

<%@ include file="/init.jsp" %>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String template1_cfg = GetterUtil.getString(portletPreferences.getValue("template1", StringPool.UTF8));
		String template2_cfg = GetterUtil.getString(portletPreferences.getValue("template2", StringPool.UTF8));
		String template3_cfg = GetterUtil.getString(portletPreferences.getValue("template3", StringPool.UTF8));
	
		List<WorkspaceSlide> workspaceSlides = new LinkedList<WorkspaceSlide>();
		List<Group> gruppenArbeiten = null;				
		
		try {
			gruppenArbeiten = WorkspaceUtilService.getAllGroupworkSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	
		//System.out.println(gruppenArbeiten);	
		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, gruppenArbeiten, template1_cfg);
		List<Group> portfolios = null;		
	
		try {
			portfolios = WorkspaceUtilService.getAllPortfolioSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
	
		//System.out.println(portfolios);	
		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, portfolios, template2_cfg);
		List<Group> courses = null;		
		
		try {
			courses = WorkspaceUtilService.getAllCourseSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		//System.out.println(courses);		
		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, courses, template3_cfg);
		List<Group> other = null;		
		
		try {
			other = WorkspaceUtilService.getOtherSites();
		} catch (PortalException e1) {
			e1.printStackTrace();
		} catch (SystemException e1) {
			e1.printStackTrace();
		}
		
		//System.out.println(other);		
		GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, other, template3_cfg);
		
		%>
		<div id="workspacegrid">
			<%
			for (WorkspaceSlide workspaceSlide: workspaceSlides){
				// System.out.println(workspaceSlide);
			%>
				
				<a class="workspaceBox <%= workspaceSlide.getTemplateName() %>" href="<%= workspaceSlide.getWebLink() %>">
					<span class="linkName"><%= workspaceSlide.getName() %></span>
					<% if (workspaceSlide.getNumberOfNewActivities()!=0){ %>
					<span class="numberOfActivities"><%= workspaceSlide.getNumberOfNewActivities() %></span>
					<% } %> 
				</a>
			
			<%
			}
			%>
			<div style="clear:left;" />
		</div>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
</c:choose>