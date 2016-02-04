package de.unipotsdam.elis.custompages.util.jsp;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import com.liferay.compat.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.notifications.MyCustomPagesNotificationHandler;
import de.unipotsdam.elis.custompages.util.CustomPageUtil;

/**
 * Helper for the JSP pages.
 * 
 * @author Matthias
 *
 */
public class JspHelper {

	public static String getFeedbackStatusString(PortletConfig portletConfig, Locale locale, int feedbackStatus) {
		if (feedbackStatus == CustomPageStatics.FEEDBACK_REQUESTED)
			return LanguageUtil.get(portletConfig, locale, "custompages-feedback-requested");
		if (feedbackStatus == CustomPageStatics.FEEDBACK_DELIVERED)
			return LanguageUtil.get(portletConfig, locale, "custompages-feedback-delivered");
		else
			return LanguageUtil.get(portletConfig, locale, "custompages-no-feedback-requested");
	}

	public static void sendCustomPageNotification(User recipient, User sender, String message, String customPageLink,
			ServiceContext serviceContext) throws PortalException, SystemException {

		JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		payloadJSON.put("userId", sender.getUserId());
		payloadJSON.put("url", customPageLink);
		payloadJSON.put("message", message);

		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), MyCustomPagesNotificationHandler.PORTLET_ID, payloadJSON);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(recipient.getUserId(), notificationEvent);
	}

	public static void handleSocialActivities(Layout customPage, PortletRequest request, User receiver, int activityType)
			throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		String portletId = PortalUtil.getPortletId(request);
		String customPageURL = PortalUtil.getLayoutFullURL(customPage, themeDisplay);
		String notificationMessage;
		int socialActivityType;
		if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_PUBLISHED) {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-published-message", new Object[] { themeDisplay.getUser().getFullName(),
							customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_PUBLISHED;
		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-feedback-requested-message", new Object[] {
							themeDisplay.getUser().getFullName(), customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_REQUESTED;
		} else {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-feedback-delivered", new Object[] { themeDisplay.getUser().getFullName(),
							customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_DELIVERED;

		}
		createCustomPageActivity(customPage, themeDisplay.getUserId(), receiver.getUserId(), socialActivityType);
		createCustomPageNotification(themeDisplay.getUser(), receiver, notificationMessage, customPageURL, portletId);
	}

	public static void createCustomPageActivity(Layout layout, long userId, long receiverUserId, int socialActivityType)
			throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", layout.getUserId());
		jsonObject.put("title", layout.getName());
		SocialActivityLocalServiceUtil.addActivity(userId, 0, Layout.class.getName(), layout.getPlid(),
				socialActivityType, jsonObject.toString(), receiverUserId);
	}

	private static void createCustomPageNotification(User sender, User receiver, String message, String customPageURL,
			String portletId) throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", sender.getUserId());
		jsonObject.put("url", customPageURL);
		jsonObject.put("message", message);
		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), portletId, jsonObject);
		notificationEvent.setDeliveryRequired(0);
		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(receiver.getUserId(), notificationEvent);
	}

	public static void addToCustomPageJSONArray(JSONArray customPageJSONArray, Layout customPage, ThemeDisplay themeDisplay)
			throws PortalException, SystemException {
		JSONObject customPageJSON = JSONFactoryUtil.createJSONObject();
		customPageJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageJSON.put("plid", customPage.getPlid());
		customPageJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageJSON.put("lastChangesInMilliseconds", customPage.getModifiedDate().getTime());
		customPageJSON.put("isGlobal", CustomPageUtil.isPublishedGlobal(customPage.getPlid()));
		JSONArray customPageFeedbackJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject customPageFeedbackJSON = null;
		boolean inFeedbackProcess = false;
		for (CustomPageFeedback customPageFeedback : CustomPageUtil.getCustomPageFeedbacks(customPage.getPlid())) {
			customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
			customPageFeedbackJSON.put("userId", customPageFeedback.getUserId());
			customPageFeedbackJSON.put("userName", customPageFeedback.getUser().getFullName());
			customPageFeedbackJSON.put("feedbackStatus", customPageFeedback.getFeedbackStatus());
			customPageFeedbackJSON.put(
					"creationDate",
					FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
							customPageFeedback.getCreateDate()));
			customPageFeedbackJSON.put(
					"modifiedDate",
					FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
							customPageFeedback.getModifiedDate()));
			customPageFeedbackJSONArray.put(customPageFeedbackJSON);
			if (customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_REQUESTED)
				inFeedbackProcess = true;
		}
		customPageJSON.put("inFeedbackProcess", inFeedbackProcess);
		customPageJSON.put("customPageFeedbacks", customPageFeedbackJSONArray);
		customPageJSONArray.put(customPageJSON);
	}

	public static void publicAddToCustomPageJSONArray(JSONArray customPageJSONArray, Layout customPage,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject customPageJSON = JSONFactoryUtil.createJSONObject();
		customPageJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageJSON.put("lastChangesInMilliseconds", customPage.getModifiedDate().getTime());
		customPageJSON.put(
				"templateName",
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(
						customPage.getLayoutPrototypeUuid(), themeDisplay.getCompanyId()).getName(
						themeDisplay.getLocale()));
		customPageJSONArray.put(customPageJSON);
	}

	public static void addToCustomPageFeedbackJSONArray(JSONArray customPageFeedbackJSONArray, Layout customPage,
			ThemeDisplay themeDisplay, PortletConfig portletConfig) throws PortalException, SystemException {
		JSONObject customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON.put("plid", customPage.getPlid());
		customPageFeedbackJSON.put("userId", customPage.getUserId());
		customPageFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(customPage.getUserId()).getFullName());
		customPageFeedbackJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		CustomPageFeedback customPageFeedback = CustomPageUtil.getCustomPageFeedback(customPage.getPlid(),
				themeDisplay.getUserId());
		customPageFeedbackJSON.put("feedbackStatus", customPageFeedback.getFeedbackStatus());
		customPageFeedbackJSON
				.put("feedbackStatusString",
						getFeedbackStatusString(portletConfig, themeDisplay.getLocale(),
								customPageFeedback.getFeedbackStatus()));
		customPageFeedbackJSON.put("hidden", customPageFeedback.isHidden());
		customPageFeedbackJSON.put(
				"createDate",
				FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPageFeedback.getCreateDate()));
		customPageFeedbackJSONArray.put(customPageFeedbackJSON);
	}

	public static void addGlobalCustomPageToJSONArray(JSONArray customPageFeedbackJSONArray, Layout customPage,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON.put("plid", customPage.getPlid());
		customPageFeedbackJSON.put("userId", customPage.getUserId());
		customPageFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(customPage.getUserId()).getFullName());
		customPageFeedbackJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageFeedbackJSON.put(
				"modifiedDate",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageFeedbackJSON.put("modifiedDateInMilliseconds", customPage.getModifiedDate().getTime());
		customPageFeedbackJSONArray.put(customPageFeedbackJSON);
	}

	public static Map<Locale, String> getLocaleMap(String key, PortletConfig portletConfig) {
		Map<Locale, String> result = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			result.put(locale, LanguageUtil.get(portletConfig, locale, key));
		}
		return result;
	}

	/**
	 * Returns the layout prototype with the given name
	 * 
	 * @param name
	 * @return layout prototype
	 * @throws SystemException
	 */
	public static Map<String, LayoutPrototype> getCustomPageLayoutPrototypes() throws SystemException {
		Map<String, LayoutPrototype> result = new HashMap<String, LayoutPrototype>();
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());

		for (LayoutPrototype lp : layoutPrototypes) {
			if (lp.getName(Locale.GERMAN).equals(CustomPageStatics.BLOG_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(CustomPageStatics.CDP_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(CustomPageStatics.WIKI_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(CustomPageStatics.EMPTY_LAYOUT_PROTOTYPE)) {
				result.put(lp.getName(Locale.GERMAN), lp);
			}
		}
		return result;
	}
}
