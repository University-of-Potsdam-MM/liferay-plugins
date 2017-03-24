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

package de.unipotsdam.elis.language.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Matthias
 * @generated
 */
public class LanguageKeySoap implements Serializable {
	public static LanguageKeySoap toSoapModel(LanguageKey model) {
		LanguageKeySoap soapModel = new LanguageKeySoap();

		soapModel.setKey(model.getKey());
		soapModel.setValue(model.getValue());
		soapModel.setTooltipContent(model.getTooltipContent());

		return soapModel;
	}

	public static LanguageKeySoap[] toSoapModels(LanguageKey[] models) {
		LanguageKeySoap[] soapModels = new LanguageKeySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LanguageKeySoap[][] toSoapModels(LanguageKey[][] models) {
		LanguageKeySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LanguageKeySoap[models.length][models[0].length];
		}
		else {
			soapModels = new LanguageKeySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LanguageKeySoap[] toSoapModels(List<LanguageKey> models) {
		List<LanguageKeySoap> soapModels = new ArrayList<LanguageKeySoap>(models.size());

		for (LanguageKey model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LanguageKeySoap[soapModels.size()]);
	}

	public LanguageKeySoap() {
	}

	public String getPrimaryKey() {
		return _key;
	}

	public void setPrimaryKey(String pk) {
		setKey(pk);
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getValue() {
		return _value;
	}

	public void setValue(String value) {
		_value = value;
	}

	public String getTooltipContent() {
		return _tooltipContent;
	}

	public void setTooltipContent(String tooltipContent) {
		_tooltipContent = tooltipContent;
	}

	private String _key;
	private String _value;
	private String _tooltipContent;
}