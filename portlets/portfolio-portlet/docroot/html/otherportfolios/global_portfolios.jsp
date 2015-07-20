<%@page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<% 
	JSONArray portfolioJSONArray = JSONFactoryUtil.createJSONArray();
 	for (Portfolio portfolio : PortfolioLocalServiceUtil.getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(PortfolioStatics.PUBLISHMENT_GLOBAL, themeDisplay.getUserId())){
 		String div = "messageIconDiv_" + portfolio.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "sendMessage(" + portfolio.getLayout().getUserId() + ");";
 		%>
 		<div id="<%= div %>" hidden=true>
<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
</div>
 	

 		
<% 

	JspHelper.addGlobalPortfolioToJSONArray(portfolioJSONArray, portfolio, themeDisplay);
 	} 
 	String portfoliosJSON = portfolioJSONArray.toString();%>


<aui:input class="filterInput" id="otherPortfoliosFilterInput" name="" label=""></aui:input>

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
                    return '<a href='+ o.data.url + '>' + o.data.title + '</a>';
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
                label: '<%= LanguageUtil.get(pageContext, "portfolio-last-changes-column")%>',
                key: 'changes',
                formatter: function(o) {
                    return o.data.modifiedDate;
                },
                sortable: true,
                sortFn: function(a, b, desc) {
                	var datA = a.get("modifiedDate").split('.');
                	var datB = b.get("modifiedDate").split('.');
                        var compare = new Date(datA[0],datA[1],datA[2])-new Date(datB[0],datB[1],datB[2]);
                        return desc ? compare : -compare
                }
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
</aui:script>