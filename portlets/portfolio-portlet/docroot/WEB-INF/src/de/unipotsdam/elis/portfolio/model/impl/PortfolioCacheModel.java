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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.unipotsdam.elis.portfolio.model.Portfolio;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Portfolio in entity cache.
 *
 * @author Matthias
 * @see Portfolio
 * @generated
 */
public class PortfolioCacheModel implements CacheModel<Portfolio>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{plid=");
		sb.append(plid);
		sb.append(", publishmentType=");
		sb.append(publishmentType);
		sb.append(", learningTemplateId=");
		sb.append(learningTemplateId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Portfolio toEntityModel() {
		PortfolioImpl portfolioImpl = new PortfolioImpl();

		portfolioImpl.setPlid(plid);
		portfolioImpl.setPublishmentType(publishmentType);

		if (learningTemplateId == null) {
			portfolioImpl.setLearningTemplateId(StringPool.BLANK);
		}
		else {
			portfolioImpl.setLearningTemplateId(learningTemplateId);
		}

		portfolioImpl.resetOriginalValues();

		return portfolioImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		plid = objectInput.readLong();
		publishmentType = objectInput.readInt();
		learningTemplateId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(plid);
		objectOutput.writeInt(publishmentType);

		if (learningTemplateId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(learningTemplateId);
		}
	}

	public long plid;
	public int publishmentType;
	public String learningTemplateId;
}