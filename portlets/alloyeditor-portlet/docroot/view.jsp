<%@page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@page import="com.liferay.portal.theme.PortletDisplay"%>
<%@page import="com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil"%>
<%@page import="com.liferay.portlet.journal.model.JournalArticle"%>
<%@page import="com.liferay.portal.kernel.util.Constants"%>
<%
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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
%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<% 
	JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(layout.getGroupId(), portletDisplay.getId(), 1);
%>


<link href="<%=request.getContextPath()%>/alloy-editor/assets/alloy-editor-atlas-min.css" rel="stylesheet">

<script src="<%=request.getContextPath()%>/alloy-editor/alloy-editor-all.js">
</script>

<portlet:resourceURL var="saveContentURL">
	<portlet:param name="<%=Constants.CMD%>" value="saveContent" />
</portlet:resourceURL>



<div id="alloyeditor">
<% if (article != null){ 
	String content = LocalizationUtil.getLocalization(article.getContent(), LocalizationUtil.getDefaultLanguageId(article.getContent()));%>
	<%= content.substring("![CDATA[".length(), content.length() - ("]]".length() + 1))%>
<% } else {%>

<h1 style="text-align: center;">Markiere mich!</h1>

<% } %>
 
<portlet:defineObjects />

<aui:script>
var alloyEditor = AlloyEditor.editable('alloyeditor', {
    extraPlugins: AlloyEditor.Core.ATTRS.extraPlugins.value + ',ae_richcombobridge,font',
    toolbars: {
    	add: {
            buttons: ['image', 'camera', 'hline', 'table'],
            tabIndex: 2
        },
        styles: {
            selections: [{
                name: 'link',
                buttons: ['linkEdit'],
                test: AlloyEditor.SelectionTest.link
            }, {
                name: 'image',
                buttons: ['imageLeft', 'imageCenter', 'imageRight'],
                test: AlloyEditor.SelectionTest.image
            }, {
                name: 'text',
                buttons: ['styles','Font','FontSize', 'bold', 'italic', 'underline', 'quote', 'code',
                          'ul','ol','paragraphLeft', 'paragraphCenter', 'paragraphRight', 'paragraphJustify', 'link'],
                test: AlloyEditor.SelectionTest.text
            }, {
                name: 'table',
                buttons: ['tableRow', 'tableColumn', 'tableCell', 'tableRemove'],
                getArrowBoxClasses: AlloyEditor.SelectionGetArrowBoxClasses.table,
                setPosition: AlloyEditor.SelectionSetPosition.table,
                test: AlloyEditor.SelectionTest.table
        	}]
        }
    }
});

var ckeditorInstance = alloyEditor.get('nativeEditor');
var oldContent = ckeditorInstance.getData();

function updateContent(){
	AUI().use('aui-base', 'aui-io-request', function(A) {
		if (oldContent !== ckeditorInstance.getData()) {
		    A.io.request('<%=saveContentURL.toString()%>', {
		        dataType: 'text/html',
		        method: 'post',
		        data: { <portlet:namespace/>content: ckeditorInstance.getData()
		        }
		    });
		    oldContent = ckeditorInstance.getData();
		}
    });
}

function updateContentWithTimer(){
	updateContent();
	(function(){
	    // do some stuff
	    setTimeout(updateContentWithTimer, 5000);
	})();
}

updateContentWithTimer();

window.onbeforeunload = updateContent;
</aui:script>
