<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	ResultRow row = (ResultRow) request.getAttribute(WebKeys.SEARCH_CONTAINER_RESULT_ROW);
	Portfolio userPortfolio = (Portfolio) row.getObject();
	User owner = UserLocalServiceUtil.getUserById(userPortfolio.getLayout().getUserId());
	String messageTaglibOnClick = liferayPortletResponse.getNamespace() + "sendMessage();";
%>

<%= owner.getScreenName() %>

<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= messageTaglibOnClick %>" url="javascript:;" />

<aui:script>
Liferay.provide(
		window,
		'<portlet:namespace />sendMessage',
		function() {
			<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>" />

			var uri = '<liferay-portlet:renderURL portletName="1_WAR_privatemessagingportlet" windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/new_message.jsp" /><portlet:param name="redirect" value="<%= redirectURL %>" /></liferay-portlet:renderURL>';

			uri = Liferay.Util.addParams('<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>userIds=' + '<%= userPortfolio.getLayout().getUserId() %>', uri) || uri;

			Liferay.Util.openWindow(
				{
					dialog: {
						centered: true,
						constrain: true,
						cssClass: 'private-messaging-portlet',
						destroyOnHide: true,
						height: 600,
						modal: true,
						plugins: [Liferay.WidgetZIndex],
						width: 600
					},
					id: '<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>Dialog',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "new-message") %>', 
					uri: uri
				}
			);
		},
		['liferay-util-window']
	);

</aui:script>