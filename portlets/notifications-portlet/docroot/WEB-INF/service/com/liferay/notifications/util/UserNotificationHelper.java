package com.liferay.notifications.util;

import java.util.List;

import javax.portlet.PortletMode;
import javax.portlet.PortletModeException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletURLFactoryUtil;

public class UserNotificationHelper {

	/**
	 * This method returns the link to configuration tab of
	 * notifications-portlet for a given user. Link is created to the page where
	 * the notification portlet is located.
	 * 
	 * @param serviceContext
	 * @param user
	 * @return
	 * @throws WindowStateException
	 * @throws PortletModeException
	 * @throws SystemException
	 * @throws PortalException
	 */
	public static String getConfigURL(ServiceContext serviceContext, User user)
			throws WindowStateException, PortletModeException, SystemException,
			PortalException {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				user.getGroupId(), true);

		Layout layout = null;

		// iterate over all layouts to get all portlets
		for (Layout possibleLayout : layouts) {
			LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) possibleLayout
					.getLayoutType();
			List<String> actualPortletList = layoutTypePortlet.getPortletIds();

			// iterate over all portlets to find notification portlet
			for (String portletId : actualPortletList) {
				if (PortletKeys.NOTIFICATIONS.equals(portletId)) {
					layout = possibleLayout;
					break;
				}
			}
		}

		if (layout == null) {
			// Notifications Portlet was not found, so display on first page
			// if there is a page, else no link can be created
			if (!layouts.isEmpty())
				layout = layouts.get(0);
			else
				return "";

		} 
		
		PortletURL myUrl = PortletURLFactoryUtil.create(
				serviceContext.getRequest(), "1_WAR_notificationsportlet",
				layout.getPlid(), PortletRequest.RENDER_PHASE);
		myUrl.setWindowState(WindowState.MAXIMIZED);
		myUrl.setPortletMode(PortletMode.VIEW);
		myUrl.setParameter("actionable", "false");
		myUrl.setParameter("mvcPath", "/notifications/configuration.jsp");

		return myUrl.toString();
	
	}

	/**
	 * Same Method as getConfigURL(ServiceContext, User) but with
	 * themeDisplay instead. Cast from serviceContext 
	 * to themeDisplay always failed.
	 * @param themeDisplay
	 * @param user
	 * @return
	 * @throws WindowStateException
	 * @throws PortletModeException
	 * @throws SystemException
	 * @throws PortalException
	 */
	public static String getConfigURL(ThemeDisplay themeDisplay, User user)
			throws WindowStateException, PortletModeException, SystemException,
			PortalException {

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
				user.getGroupId(), true);

		Layout layout = null;

		// iterate over all layouts to get all portlets
		for (Layout possibleLayout : layouts) {
			LayoutTypePortlet layoutTypePortlet = (LayoutTypePortlet) possibleLayout
					.getLayoutType();
			List<String> actualPortletList = layoutTypePortlet.getPortletIds();

			// iterate over all portlets to find notification portlet
			for (String portletId : actualPortletList) {
				if (PortletKeys.NOTIFICATIONS.equals(portletId)) {
					layout = possibleLayout;
					break;
				}
			}
		}

		if (layout == null) {
			// Notifications Portlet was not found, so display on first page
			// if there is a page, else no link can be created
			if (!layouts.isEmpty())
				layout = layouts.get(0);
			else
				return "";

		} 
		
		PortletURL myUrl = PortletURLFactoryUtil.create(
				themeDisplay.getRequest(), "1_WAR_notificationsportlet",
				layout.getPlid(), PortletRequest.RENDER_PHASE);
		myUrl.setWindowState(WindowState.MAXIMIZED);
		myUrl.setPortletMode(PortletMode.VIEW);
		myUrl.setParameter("actionable", "false");
		myUrl.setParameter("mvcPath", "/notifications/configuration.jsp");
		
		return myUrl.toString();
		
	}
}
