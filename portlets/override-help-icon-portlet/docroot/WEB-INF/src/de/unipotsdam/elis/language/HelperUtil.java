package de.unipotsdam.elis.language;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;

import de.unipotsdam.elis.language.model.LanguageKey;
import de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil;
import de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil;

public class HelperUtil {
	
	public static void updateTooltipContent(LanguageKey languageKey) throws SystemException, PortalException{
		Map<Locale,String> map = new HashMap<Locale, String>();
		List<String> articleIds = new ArrayList<String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()){
			Tuple result = generateTooltipContent(languageKey.getKey(), languageKey.getValue(), locale);
			map.put(locale,(String)result.getObject(0));
			if (result.getObject(1) != null)
				articleIds.add((String)result.getObject(1));
		}
		
		String tooltipContent = LocalizationUtil.updateLocalization(map, "", "Name", LocaleUtil.toLanguageId(Locale.GERMAN));
		languageKey.setTooltipContent(tooltipContent);
		LanguageKeyLocalServiceUtil.updateLanguageKey(languageKey);
		
		LanguageKeyJournalArticleLocalServiceUtil.removeLanguageKeyJournalArticleByKey(languageKey.getKey());
		for (String articleId : articleIds){
			LanguageKeyJournalArticleLocalServiceUtil.addLanguageKeyJournalArticle(languageKey.getKey(), articleId);
		}
	}
	
	public static String generateTooltipContent(String key, String value) throws SystemException, PortalException{
		Map<Locale,String> map = new HashMap<Locale, String>();
		List<String> articleIds = new ArrayList<String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()){
			Tuple result = generateTooltipContent(key, value, locale);
			map.put(locale,(String)result.getObject(0));
			if (result.getObject(1) != null)
				articleIds.add((String)result.getObject(1));
		}
		LanguageKeyJournalArticleLocalServiceUtil.removeLanguageKeyJournalArticleByKey(key);
		for (String articleId : articleIds){
			LanguageKeyJournalArticleLocalServiceUtil.addLanguageKeyJournalArticle(key, articleId);
		}
		
		return LocalizationUtil.updateLocalization(map, "", "Name", LocaleUtil.toLanguageId(Locale.GERMAN));
	}
	
	private static Tuple generateTooltipContent(String key, String value, Locale locale) throws SystemException, PortalException{
		
		String link = null;
		String articleId = null;
		String translation = LocalizationUtil.getLocalization(value, LocaleUtil.toLanguageId(locale));
		
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
								articleId = prefs.getValue("articleId", null);
								Long articleGroupId = NumberUtils.createLong(prefs.getValue("groupId", null));
								if (articleId != null && articleGroupId != null ){
									JournalArticle article = JournalArticleLocalServiceUtil.fetchLatestArticle(
											articleGroupId, articleId, 0);	
									Document doc1 = Jsoup.parse(article.getContentByLocale(locale.getCountry()));
									// needs to be parsed again because tags are escaped
									Elements pElements = Jsoup.parse(doc1.text()).select("p");
									if (!pElements.isEmpty()){
										pageLink.replaceWith(new TextNode(pElements.first().text(),""));
										boolean more = BooleanUtils.toBoolean(pageLink.attr("more"));
										if (more) {
											link = PortalUtil.getLayoutFullURL(PortalUtil.getScopeGroupId(helpPageLayout, portlet.getPortletId()), portlet.getPortletId(),true);
										}
									}
									else {
										throw new PortalException("Error parsing language key. No first paragraph found.");
									}
								}
								else {
									throw new PortalException("Error parsing language key. No web content set for the chosen portlet.");
								}
							}
							currentJournalContentIndex++;
						} 
					}
					if (currentJournalContentIndex <= journalContentIndex){
						throw new PortalException("Error parsing language key. JournalContentIndex out of range.");
					}
				}
				else{
					throw new PortalException("Error parsing language key. Could not find layout with groupId = " + groupId + ", privateLayout = " + privateLayout + " and friendlyURL = " + friendlyURL);
				}
			}
			else{
				throw new PortalException("Error parsing language key. groupId, privateLayout or friendlyURL is missing or one of them has not the correct format. Key:");
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
		
		StringBuilder sb = new StringBuilder();
		sb.append(doc.body().text());
		if (link != null){
			sb.append("<br>");
			sb.append("<span class=\"more-link\">");
			sb.append("<a href=\"");
			sb.append(link);
			sb.append("\" target=\"_blank\">");
			sb.append(LanguageUtil.get(locale, "more"));
			sb.append("</a>");
			sb.append("</span>");
		}
		return new Tuple(sb.toString(),articleId);
	}
}
