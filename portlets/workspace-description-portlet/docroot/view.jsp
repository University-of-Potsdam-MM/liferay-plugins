<%@page import="com.liferay.portal.model.Group"%>
<%@page import="com.liferay.portal.model.GroupConstants"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutSetPrototype"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.service.LayoutSetLocalServiceUtil"%>
<%@page import="com.liferay.portal.service.persistence.LayoutSetUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
Group layoutSetGroup = layout.getLayoutSet().getGroup();
	String layoutSetDescription = layout.getLayoutSet().getGroup().getDescription();
	layoutSetDescription = (layoutSetDescription.equals("")) ? LanguageUtil.get(pageContext, "no-description") : layoutSetDescription;
	long layoutSetPrototypeId = LayoutSetLocalServiceUtil.getLayoutSet(layout.getLayoutSet().getGroupId(), true).getLayoutSetPrototypeId();
	String layoutSetPrototypeName = (layoutSetPrototypeId == 0) ? LanguageUtil.get(pageContext, "no-template") : LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototype(layoutSetPrototypeId).getName(locale);
	String type;
	if (layoutSetGroup.getType() == GroupConstants.TYPE_SITE_RESTRICTED)
		type = LanguageUtil.get(pageContext, "restricted");
	else if (layoutSetGroup.getType() == GroupConstants.TYPE_SITE_PRIVATE)
		type = LanguageUtil.get(pageContext, "hidden-site");
	else 
		type = LanguageUtil.get(pageContext, "open-site");
%>

<div id="workspace-description">
	<span id="workspace-description-label"><%= LanguageUtil.get(pageContext, "workspace-description-label") %></span>
	<span id="workspace-description-content"><%= layoutSetDescription %></span>
</div>

<div id="workspace-template-name">
	<span id="workspace-template-name-label"><%= LanguageUtil.get(pageContext, "workspace-template-name-label") %></span>
	<span id="workspace-template-name-content"> <%=layoutSetPrototypeName %></span>
</div>

<div id="workspace-template-type">
	<span id="workspace-template-type-label"><%= LanguageUtil.get(pageContext, "workspace-template-type-label") %></span>
	<span id="workspace-template-type-content"> <%=type %></span>
</div>