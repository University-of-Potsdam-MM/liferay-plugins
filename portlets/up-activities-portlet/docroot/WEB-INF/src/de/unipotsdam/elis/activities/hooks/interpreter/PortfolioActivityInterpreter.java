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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
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
		String title = getTitle(jsonObject, serviceContext, activitySet.getModifiedDate());
		return new SocialActivityFeedEntry("", title, "");
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity, ServiceContext serviceContext)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(activity.getExtraData());
		String title = getTitle(jsonObject, serviceContext, activity.getCreateDate());
		return new SocialActivityFeedEntry("", title, "");
	}
	
	private String getTitle(JSONObject jsonObject, ServiceContext serviceContext, long displayDate) throws PortalException, SystemException, PortletException{

		StringBundler sb = new StringBundler(8);

		sb.append("<div class=\"activity-header\">");
		sb.append("<div class=\"activity-user-name\">");
		
		String url = jsonObject.getString("url");

		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(jsonObject.getLong("plid"));
		User sender = UserLocalServiceUtil.getUser(jsonObject.getLong("userId"));
		User receiver = UserLocalServiceUtil.getUser(jsonObject.getLong("receiverUserId"));
		String portfolioTitle = "<a href=\"" + url + "\">" + portfolio.getLayout().getTitle(serviceContext.getLocale()) + "</a>";
		String title = "";
		if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_PORTFOLIO_PUBLISHED) {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-portfolio-published-activity",
					new String[] { sender.getFullName(), portfolioTitle, receiver.getFullName() });
		} else if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-feedback-requested-activity",
					new String[] { sender.getFullName(), portfolioTitle, receiver.getFullName() });
		} else {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			title = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-feedback-delivered-activity", new String[] { sender.getFullName(), portfolioTitle });
		}

		sb.append(title);
		
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
