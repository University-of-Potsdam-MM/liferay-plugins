<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.LinkedList" %>

<%@ page import="com.liferay.portal.theme.ThemeDisplay" %>
<%@ page import="com.liferay.portal.kernel.exception.PortalException" %>
<%@ page import="com.liferay.portal.kernel.exception.SystemException" %>
<%@ page import="com.liferay.portal.model.Group" %>
<%@ page import="com.liferay.portal.model.User" %>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>

<%@ page import="com.liferay.portal.kernel.util.Constants" %>
<%@ page import="com.liferay.portal.kernel.util.GetterUtil" %>
<%@ page import="com.liferay.portal.kernel.util.StringPool" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="de.unipotsdam.elis.workspacegrid.WorkspaceGridPortlet"%>
<%@ page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@ page import="com.liferay.portal.service.LayoutSetPrototypeServiceUtil"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String visibleWorkspaces = portletPreferences.getValue(WorkspaceGridPortlet.NUMBER_OF_VISIBLE_WORKSPACES, "10");
%>