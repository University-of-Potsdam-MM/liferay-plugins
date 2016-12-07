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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthias
 * @generated
 */
public class MoodleSocialActivitySoap implements Serializable {
	public static MoodleSocialActivitySoap toSoapModel(
		MoodleSocialActivity model) {
		MoodleSocialActivitySoap soapModel = new MoodleSocialActivitySoap();

		soapModel.setExtSocialActivityId(model.getExtSocialActivityId());
		soapModel.setUserId(model.getUserId());
		soapModel.setType(model.getType());
		soapModel.setExtServiceActivityType(model.getExtServiceActivityType());
		soapModel.setExtServiceContext(model.getExtServiceContext());
		soapModel.setData(model.getData());
		soapModel.setPublished(model.getPublished());

		return soapModel;
	}

	public static MoodleSocialActivitySoap[] toSoapModels(
		MoodleSocialActivity[] models) {
		MoodleSocialActivitySoap[] soapModels = new MoodleSocialActivitySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MoodleSocialActivitySoap[][] toSoapModels(
		MoodleSocialActivity[][] models) {
		MoodleSocialActivitySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MoodleSocialActivitySoap[models.length][models[0].length];
		}
		else {
			soapModels = new MoodleSocialActivitySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MoodleSocialActivitySoap[] toSoapModels(
		List<MoodleSocialActivity> models) {
		List<MoodleSocialActivitySoap> soapModels = new ArrayList<MoodleSocialActivitySoap>(models.size());

		for (MoodleSocialActivity model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MoodleSocialActivitySoap[soapModels.size()]);
	}

	public MoodleSocialActivitySoap() {
	}

	public long getPrimaryKey() {
		return _extSocialActivityId;
	}

	public void setPrimaryKey(long pk) {
		setExtSocialActivityId(pk);
	}

	public long getExtSocialActivityId() {
		return _extSocialActivityId;
	}

	public void setExtSocialActivityId(long extSocialActivityId) {
		_extSocialActivityId = extSocialActivityId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public String getExtServiceActivityType() {
		return _extServiceActivityType;
	}

	public void setExtServiceActivityType(String extServiceActivityType) {
		_extServiceActivityType = extServiceActivityType;
	}

	public String getExtServiceContext() {
		return _extServiceContext;
	}

	public void setExtServiceContext(String extServiceContext) {
		_extServiceContext = extServiceContext;
	}

	public String getData() {
		return _data;
	}

	public void setData(String data) {
		_data = data;
	}

	public long getPublished() {
		return _published;
	}

	public void setPublished(long published) {
		_published = published;
	}

	private long _extSocialActivityId;
	private long _userId;
	private int _type;
	private String _extServiceActivityType;
	private String _extServiceContext;
	private String _data;
	private long _published;
}