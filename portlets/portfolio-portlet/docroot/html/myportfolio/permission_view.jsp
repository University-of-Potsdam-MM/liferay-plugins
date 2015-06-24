<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String portfolioPlid = renderRequest.getParameter("plid");
	Layout portfolio = LayoutLocalServiceUtil.getLayout(Long.valueOf(portfolioPlid));
	List<String> users = PortfolioManager.getUserOfPortfolio(portfolio.getPlid()); 
	String redirect = PortalUtil.getCurrentURL(renderRequest);
%>

<portlet:actionURL name="publishPortfolioToUser" var="publishPortfolioToUserURL">
	<portlet:param name="mvcPath" value="/html/myportfolio/permission_view.jsp" />
	<portlet:param name="plid" value="<%=portfolioPlid%>" />
	<portlet:param name="redirect" value="<%=redirect%>" />
</portlet:actionURL>

<portlet:resourceURL var="getUsers">
	<portlet:param name="<%=Constants.CMD %>" value="get_users" />
</portlet:resourceURL>

<liferay-ui:error key="error-not-a-user" message="portfolio-error-not-a-user"></liferay-ui:error>
<liferay-ui:error key="error" message="portfolio-error"></liferay-ui:error>


<aui:form action="<%=publishPortfolioToUserURL.toString()%>" method="post">
	<!-- browser autocomplete deaktivieren -->
	<aui:input id="userNameInput" name="userName" type="text" label="model.resource.com.liferay.portal.model.User" />
	<aui:button type="submit" value="action.ADD_USER" />
</aui:form>

<liferay-ui:search-container>
	<liferay-ui:search-container-results results="<%=users%>" total="<%=users.size()%>" />
	<liferay-ui:search-container-row className="java.lang.String" modelVar="userName">
		<liferay-ui:search-container-column-text name="User" value="<%=userName%>" />
		<liferay-ui:search-container-column-jsp align="right" path="/html/myportfolio/user_actions.jsp" />
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script>


AUI().use('autocomplete-list','aui-base','aui-io-request','autocomplete-filters','autocomplete-highlighters', function (A) {
	var contactSearchFormatter = function (query, results) {
		return A.Array.map(results, function (result) {
	// For debugging: console.debug(result.raw);
			return '<strong>'+result.raw.screenName+'</strong><br/>'+result.raw.email;
		});
	};
	var testData;
	new A.AutoCompleteList({
	allowBrowserAutocomplete: 'true',
	activateFirstItem: 'true',
	inputNode: '#<portlet:namespace />userNameInput',
	resultTextLocator:'screenName',
	render: 'true',
	resultHighlighter: 'phraseMatch',
	resultFilters:['phraseMatch'],
	resultFormatter: contactSearchFormatter,
	source:function(){
		var inputValue=A.one("#<portlet:namespace />userNameInput").get('value');
		var myAjaxRequest=A.io.request('<%=getUsers.toString()%>',{
			    dataType: 'json',
				method:'POST',
				data:{
				<portlet:namespace />userEmail:inputValue,
				},
				autoLoad:false,
				sync:false,
			    on: {
	 				 success:function(){
	 					var data=this.get('responseData');
	 					testData=data;
	 				}}
		});
		myAjaxRequest.start();
		return testData;},
	});
});
</aui:script>