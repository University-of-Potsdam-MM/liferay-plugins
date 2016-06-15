<%@ include file="/html/portlet/document_library/init.jsp" %>

<liferay-portlet:actionURL varImpl="submitPasswordURL">
	<portlet:param name="struts_action" value="/document_library/view" />
	<portlet:param name="<%= Constants.CMD %>" value="submitPassword" />
</liferay-portlet:actionURL>

<aui:input name="struts_action" type="hidden" value="/document_library/view" />
<aui:input name="<%= Constants.CMD %>" type="hidden" value="submitPassword" />
<aui:input name="password" type="password" />

<aui:button name="submit" type="submit" />

<aui:script>
AUI().use('aui-base',function(A){
	// the method and the action of the ancestor form (in view.jsp) has to be overwritten 
	var ancestorForm = A.one('#<portlet:namespace />password').ancestor('#<portlet:namespace />fm2');
	ancestorForm.set('method','post');
	ancestorForm.set('action','<%= submitPasswordURL.toString() %>');
});
</aui:script>