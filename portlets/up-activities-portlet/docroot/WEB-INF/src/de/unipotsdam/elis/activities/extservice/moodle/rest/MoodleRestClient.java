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

import de.unipotsdam.elis.activities.ActivitiesAdminPortlet;

public class MoodleRestClient {
	
	private static final String SERVICE_NAME = "campusup",
			WS_FUNCTION_GET_COURSE_NEWS = "local_campusup_get_recent_course_activities",
			WS_FUNCTION_ADD_USER = "local_campusup_add_user";
	
	private static String getToken() throws JSONException, ClientErrorException {

		String _target = ActivitiesAdminPortlet.getMoodleServiceEndpoint(); // set in admin portlet
		String username = ActivitiesAdminPortlet.getMoodleServiceUsername();
		String password = ActivitiesAdminPortlet.getMoodleServicePassword();
		
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(_target).path("/login/token.php").queryParam("username", username)
				.queryParam("password", password).queryParam("service", SERVICE_NAME);

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);

		JSONObject jsonResponse = JSONFactoryUtil.createJSONObject(response.readEntity(String.class));

		String token = jsonResponse.getString("token", "null");

		if (token.equals("null"))
			throw new JSONException(jsonResponse.toString());

		return token;
	}

	public static JSONArray getLatestCourseNews(int startIndex, int endIndex)
			throws JSONException, ClientErrorException {

		String _target = ActivitiesAdminPortlet.getMoodleServiceEndpoint(); // set in admin portlet
		
		String token = getToken();

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(_target).path("/webservice/rest/server.php")
				.queryParam("wsfunction", WS_FUNCTION_GET_COURSE_NEWS)
				.queryParam("startindex", startIndex).queryParam("count", endIndex - startIndex)
				.queryParam("wstoken", token).queryParam("moodlewsrestformat", "json");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);

		return JSONFactoryUtil.createJSONArray(response.readEntity(String.class));
	}

	public static JSONArray getLatestCourseNews(long startTime, long endTime,
			int startIndex, int endIndex) throws JSONException, ClientErrorException {

		String _target = ActivitiesAdminPortlet.getMoodleServiceEndpoint(); // set in admin portlet
		
		String token = getToken();

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(_target).path("/webservice/rest/server.php")
				.queryParam("wsfunction", WS_FUNCTION_GET_COURSE_NEWS).queryParam("starttime", startTime)
				.queryParam("endtime", endTime).queryParam("startindex", startIndex)
				.queryParam("count", endIndex - startIndex).queryParam("wstoken", token)
				.queryParam("moodlewsrestformat", "json");
		
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get(); 

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);
		// TODO: fix crash, if webservice_access_exception occurs
		return JSONFactoryUtil.createJSONArray(response.readEntity(String.class));
	}
	
	/**
	 * add user of campus.UP to moodle, too receive activities.
	 * @param screenName
	 * @throws ClientErrorException 
	 * @throws JSONException 
	 */
	public static boolean addCampusUpUser(String screenName) throws JSONException, ClientErrorException {
		String _target = ActivitiesAdminPortlet.getMoodleServiceEndpoint(); // set in admin portlet
		
		String token = getToken(); 

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target(_target).path("/webservice/rest/server.php")
				.queryParam("wsfunction", WS_FUNCTION_ADD_USER)
				.queryParam("userid", screenName)
				.queryParam("wstoken", token)
				.queryParam("moodlewsrestformat", "json");
		
		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get(); 

		if (response.getStatus() != 200)
			throw new ClientErrorException(response);

		return true;//JSONFactoryUtil.createJSONArray(response.readEntity(String.class));
	}
}
