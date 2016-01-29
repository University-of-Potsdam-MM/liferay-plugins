<%@page import="java.util.regex.Pattern"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil"%>
<%@page import="de.unipotsdam.elis.language.model.LanguageKey"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.GetterUtil"%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<% 
String message = (String) request.getAttribute("liferay-ui:icon:message");
LanguageKey languageKey = LanguageKeyLocalServiceUtil.fetchLanguageKey(message); 
String translation;
String link = null;

if (languageKey != null){
	translation = LocalizationUtil.getLocalization(languageKey.getValue(), LocaleUtil.toLanguageId(themeDisplay.getLocale()));
	String[] splitString = translation.split(Pattern.quote("["));
	if (splitString.length > 1){
		translation = splitString[0];
		link = splitString[1].replace("]", "");
	}
}else
	translation = LanguageUtil.get(pageContext, message);
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
	<span class="hide-accessible tooltip-text"
		id="<%= message %>">
		<%=translation%>
		<br>
		<% if (link != null) {%>
			<span class="more-link">
				<a href="<%=link%>" onClick="window.open('<%=link%>');"><%= LanguageUtil.get(pageContext, "more") %></a>
			</span>
		<% } %>
	</span> 
</span>