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
		<div id="<portlet:namespace />workspacegrid">
		
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
		workspacegridlist.append('<li class="workspaceslide" style="background-color:' + group.color + '">' +
	    		'<span hidden="true" class="groupId">' + group.groupId + '</span>' +
	    		'<span hidden="true" class="url">' + group.url + '</span>' +
	    		'<div class="workspaceName">' + group.name + '</div>' +
				((group.activitiesCount.length > 0) ? ('<span class="numberOfActivities">' + group.activitiesCount + '</span><span class="icon"></span>') : '' ) +
	    		'<div class="mouseover-actions"><div class="move-workspaceslide"></div><div class="visit-workspace"><span hidden="true" class="url">' + group.url + '</span></div></div></li>');
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
	getData();
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

function getData(){
AUI().use(
		  'sortable',
		  function(A) {    
			  sortable = new A.Sortable({
		        container: '#<portlet:namespace />workspacegrid',
		        nodes: 'li',
		        opacity: '.5'
		    });
			  var button = 1;
			  sortable.delegate.after("drag:mouseDown", function(e){
				  	button = e.ev.button;
				}, false);
		  
			sortable.delegate.after("drag:mouseup", function(e){
				if (button == 1)
					window.open(sortable.delegate.get('currentNode').one('.url').get('textContent'),"_self");
			}, false);

			sortable.delegate.after("drag:drophit", function(e){
				sortable.delegate.get('currentNode')
				AUI().use('aui-base', 'aui-io-request', function(A) {
		        	var node = sortable.delegate.get('currentNode');
		            A.io.request('<%=saveWorkspaceOrderURL.toString()%>', {
		                dataType: 'text/html',
		                method: 'post',
		                data: { <portlet:namespace/>prev : (node.previous() ? node.previous().one('.groupId').get('textContent') : 'null'), 
		                	<portlet:namespace/>next : (node.next() ? node.next().one('.groupId').get('textContent') : 'null'),
		                	<portlet:namespace/>current: node.one('.groupId').get('textContent')},
		                on: {
		                    success: function() {
		                    }
		                }
		            });
		        });
			}, false);
		  }
		);
}
</aui:script>