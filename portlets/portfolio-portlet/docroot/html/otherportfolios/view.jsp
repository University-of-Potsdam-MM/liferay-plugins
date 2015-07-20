
<%@page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@ include file="/html/init.jsp"%>

<portlet:defineObjects /> 
<liferay-theme:defineObjects />

<portlet:renderURL var="tabURL"/>
 
<%
	String tab = ParamUtil.getString(request, "myParam", "1"); 
	String tabNames = LanguageUtil.get(pageContext, "portfolio-for-me") + "," + LanguageUtil.get(pageContext, "portfolio-portalwide");
	String redirect = PortalUtil.getCurrentURL(renderRequest); 
%>

<liferay-ui:tabs names="<%= tabNames %>" url="<%=tabURL.toString()%>" param="myParam" tabsValues="1,2" >
 
    <c:if test='<%= tab.equalsIgnoreCase("1")%>' >      
        <jsp:include page="/html/otherportfolios/portfolios_for_user.jsp" flush="true" />
    </c:if>
     
    <c:if test='<%= tab.equalsIgnoreCase("2")%>' >     
        <jsp:include page="/html/otherportfolios/global_portfolios.jsp" flush="true" /> 
    </c:if>
     
</liferay-ui:tabs>

