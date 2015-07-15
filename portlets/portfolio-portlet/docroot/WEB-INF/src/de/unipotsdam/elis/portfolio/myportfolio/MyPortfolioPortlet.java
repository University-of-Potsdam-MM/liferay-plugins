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
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
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

		user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);

		// TODO: fehler wenn nutzer unbekannt
		if (user == null) {
			throw new SystemException("User unknown");
		} else {
			long plid = ParamUtil.getLong(actionRequest, "portfolioPlid");
			Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
			portfolio.removeUser(user.getUserId());
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
		long plid = Long.valueOf(ParamUtil.getString(actionRequest, "portfolioPlid"));
		PortfolioLocalServiceUtil.deletePortfolio(plid);
	}

	public static void deletePublishment(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {
		// TODO: Sollte nicht löschbar sein, wenn es sich im Feedbackprozess
		// befindet.
		// TODO: was ist wenn nutzer mitlerweile gel�scht?
		long plid = Long.valueOf(ParamUtil.getString(actionRequest, "portfolioPlid"));
		String userName = ParamUtil.getString(actionRequest, "userName");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long userId = UserLocalServiceUtil.getUserIdByScreenName(themeDisplay.getCompanyId(), userName);
		PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(plid, userId);
	}

	public static void deleteGlobalPublishment(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException, SystemException {
		// TODO: Sollte nicht löschbar sein, wenn es sich im Feedbackprozess
		// befindet.
		long plid = Long.valueOf(ParamUtil.getString(actionRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.setPrivate();
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		if (cmd.equals("getUsers")) {
			getUsers(resourceRequest, resourceResponse);
		} else if (cmd.equals("userExists")) {
			try {
				userExists(resourceRequest, resourceResponse);
			} catch (SystemException e) {
				e.printStackTrace();
			}
		} else if (cmd.equals("userHasPermission")) {
			try {
				userHasPermission(resourceRequest, resourceResponse);
			} catch (SystemException e) {
				e.printStackTrace();
			} catch (PortalException e) {
				e.printStackTrace();
			}
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

	public void createPortfolio(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// TODO: eventuell Fehlermeldungen
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String portfolioName = ParamUtil.getString(uploadRequest, "portfolioName");
		String template = ParamUtil.getString(uploadRequest, "template");

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		// Get Portfolio page
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getUser().getGroupId(), false);
		Layout portfolioParentPage = null;
		for (Layout layout : layouts) {
			if (layout.getName(themeDisplay.getLocale()).equals("Portfolio"))
				portfolioParentPage = layout;
		}

		// preparations
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), actionRequest);

		if (portfolioParentPage == null) {
			portfolioParentPage = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), themeDisplay.getUser()
					.getGroupId(), false, 0, "Portfolio", "Portfolio", "", LayoutConstants.TYPE_PORTLET, false,
					"/Portfolio", serviceContext);
		}

		Layout newPortfolio = null;

		if (!template.equals(PortfolioStatics.EMPTY_LAYOUT_PROTOTYPE)) {
			LayoutPrototype lp = getLayoutPrototype(template);
			if (lp != null) {
				serviceContext.setAttribute("layoutPrototypeLinkEnabled", false);

				serviceContext.setAttribute("layoutPrototypeUuid", lp.getUuid());

				newPortfolio = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(),
						portfolioParentPage.getGroupId(), false, portfolioParentPage.getLayoutId(), portfolioName,
						portfolioName, "", LayoutConstants.TYPE_PORTLET, false, null, serviceContext);

				SitesUtil.mergeLayoutPrototypeLayout(newPortfolio.getGroup(), newPortfolio);
			} else {
				System.err.println("Could not find layout prototype with the name " + template);
			}
		} else {
			newPortfolio = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), portfolioParentPage.getGroupId(),
					false, portfolioParentPage.getLayoutId(), portfolioName, portfolioName, "",
					LayoutConstants.TYPE_PORTLET, false, null, serviceContext);
		}
		PortfolioLocalServiceUtil.addPortfolio(newPortfolio.getPlid());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
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

	public void userExists(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String name = ParamUtil.getString(resourceRequest, "name");
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		out.print(user != null && themeDisplay.getUserId() != user.getUserId());
	}

	public void userHasPermission(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, SystemException, PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String name = ParamUtil.getString(resourceRequest, "name");
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		if (user != null)
			out.print(portfolio.userHasPermission(user.getUserId()));
		else
			out.print(false);
	}

	public void publishPortfolioToUsers(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortletException, SystemException, NumberFormatException, PortalException, IOException {
		// TODO: eventuell Fehlermeldungen
		String userNames = ParamUtil.getString(actionRequest, "userNames");
		String portfolioPlid = ParamUtil.getString(actionRequest, "portfolioPlid");
		String[] userNameList = userNames.split(";");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		for (String userName : userNameList) {
			User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);
			if (user != null) {
				Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(Long.parseLong(portfolioPlid));
				portfolio.publishToUser(user.getUserId(),
						ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));
				String message = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
						"portfolio-portfolio-published-message", new Object[] { themeDisplay.getUser().getFullName(),
								portfolio.getLayout().getTitle(themeDisplay.getLocale()) });
				String portfolioLink = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(),
						themeDisplay.getUser());
				JspHelper.sendPortfolioNotification(user, themeDisplay.getUser(), message, portfolioLink,
						ServiceContextFactory.getInstance(actionRequest));
			}
		}
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void publishPortfolioGlobal(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortletException, SystemException, PortalException, IOException {
		long portfolioPlid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(portfolioPlid);
		portfolio.setGlobal();
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void requestFeedbackFromUser(ActionRequest actionRequest, ActionResponse actionResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(actionRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_REQUESTED);
		sendFeedbackRequestedNotification(actionRequest, userId, portfolio);
	}

	private void sendFeedbackRequestedNotification(ActionRequest actionRequest, long userId, Portfolio portfolio)
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

	public void removeFeedbackForPortfolio(ActionRequest actionRequest, ActionResponse actionResponse)
			throws SystemException, PortalException {
		long plid = Long.parseLong(ParamUtil.getString(actionRequest, "portfolioPlid"));
		long userId = Long.parseLong(ParamUtil.getString(actionRequest, "userId"));
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(plid);
		portfolio.updateFeedbackStatus(userId, PortfolioStatics.FEEDBACK_UNREQUESTED);
	}

	public void requestFeedbackFromUsers(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortletException, SystemException, NumberFormatException, PortalException, IOException {
		String userNames = ParamUtil.getString(actionRequest, "userNames");
		String portfolioPlid = ParamUtil.getString(actionRequest, "portfolioPlid");
		String[] userNameList = userNames.split(";");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		for (String userName : userNameList) {
			User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), userName);
			if (user != null) {
				Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(Long.parseLong(portfolioPlid));
				portfolio.publishToUserAndRequestFeedback(user.getUserId(),
						ServiceContextFactory.getInstance(PortfolioFeedback.class.getName(), actionRequest));

				sendFeedbackRequestedNotification(actionRequest, user.getUserId(), portfolio);
			}
		}
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void filterPortfolios(ActionRequest actionRequest, ActionResponse actionResponse) {
		String filterValue = ParamUtil.getString(actionRequest, "filterValue");
		actionResponse.setRenderParameter("filterValue", filterValue);
	}

}
