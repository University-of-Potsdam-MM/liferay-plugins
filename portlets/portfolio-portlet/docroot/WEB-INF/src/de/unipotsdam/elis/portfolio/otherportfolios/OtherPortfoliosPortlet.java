package de.unipotsdam.elis.portfolio.otherportfolios;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;
import de.unipotsdam.elis.portfolio.util.jsp.JspHelper;

/**
 * Portlet implementation class OtherPortfoliosPortlet
 */
public class OtherPortfoliosPortlet extends MVCPortlet {

	public void removePortfolioFeedback(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException, SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(plid, themeDisplay.getUserId());
	}

	public void markAsFeedbackDelivered(ActionRequest actionRequest, ActionResponse actionResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.updateFeedbackStatus(themeDisplay.getUserId(), PortfolioStatics.FEEDBACK_DELIVERED);
		String message = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
				"portfolio-portfolio-feedback-delivered", new Object[] { themeDisplay.getUser().getFullName(),
						portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
		User portfolioOwner = UserLocalServiceUtil.getUser(portfolio.getLayout().getUserId());
		String portfolioLink = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), portfolioOwner);
		JspHelper.sendPortfolioNotification(portfolioOwner, themeDisplay.getUser(), message, portfolioLink,
				ServiceContextFactory.getInstance(actionRequest));
	}

	public void filterPortfolios(ActionRequest actionRequest, ActionResponse actionResponse) {
		String filterValue = ParamUtil.getString(actionRequest, "filterValue");
		actionResponse.setRenderParameter("filterValue", filterValue);
		String tab = ParamUtil.getString(actionRequest, "myParam");
		if (tab != null)
			actionResponse.setRenderParameter("myParam", tab);

	}

}
