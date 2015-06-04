package model;

import java.io.Serializable;

public class Facility implements Serializable {
	
	public Facility(){
		super();
	}
	
	public Facility(String facilityId, String facilityName) {
		this.facilityId = facilityId;
		this.facilityName = facilityName;
	}
	
	private String facilityId;
	private String facilityName;
	
	public String getFacilityId() {
		return facilityId;
	}
	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}
	public String getFacilityName() {
		return facilityName;
	}
	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}
	
}
