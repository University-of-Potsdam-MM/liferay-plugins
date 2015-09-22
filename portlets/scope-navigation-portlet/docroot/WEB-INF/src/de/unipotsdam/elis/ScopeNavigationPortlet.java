package de.unipotsdam.elis;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.LayoutSet;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class ScopeNavigationPortlet extends MVCPortlet {

	public void changeScope(ActionRequest actionRequest, ActionResponse actionResponse) throws PortalException,
			SystemException, IOException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		LayoutSet layoutset = themeDisplay.getLayoutSet();
		if (ParamUtil.getString(actionRequest, "scopeNavigation").equals("private"))
			actionResponse.sendRedirect(PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, true));
		else if (ParamUtil.getString(actionRequest, "scopeNavigation").equals("public"))
			actionResponse.sendRedirect(PortalUtil.getDisplayURL(layoutset.getGroup(), themeDisplay, false));
	}

}
