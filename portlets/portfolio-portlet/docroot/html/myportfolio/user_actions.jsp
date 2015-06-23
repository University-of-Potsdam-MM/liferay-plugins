<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />

<%
	String plid = renderRequest.getParameter("plid");
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	String userName = (String) row.getObject();
	String redirect = PortalUtil.getCurrentURL(renderRequest);
%>

<liferay-ui:icon-menu>

	<portlet:actionURL name="removeUser" var="removeUserURL">
		<portlet:param name="userName" value="<%=userName%>" />
		<portlet:param name="plid" value="<%=plid%>" />
		<portlet:param name="redirect" value="<%=redirect%>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%=removeUserURL.toString()%>" />
</liferay-ui:icon-menu>