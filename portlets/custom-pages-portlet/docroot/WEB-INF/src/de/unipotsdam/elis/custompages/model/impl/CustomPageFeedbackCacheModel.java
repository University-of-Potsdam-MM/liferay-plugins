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

package de.unipotsdam.elis.custompages.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CustomPageFeedback in entity cache.
 *
 * @author Matthias
 * @see CustomPageFeedback
 * @generated
 */
public class CustomPageFeedbackCacheModel implements CacheModel<CustomPageFeedback>,
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
	public CustomPageFeedback toEntityModel() {
		CustomPageFeedbackImpl customPageFeedbackImpl = new CustomPageFeedbackImpl();

		customPageFeedbackImpl.setPlid(plid);
		customPageFeedbackImpl.setUserId(userId);
		customPageFeedbackImpl.setFeedbackStatus(feedbackStatus);
		customPageFeedbackImpl.setHidden(hidden);

		if (createDate == Long.MIN_VALUE) {
			customPageFeedbackImpl.setCreateDate(null);
		}
		else {
			customPageFeedbackImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			customPageFeedbackImpl.setModifiedDate(null);
		}
		else {
			customPageFeedbackImpl.setModifiedDate(new Date(modifiedDate));
		}

		customPageFeedbackImpl.resetOriginalValues();

		return customPageFeedbackImpl;
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