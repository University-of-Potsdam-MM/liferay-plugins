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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<portlet:defineObjects />

This is the <b>Test1</b> portlet.


<%@ include file="/init.jsp" %>
<%
	/*
	*	Template CSS Classes
	*/
	String template1Name = "template1";
	String template2Name = "template2";
	String template3Name = "template3";
	
	List<WorkspaceSlide> workspaceSlides = new LinkedList<WorkspaceSlide>();
	
	List<Group> gruppenArbeiten = null;				
	
	try {
		gruppenArbeiten = WorkspaceUtilService.getAllGroupworkSites();
	} catch (PortalException e1) {
		e1.printStackTrace();
	} catch (SystemException e1) {
		e1.printStackTrace();
	}

	System.out.println(gruppenArbeiten);	
	GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, gruppenArbeiten, template1Name);
	
	
	List<Group> portfolios = null;		

	try {
		portfolios = WorkspaceUtilService.getAllPortfolioSites();
	} catch (PortalException e1) {
		e1.printStackTrace();
	} catch (SystemException e1) {
		e1.printStackTrace();
	}

	System.out.println(portfolios);	
	GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, portfolios, template2Name);
	
	
	List<Group> courses = null;		
	
	try {
		courses = WorkspaceUtilService.getAllCourseSites();
	} catch (PortalException e1) {
		e1.printStackTrace();
	} catch (SystemException e1) {
		e1.printStackTrace();
	}
	
	System.out.println(courses);		
	GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, courses, template3Name);
	
	List<Group> other = null;		
	
	try {
		other = WorkspaceUtilService.getOtherSites();
	} catch (PortalException e1) {
		e1.printStackTrace();
	} catch (SystemException e1) {
		e1.printStackTrace();
	}
	
	System.out.println(other);		
	GroupToSlideConverter.convertGroupsToSlides(workspaceSlides, other, template3Name);
	
	
	%>
	<div id="workspacegrid">
		<%
		for (WorkspaceSlide workspaceSlide: workspaceSlides){
			
			System.out.println(workspaceSlide);
		%>
		
		
			
			<a class="workspaceBox <%= workspaceSlide.getTemplateName() %>" href="<%= workspaceSlide.getWebLink() %>">
				<span class="linkName"><%= workspaceSlide.getName() %></span>
				<span class="numberOfActivities"><%= workspaceSlide.getNumberOfNewActivities() %></span> 
			</a>
		
		<%
		}
		%>
		<div style="clear:left;">
	</div>	