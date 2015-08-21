<%@page import="java.util.Locale"%>
<%@page import="java.util.Map"%>
<%@page import="com.liferay.portal.model.LayoutPrototype"%>

<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
	Map<String,LayoutPrototype> portfolioTemplates = JspHelper.getPortfolioLayoutPrototypes();
%>

<aui:layout cssClass="popup-layout">

	<div class="message-container" id="<portlet:namespace />messageContainer"></div>

	<aui:form name="form" class="form" method="post" onSubmit="createPortfolio(event);">
		<aui:input id="portfolioNameInput" name="portfolioName" type="text" label="portfolio-name">
	      	<aui:validator name="required" />
	    </aui:input>
	    
	 	<div id="templateChoice">
			<aui:field-wrapper name="template" >
				<% LayoutPrototype lp = portfolioTemplates.get(PortfolioStatics.BLOG_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMAN) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = portfolioTemplates.get(PortfolioStatics.WIKI_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMAN) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = portfolioTemplates.get(PortfolioStatics.CDP_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMAN) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				<% lp = portfolioTemplates.get(PortfolioStatics.EMPTY_LAYOUT_PROTOTYPE); %>
					<aui:input id="<%= String.valueOf(lp.getLayoutPrototypeId()) %>" checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= lp.getName(Locale.GERMAN) %>" label="<%= lp.getName(themeDisplay.getLocale()) %>" />
					<input id="<%= portletDisplay.getNamespace() + lp.getLayoutPrototypeId() + "_description" %>" hidden="true" value ="<%= lp.getDescription() %>"/>
				
			</aui:field-wrapper>
		</div>
		<div id="description">
			<div><%= LanguageUtil.get(pageContext, "description") %></div>
			<div id="descriptionContent"></div>
		</div>
		<div id="addPortfolioButton">
			<aui:button type="submit" value="portfolio-add-portfolio" />
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

function createPortfolio(event) {
    event.preventDefault();
    AUI().use('aui-io-request-deprecated', 'aui-loading-mask-deprecated', 'autocomplete,io-upload-iframe', 'json-parse', function(A) {
        var form = A.one('#<portlet:namespace />form');
        var loadingMask = new A.LoadingMask({
            'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "portfolio-creating-Portfolio") %>',
            target: A.one('.popup-layout')
        });

        loadingMask.show();

        A.io.request(
            '<portlet:actionURL name="createPortfolio"></portlet:actionURL>', {
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
