<%@ page import="java.util.List"%>
<%@ page import="java.text.Format"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="javax.portlet.PortletURL"%>
<%@page import="java.util.Locale"%>
<%@page import="java.util.Map"%>

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
<%@ page import="com.liferay.compat.portal.kernel.util.HtmlUtil"%>
<%@ page import="com.liferay.portal.model.Team"%>
<%@ page import="com.liferay.portal.service.permission.GroupPermissionUtil"%>
<%@ page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@ page import="com.liferay.portal.security.permission.ActionKeys"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@ page import="com.liferay.portal.model.RoleConstants"%>
<%@ page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil"%>
<%@ page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.Role"%>
<%@ page import="com.liferay.portal.model.Group"%>
<%@ page import="com.liferay.portal.model.LayoutPrototype"%>
<%@ page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@ page import="com.liferay.portal.util.SessionClicks"%>
<%@ page import="de.unipotsdam.elis.custompages.util.jsp.JspHelper"%>
<%@ page import="de.unipotsdam.elis.custompages.CustomPageStatics"%>
<%@ page import="de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />