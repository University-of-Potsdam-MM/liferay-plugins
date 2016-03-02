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

import de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link de.unipotsdam.elis.custompages.service.http.CustomPageFeedbackServiceSoap}.
 *
 * @author Matthias
 * @see de.unipotsdam.elis.custompages.service.http.CustomPageFeedbackServiceSoap
 * @generated
 */
public class CustomPageFeedbackSoap implements Serializable {
	public static CustomPageFeedbackSoap toSoapModel(CustomPageFeedback model) {
		CustomPageFeedbackSoap soapModel = new CustomPageFeedbackSoap();

		soapModel.setPlid(model.getPlid());
		soapModel.setUserId(model.getUserId());
		soapModel.setFeedbackStatus(model.getFeedbackStatus());
		soapModel.setHidden(model.getHidden());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static CustomPageFeedbackSoap[] toSoapModels(
		CustomPageFeedback[] models) {
		CustomPageFeedbackSoap[] soapModels = new CustomPageFeedbackSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CustomPageFeedbackSoap[][] toSoapModels(
		CustomPageFeedback[][] models) {
		CustomPageFeedbackSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CustomPageFeedbackSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CustomPageFeedbackSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CustomPageFeedbackSoap[] toSoapModels(
		List<CustomPageFeedback> models) {
		List<CustomPageFeedbackSoap> soapModels = new ArrayList<CustomPageFeedbackSoap>(models.size());

		for (CustomPageFeedback model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CustomPageFeedbackSoap[soapModels.size()]);
	}

	public CustomPageFeedbackSoap() {
	}

	public CustomPageFeedbackPK getPrimaryKey() {
		return new CustomPageFeedbackPK(_plid, _userId);
	}

	public void setPrimaryKey(CustomPageFeedbackPK pk) {
		setPlid(pk.plid);
		setUserId(pk.userId);
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public int getFeedbackStatus() {
		return _feedbackStatus;
	}

	public void setFeedbackStatus(int feedbackStatus) {
		_feedbackStatus = feedbackStatus;
	}

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	private long _plid;
	private long _userId;
	private int _feedbackStatus;
	private boolean _hidden;
	private Date _createDate;
	private Date _modifiedDate;
}