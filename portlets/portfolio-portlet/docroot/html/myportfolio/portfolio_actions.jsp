<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	long portfolioPlid = ((Layout) row.getObject()).getPlid();
	String redirect = PortalUtil.getCurrentURL(renderRequest);
	Layout portfolio = LayoutLocalServiceUtil.getLayout(portfolioPlid); 
%>

<liferay-ui:icon-menu>

	<portlet:actionURL name="deletePortfolio" var="deletePortfolioURL">
		<portlet:param name="portfolioPlid"
			value="<%=String.valueOf(portfolioPlid)%>" />
		<portlet:param name="redirect" value="<%=redirect%>" />
	</portlet:actionURL>

	<liferay-ui:icon-delete url="<%=deletePortfolioURL.toString()%>" />

	<liferay-ui:icon image="edit"
		url="<%=JspHelper.getPortfolioURL(themeDisplay, portfolio, themeDisplay.getUser())%>"></liferay-ui:icon>
</liferay-ui:icon-menu>