<%@page import="org.jsoup.select.Elements"%>
<%@page import="org.jsoup.nodes.TextNode"%>
<%@page import="org.jsoup.nodes.Node"%>
<%@page import="com.liferay.portal.util.PortalUtil"%>
<%@page import="java.util.regex.Matcher"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="com.liferay.portal.service.PortletPreferencesLocalServiceUtil"%>
<%@page import="com.liferay.portal.util.PortletKeys"%>
<%@page import="com.liferay.portal.kernel.util.StringUtil"%>
<%@page import="org.apache.commons.lang3.BooleanUtils"%>
<%@page import="org.apache.commons.lang3.math.NumberUtils"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.Portlet"%>
<%@page import="java.util.List"%>
<%@page import="com.liferay.portal.model.LayoutTypePortlet"%>
<%@page import="com.liferay.portal.model.Layout"%>
<%@page import="org.jsoup.nodes.Element"%>
<%@page import="org.jsoup.nodes.Document"%>
<%@page import="org.jsoup.Jsoup"%>
<%@page import="com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil"%>
<%@page import="com.liferay.portal.model.LayoutFriendlyURL"%>
<%@page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil"%>
<%@page import="de.unipotsdam.elis.language.model.LanguageKey"%>
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
String translation = null;
String link = null;

String errorMessage = "";

if (languageKey != null){
	translation = LocalizationUtil.getLocalization(languageKey.getValue(), LocaleUtil.toLanguageId(themeDisplay.getLocale()));
	String[] splitString = translation.split(Pattern.quote("["));
	
	Document doc = Jsoup.parse(translation);
	
	Element pageLink = doc.select("page-link").first();
	if (pageLink != null){
		Long groupId = NumberUtils.createLong(pageLink.attr("groupId"));
		Boolean privateLayout = BooleanUtils.toBooleanObject(pageLink.attr("privateLayout"));
		String friendlyURL = pageLink.attr("friendlyURL");
		int journalContentIndex = NumberUtils.toInt(pageLink.attr("journalContentIndex"));
		if (groupId != null && privateLayout != null && friendlyURL != null){
			Layout helpPageLayout = LayoutLocalServiceUtil.fetchLayoutByFriendlyURL(groupId, privateLayout, friendlyURL);
			if (helpPageLayout != null){
				LayoutTypePortlet helpPageLayoutTypePortlet = (LayoutTypePortlet)helpPageLayout.getLayoutType();
				List<Portlet> portlets = helpPageLayoutTypePortlet.getAllPortlets();
				int currentJournalContentIndex = 0;
				for(Portlet portlet : portlets){
					if (portlet.getPortletName().equals(PortletKeys.JOURNAL_CONTENT)){
						if (journalContentIndex == currentJournalContentIndex){
							PortletPreferences prefs = PortletPreferencesLocalServiceUtil.fetchPreferences(
									helpPageLayout.getCompanyId(), PortletKeys.PREFS_OWNER_ID_DEFAULT,
									PortletKeys.PREFS_OWNER_TYPE_LAYOUT, helpPageLayout.getPlid(), portlet.getPortletId());
							String articleId = prefs.getValue("articleId", null);
							Long articleGroupId = NumberUtils.createLong(prefs.getValue("groupId", null));
							if (articleId != null && articleGroupId != null ){
								JournalArticle article = JournalArticleLocalServiceUtil.fetchLatestArticle(
										articleGroupId, articleId, 0);	
								Document doc1 = Jsoup.parse(article.getContentByLocale(request.getLocale().getCountry()));
								// needs to be parsed again because tags are escaped
								Elements pElements = Jsoup.parse(doc1.text()).select("p");
								if (!pElements.isEmpty()){
									pageLink.replaceWith(new TextNode(pElements.first().text(),""));
									boolean more = BooleanUtils.toBoolean(pageLink.attr("more"));
									if (more) {
										link = PortalUtil.getLayoutFullURL(helpPageLayout, themeDisplay);
									}
								}
								else
									errorMessage = "no first paragraph found";
							}
							else {
								errorMessage = "no web conent set on the choosen page";
							}
						}
						currentJournalContentIndex++;
					}
				}
				if (currentJournalContentIndex <= journalContentIndex){
					errorMessage = "journalContentIndex out of range";
				}
			}
			else{
				errorMessage = "could not found layout with groupId = " + groupId + ", privateLayout = " + privateLayout + " and friendlyURL = " + friendlyURL;
			}
		}
		else{
			errorMessage = "groupId, privateLayout or friendlyURL is missing or one of them has not the correct format";
		}
	}
	
	Element more = doc.select("more").first();
	if (more != null){
		String moreLink = more.attr("url");
		if (moreLink != null){
			link = moreLink;
			more.remove();
		}
	}
		
	translation = doc.body().toString();
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
				<a href="<%=link%>" target="_blank"><%= LanguageUtil.get(pageContext, "more") %></a>
			</span>
		<% } %>
	</span> 
</span>

<script>
if ("<%= errorMessage %>" != "")
	console.error("Error parsing custom portlet help string: " + "<%= errorMessage %>");
</script>