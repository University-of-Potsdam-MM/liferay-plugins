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
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.bean.PortletBeanLocatorUtil"%>
<%@page import="java.lang.reflect.Method"%>

<%
	boolean isOwncloudRepository = false;
	if (request.getAttribute("isOwncloudRepository") != null) 
		isOwncloudRepository = Boolean.parseBoolean(request.getAttribute("isOwncloudRepository").toString());
		 
		 
	Object owncloudRepositoryObject = null;
	Method getDownloadLinkMethod = null;
	Method getFromCacheMethod = null;
		 
	if (isOwncloudRepository){		
		ClassLoader classLoader = PortletBeanLocatorUtil.getBeanLocator("owncloud-hook").getClassLoader();
		Class owncloudRepositoryClass = classLoader.loadClass("de.unipotsdam.elis.owncloud.repository.OwncloudRepository");
		getDownloadLinkMethod = owncloudRepositoryClass.getMethod("getDownloadLink", long.class);
		owncloudRepositoryObject = owncloudRepositoryClass.newInstance();
		
		Class owncloudCacheManagerClass = classLoader.loadClass("de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager");
		getFromCacheMethod = owncloudCacheManagerClass.getMethod("getFromCache", String.class, String.class);
	}
%>