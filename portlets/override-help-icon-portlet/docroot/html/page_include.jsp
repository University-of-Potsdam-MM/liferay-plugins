
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="de.unipotsdam.elis.language.HelperUtil"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="de.unipotsdam.elis.language.model.LanguageKey"%>
<%@page import="de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<% 
String message = (String) request.getAttribute("liferay-ui:icon:message");
LanguageKey languageKey = LanguageKeyLocalServiceUtil.fetchLanguageKey(message); 
String translation = null;

if (languageKey != null){
	if (StringUtils.isEmpty(LocalizationUtil.getLocalization(languageKey.getTooltipContent(), LocaleUtil.toLanguageId(locale))))
		HelperUtil.updateTooltipContent(languageKey);
	translation = LocalizationUtil.getLocalization(languageKey.getTooltipContent(), LocaleUtil.toLanguageId(locale));;		
} else {
	translation = LanguageUtil.get(pageContext, message);
}
String id = StringUtil.randomId(); 
%>

<span class="taglib-icon-help">
	<img alt="" 
		aria-labelledby="<%= id %>" 
		onBlur="Liferay.Portal.ToolTip.hide();" 
		onFocus="Liferay.Portal.ToolTip.show(this);"
		onMouseOver="Liferay.Portal.ToolTip.show(this);"
		src="<%=themeDisplay.getPathThemeImages() + "/portlet/help.png"%>"
		tabIndex="0" />
	<span class="hide-accessible tooltip-text" id="<%= message %>"> <%=translation%> </span> 
</span>