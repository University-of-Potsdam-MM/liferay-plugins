package de.unipotsdam.elis.custompages.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionImpl;

public class CustomPortletPermissionImpl extends PortletPermissionImpl {

	public CustomPortletPermissionImpl() {
		System.out.println("CustomPortletPermission 3 created");
	}

	@Override
	public boolean contains(PermissionChecker permissionChecker, long groupId, Layout layout, String portletId,
			String actionId, boolean strict) throws PortalException, SystemException {

		if (!permissionChecker.isOmniadmin() && !actionId.equals(ActionKeys.VIEW) && layout != null) {
			if (PermissionHelper.isCustomPage(layout) && PermissionHelper.feedbackRequested(layout.getPlid())) {
				if (((LayoutTypePortlet) layout.getLayoutType()).getPortletIds().contains(portletId)) {
					return false;
				}
			}
		}
		return super.contains(permissionChecker, groupId, layout, portletId, actionId, strict);
	}

}
