package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

public class MoodleAssignmentActivityInterpreter extends
		MoodleDefaultActivityInterpreter {
	
	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
//		String moodleSocialActivityType = data.getString("type");
//		
//		if (moodleSocialActivityType.equalsIgnoreCase("submitt")) {
//			return "submitted-an-assignment";
//		}
		return "added-new-assign";
	}
}
