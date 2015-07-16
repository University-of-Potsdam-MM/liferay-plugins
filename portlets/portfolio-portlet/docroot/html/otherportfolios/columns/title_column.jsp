<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	Portfolio userPortfolio = (Portfolio) row.getObject();
	User owner = UserLocalServiceUtil.getUserById(userPortfolio.getLayout().getUserId());
	String redirect = PortalUtil.getCurrentURL(request);
%>

<a href="<%= JspHelper.getPortfolioURL(themeDisplay, userPortfolio.getLayout(), owner) %>"><%= userPortfolio.getLayout().getName(themeDisplay.getLocale())%></a>
(<%= FastDateFormatFactoryUtil.getDate(locale, timeZone).format(userPortfolio.getPortfolioFeedback(themeDisplay.getUserId()).getModifiedDate()) %>)

<portlet:actionURL name="removePortfolioFeedback" var="removePortfolioPermissionURL">
	<portlet:param name="portfolioPlid"
		value="<%=String.valueOf(userPortfolio.getPlid())%>" />
	<portlet:param name="redirect" value="<%=redirect%>" />
</portlet:actionURL>

<liferay-ui:icon-delete url="<%=removePortfolioPermissionURL.toString()%>" /> 
