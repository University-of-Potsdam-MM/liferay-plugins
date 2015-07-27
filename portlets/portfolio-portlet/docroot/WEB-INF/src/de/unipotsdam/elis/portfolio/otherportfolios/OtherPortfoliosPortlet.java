package de.unipotsdam.elis.portfolio.otherportfolios;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.Constants;
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
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;
import de.unipotsdam.elis.portfolio.util.jsp.JspHelper;

/**
 * Portlet implementation class OtherPortfoliosPortlet
 */
public class OtherPortfoliosPortlet extends MVCPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		try {
			if (cmd.equals("getPortfoliosForUser"))
				getPortfoliosForUser(resourceRequest, resourceResponse);
			if (cmd.equals("markAsFeedbackDelivered"))
				markAsFeedbackDelivered(resourceRequest, resourceResponse);
			if (cmd.equals("getGlobalPortfolios"))
				getGlobalPortfolios(resourceRequest, resourceResponse);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns portfolios published to the user
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void getPortfoliosForUser(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByPortfolioFeedbackUserId(themeDisplay
				.getUserId());
		JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
		for (Portfolio portfolio : portfolios) {
			JspHelper.addToPortfolioFeedbackJSONArray(portfolioJSONArray, portfolio, themeDisplay, portletConfig);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(portfolioJSONArray.toString());
	}

	/**
	 * Marks a portfolio as feedback delivered
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void markAsFeedbackDelivered(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.updateFeedbackStatus(themeDisplay.getUserId(), PortfolioStatics.FEEDBACK_DELIVERED);
		String message = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
				"portfolio-portfolio-feedback-delivered", new Object[] { themeDisplay.getUser().getFullName(),
						portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
		User portfolioOwner = UserLocalServiceUtil.getUser(portfolio.getLayout().getUserId());
		String portfolioLink = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), portfolioOwner);
		JspHelper.sendPortfolioNotification(portfolioOwner, themeDisplay.getUser(), message, portfolioLink,
				ServiceContextFactory.getInstance(resourceRequest));
	}

	/**
	 * Returns portfolios published globally
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void getGlobalPortfolios(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByPortfolioFeedbackUserId(themeDisplay
				.getUserId());
		JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
		for (Portfolio portfolio : portfolios) {
			JspHelper.addGlobalPortfolioToJSONArray(portfolioJSONArray, portfolio, themeDisplay);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(portfolioJSONArray.toString());
	}

}
