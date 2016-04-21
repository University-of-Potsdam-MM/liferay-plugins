package de.unipotsdam.elis.custompages.event;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.DuplicateColumnNameException;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoTableLocalServiceUtil;

import de.unipotsdam.elis.custompages.CustomPageStatics;

public class CustomPageStartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			ExpandoColumn expandoColumn = addExpandoAttribute(PortalUtil.getDefaultCompanyId(), CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME,
					ExpandoColumnConstants.INTEGER, Layout.class.getName());

			if (!expandoColumn.getDefaultData().equals("1")) {
				expandoColumn.setDefaultData("1");
				ExpandoColumnLocalServiceUtil.updateExpandoColumn(expandoColumn);
			}
			
			addExpandoAttribute(PortalUtil.getDefaultCompanyId(), CustomPageStatics.CREATED_DYNAMICAL_CUSTOM_FIELD_NAME,
					ExpandoColumnConstants.BOOLEAN, Layout.class.getName());
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
	}

	public static ExpandoColumn addExpandoAttribute(long companyId, String columname, int type, String className)
			throws PortalException, SystemException {
		ExpandoTable table = null;
		ExpandoColumn expandoColumn = null;

		try {
			table = ExpandoTableLocalServiceUtil.addTable(companyId, className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME);
		} catch (DuplicateTableNameException dtne) {
			/* Get the default table for User Custom Fields */
			table = ExpandoTableLocalServiceUtil.getTable(companyId, className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		try {
			expandoColumn = ExpandoColumnLocalServiceUtil.addColumn(table.getTableId(), columname, type);
		} catch (DuplicateColumnNameException dcne) {
			expandoColumn = ExpandoColumnLocalServiceUtil.getColumn(companyId, className,
					ExpandoTableConstants.DEFAULT_TABLE_NAME, columname);
		}

		Role userRole = RoleLocalServiceUtil.getRole(PortalUtil.getDefaultCompanyId(), RoleConstants.USER);


		ResourcePermission resourcePermission = ResourcePermissionLocalServiceUtil.fetchResourcePermission(
				PortalUtil.getDefaultCompanyId(), ExpandoColumn.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(expandoColumn.getPrimaryKey()), userRole.getRoleId());

		if (resourcePermission != null) {
			// Set (only) view permission if not yet set
			if (resourcePermission.getActionIds() != 9) {
				resourcePermission.setActionIds(9);
				ResourcePermissionLocalServiceUtil.updateResourcePermission(resourcePermission);
			}
		} else {
			// Add resourcePermission if not existent
			ResourcePermissionLocalServiceUtil.setResourcePermissions(PortalUtil.getDefaultCompanyId(),
					ExpandoColumn.class.getName(), ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(expandoColumn.getPrimaryKey()), userRole.getRoleId(), new String[] {
							ActionKeys.VIEW, ActionKeys.UPDATE });
		}

		return expandoColumn;
	}

}
