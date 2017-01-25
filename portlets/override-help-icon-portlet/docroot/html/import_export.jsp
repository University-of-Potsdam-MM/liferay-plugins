<%@ include file="/html/init.jsp"%>

<aui:layout cssClass="popup-layout">
	<div class="message-container" id="<portlet:namespace />messageContainer"></div>
	
	<portlet:resourceURL  var="exportLanguageKeysURL"></portlet:resourceURL>
	<aui:button onClick="<%= exportLanguageKeysURL.toString() %>" value="export" />
	<br><br><br>
	<aui:form name="form" class="form" method="post" onSubmit="importLanguageKeys(event)">
		<aui:input id="importContent" name="importContent" title="import-content" type="text" helpMessage="import-export-help">
	      	<aui:validator name="required" />
	    </aui:input>
		<aui:button type="submit" value="import"/>
	</aui:form> 
	
</aui:layout>

<aui:script>

function importLanguageKeys(event) {
    event.preventDefault();
    callActionURL('<%= UnicodeLanguageUtil.get(pageContext, "importing") %>',
    		'<portlet:actionURL name="importLanguageKeys"></portlet:actionURL>',
    		'<%= LanguageUtil.get(pageContext, "import-failure")%>');
}

function callActionURL(loadingString, actionURL,failureMessage) {
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
                            	console.log("EWtw");
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