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

package de.unipotsdam.elis.activities.service.base;

import de.unipotsdam.elis.activities.service.ExtLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class ExtLocalServiceClpInvoker {
	public ExtLocalServiceClpInvoker() {
		_methodName16 = "getBeanIdentifier";

		_methodParameterTypes16 = new String[] {  };

		_methodName17 = "setBeanIdentifier";

		_methodParameterTypes17 = new String[] { "java.lang.String" };

		_methodName20 = "findSocialActivitySetsByUserIdAndActivityTypes";

		_methodParameterTypes20 = new String[] { "long", "int[][]", "int", "int" };

		_methodName21 = "countSocialActivitySetsByUserIdAndActivityTypes";

		_methodParameterTypes21 = new String[] { "long", "int[][]" };

		_methodName22 = "findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes";

		_methodParameterTypes22 = new String[] { "long", "int[][]", "int", "int" };

		_methodName23 = "countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes";

		_methodParameterTypes23 = new String[] { "long", "int[][]" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return ExtLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			ExtLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName20.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes20, parameterTypes)) {
			return ExtLocalServiceUtil.findSocialActivitySetsByUserIdAndActivityTypes(((Long)arguments[0]).longValue(),
				(int[])arguments[1], ((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName21.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes21, parameterTypes)) {
			return ExtLocalServiceUtil.countSocialActivitySetsByUserIdAndActivityTypes(((Long)arguments[0]).longValue(),
				(int[])arguments[1]);
		}

		if (_methodName22.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes22, parameterTypes)) {
			return ExtLocalServiceUtil.findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(((Long)arguments[0]).longValue(),
				(int[])arguments[1], ((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName23.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes23, parameterTypes)) {
			return ExtLocalServiceUtil.countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(((Long)arguments[0]).longValue(),
				(int[])arguments[1]);
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName16;
	private String[] _methodParameterTypes16;
	private String _methodName17;
	private String[] _methodParameterTypes17;
	private String _methodName20;
	private String[] _methodParameterTypes20;
	private String _methodName21;
	private String[] _methodParameterTypes21;
	private String _methodName22;
	private String[] _methodParameterTypes22;
	private String _methodName23;
	private String[] _methodParameterTypes23;
}