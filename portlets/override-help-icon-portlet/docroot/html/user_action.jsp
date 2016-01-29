
<%@ include file="/html/init.jsp"%>

<% 

ResultRow row = (ResultRow)request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);

LanguageKey languageKey = (LanguageKey)row.getObject();

Map<Locale,String> nameNap = LocalizationUtil.getLocalizationMap(languageKey.getValue());
%>

<liferay-ui:icon-menu>
	<portlet:actionURL name="deleteEntry" var="deleteEntryURL" >
	    <portlet:param name="mvcPath" value="/html/view.jsp" />
		<portlet:param name="key" value="<%=languageKey.getKey()%>" />
	</portlet:actionURL>
	
	<portlet:renderURL var="editEntryURL" windowState="<%=LiferayWindowState.POP_UP.toString()%>" portletMode="<%=LiferayPortletMode.VIEW.toString()%>">
		<portlet:param name="mvcPath" value="/html/popup.jsp"/>
		<portlet:param name="key" value="<%= languageKey.getKey() %>"/>
		<% for (Locale availableLocal : LanguageUtil.getAvailableLocales()){ 
			if (nameNap.containsKey(availableLocal)) {%>
				<portlet:param name="<%=availableLocal.getLanguage()%>" value="<%= nameNap.get(availableLocal) %>"/>	
		<% } } %>
	</portlet:renderURL>
	<%
		String editMethod = "openPopUp('" + editEntryURL.toString() + "','"  + LanguageUtil.get(pageContext, "edit-language-key") + "');";
	%>
	
		<liferay-ui:icon
			image="delete"
			url="<%= deleteEntryURL %>"			
		/>
		
		<liferay-ui:icon
			image="edit"
			url="javascript:void(0);"
			onClick="<%= editMethod %>"		
		/>
		
		
</liferay-ui:icon-menu>

