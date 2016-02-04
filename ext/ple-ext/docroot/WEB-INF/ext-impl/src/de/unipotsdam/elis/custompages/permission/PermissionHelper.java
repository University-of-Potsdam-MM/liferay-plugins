package de.unipotsdam.elis.custompages.permission;

import java.io.Serializable;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;

import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

public class PermissionHelper {

	public static boolean checkPortfolioPermission(long groupId, String actionId) {
		Group group;
		try {
			group = GroupLocalServiceUtil.getGroup(groupId);
			if (group.isLayout()) {
				Layout layout = LayoutLocalServiceUtil.getLayout(group.getClassPK());
				if (isCustomPage(layout) && PermissionHelper.feedbackRequested(layout.getPlid()) && actionId != ActionKeys.VIEW) {
					return false;
				}
			}
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean userHasViewPermission(long plid, long userId) {
		try {
			if ((CustomPageFeedbackLocalServiceUtil.fetchCustomPageFeedback(plid,
					CustomPageStatics.PUBLISHMENT_TYPE_GLOBAL) != null))
				return true;
			Layout layout = LayoutLocalServiceUtil.getLayout(plid);
			if (layout.getUserId() == userId)
				return true;
			CustomPageFeedback pf = CustomPageFeedbackLocalServiceUtil.fetchCustomPageFeedback(plid, userId);
			return pf != null;
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static boolean feedbackRequested(long plid) throws SystemException {
		return CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlidAndFeedbackStatus(plid,
				CustomPageStatics.FEEDBACK_REQUESTED).size() != 0;
	}
	
	public static boolean isCustomPage(Layout layout){
		Serializable pageType = layout.getExpandoBridge().getAttribute("PageType");
		if (pageType != null && ((Short)pageType) != 0)
			return true;
		return false;
	}
}
