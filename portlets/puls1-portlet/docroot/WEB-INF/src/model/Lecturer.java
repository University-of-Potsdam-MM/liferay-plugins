package model;

import java.io.Serializable;
import java.util.List;

public class Lecturer implements Serializable {
	private String lecturerId;
	private String lecturerFirstname;
	private String lecturerLastname;
	private String gender;
	private String officeHours;
	private String staffRole;
	private String status;
	private String lecturerTitle;
	private LecturerContact lecturerContact;
	private List<Facility> facilityies;

	public String getLecturerId() {
		return lecturerId;
	}

	public void setLecturerId(String lecturerId) {
		this.lecturerId = lecturerId;
	}

	public String getLecturerFirstname() {
		return lecturerFirstname;
	}

	public void setLecturerFirstname(String lecturerFirstname) {
		this.lecturerFirstname = lecturerFirstname;
	}

	public String getLecturerLastname() {
		return lecturerLastname;
	}

	public void setLecturerLastname(String lecturerLastname) {
		this.lecturerLastname = lecturerLastname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getOfficeHours() {
		return officeHours;
	}

	public void setOfficeHours(String officeHours) {
		this.officeHours = officeHours;
	}

	public String getStaffRole() {
		return staffRole;
	}

	public void setStaffRole(String staffRole) {
		this.staffRole = staffRole;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLecturerTitle() {
		return lecturerTitle;
	}

	public void setLecturerTitle(String lecturerTitle) {
		this.lecturerTitle = lecturerTitle;
	}

	public LecturerContact getLecturerContact() {
		return lecturerContact;
	}

	public void setLecturerContact(LecturerContact lecturerContact) {
		this.lecturerContact = lecturerContact;
	}

	public List<Facility> getFacilityies() {
		return facilityies;
	}

	public void setFacilityies(List<Facility> facilityies) {
		this.facilityies = facilityies;
	}

	public String getFormattedName() {
		String name = "";
		if (lecturerTitle != null)
			name += lecturerTitle;
		if (lecturerFirstname != null)
			name += " " + lecturerFirstname;
		if (lecturerLastname != null)
			name += " " + lecturerLastname;
		System.out.println("name:" + name);
		return name;
	}

}
