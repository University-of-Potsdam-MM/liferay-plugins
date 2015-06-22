<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String redirect = PortalUtil.getCurrentURL(renderRequest);
%>

<portlet:actionURL name="createPortfolio" var="createPortfolioURL">
	<portlet:param name="mvcPath" value="/html/myportfolio/create_portfolio.jsp" />
	<portlet:param name="redirect" value="<%=redirect%>" />
</portlet:actionURL>

<aui:form action="<%=createPortfolioURL.toString()%>" method="post">
	<aui:input id="portfolioNameInput" name="portfolioName" type="text" label="portfolio-name" />
	<aui:button type="submit" value="portfolio-add-portfolio" />
</aui:form>