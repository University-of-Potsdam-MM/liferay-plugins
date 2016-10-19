package de.unipotsdam.elis.activities.extservice.moodle.rest;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;

public class MoodleRestClient {

	/**
	 * http://localhost/moodle/webservice/rest/server.php?
	 * wstoken=eaf10af21b98618e8bf0ea473a5e8e24
	 * &wsfunction=webservice_get_recent_course_activities
	 * &moodlewsrestformat=json
	 */
	
	private final static String TARGET = "http://localhost";

	private static String getToken(String username, String password) throws JSONException, ClientErrorException {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(TARGET).path("moodle/login/token.php").queryParam("username", username)
				.queryParam("password", password).queryParam("service", "webservice_recent_course_activities");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);

		JSONObject jsonResponse = JSONFactoryUtil.createJSONObject(response.readEntity(String.class));
		System.out.println("MOERP: "+jsonResponse.toString());
		String token = jsonResponse.getString("token", "null");

		if (token.equals("null"))
			throw new JSONException(jsonResponse.toString());

		return token;
	}

	public static JSONArray getLatestCourseNews(String username, String password, int startIndex, int endIndex)
			throws JSONException, ClientErrorException {

		String token = getToken(username, password);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(TARGET).path("moodle/webservice/rest/server.php")
				.queryParam("wsfunction", "webservice_get_recent_course_activities")
				.queryParam("startindex", startIndex).queryParam("count", endIndex - startIndex)
				.queryParam("wstoken", token).queryParam("moodlewsrestformat", "json");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);

		return JSONFactoryUtil.createJSONArray(response.readEntity(String.class));
	}

	public static JSONArray getLatestCourseNews(String username, String password, long startTime, long endTime,
			int startIndex, int endIndex) throws JSONException, ClientErrorException {

		String token = getToken(username, password);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(TARGET).path("moodle/webservice/rest/server.php")
				.queryParam("wsfunction", "webservice_get_recent_course_activities").queryParam("starttime", startTime)
				.queryParam("endtime", endTime).queryParam("startindex", startIndex)
				.queryParam("count", endIndex - startIndex).queryParam("wstoken", token)
				.queryParam("moodlewsrestformat", "json");
		
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get(); 

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);
		// TODO: fix crash, if webservice_access_exception occurs
		return JSONFactoryUtil.createJSONArray(response.readEntity(String.class));
	}
}
