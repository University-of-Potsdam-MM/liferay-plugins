package com.liferay.notifications.notifications;

import com.liferay.compat.portal.kernel.notifications.BaseModelUserNotificationHandler;
import com.liferay.portal.util.PortletKeys;

/**
 * 
 * @author Patrick Wolfien
 *
 */
public class SiteMembershipUserNotificationHandler extends
		BaseModelUserNotificationHandler {

	public SiteMembershipUserNotificationHandler() {
		setPortletId(PortletKeys.SITE_MEMBERSHIPS_ADMIN);
	}
}
