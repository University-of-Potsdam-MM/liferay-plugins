<%@ include file="/html/init.jsp" %> 

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL" />
<liferay-ui:error key="at-least-one-activity-needs-to-be-selected" message="at-least-one-activity-needs-to-be-selected"></liferay-ui:error>


<c:choose>
	<c:when test="<%= group.isUser() && layout.isPrivateLayout() %>"> 
		<aui:form action="<%= configurationActionURL %>" method="post" name="fm">
			<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
			<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />
			<span><%= LanguageUtil.get(pageContext, "visible-activities") %></span>
			
			<% for (String tabName : ActivitiesPortlet.ACTIVITY_TABS){%>
				<aui:input name="<%= tabName %>" value="<%=pref.getValue(tabName, \"false\") %>" type="checkbox"></aui:input>
			<%} %>
			
			<aui:button-row>
				<aui:button type="submit" />
			</aui:button-row>
		</aui:form>
	</c:when>
	<c:otherwise>
		<liferay-ui:message key="no-configuration-possible-because-public-page" />
	</c:otherwise>
</c:choose>
