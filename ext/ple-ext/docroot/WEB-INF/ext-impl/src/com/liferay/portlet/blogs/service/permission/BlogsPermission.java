package com.liferay.portlet.blogs.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;

import de.unipotsdam.elis.portfolio.permission.PermissionHelper;

public class BlogsPermission {

	public static final String RESOURCE_NAME = "com.liferay.portlet.blogs";

	public static void check(PermissionChecker permissionChecker, long groupId, String actionId) throws PortalException {

		if (!contains(permissionChecker, groupId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(PermissionChecker permissionChecker, long groupId, String actionId) {

		if (!PermissionHelper.checkPortfolioPermission(groupId, actionId))
			return false;

		Boolean hasPermission = StagingPermissionUtil.hasPermission(permissionChecker, groupId, RESOURCE_NAME, groupId,
				PortletKeys.BLOGS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return permissionChecker.hasPermission(groupId, RESOURCE_NAME, groupId, actionId);
	}

}
