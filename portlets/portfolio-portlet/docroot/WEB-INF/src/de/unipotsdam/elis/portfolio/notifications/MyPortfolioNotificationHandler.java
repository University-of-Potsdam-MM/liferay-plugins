package de.unipotsdam.elis.portfolio.notifications;

import com.liferay.compat.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.ServiceContext;

public class MyPortfolioNotificationHandler extends BaseUserNotificationHandler {
	
	public static String PORTLET_ID = "myportfolio_WAR_portfolioportlet";

	public MyPortfolioNotificationHandler() {

		setPortletId(PORTLET_ID);

	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		String message = jsonObject.getString("message");

		String body = StringUtil.replace(getBodyTemplate(), new String[] { "[$TITLE$]" }, new String[] { message });

		return body;
	}

	@Override
	protected String getLink(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		String url = jsonObject.getString("url");

		return url;
	}

	protected String getBodyTemplate() throws Exception {
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\">[$TITLE$]</div>");
		return sb.toString();
	}

}
