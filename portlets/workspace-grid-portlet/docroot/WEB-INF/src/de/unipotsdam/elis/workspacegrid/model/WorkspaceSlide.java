package de.unipotsdam.elis.workspacegrid.model;

import java.io.Serializable;
import java.net.URL;

public class WorkspaceSlide implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private URL webLink;
	
	private String name;
	
	private String templateName;	
	
	private int numberOfNewActivities;
	
	
	public WorkspaceSlide(String name, String templateName, URL webLink, int numberOfNewActivities) {
		super();
		this.templateName = templateName;		
		this.webLink = webLink;
		this.setName(name);
		this.setNumberOfNewActivities(numberOfNewActivities);
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public URL getWebLink() {
		return webLink;
	}

	public void setWebLink(URL webLink) {
		this.webLink = webLink;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfNewActivities() {
		return numberOfNewActivities;
	}

	public void setNumberOfNewActivities(int numberOfNewActivities) {
		this.numberOfNewActivities = numberOfNewActivities;
	}

}