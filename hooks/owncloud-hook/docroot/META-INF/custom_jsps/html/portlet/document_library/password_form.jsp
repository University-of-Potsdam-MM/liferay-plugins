<%@ include file="/html/portlet/document_library/init.jsp" %>

<liferay-portlet:actionURL varImpl="submitPasswordURL">
	<portlet:param name="struts_action" value="/document_library/view" />
	<portlet:param name="<%= Constants.CMD %>" value="submitPassword" />
</liferay-portlet:actionURL>


<aui:form action="<%= submitPasswordURL.toString() %>" method="post" name="passwordForm" id="passwordForm">
	<aui:input name="struts_action" type="hidden" value="/document_library/view" />
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="submitPassword" />
	<aui:input name="redirect" type="hidden" value="<%= currentURL %>" />
	<aui:input name="password" type="password" />
	
	<aui:button name="submit" type="submit" />
</aui:form>