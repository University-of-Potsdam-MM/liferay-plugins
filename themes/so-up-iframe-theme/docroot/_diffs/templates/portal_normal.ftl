<!DOCTYPE html>

<#include init />

<html class="${root_css_class} smallhead" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${company_name} | ${the_title}</title>
	<meta content="initial-scale=1.0, width=device-width" name="viewport" />
	${theme.include(top_head_include)}
	

</head>
<body class="dockbar-split so-strata-theme ${css_class}">
	
	<!-- Ausblenden damit es keine Probleme beim JS gibt-->
	<div class="hidden">
		${theme.search()}
		${theme.include(body_top_include)}
	</div>
	<div class="windowdiv">
	</div>

<div class="container-fluid" id="wrapper">
	<#if is_signed_in>
		<div id="sidebar">
			<div class="title"><span>Workspaces</span><span class="arrow"></span></div>
			<div class="content">
				<h2>Workspaces</h2>
				 
				<div class="search"><span class="icon search"></span>Suchen</div>
				<div class="add"><span class="icon add"></span>Hinzuf&uuml;gen</div>
				<div class="filter"><span class="icon"></span><input name="filter" id="grouproom-filter"></div>
				<div class="switch">
					<input type="radio" name="select-type" id="own" value="sidebar-my-sites" checked="checked">Eigene</input>
					<input type="radio" name="select-type" id="public" value="sidebar-all-sites">&Ouml;ffentliche</input>
				</div>
				<ul id="sidebar-my-sites">
					<#list user_my_sites as user_site>
						<#if user_site.hasPrivateLayouts()>
							<li><a href="/group${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
						<#elseif user_site.hasPublicLayouts()>
							<li><a href="/web${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
						</#if>
					</#list>
	        	</ul>
	        	<ul id="sidebar-all-sites" class="hidden">
	        	</ul>
			</div>
		</div>
	</#if>
	<header id="mobile-menu">
		<div id="up-menu">
			<#if is_signed_in>
			<div class="main">
				<a href="#grouproom-trigger" class="mobile-menu aui-button aui-style-default aui-dropdown2-trigger ">
					<span class="icon grouproom"></span></a>
				<a href="#personal-trigger" class="mobile-menu aui-button aui-style-default aui-dropdown2-trigger">
					<span class="icon personal"></span></a>
				<a href="#contact-trigger" class="mobile-menu aui-button aui-style-default aui-dropdown2-trigger">
					<span class="icon profile"></span></a>
				<a href="#portfolio-trigger" class="mobile-menu aui-button aui-style-default aui-dropdown2-trigger">
					<span class="icon portfolio"></span></a>
			</div>
						
			</#if>
		</div>
		<div id="grouproom-trigger" class="submenu aui-style-default aui-dropdown hidden">
			<#if is_signed_in>
			<!-- <ul class="aui-list-truncate">
				<#list user_my_sites as user_site>
					<#if user_site.hasPrivateLayouts()>
						<li><a href="/group${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
					<#elseif user_site.hasPublicLayouts()>
						<li><a href="/web${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
					</#if>
				</#list>
			</ul> -->
			
			
				<ul class="aui-list-truncate">
					<#list myPrivateLayouts as myLayout>
						<#assign portfoliopage = false />
						<#if myLayout.getExpandoBridge().hasAttribute("Portfolio")>
							<#if myLayout.getExpandoBridge().getAttribute("Portfolio")??>
								<#assign portfoliopage = myLayout.getExpandoBridge().getAttribute("Portfolio") />
							</#if>
						</#if>1
						<#if myLayout.isRootLayout() && !myLayout.isHidden()>
							<#if portfoliopage?string("true", "false") = "false">
								<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
							</#if>
						</#if>
					</#list>
				</ul>
				
			</#if>
		</div>
		<div id="personal-trigger" class="submenu aui-style-default aui-dropdown2 hidden">
			<#if is_signed_in>
			<#--<ul class="aui-list-truncate">
				<#list myPrivateLayouts as myLayout>
					<#if myLayout.getExpandoBridge().getAttribute("Portfolio")??>
						<#assign portfoliopage = myLayout.getExpandoBridge().getAttribute("Portfolio") />
					</#if>
					<#if myLayout.isRootLayout() && !myLayout.isHidden()>
						<#if portfoliopage??>
							<#if portfoliopage?string("true", "false") = "false">
								<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
							</#if>
						</#if>	
					</#if>
				</#list>
			</ul> -->
			<ul class="aui-list-truncate">
					<li><a href="${portal_url}/user/${user_sname}/mediaup/">Media.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/moodle/">Moodle.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/boxup/">Box.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/padup/">Pad.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/puls/">PULS</a></li>
					<li><a href="${portal_url}/user/${user_sname}/mediaup/"><@liferay.language key="so-up-theme-library" /></a></li>	
				</ul>
			
			</#if>
		</div>
		<div id="contact-trigger" class="submenu aui-style-default aui-dropdown2 hidden">
			
			<#if is_signed_in>
			<ul class="aui-list-truncate">
				
					<#list myPublicLayouts as myLayout>
						<#if !myLayout.getName(themeDisplay.getLocale()).equals("Portfolio") && myLayout.isRootLayout() && !myLayout.isHidden()>
							<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
						</#if>
					</#list>
				
			</ul>
			</#if>
		</div>
		<div id="portfolio-trigger" class="submenu aui-style-default aui-dropdown2 hidden">
			<#if is_signed_in>
			<ul class="aui-list-truncate">
					
				<#list myPrivateLayouts as myLayout>
					<#assign portfoliopage = false />
					<#if myLayout.getExpandoBridge().hasAttribute("Portfolio")>
						<#if myLayout.getExpandoBridge().getAttribute("Portfolio")??>
							<#assign portfoliopage = myLayout.getExpandoBridge().getAttribute("Portfolio") />
						</#if>
					</#if>
					<#if myLayout.isRootLayout() && !myLayout.isHidden()>
						<#if portfoliopage?string("true", "false") = "true">
							<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
						</#if>
					</#if>
				</#list>
				
			</ul>
		</#if>
		</div>
		<div id="services-trigger" class="submenu aui-style-default aui-dropdown2 hidden">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
	</header>
	<header id="banner" role="banner">
		<@liferay.dockbar />

		<#if is_signed_in>
		<ul id="main-menu" class="enabled">
			<li>
				<@liferay.language key="so-up-theme-private-space" /><span class="icon arrow"></span>
				<ul class="hidden">
					<#list myPrivateLayouts as myLayout>
						<#assign portfoliopage = false />
						<#if myLayout.getExpandoBridge().hasAttribute("Portfolio")>
							<#if myLayout.getExpandoBridge().getAttribute("Portfolio")??>
								<#assign portfoliopage = myLayout.getExpandoBridge().getAttribute("Portfolio") />
							</#if>
						</#if>
						<#if myLayout.isRootLayout() && !myLayout.isHidden()>
							<#if portfoliopage?string("true", "false") = "false">
								<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
							</#if>
						</#if>
					</#list>
				</ul>
			</li>
			<li>
				<@liferay.language key="so-up-theme-up-services" /><span class="icon arrow"></span>
				<ul class="hidden">
					<li><a href="${portal_url}/user/${user_sname}/mediaup/">Media.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/moodle/">Moodle.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/boxup/">Box.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/padup/">Pad.UP</a></li>
					<li><a href="${portal_url}/user/${user_sname}/puls/">PULS</a></li>
					<li><a href="${portal_url}/user/${user_sname}/mediaup/"><@liferay.language key="so-up-theme-library" /></a></li>	
				</ul>
			</li>
			<li>
				<@liferay.language key="so-up-theme-public-space" /><span class="icon arrow"></span>
				<ul class="hidden">
					<#list myPublicLayouts as myLayout>
						<#if !myLayout.getName(themeDisplay.getLocale()).equals("Portfolio") && myLayout.isRootLayout() && !myLayout.isHidden()>
							<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
						</#if>
					</#list>
				</ul>
			</li>
			<li><@liferay.language key="so-up-theme-portfolio" />
				<span class="icon arrow"></span>
				<ul class="hidden">
					<#list myPrivateLayouts as myLayout>
						<#assign portfoliopage = false />
						<#if myLayout.getExpandoBridge().hasAttribute("Portfolio")>
							<#if myLayout.getExpandoBridge().getAttribute("Portfolio")??>
								<#assign portfoliopage = myLayout.getExpandoBridge().getAttribute("Portfolio") />
							</#if>
						</#if>
						<#if myLayout.isRootLayout() && !myLayout.isHidden()>
							<#if portfoliopage?string("true", "false") = "true">
								<li><a href="${PortalUtil.getLayoutURL(myLayout, themeDisplay)}">${myLayout.getName(themeDisplay.getLocale())}</a></li>
							</#if>
						</#if>
					</#list>
				</ul>
			</li>
			<span class="icon close hidden"><@liferay.language key="close" /></span>
		</ul>
		<#else>	
		<ul id="main-menu" class="disabled">
			<li>
				<@liferay.language key="so-up-theme-private-space" /><span class="icon arrow disabled"></span>
			</li>
			<li>
				<@liferay.language key="so-up-theme-up-services" /><span class="icon arrow disabled"></span>
			</li>
			<li>
				<@liferay.language key="my-profile" /><span class="icon arrow disabled"></span>
			</li>
			<li>
				<@liferay.language key="so-up-theme-portfolio" /><span class="icon arrow disabled"></span>
			</li>
		</ul>
		</#if>		
	
	</header>
	<div class="wrapper-portlet-area">
		<div id="heading">
			
			<!-- <div id="subnav-mobile">
			<#if has_navigation>
			
			<h3 class="clicker">Navigation</h3>
				<#include "${full_templates_path}/navigation-mobile.ftl" />
		
			</#if>
				</div>
			
		</div>
	
		<#if has_navigation>
				<#include "${full_templates_path}/navigation.ftl" />
		</#if> -->

		<div id="content">
		
			<#if selectable>
				${theme.include(content_include)}
			<#else>
				${portletDisplay.recycle()}

				${portletDisplay.setTitle(the_title)}

				${theme.wrapPortlet("portlet.ftl", content_include)}
			</#if>
		</div><!-- end content -->
	</div><!-- end portlet area wrapper -->

</div>

${theme.include(body_bottom_include)}
	
	
</body>

${theme.include(bottom_include)}

</html>