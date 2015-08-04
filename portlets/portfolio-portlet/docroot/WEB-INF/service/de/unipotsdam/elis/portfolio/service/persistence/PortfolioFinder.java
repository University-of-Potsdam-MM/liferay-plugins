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

/**
 * @author Matthias
 */
public interface PortfolioFinder {
	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByLayoutUserId(
		long userId, int begin, int end);

	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByLayoutUserId(
		long userId);

	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPortfolioFeedbackUserId(
		long userId, int begin, int end);

	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPortfolioFeedbackUserId(
		long userId);

	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(
		long publishmentType, long userId, int begin, int end);

	public java.util.List<de.unipotsdam.elis.portfolio.model.Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(
		long publishmentType, long userId);
}