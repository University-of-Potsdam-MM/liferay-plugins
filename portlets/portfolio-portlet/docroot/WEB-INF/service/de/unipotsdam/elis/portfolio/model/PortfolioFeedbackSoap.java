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

import de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link de.unipotsdam.elis.portfolio.service.http.PortfolioFeedbackServiceSoap}.
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.http.PortfolioFeedbackServiceSoap
 * @generated
 */
public class PortfolioFeedbackSoap implements Serializable {
	public static PortfolioFeedbackSoap toSoapModel(PortfolioFeedback model) {
		PortfolioFeedbackSoap soapModel = new PortfolioFeedbackSoap();

		soapModel.setPlid(model.getPlid());
		soapModel.setUserId(model.getUserId());
		soapModel.setFeedbackStatus(model.getFeedbackStatus());
		soapModel.setHidden(model.getHidden());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());

		return soapModel;
	}

	public static PortfolioFeedbackSoap[] toSoapModels(
		PortfolioFeedback[] models) {
		PortfolioFeedbackSoap[] soapModels = new PortfolioFeedbackSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortfolioFeedbackSoap[][] toSoapModels(
		PortfolioFeedback[][] models) {
		PortfolioFeedbackSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PortfolioFeedbackSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortfolioFeedbackSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortfolioFeedbackSoap[] toSoapModels(
		List<PortfolioFeedback> models) {
		List<PortfolioFeedbackSoap> soapModels = new ArrayList<PortfolioFeedbackSoap>(models.size());

		for (PortfolioFeedback model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PortfolioFeedbackSoap[soapModels.size()]);
	}

	public PortfolioFeedbackSoap() {
	}

	public PortfolioFeedbackPK getPrimaryKey() {
		return new PortfolioFeedbackPK(_plid, _userId);
	}

	public void setPrimaryKey(PortfolioFeedbackPK pk) {
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