package de.unipotsdam.elis.activities.extservice.util;
import javax.ws.rs.ClientErrorException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.activities.extservice.moodle.rest.MoodleRestClient;
import de.unipotsdam.elis.activities.model.MoodleSocialActivity;
import de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil;

public class ExtSocialActivityUtil {

	public static void requestNewMoodleActivities(long userId, String username, String password)
			throws ClientErrorException, PortalException, SystemException, DatatypeConfigurationException {
		MoodleSocialActivity mostRecentMoodleActivity = MoodleSocialActivityLocalServiceUtil
				.getMostRecentMoodleSocialActivity(userId);
//		System.out.println("MOERP_most_recent "+mostRecentMoodleActivity.getData());
		long currentTime = System.currentTimeMillis() / 1000;
		// TODO: könnte eine fette abfrage werden wenn wirklich alle aktivitäten
		// geholt werden soll -> vielleicht doch immer auf 10 reduzieren oder so
		long starttime = (mostRecentMoodleActivity != null) ? mostRecentMoodleActivity.getPublished() + 1 : 0;
		System.out.println("MOERP_starttime "+ starttime);
		System.out.println("MOERP_currenttime "+ currentTime);
		try {
			JSONArray mostRecentMoodleActivities = MoodleRestClient.getLatestCourseNews(username, password, starttime,
					currentTime, 0, 0); // TODO: beheben dass trotzdem alle Ergebnisse geholt werden
			
			/*
			 * Alle mostRecentMoodleActivities zur DB hinzufügen fürht zu doppelten Einträgen. Daher maximale starttime 
			 * in DB raussuchen und nur neuere Einträge hinzufügen!
			 */
			addMoodleSocialActivitiesToDB(userId, mostRecentMoodleActivities);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

	public static void addMoodleSocialActivitiesToDB(long userId, JSONArray extActivities) throws PortalException,
			SystemException, DatatypeConfigurationException {
		for (int i = 0; i < extActivities.length(); i++) {
			addMoodleSocialActivityToDB(userId, extActivities.getJSONObject(i));
		}
	}

	private static MoodleSocialActivity addMoodleSocialActivityToDB(long userId, JSONObject data)
			throws PortalException, SystemException, DatatypeConfigurationException {
		JSONObject context = data.getJSONObject("context");
		String extServiceContext = "<a class=\"group\" href=\"" + context.getString("url") + "\">"
				+ HtmlUtil.escape(context.getString("name")) + "</a>";
		String extActivityType = data.getJSONObject("object").getJSONArray("type").getString(1);
		int activityType = 0;
		if (extActivityType.equals("ma:post"))
			activityType = ExtendedSocialActivityKeyConstants.MB_REPLY_MESSAGE;
		else if (extActivityType.equals("ma:discussion"))
			activityType = ExtendedSocialActivityKeyConstants.MB_ADD_MESSAGE;
		else if (extActivityType.equals("ma:page"))
			activityType = ExtendedSocialActivityKeyConstants.WIKI_ADD_PAGE;

		long published = DatatypeFactory.newInstance().newXMLGregorianCalendar(data.getString("published"))
				.toGregorianCalendar().getTimeInMillis() / 1000;
		//TODO: Nutzer der Webservice abruft muss nicht zwingend auch die Activität erzeugt haben!
		MoodleSocialActivity result = MoodleSocialActivityLocalServiceUtil.addMoodleSocialActivity(userId,
				activityType, extActivityType, extServiceContext, data.toString(), published);

		SocialActivityLocalServiceUtil.addActivity(userId, 0, MoodleSocialActivity.class.getName(),
				result.getExtSocialActivityId(), activityType, null, 0);

		return result; // wozu?
	}
}
