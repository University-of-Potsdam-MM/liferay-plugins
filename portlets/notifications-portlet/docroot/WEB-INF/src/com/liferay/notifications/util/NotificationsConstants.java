/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.notifications.util;

/**
 * @author Lin Cui
 */
public interface NotificationsConstants {

	public static final String[] ACTIONABLE_TYPES = {
		// add portlets here you want to control via notification requests.
		// BEGIN CHANGE
		// added PortletKeys.SITE_MEMBERSHIPS_ADMIN to control workspace join requests, added custompages to handle feedback requests
//		PortletKeys.CONTACTS_CENTER, PortletKeys.SO_INVITE_MEMBERS
		PortletKeys.CONTACTS_CENTER, PortletKeys.SO_INVITE_MEMBERS, PortletKeys.SITE_MEMBERSHIPS_ADMIN, "othercustompages_WAR_custompagesportlet", "mycustompages_WAR_custompagesportlet"
		// END CHANGE
	};

}