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
AUI().use('aui-base', 'aui-io-request-deprecated', function(A) {
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
	if (!showAll && data.length > parseInt("<%= visibleWorkspaces%>")){
		end = parseInt("<%= visibleWorkspaces%>");
		allVisible = false;
	}
	else {
		end = data.length;
		allVisible = true;
	}
	if (data.length == 0){
		workspacegridlist.append('<div class="alert alert-info"><%= LanguageUtil.get(pageContext, "empty-workspace-list")%></div>')
	}
	else {
		for (var i = 0; i < end; i++) {
			var group = data[i];
			
			var completeName= group.name;
		
			var laenge = completeName.length;
			 if (laenge > 35) {
				var shortstring = completeName.substr(0,35);
		   		var shortName = shortstring.concat(" ...");
			} else {
				var shortName = group.name;
			}
			
			var additionalClasses = '';
			var backgroundColor = group.color;
			if (group.globalWorkspace == 'true'){
				additionalClasses += 'globalWorkspace';
				backgroundColor = '#014260'; 
			}
				
			workspacegridlist.append('<li class="workspaceslide move-workspaceslide mouseover-actions visit-workspace ' + additionalClasses + '" style="background-color:' + backgroundColor + '; border-color:'+ backgroundColor +'">' +
		    		'<span hidden="true" class="groupId">' + group.groupId + '</span>' +
		    		'<span hidden="true" class="url">' + group.url + '</span>' + 
		    		'<div class="workspaceName move-workspaceslide" id="name_' + group.groupId + '">' + shortName + '</div>' +
		    		'<div class="activities">' +
					((group.activitiesCount.length > 0) ? ('<span class="activity-icon" style="background-color:'+ backgroundColor +'"></span><span class="numberOfActivities" style="color:'+ backgroundColor +'">' + group.activitiesCount + '</span>') : '' ) +
		    		'</div></li>');
	
			Hyphenator.hyphenate($('#name_' + group.groupId).get(0), 'de');
		}
	}
	
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
                a.target.ancestor('.workspaceslide').addClass('loading-animation');
                window.open(a.currentTarget.one('.url').get('textContent'), "_self");
            });
        });

    var elements = document.getElementById('<portlet:namespace />workspacegridlist');
    var sortable = new Sortable(elements, {
        animation: 250,
        onEnd: function (/**Event*/evt) {
            console.log(evt);
            AUI().use('aui-base', 'aui-io-request-deprecated', function(A) {
	            A.io.request('<%=saveWorkspaceOrderURL.toString()%>', {
	                dataType: 'text/html',
	                method: 'post',
	                data: { <portlet:namespace/>newIndex : evt.newIndex, 
	                	<portlet:namespace/>oldIndex : evt.oldIndex},
	                on: {
	                    success: function() {
	                    }
	                }
	            });
	        });
        }
    });	
}
</aui:script>