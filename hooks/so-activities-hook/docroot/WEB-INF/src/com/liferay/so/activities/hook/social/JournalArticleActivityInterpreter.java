package com.liferay.so.activities.hook.social;

import java.util.List;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivityConstants;

public class JournalArticleActivityInterpreter extends SOSocialActivityInterpreter {

	@Override
	public String[] getClassNames() {
		return _CLASS_NAMES;
	}

	@Override
	protected String getPath(
			SocialActivity activity, ServiceContext serviceContext)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				activity.getClassPK());

		String layoutUuid = article.getLayoutUuid();

		if (Validator.isNull(layoutUuid)) {
			return null;
		}

		Layout layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
			layoutUuid, article.getGroupId(), false);

		if (layout == null) {
			layout = LayoutLocalServiceUtil.fetchLayoutByUuidAndGroupId(
				layoutUuid, article.getGroupId(), true);
		}

		String groupFriendlyURL = PortalUtil.getGroupFriendlyURL(
			layout.getGroup(), layout.isPrivateLayout(),
			serviceContext.getThemeDisplay());

		return groupFriendlyURL.concat(
			JournalArticleConstants.CANONICAL_URL_SEPARATOR).concat(
				article.getUrlTitle());
	}

	@Override
	protected Object[] getTitleArguments(String groupName,
			SocialActivity socialActivity, String link, String title,
			ServiceContext serviceContext) throws Exception {
		
		String articleLink = createJournalArtcileLink(socialActivity.getClassPK(), serviceContext);
		
		return new Object[] {articleLink};
	}
	
	@Override
	protected String getTitlePattern(
		String groupName, SocialActivity activity) {

		int activityType = activity.getType();
		
		if (activityType == 1 || activityType == 2) {
			return "updated-an-image-text-editor-entry";
		}
		else if (activityType ==
				SocialActivityConstants.TYPE_ADD_COMMENT) {

			return "commented-on-an-image-text-editor-entry";
		}
		
		return null;
	}

	private String createJournalArtcileLink(long resourcePK,
			ServiceContext serviceContext) throws Exception {
		
		String url = StringPool.BLANK;
		String title = StringPool.BLANK;
		
		// resourcePK is saved as classPK in SocialActivity
		List<JournalArticle> articles = JournalArticleLocalServiceUtil.getArticlesByResourcePrimKey(resourcePK);
		
		if(articles.isEmpty())
			return title;
		
		// Util-Method returns list, so get first entry
		JournalArticle journalArticle = articles.get(0);

		// get workspace -> Group where parentGroupId is 0
		Group workspace = GroupLocalServiceUtil.getGroup(journalArticle.getGroupId());
		while (workspace.getParentGroupId() != 0) {
			workspace = GroupLocalServiceUtil.getGroup(workspace.getParentGroupId());
		}
		
		// get Layout where journal Article is on
		List<Long> layoutIds = JournalContentSearchLocalServiceUtil.getLayoutIds(workspace.getGroupId(), true, journalArticle.getArticleId());
		if (!layoutIds.isEmpty()) {
			// get first layout where content is on, if there are more journal articles they all have same content
			Layout layout = LayoutLocalServiceUtil.getLayout(workspace.getGroupId(), true, layoutIds.get(0));
			url = serviceContext.getPortalURL()+PortalUtil.getLayoutActualURL(layout);

		} else {
			// no layout founds, try again with public layout
			layoutIds = JournalContentSearchLocalServiceUtil.getLayoutIds(workspace.getGroupId(), false, journalArticle.getArticleId());
			if (!layoutIds.isEmpty()) {
				// get first layout where content is on, if there are more journal articles they all have same content
				Layout layout = LayoutLocalServiceUtil.getLayout(workspace.getGroupId(), false, layoutIds.get(0));
				url = serviceContext.getPortalURL()+PortalUtil.getLayoutActualURL(layout);
			} 
		}
		
		title = journalArticle.getTitle(serviceContext.getLocale());
		
		StringBundler sb = new StringBundler(5);
		
		sb.append("<a href=\"");
		sb.append(url);
		sb.append("\" >");
		sb.append(title);
		sb.append("</a>");
		
		return sb.toString();
	}
	
	private static final String[] _CLASS_NAMES =
		{JournalArticle.class.getName()};
}
