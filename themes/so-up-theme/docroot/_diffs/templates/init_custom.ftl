<#if is_signed_in>
	<#assign user_my_sites = user.getMySiteGroups() />
	<#assign user_sname = user.getScreenName() />
	<#assign user_account_url = themeDisplay.getURLMyAccount() />
<#else>
	<#assign user_my_sites = user.getMySiteGroups()  />
	<#assign user_sname = "" />
	<#assign user_account_url = "" />
</#if>