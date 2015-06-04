package soap;

import java.io.IOException;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;

public class SoapClient {

	private static String URL = "https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest.pulsTestHttpsSoap11Endpoint";

	public static SOAPMessage GetLectureScheduleRoot() throws UnsupportedOperationException, SOAPException, IOException {
		SOAPConnection soapConnection = createSOAPConnection();
		SOAPMessage soapResponse = soapConnection.call(createGetLectureScheduleRootRequest(), URL);
		soapConnection.close();
		return soapResponse;
	}

	public static SOAPMessage GetLectureScheduleSubTree(String headerId) throws UnsupportedOperationException,
			SOAPException, IOException {
		SOAPConnection soapConnection = createSOAPConnection();
		SOAPMessage soapResponse = soapConnection.call(createGetLectureScheduleSubTreeRequest(headerId), URL);
		soapConnection.close();
		return soapResponse;
	}

	public static SOAPMessage GetLectureScheduleCourses(String headerId) throws UnsupportedOperationException,
			SOAPException, IOException {
		SOAPConnection soapConnection = createSOAPConnection();
		SOAPMessage soapResponse = soapConnection.call(createGetLectureScheduleCoursesRequest(headerId), URL);
		soapConnection.close();
		return soapResponse;
	}

	public static SOAPMessage GetCourseData(String courseId) throws UnsupportedOperationException, SOAPException,
			IOException {
		SOAPConnection soapConnection = createSOAPConnection();
		SOAPMessage soapResponse = soapConnection.call(createGetCourseDataRequest(courseId), URL);
		soapConnection.close();
		return soapResponse;
	}

	public static SOAPMessage GetLecturerDetails(String lecturerId, String semester) throws UnsupportedOperationException, SOAPException,
			IOException {
		SOAPConnection soapConnection = createSOAPConnection();
		SOAPMessage soapResponse = soapConnection.call(createGetLecturerDetails(lecturerId,semester), URL);
		soapConnection.close();
		return soapResponse;
	}

	private static SOAPConnection createSOAPConnection() throws UnsupportedOperationException, SOAPException {
		SOAPConnectionFactory soapConnectionFactory = SOAPConnectionFactory.newInstance();
		return soapConnectionFactory.createConnection();
	}

	private static SOAPMessage createGetLectureScheduleRootRequest() throws SOAPException, IOException {
		SOAPMessage soapMessage = initSoapMessage();

		SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("Elis_getLectureScheduleRoot", "pul");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("condition");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("semester");
		soapBodyElem2.addTextNode("0");
		
		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction",
				"https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/getLectureScheduleRoot");
		
		soapMessage.saveChanges();
		return soapMessage;
	}

	private static SOAPMessage createGetLectureScheduleSubTreeRequest(String headerId) throws SOAPException,
			IOException {
		SOAPMessage soapMessage = initSoapMessage();

		SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("Elis_getLectureScheduleSubTree", "pul");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("condition");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("headerId");
		soapBodyElem2.addTextNode(headerId);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction",
				"https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/getLectureScheduleSubTree");

		soapMessage.saveChanges();
		return soapMessage;
	}

	private static SOAPMessage createGetLectureScheduleCoursesRequest(String headerId) throws SOAPException,
			IOException {
		SOAPMessage soapMessage = initSoapMessage();

		// SOAP Body
		SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("Elis_getLectureScheduleCourses", "pul");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("condition");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("headerId");
		soapBodyElem2.addTextNode(headerId);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction",
				"https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/getLectureScheduleCourses");

		soapMessage.saveChanges();
		
		return soapMessage;
	}

	private static SOAPMessage createGetCourseDataRequest(String courseId) throws SOAPException, IOException {
		SOAPMessage soapMessage = initSoapMessage();

		// SOAP Body
		SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("Elis_getCourseData", "pul");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("condition");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("courseId");
		soapBodyElem2.addTextNode(courseId);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", "https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/getCourseData");

		soapMessage.saveChanges();
		
		return soapMessage;
	}

	private static SOAPMessage createGetLecturerDetails(String lecturerId, String semester) throws SOAPException, IOException {
		SOAPMessage soapMessage = initSoapMessage();

		// SOAP Body
		SOAPBody soapBody = soapMessage.getSOAPPart().getEnvelope().getBody();
		SOAPElement soapBodyElem = soapBody.addChildElement("Elis_getLecturerDetails", "pul");
		SOAPElement soapBodyElem1 = soapBodyElem.addChildElement("condition");
		SOAPElement soapBodyElem2 = soapBodyElem1.addChildElement("lecturerId");
		soapBodyElem2.addTextNode(lecturerId);
		SOAPElement soapBodyElem3 = soapBodyElem1.addChildElement("semester");
		soapBodyElem3.addTextNode(semester);

		MimeHeaders headers = soapMessage.getMimeHeaders();
		headers.addHeader("SOAPAction", "https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/getLecturerDetails");

		soapMessage.saveChanges();
		
		return soapMessage;
	}

	private static SOAPMessage initSoapMessage() throws SOAPException {
		MessageFactory messageFactory = MessageFactory.newInstance();
		SOAPMessage soapMessage = messageFactory.createMessage();
		SOAPPart soapPart = soapMessage.getSOAPPart();

		String serverURI = "https://esb.soft.cs.uni-potsdam.de:8243/services/pulsTest/";

		// SOAP Envelope
		SOAPEnvelope envelope = soapPart.getEnvelope();
		envelope.addNamespaceDeclaration("pul", serverURI);

		return soapMessage;
	}
}
