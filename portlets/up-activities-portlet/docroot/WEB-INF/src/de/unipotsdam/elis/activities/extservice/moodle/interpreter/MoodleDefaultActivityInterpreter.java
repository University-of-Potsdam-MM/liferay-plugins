package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import javax.portlet.PortletConfig;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.extservice.interpreter.ExtServiceSocialActivityInterpreter;

public class MoodleDefaultActivityInterpreter extends
		ExtServiceSocialActivityInterpreter {

	@Override
	public String getActivityTitle(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		PortletConfig portletConfig = (PortletConfig) serviceContext.getRequest().getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);
//		if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_ADD_PAGE)
//			return LanguageUtil.get(portletConfig, serviceContext.getLocale(), "new-wiki-page");
//		else if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_UPDATE_PAGE)
//			return LanguageUtil.get(portletConfig, serviceContext.getLocale(), "updated-wiki-page");
//		return StringPool.BLANK;
		
//		return LanguageUtil.get(portletConfig, serviceContext.getLocale(), "new-moodle-activity");
		return LanguageUtil.format(portletConfig, serviceContext.getLocale(),
				getTitlePattern(activitySet, data, serviceContext), 
				getTitleArguments(activitySet, data, serviceContext)
			);
	}

	@Override
	public String getBody(JSONObject data, ThemeDisplay themeDisplay) {
//		JSONObject object = data.getJSONObject("object"); // data is das data aus activity_moodlesocialactivity tabelle
//
//		StringBuilder sb = new StringBuilder();
//		
//		sb.append("<div class=\"activity-body\"><div class=\"title\">");
//		sb.append(getLink(object, themeDisplay));
//		sb.append("</div><div class=\"moodle-default-content\">");
//
//		sb.append(StringUtil.shorten(HtmlUtil.escape(HtmlUtil.extractText(object.getString("content"))), 200));
//
//		sb.append("</div></div>");
//
//		return sb.toString();
		
		// in current ActivityStream-design no body is used.
		return StringPool.BLANK;
	}

	protected String getLink(JSONObject object, ThemeDisplay themeDisplay) {
		return wrapLink(object.getString("url"), themeDisplay.getPathThemeImages() + "/file_system/large/default.png",
				object.getString("name"));
	}
	
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
		return "new-moodle-activity";
	}
	
	protected Object[] getTitleArguments(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
		return new Object[] {getMoodleActivityLink(data, serviceContext)};
	}
	
	/**
	 * This method returns the url and name of an moodle acitivity as a href.
	 * It returns a blank String if no data was found.
	 * @return
	 */
	private String getMoodleActivityLink(JSONObject data, ServiceContext serviceContext) {
		
		// data null, return blank
		if (data == null)
			return StringPool.BLANK;
		
		// get url and name of moodle activity
		JSONObject object = data.getJSONObject("object"); 
		
		// object not set, return blank
		if (object == null)
			return StringPool.BLANK;
		
		String link = object.getString("url"); 
		String name = object.getString("name"); 
		
		// name and link not set, return blank
		if (link == null && name == null)
			return StringPool.BLANK;
		
		// no useful link is set, return only name
		if (link == null || StringPool.BLANK.equals(link))
			return name;
		
		// no name available, return question marks, 
		// TODO is there any default identifier that could be used?
		if (name == null)
			return "???";
		
		StringBuilder sb = new StringBuilder(5);
		
		sb.append("<a href=\"");
		sb.append(link);
		sb.append("\">");
		
		sb.append(name);
		sb.append("</a>");
		
		return sb.toString();
	}
}
