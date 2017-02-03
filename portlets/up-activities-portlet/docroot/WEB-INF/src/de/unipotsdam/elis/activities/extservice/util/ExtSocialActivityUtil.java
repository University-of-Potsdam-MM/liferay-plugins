package de.unipotsdam.elis.activities.extservice.util;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.ClientErrorException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.activities.extservice.moodle.rest.MoodleRestClient;
import de.unipotsdam.elis.activities.model.MoodleSocialActivity;
import de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil;

public class ExtSocialActivityUtil {
	
	public static void requestNewMoodleActivities(/*long userId, String username, String password*/)
			throws ClientErrorException, PortalException, SystemException, DatatypeConfigurationException {
		
		MoodleSocialActivity mostRecentMoodleActivity = MoodleSocialActivityLocalServiceUtil
				.getMostRecentMoodleSocialActivity();
		
		long currentTime = System.currentTimeMillis() / 1000;
		
		// TODO remove this block if time can be set via adminPortlet
		Calendar c = Calendar.getInstance(); 
	    c.setTime(new Date()); 
	    c.add(Calendar.MONTH, -1); 
	    Date date = c.getTime();
		
	    // TODO load from adminPortlet
	    long earliestStarttime = date.getTime() / 1000;
		
		// defined earliest starttime, which is used if no most recent activity was found
		long starttime = (mostRecentMoodleActivity != null) ? mostRecentMoodleActivity.getPublished() + 1 : earliestStarttime;
//		System.out.println("starttime "+ starttime);
//		System.out.println("currenttime "+ currentTime);
		
		JSONArray mostRecentMoodleActivities = null;
		
		try {
			mostRecentMoodleActivities = MoodleRestClient.getLatestCourseNews(starttime,
					currentTime, 0, 0); 
		} catch (JSONException | ClientErrorException e) {
			_log.error(e); // log error to inform admins 
		}
			
		// if there are no new activities mostRecentMoodleActivities is [], which results in length of 0
		if (mostRecentMoodleActivities != null)
			addMoodleSocialActivitiesToDB(mostRecentMoodleActivities);
		
	}

	public static void addMoodleSocialActivitiesToDB(JSONArray extActivities) throws PortalException,
			SystemException, DatatypeConfigurationException {
		for (int i = 0; i < extActivities.length(); i++) {
			addMoodleSocialActivityToDB(extActivities.getJSONObject(i));
		}
	}

	private static MoodleSocialActivity addMoodleSocialActivityToDB(JSONObject data)
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

		// TODO: add missing activityTypes
		
		long published = DatatypeFactory.newInstance().newXMLGregorianCalendar(data.getString("published"))
				.toGregorianCalendar().getTimeInMillis() / 1000;
		
		// Audience contains the user (by screenName), that this activity will be displayed to.
		// That means, the same Activity is stored for each user separately.
		JSONObject audience = data.getJSONObject("audience");
		String screenName = audience.getString("name");
		User user = UserLocalServiceUtil.getUserByScreenName(PortalUtil.getDefaultCompanyId(), screenName);
		
		long userId = user.getUserId();
		
		MoodleSocialActivity result = MoodleSocialActivityLocalServiceUtil.addMoodleSocialActivity(userId,
				activityType, extActivityType, extServiceContext, data.toString(), published);

		// BEGIN CHANGE
		// to prevent SocialActivities from being created at current time, pass Date as parameter
		//SocialActivityLocalServiceUtil.addActivity(userId, 0, MoodleSocialActivity.class.getName(),
		//		result.getExtSocialActivityId(), activityType, null, 0);
		Date createDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(data.getString("published"))
				.toGregorianCalendar().getTime();
		
		SocialActivityLocalServiceUtil.addActivity(userId, 0, createDate, MoodleSocialActivity.class.getName(),
				result.getExtSocialActivityId(), activityType, null, 0);
		// END CHANGE
		
		return result; // wozu?
	}
	
	private static Log _log = LogFactoryUtil.getLog(ExtSocialActivityUtil.class);
}
