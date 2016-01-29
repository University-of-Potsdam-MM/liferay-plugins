<%@ include file="/html/init.jsp"%>

<%
	String submitMethod;
	String submitButtonValue;
	boolean exists = !ParamUtil.getString(request, "key").equals("");
	
	if (!exists){
		submitMethod = "createLanguageKey(event);";
		submitButtonValue = "add";
	}
	else{
		submitMethod = "updateLanguageKey(event);";
		submitButtonValue = "update";
	}
%>

<aui:layout cssClass="popup-layout">

	<div class="message-container" id="<portlet:namespace />messageContainer"></div>
	
	<aui:form name="form" class="form" method="post" onSubmit="<%= submitMethod %>">
		<aui:input id="key" name="key" type="text">
	      	<aui:validator name="required" />
	    </aui:input>
	    
	    <% for (Locale availableLocale : LanguageUtil.getAvailableLocales()) { %>
		<aui:input id="<%= availableLocale.getLanguage() %>" name="<%= availableLocale.getLanguage() %>" label="<%= availableLocale.getDisplayLanguage(themeDisplay.getLocale())%>" type="text">
	    </aui:input>
	    
	    <% } %>
	    
		<div id="addLanguageKey">
			<aui:button type="submit" value="<%=submitButtonValue %>" />
		</div>
	</aui:form> 
	
</aui:layout>

<aui:script>
function setDescription(){
	AUI().use('aui-base',function(A){
	var inputs = A.all('input');
	for (var i = 0; i < inputs.size(); i++){
		var item = inputs.item(i);
		if (item.get("type") === 'radio' && item.get("checked") === true){
			var descriptionDiv = A.one('#descriptionContent');
			var description = A.one('#' + item.get("id") + '_description').get("value");
			descriptionDiv.set('innerHTML',description);
		}
	}});
}

function createLanguageKey(event) {
    event.preventDefault();
    callActionURL('<%= UnicodeLanguageUtil.get(pageContext, "creating-language-key") %>',
    		'<portlet:actionURL name="createLanguageKey"></portlet:actionURL>',
    		'<%= LanguageUtil.get(pageContext, "language-key-already-exists")%>');
}

function updateLanguageKey(event) {
    event.preventDefault();
    callActionURL('<%= UnicodeLanguageUtil.get(pageContext, "updating-language-key") %>',
    		'<portlet:actionURL name="updateLanguageKey"></portlet:actionURL>',
    		'<%= LanguageUtil.get(pageContext, "language-key-update-failed")%>');
}

function callActionURL(loadingString, actionURL, failureMessage) {
    AUI().use('aui-io-request-deprecated', 'aui-loading-mask-deprecated', 'autocomplete,io-upload-iframe', 'json-parse', function(A) {
        var form = A.one('#<portlet:namespace />form');
        var loadingMask = new A.LoadingMask({
            'strings.loading': loadingString,
            target: A.one('.popup-layout')
        });

        loadingMask.show();

        A.io.request(
        		actionURL, {
                dataType: 'json',
                form: {
                    id: form,
                    upload: true
                },
                on: {
                    complete: function(event, id, obj) {
                        var responseText = obj.responseText;

                        var responseData = A.JSON.parse(responseText);

                        if (responseData.success) {
                            Liferay.Util.getWindow('<portlet:namespace />Dialog').hide();
                        } else {
                            var messageContainer = A.one('#<portlet:namespace />messageContainer');

                            if (messageContainer) {
                                messageContainer.html('<span class="portlet-msg-error">' + failureMessage + '</span>');
                            }

                            loadingMask.hide();
                        }
                    }
                }
            }
        );
    });
}
</aui:script>
