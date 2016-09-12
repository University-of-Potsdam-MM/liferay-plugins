package de.unipotsdam.elis.custompages.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionImpl;

/**
 * Intervenes the check permission progress for a portlet. 
 * 
 * @author Matthias
 *
 */public class CustomPortletPermissionImpl extends PortletPermissionImpl {

	public CustomPortletPermissionImpl() {
		// Debug message to check whether the ext-plugin is deployed correctly
		System.out.println("CustomPortletPermission created");
	}

	@Override
	public boolean contains(PermissionChecker permissionChecker, long groupId, Layout layout, String portletId,
			String actionId, boolean strict) throws PortalException, SystemException {

		if (!permissionChecker.isOmniadmin() && !actionId.equals(ActionKeys.VIEW) && layout != null) {
			if (PermissionHelper.isCustomPage(layout) && PermissionHelper.feedbackRequested(layout.getPlid())) {
				if (((LayoutTypePortlet) layout.getLayoutType()).getPortletIds().contains(portletId)) {
					// only viewing of a portlet is permitted when the feedback is requested for the corresponding page
					return false;
				}
			}
		}
		return super.contains(permissionChecker, groupId, layout, portletId, actionId, strict);
	}

}
