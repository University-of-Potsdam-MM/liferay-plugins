package de.unipotsdam.elis.portfolio.activity;

import javax.portlet.PortletConfig;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletBagPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.model.BaseSocialActivityInterpreter;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityFeedEntry;

import de.unipotsdam.elis.portfolio.PortfolioStatics;
import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

public class PortfolioActivityInterpreter extends BaseSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return new String[] { Portfolio.class.getName() };
	}

	@Override
	protected SocialActivityFeedEntry doInterpret(SocialActivity activity, ServiceContext serviceContext)
			throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(activity.getExtraData());
		String link = jsonObject.getString("url");

		Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(jsonObject.getLong("plid"));
		User sender = UserLocalServiceUtil.getUser(jsonObject.getLong("userId"));
		User receiver = UserLocalServiceUtil.getUser(jsonObject.getLong("receiverUserId"));
		String title = "<a href=\"" + link + "\">" + portfolio.getLayout().getTitle(serviceContext.getLocale()) + "</a>";
		String body = "";
		if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_PORTFOLIO_PUBLISHED) {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			body = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-portfolio-published-activity",
					new String[] { sender.getFullName(), title, receiver.getFullName() });
		} else if (jsonObject.getInt("activityType") == PortfolioStatics.MESSAGE_TYPE_FEEDBACK_REQUESTED) {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			body = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-feedback-requested-activity",
					new String[] { sender.getFullName(), title, receiver.getFullName() });
		} else {
			PortletConfig portletConfig = PortalUtil.getPortletConfig(jsonObject.getLong("companyId"), jsonObject
					.getString("portletId"), PortletBagPool.get(jsonObject.getString("portletId")).getServletContext());
			body = LanguageUtil.format(portletConfig, serviceContext.getLocale(),
					"portfolio-feedback-delivered-activity", new String[] { sender.getFullName(), title });
		}
		return new SocialActivityFeedEntry(link, body, "");
	}

}
