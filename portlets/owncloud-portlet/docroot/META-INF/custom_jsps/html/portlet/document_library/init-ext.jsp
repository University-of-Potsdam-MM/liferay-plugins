<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>
<%-- BEGIN HOOK CHANGE --%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="java.lang.reflect.Method"%>

<%
	// Get isOwncloudRepository attribute detecting whether the current folder belongs to a repository 
	boolean isOwncloudRepository = false;
	if (request.getAttribute("isOwncloudRepository") != null) 
		isOwncloudRepository = Boolean.parseBoolean(request.getAttribute("isOwncloudRepository").toString());		 
		 
	Object owncloudRepositoryObject = null;
	Method getDownloadLinkMethod = null;
	Object owncloudCacheObject = null;
	Method getErrorMethod = null;
		 
	if (isOwncloudRepository){	
		// get method returning the download link for a file by reflection (necessary because hook classes are not visible)
		ClassLoader classLoader = PortletBeanLocatorUtil.getBeanLocator("owncloud-portlet").getClassLoader();
		Class owncloudRepositoryClass = classLoader.loadClass("de.unipotsdam.elis.owncloud.repository.OwncloudRepository");
		getDownloadLinkMethod = owncloudRepositoryClass.getMethod("getDownloadLink", long.class);
		owncloudRepositoryObject = owncloudRepositoryClass.newInstance();

		// get method to access cache (for error messages)
		Class owncloudCacheClass = classLoader.loadClass("de.unipotsdam.elis.owncloud.repository.OwncloudCache");
		Method getInstanceMethod = owncloudCacheClass.getMethod("getInstance");
		owncloudCacheObject = getInstanceMethod.invoke(null);
		getErrorMethod = owncloudCacheClass.getMethod("getError");
	}
	
	showFoldersSearch = false;
%>
<%-- END HOOK CHANGE --%>