package de.unipotsdam.elis.portfolio.service.persistence;

import java.util.List;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.unipotsdam.elis.portfolio.model.Portfolio;
import de.unipotsdam.elis.portfolio.model.impl.PortfolioImpl;

public class PortfolioFinderImpl extends BasePersistenceImpl<Portfolio> implements PortfolioFinder {

	public List<Portfolio> findByLayoutUserId(long userId, int begin, int end) {

		Session session = null;
		try {
			session = openSession();

			String sql = CustomSQLUtil.get("findPortfoliosByLayoutUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Portfolio_Portfolio", PortfolioImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);

			return (List<Portfolio>) QueryUtil.list(q, getDialect(), begin, end);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				se.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	public List<Portfolio> findByLayoutUserId(long userId) {
		return findByLayoutUserId(userId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Portfolio> findByPortfolioFeedbackUserId(long userId, int begin, int end) {

		Session session = null;
		try {
			session = openSession();

			String sql = CustomSQLUtil.get("findPortfoliosByPortfolioFeedbackUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Portfolio_Portfolio", PortfolioImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);

			return (List<Portfolio>) QueryUtil.list(q, getDialect(), begin, end);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				se.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	public List<Portfolio> findByPortfolioFeedbackUserId(long userId) {
		return findByPortfolioFeedbackUserId(userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	public List<Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(long publishmentType, long userId, int begin,
			int end) {

		Session session = null;
		try {
			session = openSession();

			String sql = CustomSQLUtil.get("findPortfoliosByPublishmentTypeAndNoPortfolioFeedback");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
			q.addEntity("Portfolio_Portfolio", PortfolioImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(publishmentType);
			qPos.add(userId);
			qPos.add(userId); 

			return (List<Portfolio>) QueryUtil.list(q, getDialect(), begin, end);
		} catch (Exception e) {
			try {
				throw new SystemException(e);
			} catch (SystemException se) {
				se.printStackTrace();
			}
		} finally {
			closeSession(session);
		}
		return null;
	}

	public List<Portfolio> findByPublishmentTypeAndNoPortfolioFeedback(long publishmentType, long userId) {
		return findByPublishmentTypeAndNoPortfolioFeedback(publishmentType, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

}
