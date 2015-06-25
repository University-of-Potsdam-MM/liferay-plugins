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
		System.out.println("check Portfolio!");
		if (PortfolioManager.pageIsPortfolio(layout)) {
			System.out.println("is Portfolio");
			if (permissionChecker.getUserId() != layout.getUserId()) {
				boolean test = PortfolioManager.userHasPermission(permissionChecker.getUserId(),layout.getPlid());
				System.out.println(test);
				return test;
			}
		}
		return super.contains(permissionChecker, layout, checkViewableGroup, actionId);
	}

}
