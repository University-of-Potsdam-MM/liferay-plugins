<%@ include file="/html/init.jsp"%>

<% 
	List<Layout> customPages = CustomPageFeedbackLocalServiceUtil
		.getGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE, themeDisplay.getUserId());
%>

<aui:input class="filterInput" id="otherCustomPagesFilterInput" name=""  placeholder="custompages-filter-placeholder"></aui:input>

<% if (customPages.size() == 0){ %>
	<div class="alert alert-info" ><%=LanguageUtil.get(pageContext,"custompages-no-custom-pages")%></div>
<% } else { %>
	<div id="globalCustomPagesTable" class="loading-animation"></div>
<% } %>

<% 
	JSONArray customPageJSONArray = JSONFactoryUtil.createJSONArray();
 	for (Layout customPage : customPages){
 		String div = "messageIconDiv_" + customPage.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "sendMessage(" + customPage.getUserId() + ");";
 		%>
 		
 		<div id="<%= div %>" hidden=true>
			<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
 	<% 

		JspHelper.addGlobalCustomPageToJSONArray(customPageJSONArray, customPage, themeDisplay);
 	} 
 	String customPagesJSON = customPageJSONArray.toString();
 %>

<aui:script>
var globalCustomPagesDataTable;
var globalCustomPagesData;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'datatable-sort',
    function(A) {
    	var globalCustomPagesDataDiv = A.one("#globalCustomPagesTable");
        if (globalCustomPagesDataDiv == null)
            return;
        var data = JSON.parse('<%=customPagesJSON%>');
        globalCustomPagesDataTable = new A.DataTable({
            columns: [{
                label: '<%= LanguageUtil.get(pageContext, "custompages-title-column")%>',
                key: 'title',
                formatter: function(o) {
                    return '<a target="_blank" href=' + o.data.url + '>' + o.data.title + '</a>';
                },
                allowHTML: true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('title').localeCompare(b.get('title'));
                    return desc ? compare : -compare
                }
            }, {
                label: '<%= LanguageUtil.get(pageContext, "custompages-creator-column")%>',
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
                label: '<%= LanguageUtil.get(pageContext, "custompages-last-changes-column")%>',
                key: 'changes',
                formatter: function(o) {
                    return o.data.modifiedDate;
                },
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = parseInt(a.get("modifiedDateInMilliseconds")) - parseInt(b.get("modifiedDateInMilliseconds"));
                    return desc ? compare : -compare
                }
            }],
            data: data,
            rowsPerPage: 5,
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "custompages-show-all")%>'],
            paginatorView : CustomPaginatorView
        });

        globalCustomPagesDataTable.render("#globalCustomPagesTable");
        globalCustomPagesDataTable.sort('title');

        globalCustomPagesData = globalCustomPagesDataTable.data;
        A.one('#<portlet:namespace />otherCustomPagesFilterInput').on('keyup', function(e) {
            var filteredData = globalCustomPagesData.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase());
            });
            globalCustomPagesDataTable.set('data', filteredData);
        });
        globalCustomPagesDataDiv.removeClass('loading-animation');
    }
);
</aui:script>