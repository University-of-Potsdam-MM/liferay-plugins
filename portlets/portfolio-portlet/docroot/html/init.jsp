<%@ page import="java.util.List"%>
<%@ page import="java.text.Format"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="javax.portlet.PortletURL"%>

<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.kernel.util.Constants"%>
<%@ page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@ page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.Layout"%>
<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="de.unipotsdam.elis.portfolio.util.jsp.JspHelper"%>
<%@ page import="de.unipotsdam.elis.portfolio.model.Portfolio"%>
<%@ page import="de.unipotsdam.elis.portfolio.model.PortfolioFeedback"%>
<%@ page import="de.unipotsdam.elis.portfolio.PortfolioStatics"%>
<%@ page import="de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>" />
<liferay-portlet:renderURL portletName="1_WAR_privatemessagingportlet" windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="privateMessagingURL">
	<portlet:param name="mvcPath" value="/new_message.jsp"/>
	<portlet:param name="redirect" value="<%= redirectURL %>"/>
</liferay-portlet:renderURL> 

<aui:script>
Liferay.provide(
    window,
    '<portlet:namespace />sendMessage',
    function(userId) {
        var uri = '<%=privateMessagingURL.toString()%>'; 
        uri = Liferay.Util.addParams('<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>userIds=' + userId, uri) || uri;

        Liferay.Util.openWindow({
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
        });
    }
);
</aui:script>