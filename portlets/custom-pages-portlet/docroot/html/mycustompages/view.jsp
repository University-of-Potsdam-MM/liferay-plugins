<%@page import="com.liferay.portal.kernel.util.LocalizationUtil"%>
<%@ include file="/html/init_view.jsp"%>

<%
	String redirect = PortalUtil.getCurrentURL(renderRequest);
	
	Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);
	
	boolean personalSite = scopeGroup.isUser();
	boolean publicPage = layout.isPublicLayout();
	boolean privatePersonalPage = personalSite && !publicPage;
	
	if (personalSite) {
		if (!publicPage) {
%>
			<aui:button id="createPageButton" name="createPageButton" type="button" class="btn-primary" value="custompages-new-page"/>
			
			<%@ include file="/html/table_filter_input.jsp" %>
			
			<aui:script>
			AUI().use('aui-base',
				    'liferay-portlet-url',
				    function(A) {
				        A.one('#<portlet:namespace />createPageButton').on('click', function(event) {
				            var renderURL = Liferay.PortletURL.createRenderURL();
				            renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
				            renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
				            renderURL.setParameter("mvcPath", "/html/mycustompages/popup/create_custompage.jsp");
				            renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
				            renderURL.setParameter("currentRedirect", "<%=redirect%>");
				            openPopUp(renderURL, "<%=LanguageUtil.get(pageContext, "custompages-create-page")%>");
				        });
				    });
			</aui:script>

<% 
		} else if (user.getUserId() == layout.getUserId()){
			if (layout.getName(themeDisplay.getLocale()).equals(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
					"custompages-portfolio-pages"))){%>
				<span class="title"><%= LanguageUtil.get(pageContext, "custompages-portfolio-pages-shared-with-other-users") %></span>
<%
			} else {%>
				<span class="title"><%= LanguageUtil.get(pageContext, "custompages-normal-pages-shared-with-other-users") %></span>
<%			} %>

		<%@ include file="/html/table_filter_input.jsp" %>

<%		} else {
			if (layout.getName(themeDisplay.getLocale()).equals(LanguageUtil.get(portletConfig, themeDisplay.getLocale(),
					"custompages-portfolio-pages"))){%>
				<span class="title"><%= LanguageUtil.format(pageContext, "custompages-portfolio-pages-shared-by-x-with-you", UserLocalServiceUtil.getUser(layout.getUserId()).getFullName()) %></span>
<%		
			} else {%>
				<span class="title"><%= LanguageUtil.format(pageContext, "custompages-normal-pages-shared-by-x-with-you", UserLocalServiceUtil.getUser(layout.getUserId()).getFullName()) %></span>	
<%			} %>
		
		<%@ include file="/html/table_filter_input.jsp" %>
<%		} %>

<portlet:resourceURL var="getUserCustomPagesURL">
	<portlet:param name="<%=Constants.CMD%>" value="getUserCustomPages" />
	<portlet:param name="privatePersonalPage" value="<%= String.valueOf(privatePersonalPage) %>" />
</portlet:resourceURL>
<portlet:resourceURL var="deleteCustomPageURL">
	<portlet:param name="<%=Constants.CMD%>" value="deleteCustomPage" />
</portlet:resourceURL>
<portlet:resourceURL var="deletePublishment1URL">
	<portlet:param name="<%=Constants.CMD%>" value="deletePublishment" />
</portlet:resourceURL>
<portlet:resourceURL var="deleteGlobalPublishment1URL">
	<portlet:param name="<%=Constants.CMD%>" value="deleteGlobalPublishment"/>
</portlet:resourceURL>
<portlet:resourceURL var="requestFeedbackFromUserURL">
	<portlet:param name="<%=Constants.CMD%>" value="requestFeedbackFromUser"/>
</portlet:resourceURL>
<portlet:resourceURL var="deleteFeedbackRequestURL">
	<portlet:param name="<%=Constants.CMD%>" value="deleteFeedbackRequest" />
</portlet:resourceURL>
<portlet:resourceURL var="renameCustomPageURL">
	<portlet:param name="<%=Constants.CMD%>" value="renameCustomPage" />
</portlet:resourceURL>
<portlet:resourceURL var="changeCustomPageTypeURL">
	<portlet:param name="<%=Constants.CMD%>" value="changeCustomPageType" />
</portlet:resourceURL>

<div style="display: none;" id="<%=themeDisplay.getPortletDisplay().getNamespace()%>noCustomPagePages" class="alert alert-info" ><%=LanguageUtil.get(pageContext,"custompages-no-custom-pages")%></div>
<div id="customPageTable" class="loading-animation"></div>

<div id="iconMenuDiv" style="display: none;">
	<liferay-ui:icon-menu extended="false" id="iconMenu">
		
		<liferay-ui:icon image="configuration" url="javascript:void(0);" message="edit" id="configurationIcon"></liferay-ui:icon>
		<liferay-ui:icon image="category" url="javascript:void(0);" message="edit" id="categoryIcon"></liferay-ui:icon>
		<liferay-ui:icon image="delete" url="javascript:void(0);" id="deleteIcon"/>
	</liferay-ui:icon-menu>
</div>
<div id="helpIconDiv" style="display: none;">
	<liferay-ui:icon id="helpIcon" image="help" message="<%=LanguageUtil.get(pageContext,\"custompages-locked-message\")%>"/>
</div>
<div id="deleteIconDiv" style="display: none;">
	<liferay-ui:icon id="deleteIcon" image="delete" url="javascript:void(0);"/>
</div>

<aui:script>
var messageOnDialogClose = '';
var dialog;
var iconMenuIdlist = [];
var myCustomPageDataTable;
var myCustomPageData;
var currentPlid;
var tableCreated = false;

var noCustomPagePagesMessageDiv;
var tableDiv;

AUI().use('aui-base','aui-io-request', function(A){
noCustomPagePagesMessageDiv = A.one("#<portlet:namespace />noCustomPagePages");
tableDiv = A.one("#customPageTable");
A.io.request('<%=getUserCustomPagesURL.toString()%>', {
    dataType: 'text/html',
    method: 'post',
    on: {
        success: function() {
        	createTable(this.get('responseData'));
        }
    }
});});


function createTable(tableData) {
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'liferay-menu',
    function(A) {    	
    	var nameEditor = new A.TextAreaCellEditor();
    	nameEditor.on('keydown', function(e) {
    	    if (e.domEvent.which == 13 || e.domEvent.keyCode == 13) {
    	        e.preventDefault();
    	        nameEditor.fire('save', {
    	            newVal: nameEditor.getValue(),
    	            prevVal: nameEditor.get('value')
    	        });
    	    }
    	});
    	nameEditor.on('save', function(e) {
    	    var newTitle = e.newVal.trim();
    	    if (newTitle.length !== 0) {
    	        A.io.request('<%=renameCustomPageURL.toString()%>', {
    	            dataType: 'text/html',
    	            method: 'post',
    	            zindex: 1,
    	            data: { <portlet:namespace/>customPagePlid: currentPlid, <portlet:namespace/>newTitle: newTitle
    	            },
    	            on: {
    	                success: function() {
    	                    updateTableData(A);
    	                }
    	            }
    	        });
    	    }
    	    updateTableData(A);
    	});

    	var privateTableColumns = [{
    	    editor: nameEditor,
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-title-column")%>',
    	    key: 'title',
    	    nodeFormatter: function(o) {
    	    	var customPageType = ((o.data.customPageType === parseInt("<%=CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE%>")) ? 'portfolio-page' : 'custom-page');
    	        var trStartTag = '<tr class="' + (((o.rowIndex % 2) == 0) ? 'table-even' : 'table-odd') + ' ' + customPageType + '">';
    	       	o.cell.ancestor().addClass(customPageType);
    	        o.td.setAttribute('rowspan', (o.data.customPageFeedbacks.length + 1));
    	        o.td.setAttribute('onclick', 'currentPlid=' + o.data.plid + ';');
    	        o.cell.setHTML('<a id="nameTag_' + o.data.plid + '" target="_blank" href="' + o.data.url + '">' + o.data.title + '</a>');
    	        var row = o.td.ancestor();
    	        for (var i in o.data.customPageFeedbacks) {
    	            var userNameCell = '<td>' + o.data.customPageFeedbacks[i].userName + ' <span>(' + o.data.customPageFeedbacks[i].creationDate + ')</span>' + '</td>';
    	            var publishmentDeleteIconCell;
    	            var feedbackCell;
    	            if (o.data.customPageFeedbacks[i].feedbackStatus === parseInt('<%=CustomPageStatics.FEEDBACK_UNREQUESTED%>')) {
    	                feedbackCell = '<td class="feedback-unrequested"><a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />requestFeedbackFromUser(' + o.data.plid + "," + o.data.customPageFeedbacks[i].userId + ');">' +
    	                    '<%= LanguageUtil.get(pageContext, "custompages-request-feedback")%>' +
    	                    '</a></td><td></td>';
    	                var publishmentDeleteIconDiv = A.one("#deleteIconDiv").cloneNode(true);
    	                publishmentDeleteIconDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deletePublishment(' + o.data.plid  + "," + o.data.customPageFeedbacks[i].userId + ');');
    	                publishmentDeleteIconCell = '<td>' + publishmentDeleteIconDiv.get("innerHTML") + '</td>';
    	            } else {
    	                publishmentDeleteIconCell = '<td></td>';
    	                if (o.data.customPageFeedbacks[i].feedbackStatus === parseInt('<%=CustomPageStatics.FEEDBACK_REQUESTED%>')) {
    	                    feedbackCell = '<td class="feedback-requested">' + '<%=LanguageUtil.get(pageContext, "custompages-feedback-requested")%>';
    	                } else {
    	                    feedbackCell = '<td class="feedback-delivered">' + '<%=LanguageUtil.get(pageContext, "custompages-feedback-received")%>';
    	                }
    	                var feedbackDeleteIconDiv = A.one("#deleteIconDiv").cloneNode(true);
    	                feedbackDeleteIconDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deleteCustomPageFeedbackRequest(' + o.data.plid  + "," + o.data.customPageFeedbacks[i].userId + ');');
    	                feedbackCell += ' <span>(' + o.data.customPageFeedbacks[i].modifiedDate + ')</span>' +
    	                    '</td><td>' + feedbackDeleteIconDiv.get('innerHTML') + '</td>'
    	            }
    	            row.insert(trStartTag + userNameCell + publishmentDeleteIconCell + feedbackCell + '</tr>', 'after');
    	        }

    	        return false;
    	    }
    	}, {
    	    label: ' ',
    	    key: 'titleOptions',
    	    nodeFormatter: function(o) {
    	        o.td.setAttribute('rowspan', (o.data.customPageFeedbacks.length + 1));
    	        if (o.data.inFeedbackProcess) {
    	            o.cell.setHTML(A.one('#helpIconDiv').get('innerHTML'));
    	        } else {
    	            var iconMenuDiv = A.one('#iconMenuDiv').cloneNode(true);
    	            var iconMenu = iconMenuDiv.one('#<portlet:namespace />iconMenu');
    	            var iconMenuId = '<portlet:namespace />iconMenu_' + o.data.plid;
    	            iconMenuDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deleteCustomPage(' + o.data.plid + ');');
    	            iconMenuDiv.one('#<portlet:namespace />configurationIcon').setAttribute('href',o.data.url);
    	            var categoryIcon = iconMenuDiv.one('#<portlet:namespace />categoryIcon');
    	            if (o.data.customPageType === <%= CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE%>){
    	            	categoryIcon.one('span').set('innerHTML','<%= LanguageUtil.get(pageContext, "custompages-change-to-portfolio-page") %>');
    	            	categoryIcon.setAttribute('onClick','<portlet:namespace />changeCustomPageType(' + o.data.plid + ',' + '<%= CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE %>' + ');');
    	            } else {
    	            	categoryIcon.one('span').set('innerHTML','<%= LanguageUtil.get(pageContext, "custompages-change-to-normal-page") %>');
    	            	categoryIcon.setAttribute('onClick','<portlet:namespace />changeCustomPageType(' + o.data.plid + ',' + '<%= CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE %>' + ');');
    	            }
    	            iconMenu.setAttribute('id',iconMenuId);
    	            if (!(iconMenuId in iconMenuIdlist)) {
    	                iconMenuIdlist.push(iconMenuId);
    	            }
    	            o.cell.setHTML(iconMenuDiv.get("innerHTML"));
    	            Liferay.Menu.register(iconMenuId);
    	        }

    	        return false;
    	    },
    	    sortable: true,
    	    sortFn: function(a, b, desc) {
    	        var compare = a.get('title').localeCompare(b.get('title'));
    	        return desc ? compare : -compare
    	    }
    	}, {
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-publishment-column")%>',
    	    key: 'publishment',
    	    editable: true,
    	    nodeFormatter: function(o) {
    	        if (o.data.isGlobal) {
    	            o.cell.setHTML('<%=LanguageUtil.get(pageContext, "custompages-portalwide-publishment") %>');
    	        } else {
    	            o.cell.setHTML(
    	                '<a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />openPublishCustomPagePopup(' + o.data.plid + ',' + o.data.isPrivate +');">' +
    	                '<%= LanguageUtil.get(pageContext, "custompages-setup-publishment")%>' +
    	                '</a>');
    	        }
    	        return false;
    	    }
    	}, {
    	    label: ' ',
    	    key: 'publishmentOptions',
    	    nodeFormatter: function(o) {
    	        if (o.data.isGlobal) {
    	            var globalPublishmentDeleteDiv = A.one("#deleteIconDiv").cloneNode(true);
    	            globalPublishmentDeleteDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deleteGlobalPublishment(' + o.data.plid  + ');');
    	            o.cell.setHTML(globalPublishmentDeleteDiv.get('innerHTML'));
    	        }
    	    }
    	}, {
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-feedback-column")%>',
    	    key: 'feedback',
    	    nodeFormatter: function(o) {
   	            o.cell.setHTML(
   	                '<a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />openRequestFeedbackPopup(' + o.data.plid + ',' + o.data.isPrivate + ');">' +
   	                '<%= LanguageUtil.get(pageContext, "custompages-request-feedback")%>' +
   	                '</a>');
    	        return false;
    	    }
    	}, {
    	    label: ' ',
    	    key: 'feedbackOptions'
    	}, {
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-changes-column")%>',
    	    key: 'changes',
    	    nodeFormatter: function(o) {
    	        o.td.setAttribute('rowspan', (o.data.customPageFeedbacks.length + 1));
    	        o.cell.setHTML(o.data.lastChanges);
    	        return false;
    	    },
    	    sortable: true,
    	    sortFn: function(a, b, desc) {
    	        var compare = parseInt(a.get("lastChangesInMilliseconds")) - parseInt(b.get("lastChangesInMilliseconds"));
    	        return desc ? compare : -compare
    	    }
    	}, ]
    	
    	var publicTableColumns = [{
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-title-column")%>',
    	    key: 'title',
    	    formatter: function(o) {
    	    	return '<a href="' + o.data.url + '">' + o.data.title + '</a><br/>' + o.data.templateName;
    	    },
    	    allowHTML: true,
    	    sortable: true,
    	    sortFn: function(a, b, desc) {
    	        var compare = a.get('title').localeCompare(b.get('title'));
    	        return desc ? compare : -compare
    	    }
    	},  {
    	    label: '<%= LanguageUtil.get(pageContext, "custompages-changes-column")%>',
    	    key: 'changes',
    	    formatter: function(o) {
    	        return o.data.lastChanges;
    	    },
    	    sortable: true,
    	    sortFn: function(a, b, desc) {
    	        var compare = parseInt(a.get("lastChangesInMilliseconds")) - parseInt(b.get("lastChangesInMilliseconds"));
    	        return desc ? compare : -compare
    	    }
    	}, ];
        
        var data = JSON.parse(tableData);

       	if (data.length > 0){
       		tableDiv.setStyle('display','block');
       		noCustomPagePagesMessageDiv.setStyle('display','none');
       	}
       	else {
       		noCustomPagePagesMessageDiv.setStyle('display','block');
       		tableDiv.setStyle('display','none');
       	}

        myCustomPageDataTable = new A.DataTable({
            columns: ('<%= privatePersonalPage %>' ==  'true') ? privateTableColumns : publicTableColumns,
            data: data,
            editEvent: 'dblclick',
            rowsPerPage: 5,
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "custompages-show-all")%>'],
            paginatorView : CustomPaginatorView
            
        });
        
        myCustomPageDataTable.render("#customPageTable");
        myCustomPageDataTable.sort('changes');
        
        <portlet:namespace />initFilter(myCustomPageDataTable,true);
        <portlet:namespace />filterTable();
       	
        myCustomPageData = myCustomPageDataTable.data;

        tableCreated = true;
        tableDiv.removeClass("loading-animation");
        
    }
);}

Liferay.provide(window, '<portlet:namespace />deleteCustomPage',
    function(plid) {
	if (confirm('<%=LanguageUtil.get(pageContext,"custompages-confirm-deletion-page")%>')){
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deleteCustomPageURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>customPagePlid: plid
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
	}
    });
    
Liferay.provide(window, '<portlet:namespace />changeCustomPageType',
	    function(plid,type) {
	        AUI().use('aui-base', 'aui-io-request', function(A) {
	            A.io.request('<%=changeCustomPageTypeURL.toString()%>', {
	                dataType: 'text/html',
	                method: 'post',
	                data: { <portlet:namespace/>customPagePlid: plid,
	                	<portlet:namespace/>customPageType: type
	                },
	                on: {
	                    success: function() {
	                        updateTableData(A);
	                        if (type === parseInt('<%=CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE %>')){
	                        	<portlet:namespace />fadeInAlert('<%= LanguageUtil.get(pageContext, "custompages-page-type-changed-to-normal-page") %>');
	                        }
	                        else {
	                        	<portlet:namespace />fadeInAlert('<%= LanguageUtil.get(pageContext, "custompages-page-added-to-portfolio") %>');
	                        }
	                    }
	                }
	            });
	        });
	    });

Liferay.provide(window, '<portlet:namespace />deletePublishment',
    function(plid, userId) {
	if (confirm('<%=LanguageUtil.get(pageContext, "custompages-confirm-deletion-publishment")%>')){
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deletePublishment1URL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>customPagePlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                        /* removed due to #622
                        var movedToPrivateArea = JSON.parse(this.get('responseData')).movedToPrivateArea;
                        if (movedToPrivateArea)
                        	<portlet:namespace />fadeInAlert('<%= LanguageUtil.get(pageContext, "custompages-page-moved-to-private-area") %>'); */
                    }
                }
            });
        });
	}
    });
    
Liferay.provide(window, '<portlet:namespace />deleteGlobalPublishment',
	    function(plid) {
		if (confirm('<%=LanguageUtil.get(pageContext, "custompages-confirm-deletion-global-publishment")%>')){
	        AUI().use('aui-base', 'aui-io-request', function(A) {
	            A.io.request('<%=deleteGlobalPublishment1URL.toString()%>', {
	                dataType: 'text/html',
	                method: 'post',
	                data: { <portlet:namespace/>customPagePlid: plid
	                },
	                on: {
	                    success: function() {
	                        updateTableData(A);
	                        /* removed due to #622
	                        var movedToPrivateArea = JSON.parse(this.get('responseData')).movedToPrivateArea;
	                        if (movedToPrivateArea)
	                        	<portlet:namespace />fadeInAlert('<%= LanguageUtil.get(pageContext, "custompages-page-moved-to-private-area") %>'); */
	                    }
	                }
	            });
	        });
		}
	    });

Liferay.provide(window, '<portlet:namespace />requestFeedbackFromUser',
    function(plid, userId) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=requestFeedbackFromUserURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>customPagePlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />deleteCustomPageFeedbackRequest',
    function(plid, userId) {
	if (confirm('<%=LanguageUtil.get(pageContext, "custompages-confirm-deletion-feedback-request")%>')){
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deleteFeedbackRequestURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>customPagePlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
	}
    });

function updateTableData(A) {
	var currentPage = myCustomPageDataTable.get('paginatorModel').get('page');
    A.io.request('<%=getUserCustomPagesURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                myCustomPageData = JSON.parse(result);
            	if (myCustomPageData.length > 0){
            		if (tableCreated){
                   		tableDiv.setStyle('display','block');
                   		noCustomPagePagesMessageDiv.setStyle('display','none');
                    }
            		else {
                		createTable(result);
            		}
            	}
            	else {
               		tableDiv.setStyle('display','none');
               		noCustomPagePagesMessageDiv.setStyle('display','block');
            	}

                myCustomPageDataTable.set('data', myCustomPageData);
                myCustomPageDataTable.get('paginatorModel').set('page',currentPage);
                <portlet:namespace />updateFilter(myCustomPageDataTable.data);
                <portlet:namespace />filterTable();
            }
        }
    });
}

function refreshPortlet() {
	var A = new AUI();
	updateTableData(A);
}

Liferay.provide(window, '<portlet:namespace />openPublishCustomPagePopup',
    function(plid,isPrivate) {
        var renderURL = Liferay.PortletURL.createRenderURL();
        renderURL.setWindowState("<%=LiferayWindowState.EXCLUSIVE.toString() %>");
        renderURL.setParameter("mvcPath", "/html/mycustompages/popup/publish_custompage.jsp");
        renderURL.setParameter("customPagePlid", plid);
        renderURL.setParameter("isPrivate", isPrivate);
        renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
        openInvitePopUp(renderURL,"<%=LanguageUtil.get(pageContext, "custompages-publish-custom-page")%>", true);
    }
);

Liferay.provide(window, '<portlet:namespace />openRequestFeedbackPopup',
    function(plid,isPrivate) {
        var renderURL = Liferay.PortletURL.createRenderURL();
        renderURL.setWindowState("<%=LiferayWindowState.EXCLUSIVE.toString() %>");
        renderURL.setParameter("mvcPath", "/html/mycustompages/popup/request_feedback.jsp");
        renderURL.setParameter("customPagePlid", plid);
        renderURL.setParameter("isPrivate", isPrivate);
        renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
        openInvitePopUp(renderURL, "<%=LanguageUtil.get(portletConfig, locale, "custompages-request-feedback") %>", false);
    }
);



function openPopUp(renderURL, title) {
    dialog = Liferay.Util.openWindow({
        dialog: {
            after: {
                destroy: function(event) {
                	refreshPortlet();
                }
            },
            centered: true,
            constrain: true,
            destroyOnHide: true,
            height: 600,
            modal: true,
            plugins: [Liferay.WidgetZIndex],
            width: 600
        },
        id: '<portlet:namespace />Dialog',
        title: title,
        uri: renderURL
    });
}

function openInvitePopUp(renderURL, title, forPublishment){
    AUI().use('aui-base','aui-io-plugin-deprecated','liferay-so-invite-members','liferay-util-window', function(A) {
	dialog = Liferay.Util.Window.getWindow(
			{
				dialog: {
					align: {
						node: null,
						points: ['tc', 'tc']
					},
					cssClass: 'invite-members',
					destroyOnClose: true,
					closeOnHide:true,
					modal: true,
					resizable: false,
					width: 900
				},
				title: title
			}
		).plug(
			A.Plugin.IO,
			{
				after: {
					success: function() {
						new Liferay.SO.InviteMembers(
							{
								dialog: dialog,
								portletNamespace: '<portlet:namespace />',
								forPublishment: forPublishment
							}
						);
					}
				},
				method: 'GET',
				uri: renderURL.toString()
			}
		).render();
	});
}

AUI().use('aui-base',
    'liferay-menu',
    function(A) {
        for (var menu in iconMenuIdlist) {
            Liferay.Menu.register(iconMenuIdlist[menu]);
        }
    });
    
function setMessageOnDialogClose(message){
	messageOnDialogClose = message;
}

function closeDialog(){
	dialog.hide();
	refreshPortlet();
	if (messageOnDialogClose != ''){
		<portlet:namespace />fadeInAlert(messageOnDialogClose);
		messageOnDialogClose = '';
	}
}
</aui:script>

<% } else { %>
	<liferay-ui:message key="custompages-portlet-use-only-on-personal-sites"/>
<% } %>

