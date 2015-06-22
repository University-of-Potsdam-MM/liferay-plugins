<#if is_signed_in>
	<#assign user_my_sites = user.getMySiteGroups() />
	<#assign user_sname = user.getScreenName() />
	<#assign user_account_url = themeDisplay.getURLMyAccount() />
	
	<#assign has_layout_add_permission = false />
	
	<#if (layout.getParentLayoutId() == 0)>
		<#assign has_layout_add_permission = groupPermission.contains(permissionChecker, page_group.getGroupId(), "ADD_LAYOUT") />
	<#else>
		<#assign has_layout_add_permission = layoutPermission.contains(permissionChecker, layout, "ADD_LAYOUT") />
	</#if>
	
	<#assign has_layout_customize_permission = layoutPermission.contains(permissionChecker, layout, "CUSTOMIZE") />
	<#assign has_layout_update_permission = layoutPermission.contains(permissionChecker, layout, "UPDATE") />
	
	<#assign show_add_controls = (has_layout_add_permission || has_layout_update_permission || (layoutTypePortlet.isCustomizable() && layoutTypePortlet.isCustomizedView() && has_layout_customize_permission)) />
	
<#else>
	<#assign user_my_sites = user.getMySiteGroups()  />
	<#assign user_sname = "" />
	<#assign user_account_url = "" />
</#if>