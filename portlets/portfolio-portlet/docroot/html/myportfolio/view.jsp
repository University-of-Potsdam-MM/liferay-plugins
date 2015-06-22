<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects /> 

<%
	List<Layout> userPortfolios = PortfolioPermissionManager.getPortfoliosOfCurrentUser();
	String popUpLink = "<a href=\"#\" class=\"popUpLink\" id=\"test\">" + 
		LanguageUtil.get(pageContext, "portfolio-choose-people") +"</a>";
%>

<!-- TODO: Button mit Funktion hinterlegen -->
<aui:button id="createPageButton" name="createPageButton" type="button" value="portfolio-create-page" />

<liferay-ui:search-container delta="10" emptyResultsMessage="portfolio-no-portfolios">
	<liferay-ui:search-container-results results="<%=userPortfolios%>" total="<%=userPortfolios.size()%>" />
	<liferay-ui:search-container-row className="com.liferay.portal.model.Layout" keyProperty="layoutId" modelVar="portfolio">
		<liferay-ui:search-container-column-text name="portfolio-title-column"
			value="<%=portfolio.getName(themeDisplay.getLocale())%>" 
			href="<%=JspHelper.getPortfolioURL(themeDisplay, portfolio, themeDisplay.getUser()) %>" />

		<liferay-ui:search-container-column-text name="portfolio-feedback-column"
			value="" />
			
		<liferay-ui:search-container-column-text name="portfolio-publishment-column"
			value="<%=popUpLink%>" />
			
		<liferay-ui:search-container-column-jsp name="portfolio-options-column" align="right" 
			path="/html/myportfolio/portfolio_actions.jsp" />
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>

<aui:script use="liferay-util-window">
AUI().use('aui-base',
		'aui-io-plugin-deprecated',
		'liferay-util-window',
		'aui-dialog-iframe-deprecated',
		function(A) {
			var renderURL = Liferay.PortletURL.createRenderURL();
			renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
			renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
			renderURL.setParameter("mvcPath", "/html/myportfolio/permission_view.jsp");
			renderURL.setParameter("plid",'${portfolio.getPlid()}');
			renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
			A.all('.popUpLink').on('click', function(event){
				var popUpWindow=Liferay.Util.Window.getWindow({
					dialog: {
					centered: true,
					constrain2view: true,
					modal: true,
					resizable: false,
					width: 700 ,
					height: 500 
					}
				}).plug(
					A.Plugin.DialogIframe, {
						autoLoad: true,
						iframeCssClass: 'dialog-iframe',
						uri:renderURL.toString()
					}).render();
					popUpWindow.show();
					popUpWindow.titleNode.html("<%=LanguageUtil.get(pageContext, "portfolio-publish-portfolio")%>");
					popUpWindow.io.start();
			});
		});
AUI().use('aui-base',
		'aui-io-plugin-deprecated',
		'liferay-util-window',
		'aui-dialog-iframe-deprecated',
		function(A) {
			var renderURL = Liferay.PortletURL.createRenderURL();
			renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
			renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
			renderURL.setParameter("mvcPath", "/html/myportfolio/create_portfolio.jsp");
			renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
			A.one('#<portlet:namespace />createPageButton').on('click', function(event){
				console.log('test');
				var popUpWindow=Liferay.Util.Window.getWindow({
					dialog: {
					centered: true,
					constrain2view: true,
					modal: true,
					resizable: false,
					width: 700 ,
					height: 500 
					}
				}).plug(
					A.Plugin.DialogIframe, {
						autoLoad: true,
						iframeCssClass: 'dialog-iframe',
						uri:renderURL.toString()
					}).render();
					popUpWindow.show();
					popUpWindow.titleNode.html("<%=LanguageUtil.get(pageContext, "portfolio-publish-portfolio")%>");
					popUpWindow.io.start();
			});
		});
</aui:script>