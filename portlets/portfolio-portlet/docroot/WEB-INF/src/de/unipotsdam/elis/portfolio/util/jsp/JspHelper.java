package de.unipotsdam.elis.portfolio.util.jsp;

import javax.servlet.jsp.PageContext;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;

import de.unipotsdam.elis.portfolio.PortfolioStatics;

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
}
