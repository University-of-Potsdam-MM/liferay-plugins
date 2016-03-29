<%@page import="java.util.Arrays"%>
<%@page import="javax.portlet.PortletPreferences"%>
<%@page import="java.util.ArrayList"%>
<%@ include file="/html/init_view.jsp"%>

<% 
	List<Layout> customPages =CustomPageFeedbackLocalServiceUtil.getCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(themeDisplay.getUserId()); 

%>

<portlet:resourceURL var="getCustomPagesForUserURL">
	<portlet:param name="<%=Constants.CMD %>" value="getCustomPagesForUser" />
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

<%@ include file="/html/table_filter_input.jsp" %>

<% if (customPages.size() == 0) {%>
	<div class="alert alert-info" ><%=LanguageUtil.get(pageContext,"custompages-no-other-custom-pages")%></div>
<% } else { %>
	<div id="otherCustomPagesTable" class="loading-animation"></div>  
<% } %>



<% 
	JSONArray customPageJSONArray = JSONFactoryUtil.createJSONArray();
	String[] hiddenLayouts = portletPreferences.getValues("hiddenLayouts", new String[]{});
	List<String> newHiddenLayouts = new ArrayList<String>();
 	for (Layout customPage : customPages){
 		String div = "messageIconDiv_" + customPage.getPlid();
 		String onClickMethod = renderResponse.getNamespace() + "sendMessage(" + customPage.getUserId() + ");";
 		%>	
 		<div id="<%= div %>" hidden=true>
			<liferay-ui:icon cssClass="send-message" image="../aui/envelope" message="message" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
		<% 
 		String divUnhidden = "unhiddenIconDiv_" + customPage.getPlid();
 		String divHidden = "hiddenIconDiv_" + customPage.getPlid();
 		onClickMethod = renderResponse.getNamespace() + "changeVisibility(" + customPage.getPlid() + ");";
		%>
 		<div id="<%= divUnhidden %>" hidden=true>
			<liferay-ui:icon image="../aui/eye-open" message="visible" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
 		<div id="<%= divHidden %>" hidden=true>
			<liferay-ui:icon image="../aui/eye-close" message="hidden" onClick="<%= onClickMethod %>" url="javascript:void(0);" />
		</div>
		<% 
		JspHelper.addToCustomPageFeedbackJSONArray(customPageJSONArray, customPage, themeDisplay, portletConfig,Arrays.asList(hiddenLayouts),newHiddenLayouts);
 	} 
 	String customPagesJSON = customPageJSONArray.toString(); 
 	portletPreferences.setValues("hiddenLayouts", newHiddenLayouts.toArray(new String[newHiddenLayouts.size()]));
 	portletPreferences.store();
 %>

<aui:script>
var otherCustomPagesDataTable;
var otherCustomPagesData;
AUI().use(
    'aui-datatable',
    'datatable-sort',
    'datatable-paginator',
    'datatable-sort',
    function(A) {
    	var otherCustomPagesTableDiv = A.one("#otherCustomPagesTable");
        if (otherCustomPagesTableDiv == null)
            return;
        var data = JSON.parse('<%=customPagesJSON%>');
        otherCustomPagesDataTable = new A.DataTable({
            columns: [{
                label: '<%= LanguageUtil.get(pageContext, "custompages-title-column")%>',
                key: 'title',
                formatter: function(o) {
                    return '<a target="_blank" href=' + o.data.url + '>' + o.data.title + '</a> (' + o.data.createDate + ')';
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
                label: '<%= LanguageUtil.get(pageContext, "custompages-published-to-column")%>',
                key: 'publishedTo',
                formatter: function(o) {
                	if (o.data.isGlobal)
                		return "<%=LanguageUtil.get(pageContext, "custompages-global")%>"
                	else
                		return "<%=user.getFullName()%>"
                },
                allowHTML: true,
                sortable: true,
                sortFn: function(a, b, desc) {
                    var compare = a.get('userName').localeCompare(b.get('userName'));
                    return desc ? compare : -compare
                }
            }, {
                label: '<%= LanguageUtil.get(pageContext, "custompages-feedback-column")%>',
                key: 'feedback',
                nodeFormatter: function(o) {
                    var result = "";
                    if (o.data.feedbackStatus === parseInt('<%= CustomPageStatics.FEEDBACK_UNREQUESTED%>')) {
                        o.td.setAttribute('class', 'feedback-unrequested');
                    } else if (o.data.feedbackStatus === parseInt('<%= CustomPageStatics.FEEDBACK_REQUESTED%>')) {
                        o.td.setAttribute('class', 'feedback-requested');
                        result += A.one('#feedbackRequestedIconDiv').get('innerHTML');
                    } else if (o.data.feedbackStatus === parseInt('<%= CustomPageStatics.FEEDBACK_DELIVERED%>')) {
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
                label: '<%= LanguageUtil.get(pageContext, "custompages-options-column")%>',
                key: 'options',
                formatter: function(o) {
                    var result = "";
                    if (o.data.feedbackStatus === parseInt('<%= CustomPageStatics.FEEDBACK_REQUESTED%>')) {
                        result += '<a href="javascript:void(0);" onClick="<portlet:namespace />markAsFeedbackDelivered(' + o.data.plid + ')">' +
                            '<%=LanguageUtil.get(pageContext, "custompages-mark-as-feedback-delivered")%>' + '</a>';
                    }
                    return result;
                },
                allowHTML: true
            }, {
                label: '<%= LanguageUtil.get(pageContext, "custompages-hidden-column")%>',
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
            pageSizes: [5, 10, 20, 30, 50, 100, '<%= LanguageUtil.get(pageContext, "custompages-show-all")%>'],
            paginatorView : CustomPaginatorView
        });

        otherCustomPagesDataTable.render("#otherCustomPagesTable");
        otherCustomPagesDataTable.sort('title');
        otherCustomPagesData = otherCustomPagesDataTable.data;
        
        <portlet:namespace />initFilter(otherCustomPagesDataTable,false);
        <portlet:namespace />filterTable();
        
        otherCustomPagesTableDiv.removeClass('loading-animation');
    }
);

function updateOtherCustomPagesData(A) {
	var currentPage = otherCustomPagesDataTable.get('paginatorModel').get('page');
    A.io.request('<%=getCustomPagesForUserURL.toString()%>', {
        dataType: 'text/html',
        method: 'post',
        on: {
            success: function() {
                result = this.get('responseData');
                otherCustomPagesData = JSON.parse(result);
                otherCustomPagesDataTable.set('data', otherCustomPagesData);
                otherCustomPagesDataTable.get('paginatorModel').set('page',currentPage);
                <portlet:namespace />updateFilter(otherCustomPagesDataTable.data);
                <portlet:namespace />filterTable();
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
                data: { <portlet:namespace/>customPagePlid: plid
                },
                on: {
                    success: function() {
                        updateOtherCustomPagesData(A);
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
                data: { <portlet:namespace/>customPagePlid: plid
                },
                on: {
                    success: function() {
                        updateOtherCustomPagesData(A);
                        <portlet:namespace />fadeInAlert('<%= LanguageUtil.get(pageContext, "custompages-page-visibility-changed") %>');
                    }
                }
            });
        });
    });
</aui:script>