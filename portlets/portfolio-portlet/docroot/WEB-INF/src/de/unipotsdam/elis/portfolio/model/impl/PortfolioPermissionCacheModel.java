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

package de.unipotsdam.elis.portfolio.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import de.unipotsdam.elis.portfolio.model.PortfolioPermission;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing PortfolioPermission in entity cache.
 *
 * @author Matthias
 * @see PortfolioPermission
 * @generated
 */
public class PortfolioPermissionCacheModel implements CacheModel<PortfolioPermission>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{plid=");
		sb.append(plid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortfolioPermission toEntityModel() {
		PortfolioPermissionImpl portfolioPermissionImpl = new PortfolioPermissionImpl();

		portfolioPermissionImpl.setPlid(plid);
		portfolioPermissionImpl.setUserId(userId);

		portfolioPermissionImpl.resetOriginalValues();

		return portfolioPermissionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		plid = objectInput.readLong();
		userId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(plid);
		objectOutput.writeLong(userId);
	}

	public long plid;
	public long userId;
}