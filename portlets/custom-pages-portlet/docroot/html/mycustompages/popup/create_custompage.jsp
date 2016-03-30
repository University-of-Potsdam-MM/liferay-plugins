<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	Map<String,LayoutPrototype> customPageTemplates = JspHelper.getCustomPageLayoutPrototypes();
%>

<aui:layout cssClass="popup-layout">

	<div class="message-container" id="<portlet:namespace />messageContainer"></div>

	<aui:form name="form" class="form" method="post" onSubmit="createCustomPage(event);">
		<aui:input id="customPageNameInput" name="customPageName" type="text" label="custompages-name">
	      	<aui:validator name="required" />
	    </aui:input>
	    
	 	<div id="templateChoice">
			<aui:field-wrapper name="template" >
				<% LayoutPrototype lp = customPageTemplates.get(CustomPageStatics.BLOG_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMANY) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = customPageTemplates.get(CustomPageStatics.WIKI_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMANY) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = customPageTemplates.get(CustomPageStatics.CDP_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMANY) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = customPageTemplates.get(CustomPageStatics.EMPTY_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMANY) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				
			</aui:field-wrapper>
		</div>
		
		<div id="customPageTypeChoice">
			<aui:field-wrapper name="customPageType" label="custompages-page-type" >
				<aui:input id="pageTypeNormalPage" inlineLabel="right"  checked="<%= true %>" name="customPageType" type="radio" value="<%= CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE%>" label="custompages-page-type-normal-page" />
				<aui:input id="pageTypePortfolioPage" inlineLabel="right" checked="<%= false %>" name="customPageType" type="radio" value="<%= CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE%>" label="custompages-page-type-portfolio-page" />
			</aui:field-wrapper>
		</div>
		
		<div id="description">
			<div><%= LanguageUtil.get(pageContext, "description") %></div>
			<div id="descriptionContent"></div>
		</div>
		<div id="addCustomPageButton">
			<aui:button type="submit" value="custompages-create-page" />
		</div>
	</aui:form> 
	
</aui:layout>

<aui:script>
AUI().use('aui-base',
	    'aui-datatable',
	    'datatable-sort',
	    'datatable-paginator',
	    'liferay-menu',
	    function(A) {    
			var fieldWrapper = A.one('.field-wrapper');
			fieldWrapper.on('click',function(){setDescription();});
	    }
	);
	
function setDescription(){
	AUI().use('aui-base',function(A){
	var inputs = A.one('div#templateChoice').all('input');
	for (var i = 0; i < inputs.size(); i++){
		var item = inputs.item(i);
		if (item.get("type") === 'radio' && item.get("checked") === true){
			var descriptionDiv = A.one('#descriptionContent');
			var description = A.one('#' + item.get("id") + '_description').get("value");
			descriptionDiv.set('innerHTML',description);
		}
	}});
}

function createCustomPage(event) {
    event.preventDefault();
    AUI().use('aui-io-request-deprecated', 'aui-loading-mask-deprecated', 'autocomplete,io-upload-iframe', 'json-parse', function(A) {
        var form = A.one('#<portlet:namespace />form');
        var loadingMask = new A.LoadingMask({
            'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "custompages-creating-custom-page") %>',
            target: A.one('.popup-layout')
        });

        loadingMask.show();

        A.io.request(
            '<portlet:actionURL name="createCustomPage"></portlet:actionURL>', {
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

setDescription();
</aui:script>
