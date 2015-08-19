package de.unipotsdam.elis.portfolio.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.LayoutPermissionImpl;

import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

public class CustomLayoutPermissionImpl extends LayoutPermissionImpl {

	public CustomLayoutPermissionImpl() {
		System.out.println("CustomLayoutPermission 6 created");
	}

	@Override
	public boolean contains(PermissionChecker permissionChecker, Layout layout, boolean checkViewableGroup,
			String actionId) throws PortalException, SystemException {
		Portfolio portfolio = PortfolioLocalServiceUtil.fetchPortfolio(layout.getPlid());
		if (portfolio != null) {
			if (permissionChecker.getUserId() != layout.getUserId()){
				 if (actionId.equals(ActionKeys.VIEW)) {
					return portfolio.userHasViewPermission(permissionChecker.getUserId());
				}
			}
			else if(!actionId.equals(ActionKeys.VIEW) && !actionId.equals(ActionKeys.CUSTOMIZE)){
				return !portfolio.feedbackRequested();
			}
		}
		return super.contains(permissionChecker, layout, checkViewableGroup, actionId);
	}
}
