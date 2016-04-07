<%@ include file="/html/init.jsp"%>

<div class="navbar-search">
   <div class="taglib-search-toggle">

  	<% if (layout.isPublicLayout()) { %>
      <div class="form-search">
         <div class="input-append" id="<portlet:namespace />searchsimple">
            <div class="advanced-search"> 
	            <input class="search-query span9" id="<portlet:namespace />filterInput" name="_127_keywords" placeholder="<%= LanguageUtil.get(pageContext, "custompages-filter-placeholder") %>" type="text" value=""> 
            </div>
         </div>
      </div>
  	
  	<% } else { %>
      <div class="form-search">
         <div class="input-append" id="<portlet:namespace />searchsimple">
            <div class="advanced-search"> 
	            <input class="search-query span9" id="<portlet:namespace />filterInput" name="_127_keywords" placeholder="<%= LanguageUtil.get(pageContext, "custompages-filter-placeholder") %>" type="text" value=""> 
	            <span class="toggle-advanced" id="<portlet:namespace />searchtoggleAdvanced"> 
	            	<span>Optionen</span>
		            <i class="icon-search"></i> 
		            <i class="caret"></i> 
	            </span> 
            </div>
         </div>
      </div>
      <div class="popover taglib-search-toggle-advanced yui3-widget yui3-widget-positioned yui3-widget-modal bottom yui3-widget-stacked" id="<portlet:namespace />searchadvanced" style="display: none; width: 248px; left: -17px; top: 19.8281px; z-index: 430;">
			<span><%= LanguageUtil.get(pageContext, "custompages-page-type") %></span>
			<aui:input id="inputPageTypeNone" name="custompages-page-type-normal-page" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPageTypeNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPageTypePortfolioPage" name="custompages-page-type-portfolio-page" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPageTypePortfolioPageCheckbox\", \"true\") %>"></aui:input>
			<span><%= LanguageUtil.get(pageContext, "custompages-publishment-column") %></span>
			<aui:input id="inputPublishmentNone" name="custompages-no-publishment" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPublishmentGlobal" name="custompages-global" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentGlobalCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPublishmentIndividuel" name="custompages-individual" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentIndividuelCheckbox\", \"true\") %>"></aui:input>
			<span><%= LanguageUtil.get(pageContext, "custompages-feedback-column") %></span>
			<aui:input id="inputFeedbackNone" name="custompages-no-publishment" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputFeedbackRequested" name="custompages-requested" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackRequestedCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputFeedbackDelivered" name="custompages-delivered" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackDeliveredCheckbox\", \"true\") %>"></aui:input>
			<span id="<portlet:namespace />inputVisibleSpan"><%= LanguageUtil.get(pageContext, "custompages-visible") %></span>
			<aui:input id="inputVisible" name="custompages-visible" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputVisibleCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputInvisible" name="custompages-hidden" type="checkbox" onChange="<%= renderResponse.getNamespace()  + \"filterTable(this)\" %>" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputInvisibleCheckbox\", \"false\") %>"></aui:input>
			<div class="arrow"></div>
	  </div>
<% } %>
   </div>
</div>

<script type="text/javascript">
   AUI().use("aui-popover", "event-key", function(a) {
	    (function() {
	        var e;
	        var h = a.one("#<portlet:namespace />searchsimple");
	        var g = a.one("#<portlet:namespace />searchadvanced");
	        var b = a.one("#<portlet:namespace />searchtoggleAdvanced");
	        var f = a.one("#<portlet:namespace />filterInput");

	        function i() {
	            if (!e) {
	                e = new a.Popover({
	                    align: {
	                        node: b,
	                        points: [a.WidgetPositionAlign.TL, a.WidgetPositionAlign.BL]
	                    },
	                    boundingBox: g,
	                    position: "bottom",
	                    visible: false,
	                    width: 248,
	                    zIndex: Liferay.zIndex.ALERT
	                })
	            }
	            return e
	        }

	        function c(k) {
	            e = i().render();
	            var m = e.get("visible");
	            e.set("visible", !m);
	            if (m) {
	                f.focus()
	            } else {
	                var l = g.one("input[type=text]");
	                if (l) {
	                    l.focus()
	                }
	            }
	            k.preventDefault()
	        }
	        if (b)
	        	b.on("click", c);
	        f.on("key", c, "down:38,40")
	    })()
	});

</script> 
<aui:script>
var <portlet:namespace />isMyCustomPagesTable = false;
var <portlet:namespace />tableData;
var <portlet:namespace />table;
var <portlet:namespace />inputPageTypeNone;
var <portlet:namespace />inputPageTypePortfolioPage;
var <portlet:namespace />inputPublishmentNone;
var <portlet:namespace />inputPublishmentGlobal;
var <portlet:namespace />inputPublishmentIndividuel;
var <portlet:namespace />inputFeedbackNone;
var <portlet:namespace />inputFeedbackRequested;
var <portlet:namespace />inputFeedbackDelivered;
var <portlet:namespace />inputVisible;
var <portlet:namespace />inputVisibleSpan;
var <portlet:namespace />inputInvisible;
var <portlet:namespace />filterInput;

AUI().use('aui-base', function(A) {
	<portlet:namespace />inputPageTypeNone = A.one('#<portlet:namespace />inputPageTypeNone');
	<portlet:namespace />inputPageTypePortfolioPage = A.one('#<portlet:namespace />inputPageTypePortfolioPage');
	<portlet:namespace />inputPublishmentNone = A.one('#<portlet:namespace />inputPublishmentNone');
	<portlet:namespace />inputPublishmentGlobal = A.one('#<portlet:namespace />inputPublishmentGlobal');
	<portlet:namespace />inputPublishmentIndividuel = A.one('#<portlet:namespace />inputPublishmentIndividuel');
	<portlet:namespace />inputFeedbackNone = A.one('#<portlet:namespace />inputFeedbackNone');
	<portlet:namespace />inputFeedbackRequested = A.one('#<portlet:namespace />inputFeedbackRequested');
	<portlet:namespace />inputFeedbackDelivered = A.one('#<portlet:namespace />inputFeedbackDelivered');
	<portlet:namespace />inputVisible = A.one('#<portlet:namespace />inputVisible');
	<portlet:namespace />inputVisibleSpan = A.one('#<portlet:namespace />inputVisibleSpan');
	<portlet:namespace />inputInvisible = A.one('#<portlet:namespace />inputInvisible');
	<portlet:namespace />inputInvisibleSpan = A.one('#<portlet:namespace />inputInvisibleSpan');
	<portlet:namespace />filterInput = A.one('#<portlet:namespace />filterInput');
	
	<portlet:namespace />filterInput.on('keyup', function(e) {
		<portlet:namespace />filterTable();
    });
	  
});

function <portlet:namespace />initFilter(customPageTable, myCustomPagesTable){
	<portlet:namespace />table = customPageTable;
	<portlet:namespace />tableData = customPageTable.data;
	<portlet:namespace />isMyCustomPagesTable = myCustomPagesTable;
	if (!myCustomPagesTable){
		<portlet:namespace />inputFeedbackRequested.ancestor().get('lastChild').set('textContent','<%= LanguageUtil.get(pageContext, "custompages-feedback-requested-filter") %>');
		<portlet:namespace />inputFeedbackDelivered.ancestor().get('lastChild').set('textContent','<%= LanguageUtil.get(pageContext, "custompages-feedback-delivered-filter") %>');
		if (<portlet:namespace />inputPublishmentNone.ancestor())
			<portlet:namespace />inputPublishmentNone.ancestor().hide();
	}
	else{
		if (<portlet:namespace />inputVisibleSpan){
			<portlet:namespace />inputVisibleSpan.hide();
			<portlet:namespace />inputVisible.ancestor().hide();
			<portlet:namespace />inputInvisible.ancestor().hide();
		}
	}
}

function <portlet:namespace />updateFilter(data){
	<portlet:namespace />tableData = data;
}

function <portlet:namespace />filterTable(element){
	AUI().use('aui-base', function(A) {
		var checkbox = A.one(element);
		if (checkbox != null){
			Liferay.Store(element.id,checkbox.attr('checked').toString());
		}
        var filteredData = <portlet:namespace />tableData.filter({
            asList: true
        }, function(list) {
        	var result = true;
        	<% if (!layout.isPublicLayout()) {%>
	        	var hasCustomPageTypeNone = list.get('customPageType') === <%= CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE%>;
	        	var hasCustomPageTypePortfolioPage = list.get('customPageType') === <%= CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE%>;
	        	var isPublishGlobal = list.get('isGlobal');
	        	var isPublishIndividual = list.get('isIndividual') && !isPublishGlobal;
	        	var isPublishNone = !isPublishGlobal && !isPublishIndividual;
	        	var feedbackRequested = list.get('inFeedbackProcess');
	        	var feedbackDelivered = list.get('feedbackDelivered');
	        	var feedbackUnrequested = !feedbackRequested && !feedbackDelivered;
	        	var isHidden = list.get('hidden');
	        	result = result && 
					((hasCustomPageTypeNone && (<portlet:namespace />inputPageTypeNone.get('value') === 'true')) ||
					(hasCustomPageTypePortfolioPage && (<portlet:namespace />inputPageTypePortfolioPage.get('value') === 'true')));
	        	result = result && 
	    			((isPublishNone && (<portlet:namespace />inputPublishmentNone.get('value') === 'true')) || 
	        		(isPublishGlobal &&(<portlet:namespace />inputPublishmentGlobal.get('value') === 'true')) || 
	        		(isPublishIndividual && (<portlet:namespace />inputPublishmentIndividuel.get('value') === 'true')));
	        	result = result && 
					((feedbackUnrequested && (<portlet:namespace />inputFeedbackNone.get('value') === 'true')) || 
		    		(feedbackRequested &&(<portlet:namespace />inputFeedbackRequested.get('value') === 'true')) || 
		    		(feedbackDelivered && (<portlet:namespace />inputFeedbackDelivered.get('value') === 'true')));
	        	if (!<portlet:namespace />isMyCustomPagesTable){
	        		result = result && 
	        		((isHidden && (<portlet:namespace />inputInvisible.get('value') === 'true')) ||
	        		(!isHidden && (<portlet:namespace />inputVisible.get('value') === 'true')));
	        	}
	        <% } %>
        	var filterInputValue = <portlet:namespace />filterInput.get("value").toLowerCase();
        	if (filterInputValue !== ""){
        		result = result && list.get('title').toLowerCase().includes(filterInputValue);
        	}
            return result;
        });
        <portlet:namespace />table.set('data', filteredData);
	});
}
</aui:script>