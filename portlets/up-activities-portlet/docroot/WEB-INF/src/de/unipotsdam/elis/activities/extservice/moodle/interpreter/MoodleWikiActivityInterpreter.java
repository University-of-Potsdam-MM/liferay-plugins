package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import javax.portlet.PortletConfig;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.activities.extservice.interpreter.ExtServiceSocialActivityInterpreter;

public class MoodleWikiActivityInterpreter extends ExtServiceSocialActivityInterpreter {

	@Override
	public String getActivityTitle(SocialActivitySet activitySet, JSONObject data, ServiceContext serviceContext) {
		PortletConfig portletConfig = (PortletConfig) serviceContext.getRequest().getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);
		if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_ADD_PAGE)
			return LanguageUtil.get(portletConfig, serviceContext.getLocale(), "new-wiki-page");
		else if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_UPDATE_PAGE)
			return LanguageUtil.get(portletConfig, serviceContext.getLocale(), "updated-wiki-page");
		return StringPool.BLANK;
	}

	@Override
	public String getBody(JSONObject data, ThemeDisplay themeDisplay) {
		JSONObject object = data.getJSONObject("object");

		StringBuilder sb = new StringBuilder();

		
		
		sb.append("<div class=\"activity-body\"><div class=\"title\">");
		sb.append(getLink(object, themeDisplay));
		sb.append("</div><div class=\"wiki-page-content\">");

		sb.append(StringUtil.shorten(HtmlUtil.escape(HtmlUtil.extractText(object.getString("content"))), 200));

		sb.append("</div></div>");

		return sb.toString();
	}

	protected String getLink(JSONObject object, ThemeDisplay themeDisplay) {
		return wrapLink(object.getString("url"), themeDisplay.getPathThemeImages() + "/common/page.png",
				object.getString("name"));
	}

}
