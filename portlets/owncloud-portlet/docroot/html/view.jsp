<%@page import="de.unipotsdam.elis.owncloud.repository.OwncloudConfigurationLoader"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<liferay-portlet:actionURL var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">
    <aui:input name="ownCloudWebdavAddress" label="Owncloud Webdav Adress" value="<%= OwncloudConfigurationLoader.getOwnCloudWebdavAddress() %>"/>
    <aui:input name="ownCloudDownloadAddress" label="Owncloud Download Adress" value="<%= OwncloudConfigurationLoader.getOwncloudDownloadAddress() %>"/>
    <aui:input name="ownCloudShareAddress" label="Owncloud Share Adress" value="<%= OwncloudConfigurationLoader.getOwncloudShareAddress() %>"/>
    <aui:input name="ownCloudRootFolder" label="Owncloud Root Folder" value="<%= OwncloudConfigurationLoader.getRootFolder() %>"/>
    <aui:input name="ownCloudRootUsername" label="Owncloud Root Username" value="<%= OwncloudConfigurationLoader.getRootUsername() %>"/>
    <aui:input name="ownCloudRootPassword" label="Owncloud Root Password" value="<%= OwncloudConfigurationLoader.getRootPassword() %>" type="password"/>

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>