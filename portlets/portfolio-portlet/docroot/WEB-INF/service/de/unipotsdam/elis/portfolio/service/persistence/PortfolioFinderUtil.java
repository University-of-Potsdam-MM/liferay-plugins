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

package de.unipotsdam.elis.portfolio.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Matthias
 */
public class PortfolioFinderUtil {
	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByLayoutUserId(
		long userId, int begin, int end) {
		return getFinder().findByLayoutUserId(userId, begin, end);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByLayoutUserId(
		long userId) {
		return getFinder().findByLayoutUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPortfolioFeedbackUserId(
		long userId, int begin, int end) {
		return getFinder().findByPortfolioFeedbackUserId(userId, begin, end);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPortfolioFeedbackUserId(
		long userId) {
		return getFinder().findByPortfolioFeedbackUserId(userId);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(
		long publishmentType, long userId, int begin, int end) {
		return getFinder()
				   .findByPublishmentTypeAndNoPortfolioFeedback(publishmentType,
			userId, begin, end);
	}

	public static java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(
		long publishmentType, long userId) {
		return getFinder()
				   .findByPublishmentTypeAndNoPortfolioFeedback(publishmentType,
			userId);
	}

	public static PortfolioFinder getFinder() {
		if (_finder == null) {
			_finder = (PortfolioFinder)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.portfolio.service.ClpSerializer.getServletContextName(),
					PortfolioFinder.class.getName());

			ReferenceRegistry.registerReference(PortfolioFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(PortfolioFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(PortfolioFinderUtil.class, "_finder");
	}

	private static PortfolioFinder _finder;
}