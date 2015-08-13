package de.unipotsdam.elis.activities.service.persistence;

import java.util.List;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class ExtFinderImpl extends BasePersistenceImpl<SocialActivitySet> implements ExtFinder{
	
	public List<SocialActivitySet> findSocialActivitySetsByUserIdAndClassNameIds(long userId, long[] classNameIds, int begin, int end){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByUserIdAndClassNameIds");
	        sql = sql.replace("{0}", createPlaceholder(classNameIds.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(classNameIds);

	        return (List<SocialActivitySet>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public int countSocialActivitySetsByUserIdAndClassNameIds(long userId, long[] classNameIds){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("countByUserIdAndClassNameIds");
	        sql = sql.replace("{0}", createPlaceholder(classNameIds.length));
	        
	        SQLQuery q = session.createSQLQuery(sql); 
	        q.setCacheable(false);
	        //q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));
	        
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(classNameIds);

	        return (Integer)q.uniqueResult();
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	    	sessionFactory.closeSession(session);
	    }

	    return 0;
	}
	
	public List<SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(long userId, long[] classNameIds, int begin, int end){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByUserGroupsOrUserIdAndClassNameIds");
	        sql = sql.replace("{0}", createPlaceholder(classNameIds.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(classNameIds);

	        return (List<SocialActivitySet>) QueryUtil.list(q, getDialect(), begin, end);
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
	
	public int countSocialActivitySetsByUserGroupsOrUserIdAndClassNameIds(long userId, long[] classNameIds){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("countByUserGroupsOrUserIdAndClassNameIds");
	        sql = sql.replace("{0}", createPlaceholder(classNameIds.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        //q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(classNameIds);

	        return (Integer)q.uniqueResult();
	    } catch (Exception e) {
	        try {
	            throw new SystemException(e);
	        } catch (SystemException se) {
	            se.printStackTrace();
	        }
	    } finally {
	    	sessionFactory.closeSession(session);
	    }

	    return 0;
	}
	
	private String createPlaceholder(int count){
		StringBuilder sb = new StringBuilder("(?");
		for (int i = 1; i < count;i++)
			sb.append(",?");
		sb.append(")");
		return sb.toString() ;
	}
	
	public SocialActivitySet findFirstSocialActivitySetByUseridAndClassNameIdAndClassPK(long userId, long classNameId, long classPK){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findFirstByUserIdAndClassNameIdAndClassPK");

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(classNameId);
	        qPos.add(classPK);

	        return (SocialActivitySet) q.uniqueResult();
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
	
	public List<SocialActivity> findSocialActivitiesByActivitySetIdAndType(long activitySetId, int type){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByActivitySetIdAndType");

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivity", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivityImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(activitySetId);
	        qPos.add(type);

	        return (List<SocialActivity>) QueryUtil.list(q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
	

	
	public List<SocialActivity> findSocialActivitiesByActivitySetId(long activitySetId){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByActivitySetId");

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivity", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivityImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(activitySetId);

	        return (List<SocialActivity>) QueryUtil.list(q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
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
