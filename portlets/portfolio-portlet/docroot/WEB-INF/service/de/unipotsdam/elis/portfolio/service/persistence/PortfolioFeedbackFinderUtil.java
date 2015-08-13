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
public class PortfolioFeedbackFinderUtil {
	public static java.util.List<com.liferay.portal.model.User> findUsersByPortfolioFeedback(
		long plid) {
		return getFinder().findUsersByPortfolioFeedback(plid);
	}

	public static PortfolioFeedbackFinder getFinder() {
		if (_finder == null) {
			_finder = (PortfolioFeedbackFinder)PortletBeanLocatorUtil.locate(de.unipotsdam.elis.portfolio.service.ClpSerializer.getServletContextName(),
					PortfolioFeedbackFinder.class.getName());

			ReferenceRegistry.registerReference(PortfolioFeedbackFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(PortfolioFeedbackFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(PortfolioFeedbackFinderUtil.class,
			"_finder");
	}

	private static PortfolioFeedbackFinder _finder;
}