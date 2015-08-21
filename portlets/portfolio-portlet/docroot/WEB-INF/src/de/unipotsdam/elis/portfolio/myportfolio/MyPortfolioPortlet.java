package de.unipotsdam.elis.portfolio.myportfolio;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
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

import com.liferay.compat.portal.kernel.util.ListUtil;
import com.liferay.compat.portal.kernel.util.StringUtil;
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
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.sites.util.SitesUtil;
import com.liferay.portal.kernel.dao.orm.CustomSQLParam;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.liferay.portal.util.comparator.UserFirstNameComparator;

import de.unipotsdam.elis.portfolio.NoSuchFeedbackException;
import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;
import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;
import de.unipotsdam.elis.portfolio.util.FriendlyURLValidator;
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
			else if (cmd.equals("renamePortfolio"))
				renamePortfolio(resourceRequest, resourceResponse);
			else if (cmd.equals("getAvailableUsers"))
				getAvailableUsers(resourceRequest, resourceResponse);
			else if (cmd.equals("getUsersPortfolioPublishedTo"))
				getUsersPortfolioPublishedTo(resourceRequest, resourceResponse);
			else
				super.serveResource(resourceRequest, resourceResponse);

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
		if (themeDisplay.getSiteGroup().isUser()) {
			List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByLayoutUserId(themeDisplay
					.getSiteGroup().getClassPK());
			JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
			for (Portfolio portfolio : portfolios) {
				if (portfolio.userHasViewPermission(themeDisplay.getUserId()))
					JspHelper.addToPortfolioJSONArray(portfolioJSONArray, portfolio, themeDisplay);
			}
			PrintWriter out = resourceResponse.getWriter();
			out.println(portfolioJSONArray.toString());
		}
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
		deletePortfolioFeedback(portfolio, themeDisplay, userId);
	}

	private void deletePortfolioFeedback(Portfolio portfolio, ThemeDisplay themeDisplay, long userId)
			throws NoSuchFeedbackException, PortalException, SystemException, Exception {

		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)
				&& !portfolio.feedbackRequested(userId))
			PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(portfolio.getPlid(), userId);
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
	 * @throws Exception
	 */
	private void requestFeedbackFromUser(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
			User user = UserLocalServiceUtil.fetchUserById(userId);
			if (user != null) {
				portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_REQUESTED);
				JspHelper.handleSocialActivities(portfolio, resourceRequest, user,
						PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED);
			}
		}
	}

	/**
	 * Removes the feedback entry for a portfolio.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void deleteFeedbackRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
			portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_UNREQUESTED);
		}
	}

	private void renamePortfolio(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		String newTitle = ParamUtil.getString(resourceRequest, "newTitle");
		if (newTitle.trim().length() != 0) {
			Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
			if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
					portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
				changeLayoutFriendlyURLs(portfolio.getLayout(), newTitle);
				Layout layout = portfolio.getLayout();
				layout.setTitle(newTitle);
				layout.setName(newTitle);
				changeLayoutFriendlyURLs(layout, newTitle);
				LayoutLocalServiceUtil.updateLayout(layout);
			}
		}
	}

	private void changeLayoutFriendlyURLs(Layout layout, String newTitle) throws PortalException, SystemException {
		String friendlyURL = StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(newTitle);
		String newFriendlyURL = friendlyURL;
		if (!FriendlyURLValidator.validateFriendlyURL(layout.getGroupId(), layout.getPrivateLayout(),
				layout.getLayoutId(), newFriendlyURL)) {
			newFriendlyURL = StringPool.SLASH + layout.getLayoutId();
		} else {
			for (int i = 1;; i++) {
				if (!FriendlyURLValidator.isDuplicate(layout.getGroupId(), layout.getPrivateLayout(),
						layout.getLayoutId(), newFriendlyURL))
					break;
				newFriendlyURL = friendlyURL + i;
			}
		}
		Locale[] locales = LanguageUtil.getAvailableLocales(layout.getGroupId());

		for (Locale locale : locales) {
			LayoutFriendlyURL layoutFriendlyURL = LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURL(
					layout.getPlid(), locale.toLanguageTag());
			layoutFriendlyURL.setFriendlyURL(newFriendlyURL);
			LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURL(layoutFriendlyURL);
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
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);

		// Get portfolio parent page
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getUser().getGroupId(), false);
		Layout portfolioParentPage = null;
		String parentPageName = LanguageUtil.get(portletConfig, themeDisplay.getLocale(), "portfolio-portfolio-pages");
		for (Layout layout : layouts) {
			if (layout.getName(themeDisplay.getLocale()).equals(parentPageName))
				portfolioParentPage = layout;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), actionRequest);

		// create portfolio parent page if none exists
		if (portfolioParentPage == null) {
			// Map<Locale,String> localeMap =
			// JspHelper.getLocaleMap("portfolio-portfolio-pages",
			// portletConfig);
			// portfolioParentPage =
			// LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(),
			// themeDisplay.getUser().getGroupId(), false,
			// 0,localeMap,localeMap,null,null,null,LayoutConstants.TYPE_PORTLET,false,parentPageName
			portfolioParentPage = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), themeDisplay.getUser()
					.getGroupId(), false, 0, parentPageName, parentPageName, "", LayoutConstants.TYPE_PORTLET, false,
					"/" + parentPageName, serviceContext);
		}

		Layout newPortfolio = null;
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		// find template and set it as parent template
		LayoutPrototype lp = getLayoutPrototype(template);
		if (lp != null) {
			serviceContext.setAttribute("layoutPrototypeLinkEnabled", false);

			serviceContext.setAttribute("layoutPrototypeUuid", lp.getUuid());

			newPortfolio = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), portfolioParentPage.getGroupId(),
					false, portfolioParentPage.getLayoutId(), portfolioName, portfolioName, "",
					LayoutConstants.TYPE_PORTLET, false, null, serviceContext);

			SitesUtil.mergeLayoutPrototypeLayout(newPortfolio.getGroup(), newPortfolio);
		} else {
			jsonObject.put("success", Boolean.FALSE);
			jsonObject.put("message", LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
					"portfolio-portfolio-could-not-be-created"));
			writeJSON(actionRequest, actionResponse, jsonObject);
			throw new SystemException("Could not find layout prototype with the name " + template);
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
	 * Requests feedback from the given user
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	public void requestFeedbackFromUsers(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long portfolioPlid = ParamUtil.getLong(actionRequest, "portfolioPlid");
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(portfolioPlid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {
			List<Long> newUserIds = ListUtil.toList(getLongArray(actionRequest, "receiverUserIds"));
			List<PortfolioFeedback> publishments = PortfolioFeedbackLocalServiceUtil
					.getPortfolioFeedbackByPlid(portfolioPlid);
			List<Long> oldUserIds = new ArrayList<Long>();
			for (PortfolioFeedback publishment : publishments)
				oldUserIds.add(publishment.getUserId());

			for (Long userId : oldUserIds) {
				if (!newUserIds.contains(userId))
					PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(portfolio.getPlid(), userId);
			}

			for (Long userId : newUserIds) {
				if (!oldUserIds.contains(userId)) {
					User user = UserLocalServiceUtil.fetchUser(userId);
					if (user != null) {
						portfolio.publishToUserAndRequestFeedback(user.getUserId(),
								ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));
						JspHelper.handleSocialActivities(portfolio, actionRequest, user,
								PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED);
					}
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

	public void filterPortfolios(ActionRequest actionRequest, ActionResponse actionResponse) {
		String filterValue = ParamUtil.getString(actionRequest, "filterValue");
		actionResponse.setRenderParameter("filterValue", filterValue);
	}

	private void getAvailableUsers(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		int end = ParamUtil.getInteger(resourceRequest, "end");
		String keywords = ParamUtil.getString(resourceRequest, "keywords");
		int start = ParamUtil.getInteger(resourceRequest, "start");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("count", UserLocalServiceUtil.searchCount(themeDisplay.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED, null));

		JSONObject optionsJSONObject = JSONFactoryUtil.createJSONObject();

		optionsJSONObject.put("end", end);
		optionsJSONObject.put("keywords", keywords);
		optionsJSONObject.put("start", start);

		jsonObject.put("options", optionsJSONObject);

		LinkedHashMap<String, Object> usersParams = new LinkedHashMap<String, Object>();
		usersParams.put("userId", new CustomSQLParam(CustomSQLUtil.get("filterByUserId"), themeDisplay.getUserId()));

		List<User> users = UserLocalServiceUtil.search(themeDisplay.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED, usersParams, start, end, new UserFirstNameComparator(true));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (User user : users) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();

			userJSONObject.put("userEmailAddress", user.getEmailAddress());
			userJSONObject.put("userFullName", user.getFullName());
			userJSONObject.put("userId", user.getUserId());

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	private void getUsersPortfolioPublishedTo(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {

		long portfolioPlid = ParamUtil.getLong(resourceRequest, "portfolioPlid");
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(portfolioPlid);

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("isGlobal", portfolio.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL);

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (PortfolioFeedback pf : PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlid(portfolioPlid)) {
			JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();
			userJSONObject.put("userEmailAddress", pf.getUser().getEmailAddress());
			userJSONObject.put("userFullName", pf.getUser().getFullName());
			userJSONObject.put("userId", pf.getUser().getUserId());
			userJSONObject.put("feedbackRequested", pf.getFeedbackStatus() == PortfolioStatics.FEEDBACK_REQUESTED);

			jsonArray.put(userJSONObject);
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	/**
	 * Publishes the portfolio to the user or publishes it globally.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	public void publishPortfolio(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long portfolioPlid = ParamUtil.getLong(actionRequest, "portfolioPlid");
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(portfolioPlid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()),
				portfolio.getLayout(), ActionKeys.CUSTOMIZE)) {

			if (!ParamUtil.getBoolean(actionRequest, "globalPublishment")) {
				if (portfolio.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL)
					portfolio.setPrivate();
			} else {
				if (portfolio.getPublishmentType() != PortfolioStatics.PUBLISHMENT_GLOBAL)
					portfolio.setGlobal();
				return;
			}

			List<Long> newUserIds = ListUtil.toList(getLongArray(actionRequest, "receiverUserIds"));
			List<PortfolioFeedback> publishments = PortfolioFeedbackLocalServiceUtil
					.getPortfolioFeedbackByPlid(portfolioPlid);
			List<Long> oldUserIds = new ArrayList<Long>();
			for (PortfolioFeedback publishment : publishments) {
				oldUserIds.add(publishment.getUserId());
				if (!newUserIds.contains(publishment.getUserId())
						&& publishment.getFeedbackStatus() != PortfolioStatics.FEEDBACK_REQUESTED)
					portfolio.removeUser(publishment.getUserId());
			}

			for (Long userId : newUserIds) {
				if (!oldUserIds.contains(userId) && userId != themeDisplay.getUserId()) {
					User user = UserLocalServiceUtil.fetchUser(userId);
					if (user != null) {
						portfolio.publishToUser(user.getUserId(),
								ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));
						JspHelper.handleSocialActivities(portfolio, actionRequest, user,
								PortfolioStatics.MESSAGE_TYPE_PORTFOLIO_PUBLISHED);
					}
				}
			}
		}
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	protected String[] getStringArray(PortletRequest portletRequest, String name) {

		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value));
	}

}
