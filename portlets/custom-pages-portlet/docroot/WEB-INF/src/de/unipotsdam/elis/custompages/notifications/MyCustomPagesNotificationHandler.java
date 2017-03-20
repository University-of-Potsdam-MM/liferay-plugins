package de.unipotsdam.elis.custompages.notifications;

import javax.portlet.ActionRequest;
import javax.portlet.PortletConfig;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.servlet.ServletContext;

import com.liferay.compat.portal.kernel.notifications.BaseUserNotificationHandler;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationEvent;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.support.tomcat.session.SessionLessManagerBase;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.custompages.CustomPageStatics;

public class MyCustomPagesNotificationHandler extends BaseUserNotificationHandler {
	
	public static String PORTLET_ID = "mycustompages_WAR_custompagesportlet";

	public MyCustomPagesNotificationHandler() {

		setPortletId(PORTLET_ID);

	}

	@Override
	protected String getBody(UserNotificationEvent userNotificationEvent, ServiceContext serviceContext)
			throws Exception {

		setActionable(false); // use notification template per default
		
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(userNotificationEvent.getPayload());

		String message = jsonObject.getString("message");

		int socialActivityType = jsonObject.getInt("socialActivityType");
		long plid = jsonObject.getLong("plid");
		
		// removed due to #747
//		if (socialActivityType == ExtendedSocialActivityKeyConstants.CUSTOM_PAGE_FEEDBACK_REQUESTED) {
//			setActionable(true); // use request template
//			return doAsRequest(jsonObject, userNotificationEvent, serviceContext);
//		}
		
		long userId = jsonObject.getLong("userId");
		
		message = getTitle(serviceContext, socialActivityType, plid, userId);
		
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

	@Override
	protected String getBodyTemplate() throws Exception {
		if (isActionable()) {		
			/*
			 * used template from superclass:
			 * <div class="title">[$TITLE$]</div>
			 * <div class="body">
			 *   <a class="btn btn-action btn-success" href="[$CONFIRM_URL$]">[$CONFIRM$]</a>
			 *   <a class="btn btn-action btn-warning" href="[$IGNORE_URL$]">[$IGNORE$]</a>
			 * </div> 
			 */
			return super.getBodyTemplate();
		}
		else {
			StringBundler sb = new StringBundler(5);
			sb.append("<div class=\"title\">[$TITLE$]</div>");
			return sb.toString();
		}
	}

	/**
	 * This method fills the request template with concrete data.   
	 * @param jsonObject
	 * @param serviceContext
	 * @return
	 * @throws Exception
	 */
	private String doAsRequest (JSONObject jsonObject, 
			UserNotificationEvent userNotificationEvent, ServiceContext serviceContext) 
		throws Exception {
		
		String message = jsonObject.getString("message");
		long plid = jsonObject.getLong("plid");
		
		LiferayPortletResponse liferayPortletResponse =
				serviceContext.getLiferayPortletResponse();

		// confirm button: mark as feedback delivered
		PortletURL confirmURL = liferayPortletResponse
				.createActionURL(OtherCustomPagesNotificationHandler.PORTLET_ID); 

		confirmURL.setParameter(ActionRequest.ACTION_NAME,
				"markAsFeedbackDelivered"); 
		confirmURL.setParameter("customPagePlid",
				String.valueOf(plid)); 
		confirmURL.setParameter(
				"userNotificationEventId",
				String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		confirmURL.setWindowState(WindowState.NORMAL);

		// ignore button: do nothing, just remove notification
		PortletURL ignoreURL = liferayPortletResponse
				.createActionURL(OtherCustomPagesNotificationHandler.PORTLET_ID);

		ignoreURL.setParameter(ActionRequest.ACTION_NAME,
				"ignoreFeedbackDelivery"); 
		ignoreURL.setParameter("customPagePlid",
				String.valueOf(plid)); 
		ignoreURL.setParameter(
				"userNotificationEventId",
				String.valueOf(userNotificationEvent.getUserNotificationEventId()));
		ignoreURL.setWindowState(WindowState.NORMAL);
		
		return StringUtil.replace(
			getBodyTemplate(),
			new String[] {
				"[$CONFIRM$]", "[$CONFIRM_URL$]", "[$IGNORE$]",
				"[$IGNORE_URL$]", "[$TITLE$]"
			},
			new String[] {
				serviceContext.translate("confirm"), confirmURL.toString(),
				serviceContext.translate("ignore"), ignoreURL.toString(), message
			});
	}
	
	private String getTitle(ServiceContext serviceContext, int activityType, long plid, long userId) 
			throws PortalException, SystemException {
		
		// getting portletConfig from serviceContext does not return correct portlet Property. So create it manually...
		Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				PORTLET_ID);
		ServletContext servletContext =
				(ServletContext)serviceContext.getAttribute(WebKeys.CTX);
		PortletConfig portletConfig =  PortletConfigFactoryUtil
				.create(portlet, servletContext);
		
		User user = UserLocalServiceUtil.getUser(userId);
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
				
		String notificationMessage = StringPool.BLANK;
		
		if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_PUBLISHED) {
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-published-message", new Object[] { user.getFullName(),
							customPage.getName(serviceContext.getLocale()) });

		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-feedback-requested-message", new Object[] {
							user.getFullName(), customPage.getName(serviceContext.getLocale()) });

		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_FEEDBACK_DELIVERED) {
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-feedback-delivered", new Object[] { user.getFullName(),
							customPage.getName(serviceContext.getLocale()) });

		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_DELETED){
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-deleted", new Object[] { user.getFullName(),
							customPage.getName(serviceContext.getLocale()) });

		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_DELETED_SHARE){
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-deleted-share", new Object[] { user.getFullName(),
							customPage.getName(serviceContext.getLocale()) });

		} else /*if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_DELETED_SUBMISSION)*/{
			notificationMessage = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"custompages-custom-page-deleted-publish", new Object[] { user.getFullName(),
							customPage.getName(serviceContext.getLocale()) });

		}
		
		return notificationMessage;
	}
}
