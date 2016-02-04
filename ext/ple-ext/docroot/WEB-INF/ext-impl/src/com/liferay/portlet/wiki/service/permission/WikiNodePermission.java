package com.liferay.portlet.wiki.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.service.WikiNodeLocalServiceUtil;

import de.unipotsdam.elis.custompages.permission.PermissionHelper;

public class WikiNodePermission {

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, nodeId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, groupId, name, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, WikiNode node, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, node, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(nodeId);

		return contains(permissionChecker, node, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException, SystemException {

		WikiNode node = WikiNodeLocalServiceUtil.getNode(groupId, name);

		return contains(permissionChecker, node, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, WikiNode node, String actionId) {
		
		if (!PermissionHelper.checkPortfolioPermission(node.getGroupId(), actionId)){
			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, node.getGroupId(), WikiNode.class.getName(),
			node.getNodeId(), PortletKeys.WIKI, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				node.getCompanyId(), WikiNode.class.getName(), node.getNodeId(),
				node.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			node.getGroupId(), WikiNode.class.getName(), node.getNodeId(),
			actionId);
	}

}
