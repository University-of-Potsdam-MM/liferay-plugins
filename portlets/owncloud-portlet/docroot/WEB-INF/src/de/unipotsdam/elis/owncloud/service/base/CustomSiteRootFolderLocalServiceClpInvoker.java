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

package de.unipotsdam.elis.owncloud.service.base;

import de.unipotsdam.elis.owncloud.service.CustomSiteRootFolderLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class CustomSiteRootFolderLocalServiceClpInvoker {
	public CustomSiteRootFolderLocalServiceClpInvoker() {
		_methodName0 = "addCustomSiteRootFolder";

		_methodParameterTypes0 = new String[] {
				"de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder"
			};

		_methodName1 = "createCustomSiteRootFolder";

		_methodParameterTypes1 = new String[] {
				"de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK"
			};

		_methodName2 = "deleteCustomSiteRootFolder";

		_methodParameterTypes2 = new String[] {
				"de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK"
			};

		_methodName3 = "deleteCustomSiteRootFolder";

		_methodParameterTypes3 = new String[] {
				"de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder"
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

		_methodName10 = "fetchCustomSiteRootFolder";

		_methodParameterTypes10 = new String[] {
				"de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK"
			};

		_methodName11 = "getCustomSiteRootFolder";

		_methodParameterTypes11 = new String[] {
				"de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK"
			};

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getCustomSiteRootFolders";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getCustomSiteRootFoldersCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateCustomSiteRootFolder";

		_methodParameterTypes15 = new String[] {
				"de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder"
			};

		_methodName32 = "getBeanIdentifier";

		_methodParameterTypes32 = new String[] {  };

		_methodName33 = "setBeanIdentifier";

		_methodParameterTypes33 = new String[] { "java.lang.String" };

		_methodName38 = "addCustomSiteRootFolder";

		_methodParameterTypes38 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};

		_methodName39 = "updateCustomSiteRootFolder";

		_methodParameterTypes39 = new String[] {
				"long", "java.lang.String", "java.lang.String"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.addCustomSiteRootFolder((de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.createCustomSiteRootFolder((de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK)arguments[0]);
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.deleteCustomSiteRootFolder((de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK)arguments[0]);
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.deleteCustomSiteRootFolder((de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.fetchCustomSiteRootFolder((de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK)arguments[0]);
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.getCustomSiteRootFolder((de.unipotsdam.elis.owncloud.service.persistence.CustomSiteRootFolderPK)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.getCustomSiteRootFolders(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.getCustomSiteRootFoldersCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder((de.unipotsdam.elis.owncloud.model.CustomSiteRootFolder)arguments[0]);
		}

		if (_methodName32.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes32, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName33.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes33, parameterTypes)) {
			CustomSiteRootFolderLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName38.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes38, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.addCustomSiteRootFolder(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
		}

		if (_methodName39.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes39, parameterTypes)) {
			return CustomSiteRootFolderLocalServiceUtil.updateCustomSiteRootFolder(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1], (java.lang.String)arguments[2]);
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
	private String _methodName32;
	private String[] _methodParameterTypes32;
	private String _methodName33;
	private String[] _methodParameterTypes33;
	private String _methodName38;
	private String[] _methodParameterTypes38;
	private String _methodName39;
	private String[] _methodParameterTypes39;
}