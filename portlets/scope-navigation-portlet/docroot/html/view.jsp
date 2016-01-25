<%@page import="com.liferay.portal.model.GroupConstants"%>
<%@page import="javax.portlet.WindowState"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="com.liferay.portal.util.PortletKeys"%>
<%@page import="javax.portlet.PortletURL"%>
<%@page import="com.liferay.portlet.PortletURLFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.model.MembershipRequestConstants"%>
<%@page import="com.liferay.portal.service.MembershipRequestLocalServiceUtil"%>
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
boolean membershipRequestPending = MembershipRequestLocalServiceUtil.hasMembershipRequest(user.getUserId(), layoutSetGroup.getGroupId(), MembershipRequestConstants.STATUS_PENDING);


boolean privateVisible = (isMember || isIrregularSite) && layoutSetGroup.hasPrivateLayouts();

if (layoutSetGroup.isUser()){
	privateVisible = layoutSetGroup.getClassPK() == user.getUserId();
}

LayoutSet layoutset = themeDisplay.getLayoutSet();

String publicURL = PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, false);
String privateURL = PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, true);

String redirectURL;
if (layoutSetGroup.hasPrivateLayouts())
	redirectURL= privateURL;
else
	redirectURL= publicURL;

if (privateVisible) {
%>

	<portlet:actionURL name="changeScope" var="changeScopeURL" >
	    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
	</portlet:actionURL>
	
	<aui:form action="<%= changeScopeURL %>" method="post" name="changeScopeForm" >
		<aui:select name="scopeNavigation" label="">
			<aui:option label="private-space" value="private" class="private" selected="<%= layout.isPrivateLayout() %>" />
			<aui:option label="public-space" value="public" class="public" selected="<%= !layout.isPrivateLayout() %>"/>
		</aui:select>
		<liferay-ui:icon-help message="private-public-help"></liferay-ui:icon-help>
	</aui:form>
	
	<aui:script>
	AUI().use("aui-base", function(A){ 
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

<% 
}
else{
%>
	<span><%= LanguageUtil.get(pageContext, "public-space") %></span>
	<liferay-ui:icon-help message="private-public-help"></liferay-ui:icon-help>
<%
	if (!isMember){
		if (membershipRequestPending){
		%>
			<div id="<portlet:namespace />siteMembership">
				<span><%= LanguageUtil.get(pageContext, "membership-requested") %></span>
			</div>
		<%
		}
		else if (layoutset.getGroup().getType() == GroupConstants.TYPE_SITE_OPEN){
			
			PortletURL portletURL = liferayPortletResponse.createActionURL(PortletKeys.SITE_MEMBERSHIPS_ADMIN);
			portletURL.setParameter("struts_action", "/sites_admin/edit_site_assignments");
			portletURL.setParameter(Constants.CMD, "group_users");
			portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
			portletURL.setParameter("groupId", String.valueOf(layoutset.getGroup().getGroupId()));
			portletURL.setParameter("addUserIds", String.valueOf(user.getUserId()));
			portletURL.setWindowState(WindowState.NORMAL);
			%>
			
			<div id="<portlet:namespace />siteMembership">
				<a id="<portlet:namespace />siteMembershipLink" href="<%= portletURL.toString()%>"><%= LanguageUtil.get(pageContext, "join-site") %></a>
			</div>
			<aui:script>
			AUI().use('aui-base', 'liferay-portlet-url', function(A) {
				A.one('#<portlet:namespace />siteMembershipLink').on(
					'click',
					function(event) {
						event.preventDefault();
							A.io.request(
								event.currentTarget.get('href'),
								{
									after: {
										success: function(event, id, obj) {
											window.open("<%=redirectURL.toString()%>","_self");
										}
								
									}
								}
							);
							
						});});
			</aui:script>
			
			<%
		}
		else if (layoutset.getGroup().getType() == GroupConstants.TYPE_SITE_RESTRICTED){
			
			PortletURL portletURL = liferayPortletResponse.createActionURL(PortletKeys.SITES_ADMIN);
			portletURL.setParameter("struts_action", "/sites_admin/post_membership_request");
			portletURL.setParameter("redirect", themeDisplay.getURLCurrent());
			portletURL.setParameter("groupId", String.valueOf(layoutset.getGroup().getGroupId()));
			portletURL.setParameter("addUserIds", "$user_id");
			portletURL.setParameter("comments", LanguageUtil.format(themeDisplay.getLocale(), 
					"x-wishes-to-join-x", new Object[] { user.getFullName(), layoutset.getGroup().getDescriptiveName() }, false));
			portletURL.setWindowState(WindowState.NORMAL);
			%>
			
			<div id="<portlet:namespace />siteMembership">
				<a id="<portlet:namespace />siteMembershipLink" href="<%= portletURL.toString()%>"><%= LanguageUtil.get(pageContext, "request-membership") %></a>
			</div>
			<aui:script>
			AUI().use('aui-base', 'liferay-portlet-url', function(A) {
				A.one('#<portlet:namespace />siteMembershipLink').on(
					'click',
					function(event) {
						event.preventDefault();
							A.io.request(
								event.currentTarget.get('href'),
								{
									after: {
										success: function(event, id, obj) {
											var siteMembershipDiv = A.one('#<portlet:namespace />siteMembership');
											siteMembershipDiv.setHTML('<span><%= LanguageUtil.get(pageContext, "membership-requested") %><span>');
											siteMembershipDiv.addClass('membershipRequested');
										}
									}
								}
							);
							
						});});
			</aui:script>
			
			<%
		}
	}
}
%>