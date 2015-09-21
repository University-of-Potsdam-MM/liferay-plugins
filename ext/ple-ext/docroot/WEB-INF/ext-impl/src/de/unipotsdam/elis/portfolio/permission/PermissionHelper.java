package de.unipotsdam.elis.portfolio.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.GroupLocalServiceUtil;

import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

public class PermissionHelper {

	public static boolean checkPortfolioPermission(long groupId, String actionId) {
		Group group;
		try {
			group = GroupLocalServiceUtil.getGroup(groupId);
			if (group.isLayout()) {
				Portfolio portfolio = PortfolioLocalServiceUtil.fetchPortfolio(group.getClassPK());
				if (portfolio != null && portfolio.feedbackRequested() && actionId != ActionKeys.VIEW) {
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
}
