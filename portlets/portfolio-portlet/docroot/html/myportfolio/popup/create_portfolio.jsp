<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<aui:layout cssClass="popup-layout">

	<div class="message-container" id="<portlet:namespace />messageContainer"></div>

	<aui:form name="form" class="form" method="post" onSubmit="event.preventDefault();">
		<aui:input id="portfolioNameInput" name="portfolioName" type="text" label="portfolio-name" />
		<aui:field-wrapper name="template" >
			<aui:input checked="<%= true %>" inlineLabel="right" name="template" type="radio" value="<%= PortfolioStatics.WIKI_LAYOUT_PROTOTYPE %>" label="wiki"  />
			<aui:input inlineLabel="right" name="template" type="radio" value="<%= PortfolioStatics.BLOG_LAYOUT_PROTOTYPE %>" label="blog"  />
			<aui:input inlineLabel="right" name="template" type="radio" value="<%= PortfolioStatics.CDP_LAYOUT_PROTOTYPE %>" label="portfolio-cdp"  />
			<aui:input inlineLabel="right" name="template" type="radio" value="<%= PortfolioStatics.EMPTY_LAYOUT_PROTOTYPE %>" label="portfolio-empty-page"  />
		</aui:field-wrapper>
		<aui:button type="submit" value="portfolio-add-portfolio" />
	</aui:form> 
	
</aui:layout>

<aui:script use="aui-io-request-deprecated,aui-loading-mask-deprecated,autocomplete,io-upload-iframe,json-parse">
var form = A.one('#<portlet:namespace />form');

form.on(
	'submit',
	function(event) {
		
		var loadingMask = new A.LoadingMask(
			{
				'strings.loading': '<%= UnicodeLanguageUtil.get(pageContext, "portfolio-creating-Portfolio") %>',
				target: A.one('.popup-layout')
			}
		);
		
		loadingMask.show();

		A.io.request(
				'<portlet:actionURL name="createPortfolio"></portlet:actionURL>',
			{
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
