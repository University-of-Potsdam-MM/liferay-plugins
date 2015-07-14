<%@page import="de.unipotsdam.elis.portfolio.myportfolio.MyPortfolioPortlet"%>
<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String parentPageRedirect = renderRequest.getParameter("currentRedirect");
%>

<portlet:actionURL name="createPortfolio" var="createPortfolioURL">
	<portlet:param name="mvcPath" value="/html/myportfolio/create_portfolio.jsp" />
	<portlet:param name="redirect" value="<%=parentPageRedirect%>" />
</portlet:actionURL>

<aui:form action="<%=createPortfolioURL.toString()%>" method="post">
	<aui:input id="portfolioNameInput" name="portfolioName" type="text" label="portfolio-name" />
	<aui:field-wrapper name="template" >
		<aui:input checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= MyPortfolioPortlet.WIKI_LAYOUT_PROTOTYPE %>" label="wiki"  />
		<aui:input inlineLabel="right" name="template" type="radio" value="<%= MyPortfolioPortlet.BLOG_LAYOUT_PROTOTYPE %>" label="blog"  />
		<aui:input inlineLabel="right" name="template" type="radio" value="<%= MyPortfolioPortlet.CDP_LAYOUT_PROTOTYPE %>" label="portfolio-cdp"  />
		<aui:input inlineLabel="right" name="template" type="radio" value="<%= MyPortfolioPortlet.EMPTY_LAYOUT_PROTOTYPE %>" label="portfolio-empty-page"  />
	</aui:field-wrapper>
	<aui:button type="submit" value="portfolio-add-portfolio" />
</aui:form> 

