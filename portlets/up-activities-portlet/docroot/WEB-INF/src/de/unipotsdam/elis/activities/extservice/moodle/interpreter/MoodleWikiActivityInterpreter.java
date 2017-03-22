package de.unipotsdam.elis.activities.extservice.moodle.interpreter;

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;

public class MoodleWikiActivityInterpreter extends MoodleDefaultActivityInterpreter {
	
	@Override
	protected String getTitlePattern(SocialActivitySet activitySet,
			JSONObject data, ServiceContext serviceContext) {
		
		if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_ADD_PAGE)
			return "new-wiki-page";
		else if (activitySet.getType() == ExtendedSocialActivityKeyConstants.WIKI_UPDATE_PAGE)
			return "updated-wiki-page";
		
		return StringPool.BLANK;
	}

}
