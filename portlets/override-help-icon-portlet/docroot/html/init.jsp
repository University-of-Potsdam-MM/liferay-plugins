<%@page import="de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil"%>
<%@page import="java.util.Locale"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%@page import="de.unipotsdam.elis.language.model.LanguageKey"%>
<%@page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@page import="com.liferay.portal.kernel.util.WebKeys"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<aui:script>	    
function openPopUp(renderURL, title) {
    Liferay.Util.openWindow({
        dialog: {
            after: {
                destroy: function(event) {
                	location.reload();
                }
            },
            centered: true,
            constrain: true,
            destroyOnHide: true,
            height: 600,
            modal: true,
            plugins: [Liferay.WidgetZIndex],
            width: 600
        },
        id: '<portlet:namespace />Dialog',
        title: title,
        uri: renderURL
    });
}
</aui:script>