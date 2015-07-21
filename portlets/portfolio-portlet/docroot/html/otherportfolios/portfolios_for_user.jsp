<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<portlet:resourceURL var="getPortfoliosForUserURL">
	<portlet:param name="<%=Constants.CMD %>" value="getPortfoliosForUser" />
</portlet:resourceURL>

<portlet:resourceURL var="markAsFeedbackDeliveredURL">
	<portlet:param name="<%=Constants.CMD %>" value="markAsFeedbackDelivered" />
</portlet:resourceURL>

<div id="feedbackRequestedIconDiv" hidden=true>
		<liferay-ui:icon toolTip="" message="" image="activate" />
	</div>
	<div id="feedbackDeliveredIconDiv" hidden=true>
		<liferay-ui:icon toolTip="" message="" image="check" />
	</div>
<% 
	JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
 	for (Portfolio portfolio : PortfolioLocalServiceUtil.getPortfoliosByPortfolioFeedbackUserId(themeDisplay.getUserId())){
 		String div = "messageIconDiv_" + portfolio.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "sendMessage(" + portfolio.getLayout().getUserId() + ");";
 		%>
 		<div id="<%= div %>" hidden=true>
<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
</div>
 	

 		
<% 

	JspHelper.addToPortfolioFeedbackJSONArray(portfolioJSONArray, portfolio, themeDisplay,portletConfig);
 	} 
 	String portfoliosJSON = portfolioJSONArray.toString();%>



<portlet:actionURL name="filterPortfolios" var="filterPortfoliosURL">
	<portlet:param name="myParam" value="1"/>
</portlet:actionURL>

<aui:input class="otherPortfoliosFilterInput" id="otherPortfoliosFilterInput" name=""  placeholder="portfolio-filter-placeholder"></aui:input>

<div id="otherPortfoliosTable"></div>  

<aui:script>
var dt1;
var currentData1;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'datatable-sort',
    function(A) {
        var data = JSON.parse('<%=portfoliosJSON%>');
        dt1 = new A.DataTable({
            columns: [{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-title-column")%>',
                key: 'title',
                formatter: function(o) {
                    return '<a href='+ o.data.url + '>' + o.data.title + '</a> (' + o.data.createDate + ')';
                },
                allowHTML : true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('title').localeCompare(b.get('title'));
                    return desc ? compare : -compare
                }
            },{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-creator-column")%>',
                key: 'creator',
                formatter: function(o) {
                    return o.data.userName + ' ' + A.one('#messageIconDiv_' + o.data.plid).get('innerHTML');
                },
                allowHTML : true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('userName').localeCompare(b.get('userName'));
                    return desc ? compare : -compare
                }
            },{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-feedback-column")%>',
                key: 'feedback',
                formatter: function(o) {
                	var result = "";
                	if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_REQUESTED%>')){
                		result += A.one('#feedbackRequestedIconDiv').get('innerHTML');
                	}
                	else if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_DELIVERED%>')){
                		result += A.one('#feedbackDeliveredIconDiv').get('innerHTML');                		
                	}
                	result += o.data.feedbackStatusString;
                    return result;
                },
                allowHTML : true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('feedbackStatusString').localeCompare(b.get('feedbackStatusString'));
                    return desc ? compare : -compare
                }
            },{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-options-column")%>',
                key: 'options',
                formatter: function(o) {
                	var result = "";
                	if (o.data.feedbackStatus === parseInt('<%= PortfolioStatics.FEEDBACK_REQUESTED%>')){
                		result += '<a href="javascript:void(0);" onClick="<portlet:namespace />markAsFeedbackDelivered(' + o.data.plid + ')">' + 
                			'<%=LanguageUtil.get(pageContext, "portfolio-mark-as-feedback-delivered")%>' + '</a>';
                	}
                    return result;
                },
                allowHTML : true
            }],
            data : data,
            rowsPerPage: 5,
            pageSizes: [5,10,20,30,50,100, 'Show All']
        });

        dt1.render("#otherPortfoliosTable");

        currentData1 = dt1.data;
        A.one('#<portlet:namespace />otherPortfoliosFilterInput').on('keyup', function(e) {
            var filteredData = currentData1.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase());
            });
            dt1.set('data', filteredData);
        });
    }
);

function updateTableData1(A){
	A.io.request('<%=getPortfoliosForUserURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                currentData1 = JSON.parse(result);
                dt1.set('data', currentData1);
            }
        }
    });
}

Liferay.provide(
		window,
		'<portlet:namespace />sendMessage',
		function(userId) {
			<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>" />

			var uri = '<liferay-portlet:renderURL portletName="1_WAR_privatemessagingportlet" windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/new_message.jsp" /><portlet:param name="redirect" value="<%= redirectURL %>" /></liferay-portlet:renderURL>';

			uri = Liferay.Util.addParams('<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>userIds=' + userId, uri) || uri;

			Liferay.Util.openWindow(
				{
					dialog: {
						centered: true,
						constrain: true,
						cssClass: 'private-messaging-portlet',
						destroyOnHide: true,
						height: 600,
						modal: true,
						plugins: [Liferay.WidgetZIndex],
						width: 600
					},
					id: '<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>Dialog',
					title: '<%= UnicodeLanguageUtil.get(pageContext, "new-message") %>', 
					uri: uri
				}
			);
		},
		['liferay-util-window']
	);
	
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
	                    	updateTableData1(A);
	                    }
	                }
	            });
	        });
	    });
</aui:script>