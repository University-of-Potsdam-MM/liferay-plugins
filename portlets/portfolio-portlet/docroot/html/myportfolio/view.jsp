<%@ include file="/html/init.jsp"%>

<%
	String redirect = PortalUtil.getCurrentURL(renderRequest);
	List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByLayoutUserId(themeDisplay.getUserId());
%>

<aui:button id="createPageButton" name="createPageButton" type="button" value="portfolio-create-page"/>

<aui:input id="filterInput" class="filterInput" name="" placeholder="portfolio-filter-placeholder"/>

<portlet:resourceURL var="getUserPortfoliosURL">
	<portlet:param name="<%=Constants.CMD%>" value="getUserPortfolios" />
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

<div id="dtable"></div>

<% if (portfolios.size() == 0){ %>
	<div class="alert alert-info" ><%=LanguageUtil.get(pageContext,"portfolio-no-portfolios")%></div>
<% } else { %>
	<div id="myPortfolioTable"></div>
<% } %>

<%
	JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
 	for (Portfolio portfolio : portfolios){
 		String div = "iconMenuDiv_" + portfolio.getPlid();
 		String menu = "iconMenu_" + portfolio.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "deletePortfolio(" + portfolio.getPlid() + ");";
 		String url = JspHelper.getPortfolioURL(themeDisplay, portfolio.getLayout(), themeDisplay.getUser());
		%>

		<div id="<%=div%>" hidden=true>
			<liferay-ui:icon-menu extended="false" id="<%=menu%>">
				<liferay-ui:icon image="delete" onClick="<%=onClickMethod%>" url="javascript:void(0);"/>
				<liferay-ui:icon image="configuration" url="<%=url%>" message="edit"></liferay-ui:icon>
			</liferay-ui:icon-menu>
		</div>

		<%
			div = "helpIconDiv_" + portfolio.getPlid();
		%>
		
		<div id="<%=div%>" hidden=true>
			<liferay-ui:icon-help message="<%=LanguageUtil.get(pageContext,\"portfolio-locked-message\")%>"/>
		</div>

		<%
			if (portfolio.getPublishmentType() == PortfolioStatics.PUBLISHMENT_GLOBAL){
				div = "globalPublishmentDeleteDiv_" + portfolio.getPlid();
				onClickMethod = renderResponse.getNamespace() + "deleteGlobalPublishment(" + portfolio.getPlid() + ");";
			%>
		
				<div id="<%=div%>" hidden=true>
					<liferay-ui:icon image="delete" onClick="<%=onClickMethod%>" url="javascript:void(0);"/>
				</div>

			<%
			}
			JspHelper.addToPortfolioJSONArray(portfolioJSONArray, portfolio, themeDisplay);
			for (PortfolioFeedback portfolioFeedback : portfolio.getPortfolioFeedbacks()){
				div = "portfolioPublishmentDeleteDiv_" + portfolio.getPlid() + "_" + portfolioFeedback.getUserId();
				onClickMethod = renderResponse.getNamespace() + "deletePublishment(" + portfolio.getPlid() + "," + portfolioFeedback.getUserId() + ");";
				%>
				
				<div id="<%=div%>" hidden=true>
					<liferay-ui:icon image="delete" onClick="<%=onClickMethod%>" url="javascript:void(0);"/>
				</div>
				
				<%
					div = "portfolioFeedbackDeleteDiv_" + portfolio.getPlid() + "_" + portfolioFeedback.getUserId();
					onClickMethod = renderResponse.getNamespace() + "deletePortfolioFeedbackRequest(" + portfolio.getPlid() + "," + portfolioFeedback.getUserId() + ");";
				%>
				
				<div id="<%=div%>" hidden=true>
					<liferay-ui:icon image="delete" onClick="<%=onClickMethod%>" url="javascript:void(0);"/>
				</div>
				
				<%
			}
 	}
 	String portfoliosJSON = portfolioJSONArray.toString();
%>

<aui:script>
var iconMenuIdlist = [];
var myPortfolioDataTable;
var myPortfolioData;
var currentPlid;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'liferay-menu',
    function(A) {    	
    	
        if (A.one("#myPortfolioTable") == null)
            return;
        var data = JSON.parse('<%=portfoliosJSON%>');
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

        myPortfolioDataTable = new A.DataTable({
            columns: [{
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
                            var publishmentDeleteIcon = A.one("#portfolioPublishmentDeleteDiv_" + o.data.plid + "_" + o.data.portfolioFeedbacks[i].userId);
                            publishmentDeleteIconCell = '<td>' + publishmentDeleteIcon.get("innerHTML") + '</td>';
                        } else {
                            publishmentDeleteIconCell = '<td></td>';
                            if (o.data.portfolioFeedbacks[i].feedbackStatus === parseInt('<%=PortfolioStatics.FEEDBACK_REQUESTED%>')) {
                                feedbackCell = '<td class="feedback-requested">' + '<%=LanguageUtil.get(pageContext, "portfolio-feedback-requested")%>';
                            } else {
                                feedbackCell = '<td class="feedback-delivered">' + '<%=LanguageUtil.get(pageContext, "portfolio-feedback-received")%>';
                            }
                            var feedbackDeleteIcon = A.one("#portfolioFeedbackDeleteDiv_" + o.data.plid + "_" + o.data.portfolioFeedbacks[i].userId);
                            feedbackCell += ' <span>(' + o.data.portfolioFeedbacks[i].modifiedDate + ')</span>' +
                                '</td><td>' + feedbackDeleteIcon.get('innerHTML') + '</td>'
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
                        o.cell.setHTML(A.one('#helpIconDiv_' + o.data.plid).get('innerHTML'));
                    } else {
                        var iconMenuDiv = A.one("#iconMenuDiv_" + o.data.plid);
                        var iconMenuId = '<portlet:namespace />iconMenu_' + o.data.plid;
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
                        o.cell.setHTML(A.one("#globalPublishmentDeleteDiv_" + o.data.plid).get('innerHTML'));
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
            }, ],
            data: data,
            editEvent: 'dblclick',
            rowsPerPage: 5,
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "portfolio-show-all")%>'],
            paginatorView : CustomPaginatorView
            
        });
        
        myPortfolioDataTable.render("#myPortfolioTable");
       
        myPortfolioData = myPortfolioDataTable.data;
        A.one('#<portlet:namespace />filterInput').on('keyup', function(e) {
            var filteredData = myPortfolioData.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase());
            });
            myPortfolioDataTable.set('data', filteredData);
        });


    }
);

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
    A.io.request('<%=getUserPortfoliosURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                myPortfolioData = JSON.parse(result);
                myPortfolioDataTable.set('data', myPortfolioData);
            }
        }
    });
}

function refreshPortlet() {
	location.reload();
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

function openPopUp(renderURL, title) {
    Liferay.Util.openWindow({
        dialog: {
            after: {
                destroy: function(event) {
                    document.location.href = '<%=redirect%>';
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