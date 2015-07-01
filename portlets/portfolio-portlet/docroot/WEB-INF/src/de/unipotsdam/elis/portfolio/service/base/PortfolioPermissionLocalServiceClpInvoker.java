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

package de.unipotsdam.elis.portfolio.service.base;

import de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class PortfolioPermissionLocalServiceClpInvoker {
	public PortfolioPermissionLocalServiceClpInvoker() {
		_methodName0 = "addPortfolioPermission";

		_methodParameterTypes0 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioPermission"
			};

		_methodName1 = "createPortfolioPermission";

		_methodParameterTypes1 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK"
			};

		_methodName2 = "deletePortfolioPermission";

		_methodParameterTypes2 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK"
			};

		_methodName3 = "deletePortfolioPermission";

		_methodParameterTypes3 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioPermission"
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

		_methodName10 = "fetchPortfolioPermission";

		_methodParameterTypes10 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK"
			};

		_methodName11 = "getPortfolioPermission";

		_methodParameterTypes11 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK"
			};

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getPortfolioPermissions";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getPortfolioPermissionsCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updatePortfolioPermission";

		_methodParameterTypes15 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioPermission"
			};

		_methodName34 = "getBeanIdentifier";

		_methodParameterTypes34 = new String[] {  };

		_methodName35 = "setBeanIdentifier";

		_methodParameterTypes35 = new String[] { "java.lang.String" };

		_methodName40 = "addPortfolioPermission";

		_methodParameterTypes40 = new String[] { "long", "long" };

		_methodName41 = "deletePortfolioPermission";

		_methodParameterTypes41 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioPermission"
			};

		_methodName42 = "deletePortfolioPermission";

		_methodParameterTypes42 = new String[] { "long", "long" };

		_methodName43 = "deletePortfolioPermissionByPlid";

		_methodParameterTypes43 = new String[] { "long" };

		_methodName44 = "deletePortfolioPermissionByUserId";

		_methodParameterTypes44 = new String[] { "long" };

		_methodName45 = "getPortfolioPermission";

		_methodParameterTypes45 = new String[] { "long", "long" };

		_methodName46 = "fetchPortfolioPermission";

		_methodParameterTypes46 = new String[] { "long", "long" };

		_methodName47 = "getPortfolioPermissionByPlid";

		_methodParameterTypes47 = new String[] { "long" };

		_methodName48 = "getPortfolioPermissionByUserId";

		_methodParameterTypes48 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.addPortfolioPermission((de.unipotsdam.elis.portfolio.model.PortfolioPermission)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.createPortfolioPermission((de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK)arguments[0]);
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.deletePortfolioPermission((de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK)arguments[0]);
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.deletePortfolioPermission((de.unipotsdam.elis.portfolio.model.PortfolioPermission)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.fetchPortfolioPermission((de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK)arguments[0]);
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermission((de.unipotsdam.elis.portfolio.service.persistence.PortfolioPermissionPK)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermissions(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermissionsCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.updatePortfolioPermission((de.unipotsdam.elis.portfolio.model.PortfolioPermission)arguments[0]);
		}

		if (_methodName34.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes34, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName35.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes35, parameterTypes)) {
			PortfolioPermissionLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName40.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes40, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.addPortfolioPermission(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName41.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes41, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.deletePortfolioPermission((de.unipotsdam.elis.portfolio.model.PortfolioPermission)arguments[0]);
		}

		if (_methodName42.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes42, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.deletePortfolioPermission(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName43.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes43, parameterTypes)) {
			PortfolioPermissionLocalServiceUtil.deletePortfolioPermissionByPlid(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			PortfolioPermissionLocalServiceUtil.deletePortfolioPermissionByUserId(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermission(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName46.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes46, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.fetchPortfolioPermission(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName47.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes47, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermissionByPlid(((Long)arguments[0]).longValue());
		}

		if (_methodName48.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes48, parameterTypes)) {
			return PortfolioPermissionLocalServiceUtil.getPortfolioPermissionByUserId(((Long)arguments[0]).longValue());
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
	private String _methodName34;
	private String[] _methodParameterTypes34;
	private String _methodName35;
	private String[] _methodParameterTypes35;
	private String _methodName40;
	private String[] _methodParameterTypes40;
	private String _methodName41;
	private String[] _methodParameterTypes41;
	private String _methodName42;
	private String[] _methodParameterTypes42;
	private String _methodName43;
	private String[] _methodParameterTypes43;
	private String _methodName44;
	private String[] _methodParameterTypes44;
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName46;
	private String[] _methodParameterTypes46;
	private String _methodName47;
	private String[] _methodParameterTypes47;
	private String _methodName48;
	private String[] _methodParameterTypes48;
}