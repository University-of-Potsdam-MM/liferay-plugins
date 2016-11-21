package de.unipotsdam.elis.custompages.util.jsp;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletConfig;
import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.compat.portal.kernel.util.HtmlUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.notifications.NotificationEvent;
import com.liferay.portal.kernel.notifications.NotificationEventFactoryUtil;
import com.liferay.portal.kernel.notifications.UserNotificationManagerUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserNotificationDeliveryConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.PortletURLFactoryUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.CustomUserLocalServiceWrapper;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.notifications.MyCustomPagesNotificationHandler;
import de.unipotsdam.elis.custompages.util.CustomPageUtil;

/**
 * Helper for the JSP pages.
 * 
 * @author Matthias
 *
 */
public class JspHelper {

	public static String getFeedbackStatusString(PortletConfig portletConfig, Locale locale, int feedbackStatus) {
		if (feedbackStatus == CustomPageStatics.FEEDBACK_REQUESTED)
			return LanguageUtil.get(portletConfig, locale, "custompages-feedback-requested");
		if (feedbackStatus == CustomPageStatics.FEEDBACK_DELIVERED)
			return LanguageUtil.get(portletConfig, locale, "custompages-feedback-delivered");
		else
			return LanguageUtil.get(portletConfig, locale, "custompages-no-feedback-requested");
	}

	public static void sendCustomPageNotification(User recipient, User sender, String message, String customPageLink,
			ServiceContext serviceContext) throws PortalException, SystemException {

		JSONObject payloadJSON = JSONFactoryUtil.createJSONObject();
		payloadJSON.put("userId", sender.getUserId());
		payloadJSON.put("url", customPageLink);
		payloadJSON.put("message", message);

		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), MyCustomPagesNotificationHandler.PORTLET_ID, payloadJSON);

		notificationEvent.setDeliveryRequired(0);

		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(recipient.getUserId(), notificationEvent);
	}

	public static void handleSocialActivities(Layout customPage, PortletRequest request, User receiver, int activityType)
			throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		String portletId = PortalUtil.getPortletId(request);
		String notificationMessage;
		int socialActivityType;
		if (activityType == CustomPageStatics.MESSAGE_TYPE_CUSTOM_PAGE_PUBLISHED) {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-published-message", new Object[] { themeDisplay.getUser().getFullName(),
							customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.CUSTOM_PAGE_PUBLISHED;
		} else if (activityType == CustomPageStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-feedback-requested-message", new Object[] {
							themeDisplay.getUser().getFullName(), customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.CUSTOM_PAGE_FEEDBACK_REQUESTED;
		} else {
			notificationMessage = LanguageUtil.format(portletConfig, themeDisplay.getLocale(),
					"custompages-custom-page-feedback-delivered", new Object[] { themeDisplay.getUser().getFullName(),
							customPage.getName(themeDisplay.getLocale()) });
			socialActivityType = ExtendedSocialActivityKeyConstants.CUSTOM_PAGE_FEEDBACK_DELIVERED;

		}
		createCustomPageActivity(customPage, themeDisplay.getUserId(), receiver.getUserId(), socialActivityType);
		// BEGIN CHANGE
		// add socialActivityType parameter to check if user wants to be notified
//		createCustomPageNotification(themeDisplay.getUser(), receiver, notificationMessage, customPage, portletId);
		createCustomPageNotification(themeDisplay.getUser(), receiver, notificationMessage, customPage, portletId, socialActivityType);
		// END CHANGE
		// BEGIN CHANGE
		// send email if user wants to be notified via email
		try {
			createCustomPageEmail(themeDisplay.getUser(), receiver, notificationMessage, 
					customPage, portletId, socialActivityType, themeDisplay);
		} catch (Exception e) {
			throw new SystemException(e);
		}
		// END CHANGE
	}

	// TODO: Da sich der Name �ndern kann, sollte er hier nicht so fest im JSON
	// kodiert werden, sondern dynamisch abgefragt werden, wenn die Activity
	// angezeigt wird
	public static void createCustomPageActivity(Layout layout, long userId, long receiverUserId, int socialActivityType)
			throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", layout.getUserId());
		jsonObject.put("title", layout.getName());
		SocialActivityLocalServiceUtil.addActivity(userId, 0, Layout.class.getName(), layout.getPlid(),
				socialActivityType, jsonObject.toString(), receiverUserId);
	}

	// TODO: Da sich der Name �ndern kann, sollte er hier nicht so fest in der
	// Nahricht kodiert werden, sondern dynamisch abgefragt werden, wenn die
	// Nachricht angezeigt wird
	private static void createCustomPageNotification(User sender, User receiver, String message, Layout customPage,
			String portletId, int socialActivityType) throws PortalException, SystemException {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("userId", sender.getUserId());
		jsonObject.put("plid", customPage.getPlid());
		jsonObject.put("message", message);
		
		// BEGIN CHANGE
		// isDeliver checks if user enabled notification for this socialActivity
		if(UserNotificationManagerUtil.isDeliver(
				receiver.getUserId(),
				portletId, 0,
				socialActivityType,
				UserNotificationDeliveryConstants.TYPE_WEBSITE)){
		
		NotificationEvent notificationEvent = NotificationEventFactoryUtil.createNotificationEvent(
				System.currentTimeMillis(), portletId, jsonObject);
		notificationEvent.setDeliveryRequired(0);
		UserNotificationEventLocalServiceUtil.addUserNotificationEvent(receiver.getUserId(), notificationEvent);
		}
		// END CHANGE
	}
	
	private static void createCustomPageEmail(User sender, User receiver, String message, Layout customPage,
			String portletId, int socialActivityType, ThemeDisplay themeDisplay) throws Exception {
		if(UserNotificationManagerUtil.isDeliver(
				receiver.getUserId(),
				portletId, 0,
				socialActivityType,
				UserNotificationDeliveryConstants.TYPE_EMAIL)){
			
			Company company = CompanyLocalServiceUtil.getCompany(sender.getCompanyId());
			
			String portalURL = PortalUtil.getPortalURL(
					company.getVirtualHostname(), PortalUtil.getPortalPort(false), false);
			
			String language = "";
			
			// check language, default: English
			if (receiver.getLocale().equals(Locale.GERMANY)) {
				language = "_"+Locale.GERMANY.toString();
			}
			
			String subject = "";
			String body = "";
			
			String templateType = "";
			
			// get templateType corresponding to socialActityId
			switch (socialActivityType) {
			case 1:
				// Seite freigegeben (share page)
				templateType = "share";
				break;
			
			case 2:
				// Seite eingereicht (submit page)
				templateType = "submit";
				break;
			
			case 3:
				// Feedback abgegeben (has given Feedback)
				templateType = "feedback";
				break;
	
			default:
				break;
			}
			
			subject = StringUtil.read(
					CustomUserLocalServiceWrapper.class.getResourceAsStream(
						"dependencies/email_custom_page_"+templateType+"_subject"+language+".tmpl"));
			
			body = StringUtil.read(
					CustomUserLocalServiceWrapper.class.getResourceAsStream(
						"dependencies/email_custom_page_"+templateType+"_body"+language+".tmpl"));
			
			// all templates have same placeholders
			subject = StringUtil.replace(
					subject,
					new String [] {
						"[$CUSTOM_PAGE_NAME$]"	
					},
					new String [] {
						customPage.getName(themeDisplay.getLocale())
					});
			
			body = StringUtil.replace(
					body,
					new String [] {
						"[$CUSTOM_PAGE_NAME$]",
						"[$TO_NAME$]", "[$USER_NAME$]",
						"[$CUSTOM_PAGE_URL$]", 
						"[$PAGEMANAGEMENT_URL$]",
						"[$CONFIG_URL$]"
					},
					new String [] { 
						customPage.getName(themeDisplay.getLocale()),
						receiver.getFullName(), sender.getFullName(),
						PortalUtil.getLayoutFullURL(customPage, themeDisplay), 
						portalURL+getPageManagementURL(receiver),
						getNotificationConfigURL(themeDisplay, receiver)
					}); 
			
			String fromName = PrefsPropsUtil.getString(
					sender.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_NAME);
			String fromAddress = PrefsPropsUtil.getString(
					sender.getCompanyId(), PropsKeys.ADMIN_EMAIL_FROM_ADDRESS);
		
			InternetAddress from = new InternetAddress(fromAddress, fromName);
			
			InternetAddress to = new InternetAddress(receiver.getEmailAddress());
			
			MailMessage mailMessage = new MailMessage(from, to, subject, body, true);
			
			MailServiceUtil.sendEmail(mailMessage);
		}
	}
	
	/**
	 * Create URL to notification configuration.
	 * @param themeDisplay
	 * @return URL
	 * @throws WindowStateException
	 * @throws PortletModeException
	 */
	private static String getNotificationConfigURL (ThemeDisplay themeDisplay, User user) 
			throws WindowStateException, PortletModeException, SystemException, PortalException {
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				user.getGroupId(), true);
		
		Layout layout  = null;
		
		if (!layouts.isEmpty())
			layout = layouts.get(0);

		if (layout != null) {
			
			PortletURL myUrl = PortletURLFactoryUtil.create(
					themeDisplay.getRequest(), "1_WAR_notificationsportlet",
					layout.getPlid(), PortletRequest.RENDER_PHASE);
			myUrl.setWindowState(WindowState.MAXIMIZED);
			myUrl.setPortletMode(PortletMode.VIEW);
			myUrl.setParameter("actionable", "false");
			myUrl.setParameter("mvcPath", "/notifications/configuration.jsp");
	
			return myUrl.toString();
		}
		return "";
	}

	/**
	 * Create URL to PageManagement, by searching all pages of a user
	 * until custom-pages-portlet is found.
	 * @param user
	 * @return URL to page with custom-pages-portlet
	 * @throws SystemException
	 * @throws PortalException
	 */
	private static String getPageManagementURL(User user) 
			throws SystemException, PortalException {
		
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(user.getGroupId(), true);
		
		Layout layout = null;
		
		// iterate over all layouts of workspace too get all portlets
		for (Layout possibleLayout : layouts) {
			LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet)possibleLayout.getLayoutType();
			List<String> actualPortletList = layoutTypePortlet.getPortletIds();
			
			// iterate over all portlets to find announcement portlet
			for (String portletId : actualPortletList) {
				if (("mycustompages_WAR_custompagesportlet".equals(portletId)) || 
						("othercustompages_WAR_custompagesportlet".equals(portletId))) {
					layout = possibleLayout;
					break;
				}
			}
		}
		
		if (layout != null)
			return PortalUtil.getLayoutActualURL(layout);
		
		
		return "";
	}
	
	public static void addToCustomPageJSONArray(JSONArray customPageJSONArray, Layout customPage,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject customPageJSON = JSONFactoryUtil.createJSONObject();
		customPageJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageJSON.put("plid", customPage.getPlid());
		customPageJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageJSON.put("customPageType", (Integer) customPage.getExpandoBridge().getAttribute("CustomPageType"));
		customPageJSON.put("lastChangesInMilliseconds", customPage.getModifiedDate().getTime());
		boolean isGlobal = CustomPageUtil.isPublishedGlobal(customPage.getPlid());
		customPageJSON.put("isGlobal", isGlobal);
		customPageJSON.put("isPrivate", customPage.isPrivateLayout());
		List<CustomPageFeedback> customPageFeedbacks = CustomPageUtil.getCustomPageFeedbacks(customPage.getPlid());
		JSONArray customPageFeedbackJSONArray = JSONFactoryUtil.createJSONArray();
		JSONObject customPageFeedbackJSON = null;
		boolean inFeedbackProcess = false;
		boolean feedbackDelivered = false;
		for (CustomPageFeedback customPageFeedback : customPageFeedbacks) {
			if (UserLocalServiceUtil.fetchUser(customPageFeedback.getUserId()) != null) {
				customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
				customPageFeedbackJSON.put("userId", customPageFeedback.getUserId());
				customPageFeedbackJSON.put("userName", customPageFeedback.getUser().getFullName());
				customPageFeedbackJSON.put("feedbackStatus", customPageFeedback.getFeedbackStatus());
				customPageFeedbackJSON.put(
						"creationDate",
						FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
								customPageFeedback.getCreateDate()));
				customPageFeedbackJSON.put(
						"modifiedDate",
						FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
								customPageFeedback.getModifiedDate()));
				customPageFeedbackJSONArray.put(customPageFeedbackJSON);
				if (customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_REQUESTED)
					inFeedbackProcess = true;
				else if (customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_DELIVERED)
					feedbackDelivered = true;
			}
		}
		customPageJSON.put("inFeedbackProcess", inFeedbackProcess);
		customPageJSON.put("feedbackDelivered", feedbackDelivered);
		customPageJSON.put("customPageFeedbacks", customPageFeedbackJSONArray);
		customPageJSON.put("isIndividual", customPageFeedbacks.size() != 0 && !isGlobal);
		customPageJSONArray.put(customPageJSON);
	}

	public static void publicAddToCustomPageJSONArray(JSONArray customPageJSONArray, Layout customPage,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		PortletConfig portletConfig = (PortletConfig) themeDisplay.getRequest().getAttribute(
				JavaConstants.JAVAX_PORTLET_CONFIG);
		JSONObject customPageJSON = JSONFactoryUtil.createJSONObject();
		customPageJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageJSON.put("customPageType", (Integer) customPage.getExpandoBridge().getAttribute("CustomPageType"));
		customPageJSON.put("isGlobal", CustomPageUtil.isPublishedGlobal(customPage.getPlid()));
		customPageJSON.put("isPrivate", customPage.isPrivateLayout());
		customPageJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageJSON.put(
				"lastChanges",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageJSON.put("lastChangesInMilliseconds", customPage.getModifiedDate().getTime());
		LayoutPrototype layoutPrototype = LayoutPrototypeLocalServiceUtil.fetchLayoutPrototypeByUuidAndCompanyId(
				customPage.getLayoutPrototypeUuid(), themeDisplay.getCompanyId());
		String templateName = LanguageUtil.get(portletConfig, themeDisplay.getLocale(), "custompages-no-template");
		if (layoutPrototype != null)
			templateName = layoutPrototype.getName(themeDisplay.getLocale());
		customPageJSON.put("templateName", templateName);
		customPageJSONArray.put(customPageJSON);
	}

	public static void addToCustomPageFeedbackJSONArray(JSONArray customPageFeedbackJSONArray, Layout customPage,
			ThemeDisplay themeDisplay, PortletConfig portletConfig, List<String> hiddenLayouts,
			List<String> newHiddenLayouts) throws PortalException, SystemException {
		JSONObject customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		boolean isHidden = hiddenLayouts.contains(String.valueOf(customPage.getPlid()));
		customPageFeedbackJSON.put("hidden", isHidden);
		if (isHidden)
			newHiddenLayouts.add(String.valueOf(customPage.getPlid()));
		customPageFeedbackJSON.put("plid", customPage.getPlid());
		customPageFeedbackJSON.put("userId", customPage.getUserId());
		customPageFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(customPage.getUserId()).getFullName());
		customPageFeedbackJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageFeedbackJSON.put("customPageType",
				(Integer) customPage.getExpandoBridge().getAttribute("CustomPageType"));
		customPageFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		boolean isGlobal = CustomPageUtil.isPublishedGlobal(customPage.getPlid());
		customPageFeedbackJSON.put("isGlobal", isGlobal);
		customPageFeedbackJSON.put("isIndividual", !isGlobal);
		CustomPageFeedback customPageFeedback = CustomPageUtil.getCustomPageFeedback(customPage.getPlid(),
				themeDisplay.getUserId());
		if (customPageFeedback == null)
			customPageFeedback = CustomPageUtil.getCustomPageFeedback(customPage.getPlid(), 0);
		customPageFeedbackJSON.put("feedbackStatus", customPageFeedback.getFeedbackStatus());
		customPageFeedbackJSON
				.put("feedbackStatusString",
						getFeedbackStatusString(portletConfig, themeDisplay.getLocale(),
								customPageFeedback.getFeedbackStatus()));
		customPageFeedbackJSON.put(
				"createDate",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPageFeedback.getCreateDate()));
		customPageFeedbackJSON.put("inFeedbackProcess",
				customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_REQUESTED);
		customPageFeedbackJSON.put("feedbackDelivered",
				customPageFeedback.getFeedbackStatus() == CustomPageStatics.FEEDBACK_DELIVERED);
		customPageFeedbackJSONArray.put(customPageFeedbackJSON);
	}

	public static void addGlobalCustomPageToJSONArray(JSONArray customPageFeedbackJSONArray, Layout customPage,
			ThemeDisplay themeDisplay) throws PortalException, SystemException {
		JSONObject customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON = JSONFactoryUtil.createJSONObject();
		customPageFeedbackJSON.put("plid", customPage.getPlid());
		customPageFeedbackJSON.put("userId", customPage.getUserId());
		customPageFeedbackJSON.put("userName", UserLocalServiceUtil.getUserById(customPage.getUserId()).getFullName());
		customPageFeedbackJSON.put("title", HtmlUtil.escape(customPage.getName(themeDisplay.getLocale())));
		customPageFeedbackJSON.put("url", PortalUtil.getLayoutFullURL(customPage, themeDisplay));
		customPageFeedbackJSON.put(
				"modifiedDate",
				FastDateFormatFactoryUtil.getDateTime(themeDisplay.getLocale(), themeDisplay.getTimeZone()).format(
						customPage.getModifiedDate()));
		customPageFeedbackJSON.put("modifiedDateInMilliseconds", customPage.getModifiedDate().getTime());
		customPageFeedbackJSONArray.put(customPageFeedbackJSON);
	}

	public static Map<Locale, String> getLocaleMap(String key, PortletConfig portletConfig) {
		Map<Locale, String> result = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			result.put(locale, LanguageUtil.get(portletConfig, locale, key));
		}
		return result;
	}

	/**
	 * Returns the layout prototype with the given name
	 * 
	 * @param name
	 * @return layout prototype
	 * @throws SystemException
	 */
	public static Map<String, LayoutPrototype> getCustomPageLayoutPrototypes() throws SystemException {
		Map<String, LayoutPrototype> result = new HashMap<String, LayoutPrototype>();
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());

		for (LayoutPrototype lp : layoutPrototypes) {
			if (lp.getName(Locale.GERMANY).equals(CustomPageStatics.BLOG_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMANY).equals(CustomPageStatics.CDP_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMANY).equals(CustomPageStatics.WIKI_LAYOUT_PROTOTYPE)
					|| lp.getName(Locale.GERMANY).equals(CustomPageStatics.EMPTY_LAYOUT_PROTOTYPE)) {
				result.put(lp.getName(Locale.GERMANY), lp);
			}
		}
		return result;
	}

	public static Layout getLayoutByNameOrCreate(PortletRequest request, String pageNameLocalizationString,
			boolean isHidden, boolean isPrivate, boolean addPortlet) throws SystemException, PortalException,
			ReadOnlyException, ValidatorException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		PortletConfig portletConfig = (PortletConfig) request.getAttribute(JavaConstants.JAVAX_PORTLET_CONFIG);
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(themeDisplay.getUser().getGroupId(), isPrivate);
		Layout layout = null;
		String parentPageName = LanguageUtil.get(portletConfig, themeDisplay.getLocale(), pageNameLocalizationString);

		for (Layout l : layouts) {
			if (l.getName(themeDisplay.getLocale()).equals(parentPageName))
				layout = l;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(Layout.class.getName(), request);

		// create custom pages parent page if none exists
		if (layout == null) {
			Map<Locale, String> localeMap = JspHelper.getLocaleMap(pageNameLocalizationString, portletConfig);
			layout = LayoutLocalServiceUtil.addLayout(themeDisplay.getUserId(), themeDisplay.getUser().getGroupId(),
					isPrivate, 0l, localeMap, localeMap, null, null, null, LayoutConstants.TYPE_PORTLET, null,
					isHidden, new HashMap<Locale, String>(), serviceContext);

			if (layout.getExpandoBridge().hasAttribute(CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME))
				layout.getExpandoBridge().setAttribute(CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME, "0");

			CustomPageUtil.setCustomPagePageType(layout, CustomPageStatics.CUSTOM_PAGE_TYPE_NONE);
			layout.getExpandoBridge().setAttribute(CustomPageStatics.CREATED_DYNAMICAL_CUSTOM_FIELD_NAME,
					new Boolean(true));

			if (addPortlet) {
				String portletId = (String) request.getAttribute(WebKeys.PORTLET_ID);
				LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
				layoutTypePortlet.setLayoutTemplateId(themeDisplay.getUserId(), "1_column");
				layoutTypePortlet.addPortletId(themeDisplay.getUserId(), portletId);

				LayoutLocalServiceUtil.updateLayout(layout);
			}
		}

		Role userRole = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), RoleConstants.OWNER);

		ResourcePermission resourcePermission = ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				PortalUtil.getDefaultCompanyId(), Layout.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layout.getPlid()), userRole.getRoleId());

		if (resourcePermission != null) {
			// Set (only) view permission if not yet set
			if (resourcePermission.getActionIds() != 1) {
				resourcePermission.setActionIds(1);
				ResourcePermissionLocalServiceUtil.updateResourcePermission(resourcePermission);
			}
		} else {
			// Add resourcePermission if not existent
			ResourcePermissionLocalServiceUtil.setResourcePermissions(PortalUtil.getDefaultCompanyId(),
					Layout.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL, String.valueOf(layout.getPlid()),
					userRole.getRoleId(), new String[] { ActionKeys.VIEW });
		}

		return layout;
	}
	
	
}
