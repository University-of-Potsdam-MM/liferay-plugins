package de.unipotsdam.elis.portfolio.util.jsp;

import javax.servlet.jsp.PageContext;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
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
	
	public static String getFeedbackStatusString(PageContext pageContext, int feedbackStatus){
		if (feedbackStatus == PortfolioStatics.FEEDBACK_REQUESTED)
			return LanguageUtil.get(pageContext, "portfolio-feedback-requested");
		if (feedbackStatus == PortfolioStatics.FEEDBACK_DELIVERED)
			return LanguageUtil.get(pageContext, "portfolio-feedback-delivered");
		else
			return LanguageUtil.get(pageContext, "portfolio-no-feedback-requested");
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
}
