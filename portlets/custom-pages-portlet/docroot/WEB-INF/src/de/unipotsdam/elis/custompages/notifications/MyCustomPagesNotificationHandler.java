package de.unipotsdam.elis.custompages.notifications;

import com.liferay.compat.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

public class MyCustomPagesNotificationHandler extends BaseUserNotificationHandler {
	
	public static String PORTLET_ID = "mycustompages_WAR_custompagesportlet";

	public MyCustomPagesNotificationHandler() {

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

		long plid = jsonObject.getLong("plid");
		
		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);
		
		if (layout != null)
			return PortalUtil.getLayoutFullURL(layout, serviceContext.getThemeDisplay());

		return "";
	}

	protected String getBodyTemplate() throws Exception {
		StringBundler sb = new StringBundler(5);
		sb.append("<div class=\"title\">[$TITLE$]</div>");
		return sb.toString();
	}

}
