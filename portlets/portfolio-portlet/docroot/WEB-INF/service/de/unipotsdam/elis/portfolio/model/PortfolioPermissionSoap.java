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

import de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link de.unipotsdam.elis.portfolio.service.http.PortfolioPermissionServiceSoap}.
 *
 * @author Matthias
 * @see de.unipotsdam.elis.portfolio.service.http.PortfolioPermissionServiceSoap
 * @generated
 */
public class PortfolioPermissionSoap implements Serializable {
	public static PortfolioPermissionSoap toSoapModel(PortfolioPermission model) {
		PortfolioPermissionSoap soapModel = new PortfolioPermissionSoap();

		soapModel.setPlid(model.getPlid());
		soapModel.setUserId(model.getUserId());

		return soapModel;
	}

	public static PortfolioPermissionSoap[] toSoapModels(
		PortfolioPermission[] models) {
		PortfolioPermissionSoap[] soapModels = new PortfolioPermissionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static PortfolioPermissionSoap[][] toSoapModels(
		PortfolioPermission[][] models) {
		PortfolioPermissionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new PortfolioPermissionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new PortfolioPermissionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static PortfolioPermissionSoap[] toSoapModels(
		List<PortfolioPermission> models) {
		List<PortfolioPermissionSoap> soapModels = new ArrayList<PortfolioPermissionSoap>(models.size());

		for (PortfolioPermission model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new PortfolioPermissionSoap[soapModels.size()]);
	}

	public PortfolioPermissionSoap() {
	}

	public PortfolioPermissionPK getPrimaryKey() {
		return new PortfolioPermissionPK(_plid, _userId);
	}

	public void setPrimaryKey(PortfolioPermissionPK pk) {
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

	private long _plid;
	private long _userId;
}