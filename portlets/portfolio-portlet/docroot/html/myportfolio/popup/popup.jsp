<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	String portfolioPlid = renderRequest.getParameter("portfolioPlid");
	String parentPageRedirect = renderRequest.getParameter("currentRedirect");  
%>

<portlet:resourceURL var="findUsers">
	<portlet:param name="<%=Constants.CMD %>" value="findUsers" />
</portlet:resourceURL>
<portlet:resourceURL var="userExists">
	<portlet:param name="<%=Constants.CMD %>" value="userExists" />
</portlet:resourceURL>
<portlet:resourceURL var="userHasPermission">
	<portlet:param name="<%=Constants.CMD %>" value="userHasPermission" />
</portlet:resourceURL>
<portlet:actionURL name="publishPortfolioToUsers" var="publishPortfolioToUsersURL" >
    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
	<portlet:param name="redirect" value="<%=parentPageRedirect%>" />
	<portlet:param name="portfolioPlid" value="<%=portfolioPlid%>" />
</portlet:actionURL>
<portlet:actionURL name="publishPortfolioGlobal" var="publishPortfolioGlobalURL" > 
    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
	<portlet:param name="redirect" value="<%=parentPageRedirect%>" />
	<portlet:param name="portfolioPlid" value="<%=portfolioPlid%>" />
</portlet:actionURL>
<portlet:actionURL name="requestFeedbackFromUsers" var="requestFeedbackFromUsersURL" >
    <portlet:param name="mvcPath" value="/html/myportfolio/view.jsp" />
	<portlet:param name="redirect" value="<%=parentPageRedirect%>" />
	<portlet:param name="portfolioPlid" value="<%=portfolioPlid%>" />
</portlet:actionURL>

<aui:script>
var currentUserNames = [];
var userNameInputField;
var addableRow;
var userTableBody;

AUI().use('aui-base',
	function(A) {
		userNameInputField = A.one("#<portlet:namespace />userNameInput");
		addableRow = A.one('#addableRow');
		userTableBody = A.one('#userTableBody');
	});

AUI().use('autocomplete-list','aui-base','aui-io-request','autocomplete-filters','autocomplete-highlighters', function (A) {
	var contactSearchFormatter = function (query, results) {
		return A.Array.map(results, function (result) {
			return '<strong>'+result.raw.screenName+'</strong><br/>'+result.raw.email;
		});
	};
	var testData;
	new A.AutoCompleteList({
	allowBrowserAutocomplete: 'true',
	activateFirstItem: 'true',
	inputNode: '#<portlet:namespace />userNameInput',
	resultTextLocator:'screenName',
	render: 'true',
	resultHighlighter: 'phraseMatch',
	resultFilters:['phraseMatch'],
	resultFormatter: contactSearchFormatter,
	source:function(){
		var inputValue=A.one("#<portlet:namespace />userNameInput").get('value');
		var myAjaxRequest=A.io.request('<%=findUsers.toString()%>',{
			    dataType: 'json',
				method:'POST',
				data:{
				<portlet:namespace />inputValue:inputValue,
				},
				autoLoad:false,
				sync:false,
			    on: {
	 				 success:function(){
	 					var data=this.get('responseData');
	 					testData=data;
	 				}}
		});
		myAjaxRequest.start();
		return testData;},
	});
});
		
function userExists(val){
	var result;
	AUI().use('aui-base',
		function(A) {
			A.io.request('<%=userExists.toString()%>', {
				dataType: 'text/html',
		       	method: 'post',
				sync: true,
				timeout: 3000,
				data:{<portlet:namespace />userName:val},
		      	on: {
		        	success: function() {
		       			result = this.get('responseData');
		            }
		       }
		    });
		});
    return (result == 'true');
}

function userHasPermission(val){
	var result;
	AUI().use('aui-base',
		function(A) {
			A.io.request('<%=userHasPermission.toString()%>', {
				dataType: 'text/html',
		       	method: 'post',
				sync: true,
				timeout: 3000,
				data:{
					<portlet:namespace />name:val,
					<portlet:namespace />portfolioPlid:<%=portfolioPlid%>				
				},
		      	on: {
		        	success: function() {
		       			result = this.get('responseData');
		            }
		       }
		    });
		});
    return (result == 'true');
}
		
function addUser(){
	event.preventDefault();
	currentUserNames.push(userNameInputField.get('value'));
	updateUserTable();
};

function updateUserTable(){
	userTableBody.setHTML("");
	if (currentUserNames.length == 0)
		userTableBody.append("<tr><td colspan=\"2\"><%=LanguageUtil.get(pageContext, "portfolio-no-users-added") %></td></tr>")
	else {
		for (var i = 0;i < currentUserNames.length; i++) {
			var addableRowCopy = addableRow.cloneNode(true);
			addableRowCopy.set("hidden", false);
			var icon = addableRowCopy.one(".taglib-icon");
			var nameField = addableRowCopy.one("#nameField");
			var text = nameField.get("firstChild");
			text.set("data",currentUserNames[i]);
			icon.set("onclick", createDeleteUserFunction(i) );
			userTableBody.append(addableRowCopy);
		}	
	}
}

function createDeleteUserFunction(i) {
    return function() { 
		currentUserNames.splice(i, 1);
		updateUserTable(); 
	};
}

function requestFeedbackFromUsers(){
	AUI().use('aui-base',function(A) {
		var requestFeedbackForm = A.one('#<portlet:namespace />requestFeedbackForm');
		requestFeedbackForm.setAttribute("action", createRequestFeedbackURL(currentUserNames).toString());});
};

var createRequestFeedbackURL = function(userNames) {
    var portletURL = '<%= requestFeedbackFromUsersURL %>';
    
    var namespace = Liferay.Util.getPortletNamespace('myportfolio_WAR_portfolioportlet');
    AUI().Object.each(
        {
            userNames: userNames
        },
        function(item, index, collection) {
            if (item) {
                portletURL = portletURL + '&' + namespace + encodeURIComponent(index) + '=' + encodeURIComponent(item);
            }
        }
    );
    return portletURL;
};
</aui:script>