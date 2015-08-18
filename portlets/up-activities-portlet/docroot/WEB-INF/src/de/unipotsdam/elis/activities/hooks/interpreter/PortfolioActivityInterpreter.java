package de.unipotsdam.elis.activities.hooks.interpreter;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import com.liferay.compat.portal.kernel.util.LocalizationUtil;
import com.liferay.compat.portal.kernel.util.Time;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySetLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil; 
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

public class PortfolioActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String getSelector() {
		return "SO";
	}

	@Override
	public String[] getClassNames() {
		return new String[] { Portfolio.class.getName() };
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivitySet activitySet, ServiceContext serviceContext)
			throws Exception {
		PortletConfig portletConfig = PortalUtil.getPortletConfig(serviceContext.getCompanyId(),
				serviceContext.getPortletId(), PortletBagPool.get(serviceContext.getPortletId()).getServletContext());
		String title = getTitle(activitySet.getUserId(),activitySet.getClassPK(), activitySet.getModifiedDate(), JSONFactoryUtil.createJSONObject(activitySet.getExtraData()) , serviceContext, portletConfig);
		List<SocialActivity> activities = ExtSocialActivitySetLocalServiceUtil.findSocialActivitiesByActivitySetId(activitySet
				.getActivitySetId());
		String body = getBody(activities.toArray(new SocialActivity[activities.size()]), serviceContext, portletConfig);
		return new SocialActivityFeedEntry("", title, body);
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity, ServiceContext serviceContext)
			throws Exception {
		PortletConfig portletConfig = PortalUtil.getPortletConfig(serviceContext.getCompanyId(),
				serviceContext.getPortletId(), PortletBagPool.get(serviceContext.getPortletId()).getServletContext());
		String title = getTitle(activity.getUserId(),activity.getClassPK(), activity.getCreateDate(), JSONFactoryUtil.createJSONObject(activity.getExtraData()), serviceContext, portletConfig);
		String body = getBody(new SocialActivity[] { activity }, serviceContext, portletConfig);
		return new SocialActivityFeedEntry("", title, body);
	}

	private String getTitle(long userId, long plid, long changeDate, JSONObject extraData,ServiceContext serviceContext, PortletConfig portletConfig)
			throws PortalException, SystemException, PortletException {
		User sender = UserLocalServiceUtil.getUser(userId);
		String portfolioTitle = LocalizationUtil.getLocalization(extraData.getString("title"), LocaleUtil.toLanguageId(serviceContext.getLocale()));
		Layout layout = LayoutLocalServiceUtil.fetchLayout(plid);
		if (layout != null){
			String url = PortalUtil.getLayoutFullURL(layout, serviceContext.getThemeDisplay());
			portfolioTitle = "<a href=\"" + url + "\">" + portfolioTitle + "</a>";
		}

		StringBundler sb = new StringBundler(8);

		sb.append("<div class=\"activity-header\">");
		sb.append("<div class=\"activity-user-name\">");

		sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(), "portfolio-in-portfolio-area",
				new String[] { getUserName(sender.getUserId(), serviceContext) }));

		sb.append("</div><div class=\"activity-time\" title=\"");

		Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(DateFormat.FULL, DateFormat.SHORT,
				serviceContext.getLocale(), serviceContext.getTimeZone());

		Date activityDate = new Date(changeDate);

		sb.append(dateFormatDate.format(activityDate));

		sb.append("\">");

		Format dateFormat = FastDateFormatFactoryUtil.getDate(DateFormat.FULL, serviceContext.getLocale(),
				serviceContext.getTimeZone());

		String relativeTimeDescription = Time.getRelativeTimeDescription(changeDate, serviceContext.getLocale(),
				serviceContext.getTimeZone(), dateFormat);

		sb.append(relativeTimeDescription);

		sb.append("</div></div><div class=\"activity-action\">");

		sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(), "portfolio-changes-to-portfolio-page",
				new String[] { portfolioTitle }));

		sb.append("</div>");

		return sb.toString();
	}

	private String getBody(SocialActivity[] activities, ServiceContext serviceContext, PortletConfig portletConfig)
			throws PortalException, SystemException {
		StringBundler sb = new StringBundler("<div class=\"activity-body\">");
		for (SocialActivity activity : activities) {
			if (activity.getType() == ExtendedSocialActivityKeyConstants.PORTFOLIO_PUBLISHED) {
				sb.append("<div class=\"title portfolioPublished\">");
				sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
						"portfolio-portfolio-page-published-activity",
						new String[] { getUserName(activity.getReceiverUserId(), serviceContext) }));
			} else if (activity.getType() == ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_REQUESTED) {
				sb.append("<div class=\"title feedbackRequested\">");
				sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
						"portfolio-feedback-requested-activity",
						new String[] { getUserName(activity.getReceiverUserId(), serviceContext) }));
			} else if (activity.getType() == ExtendedSocialActivityKeyConstants.PORTFOLIO_FEEDBACK_DELIVERED){
				sb.append("<div class=\"title feedbackDelivered\">");
				sb.append(LanguageUtil.get(portletConfig, serviceContext.getLocale(),
						"portfolio-feedback-delivered-activity"));
			} else if (activity.getType() == ExtendedSocialActivityKeyConstants.PORTFOLIO_DELETED){
				sb.append("<div class=\"title portfolioDeleted\">");
				sb.append(LanguageUtil.get(portletConfig, serviceContext.getLocale(),
						"portfolio-deleted"));
			}
			sb.append("</div>");
		}
		sb.append("</div>");
		return sb.toString();
	}

	@Override
	public void updateActivitySet(long activityId) throws PortalException, SystemException {

		SocialActivity activity = SocialActivityLocalServiceUtil.fetchSocialActivity(activityId);

		if ((activity == null) || (activity.getActivitySetId() > 0)) {
			return;
		}

		long activitySetId = getActivitySetId(activityId);

		if (activitySetId > 0) {
			SocialActivitySetLocalServiceUtil.incrementActivityCount(activitySetId, activityId);

			for (SocialActivity socialActivity : ExtSocialActivitySetLocalServiceUtil.findSocialActivitiesByActivitySetIdAndType(
					activitySetId, activity.getType())) {
				if (socialActivity.getActivityId() != activity.getActivityId()
						&& socialActivity.getReceiverUserId() == activity.getReceiverUserId()) {
					SocialActivityLocalServiceUtil.deleteActivity(socialActivity);
					SocialActivitySetLocalServiceUtil.decrementActivityCount(activitySetId);
				}
			}

			SocialActivitySet activitySet = SocialActivitySetLocalServiceUtil.getSocialActivitySet(activitySetId);
			activitySet.setModifiedDate(activity.getCreateDate());
			SocialActivitySetLocalServiceUtil.updateSocialActivitySet(activitySet);

			return;
		}

		SocialActivitySet activitySet = SocialActivitySetLocalServiceUtil.addActivitySet(activityId);
		activitySet.setExtraData(activity.getExtraData());
		SocialActivitySetLocalServiceUtil.updateSocialActivitySet(activitySet);
	}

	@Override
	protected long getActivitySetId(long activityId) {
		try {
			SocialActivitySet activitySet = null;

			SocialActivity activity = SocialActivityLocalServiceUtil.getActivity(activityId);

			activitySet = ExtSocialActivitySetLocalServiceUtil.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(
					activity.getUserId(), activity.getClassNameId(), activity.getClassPK());

			if ((activitySet != null) && !isExpired(activitySet)) {
				return activitySet.getActivitySetId();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	protected boolean isExpired(SocialActivitySet activitySet) {

		long age = System.currentTimeMillis() - activitySet.getCreateDate();

		long timeWindow = Time.MINUTE * Long.parseLong(PortletProps.get("social.activity.sets.bundling.time.window"));

		if (age > timeWindow) {
			return true;
		}

		return false;
	}

}
