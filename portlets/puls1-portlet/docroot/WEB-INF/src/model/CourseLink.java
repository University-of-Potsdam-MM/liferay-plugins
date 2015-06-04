package model;

import java.io.Serializable;

public class CourseLink implements Serializable {
	
	private String link;
	private String linkName;
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getLinkName() {
		return linkName;
	}
	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

}
