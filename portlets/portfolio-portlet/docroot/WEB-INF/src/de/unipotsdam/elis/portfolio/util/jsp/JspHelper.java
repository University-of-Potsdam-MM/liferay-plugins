package de.unipotsdam.elis.portfolio.util.jsp;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import com.liferay.compat.portal.kernel.util.HtmlUtil;
import com.liferay.compat.portal.util.PortalUtil;
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
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.notifications.MyPortfolioNotificationHandler;

/**
 * Helper for the JSP pages.
 * 
 * @author Matthias
 *
 */
public class JspHelper {

	public static String getFeedbackStatusString(PortletConfig portletConfig, Locale locale, int feedbackStatus) {
		if (feedbackStatus == PortfolioStatics.FEEDBACK_REQUESTED)
			return LanguageUtil.get(portletConfig, locale, "portfolio-feedback-requested");
		if (feedbackStatus == PortfolioStatics.FEEDBACK_DELIVERED)
			return LanguageUtil.get(portletConfig, locale, "portfolio-feedback-delivered");
		else
			return LanguageUtil.get(portletConfig, locale, "portfolio-no-feedback-requested");
	}

	public static void sendPortfolioNotification(User recipient, User sender, String message, String portfolioLink,
			ServiceContext serviceContext) throws PortalException, SystemException {

		JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		payloadJSON.put("userId", sender.getUserId());
		payloadJSON.put("url", portfolioLink);
		payloadJSON.put("message", message);

		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), MyPortfolioNotificationHandler.PORTLET_ID, payloadJSON);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(recipient.getUserId(), notificationEvent);
	}

	public static void handleSocialActivities(Portfolio portfolio, PortletRequest request, User receiver,
			int activityType) throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		String portletId = PortalUtil.getPortletId(request);
		String portfolioURL = PortalUtil.getLayoutFullURL(portfolio.getLayout(), themeDisplay);
		String notificationMessage;
		int socialActivityType;
		if (activityType == PortfolioStatics.MESSAGE_TYPE_PORTFOLIO_PUBLISHED) {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"portfolio-portfolio-published-message", new Object[] { themeDisplay.getUser().getFullName(),
							portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_PUBLISHED;
		} else if (activityType == PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			notificationMessage = LanguageUtil.format(
					portletConfig,
					themeDisplay.getLocale(),
					"portfolio-portfolio-feedback-requested-message",
					new Object[] { themeDisplay.getUser().getFullName(),
							portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_REQUESTED;
		} else {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"portfolio-portfolio-feedback-delivered", new Object[] { themeDisplay.getUser().getFullName(),
							portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_DELIVERED;

		}
		createPortfolioActivity(portfolio.getLayout(), themeDisplay.getUserId(), receiver.getUserId(),
				socialActivityType);
		createPortfolioNotification(themeDisplay.getUser(), receiver, notificationMessage, portfolioURL, portletId);
	}

	public static void createPortfolioActivity(Layout layout, long userId, long receiverUserId, int socialActivityType)
			throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", layout.getUserId());
		jsonObject.put("title", layout.getTitle());
		SocialActivityLocalServiceUtil.addActivity(userId, 0, Portfolio.class.getName(), layout.getPlid(),
				socialActivityType, jsonObject.toString(), receiverUserId);
	}

	private static void createPortfolioNotification(User sender, User receiver, String message, String portfolioURL,
			String portletId) throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", sender.getUserId());
		jsonObject.put("url", portfolioURL);
		jsonObject.put("message", message);
		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), portletId, jsonObject);
		notificationEvent.setDeliveryRequired(0);
		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(receiver.getUserId(), notificationEvent);
	}

	public static void addToPortfolioJSONArray(JSONArray portfolioJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject portfolioJSON = JSONFactoryUtil.createJSONObject();
		portfolioJSON.put("title", HtmlUtil.escape(portfolio.getLayout().getTitle(themeDisplay.getLocale())));
		portfolioJSON.put("url", PortalUtil.getLayoutFullURL(portfolio.getLayout(), themeDisplay));
		portfolioJSON.put("plid", portfolio.getPlid());
		portfolioJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolio.getLayout().getModifiedDate()));
		portfolioJSON.put("lastChangesInMilliseconds", portfolio.getLayout().getModifiedDate().getTime());
		portfolioJSON.put("isGlobal", portfolio.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL);
		JSONArray portfolioFeedbackJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject portfolioFeedbackJSON = null;
		boolean inFeedbackProcess = false;
		for (PortfolioFeedback portfolioFeedback : portfolio.getPortfolioFeedbacks()) {
			portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
			portfolioFeedbackJSON.put("userId", portfolioFeedback.getUserId());
			portfolioFeedbackJSON.put("userName", portfolioFeedback.getUser().getFullName());
			portfolioFeedbackJSON.put("feedbackStatus", portfolioFeedback.getFeedbackStatus());
			portfolioFeedbackJSON.put(
					"creationDate",
					FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
							portfolioFeedback.getCreateDate()));
			portfolioFeedbackJSON.put(
					"modifiedDate",
					FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
							portfolioFeedback.getModifiedDate()));
			portfolioFeedbackJSONArray.put(portfolioFeedbackJSON);
			if (portfolioFeedback.getFeedbackStatus() == PortfolioStatics.FEEDBACK_REQUESTED)
				inFeedbackProcess = true;
		}
		portfolioJSON.put("inFeedbackProcess", inFeedbackProcess);
		portfolioJSON.put("portfolioFeedbacks", portfolioFeedbackJSONArray);
		portfolioJSONArray.put(portfolioJSON);
	}

	public static void publicAddToPortfolioJSONArray(JSONArray portfolioJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject portfolioJSON = JSONFactoryUtil.createJSONObject();
		portfolioJSON.put("title", HtmlUtil.escape(portfolio.getLayout().getTitle(themeDisplay.getLocale())));
		portfolioJSON.put("url", PortalUtil.getLayoutFullURL(portfolio.getLayout(), themeDisplay));
		portfolioJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolio.getLayout().getModifiedDate()));
		portfolioJSON.put("lastChangesInMilliseconds", portfolio.getLayout().getModifiedDate().getTime());
		portfolioJSON.put(
				"templateName",
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypeByUuidAndCompanyId(
						portfolio.getLayout().getLayoutPrototypeUuid(), themeDisplay.getCompanyId()).getName(
						themeDisplay.getLocale()));
		portfolioJSONArray.put(portfolioJSON);
	}

	public static void addToPortfolioFeedbackJSONArray(JSONArray portfolioFeedbackJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay, PortletConfig portletConfig) throws PortalException, SystemException {
		JSONObject portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON.put("plid", portfolio.getPlid());
		portfolioFeedbackJSON.put("userId", portfolio.getLayout().getUserId());
		portfolioFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(portfolio.getLayout().getUserId())
				.getFullName());
		portfolioFeedbackJSON.put("title", HtmlUtil.escape(portfolio.getLayout().getTitle(themeDisplay.getLocale())));
		portfolioFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(portfolio.getLayout(), themeDisplay));
		PortfolioFeedback portfolioFeedback = portfolio.getPortfolioFeedback(themeDisplay.getUserId());
		portfolioFeedbackJSON.put("feedbackStatus", portfolioFeedback.getFeedbackStatus());
		portfolioFeedbackJSON
				.put("feedbackStatusString",
						getFeedbackStatusString(portletConfig, themeDisplay.getLocale(),
								portfolioFeedback.getFeedbackStatus()));
		portfolioFeedbackJSON.put("hidden", portfolioFeedback.isHidden());
		portfolioFeedbackJSON.put(
				"createDate",
				FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolioFeedback.getCreateDate()));
		portfolioFeedbackJSONArray.put(portfolioFeedbackJSON);
	}

	public static void addGlobalPortfolioToJSONArray(JSONArray portfolioFeedbackJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON.put("plid", portfolio.getPlid());
		portfolioFeedbackJSON.put("userId", portfolio.getLayout().getUserId());
		portfolioFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(portfolio.getLayout().getUserId())
				.getFullName());
		portfolioFeedbackJSON.put("title", HtmlUtil.escape(portfolio.getLayout().getTitle(themeDisplay.getLocale())));
		portfolioFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(portfolio.getLayout(), themeDisplay));
		portfolioFeedbackJSON.put(
				"modifiedDate",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolio.getLayout().getModifiedDate()));
		portfolioFeedbackJSON.put("modifiedDateInMilliseconds", portfolio.getLayout().getModifiedDate().getTime());
		portfolioFeedbackJSONArray.put(portfolioFeedbackJSON);
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
	public static Map<String, LayoutPrototype> getPortfolioLayoutPrototypes() throws SystemException {
		Map<String, LayoutPrototype> result = new HashMap<String, LayoutPrototype>();
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());

		for (LayoutPrototype lp : layoutPrototypes) {
			if (lp.getName(Locale.GERMAN).equals(PortfolioStatics.BLOG_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(PortfolioStatics.CDP_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(PortfolioStatics.WIKI_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMAN).equals(PortfolioStatics.EMPTY_LAYOUT_PROTOTYPE)) {
				result.put(lp.getName(Locale.GERMAN), lp);
			}
		}
		return result;
	}
}
