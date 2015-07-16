<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<%
	String filterValue = ParamUtil.getString(request, "filterValue");
	List<Portfolio> portfolios = 
		PortfolioLocalServiceUtil.getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(PortfolioStatics.PUBLISHMENT_GLOBAL, themeDisplay.getUserId(), filterValue, themeDisplay.getLocale()); 
%>

<portlet:actionURL name="filterPortfolios" var="filterPortfoliosURL">
	<portlet:param name="myParam" value="2"/>
</portlet:actionURL>

<aui:form action="<%= filterPortfoliosURL.toString() %>">
	<aui:input class="filterInput" name="filterValue" label=""></aui:input>
</aui:form>

<liferay-ui:search-container delta="10" emptyResultsMessage="portfolio-no-other-portfolios">
	<liferay-ui:search-container-results results="<%=portfolios%>"
		total="<%=portfolios.size()%>" />

	<liferay-ui:search-container-row
		className="de.unipotsdam.elis.portfolio.model.Portfolio" keyProperty="plid"
		modelVar="portfolio">
		
		<%  	
			User owner = UserLocalServiceUtil.getUserById(portfolio.getLayout().getUserId());
		%>
		
		<liferay-ui:search-container-column-text name="portfolio-title-column"
		value="<%= portfolio.getLayout().getName(themeDisplay.getLocale())%>" href="<%= JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), owner) %>"/>

		<liferay-ui:search-container-column-text name="portfolio-creator-column"
			value="<%= owner.getScreenName() %>" />
			
		<liferay-ui:search-container-column-text name="portfolio-last-changes-column"
			value="<%= FastDateFormatFactoryUtil.getDate(locale, timeZone).format(portfolio.getLayout().getModifiedDate()) %>" /> 
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>