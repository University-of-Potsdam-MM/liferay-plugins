package de.unipotsdam.elis.activities.extservice.interpreter;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;

import javax.xml.datatype.DatatypeFactory;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivitySet;

import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleAssignmentActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleChoiceActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleDataBaseActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleDefaultActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleFeedbackActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleGlossaryActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleGroupSelfSelectionActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleHotPotActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleJournalActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleLessonActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleMBActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleMindmapActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleQuizActivityInterpreter;
import de.unipotsdam.elis.activities.extservice.moodle.interpreter.MoodleRessourcesActivityInterpreter;
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
			
			String moodleSocialActivityType = data.getJSONObject("object").getJSONArray("type").getString(1);
			
			if (moodleSocialActivityType.equals("ma:assign")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:assign_submission")) {
				_interpreter = new MoodleAssignmentActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:chat")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:choice")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:choice_answers")) {
				_interpreter = new MoodleChoiceActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:comment")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:data")) {
				_interpreter = new MoodleDefaultActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:discussion")) {
				_interpreter = new MoodleMBActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:entry")) {
				_interpreter = new MoodleGlossaryActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:feedback")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:feedback_completed")) {
				_interpreter = new MoodleFeedbackActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:forum")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:glossary")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:group_member")) {
				_interpreter = new MoodleGroupSelfSelectionActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:groupselect")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:hotpot")) {
				_interpreter = new MoodleHotPotActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:journal")) {
				_interpreter = new MoodleJournalActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:lesson")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:lesson_page")) {
				_interpreter = new MoodleLessonActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:mindmap")) {
				_interpreter = new MoodleMindmapActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:ouwiki")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:page")) {
				_interpreter = new MoodleWikiActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:post")) {
				_interpreter = new MoodleMBActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:quiz")) {
				_interpreter = new MoodleQuizActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:record")) {
				_interpreter = new MoodleDataBaseActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:resource")) {
				_interpreter = new MoodleRessourcesActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:survey")) {
				_interpreter = new MoodleSurveyActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:url")) {
				_interpreter = new MoodleRessourcesActivityInterpreter();
			} else if (moodleSocialActivityType.equals("ma:wiki")) {
				return null;
			} else if (moodleSocialActivityType.equals("ma:workshop")) {
				_interpreter = new MoodleWorkshopActivityInterpreter();
			} else {
				_interpreter = new MoodleDefaultActivityInterpreter();
				return null;
			}

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

		Format dateFormat = FastDateFormatFactoryUtil.getDate(DateFormat.FULL, serviceContext.getLocale(),
				serviceContext.getTimeZone());
		String relativeTimeDescription = Time.getRelativeTimeDescription(displayDate, serviceContext.getLocale(),
				serviceContext.getTimeZone(), dateFormat);

		sb.append(relativeTimeDescription);

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

}
