package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

public class MoodleDialogueActivityInterpreter extends
		MoodleDefaultActivityInterpreter {
	
	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {

		return "create-new-dialogue-conversation";
	}
}
