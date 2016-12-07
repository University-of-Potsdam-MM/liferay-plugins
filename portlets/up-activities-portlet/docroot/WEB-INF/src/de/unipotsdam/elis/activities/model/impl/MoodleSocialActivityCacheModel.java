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

package de.unipotsdam.elis.activities.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.unipotsdam.elis.activities.model.MoodleSocialActivity;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing MoodleSocialActivity in entity cache.
 *
 * @author Matthias
 * @see MoodleSocialActivity
 * @generated
 */
public class MoodleSocialActivityCacheModel implements CacheModel<MoodleSocialActivity>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{extSocialActivityId=");
		sb.append(extSocialActivityId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", type=");
		sb.append(type);
		sb.append(", extServiceActivityType=");
		sb.append(extServiceActivityType);
		sb.append(", extServiceContext=");
		sb.append(extServiceContext);
		sb.append(", data=");
		sb.append(data);
		sb.append(", published=");
		sb.append(published);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MoodleSocialActivity toEntityModel() {
		MoodleSocialActivityImpl moodleSocialActivityImpl = new MoodleSocialActivityImpl();

		moodleSocialActivityImpl.setExtSocialActivityId(extSocialActivityId);
		moodleSocialActivityImpl.setUserId(userId);
		moodleSocialActivityImpl.setType(type);

		if (extServiceActivityType == null) {
			moodleSocialActivityImpl.setExtServiceActivityType(StringPool.BLANK);
		}
		else {
			moodleSocialActivityImpl.setExtServiceActivityType(extServiceActivityType);
		}

		if (extServiceContext == null) {
			moodleSocialActivityImpl.setExtServiceContext(StringPool.BLANK);
		}
		else {
			moodleSocialActivityImpl.setExtServiceContext(extServiceContext);
		}

		if (data == null) {
			moodleSocialActivityImpl.setData(StringPool.BLANK);
		}
		else {
			moodleSocialActivityImpl.setData(data);
		}

		moodleSocialActivityImpl.setPublished(published);

		moodleSocialActivityImpl.resetOriginalValues();

		return moodleSocialActivityImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		extSocialActivityId = objectInput.readLong();
		userId = objectInput.readLong();
		type = objectInput.readInt();
		extServiceActivityType = objectInput.readUTF();
		extServiceContext = objectInput.readUTF();
		data = objectInput.readUTF();
		published = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(extSocialActivityId);
		objectOutput.writeLong(userId);
		objectOutput.writeInt(type);

		if (extServiceActivityType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(extServiceActivityType);
		}

		if (extServiceContext == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(extServiceContext);
		}

		if (data == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(data);
		}

		objectOutput.writeLong(published);
	}

	public long extSocialActivityId;
	public long userId;
	public int type;
	public String extServiceActivityType;
	public String extServiceContext;
	public String data;
	public long published;
}