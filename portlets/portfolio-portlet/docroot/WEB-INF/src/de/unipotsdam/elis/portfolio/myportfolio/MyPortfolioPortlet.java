package de.unipotsdam.elis.portfolio.myportfolio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.portfolio.permission.PortfolioPermissionManager;
import de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalServiceUtil;

public class MyPortfolioPortlet extends MVCPortlet {

	/**
	 * Publishes the portfolio to a user.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	public void publishPortfolioToUser(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException, SystemException, PortalException {
		String userName = ParamUtil.getString(actionRequest, "userName");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user;
		// TODO: ScreenName eindeutig?
		user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);

		if (user == null) {
			addErrorMessage(actionRequest, actionResponse, "error-not-a-user");
		} else {
			long plid = ParamUtil.getLong(actionRequest, "plid");
			boolean success = PortfolioPermissionManager.publishPortfolioToUser(plid, user.getUserId());
			if (!success) {
				addErrorMessage(actionRequest, actionResponse, "error");
			}
		}
	}

	/**
	 * Adds a error message, hides the default error message and redirects to
	 * the current page.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @param errorKey
	 * @throws IOException
	 */
	private void addErrorMessage(ActionRequest actionRequest, ActionResponse actionResponse, String errorKey)
			throws IOException {
		SessionErrors.add(actionRequest, errorKey);
		// hide default error message
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		SessionMessages.add(actionRequest, ((LiferayPortletConfig) portletConfig).getPortletId()
				+ SessionMessages.KEY_SUFFIX_HIDE_DEFAULT_ERROR_MESSAGE);
		// redirect to current page
		String redirect = getRedirect(actionRequest, actionResponse);
		actionResponse.sendRedirect(redirect);
	}

	/**
	 * Retracts the publishment of a portfolio to a user.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 * @throws PortalException
	 */
	public void removeUser(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException, SystemException, PortalException {
		String userName = ParamUtil.getString(actionRequest, "userName");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		User user;
		// TODO: ScreenName eindeutig?
		user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);

		if (user == null) {
			throw new SystemException("User unknown");
		} else {
			long plid = ParamUtil.getLong(actionRequest, "plid");
			PortfolioPermissionManager.removeUserFromPortfolio(plid, user.getUserId());
		}
	}

	/**
	 * Deletes the portfolio.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static void deletePortfolio(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {
		// TODO: Sollte nicht löschbar sein, wenn es sich im Feedbackprozess
		// befindet.
		long plid = Long.valueOf(ParamUtil.getString(actionRequest, "plid"));
		PortfolioPermissionLocalServiceUtil.deletePortfolioPermissionByPlid(plid);
		LayoutLocalServiceUtil.deleteLayout(plid, ServiceContextFactory.getInstance(actionRequest));
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals("get_users")) {
			getUsers(resourceRequest, resourceResponse);
		}

	}

	private void getUsers(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		JSONArray usersJSONArray = JSONFactoryUtil.createJSONArray();
		String userEmail = ParamUtil.getString(resourceRequest, "userEmail");
		DynamicQuery userQuery = DynamicQueryFactoryUtil.forClass(User.class, PortalClassLoaderUtil.getClassLoader());
		Criterion criterion = RestrictionsFactoryUtil.like("screenName", StringPool.PERCENT + userEmail
				+ StringPool.PERCENT);
		userQuery.add(criterion);
		JSONObject userJSON = null;
		try {
			List<User> userList = UserLocalServiceUtil.dynamicQuery(userQuery, 0, 5);
			for (User user : userList) {
				userJSON = JSONFactoryUtil.createJSONObject();
				userJSON.put("userId", user.getUserId());
				userJSON.put("email", user.getEmailAddress());
				userJSON.put("screenName", user.getScreenName());
				userJSON.put("fullName", user.getFullName());
				usersJSONArray.put(userJSON);
			}
		} catch (Exception e) {

		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(usersJSONArray.toString());
	}

	public void createPortfolio(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException, SystemException, PortalException {
		String portfolioName = ParamUtil.getString(actionRequest, "portfolioName");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		// Get Portfolio page
		// TODO: eindeutigeren weg vielleicht?
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getUser().getGroupId(), false);
		Layout portfolioPage = null;
		for (Layout layout : layouts) {
			if (layout.getName(themeDisplay.getLocale()).equals("Portfolio"))
				portfolioPage = layout;
		}
		if (portfolioPage != null) {
			long userId = themeDisplay.getUserId();
			long groupId = portfolioPage.getGroupId();
			boolean privateLayout = false;
			long parentLayoutId = portfolioPage.getLayoutId();
			String name = portfolioName;
			String title = portfolioName;
			String description = "";
			String type = LayoutConstants.TYPE_PORTLET;
			boolean hidden = false;
			String friendlyURL = "/" + portfolioName;
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), actionRequest);
			Layout newLayout = LayoutLocalServiceUtil.addLayout(userId, groupId, privateLayout, parentLayoutId, name,
					title, description, type, hidden, friendlyURL, serviceContext);
			LayoutPrototype portfolioPrototype = PortfolioPermissionManager.getPortfolioPrototype();
			newLayout.setLayoutPrototypeUuid(portfolioPrototype.getUuid());
			LayoutLocalServiceUtil.updateLayout(newLayout);
		}
	}
}
