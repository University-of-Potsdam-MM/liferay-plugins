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
		<div class="" id="up_section_indicator"></div>
		<div id="up_logo">
            <div id="up_logo_indicator"></div>
            <div id="up_logo_image">
                <a title="Zur Startseite" href="/">
                <img alt="Logo Universit&auml;t Potsdam" src="/so-up-theme/images/up/up_logo_university_2.png"></a>
            </div>
	        <div id="up_logo_title">
	            <a title="Zur Startseite" href="${company_url}">${company_name}</a>
	            <span class="site-name-mobile">// ${site_name}</span>
	        </div>
            <div id="up_logo_footer">
            </div>
        </div>
		<@liferay.dockbar />
		<#if is_signed_in>
		
		<ul id="admin">
			<#if ((!page_group.isControlPanel()) && user.isSetupComplete() && (show_add_controls || show_edit_controls || show_preview_controls || show_toggle_controls))>		
			<li>
				<a href="javascript:;" id="toggleDockbar">
					<!--<a class="toggle-controls-link" role="menuitem" href="javascript:void(0);" tabindex="0">-->
						<span class="icon workspace"></span><@liferay.language key="so-up-theme-workspace-configure" />
					<!--</a>-->
				</a>
			</li>
			</#if>
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
	
		<nav id="breadcrumbs"><@liferay.breadcrumbs /></nav>
	</header>
	<div class="wrapper-portlet-area">
		<div id="heading">
			<h1 class="site-title">
				<a class="${logo_css_class}" href="${site_default_url}" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
					<!-- <img alt="${logo_description}" height="${site_logo_height}" src="${site_logo}" width="${site_logo_width}" /> -->
				</a>

				<!-- <#if show_site_name> -->
					<span class="site-name hideformobile" title="<@liferay.language_format arguments="${site_name}" key="go-to-x" />">
						${site_name}
					</span> 
				<!--</#if> -->
					
					
			
			
				
			</h1>

			<h2 class="page-title">
				<span>${the_title}</span>
			</h2>
			
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

	
	<#if !is_signed_in>
		<!-- OE Footer -->
	<div id="up_oe_footer_wrapper">
		<div id="up_oe_footer" class="row-f up-smooth-box">

			<div class="six columns up-oe-footer-box up-oe-footer-box-first">
				<div class="up-oe-footer-box-header">
					<h2>Soziale Medien</h2>
					<div class="up-oe-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-oe-footer-box-content">
					<p class="bodytext">
						<span style="font-weight: normal; line-height: 20.296875px;"><a
							href="https://www.facebook.com/unipotsdam"
							title="Unsere Facebook-Fanpage im neuen Fenster �ffnen"
							target="_blank" class="up-sm-facebook">Facebook</a></span><br />
						<span style="font-weight: normal; line-height: 20.296875px;"><a
							href="http://www.youtube.com/user/PresseUniPotsdam"
							title="Unseren YouTube-Kanal im neuen Fenster �ffnen"
							target="_blank" class="up-sm-youtube">YouTube</a></span><br />
						<span style="font-weight: normal; line-height: 20.296875px;"><a
							href="http://www.xing.com/companies/universit%C3%84tpotsdam"
							title="Unsere XING-Seite im neuen Fenster �ffnen" target="_blank"
							class="up-sm-xing">XING</a></span><br />
						<span style="font-weight: normal; line-height: 20.296875px;"><a
							href="https://twitter.com/unipotsdam"
							title="Unsere Twitter-Seite im neuen Fenster �ffnen"
							target="_blank" class="up-sm-twitter">Twitter</a><br /></span><span
							style="font-family: inherit; line-height: 1.45em;"><a
							href="http://www.researchgate.net/institution/Universit�Potsdam"
							title="Unsere Research Gate-Seite im neuen Fenster �ffnen"
							target="_blank" class="up-sm-researchgate">Research Gate</a><br />
						<br /></span>
					</p>
				</div>
			</div>


			<div class="six columns up-oe-footer-box">
				<div class="up-oe-footer-box-header">
					<h2>Kontakt</h2>
					<div class="up-oe-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-oe-footer-box-content">
					<p class="bodytext">
						Universit&auml;t Potsdam<br />Am Neuen Palais 10<br />14469 Potsdam
					</p>
					<p class="bodytext">
						Tel.: 0331 977-0<br />Fax: 0331 97 21 63
					</p>
					<p class="bodytext">
						<a href="http://www.zeik.uni-potsdam.de/etc/impressum.html">Impressum</a><br />
						<a href="http://www.uni-potsdam.de/presse/ansprechpartner.html"
							title="Referat f�r Presse- und �ffentlichkeitsarbeit"
							target="_blank">Presse</a>
					</p>
				</div>
			</div>

			<div class="twelve columns up-oe-footer-box up-oe-footer-box-last">
				<div class="up-oe-footer-box-header">
					<h2>Anfahrt</h2>

					<div class="up-oe-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-oe-footer-box-content">
					<div class="up-oe-footer-map-box">
						<div class="up-oe-footer-map-buttons act-palais act-indicator">
							<div
								class="up-oe-footer-map-button up-oe-footer-map-button-palais cur">
								<p>Campus Am Neuen Palais</p>
							</div>
							<div
								class="up-oe-footer-map-button up-oe-footer-map-button-golm ">
								<p>Campus Golm</p>
							</div>
							<div
								class="up-oe-footer-map-button up-oe-footer-map-button-griebnitzsee last ">
								<p>Campus Griebnitzsee</p>
							</div>
						</div>
						<div class="up-oe-footer-maps act-palais act-indicator">

							<div class="up-oe-footer-map up-oe-footer-map-palais up-map cur">
								<a href="http://www.openstreetmap.org/#map=17/52.4008/13.0151"
									target="_blank"><img
									src="${javascript_folder}/files/up-campus-map-palais.jpg" alt="Campus Palais"></a>
								<a class="up-oe-footer-map-link"
									href="http://www.openstreetmap.org/#map=17/52.4008/13.0151"
									target="_blank"> Gr&ouml;&szlig;ere Karte anzeigen </a>
							</div>

							<div class="up-oe-footer-map up-oe-footer-map-golm up-map ">
								<a href="http://www.openstreetmap.org/#map=17/52.40862/12.97618"
									target="_blank"><img
									src="${javascript_folder}/files/up-campus-map-golm.jpg" alt="Campus Golm"></a>
								<a class="up-oe-footer-map-link"
									href="http://www.openstreetmap.org/#map=17/52.40862/12.97618"
									target="_blank"> Gr&ouml;&szlig;ere Karte anzeigen </a>
							</div>

							<div
								class="up-oe-footer-map up-oe-footer-map-griebnitzsee up-map ">
								<a href="http://www.openstreetmap.org/#map=17/52.39295/13.12888"
									target="_blank"><img
									src="${javascript_folder}/files/up-campus-map-griebnitzsee.jpg"
									alt="Campus Griebnitzsee"></a> <a
									class="up-oe-footer-map-link"
									href="http://www.openstreetmap.org/#map=17/52.39295/13.12888"
									target="_blank"> Gr&ouml;&szlig;ere Karte anzeigen </a>
							</div>

							<div class="up-clear"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="row-f up-footer-closer"></div>
	</div>


	<!-- Global Footer  -->
	<div id="up_global_footer_wrapper">
		<div id="up_global_footer" class="row-f up-smooth-box">
			<div
				class="six columns up-global-footer-box up-global-footer-box-first">
				<div class="up-global-footer-box-header">
					<h2>Zertifikate</h2>
					<div class="up-global-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-global-footer-box-content">
					<p class="bodytext">
						<span style="line-height: 20.296875px;"><a
							href="http://www.hrk.de/themen/internationales/arbeitsfelder/nationaler-kodex-fuer-das-auslaenderstudium/"
							title="Nationaler Kodex f�r das Ausl�nderstudium an deutschen Hochschulen"
							target="_blank">Code of Conduct</a><br />
						<a
							href="http://www.stifterverband.org/wissenschaft_und_hochschule/lehre/exzellenz_in_der_lehre/index.html"
							title="Wettbewerb exzellente Lehre" target="_blank">Exzellenz
								der Lehre</a><br />
						<a href="http://www.hrk.de/audit/"
							title="Audit �Internationalisierung der Hochschulen� (HRK)"
							target="_blank">HRK-Audit</a><br />
						<a href="http://www.uni-potsdam.de/zfq/index.html"
							title="Zentrum f�r Qualit�tsentwicklung in Lehre und Studium"
							target="_blank">Systemakkreditiert</a><br />
						<a href="http://www.total-e-quality.de/"
							title="Homepage Total E-Quality" target="_blank">Total
								E-Quality</a></span><span style="line-height: 20.296875px;"></span>
					</p>
				</div>
			</div>
			<div class="six columns up-global-footer-box">
				<div class="up-global-footer-box-header">
					<h2>Mitgliedschaften</h2>
					<div class="up-global-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-global-footer-box-content">
					<p class="bodytext">
						<span style="line-height: 20.296875px;"> <a
							href="http://www.pearlsofscience.de/ueber-pearls.html"
							title="Homepage pearls � Potsdam Research Network"
							target="_blank">pearls - Potsdam Research Network</a><br /> <a
							href="http://www.mittelgrosse-universitaeten.de/"
							title="Homepage Netzwerk Mittelgro�e Universit�ten"
							target="_blank">Netzwerk Mittelgro&szlig;e Universit&auml;ten</a><br />
						<a href="http://www.blrk.de/"
							title="Homepage Brandenburgische Landesrektorenkonferenz"
							target="_blank">Brandenburgische Landesrektorenkonferenz</a></span>
					</p>
				</div>
			</div>
			<div class="six columns up-global-footer-box">
				<div class="up-global-footer-box-header">
					<h2>Uni kompakt</h2>
					<div class="up-global-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-global-footer-box-content">
					<p class="bodytext">
						<span style="line-height: 20.296875px;"><a
							href="http://www.uni-potsdam.de/studium/termine.html"
							title="Fristen und Termine rund um das Studium" target="_blank">Fristen
								&amp; Termine</a><br />
						<a href="http://www.uni-potsdam.de/uni-a-z/index-a-z.html"
							title="Schlagw�rter�bersicht von A-Z" target="_blank">Index
								A-Z</a><br />
						<a href="http://www.uni-potsdam.de/presse/"
							title="Referat f�r Presse- und �ffentlichkeitsarbeit"
							target="_blank">Presse</a><br />
						<a href="http://www.uni-potsdam.de/verwaltung/dezernat3/stellen/"
							title="�bersicht der offenen Stellenanzeigen" target="_blank">Stellenausschreibungen</a><br />
						<a
							href="http://www.uni-potsdam.de/studium/konkret/vorlesungsverzeichnisse.html"
							title="�bersicht der Vorlesungsverzeichnisse der Uni Potsdam"
							target="_blank">Vorlesungsverzeichnis</a></span><br />
						<a href="http://www.VBB.de/FahrplanBerlinPotsdam"
							title="Unifahrplan des VBB" target="_blank">Unifahrplan des
							VBB</a><br />
						<a href="http://www.uni-potsdam.de/uni-a-z/adressen.html"
							title="Zentrale Adressen und Anschriften der Uni Potsdam"
							target="_blank"><span style="line-height: 20.296875px;">Zentrale
								Adressen</span><span style="line-height: 20.296875px;"></span></a>
					</p>
				</div>
			</div>
			<div
				class="six columns up-global-footer-box up-global-footer-box-last">
				<div class="up-global-footer-box-header">
					<h2>Diese Seite</h2>
					<div class="up-global-footer-box-arrow up-icon"></div>
				</div>
				<div class="up-global-footer-box-content">
					<p>
						<a href="javascript:window.print();" target="_self"
							title="Diese Seite drucken"> Drucken</a><br />
						<a href="index.html#" class="hide-for-small"
							onclick="return add_favorite(this);" target="_blank"
							title="Diese Seite als Bookmark speichern"> Als Bookmark
							speichern</a>
						<script type="text/javascript">
	/* <![CDATA[ */
	function add_favorite(a){title=document.title;url=document.location;try{window.external.AddFavorite(url,title)}catch(e){try{window.sidebar.addPanel(title,url,"")}catch(e){if(typeof opera=="object"){a.rel="sidebar";a.title=title;a.url=url;return true}else alert('Press '+(navigator.userAgent.toLowerCase().indexOf('mac')!=-1?'Command/Cmd':'CTRL')+' + D to bookmark this page.')}};return false};
	/* ]]> */
	</script>
						<a href="rss-feed-abonnieren.html" title="RSS-Feed abonnieren">RSS-Feed
							abonnieren</a>
					</p>
				</div>
			</div>
		</div>
		<div class="row-f show-for-print">
			<div class="twentyfour columns up-footer-url">
				<span>URL:</span> <a href="http://www.uni-potsdam.de/index.html"
					class="url">http://www.uni-potsdam.de/index.html</a>
			</div>
		</div>
		<div class="row-f up-footer-closer"></div>
	</div>
	</#if>
</div>

${theme.include(body_bottom_include)}



	<!-- required js-files for dependencies -->
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/modernizr.custom-573fab3ff3dfd74500f7116dbdb911d2.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery-ui-1.9.2.custom.min.js"></script>

	<!-- additional js-files for footer -->	
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery.foundation.orbit-6d51523cd7444f425eb4e498e938bf94.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery.validate-b36894a2cc15ccb5515ec7a168e9bd33.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery.ui.datepicker.validation.de-56d423c39684e2860682e96254d68980.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/messages_de-5a79c9036918be6d530b44a001563f7d.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/additional-methods_de-240364298e78483fb61185923eed9a3c.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/app-6be339b929c045ff795012ff9b4b639e.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/up-83ead6ee5b861de54161bc2667f256ae.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/up.navigations-79615011a3a452d69f6632ddf8387b43.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/jquery.dlmenu-4e5fe3ebab920f69e7449f2c1eaccf81.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/shadowbox_de-eaa4e8ef27adc478e5d9221f2600b4b6.min.js"></script>
	<script type="text/javascript"
		src="${javascript_folder}/files/javascript_d0d7de8fd9-d0d7de8fd9ef737cf646b76476cd4a08.min.js"></script>
	<script type="text/javascript" src="${javascript_folder}/custom.js"></script>
	$(window).on('resize',clickdropdown);
		

	
	
</body>

${theme.include(bottom_include)}

</html>