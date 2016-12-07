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

package de.unipotsdam.elis.activities.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link MoodleSocialActivity}.
 * </p>
 *
 * @author Matthias
 * @see MoodleSocialActivity
 * @generated
 */
public class MoodleSocialActivityWrapper implements MoodleSocialActivity,
	ModelWrapper<MoodleSocialActivity> {
	public MoodleSocialActivityWrapper(
		MoodleSocialActivity moodleSocialActivity) {
		_moodleSocialActivity = moodleSocialActivity;
	}

	@Override
	public Class<?> getModelClass() {
		return MoodleSocialActivity.class;
	}

	@Override
	public String getModelClassName() {
		return MoodleSocialActivity.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("extSocialActivityId", getExtSocialActivityId());
		attributes.put("userId", getUserId());
		attributes.put("type", getType());
		attributes.put("extServiceActivityType", getExtServiceActivityType());
		attributes.put("extServiceContext", getExtServiceContext());
		attributes.put("data", getData());
		attributes.put("published", getPublished());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long extSocialActivityId = (Long)attributes.get("extSocialActivityId");

		if (extSocialActivityId != null) {
			setExtSocialActivityId(extSocialActivityId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}

		String extServiceActivityType = (String)attributes.get(
				"extServiceActivityType");

		if (extServiceActivityType != null) {
			setExtServiceActivityType(extServiceActivityType);
		}

		String extServiceContext = (String)attributes.get("extServiceContext");

		if (extServiceContext != null) {
			setExtServiceContext(extServiceContext);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}

		Long published = (Long)attributes.get("published");

		if (published != null) {
			setPublished(published);
		}
	}

	/**
	* Returns the primary key of this moodle social activity.
	*
	* @return the primary key of this moodle social activity
	*/
	@Override
	public long getPrimaryKey() {
		return _moodleSocialActivity.getPrimaryKey();
	}

	/**
	* Sets the primary key of this moodle social activity.
	*
	* @param primaryKey the primary key of this moodle social activity
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_moodleSocialActivity.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the ext social activity ID of this moodle social activity.
	*
	* @return the ext social activity ID of this moodle social activity
	*/
	@Override
	public long getExtSocialActivityId() {
		return _moodleSocialActivity.getExtSocialActivityId();
	}

	/**
	* Sets the ext social activity ID of this moodle social activity.
	*
	* @param extSocialActivityId the ext social activity ID of this moodle social activity
	*/
	@Override
	public void setExtSocialActivityId(long extSocialActivityId) {
		_moodleSocialActivity.setExtSocialActivityId(extSocialActivityId);
	}

	/**
	* Returns the user ID of this moodle social activity.
	*
	* @return the user ID of this moodle social activity
	*/
	@Override
	public long getUserId() {
		return _moodleSocialActivity.getUserId();
	}

	/**
	* Sets the user ID of this moodle social activity.
	*
	* @param userId the user ID of this moodle social activity
	*/
	@Override
	public void setUserId(long userId) {
		_moodleSocialActivity.setUserId(userId);
	}

	/**
	* Returns the user uuid of this moodle social activity.
	*
	* @return the user uuid of this moodle social activity
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _moodleSocialActivity.getUserUuid();
	}

	/**
	* Sets the user uuid of this moodle social activity.
	*
	* @param userUuid the user uuid of this moodle social activity
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_moodleSocialActivity.setUserUuid(userUuid);
	}

	/**
	* Returns the type of this moodle social activity.
	*
	* @return the type of this moodle social activity
	*/
	@Override
	public int getType() {
		return _moodleSocialActivity.getType();
	}

	/**
	* Sets the type of this moodle social activity.
	*
	* @param type the type of this moodle social activity
	*/
	@Override
	public void setType(int type) {
		_moodleSocialActivity.setType(type);
	}

	/**
	* Returns the ext service activity type of this moodle social activity.
	*
	* @return the ext service activity type of this moodle social activity
	*/
	@Override
	public java.lang.String getExtServiceActivityType() {
		return _moodleSocialActivity.getExtServiceActivityType();
	}

	/**
	* Sets the ext service activity type of this moodle social activity.
	*
	* @param extServiceActivityType the ext service activity type of this moodle social activity
	*/
	@Override
	public void setExtServiceActivityType(
		java.lang.String extServiceActivityType) {
		_moodleSocialActivity.setExtServiceActivityType(extServiceActivityType);
	}

	/**
	* Returns the ext service context of this moodle social activity.
	*
	* @return the ext service context of this moodle social activity
	*/
	@Override
	public java.lang.String getExtServiceContext() {
		return _moodleSocialActivity.getExtServiceContext();
	}

	/**
	* Sets the ext service context of this moodle social activity.
	*
	* @param extServiceContext the ext service context of this moodle social activity
	*/
	@Override
	public void setExtServiceContext(java.lang.String extServiceContext) {
		_moodleSocialActivity.setExtServiceContext(extServiceContext);
	}

	/**
	* Returns the data of this moodle social activity.
	*
	* @return the data of this moodle social activity
	*/
	@Override
	public java.lang.String getData() {
		return _moodleSocialActivity.getData();
	}

	/**
	* Sets the data of this moodle social activity.
	*
	* @param data the data of this moodle social activity
	*/
	@Override
	public void setData(java.lang.String data) {
		_moodleSocialActivity.setData(data);
	}

	/**
	* Returns the published of this moodle social activity.
	*
	* @return the published of this moodle social activity
	*/
	@Override
	public long getPublished() {
		return _moodleSocialActivity.getPublished();
	}

	/**
	* Sets the published of this moodle social activity.
	*
	* @param published the published of this moodle social activity
	*/
	@Override
	public void setPublished(long published) {
		_moodleSocialActivity.setPublished(published);
	}

	@Override
	public boolean isNew() {
		return _moodleSocialActivity.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_moodleSocialActivity.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _moodleSocialActivity.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_moodleSocialActivity.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _moodleSocialActivity.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _moodleSocialActivity.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_moodleSocialActivity.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _moodleSocialActivity.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_moodleSocialActivity.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_moodleSocialActivity.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_moodleSocialActivity.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MoodleSocialActivityWrapper((MoodleSocialActivity)_moodleSocialActivity.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.activities.model.MoodleSocialActivity moodleSocialActivity) {
		return _moodleSocialActivity.compareTo(moodleSocialActivity);
	}

	@Override
	public int hashCode() {
		return _moodleSocialActivity.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.activities.model.MoodleSocialActivity> toCacheModel() {
		return _moodleSocialActivity.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity toEscapedModel() {
		return new MoodleSocialActivityWrapper(_moodleSocialActivity.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.activities.model.MoodleSocialActivity toUnescapedModel() {
		return new MoodleSocialActivityWrapper(_moodleSocialActivity.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _moodleSocialActivity.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _moodleSocialActivity.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_moodleSocialActivity.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MoodleSocialActivityWrapper)) {
			return false;
		}

		MoodleSocialActivityWrapper moodleSocialActivityWrapper = (MoodleSocialActivityWrapper)obj;

		if (Validator.equals(_moodleSocialActivity,
					moodleSocialActivityWrapper._moodleSocialActivity)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public MoodleSocialActivity getWrappedMoodleSocialActivity() {
		return _moodleSocialActivity;
	}

	@Override
	public MoodleSocialActivity getWrappedModel() {
		return _moodleSocialActivity;
	}

	@Override
	public void resetOriginalValues() {
		_moodleSocialActivity.resetOriginalValues();
	}

	private MoodleSocialActivity _moodleSocialActivity;
}