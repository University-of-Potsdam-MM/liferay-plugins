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

/**
 * @author Matthias
 */
public interface CustomPageFeedbackFinder {
	public java.util.List<com.liferay.portal.model.User> findUsersByCustomPageFeedback(
		long plid);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndLayoutUserId(
		int pageType, long userId);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(
		long userId);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserId(
		long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserId(
		long userId);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(
		long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(
		long userId);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(
		int pageType, long userId);

	public java.util.List<com.liferay.portal.model.Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId, int begin, int end);

	public java.util.List<com.liferay.portal.model.Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(
		int pageType, long userId);
}