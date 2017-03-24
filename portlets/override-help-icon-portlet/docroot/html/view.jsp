<%@page import="com.liferay.portal.kernel.util.HtmlUtil"%>
<%@ include file="/html/init.jsp"%>

<div>
	<aui:button id="createLanguageKeyButton" name="createLanguageKeyButton" type="button" value="add-language-key"/>
	
	<aui:button id="importExportButton" name="importExportButton" type="button" value="import-export"/>
	<br><br>
	
	<portlet:renderURL var="searchURL" >
		<portlet:param name="mvcPath" value="/html/view.jsp"/>
	</portlet:renderURL>
	
	<aui:form id="searchForm" class="searchForm" action="<%= searchURL %>">
		<aui:input name="search" label="" placeholder="search"></aui:input>
	</aui:form>
</div>
<aui:script>
AUI().use('aui-base',
	    'liferay-portlet-url',
	    function(A) {
	        A.one('#<portlet:namespace />createLanguageKeyButton').on('click', function(event) {
	            var renderURL = Liferay.PortletURL.createRenderURL();
	            renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
	            renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
	            renderURL.setParameter("mvcPath", "/html/popup.jsp");
	            renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
	            openPopUp(renderURL, '<%=LanguageUtil.get(pageContext, "add-language-key")%>');
	        });
	    });
AUI().use('aui-base',
	    'liferay-portlet-url',
	    function(A) {
	        A.one('#<portlet:namespace />importExportButton').on('click', function(event) {
	            var renderURL = Liferay.PortletURL.createRenderURL();
	            renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
	            renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
	            renderURL.setParameter("mvcPath", "/html/import_export.jsp");
	            renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
	            openPopUp(renderURL, '<%=LanguageUtil.get(pageContext, "import-export")%>');
	        });
	    });
</aui:script>

<liferay-ui:search-container delta="10" emptyResultsMessage="no-language-keys-found">
	<liferay-ui:search-container-results
		results="<%= LanguageKeyLocalServiceUtil.search(ParamUtil.getString(request, \"search\"),searchContainer.getStart(), searchContainer.getEnd()) %>"
		total="<%= LanguageKeyLocalServiceUtil.getLanguageKeiesCount() %>"
	/>

	<liferay-ui:search-container-row
		className="de.unipotsdam.elis.language.model.LanguageKey"
		keyProperty="key"
		modelVar="languageKey"
	>
		<liferay-ui:search-container-column-text
			name="key"
			value="<%= languageKey.getKey() %>"
		/>

		<liferay-ui:search-container-column-text
			name="value"
			value="<%= HtmlUtil.escape(LocalizationUtil.getLocalization(languageKey.getValue(), LocaleUtil.toLanguageId(themeDisplay.getLocale())))  %>"
		/>
		
		<liferay-ui:search-container-column-jsp
			align="right"
			name="entry-options"
			path="/html/user_action.jsp"
		/>
	</liferay-ui:search-container-row>

	<liferay-ui:search-iterator />

</liferay-ui:search-container>