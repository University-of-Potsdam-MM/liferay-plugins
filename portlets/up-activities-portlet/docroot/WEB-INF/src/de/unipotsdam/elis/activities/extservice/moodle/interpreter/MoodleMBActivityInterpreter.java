package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;

public class MoodleMBActivityInterpreter extends MoodleDefaultActivityInterpreter {

	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
		if (activitySet.getType() == ExtendedSocialActivityKeyConstants.MB_REPLY_MESSAGE)
			return "new-forum-post";
		else if (activitySet.getType() == ExtendedSocialActivityKeyConstants.MB_ADD_MESSAGE)
			return "new-discussion";
		
		return StringPool.BLANK;
	}
}
