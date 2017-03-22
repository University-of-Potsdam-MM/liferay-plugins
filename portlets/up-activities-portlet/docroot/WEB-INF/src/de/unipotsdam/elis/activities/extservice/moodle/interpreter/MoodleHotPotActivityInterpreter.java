package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

public class MoodleHotPotActivityInterpreter extends
		MoodleDefaultActivityInterpreter {
	
	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
//		String moodleSocialActivityType = data.getString("type");
//		
//		if ((moodleSocialActivityType.equalsIgnoreCase("add")) ||  (moodleSocialActivityType.equalsIgnoreCase("created"))) {
//			return "added-new-hotpot";
//		}
//		
//		if (moodleSocialActivityType.equalsIgnoreCase("submitt")) {
//			return "submitt-hotpot";
//		}
//			
//		return "sth-with-hotpot";
		
		return "added-new-hotpot";
	}
}
