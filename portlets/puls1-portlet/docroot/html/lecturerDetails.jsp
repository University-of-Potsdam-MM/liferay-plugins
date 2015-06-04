<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />
<c:set var="currentLecturer"
	value="${renderRequest.getPortletSession().getAttribute('currentLecturer')}" />
<table>
	<c:if test="${currentLecturer.lecturerTitle != null}">
		<tr>
			<td>Titel:</td>
			<td><c:out value="${currentLecturer.lecturerTitle}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.lecturerFirstname != null}">
		<tr>
			<td>Vorname:</td>
			<td><c:out value="${currentLecturer.lecturerFirstname}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.lecturerLastname != null}">
		<tr>
			<td>Nachname:</td>
			<td><c:out value="${currentLecturer.lecturerLastname}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.gender != null}">
		<tr>
			<td>Geschlecht:</td>
			<td><c:out value="${currentLecturer.gender}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.officeHours != null}">
		<tr>
			<td>Sprechzeiten:</td>
			<td><c:out value="${currentLecturer.officeHours}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.staffRole != null}">
		<tr>
			<td>Aufgabe:</td>
			<td><c:out value="${currentLecturer.staffRole}" /></td>
		</tr>
	</c:if>
	<c:if test="${currentLecturer.lecturerContact != null}">
		<c:if test="${currentLecturer.lecturerContact.phone != null}">
			<tr>
				<td>Telefon:</td>
				<td><c:out value="${currentLecturer.lecturerContact.phone}" /></td>
			</tr>
		</c:if>
		<c:if test="${currentLecturer.lecturerContact.fax != null}">
			<tr>
				<td>Fax:</td>
				<td><c:out value="${currentLecturer.lecturerContact.fax}" /></td>
			</tr>
		</c:if>
		<c:if test="${currentLecturer.lecturerContact.email != null}">
			<tr>
				<td>E-Mail:</td>
				<td><c:out value="${currentLecturer.lecturerContact.email}" /></td>
			</tr>
		</c:if>
		<c:if test="${currentLecturer.lecturerContact.hyperlink != null}">
			<tr>
				<td>Webseite:</td>
				<td><c:out value="${currentLecturer.lecturerContact.hyperlink}" /></td>
			</tr>
		</c:if>
	</c:if>
</table>