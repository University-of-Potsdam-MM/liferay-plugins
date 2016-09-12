package de.unipotsdam.elis.webdav;

import com.github.sardine.Sardine;
import com.github.sardine.SardineFactory;

import de.unipotsdam.elis.owncloud.repository.OwncloudConfigurationLoader;

/**
 * Allows the connection to a webdav server.
 *
 */
public class WebdavEndpoint {

	private Sardine sardine;
	private String endpoint;
	private boolean _isRoot;

	public WebdavEndpoint(String username, String password, String endpoint) {
		this.sardine = SardineFactory.begin();
		this.sardine.setCredentials(username, password);
		this.endpoint = endpoint;
		this._isRoot = username.equals(OwncloudConfigurationLoader
				.getRootUsername());
	}

	public WebdavEndpoint(String username, String password) {
		this(username, password, OwncloudConfigurationLoader
				.getOwnCloudWebdavAddress());
	}

	public Sardine getSardine() {
		return sardine;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public boolean isRoot() {
		return _isRoot;
	}
}
