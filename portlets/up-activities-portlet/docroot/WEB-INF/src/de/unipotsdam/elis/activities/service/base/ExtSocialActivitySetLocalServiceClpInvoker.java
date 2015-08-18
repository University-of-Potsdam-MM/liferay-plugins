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

import de.unipotsdam.elis.activities.service.ExtSocialActivitySetLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class ExtSocialActivitySetLocalServiceClpInvoker {
	public ExtSocialActivitySetLocalServiceClpInvoker() {
		_methodName16 = "getBeanIdentifier";

		_methodParameterTypes16 = new String[] {  };

		_methodName17 = "setBeanIdentifier";

		_methodParameterTypes17 = new String[] { "java.lang.String" };

		_methodName20 = "findSocialActivitySetsByUserIdAndClassNames";

		_methodParameterTypes20 = new String[] {
				"long", "java.lang.String[][]", "int", "int"
			};

		_methodName21 = "countSocialActivitySetsByUserIdAndClassNames";

		_methodParameterTypes21 = new String[] { "long", "java.lang.String[][]" };

		_methodName22 = "findSocialActivitySetsByUserGroupsOrUserIdAndClassNames";

		_methodParameterTypes22 = new String[] {
				"long", "java.lang.String[][]", "int", "int"
			};

		_methodName23 = "countSocialActivitySetsByUserGroupsOrUserIdAndClassNames";

		_methodParameterTypes23 = new String[] { "long", "java.lang.String[][]" };

		_methodName24 = "findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK";

		_methodParameterTypes24 = new String[] { "long", "long", "long" };

		_methodName25 = "findSocialActivitiesByActivitySetIdAndType";

		_methodParameterTypes25 = new String[] { "long", "int" };

		_methodName26 = "findSocialActivitiesByActivitySetId";

		_methodParameterTypes26 = new String[] { "long" };

		_methodName27 = "deleteActivitySetsByClassPK";

		_methodParameterTypes27 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName16.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes16, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName17.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes17, parameterTypes)) {
			ExtSocialActivitySetLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName20.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes20, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.findSocialActivitySetsByUserIdAndClassNames(((Long)arguments[0]).longValue(),
				(java.lang.String[])arguments[1],
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName21.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes21, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.countSocialActivitySetsByUserIdAndClassNames(((Long)arguments[0]).longValue(),
				(java.lang.String[])arguments[1]);
		}

		if (_methodName22.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes22, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.findSocialActivitySetsByUserGroupsOrUserIdAndClassNames(((Long)arguments[0]).longValue(),
				(java.lang.String[])arguments[1],
				((Integer)arguments[2]).intValue(),
				((Integer)arguments[3]).intValue());
		}

		if (_methodName23.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes23, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.countSocialActivitySetsByUserGroupsOrUserIdAndClassNames(((Long)arguments[0]).longValue(),
				(java.lang.String[])arguments[1]);
		}

		if (_methodName24.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes24, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Long)arguments[2]).longValue());
		}

		if (_methodName25.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes25, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.findSocialActivitiesByActivitySetIdAndType(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName26.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes26, parameterTypes)) {
			return ExtSocialActivitySetLocalServiceUtil.findSocialActivitiesByActivitySetId(((Long)arguments[0]).longValue());
		}

		if (_methodName27.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes27, parameterTypes)) {
			ExtSocialActivitySetLocalServiceUtil.deleteActivitySetsByClassPK(((Long)arguments[0]).longValue());

			return null;
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
	private String _methodName24;
	private String[] _methodParameterTypes24;
	private String _methodName25;
	private String[] _methodParameterTypes25;
	private String _methodName26;
	private String[] _methodParameterTypes26;
	private String _methodName27;
	private String[] _methodParameterTypes27;
}