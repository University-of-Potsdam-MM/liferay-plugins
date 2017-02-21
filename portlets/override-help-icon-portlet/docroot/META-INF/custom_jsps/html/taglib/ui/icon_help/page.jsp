<%@page import="com.liferay.portal.kernel.servlet.ServletContextPool"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
    ServletContext portletContext = ServletContextPool.get("override-help-icon-portlet");
    if (portletContext != null) {
%>
		<!-- needs to be done to be able to load custom classes -->
        <liferay-util:include page="/html/page_include.jsp" servletContext="<%=portletContext%>" />
<%
    }
%>