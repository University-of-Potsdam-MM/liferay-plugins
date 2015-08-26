<%@ include file="/html/init.jsp"%>

<% 
	List<Portfolio> portfolios = PortfolioLocalServiceUtil.getPortfoliosByPublishmentTypeAndNoPortfolioFeedback(PortfolioStatics.PUBLISHMENT_GLOBAL, themeDisplay.getUserId());
%>

<aui:input class="filterInput" id="otherPortfoliosFilterInput" name=""  placeholder="portfolio-filter-placeholder"></aui:input>

<% if (portfolios.size() == 0){ %>
	<div class="alert alert-info" ><%=LanguageUtil.get(pageContext,"portfolio-no-portfolios")%></div>
<% } else { %>
	<div id=globalPortfoliosTable></div>
<% } %>

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

		JspHelper.addGlobalPortfolioToJSONArray(portfolioJSONArray, portfolio, themeDisplay);
 	} 
 	String portfoliosJSON = portfolioJSONArray.toString();
 %>

<aui:script>
var globalPortfoliosDataTable;
var globalPortfoliosData;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'datatable-sort',
    function(A) {
        if (A.one("#globalPortfoliosTable") == null)
            return;
        var data = JSON.parse('<%=portfoliosJSON%>');
        globalPortfoliosDataTable = new A.DataTable({
            columns: [{
                label: '<%= LanguageUtil.get(pageContext, "portfolio-title-column")%>',
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
                label: '<%= LanguageUtil.get(pageContext, "portfolio-last-changes-column")%>',
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
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "portfolio-show-all")%>'],
            paginatorView : CustomPaginatorView
        });

        globalPortfoliosDataTable.render("#globalPortfoliosTable");
        globalPortfoliosDataTable.sort('title');

        globalPortfoliosData = globalPortfoliosDataTable.data;
        A.one('#<portlet:namespace />otherPortfoliosFilterInput').on('keyup', function(e) {
            var filteredData = globalPortfoliosData.filter({
                asList: true
            }, function(list) {

                return list.get('title').toLowerCase().includes(e.currentTarget.get("value").toLowerCase());
            });
            globalPortfoliosDataTable.set('data', filteredData);
        });
    }
);
</aui:script>