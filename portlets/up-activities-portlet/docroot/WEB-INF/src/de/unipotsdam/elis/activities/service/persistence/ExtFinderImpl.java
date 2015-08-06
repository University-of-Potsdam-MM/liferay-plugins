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
import com.liferay.portlet.social.model.SocialActivitySet;
import com.liferay.util.dao.orm.CustomSQLUtil;

public class ExtFinderImpl extends BasePersistenceImpl<SocialActivitySet> implements ExtFinder{
	
	public List<SocialActivitySet> findSocialActivitySetsByUserIdAndActivityTypes(long userId, int[] activityTypes, int begin, int end){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByUserIdAndActivityTypes");
	        sql = sql.replace("{0}", createPlaceholder(activityTypes.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(activityTypes);

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
	
	public int countSocialActivitySetsByUserIdAndActivityTypes(long userId, int[] activityTypes){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("countByUserIdAndActivityTypes");
	        sql = sql.replace("{0}", createPlaceholder(activityTypes.length));
	        
	        SQLQuery q = session.createSQLQuery(sql); 
	        q.setCacheable(false);
	        //q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));
	        
	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(activityTypes);


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
	
	public List<SocialActivitySet> findSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(long userId, int[] activityTypes, int begin, int end){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("findByUserGroupsOrUserIdAndActivityTypes");
	        sql = sql.replace("{0}", createPlaceholder(activityTypes.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(activityTypes);

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
	
	public int countSocialActivitySetsByUserGroupsOrUserIdAndActivityTypes(long userId, int[] activityTypes){
		SessionFactory sessionFactory = (SessionFactory) PortalBeanLocatorUtil.locate("liferaySessionFactory");
		Session session = null;
	    try {
	        session = sessionFactory.openSession();

	        String sql = CustomSQLUtil.get("countByUserGroupsOrUserIdAndActivityTypes");
	        sql = sql.replace("{0}", createPlaceholder(activityTypes.length));

	        SQLQuery q = session.createSQLQuery(sql);
	        q.setCacheable(false);
	        //q.addEntity("SocialActivitySet", PortalClassLoaderUtil.getClassLoader().loadClass("com.liferay.portlet.social.model.impl.SocialActivitySetImpl"));

	        QueryPos qPos = QueryPos.getInstance(q);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(userId);
	        qPos.add(activityTypes);

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

}
