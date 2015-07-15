

<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects /> 

<%
	String filterValue = ParamUtil.getString(request, "filterValue");
	List<Portfolio> userPortfolios = 
			PortfolioLocalServiceUtil.getPortfoliosByLayoutUserId(themeDisplay.getUserId(), (filterValue == null) ? "" : filterValue, themeDisplay.getLocale()); 
	String redirect = PortalUtil.getCurrentURL(renderRequest);  
	
%>
<portlet:actionURL name="filterPortfolios" var="filterPortfoliosURL">
</portlet:actionURL>

<aui:button id="createPageButton" name="createPageButton" type="button" value="portfolio-create-page" />

<!-- TODO: filter dynamisch machen (filtern ohne Enter drücken zu müssen) -->
<aui:form action="<%= filterPortfoliosURL.toString() %>">
	<aui:input class="filterInput" name="filterValue" label=""></aui:input>
</aui:form>
<!--  <div id="edittest">test</div>-->

<table id="test" class="aui table table-striped table-bordered">
    <thead>
        <tr>
            <th id="titel"><%= LanguageUtil.get(pageContext, "portfolio-title-column")%> &nbsp; &nbsp;  <liferay-ui:icon image="../arrows/09_down"/><liferay-ui:icon image="../arrows/09_up"/></th>
            <th id="publishment"><%= LanguageUtil.get(pageContext, "portfolio-publishment-column")%></th>
            <th id="feedback"><%= LanguageUtil.get(pageContext, "portfolio-feedback-column")%></th>
            <th id="changes"><%= LanguageUtil.get(pageContext, "portfolio-changes-column")%></th>
        </tr>
    </thead>
    
    <tbody>
	    <% 	for (Portfolio userPortfolio : userPortfolios){
	    	boolean isGlobal = userPortfolio.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL;
	    	List<PortfolioFeedback> pfs = userPortfolio.getPortfolioFeedbacks(); %>
	    	<tr>
	    	
	    		<td rowspan ="<%= pfs.size() + 1%>">
	    			<a href="<%=JspHelper.getPortfolioURL(themeDisplay, userPortfolio.getLayout(), themeDisplay.getUser()) %>">
	    				<%= userPortfolio.getLayout().getName(themeDisplay.getLocale())%>
					</a>
					<liferay-ui:icon-menu>
	
						<portlet:actionURL name="deletePortfolio" var="deletePortfolioURL">
							<portlet:param name="portfolioPlid"
								value="<%=String.valueOf(userPortfolio.getPlid())%>" />
							<portlet:param name="redirect" value="<%=redirect%>" />
						</portlet:actionURL>
	
						<liferay-ui:icon-delete url="<%=deletePortfolioURL.toString()%>" />
						
				
						<liferay-ui:icon image="edit"
							url="<%=JspHelper.getPortfolioURL(themeDisplay, userPortfolio.getLayout(), themeDisplay.getUser())%>"></liferay-ui:icon>
					</liferay-ui:icon-menu>
				</td>
				
				<% if (isGlobal){ %>
	    		
					<td>
						<%=LanguageUtil.get(pageContext, "portfolio-portalwide-publishment") %>
						
						<portlet:actionURL name="deleteGlobalPublishment" var="deleteGlobalPublishmentURL"> 
							<portlet:param name="portfolioPlid"
								value="<%=String.valueOf(userPortfolio.getPlid())%>" />
							<portlet:param name="redirect" value="<%=redirect%>" />
						</portlet:actionURL>
				
						<liferay-ui:icon-delete url="<%=deleteGlobalPublishmentURL.toString()%>" />
						
					</td>
						
					<td>
						<a href="#" class="feedbackPopUpLink"><%= LanguageUtil.get(pageContext, "portfolio-request-feedback")%>
							<input id="portfolioPlid" hidden="true" value="<%=userPortfolio.getPlid()%>"/> 
						</a>						
					</td>
					
					
				<% } else {%>
				
					<td>
		    			<a href="#" class="popUpLink"><%= LanguageUtil.get(pageContext, "portfolio-setup-publishment")%>
							<input id="portfolioPlid" hidden="true" value="<%=userPortfolio.getPlid()%>"/> 
						</a>
					</td>
					
					<td>
					</td>
					
				<% }%>	
								
				<td rowspan ="<%= (pfs.size() + 1)%>">
					<%= FastDateFormatFactoryUtil.getDate(locale, timeZone).format(userPortfolio.getLayout().getModifiedDate()) %>
				</td>
				
	    	</tr>
	    	
    		<% for (PortfolioFeedback pp : pfs) {%>
    			<tr>    	
    				<td>
		    			<%= pp.getUser().getScreenName() %> (<%= FastDateFormatFactoryUtil.getDate(locale, timeZone).format(pp.getCreateDate()) %>)
		    			
		    			<portlet:actionURL name="deletePublishment" var="deletePublishmentURL">
							<portlet:param name="userName"
								value="<%=pp.getUser().getScreenName()%>" />
							<portlet:param name="portfolioPlid"
								value="<%=String.valueOf(userPortfolio.getPlid())%>" />
							<portlet:param name="redirect" value="<%=redirect%>" />
						</portlet:actionURL>
						
						<% if (!userPortfolio.feedbackRequested(pp.getUserId())) { %>
							<liferay-ui:icon-delete url="<%=deletePublishmentURL.toString()%>" />
						<% } %>
						
					</td>
					
					<td>
						<% if (pp.getFeedbackStatus() == 0){ %>
						   
							 <portlet:actionURL name="requestFeedbackFromUser" var="requestFeedbackURL">
								<portlet:param name="portfolioPlid" value="<%=String.valueOf(userPortfolio.getPlid())%>" />
								<portlet:param name="userId" value="<%=String.valueOf(pp.getUser().getUserId())%>" />
								<portlet:param name="redirect" value="<%=redirect%>" />
							</portlet:actionURL>
							<a href="<%=requestFeedbackURL.toString()%>"><%=LanguageUtil.get(pageContext, "portfolio-request-feedback")%></a> 
							
						<% } else { %>
						
							<portlet:actionURL name="removeFeedbackForPortfolio" var="removeFeedbackURL">
								<portlet:param name="portfolioPlid" value="<%=String.valueOf(userPortfolio.getPlid())%>" />
								<portlet:param name="userId" value="<%=String.valueOf(pp.getUser().getUserId())%>" />
								<portlet:param name="redirect" value="<%=redirect%>" />
							</portlet:actionURL>
							
							<% if (pp.getFeedbackStatus() == 1){ %>
							
								<%=LanguageUtil.get(pageContext, "portfolio-feedback-requested")%>
								
							<% } else { %>
							
								<%=LanguageUtil.get(pageContext, "portfolio-feedback-received")%>
								
							<% } %>
							
							(<%= FastDateFormatFactoryUtil.getDate(locale, timeZone).format(pp.getModifiedDate()) %>)
							<liferay-ui:icon-delete url="<%=removeFeedbackURL.toString()%>" />
						<% } %>	
					</td>
				</tr>
			<% } %>
	   	<% } %>
    </tbody>
</table>
<aui:script use="liferay-util-window">
/*
AUI().ready('aui-base','aui-editable-deprecated','aui-node-deprecated','event',
		function(A) {
	 	var editable = new A.Editable({
		  node: '#edittest'
		});
	 	//A.Event.simulate(editable,"click");
	 	editable.show();
		});*/

AUI().use('aui-base',
	'aui-io-plugin-deprecated',
	'liferay-util-window',
	function(A) {
		A.all('.popUpLink').on('click', function(event){
			var portfolioPlid = event.currentTarget.one('#portfolioPlid').get('value');
			var renderURL = Liferay.PortletURL.createRenderURL();
			renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
			renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
			renderURL.setParameter("mvcPath", "/html/myportfolio/popup/publish_portfolio.jsp");
			renderURL.setParameter("portfolioPlid", portfolioPlid);
			renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
			renderURL.setParameter("currentRedirect","<%=redirect%>");
			openPopUp(renderURL,"<%=LanguageUtil.get(portletConfig, locale, "portfolio-publish-portfolio") %>");
		});
	});
	
AUI().use('aui-base',
	'aui-io-plugin-deprecated',
	'liferay-util-window',
	function(A) {
		A.all('.feedbackPopUpLink').on('click', function(event){
			var portfolioPlid = event.currentTarget.one('#portfolioPlid').get('value');
			var renderURL = Liferay.PortletURL.createRenderURL();
			renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
			renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
			renderURL.setParameter("mvcPath", "/html/myportfolio/popup/request_feedback.jsp");
			renderURL.setParameter("portfolioPlid", portfolioPlid);
			renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
			renderURL.setParameter("currentRedirect","<%=redirect%>");
			openPopUp(renderURL,"<%=LanguageUtil.get(portletConfig, locale, "portfolio-request-feedback") %>");
		});
	});
		
AUI().use('aui-base',
	'aui-io-plugin-deprecated',
	'liferay-portlet-url',
	'liferay-util-window',
	'aui-dialog-iframe-deprecated',
	function(A) {
		A.one('#<portlet:namespace />createPageButton').on('click', function(event){
			var renderURL = Liferay.PortletURL.createRenderURL();
			renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
			renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
			renderURL.setParameter("mvcPath", "/html/myportfolio/popup/create_portfolio.jsp");
			renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
			renderURL.setParameter("currentRedirect","<%=redirect%>");
			openPopUp(renderURL,"<%=LanguageUtil.get(pageContext, "portfolio-add-portfolio")%>");
		});
	});

function openPopUp(renderURL, title){
	
	Liferay.Util.openWindow(
			{
				dialog: {
					after: {
						destroy: function(event) {
							document.location.href = '<%=redirect%>';
						}
					},
					centered: true,
					constrain: true,
					destroyOnHide: true,
					height: 600,
					modal: true,
					plugins: [Liferay.WidgetZIndex],
					width: 600
				},
				id: '<portlet:namespace />Dialog',
				title: title, 
				uri: renderURL
			}
		);
	
}

</aui:script>