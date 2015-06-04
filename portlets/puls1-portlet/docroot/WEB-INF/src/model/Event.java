package model;

import java.io.Serializable;
import java.util.List;

public class Event implements Serializable {

	private String eventId;
	private int groupId;
	private String group;
	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String day;
	private String rhythm;
	private String building;
	private String room;
	private String roomType;
	private String comment;
	private List<Lecturer> lecturers;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getRhythm() {
		return rhythm;
	}

	public void setRhythm(String rhythm) {
		this.rhythm = rhythm;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<Lecturer> getLecturers() {
		return lecturers;
	}

	public void setLecturers(List<Lecturer> lecturers) {
		this.lecturers = lecturers;
	}

}
