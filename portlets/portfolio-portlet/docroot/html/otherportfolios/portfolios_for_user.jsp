<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<%
	String filterValue = ParamUtil.getString(request, "filterValue");
	List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByPortfolioFeedbackUserId(themeDisplay.getUserId(), (filterValue == null) ? "" : filterValue, themeDisplay.getLocale()); 
%>

<portlet:actionURL name="filterPortfolios" var="filterPortfoliosURL">
	<portlet:param name="myParam" value="1"/>
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
		
		<liferay-ui:search-container-column-jsp name="portfolio-title-column"
			path="/html/otherportfolios/columns/title_column.jsp"/>
			
		<liferay-ui:search-container-column-jsp name="portfolio-creator-column"
			path="/html/otherportfolios/columns/creator_column.jsp"/>
			
		<liferay-ui:search-container-column-jsp name="portfolio-feedback-column"
			path="/html/otherportfolios/columns/feedback_column.jsp" />
			
		<liferay-ui:search-container-column-jsp name="portfolio-options-column"
			path="/html/otherportfolios/columns/options_column.jsp" />
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>