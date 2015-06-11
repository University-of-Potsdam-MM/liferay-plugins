<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
<!--   <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.min.js"></script>
	<script src="//aui-cdn.atlassian.com/aui-adg/5.8.10/js/aui.js"></script>
	<link rel="stylesheet" type="text/css" href="//aui-cdn.atlassian.com/aui-adg/5.8.10/css/aui.css"/>-->	
</head>
<body class="${css_class}">
<div class="container-fluid" id="wrapper">
	<#if is_signed_in>
		<div id="sidebar">
			<div class="title">Workspaces<span class="arrow"></span></div>
			<div class="content">
				<h2>Workspaces</h2>
				 
				<div class="search"><span class="icon search"></span>Suchen</div>
				<div class="add"><span class="icon add"></span>Hinzuf&uuml;gen</div>
				<div class="filter"><span class="icon"></span><input name="filter" id="grouproom-filter"></div>
				<div class="switch">
					<input type="radio" name="select-type" id="own" value="sidebar-my-sites" selected="selected">Eigene</input>
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
	        	<ul id="sidebar-all-sites hidden">
	        		<#list theme.sitesDirectory() as all_site>
	        			<li></li>
	        		</#list>
	        	</ul>
			</div>
		</div>
	</#if>
	<header id="mobile-menu">
		<div id="up-menu">
			<a href="#grouproom-trigger" aria-owns="grouproom" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger ">
				Gruppenr&auml;ume</a>
			<a href="#personal-trigger" aria-owns="personal" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger">
				Pers&ouml;nlicher Bereich</a>
			<a href="#profile-trigger" aria-owns="profile" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger">
				Mein Profil</a>
			<a href="#contact-trigger" aria-owns="contact" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger">
				Meine Kontakte</a>
			<a href="#portfolio-trigger" aria-owns="portfolio" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger">
				Portfolio</a>
			<a class="aui-button aui-style-default edit">
				Edit</a>
			<a href="#services" aria-owns="services" aria-haspopup="true" class="aui-button aui-style-default aui-dropdown2-trigger up-services">
				UP Dienste</a>
		</div>
		<div id="grouproom" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<#list user_my_sites as user_site>
					<#if user_site.hasPrivateLayouts()>
						<li><a href="/group${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
					<#elseif user_site.hasPublicLayouts()>
						<li><a href="/web${user_site.friendlyURL}">${user_site.descriptiveName}</a></li>
					</#if>
				</#list>
			</ul>
		</div>
		<div id="personal" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
		<div id="profile" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
		<div id="contact" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
		<div id="portfolio" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
		<div id="services" class="aui-style-default aui-dropdown2">
			<ul class="aui-list-truncate">
				<li></li>
			</ul>
		</div>
	</header>
	<header id="banner" role="banner">
		<div class="" id="up_section_indicator"></div>
		<div id="up_logo">
            <div id="up_logo_indicator"></div>
            <div id="up_logo_image">
                <a title="Zur Startseite" href="/">
                <img alt="Logo Universit&auml;t Potsdam" src="/so-up-theme/images/up/up_logo_university_2.png"></a>
            </div>
	        <div id="up_logo_title">
	            <a title="Zur Startseite" href="/">Learn.UP</a>
	        </div>
            <div id="up_logo_footer">
            </div>
        </div>
		<ul id="up-general">
			<li class="unipage"><a href="http://www.uni-potsdam.de">Uni Startseite</a></li>
			<li class="services"><a class="up_services" href="">UP Dienste</a><span class="icon arrow"></span>
				<ul class="service_list hidden">
					<li><a href="https://moodle2.uni-potsdam.de/">Moodle</a></li> 
					<li><a href="https://puls.uni-potsdam.de/qisserver/rds?state=user&type=0&application=lsf">PULS</a></li>
					<li><a href="http://mediaup.uni-potsdam.de/">Media.UP</a></li>
					<li><a href="http://info.ub.uni-potsdam.de/">Bibliothek</a></li>
					<li><a href="http://www.hochschulsport-potsdam.de/">Hochschulsport</a></li>
				</ul> 
			</li>			
			<li class="lang">
				<span class="lang-img"></span>
				<select name="language_id">
					<option value="de_DE" selected>Deutsch</option>
					<option value="en_US">English</option>
				</select>
			</li>
			<li class="search">
				<span class="search-img"></span>
				<div id="searchfield" class="hidden searchfield">
					<span class="search-img"></span>
					${theme.search()}
				</div>
			</li>
			
			<#if is_signed_in>
				<li class="logout"><a href="${sign_out_url}" id="sign-out" rel="nofollow">${user_sname}<span class="icon logout"></span></a></li>
			<#else>
				<li><a href="${sign_in_url}" data-redirect="${is_login_redirect_required?string}" id="sign-in" rel="nofollow">${sign_in_text}</a></li>
			</#if>
		</ul>
		<#if is_signed_in>
		<ul id="admin">
			<li>
				<div id="toggleDockbar">
					<span class="icon workspace"></span>Workspace konfigurieren
				</div>
			</li>
			<#if show_control_panel>
			<li>
				<a href="${control_panel_url}">
				<div id="adminbox">
					<span class="icon admin"></span>Administration
				</div>
				</a>
			</li>
			</#if>
		</ul>
		</#if>
		
		<#if is_signed_in>
		<ul id="main-menu" class="enabled">
			<li>
				Pers&ouml;nlicher Bereich <span class="icon arrow"></span>
				<ul class="hidden">
					<li><a href="/user/${user_sname}/dashboard/">&Uumlbersicht</a></li>
					<li><a href="/user/${user_sname}/calendar/">Kalender</a></li>
					<li><a href="/user/${user_sname}/e-mail/">Webmail</a></li>
					<li>Box.Up</li>
					<li>Poodle</li>
					<li>Pad.Up</li>
				</ul>
			</li>
			<li>
				Mein Profil <span class="icon arrow"></span>
				<ul class="hidden">
					<li><a href="${my_account_url}">Profilansicht</a></li>
					<li>Bearbeitungsmodus</li>
				</ul>
			</li>
			<li>
				Meine Kontakte <span class="icon arrow"></span>
				<ul class="hidden">
					<li><a href="/user/${user_id}/contacts/">Kontaktliste</a></li>
					<li>Personen suchen</li>
				</ul>
			</li>
			<li>
				Portfolio <span class="icon arrow"></span>
				<ul class="hidden">
					<li>Kategorien</li>
					<li>Portfolioseiten</li>
					<li>Portfolioaufgaben</li>
				</ul>
			</li>
			<span class="icon close hidden">Schlieﬂen</span>
		</ul>
		<#else>	
		<ul id="main-menu" class="disabled">
			<li>
				Pers&ouml;nlicher Bereich <span class="icon arrow disabled"></span>
			</li>
			<li>
				Mein Profil <span class="icon arrow disabled"></span>
			</li>
			<li>
				Meine Kontakte <span class="icon arrow disabled"></span>
			</li>
			<li>
				Portfolio <span class="icon arrow disabled"></span>
			</li>
		</ul>
		</#if>	
		
		<!-- Ausblenden damit es keine Probleme beim JS gibt -->
		<div class="hidden">
			<@liferay.dockbar />
			${theme.include(body_top_include)}
		</div>

		
	
		<nav id="breadcrumbs"><@liferay.breadcrumbs /></nav>
	</header>
	<div class="wrapper-portlet-area">
	<div id="heading">
			<h1 class="site-title">
				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" />
				</a>

				<#if show_site_name>
					<span class="site-name" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
						${site_name}
					</span>
				</#if>
			</h1>

			<h2 class="page-title">
				<span>${the_title}</span>
			</h2>
		</div>
	
		<#if has_navigation>
				<#include "${full_templates_path}/navigation.ftl" />
		</#if>

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

	<footer id="footer" role="contentinfo">
	</footer>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>