package de.unipotsdam.elis.portfolio.myportfolio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;
import de.unipotsdam.elis.portfolio.util.jsp.JspHelper;

public class MyPortfolioPortlet extends MVCPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		try {
			if (cmd.equals("findUsers"))
				findUsers(resourceRequest, resourceResponse);
			else if (cmd.equals("userExists"))
				userExists(resourceRequest, resourceResponse);
			else if (cmd.equals("userHasViewPermission"))
				userHasViewPermission(resourceRequest, resourceResponse);
			else if (cmd.equals("feedbackRequested"))
				feedbackRequested(resourceRequest, resourceResponse);
			else if (cmd.equals("getUserPortfolios"))
				getUserPortfolios(resourceRequest, resourceResponse);
			else if (cmd.equals("deletePortfolio"))
				deletePortfolio(resourceRequest, resourceResponse);
			else if (cmd.equals("deletePublishment"))
				deletePublishment(resourceRequest, resourceResponse);
			else if (cmd.equals("deleteGlobalPublishment"))
				deleteGlobalPublishment(resourceRequest, resourceResponse);
			else if (cmd.equals("requestFeedbackFromUser"))
				requestFeedbackFromUser(resourceRequest, resourceResponse);
			else if (cmd.equals("deleteFeedbackRequest"))
				deleteFeedbackRequest(resourceRequest, resourceResponse);
		} catch (Exception e) {
			e.printStackTrace();
			throw new PortletException(e);
		}
	}

	/**
	 * Returns the user with names matching the search term.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortletException
	 * @throws SystemException
	 */
	private void findUsers(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException, SystemException {
		JSONArray usersJSONArray = JSONFactoryUtil.createJSONArray();
		String inputValue = ParamUtil.getString(resourceRequest, "inputValue");
		DynamicQuery userQuery = DynamicQueryFactoryUtil.forClass(User.class, PortalClassLoaderUtil.getClassLoader());
		Criterion criterion = RestrictionsFactoryUtil.like("screenName", StringPool.PERCENT + inputValue
				+ StringPool.PERCENT);
		userQuery.add(criterion);
		JSONObject userJSON = null;
		List<User> userList = UserLocalServiceUtil.dynamicQuery(userQuery, -1, -1);
		for (User user : userList) {
			userJSON = JSONFactoryUtil.createJSONObject();
			userJSON.put("userId", user.getUserId());
			userJSON.put("email", user.getEmailAddress());
			userJSON.put("screenName", user.getScreenName());
			userJSON.put("fullName", user.getFullName());
			usersJSONArray.put(userJSON);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(usersJSONArray.toString());
	}

	/**
	 * Checks whether a user with the given name exists
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws SystemException
	 */
	private void userExists(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String userName = ParamUtil.getString(resourceRequest, "userName");
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);
		PrintWriter out = resourceResponse.getWriter();
		out.print(user != null && themeDisplay.getUserId() != user.getUserId());
	}

	/**
	 * Checks whether a user has the permission to view a portfolio
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void userHasViewPermission(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String name = ParamUtil.getString(resourceRequest, "name");
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		if (user != null)
			out.print(portfolio.userHasViewPermission(user.getUserId()));
		else
			out.print(false);
	}

	/**
	 * Checks whether feedback is already requested by a user
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void feedbackRequested(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String name = ParamUtil.getString(resourceRequest, "name");
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		if (user != null)
			out.print(portfolio.feedbackRequested(user.getUserId()));
		else
			out.print(false);
	}

	/**
	 * Returns the portfolios of the current user
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void getUserPortfolios(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByLayoutUserId(themeDisplay.getUserId());
		JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
		for (Portfolio portfolio : portfolios) {
			JspHelper.addToPortfolioJSONArray(portfolioJSONArray, portfolio, themeDisplay);
		}
		PrintWriter out = resourceResponse.getWriter();
		out.println(portfolioJSONArray.toString());
	}

	/**
	 * Deletes the portfolio.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deletePortfolio(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.DELETE)
				&& !portfolio.feedbackRequested())
			PortfolioLocalServiceUtil.deletePortfolio(plid);
	}

	/**
	 * Deletes the publishment of a portfolio.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deletePublishment(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)
				&& !portfolio.feedbackRequested(userId))
			PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(plid, userId);
	}

	/**
	 * Deletes the global publishment of the portfolio.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deleteGlobalPublishment(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE))
			portfolio.setPrivate();
	}

	/**
	 * Requests feedback from a single user
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void requestFeedbackFromUser(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (themeDisplay.getUserId() == portfolio.getLayout().getUserId()) {
			portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_REQUESTED);
			sendFeedbackRequestedNotification(resourceRequest, userId, portfolio);
		}
	}

	/**
	 * Removes the feedback entry for a portfolio.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws SystemException
	 * @throws PortalException
	 */
	private void deleteFeedbackRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (themeDisplay.getUserId() == portfolio.getLayout().getUserId()) {
			portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_UNREQUESTED);
		}
	}

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(actionRequest, ActionRequest.ACTION_NAME);

			if (actionName.equals("createPortfolio")) {
				createPortfolio(actionRequest, actionResponse);
			} else if (actionName.equals("publishPortfolioGlobal")) {
				publishPortfolioGlobal(actionRequest, actionResponse);
			} else if (actionName.equals("publishPortfolioToUsers")) {
				publishPortfolioToUsers(actionRequest, actionResponse);
			} else if (actionName.equals("requestFeedbackFromUsers")) {
				requestFeedbackFromUsers(actionRequest, actionResponse);
			} else {
				super.processAction(actionRequest, actionResponse);
			}
		} catch (Exception e) {
			throw new PortletException(e);
		}
	}

	/**
	 * Creates a portfolio
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void createPortfolio(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String portfolioName = ParamUtil.getString(uploadRequest, "portfolioName");
		String template = ParamUtil.getString(uploadRequest, "template");

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		// Get portfolio parent page
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getUser().getGroupId(), false);
		Layout portfolioParentPage = null;
		for (Layout layout : layouts) {
			if (layout.getName(themeDisplay.getLocale()).equals("Portfolio"))
				portfolioParentPage = layout;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), actionRequest);

		// create portfolio parent page if none exists
		if (portfolioParentPage == null) {
			portfolioParentPage = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), themeDisplay.getUser()
					.getGroupId(), false, 0, "Portfolio", "Portfolio", "", LayoutConstants.TYPE_PORTLET, false,
					"/Portfolio", serviceContext);
		}

		Layout newPortfolio = null;
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (!template.equals(PortfolioStatics.EMPTY_LAYOUT_PROTOTYPE)) {
			// find template and set it as parent template
			LayoutPrototype lp = getLayoutPrototype(template);
			if (lp != null) {
				serviceContext.setAttribute("layoutPrototypeLinkEnabled", false);

				serviceContext.setAttribute("layoutPrototypeUuid", lp.getUuid());

				newPortfolio = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(),
						portfolioParentPage.getGroupId(), false, portfolioParentPage.getLayoutId(), portfolioName,
						portfolioName, "", LayoutConstants.TYPE_PORTLET, false, null, serviceContext);

				SitesUtil.mergeLayoutPrototypeLayout(newPortfolio.getGroup(), newPortfolio);
			} else {
				jsonObject.put("success", Boolean.FALSE);
				PortletConfig portletConfig = (PortletConfig) actionRequest
						.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
				jsonObject.put("message", LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
						"portfolio-portfolio-could-not-be-created"));
				writeJSON(actionRequest, actionResponse, jsonObject);
				throw new SystemException("Could not find layout prototype with the name " + template);
			}
		} else {
			// create empty page
			newPortfolio = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), portfolioParentPage.getGroupId(),
					false, portfolioParentPage.getLayoutId(), portfolioName, portfolioName, "",
					LayoutConstants.TYPE_PORTLET, false, null, serviceContext);
		}
		PortfolioLocalServiceUtil.addPortfolio(newPortfolio.getPlid());

		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	/**
	 * Returns the layout prototype with the given name
	 * 
	 * @param name
	 * @return layout prototype
	 * @throws SystemException
	 */
	private LayoutPrototype getLayoutPrototype(String name) throws SystemException {
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());

		for (LayoutPrototype lp : layoutPrototypes) {
			if (lp.getName(Locale.GERMAN).equals(name)) {
				return lp;
			}
		}
		return null;
	}

	/**
	 * Publishes the portfolio globally
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void publishPortfolioGlobal(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long portfolioPlid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(portfolioPlid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE))
			portfolio.setGlobal();
	}

	/**
	 * Publishes the portfolio to the user
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void publishPortfolioToUsers(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String userNames = ParamUtil.getString(actionRequest, "userNames");
		String portfolioPlid = ParamUtil.getString(actionRequest, "portfolioPlid");
		String[] userNameList = userNames.split(";");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(Long.parseLong(portfolioPlid));
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
			for (String userName : userNameList) {
				User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);
				if (user != null) {
					portfolio.publishToUser(user.getUserId(),
							ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));
					String message = LanguageUtil.format(
							portletConfig,
							themeDisplay.getLocale(),
							"portfolio-portfolio-published-message",
							new Object[] { themeDisplay.getUser().getFullName(),
									portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
					String portfolioLink = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(),
							themeDisplay.getUser());
					JspHelper.sendPortfolioNotification(user, themeDisplay.getUser(), message, portfolioLink,
							ServiceContextFactory.getInstance(actionRequest));
				}
			}
		}
	}

	/**
	 * Requests feedback from the given user
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception 
	 */
	private void requestFeedbackFromUsers(ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		String userNames = ParamUtil.getString(actionRequest, "userNames");
		String portfolioPlid = ParamUtil.getString(actionRequest, "portfolioPlid");
		String[] userNameList = userNames.split(";");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(Long.parseLong(portfolioPlid));
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
			for (String userName : userNameList) {
				User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);
				if (user != null) {
					portfolio.publishToUserAndRequestFeedback(user.getUserId(),
							ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));
					sendFeedbackRequestedNotification(actionRequest, user.getUserId(), portfolio);
				}
			}
		}
	}

	@Override
	protected void writeJSON(PortletRequest portletRequest, ActionResponse actionResponse, Object json)
			throws IOException {
		HttpServletResponse response = PortalUtil.getHttpServletResponse(actionResponse);
		response.setContentType(ContentTypes.TEXT_HTML);
		ServletResponseUtil.write(response, json.toString());
		response.flushBuffer();
	}

	@Override
	protected void writeJSON(PortletRequest portletRequest, MimeResponse mimeResponse, Object json) throws IOException {
		mimeResponse.setContentType(ContentTypes.TEXT_HTML);
		PortletResponseUtil.write(mimeResponse, json.toString());
		mimeResponse.flushBuffer();
	}

	/**
	 * Sends notification to a user
	 * 
	 * @param actionRequest
	 * @param userId
	 * @param portfolio
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void sendFeedbackRequestedNotification(PortletRequest actionRequest, long userId, Portfolio portfolio)
			throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		String message = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
				"portfolio-portfolio-feedback-requested-message", new Object[] { themeDisplay.getUser().getFullName(),
						portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
		String portfolioLink = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), themeDisplay.getUser());
		JspHelper.sendPortfolioNotification(UserLocalServiceUtil.getUser(userId), themeDisplay.getUser(), message,
				portfolioLink, ServiceContextFactory.getInstance(actionRequest));
	}

	public void filterPortfolios(ActionRequest actionRequest, ActionResponse actionResponse) {
		String filterValue = ParamUtil.getString(actionRequest, "filterValue");
		actionResponse.setRenderParameter("filterValue", filterValue);
	}

}
