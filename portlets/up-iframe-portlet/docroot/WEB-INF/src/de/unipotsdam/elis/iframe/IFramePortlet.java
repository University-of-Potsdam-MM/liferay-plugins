/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package de.unipotsdam.elis.iframe;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.iframe.util.IFrameUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class IFramePortlet extends MVCPortlet {

	public static final String DEFAULT_EDIT_ACTION = "/iframe/edit";

	public static final String DEFAULT_VIEW_ACTION = "/iframe/view";

	@Override
	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException,
			PortletException {
		String src = actionRequest.getParameter("website");
		
		PortletPreferences portletPreferences = actionRequest.getPreferences();
		
		portletPreferences.setValue("src", src);
		portletPreferences.store();
		
		super.processAction(actionRequest, actionResponse);
	}

	@Override
	public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		// TODO Auto-generated method stub

		String src = "";
		try {
			src = transformSrc(request, response);
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * if (Validator.isNull(src) || src.equals(Http.HTTP_WITH_SLASH) ||
		 * src.equals(Http.HTTPS_WITH_SLASH)) {
		 * 
		 * response.setRenderParameter("jspPage", "html/config.jsp"); }
		 */
		request.setAttribute("IFRAME_SRC", src);
		super.render(request, response);
	}

	protected String getPassword(RenderRequest renderRequest, RenderResponse renderResponse) throws PortalException,
			SystemException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String password = portletPreferences.getValue("basicPassword", StringPool.BLANK);

		return IFrameUtil.getPassword(renderRequest, password);
	}

	protected String getSrc(RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String src = portletPreferences.getValue("src", StringPool.BLANK);

		src = ParamUtil.getString(renderRequest, "src", src);

		return src;
	}

	protected String getUserName(RenderRequest renderRequest, RenderResponse renderResponse) throws PortalException,
			SystemException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String userName = portletPreferences.getValue("basicUserName", StringPool.BLANK);

		return IFrameUtil.getUserName(renderRequest, userName);
	}

	protected String transformSrc(RenderRequest renderRequest, RenderResponse renderResponse) throws PortalException,
			SystemException {

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		String src = getSrc(renderRequest, renderResponse);

		boolean auth = GetterUtil.getBoolean(portletPreferences.getValue("auth", StringPool.BLANK));

		if (!auth) {
			return src;
		}

		String authType = portletPreferences.getValue("authType", StringPool.BLANK);

		if (authType.equals("basic")) {
			String userName = getUserName(renderRequest, renderResponse);
			String password = getPassword(renderRequest, renderResponse);

			int pos = src.indexOf("://");

			String protocol = src.substring(0, pos + 3);
			String url = src.substring(pos + 3);

			src = protocol + userName + ":" + password + "@" + url;
		} else {
			ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			String portletId = PortalUtil.getPortletId(renderRequest);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(themeDisplay.getCompanyId(), portletId);

			src = themeDisplay.getPathMain() + "/" + portlet.getStrutsPath() + "/proxy?p_l_id="
					+ themeDisplay.getPlid() + "&p_p_id=" + portletId;
		}

		return src;
	}

}