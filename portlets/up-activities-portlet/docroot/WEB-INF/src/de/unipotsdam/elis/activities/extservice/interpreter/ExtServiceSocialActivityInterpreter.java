package de.unipotsdam.elis.activities.extservice.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.model.SocialActivitySet;

public abstract class ExtServiceSocialActivityInterpreter {

	public abstract String getActivityTitle(SocialActivitySet activitySet, JSONObject data, ServiceContext serviceContext);
	
//	public abstract String getBody(JSONObject data, ThemeDisplay themeDisplay);
	public String getBody(JSONObject data, ThemeDisplay themeDisplay){
	
		String loginName = data.getJSONObject("actor").getString("id");

		User activitySetUser = null;
		String userDisplayURL = StringPool.BLANK;
		String userFullName = StringPool.BLANK;
		String userPortraitURL = StringPool.BLANK;
		
		try {
			activitySetUser = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), loginName);
			if (activitySetUser != null) {
				userDisplayURL = activitySetUser.getDisplayURL(themeDisplay);
				userFullName = activitySetUser.getFullName();
				userPortraitURL = activitySetUser.getPortraitURL(themeDisplay);
				
			} else {
				
				userPortraitURL = "/image/user_male_portrait";
			}
		} catch (Exception e) {}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("<div class=\"user-portrait\">");
		sb.append("<span class=\"avatar\">");
		if (activitySetUser != null){
			sb.append("<a href=\""+userDisplayURL+"\">");
			sb.append("<img alt=\""+HtmlUtil.escape(userFullName)+"\" src=\""+userPortraitURL+"\" />");
			sb.append("</a>");
		} else {
			sb.append("<a>");
			sb.append("<img src=\""+userPortraitURL+"\" />");
			sb.append("</a>");
		}
		sb.append("</span>");
		sb.append("</div>");
		
		return sb.toString();
	}

	protected String wrapLink(String link, String iconPath, String text) {
		StringBuilder sb = new StringBuilder();

		sb.append("<a href=\"");
		sb.append(link);
		sb.append("\">");
		sb.append("<span><img class=\"icon\" src=\"");
		sb.append(iconPath);
		sb.append("\"></span><span>");
		sb.append(text);
		sb.append("</span>");
		sb.append("</a>");

		return sb.toString();
	}
}
