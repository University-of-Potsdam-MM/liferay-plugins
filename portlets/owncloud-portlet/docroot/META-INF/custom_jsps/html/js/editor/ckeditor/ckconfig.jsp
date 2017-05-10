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

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page import="com.liferay.portal.kernel.language.LanguageUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ContentTypes" %>
<%@ page import="com.liferay.portal.kernel.util.HtmlUtil" %>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil" %>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil" %>
<%@ page import="com.liferay.portal.kernel.xuggler.XugglerUtil" %>

<%@ page import="java.util.Locale" %>

<%
String contentsLanguageId = ParamUtil.getString(request, "contentsLanguageId");

Locale contentsLocale = LocaleUtil.fromLanguageId(contentsLanguageId);

contentsLanguageId = LocaleUtil.toLanguageId(contentsLocale);

String cssPath = ParamUtil.getString(request, "cssPath");
String cssClasses = ParamUtil.getString(request, "cssClasses");
boolean inlineEdit = ParamUtil.getBoolean(request, "inlineEdit");

String languageId = ParamUtil.getString(request, "languageId");

Locale locale = LocaleUtil.fromLanguageId(languageId);

languageId = LocaleUtil.toLanguageId(locale);

String name = ParamUtil.getString(request, "name");
boolean resizable = ParamUtil.getBoolean(request, "resizable");

response.setContentType(ContentTypes.TEXT_JAVASCRIPT);
%>

;(function() {
	var ckEditor = CKEDITOR.instances['<%= HtmlUtil.escapeJS(name) %>'];

	if (!CKEDITOR.stylesSet.get('liferayStyles')) {
		CKEDITOR.addStylesSet(
			'liferayStyles',
			[

			// BEGIN HOOK CHANGE 
			// Block Styles
			{name: 'Normal', element: 'p'},
			{name: 'Heading 1', element: 'h1', attributes: {'class':'editor-h1'}},
			{name: 'Heading 2', element: 'h2', attributes: {'class':'editor-h2'}},
			{name: 'Heading 3', element: 'h3', attributes: {'class':'editor-h3'}},
			{name: 'Heading 4', element: 'h4', attributes: {'class':'editor-h4'}},
			{name: 'Heading 5', element: 'h5', attributes: {'class':'editor-h5'}},

			// Special classes

	
			{name: 'Cited Work', element:'cite'},
			{name: 'Computer Code', element:'code'},

			// Custom styles

			{name: 'Yellow box', element: 'div', attributes: {'class': 'portlet-msg-info'}},
			{name: 'Right align', element: 'p', attributes: {'class':'editor-rightalign'}},
			{name: 'Blue text', element: 'span', attributes: {'class':'editor-darkblue'}},
			{name: 'Grey box', element: 'div', attributes: {'class':'editor-greydiv'}},
			{name: 'Large link', element: 'div', attributes: {'class':'editor-arrow'}},
			{name: 'Blue box', element: 'div', attributes: {'class':'editor-lightblue'}},
			
			// END HOOK CHANGE
			
			]
		);
	}

	var config = ckEditor.config;

	config.autoParagraph = false;

	config.autoSaveTimeout = 3000;

	config.bodyClass = 'html-editor <%= HtmlUtil.escapeJS(cssClasses) %>';

	config.closeNoticeTimeout = 8000;

	config.contentsCss = ['<%= HtmlUtil.escapeJS(cssPath) %>/aui.css', '<%= HtmlUtil.escapeJS(cssPath) %>/main.css'];

<%
String contentsLanguageDir = LanguageUtil.get(contentsLocale, "lang.dir");
%>

	config.contentsLangDirection = '<%= HtmlUtil.escapeJS(contentsLanguageDir) %>';

	config.contentsLanguage = '<%= contentsLanguageId.replace("iw_", "he_") %>';

	config.entities = false;

	<%-- BEGIN HOOK CHANGE --%>
	// ajaxsave needs to be removed to avoid error message after the update of the ckeditor to version 4.5
	// added uploadimage to allow the upload of images with drag and drop
	// added mathjax to allow the usage of math equations
	//config.extraPlugins = 'ajaxsave,media,restore,scayt,wsc';
	config.extraPlugins = 'media,restore,scayt,wsc,uploadimage,oembed,mathjax';
	config.mathJaxLib = '//cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.1/MathJax.js?config=TeX-AMS_HTML';
	<%-- END HOOK CHANGE --%>

	config.height = 265;

	config.language = '<%= languageId.replace("iw_", "he_") %>';

	config.resize_enabled = <%= resizable %>;

	<c:if test="<%= resizable %>">
		config.resize_dir = 'vertical';
	</c:if>

	config.stylesCombo_stylesSet = 'liferayStyles';

	config.toolbar_editInPlace = [
		['Styles'],
		['Bold', 'Italic', 'Underline', 'Strike'],
		['Subscript', 'Superscript', 'SpecialChar'],
		['Undo', 'Redo'],
		['SpellChecker', 'Scayt'],
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'], ['Source', 'RemoveFormat'],
	];

	config.toolbar_email = [
		['FontSize', 'TextColor', 'BGColor', '-', 'Bold', 'Italic', 'Underline', 'Strike'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['SpellChecker', 'Scayt'],
		'/',
		['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'SelectAll', 'RemoveFormat'],
		['Source'],
		['Link', 'Unlink'],
		['Image']
	];
	<%-- BEGIN HOOK HANGE --%>
	<%-- modify toolbar for the new plugins--%>
	<%-- config.toolbar_liferay = [
		['Bold', 'Italic', 'Underline', 'Strike'],

		<c:if test="<%= inlineEdit %>">
			['AjaxSave', '-', 'Restore'],
		</c:if>

		['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', ],
		['Styles', 'FontSize', '-', 'TextColor', 'BGColor'],
		'/',
		['NumberedList', 'BulletedList', '-', 'Outdent', 'Indent'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['Image', 'Link', 'Unlink', 'Anchor'],
		['Flash', <c:if test="<%= XugglerUtil.isEnabled() %>"> 'Audio', 'Video',</c:if> 'Table', '-', 'Smiley', 'SpecialChar'],
		['Find', 'Replace', 'SpellChecker', 'Scayt'],
		['SelectAll', 'RemoveFormat'],
		['Subscript', 'Superscript']

		<c:if test="<%= !inlineEdit %>">
			,['Source']
		</c:if>
	];

	config.toolbar_liferayArticle = [
		['Styles', 'FontSize', '-', 'TextColor', 'BGColor'],
		['Bold', 'Italic', 'Underline', 'Strike'],
		['Subscript', 'Superscript'],
		'/',
		['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'SelectAll', 'RemoveFormat'],
		['Find', 'Replace', 'SpellChecker', 'Scayt'],
		['NumberedList','BulletedList','-','Outdent','Indent','Blockquote'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		'/',
		['Source'],
		['Link', 'Unlink', 'Anchor'],
		['Image', 'Flash', <c:if test="<%= XugglerUtil.isEnabled() %>">'Audio', 'Video',</c:if> 'Table', '-', 'Smiley', 'SpecialChar', 'LiferayPageBreak']
	];--%>
	
	config.toolbar_liferay = [
		['Styles', 'Bold', 'Italic', 'Underline', 'Strike'],
		['Subscript', 'Superscript', '-', 'RemoveFormat'],
		'/',

		<c:if test="<%= inlineEdit %>">
			['AjaxSave', '-', 'Restore'],
		</c:if>
		['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', '-', 'NumberedList', 'BulletedList', 'Outdent', 'Indent'],
		'/',
		['Image', 'oembed', 'Link', 'Unlink', '-', 'Table', 'SpecialChar', 'Mathjax']

		<c:if test="<%= !inlineEdit %>">
			,['Source']
		</c:if>
	];

	config.toolbar_liferayArticle = [
		['Styles', 'Bold', 'Italic', 'Underline', 'Strike'],
		['Subscript', 'Superscript', '-', 'RemoveFormat'],
		'/',
		['Undo', 'Redo', '-', 'Cut', 'Copy', 'Paste', 'PasteText', '-', 'NumberedList', 'BulletedList', 'Outdent', 'Indent'],
		'/',
		['Image', 'oembed', 'Link', 'Unlink', '-', 'Table', 'SpecialChar', 'Mathjax']

		<c:if test="<%= !inlineEdit %>">
			,['Source']
		</c:if>
	];
	<%-- END HOOK HANGE --%>
	
	config.toolbar_phone = [
		['Bold', 'Italic', 'Underline'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink']
	];

	config.toolbar_simple = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink', 'Table']
	];

	config.toolbar_tablet = [
		['Bold', 'Italic', 'Underline', 'Strike'],
		['NumberedList', 'BulletedList'],
		['Image', 'Link', 'Unlink'],
		['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'],
		['Styles', 'FontSize']
	];

	ckEditor.on(
		'dialogDefinition',
		function(event) {
			var dialogDefinition = event.data.definition;

			var onShow = dialogDefinition.onShow;

			dialogDefinition.onShow = function() {
				if (typeof onShow === 'function') {
					onShow.apply(this, arguments);
				}

				if (window.top != window.self) {
					var editorElement = this.getParentEditor().container;

					var documentPosition = editorElement.getDocumentPosition();

					var dialogSize = this.getSize();

					var x = documentPosition.x + ((editorElement.getSize('width', true) - dialogSize.width) / 2);
					var y = documentPosition.y + ((editorElement.getSize('height', true) - dialogSize.height) / 2);

					this.move(x, y, false);
				}
			}
		}
	);
})();
