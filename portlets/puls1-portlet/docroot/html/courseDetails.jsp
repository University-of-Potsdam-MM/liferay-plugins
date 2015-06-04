<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<c:set var="currentEntry"
	value="${renderRequest.getPortletSession().getAttribute('currentEntry')}" />
<table>
	<c:if
		test="${currentEntry.facility != null && currentEntry.facility.facilityName != null}">
		<tr>
			<td>Einrichtung:</td>
			<td><c:out value="${currentEntry.facility.facilityName}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.courseNumber != null}">
		<tr>
			<td>Modulnummer:</td>
			<td><c:out value="${currentEntry.courseNumber}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.semester != null}">
		<tr>
			<td>Semester:</td>
			<td><c:out value="${currentEntry.semester}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.sws != null}">
		<tr>
			<td>SWS:</td>
			<td><c:out value="${currentEntry.sws}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.language != null}">
		<tr>
			<td>Sprache:</td>
			<td><c:out value="${currentEntry.language}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.courseLinks != null}">
		<tr>
			<td>Links:</td>
			<td><c:forEach items="${currentEntry.courseLinks}" var="link">
					<a href="${link.link}">${link.linkName}</a>
					<br>
				</c:forEach></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.enrolmentBegin != null}">
		<tr>
			<td>Belegungsbeginn:</td>
			<td><c:out value="${currentEntry.enrolmentBegin}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.enrolmentEnd != null}">
		<tr>
			<td>Belegungsende:</td>
			<td><c:out value="${currentEntry.enrolmentEnd}" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.comment != null}">
		<tr>
			<td>Kommentar:</td>
			<td><c:out value="${currentEntry.comment}" escapeXml="false" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.literature != null}">
		<tr>
			<td>Literatur:</td>
			<td><c:out value="${currentEntry.literature}" escapeXml="false" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.qualification != null}">
		<tr>
			<td>Vorrausetzung:</td>
			<td><c:out value="${currentEntry.qualification}"
					escapeXml="false" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.examination != null}">
		<tr>
			<td>Leistungsnachweis:</td>
			<td><c:out value="${currentEntry.examination}" escapeXml="false" /></td>
		</tr>
	</c:if>

	<c:if test="${currentEntry.events != null}">
		<tr>
			<td>Events:</td>
			<c:set var="i" value="${1}" />
			<td><c:forEach items="${currentEntry.events}" var="event">
			Termin <c:out value="${i}"></c:out>
					<table>
						<c:if test="${event.group != null}">
							<tr>
								<td>Gruppe:</td>
								<td><c:out value="${event.group}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.startDate != null}">
							<tr>
								<td>Start:</td>
								<td><c:out value="${event.startDate}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.endDate != null}">
							<tr>
								<td>Ende:</td>
								<td><c:out value="${event.endDate}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.startTime != null}">
							<tr>
								<td>Startzeit:</td>
								<td><c:out value="${event.startTime}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.endTime != null}">
							<tr>
								<td>Ende:</td>
								<td><c:out value="${event.endTime}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.day != null}">
							<tr>
								<td>Tag:</td>
								<td><c:out value="${event.day}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.rhythm != null}">
							<tr>
								<td>Rhythmus:</td>
								<td><c:out value="${event.rhythm}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.building != null}">
							<tr>
								<td>Gebäude:</td>
								<td><c:out value="${event.building}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.room != null}">
							<tr>
								<td>Raum:</td>
								<td><c:out value="${event.room}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.roomType != null}">
							<tr>
								<td>Raumart:</td>
								<td><c:out value="${event.roomType}" /></td>
							</tr>
						</c:if>
						<c:if test="${event.comment != null}">
							<tr>
								<td>Kommentar:</td>
								<td><c:out value="${event.comment}" escapeXml="false" /></td>
							</tr>
						</c:if>
						<c:if test="${event.lecturers != null}">
							<tr>
								<td>Dozenten:</td>
								<td><c:forEach items="${event.lecturers}" var="lecturer">
										<a href="#" class="lecturerLink" id="${lecturer.lecturerId}">${lecturer.getFormattedName()}</a>
										<br>
									</c:forEach></td>
							</tr>
						</c:if>
					</table>
					<br>
					<c:set var="i" value="${i+1}" />
				</c:forEach></td>
		</tr>
	</c:if>

</table>

<aui:script>
	AUI()
			.use(
					'aui-base',
					'aui-io-plugin-deprecated',
					'liferay-util-window',

					function(A) {
						A
								.all('.lecturerLink')
								.on(
										'click',
										function(event) {
											event.preventDefault();

											var actionURL = Liferay.PortletURL
													.createActionURL();
											actionURL
													.setWindowState("<%=LiferayWindowState.EXCLUSIVE.toString() %>");
											actionURL
													.setPortletMode("<%=LiferayPortletMode.VIEW %>");
											actionURL.setParameter(
													"lecturerId", event.target
															.get("id"));
											actionURL.setParameter("entryId",
													'${currentEntry.id}');
											actionURL
													.setPortletId("<%=themeDisplay.getPortletDisplay().getId() %>");
											actionURL
													.setName("showLecturerDetails");

											var popUpWindow = Liferay.Util.Window
													.getWindow(
															{
																dialog : {
																	centered : true,
																	constrain2view : true,
																	//cssClass: 'yourCSSclassName',
																	modal : true,
																	resizable : false,
																	width : 475
																}
															})
													.plug(A.Plugin.IO, {
														autoLoad : false
													}).render();
											popUpWindow.show();
											popUpWindow.titleNode
													.html("Informationen zum Dozenten");
											popUpWindow.io.set('uri', actionURL
													.toString());
											popUpWindow.io.start();

										});
					});
</aui:script>
	






