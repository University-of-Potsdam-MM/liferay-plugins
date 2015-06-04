package controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import model.CatalogEntry;
import model.Course;
import model.Lecturer;
import soap.SoapController;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class Puls1Portlet extends MVCPortlet { 

	Map<String, CatalogEntry> catalogMap;

	public void getCatalogRoot(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {
		catalogMap = new HashMap<String, CatalogEntry>();
		CatalogEntry root = SoapController.getScheduleRoot(catalogMap);
		if (root == null)
			SessionErrors.add(actionRequest, "error");
		else
			actionRequest.getPortletSession().setAttribute("currentEntry", root);
	}

	public void getCatalogEntry(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {
		String entryId = actionRequest.getParameter("entryId");
		CatalogEntry entry = catalogMap.get(entryId);
		if (entry instanceof Course)
			SoapController.getCourseDetails((Course) entry);
		else if (!entry.hasChildren())
			SoapController.getScheduleChildren(catalogMap, entry);
		actionRequest.getPortletSession().setAttribute("currentEntry", entry);
	}

	public void showLecturerDetails(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {
		String lecturerId = actionRequest.getParameter("lecturerId");
		String entryId = actionRequest.getParameter("entryId");
		Course entry = (Course) catalogMap.get(entryId);
		Lecturer lecturer = SoapController.getLecturerDetails(lecturerId, entry.getSemester());
		actionRequest.getPortletSession().setAttribute("currentLecturer", lecturer);
		actionResponse.setRenderParameter("jspPage","/html/lecturerDetails.jsp");
	}
}
