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

package com.liferay.skinny.service.impl;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.ac.AccessControlled;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.storage.Fields;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.skinny.model.SkinnyDDLRecord;
import com.liferay.skinny.model.SkinnyJournalArticle;
import com.liferay.skinny.service.base.SkinnyServiceBaseImpl;

import java.io.Serializable;

import java.text.Format;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author James Falkner
 * @author Amos Fong
 */
public class SkinnyServiceImpl extends SkinnyServiceBaseImpl {

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<SkinnyDDLRecord> getSkinnyDDLRecords(long ddlRecordSetId)
		throws Exception {

		List<SkinnyDDLRecord> skinnyDDLRecords =
			new ArrayList<SkinnyDDLRecord>();

		PermissionChecker permissionChecker = getPermissionChecker();

		DDLRecordSet ddlRecordSet = ddlRecordSetLocalService.getRecordSet(
			ddlRecordSetId);

		if (permissionChecker.hasPermission(
				ddlRecordSet.getGroupId(), DDLRecordSet.class.getName(),
				ddlRecordSet.getRecordSetId(), ActionKeys.VIEW)) {

			for (DDLRecord ddlRecord : ddlRecordSet.getRecords()) {
				SkinnyDDLRecord skinnyDDLRecord = getSkinnyDDLRecord(ddlRecord);

				skinnyDDLRecords.add(skinnyDDLRecord);
			}
		}

		return skinnyDDLRecords;
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<SkinnyJournalArticle> getSkinnyJournalArticles(
			long companyId, String groupName, long ddmStructureId,
			String locale)
		throws Exception {

		List<SkinnyJournalArticle> skinnyJournalArticles =
			new ArrayList<SkinnyJournalArticle>();

		Group group = groupLocalService.getGroup(companyId, groupName);

		DDMStructure ddmStructure = ddmStructureLocalService.getDDMStructure(
			ddmStructureId);

		Set<String> journalArticleIds = new HashSet<String>();

		List<JournalArticle> journalArticles =
			journalArticleLocalService.getStructureArticles(
				group.getGroupId(), ddmStructure.getStructureKey());

		for (JournalArticle journalArticle : journalArticles) {
			if (journalArticleIds.contains(journalArticle.getArticleId())) {
				continue;
			}

			journalArticleIds.add(journalArticle.getArticleId());

			try {
				PermissionChecker permissionChecker = getPermissionChecker();

				if (permissionChecker.hasPermission(
						group.getGroupId(), JournalArticle.class.getName(),
						journalArticle.getResourcePrimKey(), ActionKeys.VIEW)) {

					JournalArticle latestJournalArticle =
						journalArticleLocalService.getLatestArticle(
							group.getGroupId(), journalArticle.getArticleId(),
							WorkflowConstants.STATUS_APPROVED);

					SkinnyJournalArticle skinnyJournalArticle =
						getSkinnyJournalArticle(latestJournalArticle, locale);

					skinnyJournalArticles.add(skinnyJournalArticle);
				}
			}
			catch (NoSuchArticleException nsae) {
			}
		}

		return skinnyJournalArticles;
	}

	protected SkinnyDDLRecord getSkinnyDDLRecord(DDLRecord ddlRecord)
		throws Exception {

		SkinnyDDLRecord skinnyDDLRecord = new SkinnyDDLRecord();

		skinnyDDLRecord.addDynamicElement("uuid", ddlRecord.getUuid());

		Fields fields = ddlRecord.getFields();

		for (String fieldName : fields.getNames()) {
			String fieldValueString = StringPool.BLANK;

			String fieldDataType = GetterUtil.getString(
				ddlRecord.getFieldDataType(fieldName));

			Serializable fieldValue = ddlRecord.getFieldValue(fieldName);

			if (fieldDataType.equals("boolean")) {
				boolean booleanValue = GetterUtil.getBoolean(fieldValue);

				fieldValueString = String.valueOf(booleanValue);
			}
			else if (fieldDataType.equals("date")) {
				fieldValueString = _format.format(fieldValue);
			}
			else if (fieldDataType.equals("double")) {
				double doubleValue = GetterUtil.getDouble(fieldValue);

				fieldValueString = String.valueOf(doubleValue);
			}
			else if (fieldDataType.equals("integer") ||
					 fieldDataType.equals("number")) {

				int intValue = GetterUtil.getInteger(fieldValue);

				fieldValueString = String.valueOf(intValue);
			}
			else {
				fieldValueString = GetterUtil.getString(fieldValue);
			}

			skinnyDDLRecord.addDynamicElement(fieldName, fieldValueString);
		}

		return skinnyDDLRecord;
	}

	protected SkinnyJournalArticle getSkinnyJournalArticle(
			JournalArticle journalArticle, String locale)
		throws Exception {

		SkinnyJournalArticle skinnyJournalArticle = new SkinnyJournalArticle();

		String content = null;

		if (ArrayUtil.contains(journalArticle.getAvailableLocales(), locale)) {
			content = journalArticle.getContentByLocale(locale);
		}
		else {
			content = journalArticle.getContent();
		}

		Document document = SAXReaderUtil.read(content);

		Element rootElement = document.getRootElement();

		populateSkinnyJournalArticle(skinnyJournalArticle, rootElement);

		return skinnyJournalArticle;
	}

	protected void populateSkinnyJournalArticle(
		SkinnyJournalArticle skinnyJournalArticle, Element parentElement) {

		List<Element> elements = parentElement.elements();

		for (Element element : elements) {
			String elementName = element.getName();

			if (elementName.equals("dynamic-element")) {
				Element dynamicElementElement = element.element(
					"dynamic-content");

				if (dynamicElementElement != null) {
					skinnyJournalArticle.addDynamicElement(
						element.attributeValue("name"),
						dynamicElementElement.getTextTrim());
				}
			}
			else {
				populateSkinnyJournalArticle(skinnyJournalArticle, element);
			}
		}
	}

	private Format _format = FastDateFormatFactoryUtil.getSimpleDateFormat(
		"yyyy-MM-dd");

}