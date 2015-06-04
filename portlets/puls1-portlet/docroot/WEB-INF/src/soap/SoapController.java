package soap;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import model.CatalogEntry;
import model.Course;
import model.Lecturer;

public class SoapController {

	public static CatalogEntry getScheduleRoot(Map<String, CatalogEntry> catalogMap) {
		try {
			SOAPMessage message = SoapClient.GetLectureScheduleRoot();
			return SoapMessageParser.parseScheduleRoot(message.getSOAPBody(), catalogMap);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean getScheduleChildren(Map<String, CatalogEntry> catalogMap, CatalogEntry currentEntry) {
		try {
			SOAPMessage message = SoapClient.GetLectureScheduleSubTree(currentEntry.getId());
			if (SoapMessageParser.parseCatalogEntries(message.getSOAPBody(), currentEntry, catalogMap))
				return true;
			message = SoapClient.GetLectureScheduleCourses(currentEntry.getId());
			return SoapMessageParser.parseCourseEntries(message.getSOAPBody(), currentEntry, catalogMap);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean getCourseDetails(Course course) {
		try {
			SOAPMessage message = SoapClient.GetCourseData(course.getId());
			if (SoapMessageParser.parseCourseDetails(message.getSOAPBody(), course))
				return true;
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static Lecturer getLecturerDetails(String lecturerId, String semester) {
		try {
			SOAPMessage message = SoapClient.GetLecturerDetails(lecturerId, semester);
			return SoapMessageParser.parseLecturerDetails(message.getSOAPBody());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
