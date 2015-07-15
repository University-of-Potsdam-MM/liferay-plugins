<%@ include file="/html/myportfolio/popup/popup.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<!-- TODO: Erfolgsmeldung beim Hinzufügen? -->
<aui:layout cssClass="popup-layout">

	<aui:form name="addUserForm" action="" method="post" onSubmit="addUser()">
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
		            return !userHasPermission(val);
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
		<aui:button type="submit" value="portfolio-request-feedback"></aui:button>
	</aui:form>
	
	<table hidden="true">
		<tr id="addableRow" >
			<td id="nameField">Cannot sort tables</td>
			<td><liferay-ui:icon id="removeIcon" image="delete" url="#" /></td>
		</tr>
	</table>

</aui:layout>

<aui:script use="aui-io-request-deprecated,aui-loading-mask-deprecated,autocomplete,io-upload-iframe,json-parse">
var form = A.one('#<portlet:namespace />form');

form.on(
	'submit',
	function(event) {
		
		var loadingMask = new A.LoadingMask(
			{
				'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "sending-message") %>',
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
	}
);
</aui:script>