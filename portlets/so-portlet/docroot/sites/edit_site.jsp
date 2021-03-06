<%--
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This file is part of Liferay Social Office. Liferay Social Office is free
 * software: you can redistribute it and/or modify it under the terms of the GNU
 * Affero General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * Liferay Social Office is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Liferay Social Office. If not, see http://www.gnu.org/licenses/agpl-3.0.html.
 */
--%>

<%@page import="java.util.Comparator"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Locale"%>
<%@page import="sun.util.locale.LocaleUtils"%>
<%@ include file="/sites/init.jsp" %>

<%
PortletURL portletURL = renderResponse.createRenderURL();

portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);

portletURL.setParameter("mvcPath", "/sites/edit_site.jsp");
%>

<portlet:actionURL name="addSite" var="addSiteURL" />

<aui:form action="<%= addSiteURL %>" method="post" name="dialogFm">
	<aui:model-context model="<%= Group.class %>" />

	<div class="hide portlet-msg-success">
		<liferay-ui:message key="your-request-processed-successfully" />
	</div>

	<div class="hide portlet-msg-error">
		<liferay-ui:message key="your-request-failed-to-complete" />
	</div>

	<div class="section-container">
		<div class="section site-information" data-step='<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"1", "2"}, false) %>' data-title='<%= LanguageUtil.get(pageContext, "add-site-information") %>'>
			<aui:fieldset>
				<aui:input name="name" />

				<aui:input name="description" />
			</aui:fieldset>
		</div>

		<%
		LayoutSetPrototype defaultLayoutSetPrototype = null;
		%>

		<div class="hide section site-settings" data-step='<%= LanguageUtil.format(pageContext, "step-x-of-x", new String[] {"2", "2"}, false) %>' data-title='<%= LanguageUtil.get(pageContext, "add-site-settings") %>'>
			<div class="site-options">

				<%
				List<LayoutSetPrototype> layoutSetPrototypes = LayoutSetPrototypeServiceUtil.search(company.getCompanyId(), Boolean.TRUE, null);
				if (layoutSetPrototypes.size() > 0) {
				    Collections.sort(layoutSetPrototypes, new Comparator<LayoutSetPrototype>() {
				        @Override
				        public int compare(final LayoutSetPrototype object1, final LayoutSetPrototype object2) {
				            return object1.getName(Locale.GERMAN).toUpperCase().compareTo(object2.getName(Locale.GERMAN).toUpperCase());
				        }
				       } );
				   }
				%>

				<aui:layout>
					<aui:column columnWidth="<%= 40 %>" first="<%= true %>">
						<aui:select id="layoutSetPrototypeSelect" label="template" name="layoutSetPrototypeId">
		
							<%
							for (LayoutSetPrototype layoutSetPrototype : layoutSetPrototypes) {
								UnicodeProperties settingsProperties = layoutSetPrototype.getSettingsProperties();
		
								String customJspServletContextName = settingsProperties.getProperty("customJspServletContextName", StringPool.BLANK);
		
								if (!customJspServletContextName.equals("so-hook")) {
									continue;
								}
								/*
								String layoutSetPrototypeKey = (String)layoutSetPrototype.getExpandoBridge().getAttribute(SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY);
								
								boolean layoutSetPrototypeSite = layoutSetPrototypeKey.equals(SocialOfficeConstants.LAYOUT_SET_PROTOTYPE_KEY_SITE);
		
								if (layoutSetPrototypeSite) {
									defaultLayoutSetPrototype = layoutSetPrototype;
								}*/
							%>
		
								<aui:option selected="<%= (defaultLayoutSetPrototype == null) %>" value="<%= layoutSetPrototype.getLayoutSetPrototypeId() %>"><%= layoutSetPrototype.getName(user.getLanguageId()) %></aui:option>
		
							<%
								if (defaultLayoutSetPrototype == null){
									defaultLayoutSetPrototype = layoutSetPrototype;
								}
							}
							%>
		
						</aui:select>
					</aui:column>
					
					<aui:column columnWidth="<%= 60 %>">
						<div class="template-details">
							<div class="name">
								<liferay-ui:message key="<%= defaultLayoutSetPrototype.getName(user.getLanguageId()) %>" translateArguments="false" />:
							</div>

							<div class="description">
								<liferay-ui:message key="<%= defaultLayoutSetPrototype.getDescription() %>" translateArguments="false" />
							</div>
						</div>
					</aui:column>
				</aui:layout>

				<div class="template-pages">
				<!-- <div class="template-details">
					
					<h3 class="name"><%= defaultLayoutSetPrototype.getName(locale) %></h3>
	
					<p class="description">
						<%= defaultLayoutSetPrototype.getDescription() %>
					</p>
	 				
					<aui:layout>
						<aui:column columnWidth="<%= 30 %>" first="<%= true %>">-->
							<h2><liferay-ui:message key="included-pages" /></h2>
	
							<aui:input name="deleteLayoutIds" type="hidden" />
	
							<div class="delete-layouts-container">
								<c:if test="<%= defaultLayoutSetPrototype != null %>">
	
									<%
									Group layoutSetPrototypeGroup = defaultLayoutSetPrototype.getGroup();
	
									List<Layout> prototypeLayouts = LayoutLocalServiceUtil.getLayouts(layoutSetPrototypeGroup.getGroupId(), true, 0);
	
									for (Layout prototypeLayout : prototypeLayouts) {
									%>
	
										<div class="page">
										
											<%-- BEGIN HOOK CHANGE --%>
											<%-- Pages Overview and Members cannot be unselected anymore --%>
											<%-- <input checked  data-layoutId="<%= prototypeLayout.getLayoutId() %>" id="layout<%= prototypeLayout.getLayoutId() %>" type="checkbox" /> --%>
											<% if (prototypeLayout.getName(Locale.US).equals("Overview") || prototypeLayout.getName(Locale.US).equals("Members")){ %>
												<input disabled checked data-layoutId="<%= prototypeLayout.getLayoutId() %>" id="layout<%= prototypeLayout.getLayoutId() %>" type="checkbox" />
											<% }else { %>
												<input checked data-layoutId="<%= prototypeLayout.getLayoutId() %>" id="layout<%= prototypeLayout.getLayoutId() %>" type="checkbox" />
											<% } %>
											<%-- END HOOK CHANGE --%>
											
											<label for="layout<%= prototypeLayout.getLayoutId() %>"><%= prototypeLayout.getName(locale) %></label>
										</div>
	
									<%
									}
									%>
	
								</c:if>
							</div>
						<!--</aui:column>
	
						
					</aui:layout>-->
				</div>

				<aui:layout>
					<aui:column columnWidth="<%= 40 %>">
						<aui:select id="typeSelect" label="type" name="type">
							<c:if test="<%= enableOpenSites %>">
								<aui:option label="open-site" value="<%= GroupConstants.TYPE_SITE_OPEN %>" />
							</c:if>
		
							<c:if test="<%= enablePublicRestrictedSites %>">
								<aui:option label="<%= GroupConstants.getTypeLabel(GroupConstants.TYPE_SITE_PUBLIC_RESTRICTED) %>" value="<%= GroupConstants.TYPE_SITE_PUBLIC_RESTRICTED %>" />
							</c:if>
		
							<c:if test="<%= enablePrivateRestrictedSites %>">
								<aui:option label="restricted" value="<%= GroupConstants.TYPE_SITE_PRIVATE_RESTRICTED %>" />
							</c:if>
		
							<c:if test="<%= enablePrivateSites %>">
								<aui:option label="hidden-site" value="<%= GroupConstants.TYPE_SITE_PRIVATE %>" />
							</c:if>
						</aui:select>
					</aui:column>
					<aui:column columnWidth="<%= 60 %>">
						<div class="type-details">
							<div class="permission">
								<liferay-ui:message key="permissions" />
							</div>

							<div class="message">

								<%
								String description = StringPool.BLANK;

								if (enableOpenSites) {
									description = "open-sites-are-listed-pages-are-public-and-users-are-free-to-join-and-collaborate";
								}
								else if (enablePublicRestrictedSites) {
									description = "public-restricted-sites-are-listed-pages-are-public-and-users-must-request-to-join-and-collaborate";
								}
								else if (enablePrivateRestrictedSites) {
									description = "private-restricted-sites-are-listed-pages-are-private-and-users-must-request-to-join-and-collaborate";
								}
								else if (enablePrivateSites) {
									description = "private-sites-are-not-listed-pages-are-private-and-users-must-be-invited-to-collaborate";
								}
								%>

								<liferay-ui:message key="<%= description %>" />
							</div>
						</div>
					</aui:column>
				</aui:layout>
			</div>
		</div>
	</div>

	<aui:button-row cssClass="dialog-footer">
		<div class="buttons-left">
			<aui:button disabled="<%= true %>" id="previous" onClick='<%= renderResponse.getNamespace() + "previous()" %>' value="previous" />

			<aui:button id="next" onClick='<%= renderResponse.getNamespace() + "next()" %>' value="next" />
		</div>

		<div class="step" id="<portlet:namespace />step">
			<span>
				<liferay-ui:message arguments="<%= new Integer[] {1, 2} %>" key="step-x-of-x" translateArguments="<%= false %>" />
			</span>
		</div>

		<div class="buttons-right">
			<%-- BEGIN CHANGE --%>
			<%-- <aui:button id="save" onClick='<%= renderResponse.getNamespace() + "save()" %>' value="save" /> --%>
			<aui:button disabled="true" id="save" onClick='<%= renderResponse.getNamespace() + "save()" %>' value="save" />
			<%-- END CHANGE --%>
			
		</div>
	</aui:button-row>
</aui:form>
<aui:script use="aui-base,aui-io-request-deprecated,aui-loading-mask-deprecated">
	var form = A.one(document.<portlet:namespace />dialogFm);

	var sectionContainer = A.one('.so-portlet-sites-dialog .section-container');

	var previousButton = A.one('.so-portlet-sites-dialog #previous');
	var nextButton = A.one('.so-portlet-sites-dialog #next');
	// BEGIN CHANGE
	var saveButton = A.one('.so-portlet-sites-dialog #save');
	// END CHANGE

	Liferay.provide(
		window,
		'<portlet:namespace />save',
		function() {
			nextButton.set('disabled', true);

			var loadingMask = new A.LoadingMask(
				{
					'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "creating-a-new-site") %>',
					target: A.one('.so-portlet-sites-dialog')
				}
			);

			loadingMask.show();

			var layoutElems = sectionContainer.all('.delete-layouts-container .page input:not(:checked)');

			var deleteLayoutIds = [];

			layoutElems.each(
				function(item, index) {
					deleteLayoutIds.push(item.getAttribute('data-layoutId'));
				}
			);

			var deleteLayoutIdsElem = A.one('#<portlet:namespace />deleteLayoutIds');

			deleteLayoutIdsElem.set('value', deleteLayoutIds.join(','));

			A.io.request(
				form.getAttribute('action'),
				{
					after: {
						success: function(event, id, obj) {
							var data = this.get('responseData');

							if (data.result == 'success') {
								form.one('.portlet-msg-error').hide();

								Liferay.SO.Sites.updateSites(true);

								var callback = function() {
									var dialog = Liferay.SO.Sites.getPopup();

									dialog.hide();

									loadingMask.hide();
								}

								setTimeout(callback, 1000);
								
								// BEGIN HOOK CHANGE
								// open workspace after creation #662
								location.replace(data.workspaceURL);
								// END HOOK CHANGE
							}
							else if (data.result == 'failure') {
								var errorMsg = form.one('.portlet-msg-error');

								if (data.message) {
									errorMsg.html(data.message);
								}

								errorMsg.show();

								var section = A.one('.so-portlet-sites-dialog .section');

								<portlet:namespace />showSection(section);

								loadingMask.hide();
							}
						}
					},
					dataType: 'JSON',
					form: {
						id: form.getDOM()
					}
				}
			);
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />previous',
		function() {
			var section = A.one('.so-portlet-sites-dialog .section:not(.hide)').previous();

			<portlet:namespace />showSection(section);
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />showSection',
		function(section) {
			Liferay.SO.Sites.setTitle(section.getAttribute('data-title'));

			A.one('#<portlet:namespace />step').html('<span>' + section.getAttribute('data-step') + '</span>');

			sectionContainer.all('.section').hide();

			if (!section.previous('.section')) {
				Liferay.SO.Sites.disableButton(previousButton);
			}
			else {
				Liferay.SO.Sites.enableButton(previousButton);
			}

			if (!section.next('.section')) {
				Liferay.SO.Sites.disableButton(nextButton);
				// BEGIN CHANGE
				Liferay.SO.Sites.enableButton(saveButton);
				// END CHANGE
			}
			else {
				Liferay.SO.Sites.enableButton(nextButton);
			}

			previousButton.blur();
			nextButton.blur();

			section.show();
		}
	);

	Liferay.provide(
		window,
		'<portlet:namespace />next',
		function() {
			var section = A.one('.so-portlet-sites-dialog .section:not(.hide)').next();

			<portlet:namespace />showSection(section);
		}
	);

	Liferay.Util.focusFormField(document.<portlet:namespace />dialogFm.<portlet:namespace />name);
</aui:script>
<aui:script use="aui-base,aui-io-deprecated,aui-tooltip">
	// BEGIN CHANGE
	// init tooltip informing the user that at least one page has to be selected
	var tooltip = new A.Tooltip(
			{
				cssClass: 'tooltip-help',
				html: true,
				opacity: 1,
				stickDuration: 300,
				visible: false,
				zIndex: 10000,
				bodyContent: Liferay.Language.get('at-least-on-page-has-to-be-selected'),
				triggerShowEvent: 'click'
			}
		).render();
	
	var pageCheckboxes = A.all('.page input');
	pageCheckboxes.on('click', function(e){
		var checkedArray = pageCheckboxes.get('checked');
		var checkedCount = 0;
		for (var i = 0; i < checkedArray.length; i++){
			if (checkedArray[i])
				checkedCount ++;
		}
		// show tooltip and set current checkbox to checked when no other page is selected
		if (checkedCount == 0){
			e.currentTarget.set('checked',true);
			tooltip.set('trigger', e.currentTarget);
			tooltip.show();
			// set trigger null after to avoid that the tooltip is shown whenever the checkbox is clicked
			tooltip.set('trigger', null);
		}
	});
	// END CHANGE
	
	var form = A.one(document.<portlet:namespace />dialogFm);

	form.on(
		'submit',
		function(event) {
			event.halt();
		}
	);

	var templateSelect = A.one('.so-portlet-sites-dialog #<portlet:namespace />layoutSetPrototypeSelect');

	var descriptionContainer = A.one('.so-portlet-sites-dialog .template-details');
	
	var name = descriptionContainer.one('.name');
	var description = descriptionContainer.one('.description');
	
	var pagesContainer = A.one('.so-portlet-sites-dialog .template-pages');
	var pages = pagesContainer.one('.pages');

	var deleteLayoutsContainer = A.one('.so-portlet-sites-dialog .delete-layouts-container');

	templateSelect.on(
		'change',
		function(event) {
			var templateId = templateSelect.get('value');

			A.io.request(
				'<portlet:resourceURL id="getLayoutSetPrototypeDescription" />',
				{
					after: {
						success: function(event, id, obj) {
							var data = this.get('responseData');

							window.testData = data;

							name.html(data.name);
							description.html(data.description);

							var layouts = data.layouts;

							var inputBuffer = [];

							for (var i in layouts) {
								var layout = layouts[i];
								
								// BEGIN HOOK CHANGE
								// Pages Overview and Members cannot be unselected anymore
								if (layout.name == "Overview" || layout.name == "Members" || layout.name == "�bersicht" || layout.name == "Mitglieder")
									inputBuffer.push(
										'<div class="page">' +
											'<input checked disabled data-layoutId="' + layout.layoutId + '" id="layout' + layout.layoutId + '" type="checkbox" />' +
											'<label for="layout' + layout.layoutId + '">' + layout.name + '</label>' +
										'</div>');
								else
									inputBuffer.push(
										'<div class="page">' +
											'<input checked data-layoutId="' + layout.layoutId + '" id="layout' + layout.layoutId + '" type="checkbox" />' +
											'<label for="layout' + layout.layoutId + '">' + layout.name + '</label>' +
										'</div>');
								// END HOOK CHANGE
							}
							
							if (layouts.length == 0){
								inputBuffer.push('<span>' + '<%= UnicodeLanguageUtil.get(pageContext, "none") %>' + '<span>')
							}

							deleteLayoutsContainer.html(inputBuffer.join(''));
						}
					},
					data: {
						<portlet:namespace />layoutSetPrototypeId: templateId
					},
					dataType: 'JSON'
				}
			);
		}
	);

	var typeSelect = A.one('.so-portlet-sites-dialog #<portlet:namespace />typeSelect');

	typeSelect.on(
		'change',
		function(event) {
			var type = typeSelect.get("value");

			var message = "";

			if (type == <%= GroupConstants.TYPE_SITE_OPEN %>) {
				message = '<%= UnicodeLanguageUtil.get(pageContext, "open-sites-are-listed-pages-are-public-and-users-are-free-to-join-and-collaborate") %>';
			}
			else if (type == <%= GroupConstants.TYPE_SITE_PUBLIC_RESTRICTED %>) {
				message = '<%= UnicodeLanguageUtil.get(pageContext, "public-restricted-sites-are-listed-pages-are-public-and-users-must-request-to-join-and-collaborate") %>';
			}
			else if (type == <%= GroupConstants.TYPE_SITE_PRIVATE_RESTRICTED %>) {
				message = '<%= UnicodeLanguageUtil.get(pageContext, "private-restricted-sites-are-listed-pages-are-private-and-users-must-request-to-join-and-collaborate") %>';
			}
			else if (type == <%= GroupConstants.TYPE_SITE_PRIVATE %>) {
				message = '<%= UnicodeLanguageUtil.get(pageContext, "private-sites-are-not-listed-pages-are-private-and-users-must-be-invited-to-collaborate") %>';
			}

			A.one('.so-portlet-sites-dialog .type-details .message').html(message);
		}
	);
</aui:script>