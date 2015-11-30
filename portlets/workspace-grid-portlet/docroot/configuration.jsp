<%@ include file="/init.jsp" %>

<liferay-portlet:actionURL portletConfiguration="true" var="saveColorsURL" >
	<portlet:param name="<%=Constants.CMD%>" value="saveColors" />
</liferay-portlet:actionURL>
<liferay-portlet:actionURL portletConfiguration="true" var="resetColorsURL">
	<portlet:param name="<%=Constants.CMD%>" value="resetColors" />
</liferay-portlet:actionURL>

<%
	List<LayoutSetPrototype> prototypes = LayoutSetPrototypeServiceUtil.search(themeDisplay.getCompanyId(), true, null);
%>


<form id="workspaceColorForm" action="<%= saveColorsURL %>" method="post" name="fm">

<label for="noTemplate" ><%= LanguageUtil.get(pageContext, "no-template") %></label>
<input id="noTemplate" class="colorPickerInput form-control" type="text" readonly
	value="<%=portletPreferences.getValue(WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, WorkspaceGridPortlet.DEFAULT_COLOR)%>"  
		name="<portlet:namespace /><%= WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE %>"  
	style="background-color:<%=portletPreferences.getValue(WorkspaceGridPortlet.WORKSPACE_COLOR + WorkspaceGridPortlet.NO_TEMPLATE, WorkspaceGridPortlet.DEFAULT_COLOR)%>;"/>

<%  for (LayoutSetPrototype prototype : prototypes){%>

	<label for="<%= prototype.getUuid() %>"><%= prototype.getName(themeDisplay.getLocale())%></label>
	<input id="<%= prototype.getUuid() %>" class="colorPickerInput form-control" type="text" readonly 
		value="<%=portletPreferences.getValue(WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), WorkspaceGridPortlet.DEFAULT_COLOR)%>"  
		name="<portlet:namespace /><%= WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid() %>"
		style="background-color:<%=portletPreferences.getValue(WorkspaceGridPortlet.WORKSPACE_COLOR + prototype.getUuid(), WorkspaceGridPortlet.DEFAULT_COLOR)%>;"/>

<% }%>

    <aui:button-row>
       <aui:button type="submit" onClick="submitForm(0)" />
       <aui:button type="submit" value="reset" onClick="submitForm(1)"></aui:button>
    </aui:button-row>
</form>

<aui:script>
AUI().use(
		  'aui-color-picker-popover',
		  function(A) {
		    var colorPicker = new A.ColorPickerPopover(
		      {
		        trigger: '.colorPickerInput',
		        position: 'bottom',
		        zIndex: 3
		      }
		    ).render();

		    colorPicker.on('select',
		      function(event) {
		        event.trigger.setStyle('backgroundColor', event.color);
		        event.trigger.set("value",event.color);
		      }
		    );
		  }
		);
function submitForm(action){
	var A = new AUI();
	  if(action==0){
	     A.one('#workspaceColorForm').set('action',"<%=saveColorsURL%>");
	  }else{
	     A.one('#workspaceColorForm').set('action',"<%=resetColorsURL%>");
	  }
	}
</aui:script>