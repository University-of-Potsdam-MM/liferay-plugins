package de.unipotsdam.elis.custompages.service.persistence;

import java.util.List;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.persistence.CustomPageFeedbackFinder;

public class CustomPageFeedbackFinderImpl extends BasePersistenceImpl<CustomPageFeedback> implements CustomPageFeedbackFinder{

	public List<User> findUsersByCustomPageFeedback(long plid){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findUsersByCustomPageFeedback");

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
	
	public List<Layout> findCustomPagesByPageTypeAndLayoutUserId(int pageType, long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findCustomPagesByPageTypeAndLayoutUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pageType);
			qPos.add(userId);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public List<Layout> findCustomPagesByPageTypeAndLayoutUserId(int pageType, long userId) {
		return findCustomPagesByPageTypeAndLayoutUserId(pageType, userId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public List<Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public List<Layout> findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(long userId) {
		return findCustomPagesPublishedGlobalAndByCustomPageFeedbackUserId(userId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public List<Layout> findCustomPagesByLayoutUserId(long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findCustomPagesByLayoutUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public List<Layout> findCustomPagesByLayoutUserId(long userId) {
		return findCustomPagesByLayoutUserId(userId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public List<Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findCustomPagesByLayoutUserIdAndCustomPageFeedback");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(userId);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public List<Layout> findCustomPagesByLayoutUserIdAndCustomPageFeedback(long userId) {
		return findCustomPagesByLayoutUserIdAndCustomPageFeedback(userId,  QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}


	public List<Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(int pageType,long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findCustomPagesByPageTypeAndCustomPageFeedbackUserId");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pageType);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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

	public List<Layout> findCustomPagesByPageTypeAndCustomPageFeedbackUserId(int pageType, long userId) {
		return findCustomPagesByPageTypeAndCustomPageFeedbackUserId(pageType, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}
	
	public List<Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(int pageType, long userId, int begin, int end) {
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
		try {
			session = sessionFactory.openSession();

			String sql = CustomSQLUtil.get("findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser");

			SQLQuery q = session.createSQLQuery(sql);
			q.setCacheable(false);
	        q.addEntity("Layout", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portal.model.impl.LayoutImpl"));

			QueryPos qPos = QueryPos.getInstance(q);
			qPos.add(pageType);
			qPos.add(userId);
			qPos.add(userId);

			return (List<Layout>) QueryUtil.list(q, getDialect(), begin, end);
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
	

	public List<Layout> findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(int pageType, long userId) {
		return findGlobalPublishedCustomPagesByPageTypeAndNotPublishedToUser(pageType, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

}
