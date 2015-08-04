<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<% 
	List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByPortfolioFeedbackUserId(themeDisplay.getUserId()); 
%>

<portlet:resourceURL var="getPortfoliosForUserURL">
	<portlet:param name="<%=Constants.CMD %>" value="getPortfoliosForUser" />
</portlet:resourceURL>
<portlet:resourceURL var="markAsFeedbackDeliveredURL">
	<portlet:param name="<%=Constants.CMD %>" value="markAsFeedbackDelivered" />
</portlet:resourceURL>
<portlet:resourceURL var="changeVisibilityURL">
	<portlet:param name="<%=Constants.CMD %>" value="changeVisibility" />
</portlet:resourceURL>

<div id="feedbackRequestedIconDiv" hidden=true>
	<liferay-ui:icon toolTip="" message="" image="activate" />
</div>
<div id="feedbackDeliveredIconDiv" hidden=true>
	<liferay-ui:icon toolTip="" message="" image="check" />
</div>

<aui:input class="otherPortfoliosFilterInput" id="otherPortfoliosFilterInput" name=""  placeholder="portfolio-filter-placeholder"></aui:input>

<% if (portfolios.size() == 0) {%>
	<div class="alert alert-info" ><%=LanguageUtil.get(pageContext,"portfolio-no-other-portfolios")%></div>
<% } else { %>
	<div id="otherPortfoliosTable"></div>  
<% } %>

<a id="visibilityHref" href="javascript:void(0);" onClick="<portlet:namespace />changeShownPortfolioPages()"><%= LanguageUtil.get(pageContext, "portfolio-show-hidden-portfolio-pages")%></a>


<% 
	JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
 	for (Portfolio portfolio : portfolios){
 		String div = "messageIconDiv_" + portfolio.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "sendMessage(" + portfolio.getLayout().getUserId() + ");";
 		%>	
 		<div id="<%= div %>" hidden=true>
			<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
		<% 
 		String divUnhidden = "unhiddenIconDiv_" + portfolio.getPlid();
 		String divHidden = "hiddenIconDiv_" + portfolio.getPlid();
 		onClickMethod = renderResponse.getNamespace() + "changeVisibility(" + portfolio.getPlid() + ");";
		%>
 		<div id="<%= divUnhidden %>" hidden=true>
			<liferay-ui:icon image="../aui/eye-open" message="visible" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
 		<div id="<%= divHidden %>" hidden=true>
			<liferay-ui:icon image="../aui/eye-close" message="hidden" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
		<% 
		JspHelper.addToPortfolioFeedbackJSONArray(portfolioJSONArray, portfolio, themeDisplay,portletConfig);
 	} 
 	String portfoliosJSON = portfolioJSONArray.toString(); 
 %>

<aui:script>
var otherPortfoliosDataTable;
var otherPortfoliosData;
var showHidden = false;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'datatable-sort',
    function(A) {
        if (A.one("#otherPortfoliosTable") == null)
            return;
        var data = JSON.parse('<%=portfoliosJSON%>');
        otherPortfoliosDataTable = new A.DataTable({
            columns: [{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-title-column")%>',
                key: 'title',
                formatter: function(o) {
                    return '<a href=' + o.data.url + '>' + o.data.title + '</a> (' + o.data.createDate + ')';
                },
                allowHTML: true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('title').localeCompare(b.get('title'));
                    return desc ? compare : -compare
                }
            }, {
                label: '<%= LanguageUtil.get(pageContext, "portfolio-creator-column")%>',
                key: 'creator',
                formatter: function(o) {
                    return o.data.userName + ' ' + A.one('#messageIconDiv_' + o.data.plid).get('innerHTML');
                },
                allowHTML: true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('userName').localeCompare(b.get('userName'));
                    return desc ? compare : -compare
                }
            }, {
                label: '<%= LanguageUtil.get(pageContext, "portfolio-feedback-column")%>',
                key: 'feedback',
                nodeFormatter: function(o) {
                    var result = "";
                    if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_UNREQUESTED%>')) {
                        o.td.setAttribute('class', 'feedback-unrequested');
                    } else if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_REQUESTED%>')) {
                        o.td.setAttribute('class', 'feedback-requested');
                        result += A.one('#feedbackRequestedIconDiv').get('innerHTML');
                    } else if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_DELIVERED%>')) {
                        o.td.setAttribute('class', 'feedback-delivered');
                        result += A.one('#feedbackDeliveredIconDiv').get('innerHTML');
                    }
                    result += o.data.feedbackStatusString;
                    o.cell.setHTML(result);
                    return false;
                },
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('feedbackStatusString').localeCompare(b.get('feedbackStatusString'));
                    return desc ? compare : -compare
                }
            }, {
                label: '<%= LanguageUtil.get(pageContext, "portfolio-options-column")%>',
                key: 'options',
                formatter: function(o) {
                    var result = "";
                    if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_REQUESTED%>')) {
                        result += '<a href="javascript:void(0);" onClick="<portlet:namespace />markAsFeedbackDelivered(' + o.data.plid + ')">' +
                            '<%=LanguageUtil.get(pageContext, "portfolio-mark-as-feedback-delivered")%>' + '</a>';
                    }
                    return result;
                },
                allowHTML: true
            }, {
                label: '<%= LanguageUtil.get(pageContext, "portfolio-visible")%>',
                key: 'visible',
                formatter: function(o) {
                    var result = "";
                    if (Boolean(o.data.hidden)) {
                        result += A.one('#hiddenIconDiv_' + o.data.plid).get('innerHTML');
                    } else {
                        result += A.one('#unhiddenIconDiv_' + o.data.plid).get('innerHTML');
                    }
                    return result;
                },
                allowHTML: true
            }],
            data: data,
            rowsPerPage: 5,
            pageSizes: [5, 10, 20, 30, 50, 100, 'Show All']
        });

        otherPortfoliosDataTable.render("#otherPortfoliosTable");
        otherPortfoliosData = otherPortfoliosDataTable.data;
        filterPortfoliosByVisibleState(otherPortfoliosData);

        A.one('#<portlet:namespace />otherPortfoliosFilterInput').on('keyup', function(e) {
            var filteredData = otherPortfoliosData.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase()) && Boolean(list.get('hidden')) === showHidden;
            });
            otherPortfoliosDataTable.set('data', filteredData);
        });
    }
);

function updateOtherPortfoliosData(A) {
    A.io.request('<%=getPortfoliosForUserURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                otherPortfoliosData = JSON.parse(result);
                otherPortfoliosDataTable.set('data', otherPortfoliosData);
                filterPortfoliosByVisibleState(otherPortfoliosDataTable.data);
            }
        }
    });
}

Liferay.provide(window, '<portlet:namespace />markAsFeedbackDelivered',
    function(plid) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=markAsFeedbackDeliveredURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid
                },
                on: {
                    success: function() {
                        updateOtherPortfoliosData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />changeVisibility',
    function(plid) {
        AUI().use('aui-base', 'aui-io-request', function(A) {
            A.io.request('<%=changeVisibilityURL.toString()%>', {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>portfolioPlid: plid
                },
                on: {
                    success: function() {
                        updateOtherPortfoliosData(A);
                    }
                }
            });
        });
    });

Liferay.provide(window, '<portlet:namespace />changeShownPortfolioPages',
    function() {
        var A = new AUI();
        showHidden = !showHidden;
        updateOtherPortfoliosData(A);
        var visibilityHref = A.one('#visibilityHref');
        if (showHidden)
            visibilityHref.set('text', '<%= LanguageUtil.get(pageContext, "portfolio-show-unhidden-portfolio-pages")%>');
        else
            visibilityHref.set('text', '<%= LanguageUtil.get(pageContext, "portfolio-show-hidden-portfolio-pages")%>');
    });

function filterPortfoliosByVisibleState(tableData) {
    var filteredData = tableData.filter({
        asList: true
    }, function(list) {
        return Boolean(list.get('hidden')) === showHidden;
    });
    otherPortfoliosDataTable.set('data', filteredData);
}
</aui:script>