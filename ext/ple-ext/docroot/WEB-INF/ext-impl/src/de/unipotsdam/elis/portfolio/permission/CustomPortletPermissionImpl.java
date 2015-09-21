package de.unipotsdam.elis.portfolio.permission;

import java.util.Locale;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionImpl;

import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

public class CustomPortletPermissionImpl extends PortletPermissionImpl{
	

	public CustomPortletPermissionImpl() {
		System.out.println("CustomPortletPermission 1 created");
	}

	
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, Layout layout,
			String portletId, String actionId, boolean strict) throws PortalException, SystemException{
		//System.out.println(actionId + " " + portletId );
		
		if (!permissionChecker.isOmniadmin() && !actionId.equals(ActionKeys.VIEW) && layout != null){
			Portfolio portfolio = PortfolioLocalServiceUtil.fetchPortfolio(layout.getPlid());
			if (portfolio != null && portfolio.feedbackRequested()){
				if (((LayoutTypePortlet)layout.getLayoutType()).getPortletIds().contains(portletId)){
					return false;
				}
			}
			//System.out.println("Portlets:");
			/*for (String test : ((LayoutTypePortlet)layout.getLayoutType()).getPortletIds()){
				System.out.println(test);
			}*/
		}
		return super.contains(permissionChecker, groupId, layout, portletId, actionId, strict);
	}

}
