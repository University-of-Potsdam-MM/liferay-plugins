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
	
	public abstract String getBody(JSONObject data, ThemeDisplay themeDisplay);
	
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
