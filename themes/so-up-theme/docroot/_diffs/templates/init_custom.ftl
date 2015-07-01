<#if is_signed_in>
	<#assign user_my_sites = user.getMySiteGroups() />
	<#assign user_sname = user.getScreenName() />
	<#assign user_account_url = themeDisplay.getURLMyAccount() />
	<#assign sign_out_url = htmlUtil.escape(theme_display.getURLSignOut()) />
	
	<#assign has_layout_add_permission = 0 />
	
	<#if (layout.getParentLayoutId() == 0)>
		<#assign has_layout_add_permission = groupPermission.contains(permissionChecker, page_group.getGroupId(), "ADD_LAYOUT") />
	<#else>
		<#assign has_layout_add_permission = layoutPermission.contains(permissionChecker, layout, "ADD_LAYOUT") />
	</#if>
	
	<#assign has_layout_customize_permission = layoutPermission.contains(permissionChecker, layout, "CUSTOMIZE") />
	<#assign has_layout_update_permission = layoutPermission.contains(permissionChecker, layout, "UPDATE") />
	
	<#assign show_add_controls = has_layout_add_permission || has_layout_update_permission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && has_layout_customize_permission) />
	
	<#assign show_edit_controls = (theme_display.isShowLayoutTemplatesIcon() || theme_display.isShowPageSettingsIcon()) />
	<#assign show_preview_controls = (has_layout_update_permission || groupPermission.contains(permissionChecker, page_group.getGroupId(), "PREVIEW_IN_DEVICE")) />
	<#assign show_toggle_controls = ((!page_group.hasStagingGroup()) || page_group.isStagingGroup()) && (has_layout_update_permission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView()) || portletPermission.hasConfigurationPermission(permissionChecker, theme_display.getSiteGroupId(), layout, "CONFIGURATION")) />

	<#assign liferay_toggle_controls = sessionClicks.get(request, "liferay_toggle_controls", "visible") />
	
	<#assign themeNamespace = "_" + themeDisplay.getThemeId() + "_" />

	<#if liferay_toggle_controls == "">
		${sessionClicks.put(request, "liferay_toggle_controls", "hidden")}

		<#assign liferay_toggle_controls = "hidden" />
		<#assign css_class = $css_class.replaceAll("controls-visible", "controls-hidden") />
		
	</#if>	
	<#-- get private sites -->
	<#assign group = user.getGroup() />
	<#assign PortalUtil = staticUtil["com.liferay.portal.util.PortalUtil"] />
	<#assign layoutService = objectUtil("com.liferay.portal.service.LayoutLocalServiceUtil") />
	<#assign myLayouts = layoutService.getLayouts(group.getGroupId(), true) />

	
<#else>
	<#assign user_my_sites = user.getMySiteGroups()  />
	<#assign user_sname = "" />
	<#assign user_account_url = "" />
</#if>