<%--
/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
--%>

<%@ include file="/html/portlet/document_library/init.jsp" %>

<%
String strutsAction = "/document_library_display";

if (portletResource.equals(PortletKeys.DOCUMENT_LIBRARY)) {
	strutsAction = "/document_library";
}

String tabs2 = ParamUtil.getString(request, "tabs2", "display-settings");

String emailFromName = ParamUtil.getString(request, "preferences--emailFromName--", DLUtil.getEmailFromName(portletPreferences, company.getCompanyId()));
String emailFromAddress = ParamUtil.getString(request, "preferences--emailFromAddress--", DLUtil.getEmailFromAddress(portletPreferences, company.getCompanyId()));

boolean emailFileEntryAddedEnabled = ParamUtil.getBoolean(request, "preferences--emailFileEntryAddedEnabled--", DLUtil.getEmailFileEntryAddedEnabled(portletPreferences));
boolean emailFileEntryUpdatedEnabled = ParamUtil.getBoolean(request, "preferences--emailFileEntryUpdatedEnabled--", DLUtil.getEmailFileEntryUpdatedEnabled(portletPreferences));

String emailParam = StringPool.BLANK;
String defaultEmailSubject = StringPool.BLANK;
String defaultEmailBody = StringPool.BLANK;

if (tabs2.equals("document-added-email")) {
	emailParam = "emailFileEntryAdded";
	defaultEmailSubject = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_ADDED_BODY);
}
else if (tabs2.equals("document-updated-email")) {
	emailParam = "emailFileEntryUpdated";
	defaultEmailSubject = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_SUBJECT);
	defaultEmailBody = ContentUtil.get(PropsValues.DL_EMAIL_FILE_ENTRY_UPDATED_BODY);
}

String currentLanguageId = LanguageUtil.getLanguageId(request);

String emailSubjectParam = emailParam + "Subject_" + currentLanguageId;
String emailBodyParam = emailParam + "Body_" + currentLanguageId;

String emailSubject = PrefsParamUtil.getString(portletPreferences, request, emailSubjectParam, defaultEmailSubject);
String emailBody = PrefsParamUtil.getString(portletPreferences, request, emailBodyParam, defaultEmailBody);
%>

<liferay-portlet:actionURL portletConfiguration="true" var="configurationActionURL" />

<liferay-portlet:renderURL portletConfiguration="true" var="configurationRenderURL">
	<portlet:param name="tabs2" value="<%= tabs2 %>" />
</liferay-portlet:renderURL>

<aui:form action="<%= configurationActionURL %>" method="post" name="fm" onSubmit='<%= "event.preventDefault(); " + renderResponse.getNamespace() + "saveConfiguration();" %>'>
	<aui:input name="<%= Constants.CMD %>" type="hidden" value="<%= Constants.UPDATE %>" />
	<aui:input name="tabs2" type="hidden" value="<%= tabs2 %>" />
	<aui:input name="redirect" type="hidden" value="<%= configurationRenderURL %>" />

	<liferay-ui:error key="displayViewsInvalid" message="display-style-views-cannot-be-empty" />
	<liferay-ui:error key="emailFileEntryAddedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailFileEntryAddedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailFileEntryAddedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFileEntryUpdatedBody" message="please-enter-a-valid-body" />
	<liferay-ui:error key="emailFileEntryUpdatedSignature" message="please-enter-a-valid-signature" />
	<liferay-ui:error key="emailFileEntryUpdatedSubject" message="please-enter-a-valid-subject" />
	<liferay-ui:error key="emailFromAddress" message="please-enter-a-valid-email-address" />
	<liferay-ui:error key="emailFromName" message="please-enter-a-valid-name" />
	<liferay-ui:error key="rootFolderIdInvalid" message="please-enter-a-valid-root-folder" />

	<aui:input name="preferences--rootFolderId--" type="hidden" value="<%= rootFolderId %>" />
	<aui:input name="preferences--displayViews--" type="hidden" />
	<aui:input name="preferences--entryColumns--" type="hidden" />

	<aui:fieldset>
		<aui:field-wrapper label="root-folder">
			<div class="input-append">
				<liferay-ui:input-resource id="rootFolderName" url="<%= rootFolderName %>" />

				<aui:button name="selectFolderButton" value="select" />

				<%
				String taglibRemoveFolder = "Liferay.Util.removeFolderSelection('rootFolderId', 'rootFolderName', '" + renderResponse.getNamespace() + "');";
				%>

				<aui:button disabled="<%= rootFolderId <= 0 %>" name="removeFolderButton" onClick="<%= taglibRemoveFolder %>" value="remove" />
			</div>
		</aui:field-wrapper>

		<aui:input label="show-search" name="preferences--showFoldersSearch--" type="checkbox" value="<%= showFoldersSearch %>" />

		<aui:select label="maximum-entries-to-display" name="preferences--entriesPerPage--">

			<%
			for (int pageDeltaValue : PropsValues.SEARCH_CONTAINER_PAGE_DELTA_VALUES) {
			%>

				<aui:option label="<%= pageDeltaValue %>" selected="<%= entriesPerPage == pageDeltaValue %>" />

			<%
			}
			%>

		</aui:select>

		<!-- 
		<aui:input name="preferences--enableRelatedAssets--" type="checkbox" value="<%= enableRelatedAssets %>" />
		 -->
		 
	</aui:fieldset>

	<liferay-portlet:renderURL portletName="<%= portletResource %>" var="selectFolderURL" windowState="<%= LiferayWindowState.POP_UP.toString() %>">
		<portlet:param name="struts_action" value='<%= strutsAction + "/select_folder" %>' />
		<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
		<portlet:param name="ignoreRootFolder" value="<%= Boolean.TRUE.toString() %>" />
	</liferay-portlet:renderURL>

	<aui:script use="aui-base">
		A.one('#<portlet:namespace />selectFolderButton').on(
			'click',
			function(event) {
				Liferay.Util.selectEntity(
					{
						dialog: {
							constrain: true,
							modal: true,
							width: 600
						},
						id: '_<%= HtmlUtil.escapeJS(portletResource) %>_selectFolder',
						title: '<liferay-ui:message arguments="folder" key="select-x" />',
						uri: '<%= selectFolderURL.toString() %>'
					},
					function(event) {
						var folderData = {
							idString: 'rootFolderId',
							idValue: event.folderid,
							nameString: 'rootFolderName',
							nameValue: event.foldername
						};

						Liferay.Util.selectFolder(folderData, '<portlet:namespace />');
					}
				);
			}
		);
	</aui:script>
	<aui:button-row>
		<aui:button type="submit" />
	</aui:button-row>
</aui:form>

<aui:script>
	function <portlet:namespace />initEditor() {
		return "<%= UnicodeFormatter.toString(emailBody) %>";
	}

	function <portlet:namespace />updateLanguage() {
		document.<portlet:namespace />fm.<portlet:namespace /><%= Constants.CMD %>.value = '';

		submitForm(document.<portlet:namespace />fm);
	}

	Liferay.provide(
		window,
		'<portlet:namespace />saveConfiguration',
		function() {
			<c:choose>
				<c:when test='<%= tabs2.startsWith("document-") %>'>
					document.<portlet:namespace />fm.<portlet:namespace /><%= emailBodyParam %>.value = window.<portlet:namespace />editor.getHTML();
				</c:when>
				<c:when test='<%= tabs2.equals("display-settings") %>'>
					document.<portlet:namespace />fm.<portlet:namespace />displayViews.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentDisplayViews);
					document.<portlet:namespace />fm.<portlet:namespace />entryColumns.value = Liferay.Util.listSelect(document.<portlet:namespace />fm.<portlet:namespace />currentEntryColumns);
				</c:when>
			</c:choose>

			submitForm(document.<portlet:namespace />fm);
		},
		['liferay-util-list-fields']
	);
</aui:script>

<%!
public static final String EDITOR_WYSIWYG_IMPL_KEY = "editor.wysiwyg.portal-web.docroot.html.portlet.document_library.configuration.jsp";
%>