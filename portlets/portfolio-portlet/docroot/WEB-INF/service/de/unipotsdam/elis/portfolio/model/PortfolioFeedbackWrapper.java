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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link PortfolioFeedback}.
 * </p>
 *
 * @author Matthias
 * @see PortfolioFeedback
 * @generated
 */
public class PortfolioFeedbackWrapper implements PortfolioFeedback,
	ModelWrapper<PortfolioFeedback> {
	public PortfolioFeedbackWrapper(PortfolioFeedback portfolioFeedback) {
		_portfolioFeedback = portfolioFeedback;
	}

	@Override
	public Class<?> getModelClass() {
		return PortfolioFeedback.class;
	}

	@Override
	public String getModelClassName() {
		return PortfolioFeedback.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("userId", getUserId());
		attributes.put("feedbackStatus", getFeedbackStatus());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Integer feedbackStatus = (Integer)attributes.get("feedbackStatus");

		if (feedbackStatus != null) {
			setFeedbackStatus(feedbackStatus);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}
	}

	/**
	* Returns the primary key of this portfolio feedback.
	*
	* @return the primary key of this portfolio feedback
	*/
	@Override
	public de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK getPrimaryKey() {
		return _portfolioFeedback.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portfolio feedback.
	*
	* @param primaryKey the primary key of this portfolio feedback
	*/
	@Override
	public void setPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK primaryKey) {
		_portfolioFeedback.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plid of this portfolio feedback.
	*
	* @return the plid of this portfolio feedback
	*/
	@Override
	public long getPlid() {
		return _portfolioFeedback.getPlid();
	}

	/**
	* Sets the plid of this portfolio feedback.
	*
	* @param plid the plid of this portfolio feedback
	*/
	@Override
	public void setPlid(long plid) {
		_portfolioFeedback.setPlid(plid);
	}

	/**
	* Returns the user ID of this portfolio feedback.
	*
	* @return the user ID of this portfolio feedback
	*/
	@Override
	public long getUserId() {
		return _portfolioFeedback.getUserId();
	}

	/**
	* Sets the user ID of this portfolio feedback.
	*
	* @param userId the user ID of this portfolio feedback
	*/
	@Override
	public void setUserId(long userId) {
		_portfolioFeedback.setUserId(userId);
	}

	/**
	* Returns the user uuid of this portfolio feedback.
	*
	* @return the user uuid of this portfolio feedback
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioFeedback.getUserUuid();
	}

	/**
	* Sets the user uuid of this portfolio feedback.
	*
	* @param userUuid the user uuid of this portfolio feedback
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_portfolioFeedback.setUserUuid(userUuid);
	}

	/**
	* Returns the feedback status of this portfolio feedback.
	*
	* @return the feedback status of this portfolio feedback
	*/
	@Override
	public int getFeedbackStatus() {
		return _portfolioFeedback.getFeedbackStatus();
	}

	/**
	* Sets the feedback status of this portfolio feedback.
	*
	* @param feedbackStatus the feedback status of this portfolio feedback
	*/
	@Override
	public void setFeedbackStatus(int feedbackStatus) {
		_portfolioFeedback.setFeedbackStatus(feedbackStatus);
	}

	/**
	* Returns the create date of this portfolio feedback.
	*
	* @return the create date of this portfolio feedback
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _portfolioFeedback.getCreateDate();
	}

	/**
	* Sets the create date of this portfolio feedback.
	*
	* @param createDate the create date of this portfolio feedback
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_portfolioFeedback.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this portfolio feedback.
	*
	* @return the modified date of this portfolio feedback
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _portfolioFeedback.getModifiedDate();
	}

	/**
	* Sets the modified date of this portfolio feedback.
	*
	* @param modifiedDate the modified date of this portfolio feedback
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_portfolioFeedback.setModifiedDate(modifiedDate);
	}

	@Override
	public boolean isNew() {
		return _portfolioFeedback.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portfolioFeedback.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portfolioFeedback.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portfolioFeedback.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portfolioFeedback.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portfolioFeedback.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portfolioFeedback.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portfolioFeedback.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_portfolioFeedback.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_portfolioFeedback.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portfolioFeedback.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortfolioFeedbackWrapper((PortfolioFeedback)_portfolioFeedback.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.portfolio.model.PortfolioFeedback portfolioFeedback) {
		return _portfolioFeedback.compareTo(portfolioFeedback);
	}

	@Override
	public int hashCode() {
		return _portfolioFeedback.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.portfolio.model.PortfolioFeedback> toCacheModel() {
		return _portfolioFeedback.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioFeedback toEscapedModel() {
		return new PortfolioFeedbackWrapper(_portfolioFeedback.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioFeedback toUnescapedModel() {
		return new PortfolioFeedbackWrapper(_portfolioFeedback.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portfolioFeedback.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portfolioFeedback.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portfolioFeedback.persist();
	}

	@Override
	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolioFeedback.getUser();
	}

	@Override
	public com.liferay.portal.model.Layout getPortfolio()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _portfolioFeedback.getPortfolio();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioFeedbackWrapper)) {
			return false;
		}

		PortfolioFeedbackWrapper portfolioFeedbackWrapper = (PortfolioFeedbackWrapper)obj;

		if (Validator.equals(_portfolioFeedback,
					portfolioFeedbackWrapper._portfolioFeedback)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PortfolioFeedback getWrappedPortfolioFeedback() {
		return _portfolioFeedback;
	}

	@Override
	public PortfolioFeedback getWrappedModel() {
		return _portfolioFeedback;
	}

	@Override
	public void resetOriginalValues() {
		_portfolioFeedback.resetOriginalValues();
	}

	private PortfolioFeedback _portfolioFeedback;
}