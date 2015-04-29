package de.unipotsdam.elis.workspacegrid.model;

import java.util.LinkedList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;

public class WorkspaceUtilService {
	
	/**
	 * get all sites of an user
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Group> getAllUserSites() throws PortalException, SystemException {
		long userId = PrincipalThreadLocal.getUserId();
		User user = UserLocalServiceUtil.getUser(userId);
		return user.getGroups();						
	}
	
	
	/**
	 * get all sites from site template 'Portfolio'
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Group> getAllPortfolioSites() throws PortalException, SystemException {
		String filterString = "Portfolio";
		return filterSitesByTemplateName(filterString);
	}
	
	
	/**
	 * get all sites from site template 'Default Social Office Site'
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Group> getAllCourseSites() throws PortalException, SystemException {
		String filterString = "Lehre Interaktiv";
		return filterSitesByTemplateName(filterString);
	}
	
	
	/**
	 * get all sites from site template 'Vorlage1'
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Group> getAllGroupworkSites() throws PortalException, SystemException {
		String filterString = "Gruppenarbeit";
		return filterSitesByTemplateName(filterString);
	}
	
	/**
	 * get all left sites of an user minus portfolio, groupwork or course sites
	 * @return
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Group> getOtherSites() throws PortalException, SystemException {
		List<Group> result = getAllUserSites();		
		result.removeAll(getAllPortfolioSites());
		result.removeAll(getAllCourseSites());
		result.removeAll(getAllGroupworkSites());
		return result;
	}

	public static List<Group> filterSitesByTemplateName(String filterString) throws PortalException, SystemException {
		List<Group> userGroups = getAllUserSites();
		List<Group> result = new LinkedList<Group>();
		for (Group group : userGroups) {			
			try {
				if (LayoutSetPrototypeServiceUtil
				.getLayoutSetPrototype(group.getPublicLayoutSet().getLayoutSetPrototypeId())
				.getName().contains(filterString)) {
					result.add(group);
				}
			} catch (PortalException e) {				
			} catch (SystemException e) {				
			}					
		}	
		return result;
	}
	
	public static int getNumberOfActivitiesForSite(Group group) {		
		try {
			return SocialActivityLocalServiceUtil.getGroupActivitiesCount(group.getGroupId());
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
}