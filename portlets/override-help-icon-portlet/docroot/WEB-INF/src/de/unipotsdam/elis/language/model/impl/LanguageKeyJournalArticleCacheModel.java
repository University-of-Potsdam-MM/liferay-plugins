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

package de.unipotsdam.elis.language.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import de.unipotsdam.elis.language.model.LanguageKeyJournalArticle;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LanguageKeyJournalArticle in entity cache.
 *
 * @author Matthias
 * @see LanguageKeyJournalArticle
 * @generated
 */
public class LanguageKeyJournalArticleCacheModel implements CacheModel<LanguageKeyJournalArticle>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{key=");
		sb.append(key);
		sb.append(", articleId=");
		sb.append(articleId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LanguageKeyJournalArticle toEntityModel() {
		LanguageKeyJournalArticleImpl languageKeyJournalArticleImpl = new LanguageKeyJournalArticleImpl();

		if (key == null) {
			languageKeyJournalArticleImpl.setKey(StringPool.BLANK);
		}
		else {
			languageKeyJournalArticleImpl.setKey(key);
		}

		if (articleId == null) {
			languageKeyJournalArticleImpl.setArticleId(StringPool.BLANK);
		}
		else {
			languageKeyJournalArticleImpl.setArticleId(articleId);
		}

		languageKeyJournalArticleImpl.resetOriginalValues();

		return languageKeyJournalArticleImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		key = objectInput.readUTF();
		articleId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (key == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(key);
		}

		if (articleId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(articleId);
		}
	}

	public String key;
	public String articleId;
}