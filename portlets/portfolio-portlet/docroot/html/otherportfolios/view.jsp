<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<%
	List<Layout> portfolios = PortfolioManager.getPortfoliosPublishedToCurrentUser(); 
%>

<liferay-ui:search-container delta="10" emptyResultsMessage="portfolio-no-other-portfolios">
	<liferay-ui:search-container-results results="<%=portfolios%>"
		total="<%=portfolios.size()%>" />

	<liferay-ui:search-container-row
		className="com.liferay.portal.model.Layout" keyProperty="layoutId"
		modelVar="portfolio">
		
		<%  	
			User owner = UserLocalServiceUtil.getUserById(portfolio.getUserId());
		%>
		
		<liferay-ui:search-container-column-text name="portfolio-title-column"
			value="<%= portfolio.getName(themeDisplay.getLocale())%>" 
			href="<%= JspHelper.getPortfolioURL(themeDisplay, portfolio, owner) %>"/>

		<liferay-ui:search-container-column-text name="portfolio-owner-column"
			value="<%= owner.getScreenName() %>" />
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>