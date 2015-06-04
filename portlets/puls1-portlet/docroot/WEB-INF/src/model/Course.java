package model;

import java.util.List;

public class Course extends CatalogEntry {

	private Facility facility;
	private Long courseNumber;
	private String courseType;
	private String semester;
	private Integer sws;
	private String language;
	private String enrolmentBegin;
	private String enrolmentEnd;
	private List<CourseLink> courseLinks;
	private String comment;
	private String literature;
	private String qualification;
	private String examination;
	private List<Event> events;

	public Facility getFacility() {
		return facility;
	}

	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	public Long getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(Long courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getSemester() {
		return semester;
	}

	public void setSemester(String semester) {
		this.semester = semester;
	}

	public Integer getSws() {
		return sws;
	}

	public void setSws(Integer sws) {
		this.sws = sws;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getEnrolmentBegin() {
		return enrolmentBegin;
	}

	public void setEnrolmentBegin(String enrolmentBegin) {
		this.enrolmentBegin = enrolmentBegin;
	}

	public String getEnrolmentEnd() {
		return enrolmentEnd;
	}

	public void setEnrolmentEnd(String enrolmentEnd) {
		this.enrolmentEnd = enrolmentEnd;
	}

	public List<CourseLink> getCourseLinks() {
		return courseLinks;
	}

	public void setCourseLinks(List<CourseLink> courseLinks) {
		this.courseLinks = courseLinks;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getLiterature() {
		return literature;
	}

	public void setLiterature(String literature) {
		this.literature = literature;
	}

	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	public String getExamination() {
		return examination;
	}

	public void setExamination(String examination) {
		this.examination = examination;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}
	
	@Override
	public boolean isCourse(){
		return true;
	}

}
