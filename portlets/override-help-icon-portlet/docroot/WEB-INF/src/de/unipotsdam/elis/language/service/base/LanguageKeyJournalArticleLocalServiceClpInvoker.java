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

package de.unipotsdam.elis.language.service.base;

import de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class LanguageKeyJournalArticleLocalServiceClpInvoker {
	public LanguageKeyJournalArticleLocalServiceClpInvoker() {
		_methodName0 = "addLanguageKeyJournalArticle";

		_methodParameterTypes0 = new String[] {
				"de.unipotsdam.elis.language.model.LanguageKeyJournalArticle"
			};

		_methodName1 = "createLanguageKeyJournalArticle";

		_methodParameterTypes1 = new String[] {
				"de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK"
			};

		_methodName2 = "deleteLanguageKeyJournalArticle";

		_methodParameterTypes2 = new String[] {
				"de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK"
			};

		_methodName3 = "deleteLanguageKeyJournalArticle";

		_methodParameterTypes3 = new String[] {
				"de.unipotsdam.elis.language.model.LanguageKeyJournalArticle"
			};

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "dynamicQueryCount";

		_methodParameterTypes9 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery",
				"com.liferay.portal.kernel.dao.orm.Projection"
			};

		_methodName10 = "fetchLanguageKeyJournalArticle";

		_methodParameterTypes10 = new String[] {
				"de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK"
			};

		_methodName11 = "getLanguageKeyJournalArticle";

		_methodParameterTypes11 = new String[] {
				"de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK"
			};

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getLanguageKeyJournalArticles";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getLanguageKeyJournalArticlesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateLanguageKeyJournalArticle";

		_methodParameterTypes15 = new String[] {
				"de.unipotsdam.elis.language.model.LanguageKeyJournalArticle"
			};

		_methodName36 = "getBeanIdentifier";

		_methodParameterTypes36 = new String[] {  };

		_methodName37 = "setBeanIdentifier";

		_methodParameterTypes37 = new String[] { "java.lang.String" };

		_methodName42 = "removeLanguageKeyJournalArticleByKey";

		_methodParameterTypes42 = new String[] { "java.lang.String" };

		_methodName43 = "addLanguageKeyJournalArticle";

		_methodParameterTypes43 = new String[] {
				"java.lang.String", "java.lang.String"
			};

		_methodName44 = "getLanguageKeyJournalArticlesByArticleId";

		_methodParameterTypes44 = new String[] { "java.lang.String" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.addLanguageKeyJournalArticle((de.unipotsdam.elis.language.model.LanguageKeyJournalArticle)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.createLanguageKeyJournalArticle((de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK)arguments[0]);
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.deleteLanguageKeyJournalArticle((de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK)arguments[0]);
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.deleteLanguageKeyJournalArticle((de.unipotsdam.elis.language.model.LanguageKeyJournalArticle)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.fetchLanguageKeyJournalArticle((de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK)arguments[0]);
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getLanguageKeyJournalArticle((de.unipotsdam.elis.language.service.persistence.LanguageKeyJournalArticlePK)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getLanguageKeyJournalArticles(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getLanguageKeyJournalArticlesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.updateLanguageKeyJournalArticle((de.unipotsdam.elis.language.model.LanguageKeyJournalArticle)arguments[0]);
		}

		if (_methodName36.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes36, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName37.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes37, parameterTypes)) {
			LanguageKeyJournalArticleLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			LanguageKeyJournalArticleLocalServiceUtil.removeLanguageKeyJournalArticleByKey((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			LanguageKeyJournalArticleLocalServiceUtil.addLanguageKeyJournalArticle((java.lang.String)arguments[0],
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return LanguageKeyJournalArticleLocalServiceUtil.getLanguageKeyJournalArticlesByArticleId((java.lang.String)arguments[0]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName36;
	private String[] _methodParameterTypes36;
	private String _methodName37;
	private String[] _methodParameterTypes37;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
}