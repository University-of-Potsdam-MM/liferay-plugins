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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link de.unipotsdam.elis.portfolio.service.http.PortfolioServiceSoap}.
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.http.PortfolioServiceSoap
 * @generated
 */
public class PortfolioSoap implements Serializable {
	public static PortfolioSoap toSoapModel(Portfolio model) {
		PortfolioSoap soapModel = new PortfolioSoap();

		soapModel.setPlid(model.getPlid());
		soapModel.setPublishmentType(model.getPublishmentType());
		soapModel.setLearningTemplateId(model.getLearningTemplateId());

		return soapModel;
	}

	public static PortfolioSoap[] toSoapModels(Portfolio[] models) {
		PortfolioSoap[] soapModels = new PortfolioSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortfolioSoap[][] toSoapModels(Portfolio[][] models) {
		PortfolioSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PortfolioSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortfolioSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortfolioSoap[] toSoapModels(List<Portfolio> models) {
		List<PortfolioSoap> soapModels = new ArrayList<PortfolioSoap>(models.size());

		for (Portfolio model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PortfolioSoap[soapModels.size()]);
	}

	public PortfolioSoap() {
	}

	public long getPrimaryKey() {
		return _plid;
	}

	public void setPrimaryKey(long pk) {
		setPlid(pk);
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public int getPublishmentType() {
		return _publishmentType;
	}

	public void setPublishmentType(int publishmentType) {
		_publishmentType = publishmentType;
	}

	public String getLearningTemplateId() {
		return _learningTemplateId;
	}

	public void setLearningTemplateId(String learningTemplateId) {
		_learningTemplateId = learningTemplateId;
	}

	private long _plid;
	private int _publishmentType;
	private String _learningTemplateId;
}