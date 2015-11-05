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

import de.unipotsdam.elis.portfolio.service.PortfolioFeedbackLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Matthias
 * @generated
 */
public class PortfolioFeedbackLocalServiceClpInvoker {
	public PortfolioFeedbackLocalServiceClpInvoker() {
		_methodName0 = "addPortfolioFeedback";

		_methodParameterTypes0 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioFeedback"
			};

		_methodName1 = "createPortfolioFeedback";

		_methodParameterTypes1 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK"
			};

		_methodName2 = "deletePortfolioFeedback";

		_methodParameterTypes2 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK"
			};

		_methodName3 = "deletePortfolioFeedback";

		_methodParameterTypes3 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioFeedback"
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

		_methodName10 = "fetchPortfolioFeedback";

		_methodParameterTypes10 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK"
			};

		_methodName11 = "getPortfolioFeedback";

		_methodParameterTypes11 = new String[] {
				"de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK"
			};

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getPortfolioFeedbacks";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getPortfolioFeedbacksCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updatePortfolioFeedback";

		_methodParameterTypes15 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioFeedback"
			};

		_methodName44 = "getBeanIdentifier";

		_methodParameterTypes44 = new String[] {  };

		_methodName45 = "setBeanIdentifier";

		_methodParameterTypes45 = new String[] { "java.lang.String" };

		_methodName50 = "addPortfolioFeedback";

		_methodParameterTypes50 = new String[] {
				"long", "long", "int",
				"com.liferay.portal.service.ServiceContext"
			};

		_methodName51 = "addPortfolioFeedback";

		_methodParameterTypes51 = new String[] {
				"long", "long", "com.liferay.portal.service.ServiceContext"
			};

		_methodName52 = "updatePortfolioFeedback";

		_methodParameterTypes52 = new String[] { "long", "long", "int" };

		_methodName53 = "deletePortfolioFeedback";

		_methodParameterTypes53 = new String[] {
				"de.unipotsdam.elis.portfolio.model.PortfolioFeedback"
			};

		_methodName54 = "deletePortfolioFeedback";

		_methodParameterTypes54 = new String[] { "long", "long" };

		_methodName55 = "deletePortfolioFeedbackByPlid";

		_methodParameterTypes55 = new String[] { "long" };

		_methodName56 = "deletePortfolioFeedbackByUserId";

		_methodParameterTypes56 = new String[] { "long" };

		_methodName57 = "deletePortfolioFeedbackByPlidAndFeedbackStatus";

		_methodParameterTypes57 = new String[] { "long", "int" };

		_methodName58 = "getPortfolioFeedback";

		_methodParameterTypes58 = new String[] { "long", "long" };

		_methodName59 = "fetchPortfolioFeedback";

		_methodParameterTypes59 = new String[] { "long", "long" };

		_methodName60 = "getPortfolioFeedbackByPlid";

		_methodParameterTypes60 = new String[] { "long" };

		_methodName61 = "getPortfolioFeedbackByUserId";

		_methodParameterTypes61 = new String[] { "long" };

		_methodName62 = "getPortfolioFeedbackByPlidAndFeedbackStatus";

		_methodParameterTypes62 = new String[] { "long", "int" };

		_methodName63 = "getPortfolioPlidsByUserId";

		_methodParameterTypes63 = new String[] { "long" };

		_methodName64 = "getUsersByPortfolioFeedback";

		_methodParameterTypes64 = new String[] { "long" };
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.addPortfolioFeedback((de.unipotsdam.elis.portfolio.model.PortfolioFeedback)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.createPortfolioFeedback((de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK)arguments[0]);
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback((de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK)arguments[0]);
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback((de.unipotsdam.elis.portfolio.model.PortfolioFeedback)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.fetchPortfolioFeedback((de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK)arguments[0]);
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedback((de.unipotsdam.elis.portfolio.service.persistence.PortfolioFeedbackPK)arguments[0]);
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbacks(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbacksCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.updatePortfolioFeedback((de.unipotsdam.elis.portfolio.model.PortfolioFeedback)arguments[0]);
		}

		if (_methodName44.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes44, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName45.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes45, parameterTypes)) {
			PortfolioFeedbackLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName50.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes50, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.addPortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.service.ServiceContext)arguments[3]);
		}

		if (_methodName51.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes51, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.addPortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				(com.liferay.portal.service.ServiceContext)arguments[2]);
		}

		if (_methodName52.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes52, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.updatePortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName53.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes53, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback((de.unipotsdam.elis.portfolio.model.PortfolioFeedback)arguments[0]);
		}

		if (_methodName54.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes54, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName55.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes55, parameterTypes)) {
			PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByPlid(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName56.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes56, parameterTypes)) {
			PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByUserId(((Long)arguments[0]).longValue());

			return null;
		}

		if (_methodName57.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes57, parameterTypes)) {
			PortfolioFeedbackLocalServiceUtil.deletePortfolioFeedbackByPlidAndFeedbackStatus(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());

			return null;
		}

		if (_methodName58.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes58, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName59.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes59, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.fetchPortfolioFeedback(((Long)arguments[0]).longValue(),
				((Long)arguments[1]).longValue());
		}

		if (_methodName60.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes60, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlid(((Long)arguments[0]).longValue());
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByUserId(((Long)arguments[0]).longValue());
		}

		if (_methodName62.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes62, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioFeedbackByPlidAndFeedbackStatus(((Long)arguments[0]).longValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName63.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes63, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getPortfolioPlidsByUserId(((Long)arguments[0]).longValue());
		}

		if (_methodName64.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes64, parameterTypes)) {
			return PortfolioFeedbackLocalServiceUtil.getUsersByPortfolioFeedback(((Long)arguments[0]).longValue());
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
	private String _methodName44;
	private String[] _methodParameterTypes44;
	private String _methodName45;
	private String[] _methodParameterTypes45;
	private String _methodName50;
	private String[] _methodParameterTypes50;
	private String _methodName51;
	private String[] _methodParameterTypes51;
	private String _methodName52;
	private String[] _methodParameterTypes52;
	private String _methodName53;
	private String[] _methodParameterTypes53;
	private String _methodName54;
	private String[] _methodParameterTypes54;
	private String _methodName55;
	private String[] _methodParameterTypes55;
	private String _methodName56;
	private String[] _methodParameterTypes56;
	private String _methodName57;
	private String[] _methodParameterTypes57;
	private String _methodName58;
	private String[] _methodParameterTypes58;
	private String _methodName59;
	private String[] _methodParameterTypes59;
	private String _methodName60;
	private String[] _methodParameterTypes60;
	private String _methodName61;
	private String[] _methodParameterTypes61;
	private String _methodName62;
	private String[] _methodParameterTypes62;
	private String _methodName63;
	private String[] _methodParameterTypes63;
	private String _methodName64;
	private String[] _methodParameterTypes64;
}