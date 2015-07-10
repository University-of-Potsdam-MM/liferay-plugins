package de.unipotsdam.elis.portfolio.otherportfolios;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

/**
 * Portlet implementation class OtherPortfoliosPortlet
 */
public class OtherPortfoliosPortlet extends MVCPortlet {

	public void removePortfolioFeedback(ActionRequest actionRequest, ActionResponse actionResponse)
			throws IOException, PortletException, SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(plid, themeDisplay.getUserId());
	}

	public void markAsFeedbackDelivered(ActionRequest actionRequest, ActionResponse actionResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.updateFeedbackStatus(themeDisplay.getUserId(), PortfolioStatics.FEEDBACK_DELIVERED);
	}

}
