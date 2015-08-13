package de.unipotsdam.elis.portfolio.service.persistence;

import java.util.List;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.unipotsdam.elis.portfolio.model.PortfolioFeedback;

public class PortfolioFeedbackFinderImpl extends BasePersistenceImpl<PortfolioFeedback> implements PortfolioFeedbackFinder{

	public List<User> findUsersByPortfolioFeedback(long plid){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findUsersByPortfolioFeedback");

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("User_", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.UserImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(plid);

	        return (List<User>) QueryUtil.list(q, getDialect(), QueryUtil.ALL_POS,QueryUtil.ALL_POS);
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	    	sessionFactory.closeSession(session);
	    }

	    return null;
	}
}
