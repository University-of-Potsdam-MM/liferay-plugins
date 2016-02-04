package de.unipotsdam.elis.custompages.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.LayoutPermissionImpl;

public class CustomLayoutPermissionImpl extends LayoutPermissionImpl {

	public CustomLayoutPermissionImpl() {
		System.out.println("CustomLayoutPermission 3 created");
	}

	@Override
	public boolean contains(PermissionChecker permissionChecker, Layout layout, boolean checkViewableGroup,
			String actionId) throws PortalException, SystemException {

		if (!permissionChecker.isOmniadmin() && permissionChecker.isSignedIn()) {
			if (PermissionHelper.isCustomPage(layout)) {
				if (permissionChecker.getUserId() != layout.getUserId()) {
					if (actionId.equals(ActionKeys.VIEW)) {
						return PermissionHelper.userHasViewPermission(layout.getPlid(),permissionChecker.getUserId());
					}
				} else if (!actionId.equals(ActionKeys.VIEW) && !actionId.equals(ActionKeys.CUSTOMIZE)) {
					return !PermissionHelper.feedbackRequested(layout.getPlid());
				}
			}
		}
		return super.contains(permissionChecker, layout, checkViewableGroup, actionId);
	}
}
