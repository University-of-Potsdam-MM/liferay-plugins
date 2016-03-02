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

package de.unipotsdam.elis.custompages.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CustomPageFeedback}.
 * </p>
 *
 * @author Matthias
 * @see CustomPageFeedback
 * @generated
 */
public class CustomPageFeedbackWrapper implements CustomPageFeedback,
	ModelWrapper<CustomPageFeedback> {
	public CustomPageFeedbackWrapper(CustomPageFeedback customPageFeedback) {
		_customPageFeedback = customPageFeedback;
	}

	@Override
	public Class<?> getModelClass() {
		return CustomPageFeedback.class;
	}

	@Override
	public String getModelClassName() {
		return CustomPageFeedback.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("userId", getUserId());
		attributes.put("feedbackStatus", getFeedbackStatus());
		attributes.put("hidden", getHidden());
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

		Boolean hidden = (Boolean)attributes.get("hidden");

		if (hidden != null) {
			setHidden(hidden);
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
	* Returns the primary key of this custom page feedback.
	*
	* @return the primary key of this custom page feedback
	*/
	@Override
	public de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK getPrimaryKey() {
		return _customPageFeedback.getPrimaryKey();
	}

	/**
	* Sets the primary key of this custom page feedback.
	*
	* @param primaryKey the primary key of this custom page feedback
	*/
	@Override
	public void setPrimaryKey(
		de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK primaryKey) {
		_customPageFeedback.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plid of this custom page feedback.
	*
	* @return the plid of this custom page feedback
	*/
	@Override
	public long getPlid() {
		return _customPageFeedback.getPlid();
	}

	/**
	* Sets the plid of this custom page feedback.
	*
	* @param plid the plid of this custom page feedback
	*/
	@Override
	public void setPlid(long plid) {
		_customPageFeedback.setPlid(plid);
	}

	/**
	* Returns the user ID of this custom page feedback.
	*
	* @return the user ID of this custom page feedback
	*/
	@Override
	public long getUserId() {
		return _customPageFeedback.getUserId();
	}

	/**
	* Sets the user ID of this custom page feedback.
	*
	* @param userId the user ID of this custom page feedback
	*/
	@Override
	public void setUserId(long userId) {
		_customPageFeedback.setUserId(userId);
	}

	/**
	* Returns the user uuid of this custom page feedback.
	*
	* @return the user uuid of this custom page feedback
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedback.getUserUuid();
	}

	/**
	* Sets the user uuid of this custom page feedback.
	*
	* @param userUuid the user uuid of this custom page feedback
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_customPageFeedback.setUserUuid(userUuid);
	}

	/**
	* Returns the feedback status of this custom page feedback.
	*
	* @return the feedback status of this custom page feedback
	*/
	@Override
	public int getFeedbackStatus() {
		return _customPageFeedback.getFeedbackStatus();
	}

	/**
	* Sets the feedback status of this custom page feedback.
	*
	* @param feedbackStatus the feedback status of this custom page feedback
	*/
	@Override
	public void setFeedbackStatus(int feedbackStatus) {
		_customPageFeedback.setFeedbackStatus(feedbackStatus);
	}

	/**
	* Returns the hidden of this custom page feedback.
	*
	* @return the hidden of this custom page feedback
	*/
	@Override
	public boolean getHidden() {
		return _customPageFeedback.getHidden();
	}

	/**
	* Returns <code>true</code> if this custom page feedback is hidden.
	*
	* @return <code>true</code> if this custom page feedback is hidden; <code>false</code> otherwise
	*/
	@Override
	public boolean isHidden() {
		return _customPageFeedback.isHidden();
	}

	/**
	* Sets whether this custom page feedback is hidden.
	*
	* @param hidden the hidden of this custom page feedback
	*/
	@Override
	public void setHidden(boolean hidden) {
		_customPageFeedback.setHidden(hidden);
	}

	/**
	* Returns the create date of this custom page feedback.
	*
	* @return the create date of this custom page feedback
	*/
	@Override
	public java.util.Date getCreateDate() {
		return _customPageFeedback.getCreateDate();
	}

	/**
	* Sets the create date of this custom page feedback.
	*
	* @param createDate the create date of this custom page feedback
	*/
	@Override
	public void setCreateDate(java.util.Date createDate) {
		_customPageFeedback.setCreateDate(createDate);
	}

	/**
	* Returns the modified date of this custom page feedback.
	*
	* @return the modified date of this custom page feedback
	*/
	@Override
	public java.util.Date getModifiedDate() {
		return _customPageFeedback.getModifiedDate();
	}

	/**
	* Sets the modified date of this custom page feedback.
	*
	* @param modifiedDate the modified date of this custom page feedback
	*/
	@Override
	public void setModifiedDate(java.util.Date modifiedDate) {
		_customPageFeedback.setModifiedDate(modifiedDate);
	}

	@Override
	public boolean isNew() {
		return _customPageFeedback.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_customPageFeedback.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _customPageFeedback.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_customPageFeedback.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _customPageFeedback.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _customPageFeedback.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_customPageFeedback.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _customPageFeedback.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_customPageFeedback.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_customPageFeedback.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_customPageFeedback.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new CustomPageFeedbackWrapper((CustomPageFeedback)_customPageFeedback.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.custompages.model.CustomPageFeedback customPageFeedback) {
		return _customPageFeedback.compareTo(customPageFeedback);
	}

	@Override
	public int hashCode() {
		return _customPageFeedback.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.custompages.model.CustomPageFeedback> toCacheModel() {
		return _customPageFeedback.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback toEscapedModel() {
		return new CustomPageFeedbackWrapper(_customPageFeedback.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.custompages.model.CustomPageFeedback toUnescapedModel() {
		return new CustomPageFeedbackWrapper(_customPageFeedback.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _customPageFeedback.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _customPageFeedback.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_customPageFeedback.persist();
	}

	@Override
	public com.liferay.portal.model.User getUser()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedback.getUser();
	}

	@Override
	public com.liferay.portal.model.Layout getCustomPage()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _customPageFeedback.getCustomPage();
	}

	@Override
	public void changeVisibility()
		throws com.liferay.portal.kernel.exception.SystemException {
		_customPageFeedback.changeVisibility();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CustomPageFeedbackWrapper)) {
			return false;
		}

		CustomPageFeedbackWrapper customPageFeedbackWrapper = (CustomPageFeedbackWrapper)obj;

		if (Validator.equals(_customPageFeedback,
					customPageFeedbackWrapper._customPageFeedback)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public CustomPageFeedback getWrappedCustomPageFeedback() {
		return _customPageFeedback;
	}

	@Override
	public CustomPageFeedback getWrappedModel() {
		return _customPageFeedback;
	}

	@Override
	public void resetOriginalValues() {
		_customPageFeedback.resetOriginalValues();
	}

	private CustomPageFeedback _customPageFeedback;
}