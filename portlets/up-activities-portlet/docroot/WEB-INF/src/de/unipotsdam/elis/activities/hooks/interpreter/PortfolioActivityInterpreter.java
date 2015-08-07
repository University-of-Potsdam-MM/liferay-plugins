package de.unipotsdam.elis.activities.hooks.interpreter;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;

import com.liferay.compat.portal.kernel.util.Time;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivitySetLocalServiceUtil;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
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
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(activitySet.getExtraData());
		PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
				.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
		String title = getTitle(jsonObject, serviceContext,portletConfig, activitySet.getModifiedDate());
		String body = getBody(jsonObject, serviceContext, portletConfig);
		return new SocialActivityFeedEntry("", title, body);
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity, ServiceContext serviceContext)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(activity.getExtraData());
		PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
				.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
		String title = getTitle(jsonObject, serviceContext,portletConfig, activity.getCreateDate());
		String body = getBody(jsonObject, serviceContext, portletConfig);
		return new SocialActivityFeedEntry("", title, body);
	}
	
	private String getTitle(JSONObject jsonObject, ServiceContext serviceContext,PortletConfig portletConfig, long displayDate) throws PortalException, SystemException, PortletException{
		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(jsonObject.getLong("plid"));
		User sender = UserLocalServiceUtil.getUser(jsonObject.getLong("userId"));
		String url = jsonObject.getString("url");
		String portfolioTitle = "<a href=\"" + url + "\">" + portfolio.getLayout().getTitle(serviceContext.getLocale()) + "</a>";
		
		StringBundler sb = new StringBundler(8);

		sb.append("<div class=\"activity-header\">");
		sb.append("<div class=\"activity-user-name\">");
		
		sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
				"portfolio-in-portfolio-area",
				new String[] {getUserName(sender.getUserId(), serviceContext) }));
		
		sb.append("</div><div class=\"activity-time\" title=\"");

		Format dateFormatDate = FastDateFormatFactoryUtil.getDateTime(
			DateFormat.FULL, DateFormat.SHORT, serviceContext.getLocale(),
			serviceContext.getTimeZone());

		Date activityDate = new Date(displayDate);

		sb.append(dateFormatDate.format(activityDate));

		sb.append("\">");

		Format dateFormat = FastDateFormatFactoryUtil.getDate(
			DateFormat.FULL, serviceContext.getLocale(),
			serviceContext.getTimeZone());

		String relativeTimeDescription = Time.getRelativeTimeDescription(
			displayDate, serviceContext.getLocale(),
			serviceContext.getTimeZone(), dateFormat);

		sb.append(relativeTimeDescription);

		sb.append("</div></div><div class=\"activity-action\">");
		

		sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
				"portfolio-changes-to-portfolio-page",
				new String[] { portfolioTitle }));

		sb.append("</div>");

		return sb.toString();
	}
	
	private String getBody(JSONObject jsonObject, ServiceContext serviceContext,PortletConfig portletConfig) throws PortalException, SystemException{
		User receiver = UserLocalServiceUtil.getUser(jsonObject.getLong("receiverUserId"));
		StringBundler sb = new StringBundler(5);

		sb.append("<div class=\"activity-body\"><div class=\"title\">");
		if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_PORTFOLIO_PUBLISHED) {
			sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-portfolio-page-published-activity",
					new String[] { receiver.getFullName() }));
		} else if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			sb.append(LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-feedback-requested-activity",
					new String[] { getUserName(receiver.getUserId(), serviceContext) }));
		} else {
			sb.append(LanguageUtil.get(portletConfig, serviceContext.getLocale(), "portfolio-feedback-delivered-activity"));
		}
		sb.append("</div></div>");

		return sb.toString();
	}
	
	@Override
	public void updateActivitySet(long activityId)
		throws PortalException, SystemException {

		SocialActivity activity =
			SocialActivityLocalServiceUtil.fetchSocialActivity(activityId);

		if ((activity == null) || (activity.getActivitySetId() > 0)) {
			return;
		}

		long activitySetId = getActivitySetId(activityId);

		if (activitySetId > 0) {
			SocialActivitySetLocalServiceUtil.incrementActivityCount(
				activitySetId, activityId);

			return;
		}

		SocialActivitySet activitySet =
			SocialActivitySetLocalServiceUtil.addActivitySet(activityId);
		
		activitySet.setType(activity.getType());
		activitySet.setExtraData(activity.getExtraData());

		SocialActivitySetLocalServiceUtil.updateSocialActivitySet(activitySet);}
	

}
