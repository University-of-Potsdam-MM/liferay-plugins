<%@ include file="/init.jsp" %>

<%

calendarPage=ParamUtil.getString(request, "calendar-page", calendarPage);
nbEvents=ParamUtil.getString(request, "nb-events", nbEvents);
nbDays=ParamUtil.getString(request, "nb-days", nbDays);

%>
<form action="<liferay-portlet:actionURL portletConfiguration="true" />" method="post" name="<portlet:namespace />fm">
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	
	<aui:input name="calendar-page" label="<%= LanguageUtil.get(pageContext, \"calendar-page\") %>" 
			value="<%= calendarPage %>" size="100" />
	
	<aui:input name="nb-days" label="<%= LanguageUtil.get(pageContext, \"nb-days\") %>" 
			value="<%= nbDays %>" size="100" />
	
	<aui:input name="nb-events" label="<%= LanguageUtil.get(pageContext, \"nb-events\") %>" 
			value="<%= nbEvents %>" size="100" />
	
	<aui:button type="submit"/>
</form>
