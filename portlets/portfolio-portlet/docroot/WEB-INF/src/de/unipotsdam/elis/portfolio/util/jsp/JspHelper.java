package de.unipotsdam.elis.portfolio.util.jsp;

import java.util.Locale;

import javax.portlet.PortletConfig;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.notifications.PortfolioNotificationHandler;

/**
 * Helper for the JSP pages.
 * 
 * @author Matthias
 *
 */
public class JspHelper {
	//
	/**
	 * Creates the URL referring to the given portfolio. Expects that the
	 * portfolio pages are placed in the public area of the user.
	 * 
	 * @param themeDisplay
	 * @param portfolio
	 * @param user
	 * @return portfolio URL
	 */
	public static String getPortfolioURL(ThemeDisplay themeDisplay, Layout portfolio, User user) {
		return themeDisplay.getURLPortal() + themeDisplay.getPathFriendlyURLPublic() + "/" + user.getScreenName()
				+ portfolio.getFriendlyURL(themeDisplay.getLocale());
	}

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
		payloadJSON.put("portfolioLink", portfolioLink);
		payloadJSON.put("message", message);

		com.liferay.portal.kernel.notifications.NotificationEvent notificationEvent = NotificationEventFactoryUtil
				.createNotificationEvent(System.currentTimeMillis(), PortfolioNotificationHandler.PORTLET_ID,
						payloadJSON);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(recipient.getUserId(), notificationEvent);
	}

	public static void addToPortfolioJSONArray(JSONArray portfolioJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject portfolioJSON = JSONFactoryUtil.createJSONObject();
		portfolioJSON.put("title", portfolio.getLayout().getTitle(themeDisplay.getLocale()));
		portfolioJSON
				.put("url", JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), themeDisplay.getUser()));
		portfolioJSON.put("plid", portfolio.getPlid());
		portfolioJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolio.getLayout().getModifiedDate()));
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

	public static void addToPortfolioFeedbackJSONArray(JSONArray portfolioFeedbackJSONArray, Portfolio portfolio,
			ThemeDisplay themeDisplay, PortletConfig portletConfig) throws PortalException, SystemException {
		JSONObject portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON = JSONFactoryUtil.createJSONObject();
		portfolioFeedbackJSON.put("plid", portfolio.getPlid());
		portfolioFeedbackJSON.put("userId", portfolio.getLayout().getUserId());
		portfolioFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(portfolio.getLayout().getUserId())
				.getFullName());
		portfolioFeedbackJSON.put("title", portfolio.getLayout().getTitle(themeDisplay.getLocale()));
		portfolioFeedbackJSON.put("url",
				JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), UserLocalServiceUtil.getUser(portfolio.getLayout().getUserId())));
		PortfolioFeedback portfolioFeedback = portfolio.getPortfolioFeedback(themeDisplay.getUserId());
		portfolioFeedbackJSON.put("feedbackStatus", portfolioFeedback.getFeedbackStatus());
		portfolioFeedbackJSON.put("feedbackStatusString",
				getFeedbackStatusString(portletConfig, themeDisplay.getLocale(), portfolioFeedback.getFeedbackStatus()));
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
		portfolioFeedbackJSON.put("title", portfolio.getLayout().getTitle(themeDisplay.getLocale()));
		portfolioFeedbackJSON.put("url",
				getPortfolioURL(themeDisplay, portfolio.getLayout(), UserLocalServiceUtil.getUser(portfolio.getLayout().getUserId())));
		portfolioFeedbackJSON.put(
				"modifiedDate",
				FastDateFormatFactoryUtil.getDate(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						portfolio.getLayout().getModifiedDate()));
		portfolioFeedbackJSONArray.put(portfolioFeedbackJSON);
	}
}
