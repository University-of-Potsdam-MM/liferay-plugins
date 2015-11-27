package de.unipotsdam.elis.activities.moodle.rest;

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

	private static String getToken(String username, String password) throws JSONException, ClientErrorException {

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://eportfolio.uni-potsdam.de").path("moodle/login/token.php")
				.queryParam("username", username).queryParam("password", password)
				.queryParam("service", "webservice_coursenews");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();

		if (response.getStatus() != 200) 
			throw new ClientErrorException(response);
		
		JSONObject jsonResponse = JSONFactoryUtil.createJSONObject(response.readEntity(String.class));
		
		String token = jsonResponse.getString("token","null");
		
		if (token.equals("null"))
			throw new JSONException(jsonResponse.toString());
		
		return token;
	}
	
	public static JSONArray getLatestCourseNews(String username, String password) throws JSONException, ClientErrorException{
		
		String token = getToken(username, password);

		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("https://eportfolio.uni-potsdam.de").path("moodle/webservice/rest/server.php")
				.queryParam("wsfunction", "webservice_get_latest_coursenews")
				.queryParam("wstoken", token)
				.queryParam("moodlewsrestformat", "json");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		
		if (response.getStatus() != 200) 
			throw new ClientErrorException(response);
		
		JSONObject jsonResponse = JSONFactoryUtil.createJSONObject(response.readEntity(String.class));
		JSONArray courses = jsonResponse.getJSONArray("courses");
		
		if (courses == null)
			throw new JSONException(jsonResponse.toString());

		JSONArray result = JSONFactoryUtil.createJSONArray();
		for (int i = 0; i < courses.length(); i++){
			JSONArray courseNews = courses.getJSONObject(i).getJSONArray("coursenews");
			for (int j = 0; j < courseNews.length(); j++){
				result.put(courseNews.getJSONObject(j));
			}
		}
		
		return result;
	}
}
