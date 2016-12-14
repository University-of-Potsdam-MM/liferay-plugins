package de.unipotsdam.elis.custompages.mycustompages;

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
import javax.portlet.ReadOnlyException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;
import javax.servlet.http.HttpServletResponse;

import com.liferay.compat.portal.kernel.util.ListUtil;
import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.comparator.UserFirstNameComparator;

import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;
import de.unipotsdam.elis.custompages.util.CustomPageUtil;
import de.unipotsdam.elis.custompages.util.FriendlyURLValidator;
import de.unipotsdam.elis.custompages.util.jsp.JspHelper;

public class MyCustomPagesPortlet extends MVCPortlet {

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
			else if (cmd.equals("getUserCustomPages"))
				getUserCustomPages(resourceRequest, resourceResponse);
			else if (cmd.equals("deleteCustomPage"))
				deleteCustomPage(resourceRequest, resourceResponse);
			else if (cmd.equals("deletePublishment"))
				deletePublishment(resourceRequest, resourceResponse);
			else if (cmd.equals("deleteGlobalPublishment"))
				deleteGlobalPublishment(resourceRequest, resourceResponse);
			else if (cmd.equals("requestFeedbackFromUser"))
				requestFeedbackFromUser(resourceRequest, resourceResponse);
			else if (cmd.equals("deleteFeedbackRequest"))
				deleteFeedbackRequest(resourceRequest, resourceResponse);
			else if (cmd.equals("renameCustomPage"))
				renameCustomPage(resourceRequest, resourceResponse);
			else if (cmd.equals("getAvailableUsers"))
				getAvailableUsers(resourceRequest, resourceResponse);
			else if (cmd.equals("getUsersCustomPagePublishedTo"))
				getUsersCustomPagePublishedTo(resourceRequest, resourceResponse);
			else if (cmd.equals("changeCustomPageType"))
				changeCustomPageType(resourceRequest, resourceResponse);
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
		List<User> userList = UserLocalServiceUtil.dynamicQuery(userQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
	 * Checks whether a user has the permission to view a custom page
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
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		if (user != null)
			out.print(CustomPageUtil.userHasViewPermission(plid, user.getUserId()));
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
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		User user = UserLocalServiceUtil.fetchUserByScreenName(themeDisplay.getCompanyId(), name);
		PrintWriter out = resourceResponse.getWriter();
		if (user != null)
			out.print(CustomPageUtil.feedbackRequested(plid, user.getUserId()));
		else
			out.print(false);
	}

	/**
	 * Returns the custom pages of the current user
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws IOException
	 * @throws PortalException
	 * @throws SystemException
	 */
	private void getUserCustomPages(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws IOException, PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		boolean privatePersonalPage = ParamUtil.getBoolean(resourceRequest, "privatePersonalPage");
		if (themeDisplay.getSiteGroup().isUser()) {
			JSONArray customPageJSONArray = JSONFactoryUtil.createJSONArray();
			if (privatePersonalPage) {
				List<Layout> customPages = CustomPageFeedbackLocalServiceUtil.getCustomPagesByLayoutUserId(themeDisplay
						.getSiteGroup().getClassPK());
				for (Layout customPage : customPages) {
					/* Changed for #599
					if (((Integer) customPage.getExpandoBridge().getAttribute(
							CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME)).intValue() != CustomPageStatics.CUSTOM_PAGE_TYPE_NONE
							&& ((Integer) customPage.getExpandoBridge().getAttribute(
									CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME)).intValue() != 0)*/
					if (((Integer) customPage.getExpandoBridge().getAttribute(
							CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME)).intValue() != CustomPageStatics.CUSTOM_PAGE_TYPE_NONE
							&& ((Integer) customPage.getExpandoBridge().getAttribute(
									CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME)).intValue() == 2)
						JspHelper.addToCustomPageJSONArray(customPageJSONArray, customPage, themeDisplay);
				}
			} else {
				List<Layout> customPages = CustomPageFeedbackLocalServiceUtil
						.getCustomPagesByLayoutUserIdAndCustomPageFeedback(themeDisplay.getSiteGroup().getClassPK());
				String layoutName = themeDisplay.getLayout().getName(themeDisplay.getLocale());
				PortletConfig portletConfig = (PortletConfig) resourceRequest
						.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
				int customPageType = -1;
				if (layoutName.equals(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
						"custompages-custom-pages")))
					customPageType = CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE;
				else if (layoutName.equals(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
						"custompages-portfolio-pages")))
					customPageType = CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE;
				for (Layout customPage : customPages) {
					if (CustomPageUtil.userHasViewPermission(customPage.getPlid(), themeDisplay.getUserId())
							&& (customPageType == -1 || ((Integer) customPage.getExpandoBridge().getAttribute(
									CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME)).intValue() == customPageType)
							&& ((Integer) customPage.getExpandoBridge().getAttribute(
									CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME)).intValue() != 0)
						JspHelper.publicAddToCustomPageJSONArray(customPageJSONArray, customPage, themeDisplay);
				}

			}
			PrintWriter out = resourceResponse.getWriter();
			out.println(customPageJSONArray.toString());
		}
	}

	/**
	 * Deletes the custom page.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deleteCustomPage(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "customPagePlid"));
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.DELETE) && !CustomPageUtil.feedbackRequested(plid))
			CustomPageUtil.removeCustomPage(plid);
	}

	/**
	 * Deletes the publishment of a custom page.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deletePublishment(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "customPagePlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);

		boolean movedToPrivateArea = false;
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.CUSTOMIZE) && !CustomPageUtil.feedbackRequested(customPage.getPlid(), userId)) {
			CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(customPage.getPlid(), userId);
			movedToPrivateArea = movePageToPrivateAreaIfNecessary(resourceRequest, customPage, themeDisplay.getUserId());
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("movedToPrivateArea", movedToPrivateArea);
		PrintWriter out = resourceResponse.getWriter();
		out.println(jsonObject.toString());
	}

	/**
	 * Deletes the global publishment of the custom page.
	 * 
	 * @param resourceRequest
	 * @param resourceResponse
	 * @throws Exception
	 */
	private void deleteGlobalPublishment(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.valueOf(ParamUtil.getString(resourceRequest, "customPagePlid"));
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.CUSTOMIZE))
			CustomPageUtil.deleteCustomPageGlobalPublishment(plid);
		boolean movedToPrivateArea = movePageToPrivateAreaIfNecessary(resourceRequest, customPage,
				themeDisplay.getUserId());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("movedToPrivateArea", movedToPrivateArea);
		PrintWriter out = resourceResponse.getWriter();
		out.println(jsonObject.toString());
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
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.CUSTOMIZE)) {
			User user = UserLocalServiceUtil.fetchUserById(userId);
			if (user != null) {
				CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedbackStatus(plid, userId,
						CustomPageStatics.FEEDBACK_REQUESTED);
				JspHelper.handleSocialActivities(customPage, resourceRequest, user,
						CustomPageStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED);
			}
		}
	}

	/**
	 * Removes the feedback entry for a custom page.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void deleteFeedbackRequest(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		long userId = Long.parseLong(ParamUtil.getString(resourceRequest, "userId"));
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.CUSTOMIZE)) {
			CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedbackStatus(plid, userId,
					CustomPageStatics.FEEDBACK_UNREQUESTED);
		}
	}

	private void changeCustomPageType(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = ParamUtil.getLong(resourceRequest, "customPagePlid");
		Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
		int customPageType = ParamUtil.getInteger(resourceRequest, "customPageType");
		CustomPageUtil.setCustomPagePageType(customPage, customPageType);
		if (customPage.isPublicLayout())
			movePageToParentPage(customPage, resourceRequest, customPageType, themeDisplay.getUser());
	}

	private void movePageToParentPage(Layout customPage, PortletRequest request, int customPageType, User user)
			throws PortalException, SystemException, ReadOnlyException, ValidatorException, IOException, Exception {
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(user), customPage, ActionKeys.CUSTOMIZE))
			if (customPageType == CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE
					|| customPageType == CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE) {
				Layout parentPage = null;
				if (customPageType == CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE)
					parentPage = JspHelper.getLayoutByNameOrCreate(request, "custompages-custom-pages", false, false,
							true);
				else if (customPageType == CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE) {
					parentPage = JspHelper.getLayoutByNameOrCreate(request, "custompages-portfolio-pages", false,
							false, true);
				}

				if (customPage.isPrivateLayout()) {
					customPage.setPrivateLayout(false);

					customPage.setHidden(true);

					for (LayoutFriendlyURL layoutFriendlyURL : LayoutFriendlyURLLocalServiceUtil
							.getLayoutFriendlyURLs(customPage.getPlid())) {
						layoutFriendlyURL.setPrivateLayout(false);
						LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURL(layoutFriendlyURL);
					}

				}

				customPage.setParentLayoutId(parentPage.getLayoutId());

				LayoutLocalServiceUtil.updateLayout(customPage);
			}
	}

	private void renameCustomPage(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long plid = Long.parseLong(ParamUtil.getString(resourceRequest, "customPagePlid"));
		String newTitle = ParamUtil.getString(resourceRequest, "newTitle");
		if (newTitle.trim().length() != 0) {
			Layout customPage = LayoutLocalServiceUtil.getLayout(plid);
			if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
					ActionKeys.CUSTOMIZE)) {
				changeLayoutFriendlyURLs(customPage, newTitle);
				customPage.setTitle(newTitle);
				customPage.setName(newTitle);
				changeLayoutFriendlyURLs(customPage, newTitle);
				LayoutLocalServiceUtil.updateLayout(customPage);
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

			if (actionName.equals("createCustomPage")) {
				createCustomPage(actionRequest, actionResponse);
			} else {
				super.processAction(actionRequest, actionResponse);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new PortletException(e);
		}
	}

	/**
	 * Creates a custom page
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	private void createCustomPage(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String customPageName = ParamUtil.getString(uploadRequest, "customPageName");
		String template = ParamUtil.getString(uploadRequest, "template");
		int customPageType = ParamUtil.getInteger(uploadRequest, "customPageType");

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) actionRequest.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), actionRequest);

		// Get custom page parent page
		Layout customPagesParentPage = null;
		customPagesParentPage = JspHelper.getLayoutByNameOrCreate(actionRequest, "custompages-page-management", false,
				true, false);

		Layout newCustomPage = null;
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		// find template and set it as parent template
		LayoutPrototype lp = getLayoutPrototype(template);
		if (lp != null) {
			serviceContext.setAttribute("layoutPrototypeLinkEnabled", false);

			serviceContext.setAttribute("layoutPrototypeUuid", lp.getUuid());

			newCustomPage = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(),
					customPagesParentPage.getGroupId(), true, customPagesParentPage.getLayoutId(), customPageName,
					customPageName, "", LayoutConstants.TYPE_PORTLET, true, null, serviceContext);

			SitesUtil.mergeLayoutPrototypeLayout(newCustomPage.getGroup(), newCustomPage);
		} else {
			jsonObject.put("success", Boolean.FALSE);
			jsonObject.put("message", LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-could-not-be-created"));
			writeJSON(actionRequest, actionResponse, jsonObject);
			throw new SystemException("Could not find layout prototype with the name " + template);
		}
		CustomPageUtil.setCustomPagePageType(newCustomPage, customPageType);

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
			if (lp.getName(Locale.GERMANY).equals(name)) {
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
		long customPagePlid = ParamUtil.getLong(actionRequest, "customPagePlid");
		Layout customPage = LayoutLocalServiceUtil.getLayout(customPagePlid);
		if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
				ActionKeys.CUSTOMIZE)) {
			List<Long> newUserIds = ListUtil.toList(getLongArray(actionRequest, "receiverUserIds"));
			List<CustomPageFeedback> publishments = CustomPageFeedbackLocalServiceUtil
					.getCustomPageFeedbackByPlid(customPagePlid);
			List<Long> oldUserIds = new ArrayList<Long>();
			for (CustomPageFeedback publishment : publishments)
				oldUserIds.add(publishment.getUserId());

			for (Long userId : oldUserIds) {
				if (!newUserIds.contains(userId))
					CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(customPage.getPlid(), userId);
			}

			for (Long userId : newUserIds) {
				if (!oldUserIds.contains(userId)) {
					User user = UserLocalServiceUtil.fetchUser(userId);
					if (user != null) {
						CustomPageUtil.publishCustomPageToUserAndRequestFeedback(customPagePlid, userId);
						JspHelper.handleSocialActivities(customPage, actionRequest, user,
								CustomPageStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED);
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

	public void filtercustomPages(ActionRequest actionRequest, ActionResponse actionResponse) {
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

	private void getUsersCustomPagePublishedTo(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {

		long customPagePlid = ParamUtil.getLong(resourceRequest, "customPagePlid");

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("isGlobal", CustomPageUtil.isPublishedGlobal(customPagePlid));

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (CustomPageFeedback customPageFeedback : CustomPageFeedbackLocalServiceUtil
				.getCustomPageFeedbackByPlid(customPagePlid)) {
			if (UserLocalServiceUtil.fetchUser(customPageFeedback.getUserId()) != null) {
				JSONObject userJSONObject = JSONFactoryUtil.createJSONObject();
				userJSONObject.put("userEmailAddress", customPageFeedback.getUser().getEmailAddress());
				userJSONObject.put("userFullName", customPageFeedback.getUser().getFullName());
				userJSONObject.put("userId", customPageFeedback.getUser().getUserId());
				userJSONObject.put("feedbackRequested",
						customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_REQUESTED);

				jsonArray.put(userJSONObject);
			}
		}

		jsonObject.put("users", jsonArray);

		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}

	/**
	 * Publishes the custom page to the user or publishes it globally.
	 * 
	 * @param actionRequest
	 * @param actionResponse
	 * @throws Exception
	 */
	public void publishCustomPage(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
			long customPagePlid = ParamUtil.getLong(actionRequest, "customPagePlid");
			Layout customPage = LayoutLocalServiceUtil.getLayout(customPagePlid);
			if (LayoutPermissionUtil.contains(PermissionCheckerFactoryUtil.create(themeDisplay.getUser()), customPage,
					ActionKeys.CUSTOMIZE)) {
				if (ParamUtil.getBoolean(actionRequest, "globalPublishment")) {
					if (!CustomPageUtil.isPublishedGlobal(customPagePlid))
						CustomPageUtil.publishCustomPageGlobal(customPagePlid);
					if (customPage.isHidden())
						customPage.getExpandoBridge().setAttribute(
								CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME, "20");
				} else {
					if (CustomPageUtil.isPublishedGlobal(customPagePlid))
						CustomPageUtil.deleteCustomPageGlobalPublishment(customPagePlid);

					List<Long> newUserIds = ListUtil.toList(getLongArray(actionRequest, "receiverUserIds"));
					List<CustomPageFeedback> publishments = CustomPageFeedbackLocalServiceUtil
							.getCustomPageFeedbackByPlid(customPagePlid);
					List<Long> oldUserIds = new ArrayList<Long>();
					for (CustomPageFeedback publishment : publishments) {
						oldUserIds.add(publishment.getUserId());
						if (!newUserIds.contains(publishment.getUserId())
								&& publishment.getFeedbackStatus() != CustomPageStatics.FEEDBACK_REQUESTED)
							CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(publishment);
					}

					for (Long userId : newUserIds) {
						if (!oldUserIds.contains(userId) && userId != themeDisplay.getUserId()) {
							User user = UserLocalServiceUtil.fetchUser(userId);
							if (user != null) {
								CustomPageUtil.publishCustomPageToUser(customPagePlid, user.getUserId());
								if (customPage.isHidden())
									customPage.getExpandoBridge().setAttribute(
											CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME, "20");
								JspHelper.handleSocialActivities(customPage, actionRequest, user,
										CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_PUBLISHED);
							}
						}
					}
				}

			}

			movePageToPrivateAreaIfNecessary(actionRequest, customPage, themeDisplay.getUserId());
			if ((CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(customPage.getPlid()).size() != 0 || CustomPageUtil
					.isPublishedGlobal(customPagePlid)) && customPage.isPrivateLayout())
				movePageToParentPage(
						customPage,
						actionRequest,
						(Integer) customPage.getExpandoBridge().getAttribute(
								CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME), themeDisplay.getUser());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private boolean movePageToPrivateAreaIfNecessary(PortletRequest request, Layout layout, long userId)
			throws PortalException, SystemException, ReadOnlyException, ValidatorException, IOException {
		boolean createdInPrivateArea = false;
		if (layout.getExpandoBridge().hasAttribute(CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME))
			createdInPrivateArea = ((Integer) layout.getExpandoBridge().getAttribute(
					CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME)).intValue() != 0;
		if (CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(layout.getPlid()).size() == 0
				&& !CustomPageUtil.isPublishedGlobal(layout.getPlid()) && createdInPrivateArea) {

			for (LayoutFriendlyURL layoutFriendlyURL : LayoutFriendlyURLLocalServiceUtil.getLayoutFriendlyURLs(layout
					.getPlid())) {
				layoutFriendlyURL.setPrivateLayout(true);
				LayoutFriendlyURLLocalServiceUtil.updateLayoutFriendlyURL(layoutFriendlyURL);
			}

			int personalAreaSection = ((Integer) layout.getExpandoBridge().getAttribute(
					CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME)).intValue();
			if (personalAreaSection == 20) {
				Layout parentLayout = JspHelper.getLayoutByNameOrCreate(request, "custompages-page-management", false,
						true, true);
				layout.setParentLayoutId(parentLayout.getLayoutId());
				LayoutLocalServiceUtil.updateLayout(layout);
				layout.getExpandoBridge().setAttribute(CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME, "2");
			} else {
				layout = LayoutLocalServiceUtil.updateParentLayoutId(layout.getPlid(), 0);
			}

			if (personalAreaSection == 1 || personalAreaSection == 2 || personalAreaSection == 3)
				layout.setHidden(false);
			layout.setPrivateLayout(true);
			LayoutLocalServiceUtil.updateLayout(layout);
			return true;
		}
		return false;
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
