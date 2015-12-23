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

<%@ include file="/init.jsp" %>

<script src="<%=request.getContextPath()%>/js/Sortable/Sortable.js">
</script>
<script src="<%=request.getContextPath()%>/js/hyphenator.js">
</script>

<portlet:resourceURL var="getUserWorkspacesURL">
	<portlet:param name="<%=Constants.CMD%>" value="getUserWorkspaces" />
</portlet:resourceURL>
<portlet:resourceURL var="saveWorkspaceOrderURL">
	<portlet:param name="<%=Constants.CMD%>" value="saveWorkspaceOrder" />
</portlet:resourceURL>

<c:choose>
	<c:when test="<%= themeDisplay.isSignedIn() %>">

		<%
		String[] workspaceOrder = portletPreferences.getValues("workspaceOrder", new String[]{});
		%>
		<div class="loading-animation" id="<portlet:namespace />workspacegrid">
		
        <ul id="<portlet:namespace />workspacegridlist">
        </ul>
		</div>
		<div id="<portlet:namespace />showMoreLinkContainer"></div>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
</c:choose>
<aui:script>
var sortable;
var showAll = false;
var data;
AUI().use('aui-base', 'aui-io-request', function(A) {
    A.io.request('<%=getUserWorkspacesURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function(result) {
            	data = JSON.parse(this.get('responseData'));
            	renderWorkspaceGrid();
            }
        }
    });
});


function renderWorkspaceGrid(){
	AUI().use('aui-base', function(A) {
	var workspacegrid = A.one('#<portlet:namespace />workspacegrid');
	var workspacegridlist = A.one('#<portlet:namespace />workspacegridlist');
	var showMoreLinkContainer = A.one('#<portlet:namespace />showMoreLinkContainer');
	workspacegridlist.empty()
	var end;
	var allVisible;
	if (!showAll && data.length > 10){
		end = 10;
		allVisible = false;
	}
	else {
		end = data.length;
		allVisible = true;
	}
	for (var i = 0; i < end; i++) {
		var group = data[i];
		
		var completeName= group.name;
	
		var laenge = completeName.length;
		 if (laenge > 20) {
			var shortstring = completeName.substr(0,20);
	   		var shortName = shortstring.concat(" ...");
		} else {
			var shortName = group.name;
		}
		
		workspacegridlist.append('<li class="workspaceslide" style="background-color:' + group.color + '; border-color:'+ group.color +'">' +
	    		'<span hidden="true" class="groupId">' + group.groupId + '</span>' +
	    		'<span hidden="true" class="url">' + group.url + '</span>' + 
	    		//'<div class="workspaceName">' + group.name + '</div>' +
	    		'<div class="workspaceName">' + shortName + '</div>' +
	    	
	    		'<div class="activities">' +
				((group.activitiesCount.length > 0) ? ('<span class="activity-icon" style="background-color:'+ group.color +'"></span><span class="numberOfActivities" style="color:'+ group.color +'">' + group.activitiesCount + '</span>') : '' ) +
	    		'</div><div class="mouseover-actions"><div class="move-workspaceslide"></div><div class="visit-workspace"><span hidden="true" class="url">' + group.url + '</span></div></div></li>');
	}
	
	var workspacegridlistnew = $('#<portlet:namespace />workspacegridlist').get(0);
	Hyphenator.hyphenate(workspacegridlistnew, 'de');
	
	if (!allVisible){
		showMoreLinkContainer.setHTML('<a id="moreWorkspacesLink" href="javascript:;" onClick="renderMore()">'+ '<%= LanguageUtil.get(pageContext, "show-more")%>' +'</a>');
	}
	else if (showAll){
		showMoreLinkContainer.setHTML('<a id="moreWorkspacesLink" href="javascript:;" onClick="renderLess()">'+ '<%= LanguageUtil.get(pageContext, "show-less")%>' +'</a>');		
	}
	else{
		showMoreLinkContainer.empty(); 
	}
	initSortable();
	workspacegrid.removeClass('loading-animation');
	});
}

function renderMore(){
	showAll = true;
	renderWorkspaceGrid();
}


function renderLess(){
	showAll = false;
	renderWorkspaceGrid();
}

function initSortable() {
    AUI().use(
        'aui-base',
        function(A) {
            var currentNodeYuid;

            A.all('.workspaceslide').on('mousedown', function(a) {
                if (!a.target.hasClass('move-workspaceslide')) {
                    a.preventDefault();
                }
            });
            A.all('.mouseover-actions').on('mousedown', function(a) {
                if (!a.target.hasClass('move-workspaceslide')) {
                    a.preventDefault();
                }
            });
            A.all('.visit-workspace').on('click', function(a) {
                window.open(a.currentTarget.one('.url').get('textContent'), "_self");
                a.target.addClass('loading');
            });
        });

    var elements = document.getElementById('<portlet:namespace />workspacegridlist');
    var sortable = new Sortable(elements, {
        animation: 250
    });	
}
</aui:script>