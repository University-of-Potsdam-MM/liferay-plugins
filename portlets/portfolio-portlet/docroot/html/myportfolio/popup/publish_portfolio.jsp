<%@ include file="/html/myportfolio/popup/popup.jsp"%> 

<!-- TODO: Erfolgsmeldung beim Hinzufügen? -->

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
		<aui:validator name="custom" errorMessage="portfolio-already-published-to-user">
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

<aui:form name="publishPortfolioForm" id="publishPortfolioForm" method="post">
	<aui:button type="submit" value="portfolio-publish" onClick="publishPortfolioToUsers()"></aui:button>
	<aui:button type="submit" value="portfolio-publish-portalwide" onClick="publishPortfolioGlobal()"></aui:button>
</aui:form>

<table hidden="true">
	<tr id="addableRow" >
		<td id="nameField">Cannot sort tables</td>
		<td><liferay-ui:icon id="removeIcon" image="delete" url="#" /></td>
	</tr>
</table>

