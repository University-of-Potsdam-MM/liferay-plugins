package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

public class MoodleGlossaryActivityInterpreter extends
		MoodleDefaultActivityInterpreter {
	
	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {

		String moodleSocialActivityType = data.getJSONObject("object").getJSONArray("type").getString(1);
		
		if ("ma:category".equals(moodleSocialActivityType))
			return "create-new-glossary-category";
		else
			return "create-new-glossary-entry";
	}
}
