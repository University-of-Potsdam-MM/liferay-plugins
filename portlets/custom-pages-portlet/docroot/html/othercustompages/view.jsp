<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<portlet:renderURL var="tabURL"/>
 
<%
	String tab = ParamUtil.getString(request, "targetTab", "1"); 
	String tabNames = LanguageUtil.get(pageContext, "custompages-for-me") + "," + LanguageUtil.get(pageContext, "custompages-portalwide");
	String redirect = PortalUtil.getCurrentURL(renderRequest); 
%>

<liferay-ui:tabs names="<%= tabNames %>" url="<%=tabURL.toString()%>" param="targetTab" tabsValues="1,2" >
 
    <c:if test='<%= tab.equalsIgnoreCase("1")%>' >      
        <jsp:include page="/html/othercustompages/custompages_for_user.jsp" flush="true" />
    </c:if>
     
    <c:if test='<%= tab.equalsIgnoreCase("2")%>' >     
        <jsp:include page="/html/othercustompages/global_custompages.jsp" flush="true" /> 
    </c:if>
     
</liferay-ui:tabs>