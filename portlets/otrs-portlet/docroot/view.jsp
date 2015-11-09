<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.Date"%>
<%@page import="com.liferay.portal.kernel.util.DateUtil"%>
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

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<%
	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
	String formattedDate = dateFormat.format(Calendar.getInstance().getTime());
%>

<portlet:defineObjects />

<portlet:actionURL name="submitForm" var="submitFormURL" >
</portlet:actionURL>

<aui:form action="<%= submitFormURL %>" method="post" name="name">
	<aui:input name="title">
		<aui:validator name="required"></aui:validator>
	</aui:input>
	<aui:input id="date" name="date" value="<%= formattedDate%>"></aui:input>
	<aui:input type="textarea" name="content">
		<aui:validator name="required"></aui:validator>
	</aui:input>
	<aui:select name="priority">
		<aui:option label="very-high" value="5"></aui:option>
		<aui:option label="high" value="4"></aui:option>
		<aui:option label="medium" value="3"></aui:option>
		<aui:option label="low" value="2" selected="true"></aui:option>
		<aui:option label="very-low" value="1" selected="true"></aui:option>
	</aui:select>
	<aui:fieldset label="Queue" >
		<aui:input name="queue" type="radio" label="Junk" value="Junk" checked="true"></aui:input>
		<aui:input name="queue" type="radio" label="Misc" value="Misc"></aui:input>
		<aui:input name="queue" type="radio" label="Postmaster" value="Postmaster"></aui:input>
		<aui:input name="queue" type="radio" label="Raw" value="Raw"></aui:input>
	</aui:fieldset>
	<aui:input type="textarea" name="comment"></aui:input>
	<aui:button type="submit" name="submit"/>
</aui:form>

<aui:script>
AUI().use(
		  'aui-datepicker',
		  function(A) {
		    new A.DatePicker(
		      {
		        trigger : '#<portlet:namespace />date',
		        popover: {
		          zIndex: 1
		        },
		        on: {
		          selectionChange: function(event) {
		            console.log(event.newSelection)
		          }
		        }
		      }
		    );
		  }
		);
</aui:script>
