package de.unipotsdam.elis.portfolio.permission; 

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.LayoutPermissionImpl;

import de.unipotsdam.elis.portfolio.PortfolioManager;

public class CustomLayoutPermissionImpl extends LayoutPermissionImpl {

	@Override
	public boolean contains(PermissionChecker permissionChecker, Layout layout, boolean checkViewableGroup,
			String actionId) throws PortalException, SystemException {
		if (PortfolioManager.pageIsPortfolio(layout)) {
			if (permissionChecker.getUserId() != layout.getUserId()) {
				return PortfolioManager.userHasPermission(permissionChecker.getUserId(),layout.getPlid());
			}
		}
		return super.contains(permissionChecker, layout, checkViewableGroup, actionId);
	}

}
