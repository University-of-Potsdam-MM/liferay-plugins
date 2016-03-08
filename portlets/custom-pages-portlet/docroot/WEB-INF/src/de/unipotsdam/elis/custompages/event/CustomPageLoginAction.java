package de.unipotsdam.elis.custompages.event;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.compat.portal.kernel.util.LocaleUtil;
import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.util.CustomPageUtil;
import de.unipotsdam.elis.custompages.util.jsp.JspHelper;

public class CustomPageLoginAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		try {
			creatLayoutIfNecessary(request, "custompages-page-management");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Layout creatLayoutIfNecessary(HttpServletRequest request, String pageNameLocalizationString)
			throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				"mycustompages_WAR_custompagesportlet");
		PortletConfig portletConfig = PortletConfigFactoryUtil
				.create(portlet, request.getSession().getServletContext());
		User user = UserLocalServiceUtil.getUser(Long.parseLong(request.getRemoteUser()));
		PermissionChecker checker = PermissionCheckerFactoryUtil.create(user);
		PermissionThreadLocal.setPermissionChecker(checker);
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(user.getGroupId(), true);
		Layout layout = null;
		String pageName = LanguageUtil.get(portletConfig, request.getLocale(), pageNameLocalizationString);

		for (Layout l : layouts) {
			Boolean createdDynamical = (Boolean) l.getExpandoBridge().getAttribute(
					CustomPageStatics.CREATED_DYNAMICAL_CUSTOM_FIELD_NAME);
			if (l.getName(request.getLocale()).equals(pageName) && createdDynamical.booleanValue())
				layout = l;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(request);

		// create custom pages parent page if none exists
		if (layout == null) {
			Map<Locale, String> localeMap = JspHelper.getLocaleMap(pageNameLocalizationString, portletConfig);
			layout = LayoutLocalServiceUtil.addLayout(user.getUserId(), user.getGroupId(), true, 0l, localeMap,
					localeMap, null, null, null, LayoutConstants.TYPE_PORTLET, null, false,
					new HashMap<Locale, String>(), serviceContext);

			layout.getExpandoBridge().setAttribute(CustomPageStatics.PERSONAL_AREA_SECTION_CUSTOM_FIELD_NAME, "2");
			layout.getExpandoBridge().setAttribute(CustomPageStatics.CREATED_DYNAMICAL_CUSTOM_FIELD_NAME,
					new Boolean(true));
			CustomPageUtil.setCustomPagePageType(layout, CustomPageStatics.CUSTOM_PAGE_TYPE_NONE);

			LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
			layoutTypePortlet.setLayoutTemplateId(user.getUserId(), "1_column");
			layoutTypePortlet.addPortletId(user.getUserId(), portlet.getPortletId());

			LayoutLocalServiceUtil.updateLayout(layout);

			PortletPreferences portletSetup = PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout,
					portlet.getPortletId());
			portletSetup.setValue("portletSetupTitle_" + LocaleUtil.toLanguageId(LocaleUtil.GERMAN), "");
			portletSetup.setValue("portletSetupTitle_" + LocaleUtil.toLanguageId(LocaleUtil.ENGLISH), "");
			portletSetup.setValue("portletSetupUseCustomTitle", String.valueOf(true));
			portletSetup.store();
		}

		LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) layout.getLayoutType();
		if (!layoutTypePortlet.getLayoutTemplateId().equals("1_column")) {
			layoutTypePortlet.setLayoutTemplateId(user.getUserId(), "1_column");
		}

		if (!layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
			layoutTypePortlet.addPortletId(user.getUserId(), portlet.getPortletId());
			LayoutLocalServiceUtil.updateLayout(layout);
		}

		portlet = PortletLocalServiceUtil.getPortletById(PortalUtil.getDefaultCompanyId(),
				"othercustompages_WAR_custompagesportlet");

		if (!layoutTypePortlet.hasPortletId(portlet.getPortletId())) {
			layoutTypePortlet.addPortletId(user.getUserId(), portlet.getPortletId());
			LayoutLocalServiceUtil.updateLayout(layout);
		}

		List<?> dashboardLayouts = LayoutLocalServiceUtil.dynamicQuery(DynamicQueryFactoryUtil.forClass(Layout.class)
				.add(PropertyFactoryUtil.forName("friendlyURL").eq("/so/dashboard"))
				.add(PropertyFactoryUtil.forName("sourcePrototypeLayoutUuid").eq("")));

		if (!dashboardLayouts.isEmpty()) {
			layout.setPriority(((Layout) dashboardLayouts.get(0)).getPriority() + 1);
			LayoutLocalServiceUtil.updateLayout(layout);
		} else if (layout.getPriority() != 0) {
			layout.setPriority(10);
			LayoutLocalServiceUtil.updateLayout(layout);
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

	// TODO: maybe there is a better way to get the LayoutSetPrototype
	private static LayoutSetPrototype getUserHomeLayoutSetPrototype() throws SystemException {
		for (LayoutSetPrototype layoutSetPrototype : LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(-1, -1)) {
			if (layoutSetPrototype.getDescription().equals("Social Office User Home"))
				return layoutSetPrototype;
		}
		return null;
	}

}
