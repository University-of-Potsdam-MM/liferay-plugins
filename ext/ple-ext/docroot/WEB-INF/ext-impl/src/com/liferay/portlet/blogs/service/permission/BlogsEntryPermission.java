package com.liferay.portlet.blogs.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;

import de.unipotsdam.elis.portfolio.permission.PermissionHelper;

public class BlogsEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, BlogsEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, entryId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, BlogsEntry entry,
		String actionId) {
		
		if (!PermissionHelper.checkPortfolioPermission(entry.getGroupId(), actionId))
			return false;

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, entry.getGroupId(), BlogsEntry.class.getName(),
			entry.getEntryId(), PortletKeys.BLOGS, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (entry.isDraft() || entry.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!contains(permissionChecker, entry, ActionKeys.UPDATE)) {

				return false;
			}
		}
		else if (entry.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, entry.getGroupId(),
				BlogsEntry.class.getName(), entry.getEntryId(), actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (permissionChecker.hasOwnerPermission(
				entry.getCompanyId(), BlogsEntry.class.getName(),
				entry.getEntryId(), entry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			entry.getGroupId(), BlogsEntry.class.getName(), entry.getEntryId(),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException, SystemException {

		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(entryId);

		return contains(permissionChecker, entry, actionId);
	}

}