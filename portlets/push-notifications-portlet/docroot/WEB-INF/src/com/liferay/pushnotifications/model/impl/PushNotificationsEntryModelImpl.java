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

package com.liferay.pushnotifications.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSON;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import com.liferay.pushnotifications.model.PushNotificationsEntry;
import com.liferay.pushnotifications.model.PushNotificationsEntryModel;
import com.liferay.pushnotifications.model.PushNotificationsEntrySoap;

import java.io.Serializable;

import java.sql.Types;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The base model implementation for the PushNotificationsEntry service. Represents a row in the &quot;PushNotificationsEntry&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.liferay.pushnotifications.model.PushNotificationsEntryModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link PushNotificationsEntryImpl}.
 * </p>
 *
 * @author Bruno Farache
 * @see PushNotificationsEntryImpl
 * @see com.liferay.pushnotifications.model.PushNotificationsEntry
 * @see com.liferay.pushnotifications.model.PushNotificationsEntryModel
 * @generated
 */
@JSON(strict = true)
public class PushNotificationsEntryModelImpl extends BaseModelImpl<PushNotificationsEntry>
	implements PushNotificationsEntryModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a push notifications entry model instance should use the {@link com.liferay.pushnotifications.model.PushNotificationsEntry} interface instead.
	 */
	public static final String TABLE_NAME = "PushNotificationsEntry";
	public static final Object[][] TABLE_COLUMNS = {
			{ "pushNotificationsEntryId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "createTime", Types.BIGINT },
			{ "parentPushNotificationsEntryId", Types.BIGINT },
			{ "childrenPushNotificationsEntriesCount", Types.INTEGER },
			{ "payload", Types.VARCHAR },
			{ "ratingsTotalScore", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table PushNotificationsEntry (pushNotificationsEntryId LONG not null primary key,userId LONG,createTime LONG,parentPushNotificationsEntryId LONG,childrenPushNotificationsEntriesCount INTEGER,payload STRING null,ratingsTotalScore LONG)";
	public static final String TABLE_SQL_DROP = "drop table PushNotificationsEntry";
	public static final String ORDER_BY_JPQL = " ORDER BY pushNotificationsEntry.createTime ASC";
	public static final String ORDER_BY_SQL = " ORDER BY PushNotificationsEntry.createTime ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.pushnotifications.model.PushNotificationsEntry"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.pushnotifications.model.PushNotificationsEntry"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.pushnotifications.model.PushNotificationsEntry"),
			true);
	public static long CREATETIME_COLUMN_BITMASK = 1L;
	public static long PARENTPUSHNOTIFICATIONSENTRYID_COLUMN_BITMASK = 2L;

	/**
	 * Converts the soap model instance into a normal model instance.
	 *
	 * @param soapModel the soap model instance to convert
	 * @return the normal model instance
	 */
	public static PushNotificationsEntry toModel(
		PushNotificationsEntrySoap soapModel) {
		if (soapModel == null) {
			return null;
		}

		PushNotificationsEntry model = new PushNotificationsEntryImpl();

		model.setPushNotificationsEntryId(soapModel.getPushNotificationsEntryId());
		model.setUserId(soapModel.getUserId());
		model.setCreateTime(soapModel.getCreateTime());
		model.setParentPushNotificationsEntryId(soapModel.getParentPushNotificationsEntryId());
		model.setChildrenPushNotificationsEntriesCount(soapModel.getChildrenPushNotificationsEntriesCount());
		model.setPayload(soapModel.getPayload());
		model.setRatingsTotalScore(soapModel.getRatingsTotalScore());

		return model;
	}

	/**
	 * Converts the soap model instances into normal model instances.
	 *
	 * @param soapModels the soap model instances to convert
	 * @return the normal model instances
	 */
	public static List<PushNotificationsEntry> toModels(
		PushNotificationsEntrySoap[] soapModels) {
		if (soapModels == null) {
			return null;
		}

		List<PushNotificationsEntry> models = new ArrayList<PushNotificationsEntry>(soapModels.length);

		for (PushNotificationsEntrySoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.liferay.pushnotifications.model.PushNotificationsEntry"));

	public PushNotificationsEntryModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _pushNotificationsEntryId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPushNotificationsEntryId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _pushNotificationsEntryId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return PushNotificationsEntry.class;
	}

	@Override
	public String getModelClassName() {
		return PushNotificationsEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pushNotificationsEntryId", getPushNotificationsEntryId());
		attributes.put("userId", getUserId());
		attributes.put("createTime", getCreateTime());
		attributes.put("parentPushNotificationsEntryId",
			getParentPushNotificationsEntryId());
		attributes.put("childrenPushNotificationsEntriesCount",
			getChildrenPushNotificationsEntriesCount());
		attributes.put("payload", getPayload());
		attributes.put("ratingsTotalScore", getRatingsTotalScore());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pushNotificationsEntryId = (Long)attributes.get(
				"pushNotificationsEntryId");

		if (pushNotificationsEntryId != null) {
			setPushNotificationsEntryId(pushNotificationsEntryId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long parentPushNotificationsEntryId = (Long)attributes.get(
				"parentPushNotificationsEntryId");

		if (parentPushNotificationsEntryId != null) {
			setParentPushNotificationsEntryId(parentPushNotificationsEntryId);
		}

		Integer childrenPushNotificationsEntriesCount = (Integer)attributes.get(
				"childrenPushNotificationsEntriesCount");

		if (childrenPushNotificationsEntriesCount != null) {
			setChildrenPushNotificationsEntriesCount(childrenPushNotificationsEntriesCount);
		}

		String payload = (String)attributes.get("payload");

		if (payload != null) {
			setPayload(payload);
		}

		Long ratingsTotalScore = (Long)attributes.get("ratingsTotalScore");

		if (ratingsTotalScore != null) {
			setRatingsTotalScore(ratingsTotalScore);
		}
	}

	@JSON
	@Override
	public long getPushNotificationsEntryId() {
		return _pushNotificationsEntryId;
	}

	@Override
	public void setPushNotificationsEntryId(long pushNotificationsEntryId) {
		_pushNotificationsEntryId = pushNotificationsEntryId;
	}

	@JSON
	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@JSON
	@Override
	public long getCreateTime() {
		return _createTime;
	}

	@Override
	public void setCreateTime(long createTime) {
		_columnBitmask = -1L;

		if (!_setOriginalCreateTime) {
			_setOriginalCreateTime = true;

			_originalCreateTime = _createTime;
		}

		_createTime = createTime;
	}

	public long getOriginalCreateTime() {
		return _originalCreateTime;
	}

	@JSON
	@Override
	public long getParentPushNotificationsEntryId() {
		return _parentPushNotificationsEntryId;
	}

	@Override
	public void setParentPushNotificationsEntryId(
		long parentPushNotificationsEntryId) {
		_columnBitmask |= PARENTPUSHNOTIFICATIONSENTRYID_COLUMN_BITMASK;

		if (!_setOriginalParentPushNotificationsEntryId) {
			_setOriginalParentPushNotificationsEntryId = true;

			_originalParentPushNotificationsEntryId = _parentPushNotificationsEntryId;
		}

		_parentPushNotificationsEntryId = parentPushNotificationsEntryId;
	}

	public long getOriginalParentPushNotificationsEntryId() {
		return _originalParentPushNotificationsEntryId;
	}

	@JSON
	@Override
	public int getChildrenPushNotificationsEntriesCount() {
		return _childrenPushNotificationsEntriesCount;
	}

	@Override
	public void setChildrenPushNotificationsEntriesCount(
		int childrenPushNotificationsEntriesCount) {
		_childrenPushNotificationsEntriesCount = childrenPushNotificationsEntriesCount;
	}

	@JSON
	@Override
	public String getPayload() {
		if (_payload == null) {
			return StringPool.BLANK;
		}
		else {
			return _payload;
		}
	}

	@Override
	public void setPayload(String payload) {
		_payload = payload;
	}

	@JSON
	@Override
	public long getRatingsTotalScore() {
		return _ratingsTotalScore;
	}

	@Override
	public void setRatingsTotalScore(long ratingsTotalScore) {
		_ratingsTotalScore = ratingsTotalScore;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			PushNotificationsEntry.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public PushNotificationsEntry toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (PushNotificationsEntry)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		PushNotificationsEntryImpl pushNotificationsEntryImpl = new PushNotificationsEntryImpl();

		pushNotificationsEntryImpl.setPushNotificationsEntryId(getPushNotificationsEntryId());
		pushNotificationsEntryImpl.setUserId(getUserId());
		pushNotificationsEntryImpl.setCreateTime(getCreateTime());
		pushNotificationsEntryImpl.setParentPushNotificationsEntryId(getParentPushNotificationsEntryId());
		pushNotificationsEntryImpl.setChildrenPushNotificationsEntriesCount(getChildrenPushNotificationsEntriesCount());
		pushNotificationsEntryImpl.setPayload(getPayload());
		pushNotificationsEntryImpl.setRatingsTotalScore(getRatingsTotalScore());

		pushNotificationsEntryImpl.resetOriginalValues();

		return pushNotificationsEntryImpl;
	}

	@Override
	public int compareTo(PushNotificationsEntry pushNotificationsEntry) {
		int value = 0;

		if (getCreateTime() < pushNotificationsEntry.getCreateTime()) {
			value = -1;
		}
		else if (getCreateTime() > pushNotificationsEntry.getCreateTime()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof PushNotificationsEntry)) {
			return false;
		}

		PushNotificationsEntry pushNotificationsEntry = (PushNotificationsEntry)obj;

		long primaryKey = pushNotificationsEntry.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		PushNotificationsEntryModelImpl pushNotificationsEntryModelImpl = this;

		pushNotificationsEntryModelImpl._originalCreateTime = pushNotificationsEntryModelImpl._createTime;

		pushNotificationsEntryModelImpl._setOriginalCreateTime = false;

		pushNotificationsEntryModelImpl._originalParentPushNotificationsEntryId = pushNotificationsEntryModelImpl._parentPushNotificationsEntryId;

		pushNotificationsEntryModelImpl._setOriginalParentPushNotificationsEntryId = false;

		pushNotificationsEntryModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<PushNotificationsEntry> toCacheModel() {
		PushNotificationsEntryCacheModel pushNotificationsEntryCacheModel = new PushNotificationsEntryCacheModel();

		pushNotificationsEntryCacheModel.pushNotificationsEntryId = getPushNotificationsEntryId();

		pushNotificationsEntryCacheModel.userId = getUserId();

		pushNotificationsEntryCacheModel.createTime = getCreateTime();

		pushNotificationsEntryCacheModel.parentPushNotificationsEntryId = getParentPushNotificationsEntryId();

		pushNotificationsEntryCacheModel.childrenPushNotificationsEntriesCount = getChildrenPushNotificationsEntriesCount();

		pushNotificationsEntryCacheModel.payload = getPayload();

		String payload = pushNotificationsEntryCacheModel.payload;

		if ((payload != null) && (payload.length() == 0)) {
			pushNotificationsEntryCacheModel.payload = null;
		}

		pushNotificationsEntryCacheModel.ratingsTotalScore = getRatingsTotalScore();

		return pushNotificationsEntryCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{pushNotificationsEntryId=");
		sb.append(getPushNotificationsEntryId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", createTime=");
		sb.append(getCreateTime());
		sb.append(", parentPushNotificationsEntryId=");
		sb.append(getParentPushNotificationsEntryId());
		sb.append(", childrenPushNotificationsEntriesCount=");
		sb.append(getChildrenPushNotificationsEntriesCount());
		sb.append(", payload=");
		sb.append(getPayload());
		sb.append(", ratingsTotalScore=");
		sb.append(getRatingsTotalScore());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(25);

		sb.append("<model><model-name>");
		sb.append("com.liferay.pushnotifications.model.PushNotificationsEntry");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pushNotificationsEntryId</column-name><column-value><![CDATA[");
		sb.append(getPushNotificationsEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createTime</column-name><column-value><![CDATA[");
		sb.append(getCreateTime());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentPushNotificationsEntryId</column-name><column-value><![CDATA[");
		sb.append(getParentPushNotificationsEntryId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>childrenPushNotificationsEntriesCount</column-name><column-value><![CDATA[");
		sb.append(getChildrenPushNotificationsEntriesCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>payload</column-name><column-value><![CDATA[");
		sb.append(getPayload());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ratingsTotalScore</column-name><column-value><![CDATA[");
		sb.append(getRatingsTotalScore());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = PushNotificationsEntry.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			PushNotificationsEntry.class
		};
	private long _pushNotificationsEntryId;
	private long _userId;
	private String _userUuid;
	private long _createTime;
	private long _originalCreateTime;
	private boolean _setOriginalCreateTime;
	private long _parentPushNotificationsEntryId;
	private long _originalParentPushNotificationsEntryId;
	private boolean _setOriginalParentPushNotificationsEntryId;
	private int _childrenPushNotificationsEntriesCount;
	private String _payload;
	private long _ratingsTotalScore;
	private long _columnBitmask;
	private PushNotificationsEntry _escapedModel;
}