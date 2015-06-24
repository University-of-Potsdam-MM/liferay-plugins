<%@ page import="java.util.List"%>
<%@ page import="javax.portlet.PortletURL"%>

<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.Layout"%>
<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>

<%@ page
	import="de.unipotsdam.elis.portfolio.PortfolioManager"%>
<%@ page import="de.unipotsdam.elis.portfolio.util.jsp.JspHelper"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>