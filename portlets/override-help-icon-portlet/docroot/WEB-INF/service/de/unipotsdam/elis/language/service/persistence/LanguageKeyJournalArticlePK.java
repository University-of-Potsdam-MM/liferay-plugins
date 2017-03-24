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

package de.unipotsdam.elis.language.service.persistence;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Serializable;

/**
 * @author Matthias
 * @generated
 */
public class LanguageKeyJournalArticlePK implements Comparable<LanguageKeyJournalArticlePK>,
	Serializable {
	public String key;
	public String articleId;

	public LanguageKeyJournalArticlePK() {
	}

	public LanguageKeyJournalArticlePK(String key, String articleId) {
		this.key = key;
		this.articleId = articleId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getArticleId() {
		return articleId;
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	@Override
	public int compareTo(LanguageKeyJournalArticlePK pk) {
		if (pk == null) {
			return -1;
		}

		int value = 0;

		value = key.compareTo(pk.key);

		if (value != 0) {
			return value;
		}

		value = articleId.compareTo(pk.articleId);

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

		if (!(obj instanceof LanguageKeyJournalArticlePK)) {
			return false;
		}

		LanguageKeyJournalArticlePK pk = (LanguageKeyJournalArticlePK)obj;

		if ((key.equals(pk.key)) && (articleId.equals(pk.articleId))) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (String.valueOf(key) + String.valueOf(articleId)).hashCode();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(10);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		sb.append("key");
		sb.append(StringPool.EQUAL);
		sb.append(key);

		sb.append(StringPool.COMMA);
		sb.append(StringPool.SPACE);
		sb.append("articleId");
		sb.append(StringPool.EQUAL);
		sb.append(articleId);

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		return sb.toString();
	}
}