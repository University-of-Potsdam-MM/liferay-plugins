<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>${the_title} - ${company_name}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" />

	${theme.include(top_head_include)}
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>
    <script src="http://cdnjs.cloudflare.com/ajax/libs/require.js/2.1.15/require.min.js"></script>
	<script src="//aui-cdn.atlassian.com/aui-adg/5.8.10/js/aui.js"></script>
	<link rel="stylesheet" type="text/css" href="//aui-cdn.atlassian.com/aui-adg/5.8.10/css/aui.css"/>	
</head>
<body class="${css_class}">

<div class="container-fluid" id="wrapper">
	<#if is_signed_in>
		<div id="sidebar">
			<div class="title">Gruppenr&auml;ume</div>
			<div class="content">
				Hier muss die Liste der Gruppenräume rein
			</div>
		</div>
	</#if>
	<div id="line">
		<div class="blue"></div>
		<img class="lineimg" src="logout.png" />
		<img class="lineimg" src="config.png" />
	</div>
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
				<li>Gruppenraum 1</li>
				<li>Gruppenraum 2</li>
				<li>Gruppenraum 3</li>
				<li>Gruppenraum 4</li>
				<li>Gruppenraum 5</li>
				<li>Gruppenraum 6</li>
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
		<div id="colorbar" />
		<ul id="up-general">
			<li><a href="http://www.uni-potsdam.de">Uni Startseite</a></li>
			<#if is_signed_in>
				<li><a href="${sign_out_url}" id="sign-out" rel="nofollow">${sign_out_text}</a></li>
			<#else>
				<li><a href="${sign_in_url}" data-redirect="${is_login_redirect_required?string}" id="sign-in" rel="nofollow">${sign_in_text}</a></li>
			</#if>
			<li><select>
				<option>Deutsch</option>
			</select></li>
			<li>Suche</li>
		</ul>
		<div id="logo" ></div>
		<#if is_signed_in>
		<div id="editbox">
			<input type="checkbox" id="editToggle">Bearbeitung Ein</input>
			<select >
				<option></option>
			</select >
		</div>
		</#if>
		
		${theme.include(body_top_include)}
		
		<@liferay.dockbar />

		<div id="heading">
			<h1 class="site-title">
				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" />
				</a>

				<#if show_site_name>
					<span class="site-name" title="<@liferay.language_format objects="${site_name}" key="go-to-x" />">
						${site_name}
					</span>
				</#if>
			</h1>

			<h2 class="page-title">
				<span>${the_title}</span>
			</h2>
		</div>
	</header>

	<div id="content">
		<nav id="breadcrumbs"><@liferay.breadcrumbs /></nav>

		<#if selectable>
			${theme.include(content_include)}
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			${theme.wrapPortlet("portlet.ftl", content_include)}
		</#if>
	</div>

	<footer id="footer" role="contentinfo">
	</footer>
</div>

${theme.include(body_bottom_include)}

${theme.include(bottom_include)}

</body>

</html>