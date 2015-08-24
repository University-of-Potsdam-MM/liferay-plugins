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

package de.unipotsdam.elis.portfolio.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Portfolio}.
 * </p>
 *
 * @author Matthias
 * @see Portfolio
 * @generated
 */
public class PortfolioWrapper implements Portfolio, ModelWrapper<Portfolio> {
	public PortfolioWrapper(Portfolio portfolio) {
		_portfolio = portfolio;
	}

	@Override
	public Class<?> getModelClass() {
		return Portfolio.class;
	}

	@Override
	public String getModelClassName() {
		return Portfolio.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("publishmentType", getPublishmentType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Integer publishmentType = (Integer)attributes.get("publishmentType");

		if (publishmentType != null) {
			setPublishmentType(publishmentType);
		}
	}

	/**
	* Returns the primary key of this portfolio.
	*
	* @return the primary key of this portfolio
	*/
	@Override
	public long getPrimaryKey() {
		return _portfolio.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portfolio.
	*
	* @param primaryKey the primary key of this portfolio
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_portfolio.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plid of this portfolio.
	*
	* @return the plid of this portfolio
	*/
	@Override
	public long getPlid() {
		return _portfolio.getPlid();
	}

	/**
	* Sets the plid of this portfolio.
	*
	* @param plid the plid of this portfolio
	*/
	@Override
	public void setPlid(long plid) {
		_portfolio.setPlid(plid);
	}

	/**
	* Returns the publishment type of this portfolio.
	*
	* @return the publishment type of this portfolio
	*/
	@Override
	public int getPublishmentType() {
		return _portfolio.getPublishmentType();
	}

	/**
	* Sets the publishment type of this portfolio.
	*
	* @param publishmentType the publishment type of this portfolio
	*/
	@Override
	public void setPublishmentType(int publishmentType) {
		_portfolio.setPublishmentType(publishmentType);
	}

	@Override
	public boolean isNew() {
		return _portfolio.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portfolio.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portfolio.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portfolio.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portfolio.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portfolio.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portfolio.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portfolio.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_portfolio.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_portfolio.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portfolio.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortfolioWrapper((Portfolio)_portfolio.clone());
	}

	@Override
	public int compareTo(de.unipotsdam.elis.portfolio.model.Portfolio portfolio) {
		return _portfolio.compareTo(portfolio);
	}

	@Override
	public int hashCode() {
		return _portfolio.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.portfolio.model.Portfolio> toCacheModel() {
		return _portfolio.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.Portfolio toEscapedModel() {
		return new PortfolioWrapper(_portfolio.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.Portfolio toUnescapedModel() {
		return new PortfolioWrapper(_portfolio.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portfolio.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portfolio.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portfolio.persist();
	}

	@Override
	public com.liferay.portal.model.Layout getLayout()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.getLayout();
	}

	/**
	* Publishes a portfolio to a user.
	*
	* @param plid
	plid of the portfolio
	* @param userId
	id of the user
	* @return true if the user was added, else false
	* @throws PortalException
	* @throws SystemException
	*/
	@Override
	public boolean publishToUser(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.publishToUser(userId, serviceContext);
	}

	/**
	* Retracts the publishment of a portfolio to a user.
	*
	* @param plid
	plid of the portfolio
	* @param userId
	id of the user
	* @throws NoSuchPermissionException
	* @throws SystemException
	* @throws NoSuchFeedbackException
	*/
	@Override
	public void removeUser(long userId)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		_portfolio.removeUser(userId);
	}

	/**
	* Returns the users having access to the portfolio with the given plid.
	*
	* @param plid
	plid of the portfolio
	* @return list containing the users
	* @throws PortalException
	* @throws SystemException
	*/
	@Override
	public java.util.List<com.liferay.portal.model.User> getUsers()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.getUsers();
	}

	@Override
	public java.util.List<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> getPortfolioFeedbacks()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.getPortfolioFeedbacks();
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioFeedback getPortfolioFeedback(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.getPortfolioFeedback(userId);
	}

	@Override
	public boolean userHasViewPermission(long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.userHasViewPermission(userId);
	}

	@Override
	public void setGlobal()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portfolio.setGlobal();
	}

	@Override
	public void setPrivate()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portfolio.setPrivate();
	}

	@Override
	public void updateFeedbackStatus(long userId, int feedbackStatus)
		throws com.liferay.portal.kernel.exception.SystemException,
			de.unipotsdam.elis.portfolio.NoSuchFeedbackException {
		_portfolio.updateFeedbackStatus(userId, feedbackStatus);
	}

	@Override
	public boolean publishToUserAndRequestFeedback(long userId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.publishToUserAndRequestFeedback(userId, serviceContext);
	}

	@Override
	public boolean feedbackRequested(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.feedbackRequested(userId);
	}

	@Override
	public boolean feedbackRequested()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolio.feedbackRequested();
	}

	@Override
	public void rename(java.lang.String newTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_portfolio.rename(newTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioWrapper)) {
			return false;
		}

		PortfolioWrapper portfolioWrapper = (PortfolioWrapper)obj;

		if (Validator.equals(_portfolio, portfolioWrapper._portfolio)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Portfolio getWrappedPortfolio() {
		return _portfolio;
	}

	@Override
	public Portfolio getWrappedModel() {
		return _portfolio;
	}

	@Override
	public void resetOriginalValues() {
		_portfolio.resetOriginalValues();
	}

	private Portfolio _portfolio;
}