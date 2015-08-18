<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */
--%>

<%@ include file="/init.jsp" %>

<%@ page import="com.liferay.compat.portal.kernel.util.Time" %><%@
page import="com.liferay.microblogs.model.MicroblogsEntry" %><%@
page import="com.liferay.microblogs.model.MicroblogsEntryConstants" %><%@
page import="com.liferay.microblogs.service.MicroblogsEntryLocalServiceUtil" %><%@
page import="com.liferay.portal.kernel.util.PropsKeys" %><%@
page import="com.liferay.portal.kernel.util.PropsUtil" %><%@
page import="com.liferay.portal.service.ServiceContextFactory" %><%@
page import="com.liferay.portlet.messageboards.model.MBMessage" %><%@
page import="com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.model.SocialActivity" %><%@
page import="com.liferay.portlet.social.model.SocialActivitySet" %><%@
page import="com.liferay.portlet.social.service.SocialActivityLocalServiceUtil" %><%@
page import="com.liferay.portlet.social.service.SocialActivitySetLocalServiceUtil" %><%@
page import="de.unipotsdam.elis.activities.util.ActivitiesUtil" %><%@
page import="de.unipotsdam.elis.activities.ActivitiesPortlet"%>

<%
PortletPreferences pref = renderRequest.getPreferences();
StringBuilder sb = new StringBuilder();

String firstTab = "all";
boolean oneTab = true;
PortletURL portletURL = renderResponse.createRenderURL();
for (String tabName : ActivitiesPortlet.ACTIVITY_TABS){
	if (pref.getValue(tabName, "false").equals("true")){
		if (sb.length() == 0){
			sb.append(tabName);
			firstTab = tabName;	
		}
		else{
			oneTab = false;
			sb.append("," + tabName);
		}	
	}
}

String tabs = sb.toString();

portletURL.setParameter("tabs1", firstTab);		
String tabs1 = ParamUtil.getString(request, "tabs1", firstTab);

Format dateFormatDateTime = FastDateFormatFactoryUtil.getDateTime(locale, timeZone);


Group group = themeDisplay.getScopeGroup();
%>

<%!
private static final int _DELTA = 10;
%>