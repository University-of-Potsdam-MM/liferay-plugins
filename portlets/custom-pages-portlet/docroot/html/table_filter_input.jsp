<%@ include file="/html/init.jsp"%>

<div class="navbar-search">
   <div class="taglib-search-toggle">
      <div class="form-search">
         <div class="input-append" id="<portlet:namespace />searchsimple">
            <div class="advanced-search"> 
	            <input class="search-query span9" id="<portlet:namespace />filterInput" name="_127_keywords" placeholder="<%= LanguageUtil.get(pageContext, "custompages-filter-placeholder") %>" type="text" value=""> 
	            <span class="toggle-advanced" id="<portlet:namespace />searchtoggleAdvanced"> 
		            <i class="icon-search"></i> 
		            <i class="caret"></i> 
	            </span> 
            </div>
         </div>
      </div>
      <div class="popover taglib-search-toggle-advanced yui3-widget yui3-widget-positioned yui3-widget-modal bottom yui3-widget-stacked" id="<portlet:namespace />searchadvanced" style="display: none; width: 248px; left: -17px; top: 19.8281px; z-index: 430;">
			<span><%= LanguageUtil.get(pageContext, "custompages-page-type") %></span>
			<aui:input id="inputPageTypeNone" name="custompages-page-type-normal-page" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPageTypeNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPageTypePortfolioPage" name="custompages-page-type-portfolio-page" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPageTypePortfolioPageCheckbox\", \"true\") %>"></aui:input>
			<span><%= LanguageUtil.get(pageContext, "custompages-publishment-column") %></span>
			<aui:input id="inputPublishmentNone" name="custompages-no-publishment" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPublishmentGlobal" name="global" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentGlobalCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputPublishmentIndividuel" name="custompages-individual" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputPublishmentIndividuelCheckbox\", \"true\") %>"></aui:input>
			<span><%= LanguageUtil.get(pageContext, "custompages-feedback-column") %></span>
			<aui:input id="inputFeedbackNone" name="none" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackNoneCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputFeedbackRequested" name="custompages-requested" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackRequestedCheckbox\", \"true\") %>"></aui:input>
			<aui:input id="inputFeedbackDelivered" name="custompages-delivered" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputFeedbackDeliveredCheckbox\", \"true\") %>"></aui:input>
			<span id="<portlet:namespace />inputVisibilitySpan"><%= LanguageUtil.get(pageContext, "custompages-visible") %></span>
			<aui:input id="inputVisibility" name="custompages-hidden" type="checkbox" onChange="filterTable(this)" value="<%= SessionClicks.get(request, renderResponse.getNamespace() + \"inputVisibilityCheckbox\", \"false\") %>"></aui:input>
			<div class="arrow"></div>
	</div>
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
	        b.on("click", c);
	        f.on("key", c, "down:38,40")
	    })()
	});

</script> 
<aui:script>
var isMyCustomPagesTable = false;
var tableData;
var inputPageTypeNone;
var inputPageTypePortfolioPage;
var inputPublishmentNone;
var inputPublishmentGlobal;
var inputPublishmentIndividuel;
var inputFeedbackNone;
var inputFeedbackRequested;
var inputFeedbackDelivered;
var inputVisibility;
var inputVisibilitySpan;
var filterInput;

AUI().use('aui-base', function(A) {
	inputPageTypeNone = A.one('#<portlet:namespace />inputPageTypeNone');
	inputPageTypePortfolioPage = A.one('#<portlet:namespace />inputPageTypePortfolioPage');
	inputPublishmentNone = A.one('#<portlet:namespace />inputPublishmentNone');
	inputPublishmentGlobal = A.one('#<portlet:namespace />inputPublishmentGlobal');
	inputPublishmentIndividuel = A.one('#<portlet:namespace />inputPublishmentIndividuel');
	inputFeedbackNone = A.one('#<portlet:namespace />inputFeedbackNone');
	inputFeedbackRequested = A.one('#<portlet:namespace />inputFeedbackRequested');
	inputFeedbackDelivered = A.one('#<portlet:namespace />inputFeedbackDelivered');
	inputVisibility = A.one('#<portlet:namespace />inputVisibility');
	inputVisibilitySpan = A.one('#<portlet:namespace />inputVisibilitySpan');
	filterInput = A.one('#<portlet:namespace />filterInput');
	
	  filterInput.on('keyup', function(e) {
    	filterTable();
    });
	  
});

function initFilter(customPageTable, myCustomPagesTable){
	table = customPageTable;
	tableData = customPageTable.data;
	this.isMyCustomPagesTable = myCustomPagesTable;
	if (!myCustomPagesTable){
		inputPublishmentNone.ancestor().hide();
	}
	else{
		inputVisibilitySpan.hide();
		inputVisibility.ancestor().hide();
	}
}

function updateFilter(data){
	tableData = data;
}

function filterTable(element){
	AUI().use('aui-base', function(A) {
		var checkbox = A.one(element);
		if (checkbox != null){
			Liferay.Store(element.id,checkbox.attr('checked').toString());
		}
        var filteredData = tableData.filter({
            asList: true
        }, function(list) {
        	var hasCustomPageTypeNone = list.get('customPageType') === <%= CustomPageStatics.CUSTOM_PAGE_TYPE_NORMAL_PAGE%>;
        	var hasCustomPageTypePortfolioPage = list.get('customPageType') === <%= CustomPageStatics.CUSTOM_PAGE_TYPE_PORTFOLIO_PAGE%>;
        	var isPublishGlobal = list.get('isGlobal');
        	var isPublishIndividual = list.get('isIndividual') && !isPublishGlobal;
        	var isPublishNone = !isPublishGlobal && !isPublishIndividual;
        	var feedbackRequested = list.get('inFeedbackProcess');
        	var feedbackDelivered = list.get('feedbackDelivered');
        	var feedbackUnrequested = !feedbackRequested && !feedbackDelivered;
        	var result = true;
        	result = result && 
				((hasCustomPageTypeNone && (inputPageTypeNone.get('value') === 'true')) ||
				(hasCustomPageTypePortfolioPage && (inputPageTypePortfolioPage.get('value') === 'true')));
        	result = result && 
    			((isPublishNone && (inputPublishmentNone.get('value') === 'true')) || 
        		(isPublishGlobal &&(inputPublishmentGlobal.get('value') === 'true')) || 
        		(isPublishIndividual && (inputPublishmentIndividuel.get('value') === 'true')));
        	result = result && 
				((feedbackUnrequested && (inputFeedbackNone.get('value') === 'true')) || 
	    		(feedbackRequested &&(inputFeedbackRequested.get('value') === 'true')) || 
	    		(feedbackDelivered && (inputFeedbackDelivered.get('value') === 'true')));
        	if (!isMyCustomPagesTable){
        		result = result && (list.get('hidden') === (inputVisibility.get('value') === 'true'));
        	}
        	var filterInputValue = filterInput.get("value").toLowerCase();
        	if (filterInputValue !== ""){
        		result = result && list.get('title').toLowerCase().includes(filterInputValue);
        	}
            return result;
        });
        table.set('data', filteredData);
	});
}
</aui:script>