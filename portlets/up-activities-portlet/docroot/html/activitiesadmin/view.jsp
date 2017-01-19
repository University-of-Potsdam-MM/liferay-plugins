<%@page import="de.unipotsdam.elis.activities.ActivitiesAdminPortlet"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<liferay-portlet:actionURL var="configurationURL" />

<aui:form action="<%= configurationURL %>" method="post" name="fm">

	<aui:input name="moodleServiceEndpoint" label="Moodle Service Endpoint" value="<%= ActivitiesAdminPortlet.getMoodleServiceEndpoint() %>"/>  

    <aui:button-row>
        <aui:button type="submit" />
    </aui:button-row>
</aui:form>