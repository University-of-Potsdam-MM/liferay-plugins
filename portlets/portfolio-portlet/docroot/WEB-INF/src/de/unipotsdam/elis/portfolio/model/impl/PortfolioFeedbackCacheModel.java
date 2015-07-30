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

import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PortfolioFeedback in entity cache.
 *
 * @author Matthias
 * @see PortfolioFeedback
 * @generated
 */
public class PortfolioFeedbackCacheModel implements CacheModel<PortfolioFeedback>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(13);

		sb.append("{plid=");
		sb.append(plid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", feedbackStatus=");
		sb.append(feedbackStatus);
		sb.append(", hidden=");
		sb.append(hidden);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortfolioFeedback toEntityModel() {
		PortfolioFeedbackImpl portfolioFeedbackImpl = new PortfolioFeedbackImpl();

		portfolioFeedbackImpl.setPlid(plid);
		portfolioFeedbackImpl.setUserId(userId);
		portfolioFeedbackImpl.setFeedbackStatus(feedbackStatus);
		portfolioFeedbackImpl.setHidden(hidden);

		if (createDate == Long.MIN_VALUE) {
			portfolioFeedbackImpl.setCreateDate(null);
		}
		else {
			portfolioFeedbackImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			portfolioFeedbackImpl.setModifiedDate(null);
		}
		else {
			portfolioFeedbackImpl.setModifiedDate(new Date(modifiedDate));
		}

		portfolioFeedbackImpl.resetOriginalValues();

		return portfolioFeedbackImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		plid = objectInput.readLong();
		userId = objectInput.readLong();
		feedbackStatus = objectInput.readInt();
		hidden = objectInput.readBoolean();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(plid);
		objectOutput.writeLong(userId);
		objectOutput.writeInt(feedbackStatus);
		objectOutput.writeBoolean(hidden);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
	}

	public long plid;
	public long userId;
	public int feedbackStatus;
	public boolean hidden;
	public long createDate;
	public long modifiedDate;
}