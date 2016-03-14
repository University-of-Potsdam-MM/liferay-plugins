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

package de.unipotsdam.elis.custompages.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Matthias
 */
public class CustomPageFeedbackFinderUtil {
	public static java.util.List<com.liferay.portal.model.User> findUsersByCustomPageFeedback(
		long plid) {
		return getFinder().findUsersByCustomPageFeedback(plid);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId, int begin, int end) {
		return getFinder()
				   .findCustomPagesByPageTypeAndLayoutUserId(pageType, userId,
			begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId) {
		return getFinder()
				   .findCustomPagesByPageTypeAndLayoutUserId(pageType, userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId, int begin, int end) {
		return getFinder()
				   .findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId,
			begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId) {
		return getFinder()
				   .findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserId(
		long userId, int begin, int end) {
		return getFinder().findCustomPagesByLayoutUserId(userId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserId(
		long userId) {
		return getFinder().findCustomPagesByLayoutUserId(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(
		long userId, int begin, int end) {
		return getFinder()
				   .findCustomPagesByLayoutUserIdAndCustomPageFeedback(userId,
			begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(
		long userId) {
		return getFinder()
				   .findCustomPagesByLayoutUserIdAndCustomPageFeedback(userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId, int begin, int end) {
		return getFinder()
				   .findCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType,
			userId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId) {
		return getFinder()
				   .findCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType,
			userId);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId, int begin, int end) {
		return getFinder()
				   .findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType,
			userId, begin, end);
	}

	public static java.util.List<com.liferay.portal.model.Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId) {
		return getFinder()
				   .findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType,
			userId);
	}

	public static CustomPageFeedbackFinder getFinder() {
		if (_finder == null) {
			_finder = (CustomPageFeedbackFinder)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.custompages.service.ClpSerializer.getServletContextName(),
					CustomPageFeedbackFinder.class.getName());

			ReferenceRegistry.registerReference(CustomPageFeedbackFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(CustomPageFeedbackFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(CustomPageFeedbackFinderUtil.class,
			"_finder");
	}

	private static CustomPageFeedbackFinder _finder;
}