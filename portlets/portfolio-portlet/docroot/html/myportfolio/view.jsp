<%@ include file="/html/init.jsp"%>

<%
	String redirect = PortalUtil.getCurrentURL(renderRequest);
	
	Group scopeGroup = GroupLocalServiceUtil.getGroup(scopeGroupId);
	
	boolean personalSite = scopeGroup.isUser();
	boolean publicPage = layout.isPublicLayout();
	boolean privatePersonalPage = personalSite &&  !publicPage;
	if (personalSite) {
		if (!publicPage) {
%>

<aui:button id="createPageButton" name="createPageButton" type="button" value="portfolio-create-page"/>

<aui:script>
AUI().use('aui-base',
	    'liferay-portlet-url',
	    function(A) {
	        A.one('#<portlet:namespace />createPageButton').on('click', function(event) {
	            var renderURL = Liferay.PortletURL.createRenderURL();
	            renderURL.setWindowState("<%=LiferayWindowState.POP_UP.toString() %>");
	            renderURL.setPortletMode("<%=LiferayPortletMode.VIEW %>");
	            renderURL.setParameter("mvcPath", "/html/myportfolio/popup/create_portfolio.jsp");
	            renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
	            renderURL.setParameter("currentRedirect", "<%=redirect%>");
	            openPopUp(renderURL, "<%=LanguageUtil.get(pageContext, "portfolio-add-portfolio")%>");
	        });
	    });
</aui:script>

<% } else if (scopeGroup.getClassPK() == user.getUserId()){%>

	<span id="portletTitle"><%= LanguageUtil.get(pageContext, "portfolio-my-portfolio-pages") %></span>
	
<% } else {%>

	<span id="portletTitle"><%= LanguageUtil.format(pageContext, "portfolio-from-user-for-me-published-pages", new Object[] {UserLocalServiceUtil.getUser(scopeGroup.getClassPK()).getFullName()}) %></span>
	
<% } %>
<aui:input id="filterInput" class="filterInput" name="" placeholder="portfolio-filter-placeholder"/>

<portlet:resourceURL var="getUserPortfoliosURL">
	<portlet:param name="<%=Constants.CMD%>" value="getUserPortfolios" />
	<portlet:param name="privatePersonalPage" value="<%= String.valueOf(privatePersonalPage) %>" />
</portlet:resourceURL>
<portlet:resourceURL var="deletePortfolioURL">
	<portlet:param name="<%=Constants.CMD%>" value="deletePortfolio" />
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
<portlet:resourceURL var="renamePortfolioURL">
	<portlet:param name="<%=Constants.CMD%>" value="renamePortfolio" />
</portlet:resourceURL>

<div style="display: none;" id="<%=themeDisplay.getPortletDisplay().getNamespace()%>noPortfolioPages" class="alert alert-info" ><%=LanguageUtil.get(pageContext,"portfolio-no-portfolios")%></div>
<div style="display: none;" id="myPortfolioTable"></div>

<div id="iconMenuDiv" style="display: none;">
	<liferay-ui:icon-menu extended="false" id="iconMenu">
		<liferay-ui:icon image="delete" url="javascript:void(0);" id="deleteIcon"/>
		<liferay-ui:icon image="configuration" url="javascript:void(0);" message="edit" id="configurationIcon"></liferay-ui:icon>
	</liferay-ui:icon-menu>
</div>
<div id="helpIconDiv" style="display: none;">
	<liferay-ui:icon id="helpIcon" image="help" message="<%=LanguageUtil.get(pageContext,\"portfolio-locked-message\")%>"/>
</div>
<div id="deleteIconDiv" style="display: none;">
	<liferay-ui:icon id="deleteIcon" image="delete" url="javascript:void(0);"/>
</div>

<aui:script>
var iconMenuIdlist = [];
var myPortfolioDataTable;
var myPortfolioData;
var currentPlid;
var tableCreated = false;

var noPortfolioPagesMessageDiv;
var tableDiv;

AUI().use('aui-base', function(A){
noPortfolioPagesMessageDiv = A.one("#<portlet:namespace />noPortfolioPages");
tableDiv = A.one("#myPortfolioTable");
A.io.request('<%=getUserPortfoliosURL.toString()%>', {
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
    	    if (event.which == 13 || event.keyCode == 13) {
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
    	        A.io.request('<%=renamePortfolioURL.toString()%>', {
    	            dataType: 'text/html',
    	            method: 'post',
    	            zindex: 1,
    	            data: { <portlet:namespace/>portfolioPlid: currentPlid, <portlet:namespace/>newTitle: newTitle
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
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-title-column")%>',
    	    key: 'title',
    	    nodeFormatter: function(o) {
    	        var trStartTag = '<tr class="' + (((o.rowIndex % 2) == 0) ? 'table-even' : 'table-odd') + '">'
    	        o.td.setAttribute('rowspan', (o.data.portfolioFeedbacks.length + 1));
    	        o.td.setAttribute('onclick', 'currentPlid=' + o.data.plid + ';');
    	        o.cell.setHTML('<a id="nameTag_' + o.data.plid + '" href="' + o.data.url + '">' + o.data.title + '</a>');
    	        var row = o.td.ancestor();
    	        for (var i in o.data.portfolioFeedbacks) {
    	            var userNameCell = '<td>' + o.data.portfolioFeedbacks[i].userName + ' <span>(' + o.data.portfolioFeedbacks[i].creationDate + ')</span>' + '</td>';
    	            var publishmentDeleteIconCell;
    	            var feedbackCell;
    	            if (o.data.portfolioFeedbacks[i].feedbackStatus === parseInt('<%=PortfolioStatics.FEEDBACK_UNREQUESTED%>')) {
    	                feedbackCell = '<td class="feedback-unrequested"><a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />requestFeedbackFromUser(' + o.data.plid + "," + o.data.portfolioFeedbacks[i].userId + ');">' +
    	                    '<%= LanguageUtil.get(pageContext, "portfolio-request-feedback")%>' +
    	                    '</a></td><td></td>';
    	                var publishmentDeleteIconDiv = A.one("#deleteIconDiv").cloneNode(true);
    	                publishmentDeleteIconDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deletePublishment(' + o.data.plid  + "," + o.data.portfolioFeedbacks[i].userId + ');');
    	                publishmentDeleteIconCell = '<td>' + publishmentDeleteIconDiv.get("innerHTML") + '</td>';
    	            } else {
    	                publishmentDeleteIconCell = '<td></td>';
    	                if (o.data.portfolioFeedbacks[i].feedbackStatus === parseInt('<%=PortfolioStatics.FEEDBACK_REQUESTED%>')) {
    	                    feedbackCell = '<td class="feedback-requested">' + '<%=LanguageUtil.get(pageContext, "portfolio-feedback-requested")%>';
    	                } else {
    	                    feedbackCell = '<td class="feedback-delivered">' + '<%=LanguageUtil.get(pageContext, "portfolio-feedback-received")%>';
    	                }
    	                var feedbackDeleteIconDiv = A.one("#deleteIconDiv").cloneNode(true);
    	                feedbackDeleteIconDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deletePortfolioFeedbackRequest(' + o.data.plid  + "," + o.data.portfolioFeedbacks[i].userId + ');');
    	                feedbackCell += ' <span>(' + o.data.portfolioFeedbacks[i].modifiedDate + ')</span>' +
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
    	        o.td.setAttribute('rowspan', (o.data.portfolioFeedbacks.length + 1));
    	        if (o.data.inFeedbackProcess) {
    	            o.cell.setHTML(A.one('#helpIconDiv').get('innerHTML'));
    	        } else {
    	            var iconMenuDiv = A.one('#iconMenuDiv').cloneNode(true);
    	            var iconMenu = iconMenuDiv.one('#<portlet:namespace />iconMenu');
    	            var iconMenuId = '<portlet:namespace />iconMenu_' + o.data.plid;
    	            iconMenuDiv.one('#<portlet:namespace />deleteIcon').setAttribute('onClick','<portlet:namespace />deletePortfolio(' + o.data.plid + ');');
    	            iconMenuDiv.one('#<portlet:namespace />configurationIcon').setAttribute('href',o.data.url);
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
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-publishment-column")%>',
    	    key: 'publishment',
    	    editable: true,
    	    nodeFormatter: function(o) {
    	        if (o.data.isGlobal) {
    	            o.cell.setHTML('<%=LanguageUtil.get(pageContext, "portfolio-portalwide-publishment") %>');
    	        } else {
    	            o.cell.setHTML(
    	                '<a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />openPublishPortfolioPopup(' + o.data.plid + ');">' +
    	                '<%= LanguageUtil.get(pageContext, "portfolio-setup-publishment")%>' +
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
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-feedback-column")%>',
    	    key: 'feedback',
    	    nodeFormatter: function(o) {
    	        if (o.data.isGlobal) {
    	            o.cell.setHTML(
    	                '<a href="javascript:void(0);" class="popUpLink" onClick="<portlet:namespace />openRequestFeedbackPopup(' + o.data.plid + ');">' +
    	                '<%= LanguageUtil.get(pageContext, "portfolio-request-feedback")%>' +
    	                '</a>');
    	        }
    	        return false;
    	    }
    	}, {
    	    label: ' ',
    	    key: 'feedbackOptions'
    	}, {
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-changes-column")%>',
    	    key: 'changes',
    	    nodeFormatter: function(o) {
    	        o.td.setAttribute('rowspan', (o.data.portfolioFeedbacks.length + 1));
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
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-title-column")%>',
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
    	    label: '<%= LanguageUtil.get(pageContext, "portfolio-changes-column")%>',
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
       		noPortfolioPagesMessageDiv.setStyle('display','none');
       	}
       	else {
       		noPortfolioPagesMessageDiv.setStyle('display','block');
       		tableDiv.setStyle('display','none');
       	}

        myPortfolioDataTable = new A.DataTable({
            columns: ('<%= privatePersonalPage %>' ==  'true') ? privateTableColumns : publicTableColumns,
            data: data,
            editEvent: 'dblclick',
            rowsPerPage: 5,
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "portfolio-show-all")%>'],
            paginatorView : CustomPaginatorView
            
        });
        
        myPortfolioDataTable.render("#myPortfolioTable");
        myPortfolioDataTable.sort('changes');
       	
        myPortfolioData = myPortfolioDataTable.data;
        A.one('#<portlet:namespace />filterInput').on('keyup', function(e) {
            var filteredData = myPortfolioData.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase());
            });
            myPortfolioDataTable.set('data', filteredData);
        });

        tableCreated = true;
    }
);}

Liferay.provide(window, '<portlet:namespace />deletePortfolio',
    function(plid) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deletePortfolioURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />deletePublishment',
    function(plid, userId) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deletePublishment1URL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />deleteGlobalPublishment',
    function(plid) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deleteGlobalPublishment1URL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />requestFeedbackFromUser',
    function(plid, userId) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=requestFeedbackFromUserURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />deletePortfolioFeedbackRequest',
    function(plid, userId) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=deleteFeedbackRequestURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid, <portlet:namespace/>userId: userId
                },
                on: {
                    success: function() {
                        updateTableData(A);
                    }
                }
            });
        });
    });

function updateTableData(A) {
	var currentPage = myPortfolioDataTable.get('paginatorModel').get('page');
    A.io.request('<%=getUserPortfoliosURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                myPortfolioData = JSON.parse(result);
            	if (myPortfolioData.length > 0){
            		if (tableCreated){
                   		tableDiv.setStyle('display','block');
                   		noPortfolioPagesMessageDiv.setStyle('display','none');
                    }
            		else {
                		createTable(result);
            		}
            	}
            	else {
               		tableDiv.setStyle('display','none');
               		noPortfolioPagesMessageDiv.setStyle('display','block');
            	}

                myPortfolioDataTable.set('data', myPortfolioData);
                myPortfolioDataTable.get('paginatorModel').set('page',currentPage);
            }
        }
    });
}

function refreshPortlet() {
	var A = new AUI();
	updateTableData(A);
}

Liferay.provide(window, '<portlet:namespace />openPublishPortfolioPopup',
    function(plid) {
        var renderURL = Liferay.PortletURL.createRenderURL();
        renderURL.setWindowState("<%=LiferayWindowState.EXCLUSIVE.toString() %>");
        renderURL.setParameter("mvcPath", "/html/myportfolio/popup/publish_portfolio.jsp");
        renderURL.setParameter("portfolioPlid", plid);
        renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
        openInvitePopUp(renderURL,"<%=LanguageUtil.get(pageContext, "portfolio-publish-portfolio")%>", true);
    }
);

Liferay.provide(window, '<portlet:namespace />openRequestFeedbackPopup',
    function(plid) {
        var renderURL = Liferay.PortletURL.createRenderURL();
        renderURL.setWindowState("<%=LiferayWindowState.EXCLUSIVE.toString() %>");
        renderURL.setParameter("mvcPath", "/html/myportfolio/popup/request_feedback.jsp");
        renderURL.setParameter("portfolioPlid", plid);
        renderURL.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
        openInvitePopUp(renderURL, "<%=LanguageUtil.get(portletConfig, locale, "portfolio-request-feedback") %>", false);
    }
);



function openPopUp(renderURL, title) {
    Liferay.Util.openWindow({
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
	var dialog = Liferay.Util.Window.getWindow(
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
    
</aui:script>

<% } else { %>
	<liferay-ui:message key="portfolio-portlet-use-only-on-personal-sites"/>
<% } %>

