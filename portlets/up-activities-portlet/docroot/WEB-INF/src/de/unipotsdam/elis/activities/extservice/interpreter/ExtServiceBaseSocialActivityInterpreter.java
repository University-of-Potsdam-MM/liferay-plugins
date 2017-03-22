package de.unipotsdam.elis.activities.extservice.interpreter;

import java.text.DateFormat;
import java.text.Format;
import java.util.Arrays;
import java.util.Date;

import javax.portlet.PortletConfig;
import javax.xml.datatype.DatatypeFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleAdobeConnectActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleAssignmentActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleChoiceActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleDataBaseActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleDefaultActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleDialogueActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleFairAllocationActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleFeedbackActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleGlossaryActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleGroupSelfSelectionActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleHotPotActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleJournalActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleLessonActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleMBActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleMindmapActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodlePlanerActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleQuizActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleRessourcesActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleSCROMPackageActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleSurveyActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleWikiActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleWorkshopActivityInterpreter;
import de.unipotsdam.elis.activities.model.MoodleSocialActivity;
import de.unipotsdam.elis.activities.service.MoodleSocialActivityLocalServiceUtil;

public class ExtServiceBaseSocialActivityInterpreter extends BaseSocialActivityInterpreter {

	ExtServiceSocialActivityInterpreter _interpreter = null;

	@Override
	public String getSelector() {
		return "SO";
	}

	@Override
	public String[] getClassNames() {
		return new String[] { MoodleSocialActivity.class.getName() };
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivitySet activitySet, ServiceContext serviceContext)
			throws Exception {
		if (PortalUtil.getClassName(activitySet.getClassNameId()).equals(MoodleSocialActivity.class.getName())) {
			MoodleSocialActivity moodleSocialActivity = MoodleSocialActivityLocalServiceUtil
					.getMoodleSocialActivity(activitySet.getClassPK());
			
			JSONObject data = JSONFactoryUtil.createJSONObject(moodleSocialActivity.getData());
			
			// get interpreter for activity. If interpreter is null, do not interpret activity.
			_interpreter = getMoodleInterpreter(moodleSocialActivity);
			if (_interpreter == null)
				return null;

			return new SocialActivityFeedEntry(null, getTitle(activitySet, data,
					moodleSocialActivity.getExtServiceContext(), serviceContext), _interpreter.getBody(data,
					serviceContext.getThemeDisplay()));
		}
		return null;
	}

	protected String getTitle(SocialActivitySet activitySet, JSONObject data, String extServiceContext,
			ServiceContext serviceContext) throws Exception {

		StringBuilder sb = new StringBuilder();

		sb.append("<div class=\"activity-header\">");
		sb.append("<div class=\"activity-user-name\">");

		String userName = getUserName(data.getJSONObject("actor"), serviceContext);

		if (extServiceContext != null) {
			// check origin of activity to display it in title
			if (PortalUtil.getClassName(activitySet.getClassNameId()).equals(MoodleSocialActivity.class.getName())) {
				// somehow serviceContext.translate did not work in this case, so LanguageUtil is used instead
				PortletConfig portletConfig = (PortletConfig) serviceContext.getRequest().getAttribute(
						JavaConstants.JAVAX_PORTLET_CONFIG);
				sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
						"x-in-moodlecourse-x", new Object[] { userName, extServiceContext }));
			} else
				sb.append(serviceContext.translate("x-in-x", new Object[] { userName, extServiceContext }));

		} else {
			sb.append(userName);
		}

		sb.append("</div><div class=\"activity-time\" title=\"");

		Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(DateFormat.FULL, DateFormat.SHORT,
				serviceContext.getLocale(), serviceContext.getTimeZone());

		long displayDate = DatatypeFactory.newInstance().newXMLGregorianCalendar(data.getString("published"))
				.toGregorianCalendar().getTimeInMillis();

		Date activityDate = new Date(displayDate);

		sb.append(dateFormatDate.format(activityDate));

		sb.append("\">");

//		Format dateFormat = FastDateFormatFactoryUtil.getDate(DateFormat.FULL, serviceContext.getLocale(),
//				serviceContext.getTimeZone());
		Format dateFormat = FastDateFormatFactoryUtil.getTime(serviceContext.getLocale(),
				serviceContext.getTimeZone());
		
//		String relativeTimeDescription = Time.getRelativeTimeDescription(displayDate, serviceContext.getLocale(),
//				serviceContext.getTimeZone(), dateFormat);

//		sb.append(relativeTimeDescription);
		sb.append(dateFormat.format(activityDate));

		sb.append("</div></div>");

		sb.append("<div class=\"activity-action\">");
		sb.append(_interpreter.getActivityTitle(activitySet, data, serviceContext));
		sb.append("</div>");

		return sb.toString();
	}

	protected String getUserName(JSONObject actor, ServiceContext serviceContext) {
		String extName = actor.getString("name");
		try {
			User user = UserLocalServiceUtil
					.fetchUserByScreenName(serviceContext.getCompanyId(), actor.getString("id"));

			if (user == null) {
				return extName;
			}

			if (user.getUserId() == serviceContext.getUserId()) {
				return HtmlUtil.escape(user.getFirstName());
			}

			String userName = user.getFullName();

			Group group = user.getGroup();

			if (group.getGroupId() == serviceContext.getScopeGroupId()) {
				return HtmlUtil.escape(userName);
			}
			
			String userDisplayURL = user.getDisplayURL(serviceContext.getThemeDisplay());

			userName = "<a class=\"user\" href=\"" + userDisplayURL + "\">" + HtmlUtil.escape(userName) + "</a>";
			
			return userName;
		} catch (Exception e) {
			return actor.getString("name");
		}
	}

	@Override
	public void updateActivitySet(long activityId) throws PortalException, SystemException {
		super.updateActivitySet(activityId);
	}

	/**
	 * Get Moodle interpreter for a given moodleSocialActivity. 
	 * @param moodleSocialActivity  
	 * @return - the moodle intepreter that matches the given module type or 
	 * 				null if resourcetype is not taken into consideration. 
	 */
	private ExtServiceSocialActivityInterpreter getMoodleInterpreter(MoodleSocialActivity moodleSocialActivity) 
			throws JSONException{
		
		JSONObject data = JSONFactoryUtil.createJSONObject(moodleSocialActivity.getData());
		
		// MoodleModule that activity belongs too, e.g. a glossary, forum, choice, hotpot, ...
		String maModuleType = data.getJSONObject("object").getJSONArray("type").getString(0);
		
		// The resource of that module can be the module itself but also a post in forum or a glossary entry.
		String maRessourceType = data.getJSONObject("object").getJSONArray("type").getString(1);

		// Array that contains types that shall not be displayed, e.g. creation of glossary.
		String[] unsupportedResourceTypes = new String[] {"ma:chat", "ma:database", "ma:forum", "ma:glossary",
				"ma:journal", "ma:lesson", "ma:scheduler", "ma:ouwiki", "ma:wiki"};
		
		// moodle resources are all interpreted the same way
		String [] moodleResources = new String[] {"url", "book", "lti", "resource", "label"};
		
		if (Arrays.asList(unsupportedResourceTypes).contains(maRessourceType))
			return null;
				
		if (maModuleType.equals("assign")) { 									
			_interpreter = new MoodleAssignmentActivityInterpreter();
		} else if (maModuleType.equals("choice")) { 							
			_interpreter = new MoodleChoiceActivityInterpreter();
		} else if (maModuleType.equals("forum")) { 								
			_interpreter = new MoodleMBActivityInterpreter();
		} else if (maModuleType.equals("glossary")) { 							
			_interpreter = new MoodleGlossaryActivityInterpreter();
		} else if (maModuleType.equals("feedback")) { 							
			_interpreter = new MoodleFeedbackActivityInterpreter();
		} else if (maModuleType.equals("groupselect")) {						
			_interpreter = new MoodleGroupSelfSelectionActivityInterpreter();
		} else if (maModuleType.equals("hotpot")) {								
			_interpreter = new MoodleHotPotActivityInterpreter();
		} else if (maModuleType.equals("journal")) {							
			_interpreter = new MoodleJournalActivityInterpreter();
		} else if (maModuleType.equals("lesson")) {								
			_interpreter = new MoodleLessonActivityInterpreter();
		} else if (maModuleType.equals("mindmap")) {							
			_interpreter = new MoodleMindmapActivityInterpreter();
		} else if (maModuleType.equals("wiki")) {								
			_interpreter = new MoodleWikiActivityInterpreter();
		} else if (maModuleType.equals("quiz")) {								
			_interpreter = new MoodleQuizActivityInterpreter();
		} else if (maModuleType.equals("database")) {							
			_interpreter = new MoodleDataBaseActivityInterpreter();
		} else if (maModuleType.equals("survey")) {								
			_interpreter = new MoodleSurveyActivityInterpreter();
		} else if (Arrays.asList(moodleResources).contains(maModuleType)) {								
			_interpreter = new MoodleRessourcesActivityInterpreter();
		} else if (maModuleType.equals("workshop")) {							
			_interpreter = new MoodleWorkshopActivityInterpreter();
		} else if (maModuleType.equals("dialogue")) {
			_interpreter = new MoodleDialogueActivityInterpreter();
		} else if (maModuleType.equals("ratingallocate")) {
			_interpreter = new MoodleFairAllocationActivityInterpreter();
		} else if (maModuleType.equals("scheduler")) {
			_interpreter = new MoodlePlanerActivityInterpreter();
		} else if (maModuleType.equals("scrom")) {
			_interpreter = new MoodleSCROMPackageActivityInterpreter();
		} else if (maModuleType.equals("adobeconnect")) {
			_interpreter = new MoodleAdobeConnectActivityInterpreter();
		} else {
			_interpreter = new MoodleDefaultActivityInterpreter();
			return null;
		}
		
		return _interpreter;
		
	}
}
