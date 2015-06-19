<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationURL" />

<%
	String template1_cfg = GetterUtil.getString(portletPreferences.getValue("template1", StringPool.UTF8));
	String template2_cfg = GetterUtil.getString(portletPreferences.getValue("template2", StringPool.UTF8));
	String template3_cfg = GetterUtil.getString(portletPreferences.getValue("template3", StringPool.UTF8));
%>


<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
    
	<label for="preferences--template1--">Template 1</label>
    <aui:input name="preferences--template1--" type="text" value="<%= template1_cfg %>" />
    
    <label for="preferences--template2--">Template 2</label>
    <aui:input name="preferences--template2--" type="text" value="<%= template2_cfg %>" />
    
    <label for="preferences--template3--">Template 3</label>
    <aui:input name="preferences--template3--" type="text" value="<%= template3_cfg %>" />

    <aui:button-row>
       <aui:button type="submit" />
    </aui:button-row>
</aui:form>