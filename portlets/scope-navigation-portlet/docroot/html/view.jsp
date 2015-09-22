<%@page import="com.liferay.portal.model.Group"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<%

Group layoutSetGroup = themeDisplay.getLayoutSet().getGroup();
boolean isIrregularSite = !layoutSetGroup.isRegularSite();
boolean isMember = GroupLocalServiceUtil.hasUserGroup(user.getUserId(), layoutSetGroup.getGroupId());

boolean privateVisible = isMember || isIrregularSite;

if (layoutSetGroup.isUser()){
	privateVisible = layoutSetGroup.getClassPK() == user.getUserId();
}


%>

<portlet:actionURL name="changeScope" var="changeScopeURL" >
    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
</portlet:actionURL>

<aui:form action="<%= changeScopeURL %>" method="post" name="changeScopeForm" >
	<aui:select name="scopeNavigation" onChange="changeScope()" label="current-location">
		<% if (privateVisible) {%>
			<aui:option label="private-space" value="private" selected="<%= layout.isPrivateLayout() %>" />
		<% } %>
		<aui:option label="public-space" value="public" selected="<%= !layout.isPrivateLayout() %>"/>
	</aui:select>
	<liferay-ui:icon-help message="private-public-help"></liferay-ui:icon-help>
</aui:form>

<aui:script>
function changeScope(){
	AUI().use("aui-base", function(A){
		A.one('#<portlet:namespace />changeScopeForm').submit();
	});
}
</aui:script>