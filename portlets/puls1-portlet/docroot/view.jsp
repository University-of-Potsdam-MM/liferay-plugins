<%@page import="java.util.Map"%>
<%@page import="model.CatalogEntry"%>
<%@page import="soap.SoapMessageParser"%>
<%@page import="soap.SoapClient"%>
<%@page import="model.Course"%>
<%@page import="org.w3c.dom.Node"%>
<%@ page import="javax.portlet.PortletPreferences"%>
<%@ page import="java.util.LinkedHashMap"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />

<liferay-ui:error key="error"
	message="Ups, PULS scheint nicht erreichbar zu sein." />

<portlet:actionURL var="catalogRoot" name="getCatalogRoot" >
	<portlet:param name="mvcPath" value="/view.jsp" />
</portlet:actionURL>


<c:set var="currentEntry"
	value="${renderRequest.getPortletSession().getAttribute('currentEntry')}" />
<c:choose>
	<c:when test="${currentEntry == null}">
		<a href="${catalogRoot}">Vorlesungsverzeichnis abrufen</a>
	</c:when>
	<c:otherwise>
		<c:set var="space" value="" />
		<table class="table table-striped table-bordered">
			<c:forEach items="${currentEntry.getParentList()}" var="entry">
				<portlet:actionURL var="catalogEntry" name="getCatalogEntry">
					<portlet:param name="mvcPath" value="/view.jsp" />
					<portlet:param name="entryId" value="${entry.id}" />
				</portlet:actionURL>
				<tr>
					<td><c:out value="${space}" /><a href="${catalogEntry}">${entry.name}</a></td>
				</tr>
				<c:set var="space" value="${space}-" />
			</c:forEach>
			<portlet:actionURL var="currentCatalogEntry" name="getCatalogEntry">
				<portlet:param name="mvcPath" value="/view.jsp" />
				<portlet:param name="entryId" value="${currentEntry.id}" />
			</portlet:actionURL>
			<tr>
				<td><c:out value="${space}" /><a href="${currentCatalogEntry}">${currentEntry.name}</a></td>
			</tr>
			<c:choose>
				<c:when test="${currentEntry.isCourse()}">
					<tr>
						<td><jsp:include page="/html/courseDetails.jsp" /></td>
					</tr>
				</c:when>
				<c:otherwise>
					<c:set var="space" value="${space}-" />
					<c:choose>
						<c:when test="${currentEntry.hasChildren()}">
							<c:forEach items="${currentEntry.getChildrenIterator()}"
								var="entry">
								<portlet:actionURL var="catalogEntry" name="getCatalogEntry">
									<portlet:param name="mvcPath" value="/view.jsp" />
									<portlet:param name="entryId" value="${entry.id}" />
								</portlet:actionURL>
								<tr>
									<td><c:out value="${space}" /><a href="${catalogEntry}">${entry.name}</a></td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr>
								<td><c:out value="${space}" /> Keine weiteren Einträge!</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</table>
	</c:otherwise>
</c:choose>
