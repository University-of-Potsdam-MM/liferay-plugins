<%@ include file="/html/myportfolio/popup/popup.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<portlet:resourceURL var="feedbackRequestedURL">
	<portlet:param name="<%=Constants.CMD %>" value="feedbackRequested" />
</portlet:resourceURL>

<!-- TODO: Erfolgsmeldung beim Hinzufügen? -->
<aui:layout cssClass="popup-layout">

	<aui:form name="addUserForm" action="" method="post" onSubmit="addUser(event)">
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
			<aui:validator name="custom" errorMessage="portfolio-feedback-already-requested"> 
				function (val, fieldNode, ruleValue) {
		            return !feedbackRequested(val);
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
	
	<aui:form name="form" class="form" id="requestFeedbackForm" method="post">
		<aui:button type="submit" value="portfolio-request-feedback" onClick="requestFeedback(event)"></aui:button>
	</aui:form>
	
	<table hidden="true">
		<tr id="addableRow" >
			<td id="nameField">Cannot sort tables</td>
			<td><liferay-ui:icon id="removeIcon" image="delete" url="#" /></td>
		</tr>
	</table>

</aui:layout>

<aui:script>
function requestFeedback(event){
	event.preventDefault();
	AUI().use('aui-io-request-deprecated','aui-loading-mask-deprecated','autocomplete','io-upload-iframe','json-parse', function (A){
		var loadingMask = new A.LoadingMask(
				{
					'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "portfolio-requesting-feedback") %>',
					target: A.one('.popup-layout')
				}
			);
			
			loadingMask.show();

			A.io.request(
					'<portlet:actionURL name="requestFeedbackFromUsers"></portlet:actionURL>',
				{
					dataType: 'text/html',
					data:{<portlet:namespace />userNames:currentUserNames.toString().replace(',', ';'),<portlet:namespace />portfolioPlid:<%=portfolioPlid%>},
					on: {
						complete: function(event, id, obj) {
							var responseText = obj.responseText;

							var responseData = A.JSON.parse(responseText);

							if (responseData.success) {
								Liferay.Util.getWindow('<portlet:namespace />Dialog').hide();
							}
							else {
								var messageContainer = A.one('#<portlet:namespace />messageContainer');

								if (messageContainer) {
									messageContainer.html('<span class="portlet-msg-error">' + responseData.message + '</span>');
								}

								loadingMask.hide();
							}
						}
					}
				}
			);
	});
}


function feedbackRequested(val){
	var result;
	AUI().use('aui-base',
		function(A) {
			A.io.request('<%=feedbackRequestedURL.toString()%>', {
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
</aui:script>