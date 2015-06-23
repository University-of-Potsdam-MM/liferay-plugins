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
 * This class is a wrapper for {@link PortfolioPermission}.
 * </p>
 *
 * @author Matthias
 * @see PortfolioPermission
 * @generated
 */
public class PortfolioPermissionWrapper implements PortfolioPermission,
	ModelWrapper<PortfolioPermission> {
	public PortfolioPermissionWrapper(PortfolioPermission portfolioPermission) {
		_portfolioPermission = portfolioPermission;
	}

	@Override
	public Class<?> getModelClass() {
		return PortfolioPermission.class;
	}

	@Override
	public String getModelClassName() {
		return PortfolioPermission.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("plid", getPlid());
		attributes.put("userId", getUserId());

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
	}

	/**
	* Returns the primary key of this portfolio permission.
	*
	* @return the primary key of this portfolio permission
	*/
	@Override
	public de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK getPrimaryKey() {
		return _portfolioPermission.getPrimaryKey();
	}

	/**
	* Sets the primary key of this portfolio permission.
	*
	* @param primaryKey the primary key of this portfolio permission
	*/
	@Override
	public void setPrimaryKey(
		de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK primaryKey) {
		_portfolioPermission.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the plid of this portfolio permission.
	*
	* @return the plid of this portfolio permission
	*/
	@Override
	public long getPlid() {
		return _portfolioPermission.getPlid();
	}

	/**
	* Sets the plid of this portfolio permission.
	*
	* @param plid the plid of this portfolio permission
	*/
	@Override
	public void setPlid(long plid) {
		_portfolioPermission.setPlid(plid);
	}

	/**
	* Returns the user ID of this portfolio permission.
	*
	* @return the user ID of this portfolio permission
	*/
	@Override
	public long getUserId() {
		return _portfolioPermission.getUserId();
	}

	/**
	* Sets the user ID of this portfolio permission.
	*
	* @param userId the user ID of this portfolio permission
	*/
	@Override
	public void setUserId(long userId) {
		_portfolioPermission.setUserId(userId);
	}

	/**
	* Returns the user uuid of this portfolio permission.
	*
	* @return the user uuid of this portfolio permission
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _portfolioPermission.getUserUuid();
	}

	/**
	* Sets the user uuid of this portfolio permission.
	*
	* @param userUuid the user uuid of this portfolio permission
	*/
	@Override
	public void setUserUuid(java.lang.String userUuid) {
		_portfolioPermission.setUserUuid(userUuid);
	}

	@Override
	public boolean isNew() {
		return _portfolioPermission.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_portfolioPermission.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _portfolioPermission.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_portfolioPermission.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _portfolioPermission.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _portfolioPermission.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_portfolioPermission.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _portfolioPermission.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_portfolioPermission.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_portfolioPermission.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_portfolioPermission.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new PortfolioPermissionWrapper((PortfolioPermission)_portfolioPermission.clone());
	}

	@Override
	public int compareTo(
		de.unipotsdam.elis.portfolio.model.PortfolioPermission portfolioPermission) {
		return _portfolioPermission.compareTo(portfolioPermission);
	}

	@Override
	public int hashCode() {
		return _portfolioPermission.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<de.unipotsdam.elis.portfolio.model.PortfolioPermission> toCacheModel() {
		return _portfolioPermission.toCacheModel();
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission toEscapedModel() {
		return new PortfolioPermissionWrapper(_portfolioPermission.toEscapedModel());
	}

	@Override
	public de.unipotsdam.elis.portfolio.model.PortfolioPermission toUnescapedModel() {
		return new PortfolioPermissionWrapper(_portfolioPermission.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _portfolioPermission.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _portfolioPermission.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_portfolioPermission.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PortfolioPermissionWrapper)) {
			return false;
		}

		PortfolioPermissionWrapper portfolioPermissionWrapper = (PortfolioPermissionWrapper)obj;

		if (Validator.equals(_portfolioPermission,
					portfolioPermissionWrapper._portfolioPermission)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public PortfolioPermission getWrappedPortfolioPermission() {
		return _portfolioPermission;
	}

	@Override
	public PortfolioPermission getWrappedModel() {
		return _portfolioPermission;
	}

	@Override
	public void resetOriginalValues() {
		_portfolioPermission.resetOriginalValues();
	}

	private PortfolioPermission _portfolioPermission;
}