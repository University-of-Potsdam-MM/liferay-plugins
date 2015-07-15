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
		System.out.println("CustomLayoutPermission 4 created");
	}

	@Override
	public boolean contains(PermissionChecker permissionChecker, Layout layout, boolean checkViewableGroup,
			String actionId) throws PortalException, SystemException {
		if (actionId.equals(ActionKeys.VIEW) && PortfolioLocalServiceUtil.fetchPortfolio(layout.getPlid()) != null) {
			if (permissionChecker.getUserId() != layout.getUserId()) {
				Portfolio portfolio = PortfolioLocalServiceUtil.getPortfolio(layout.getPlid());
				return portfolio.userHasPermission(permissionChecker.getUserId());
			}
		}
		return super.contains(permissionChecker, layout, checkViewableGroup, actionId);
	}
}
