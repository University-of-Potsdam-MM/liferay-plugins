<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	Portfolio userPortfolio = (Portfolio) row.getObject();
	PortfolioFeedback pf = userPortfolio.getPortfolioFeedback(themeDisplay.getUserId());
	User owner = UserLocalServiceUtil.getUserById(userPortfolio.getLayout().getUserId());
	String redirect = PortalUtil.getCurrentURL(request);
%>

<% if (pf.getFeedbackStatus() == PortfolioStatics.FEEDBACK_REQUESTED){ %>
	<liferay-ui:icon toolTip="" message="" image="activate" />
<% } if (pf.getFeedbackStatus() == PortfolioStatics.FEEDBACK_DELIVERED){%>
	<liferay-ui:icon toolTip="" message="" image="check" />
<% } %>

<%= JspHelper.getFeedbackStatusString(pageContext, pf.getFeedbackStatus()) %>


<% if (pf.getFeedbackStatus() == PortfolioStatics.FEEDBACK_REQUESTED) { %>

	<portlet:actionURL name="markAsFeedbackDelivered" var="markAsFeedbackDeliveredURL">
		<portlet:param name="portfolioPlid"
			value="<%=String.valueOf(userPortfolio.getPlid())%>" />
		<portlet:param name="redirect" value="<%=redirect%>" />
	</portlet:actionURL>

	<liferay-ui:icon image="submit" url="<%=markAsFeedbackDeliveredURL.toString()%>" /> 
<% } %>