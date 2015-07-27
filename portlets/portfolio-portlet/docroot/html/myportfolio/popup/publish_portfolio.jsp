<%@ include file="/html/myportfolio/popup/popup.jsp"%> 

<!-- TODO: Erfolgsmeldung beim Hinzufügen? -->
<aui:layout cssClass="popup-layout">

	<div class="message-container" id="<portlet:namespace />messageContainer"></div>
	<aui:form name="addUserForm" action="" method="post" onSubmit="addUser(event)" >
		<!-- TODO: browser autocomplete deaktivieren -->
	     <aui:input name="name" id="userNameInput" type="text">
	      	<aui:validator name="required" />
	      	<aui:validator name="custom" errorMessage="portfolio-user-does-not-exist">
				function (val, fieldNode, ruleValue) {
					return userExists(val);
				}
			</aui:validator>
			<aui:validator name="custom" errorMessage="portfolio-user-already-added"> 
				function (val, fieldNode, ruleValue) {
		            return (currentUserNames.indexOf(val) == -1);
				}
			</aui:validator>
			<aui:validator name="custom" errorMessage="portfolio-already-published-to-user">
				function (val, fieldNode, ruleValue) {
		            return !userHasViewPermission(val);
				}
			</aui:validator>
	     </aui:input>
	    <aui:button type="submit" name="add" value="add" />
	</aui:form>

	<%=LanguageUtil.get(portletConfig, locale, "portfolio-user-to-add") %>
	
	<table class="aui table table-striped table-bordered">
		 <thead>
	        <tr>
	            <th><%= LanguageUtil.get(pageContext, "name")%></th>
	            <th><%= LanguageUtil.get(pageContext, "remove")%></th>
	        </tr>
	    </thead>
	    <tbody id="userTableBody">
		    <tr>
		    	<td colspan="2"><%=LanguageUtil.get(pageContext, "portfolio-no-users-added") %></td>
		    </tr>
	    </tbody>
	</table>
	
	<aui:form name="form" class="form" id="publishPortfolioForm" method="post">
		<aui:button type="submit" value="portfolio-publish" onClick="publishPortfolioToUsers(event)"></aui:button>
		<aui:button type="submit" value="portfolio-portalwide-publishment" onClick="publishPortfolioGlobal(event)"></aui:button>
	</aui:form>
	
	<table hidden="true">
		<tr id="addableRow" >
			<td id="nameField">Cannot sort tables</td>
			<td><liferay-ui:icon id="removeIcon" image="delete" url="#" /></td>
		</tr>
	</table>

</aui:layout>

<aui:script>
function publishPortfolioToUsers(event) {
    event.preventDefault();
    sendRequest('<portlet:actionURL name="publishPortfolioToUsers"></portlet:actionURL>');
};

function publishPortfolioGlobal(event) {
    event.preventDefault();
    sendRequest('<portlet:actionURL name="publishPortfolioGlobal"></portlet:actionURL>');
};

function sendRequest(actionURL) {
    AUI().use('aui-base', 'aui-io-request', 'aui-loading-mask-deprecated', 'io-upload-iframe', 'json-parse', function(A) {
        var loadingMask = new A.LoadingMask({
            'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "portfolio-publishing-Portfolio") %>',
            target: A.one('.popup-layout')
        });

        loadingMask.show();
        A.io.request(
            actionURL, {
                dataType: 'text/html',
                method: 'post',
                data: { <portlet:namespace/>userNames: currentUserNames.toString().replace(',', ';'), <portlet:namespace/>portfolioPlid: <%=portfolioPlid%>
                },
                on: {
                    success: function() {
                    	console.log("frgerg");
                        Liferay.Util.getWindow('<portlet:namespace />Dialog').hide();
                    }
                }
            }
        );
    });
};
</aui:script>