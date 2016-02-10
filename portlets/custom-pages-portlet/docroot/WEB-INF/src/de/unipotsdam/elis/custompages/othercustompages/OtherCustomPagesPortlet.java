package de.unipotsdam.elis.custompages.othercustompages;

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
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;
import de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK;
import de.unipotsdam.elis.custompages.util.jsp.JspHelper;

/**
 * Portlet implementation class OtherCustomPagesPortlet
 */
public class OtherCustomPagesPortlet extends MVCPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		try {
			if (cmd.equals("getCustomPagesForUser"))
				getCustomPagesForUser(resourceRequest, resourceResponse);
			if (cmd.equals("markAsFeedbackDelivered"))
				markAsFeedbackDelivered(resourceRequest, resourceResponse);
			if (cmd.equals("getGlobalCustomPages"))
				getGlobalCustomPages(resourceRequest, resourceResponse);
			if (cmd.equals("changeVisibility"))
				changeVisibility(resourceRequest, resourceResponse);
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns custom pages published to the user
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void getCustomPagesForUser(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) resourceRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		List<Layout> customPages = CustomPageFeedbackLocalServiceUtil
				.getCustomPagesByPageTypeAndCustomPageFeedbackUserId(CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE,
						themeDisplay.getUserId());
		JSONArray customPageJSONArray = JSONFactoryUtil.createJSONArray();
		for (Layout customPage : customPages) {
			JspHelper.addToCustomPageFeedbackJSONArray(customPageJSONArray, customPage, themeDisplay, portletConfig);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(customPageJSONArray.toString());
	}

	/**
	 * Marks a custom page as feedback delivered
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void markAsFeedbackDelivered(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedbackStatus(plid, themeDisplay.getUserId(),
				CustomPageStatics.FEEDBACK_DELIVERED);
		User customPageOwner = UserLocalServiceUtil.getUser(customPage.getUserId());
		JspHelper.handleSocialActivities(customPage, resourceRequest, customPageOwner,
				CustomPageStatics.MESSAGE_TYPE_FEEDBACK_DELIVERED);
	}

	/**
	 * Returns custom pages published globally
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void getGlobalCustomPages(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortletException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Layout> customPages = CustomPageFeedbackLocalServiceUtil.getCustomPagesByPageTypeAndCustomPageFeedbackUserId(CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE, themeDisplay.getUserId());
		JSONArray customPageJSONArray = JSONFactoryUtil.createJSONArray();
		for (Layout customPage : customPages) {
			JspHelper.addGlobalCustomPageToJSONArray(customPageJSONArray, customPage, themeDisplay);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(customPageJSONArray.toString());
	}

	private void changeVisibility(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		CustomPageFeedback customPageFeedback = CustomPageFeedbackLocalServiceUtil.getCustomPageFeedback(new CustomPageFeedbackPK(plid, themeDisplay.getUserId()));
		customPageFeedback.setHidden(!customPageFeedback.getHidden());
		CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedback(customPageFeedback);
	}

}
