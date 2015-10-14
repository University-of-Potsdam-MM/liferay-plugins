<%@page import="com.liferay.portal.model.LayoutSet"%>
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

LayoutSet layoutset = themeDisplay.getLayoutSet();

String publicURL = PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, false);
String privateURL = PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, true);
%>

<portlet:actionURL name="changeScope" var="changeScopeURL" >
    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
</portlet:actionURL>

<aui:form action="<%= changeScopeURL %>" method="post" name="changeScopeForm" >
	<aui:select name="scopeNavigation" label="current-location">
		<% if (privateVisible) {%>
			<aui:option label="private-space" value="private" class="private" selected="<%= layout.isPrivateLayout() %>" />
		<% } %>
		<aui:option label="public-space" value="public" class="public" selected="<%= !layout.isPrivateLayout() %>"/>
	</aui:select>
	<liferay-ui:icon-help message="private-public-help"></liferay-ui:icon-help>
</aui:form>

<aui:script>
AUI().use("aui-base", function(A){ 
	console.log(A.all("#<portlet:namespace />scopeNavigation"));
	A.all("#<portlet:namespace />scopeNavigation").on('change',function(e){
		if (e.target.get('value') == 'public'){
			window.location.href = "<%=publicURL%>"
		}
		else{
			window.location.href = "<%=privateURL%>"
		}
	})
});
</aui:script>