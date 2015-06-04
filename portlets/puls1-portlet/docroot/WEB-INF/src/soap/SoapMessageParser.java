package soap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBody;

import model.CatalogEntry;
import model.Course;
import model.CourseLink;
import model.Event;
import model.Facility;
import model.Lecturer;
import model.LecturerContact;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class SoapMessageParser {

	public static CatalogEntry parseScheduleRoot(SOAPBody body, Map<String, CatalogEntry> catalogMap) {
		NodeList nodeList = body.getElementsByTagName("rootNode");
		if (nodeList.getLength() > 0) {
			CatalogEntry root = parseCatalogEntry((Element) nodeList.item(0), null);
			catalogMap.put(root.getId(), root);
			nodeList = body.getElementsByTagName("childNode");
			if (parseCatalogEntries(nodeList, root, catalogMap))
				return root;
		}
		return null;
	}

	public static boolean parseCatalogEntries(SOAPBody body, CatalogEntry parent, Map<String, CatalogEntry> catalogMap) {
		NodeList catalogEntriesNodeList = body.getElementsByTagName("childNode");
		return parseCatalogEntries(catalogEntriesNodeList, parent, catalogMap);
	}

	private static boolean parseCatalogEntries(NodeList nodeList, CatalogEntry parent,
			Map<String, CatalogEntry> catalogMap) {
		if (nodeList.getLength() > 0) {
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node catalogEntryNode = nodeList.item(i);
				if (catalogEntryNode.getNodeType() == Node.ELEMENT_NODE) {
					CatalogEntry catalogEntry = parseCatalogEntry((Element) catalogEntryNode, parent);
					if (catalogEntry != null)
						catalogMap.put(catalogEntry.getId(), catalogEntry);
				}
			}
			return true;
		}
		return false;
	}

	private static CatalogEntry parseCatalogEntry(Element catalogEntryElement, CatalogEntry parent) {
		CatalogEntry catalogEntry = new CatalogEntry();
		if (catalogEntryElement.getElementsByTagName("headerId").getLength() == 0)
			return null;
		catalogEntry.setId(getElementAsString(catalogEntryElement, "headerId"));
		catalogEntry.setName(getElementAsString(catalogEntryElement, "headerName"));
		catalogEntry.setParent(parent);
		return catalogEntry;
	}

	public static boolean parseCourseEntries(SOAPBody body, CatalogEntry parent, Map<String, CatalogEntry> catalogMap) {
		NodeList courseNodeList = body.getElementsByTagName("course");
		if (courseNodeList.getLength() > 0) {
			for (int i = 0; i < courseNodeList.getLength(); i++) {
				Node catalogEntryNode = courseNodeList.item(i);
				if (catalogEntryNode.getNodeType() == Node.ELEMENT_NODE) {
					Course course = parseCourse((Element) catalogEntryNode, parent);
					if (course != null)
						catalogMap.put(course.getId(), course);
				}
			}
			return true;
		}
		return false;
	}

	private static Course parseCourse(Node courseNode, CatalogEntry parent) {
		Course course = new Course();
		Element courseElement = (Element) courseNode;
		course.setParent(parent);
		course.setId(getElementAsString(courseElement, "courseId"));
		course.setName(getElementAsString(courseElement, "courseName"));
		return course;
	}

	public static boolean parseCourseDetails(SOAPBody body, Course course) {
		NodeList courseNodeList = body.getElementsByTagName("course");
		if (courseNodeList.getLength() > 0) {
			Node catalogEntryNode = courseNodeList.item(0);
			if (catalogEntryNode.getNodeType() == Node.ELEMENT_NODE)
				addCourseDetails((Element) catalogEntryNode, course);
			return true;
		}
		return false;
	}

	private static void addCourseDetails(Node courseNode, Course course) {
		Element courseElement = (Element) courseNode;
		course.setFacility(parseFacility(courseElement));
		course.setId(getElementAsString(courseElement, "courseId"));
		course.setCourseNumber(getElementAsLong(courseElement, "courseNumber"));
		course.setName(getElementAsString(courseElement, "courseName"));
		course.setCourseType(getElementAsString(courseElement, "courseType"));
		course.setSemester(getElementAsString(courseElement, "semester"));
		course.setSws(getElementAsInteger(courseElement, "sws"));
		course.setLanguage(getElementAsString(courseElement, "language"));
		course.setEnrolmentBegin(getElementAsString(courseElement, "enrolmentBegin"));
		course.setEnrolmentEnd(getElementAsString(courseElement, "enrolmentEnd"));
		course.setCourseLinks(parseCourseLinks(courseElement));
		course.setComment(getElementAsString(courseElement, "comment"));
		course.setLiterature(getElementAsString(courseElement, "literature"));
		course.setQualification(getElementAsString(courseElement, "qualification"));
		course.setExamination(getElementAsString(courseElement, "examination"));
		course.setEvents(parseEvents(courseElement));
	}

	private static Facility parseFacility(Element courseElement) {
		Facility facility = new Facility();
		facility.setFacilityId(getElementAsString(courseElement, "facilityId"));
		facility.setFacilityName(getElementAsString(courseElement, "facilityName"));
		if (facility.getFacilityId() != null || facility.getFacilityName() != null)
			return facility;
		return null;
	}

	private static List<CourseLink> parseCourseLinks(Element courseElement) {
		NodeList courseLinksNodeList = courseElement.getElementsByTagName("courseLinks");
		if (courseLinksNodeList.getLength() > 0) {
			Element courseLinksElement = (Element) courseLinksNodeList.item(0);
			NodeList courseLinkNodeList = courseLinksElement.getElementsByTagName("courseLink");
			List<CourseLink> result = new ArrayList<CourseLink>();
			for (int i = 0; i < courseLinkNodeList.getLength(); i++) {
				Node courseLinkNode = courseLinkNodeList.item(i);
				if (courseLinkNode.getNodeType() == Node.ELEMENT_NODE) {
					result.add(parseCourseLink((Element) courseLinkNode));
				}
			}
			if (result.size() > 0)
				return result;
		}
		return null;
	}

	private static List<Event> parseEvents(Element courseElement) {
		NodeList eventsNodeList = courseElement.getElementsByTagName("events");
		if (eventsNodeList.getLength() > 0) {
			Element eventsElement = (Element) eventsNodeList.item(0);
			NodeList eventNodeList = eventsElement.getElementsByTagName("event");
			List<Event> result = new ArrayList<Event>();
			for (int i = 0; i < eventNodeList.getLength(); i++) {
				Node eventNode = eventNodeList.item(i);
				if (eventNode.getNodeType() == Node.ELEMENT_NODE) {
					result.add(parseEvent((Element) eventNode));
				}
			}
			if (result.size() > 0)
				return result;
		}
		return null;
	}

	private static Event parseEvent(Element eventElement) {
		Event event = new Event();
		event.setEventId(getElementAsString(eventElement, "courseId"));
		event.setGroupId(getElementAsInteger(eventElement, "groupId"));
		event.setGroup(getElementAsString(eventElement, "group"));
		event.setStartDate(getElementAsString(eventElement, "startDate"));
		event.setEndDate(getElementAsString(eventElement, "endDate"));
		event.setStartTime(getElementAsString(eventElement, "startTime"));
		event.setEndTime(getElementAsString(eventElement, "endTime"));
		event.setDay(getElementAsString(eventElement, "day"));
		event.setRhythm(getElementAsString(eventElement, "rhythm"));
		event.setBuilding(getElementAsString(eventElement, "building"));
		event.setRoom(getElementAsString(eventElement, "room"));
		event.setRoomType(getElementAsString(eventElement, "roomType"));
		event.setComment(getElementAsString(eventElement, "comment"));
		event.setLecturers(parseLecturers(eventElement));
		return event;
	}

	private static List<Lecturer> parseLecturers(Element eventElement) {
		NodeList lecturersNodeList = eventElement.getElementsByTagName("lecturers");
		if (lecturersNodeList.getLength() > 0) {
			Element lecturersElement = (Element) lecturersNodeList.item(0);
			NodeList lecturerNodeList = lecturersElement.getElementsByTagName("lecturer");
			List<Lecturer> result = new ArrayList<Lecturer>();
			for (int i = 0; i < lecturerNodeList.getLength(); i++) {
				Node lecturerNode = lecturerNodeList.item(i);
				if (lecturerNode.getNodeType() == Node.ELEMENT_NODE) {
					result.add(parseLecturer((Element) lecturerNode));
				}
			}
			if (result.size() > 0)
				return result;
		}
		return null;
	}

	private static Lecturer parseLecturer(Element lecturerElement) {
		Lecturer lecturer = new Lecturer();
		lecturer.setLecturerId(getElementAsString(lecturerElement, "lecturerId"));
		lecturer.setLecturerFirstname(getElementAsString(lecturerElement, "lecturerFirstname"));
		lecturer.setLecturerLastname(getElementAsString(lecturerElement, "lecturerLastname"));
		lecturer.setLecturerTitle(getElementAsString(lecturerElement, "lecturerTitle"));
		return lecturer;
	}

	public static Lecturer parseLecturerDetails(SOAPBody body) {
		NodeList lecturerNodeList = body.getElementsByTagName("lecturer");
		if (lecturerNodeList.getLength() > 0) {
			Lecturer lecturer = new Lecturer();
			Element lecturerElement = (Element) lecturerNodeList.item(0);
			lecturer.setLecturerId(getElementAsString(lecturerElement, "lecturerId"));
			lecturer.setLecturerFirstname(getElementAsString(lecturerElement, "lecturerFirstname"));
			lecturer.setLecturerLastname(getElementAsString(lecturerElement, "lecturerLastname"));
			lecturer.setGender(getElementAsString(lecturerElement, "gender"));
			lecturer.setOfficeHours(getElementAsString(lecturerElement, "officeHours"));
			lecturer.setStaffRole(getElementAsString(lecturerElement, "staffRole"));
			lecturer.setStatus(getElementAsString(lecturerElement, "status"));
			lecturer.setLecturerTitle(getElementAsString(lecturerElement, "lecturerTitel"));
			lecturer.setLecturerContact(parseLecturerContact(lecturerElement));
			return lecturer;
		}
		return null;
	}

	private static LecturerContact parseLecturerContact(Element lecturerElement) {
		NodeList lecturerContactNodeList = lecturerElement.getElementsByTagName("lecturerContact");
		if (lecturerContactNodeList.getLength() > 0) {
			Element lecturerContactElement = (Element) lecturerContactNodeList.item(0);
			LecturerContact lecturerContact = new LecturerContact();
			lecturerContact.setPhone(getElementAsString(lecturerContactElement, "phone"));
			lecturerContact.setFax(getElementAsString(lecturerContactElement, "fax"));
			lecturerContact.setEmail(getElementAsString(lecturerContactElement, "email"));
			lecturerContact.setHyperlink(getElementAsString(lecturerContactElement, "hyperlink"));
			return lecturerContact;
		}
		return null;
	}

	private static CourseLink parseCourseLink(Element courseLinkElement) {
		CourseLink courseLink = new CourseLink();
		courseLink.setLink(getElementAsString(courseLinkElement, "link"));
		courseLink.setLinkName(getElementAsString(courseLinkElement, "linkName"));
		return courseLink;
	}

	private static String getElementAsString(Element element, String elementName) {
		NodeList nodeList = element.getElementsByTagName(elementName);
		if (nodeList.getLength() > 0) {
			String result = element.getElementsByTagName(elementName).item(0).getTextContent();
			if (result.matches(".*\\w.*"))
				return result;
		}
		return null;
	}

	private static Long getElementAsLong(Element element, String elementName) {
		String string = getElementAsString(element, elementName);
		if (string != null)
			return Long.parseLong(string);
		return null;
	}

	private static Integer getElementAsInteger(Element element, String elementName) {
		String string = getElementAsString(element, elementName);
		if (string != null)
			return Integer.parseInt(string);
		return null;
	}
}
