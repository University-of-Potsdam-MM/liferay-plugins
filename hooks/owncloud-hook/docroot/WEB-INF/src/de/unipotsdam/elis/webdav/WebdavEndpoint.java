package de.unipotsdam.elis.webdav;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

public class WebdavEndpoint {
	private Sardine sardine;
	private String endpoint;
	private String username;
	private String password;
	
	public WebdavEndpoint(String username, String password, String endpoint) {
		this.sardine = SardineFactory.begin();
		this.username = username;
		this.password = password;
		this.sardine.setCredentials(username,password);		
		this.endpoint = endpoint;
	}
	public WebdavEndpoint(String username, String password) {		
		this(username,password,WebdavConfigurationLoader.getOwnCloudWebdavAddress());	
	}
	
	public Sardine getSardine() {
		return sardine;
	}

	public String getEndpoint() {
		return endpoint;
	}
	
	public String getUser() {
		return username;
	}	
	
	public Boolean isValidCredentialinDebug() {
		if (username == null || password == null) {
			return false;
		}
		return (username.equals("test") && password.equals("test"));
	}
	
	public Boolean isUserContextSet() {
		return username != null && password != null && !password.equals("false");
	}
	
}
