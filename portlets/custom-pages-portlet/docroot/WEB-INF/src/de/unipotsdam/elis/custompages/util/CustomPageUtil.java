package de.unipotsdam.elis.custompages.util;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.activities.ExtendedSocialActivityKeyConstants;
import de.unipotsdam.elis.custompages.CustomPageStatics;
import de.unipotsdam.elis.custompages.NoSuchCustomPageFeedbackException;
import de.unipotsdam.elis.custompages.model.CustomPageFeedback;
import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;
import de.unipotsdam.elis.custompages.util.jsp.JspHelper;

public class CustomPageUtil {

	/**
	 * Publishes a custom page to a user.
	 * 
	 * @param plid
	 *            plid of the custom page
	 * @param userId
	 *            id of the user
	 * @return true if the user was added, else false
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static boolean publishCustomPageToUser(long plid, long userId) throws SystemException {
		// TODO: verhindern, dass man sich selbst hinzufügen kann
		// TODO: doppelte einträge verhindern
		CustomPageFeedback pf = CustomPageFeedbackLocalServiceUtil.addCustomPageFeedback(plid, userId);
		return pf != null;
	}

	/**
	 * Retracts the publishment of a custom page to a user.
	 * 
	 * @param plid
	 *            plid of the custom page
	 * @param userId
	 *            id of the user
	 * @throws NoSuchPermissionException
	 * @throws SystemException
	 * @throws NoSuchFeedbackException
	 */
	public static void deletePublishment(long plid, long userId) throws SystemException, NoSuchCustomPageFeedbackException {
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(plid, userId);
	}

	/**
	 * Returns the users having access to the custom page with the given plid.
	 * 
	 * @param plid
	 *            plid of the custom page
	 * @return list containing the users
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<User> getUsers(long plid) throws PortalException, SystemException {
		// TODO: Nutzer existiert nicht abfangen
		// TODO: Als dynamic query
		List<User> result = new ArrayList<User>();
		List<CustomPageFeedback> pfs = CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(plid);
		;
		for (CustomPageFeedback pf : pfs) {
			User user = UserLocalServiceUtil.fetchUser(pf.getUserId());
			if (user != null)
				result.add(user);
		}
		return result;
	}

	public static boolean isPublishedGlobal(long plid) throws SystemException {
		return (CustomPageFeedbackLocalServiceUtil.fetchCustomPageFeedback(plid,
				CustomPageStatics.PUBLISHMENT_TYPE_GLOBAL) != null);
	}

	public static List<CustomPageFeedback> getCustomPageFeedbacks(long plid) throws SystemException {
		return CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlid(plid);
	}

	public static CustomPageFeedback getCustomPageFeedback(long plid, long userId) throws SystemException {
		return CustomPageFeedbackLocalServiceUtil.fetchCustomPageFeedback(plid, userId);
	}

	public static boolean userHasViewPermission(long plid, long userId) throws SystemException, PortalException {
		if (isPublishedGlobal(plid))
			return true;
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		if (layout.getUserId() == userId)
			return true;
		CustomPageFeedback pf = CustomPageFeedbackLocalServiceUtil.fetchCustomPageFeedback(plid, userId);
		return pf != null;
	}

	public static void publishCustomPageGlobal(long plid) throws PortalException, SystemException {
		CustomPageFeedbackLocalServiceUtil.addCustomPageFeedback(plid, CustomPageStatics.PUBLISHMENT_TYPE_GLOBAL);
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlidAndFeedbackStatus(plid,
				CustomPageStatics.FEEDBACK_UNREQUESTED);
	}

	public static void deleteCustomPageGlobalPubishment(long plid) throws PortalException, SystemException {
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(plid, CustomPageStatics.PUBLISHMENT_TYPE_GLOBAL);
	}

	public static void updateCustomPageFeedbackStatus(long plid, long userId, int feedbackStatus)
			throws NoSuchCustomPageFeedbackException, SystemException {
		if (feedbackStatus == CustomPageStatics.FEEDBACK_UNREQUESTED) {
			if (isPublishedGlobal(plid)) {
				CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedback(plid, userId);
				return;
			}
		}
		CustomPageFeedbackLocalServiceUtil.updateCustomPageFeedbackStatus(plid, userId, feedbackStatus);
	}

	public static void publishCustomPageToUserAndRequestFeedback(long plid, long userId) throws SystemException {
		// TODO: verhindern, dass man sich selbst hinzufügen kann
		// TODO: doppelte einträge verhindern
		CustomPageFeedbackLocalServiceUtil.addCustomPageFeedback(plid, userId, CustomPageStatics.FEEDBACK_REQUESTED);
	}

	public static boolean feedbackRequested(long plid, long userId) throws SystemException {
		CustomPageFeedback customPageFeedback = CustomPageFeedbackLocalServiceUtil
				.fetchCustomPageFeedback(plid, userId);
		if (customPageFeedback != null)
			return (customPageFeedback.getFeedbackStatus() != CustomPageStatics.FEEDBACK_UNREQUESTED);
		return false;
	}

	public static boolean feedbackRequested(long plid) throws SystemException {
		return (CustomPageFeedbackLocalServiceUtil.getCustomPageFeedbackByPlidAndFeedbackStatus(plid,
				CustomPageStatics.FEEDBACK_REQUESTED).size() != 0);
	}

	public static void renameCustomPage(long plid, String newTitle) throws PortalException, SystemException {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		layout.setTitle(newTitle);
		LayoutLocalServiceUtil.updateLayout(layout);
	}
	
	public static void removeCustomPage(long plid) throws SystemException, PortalException {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		//ExtSocialActivitySetLocalServiceUtil.deleteActivitySetsByClassPK(plid);
		JspHelper.createCustomPageActivity(layout, layout.getUserId(),0, ExtendedSocialActivityKeyConstants.CUSTOM_PAGE_DELETED);
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(plid);
		LayoutLocalServiceUtil.deleteLayout(layout, true, new ServiceContext());
	}
	
	public static void setCustomPagePageType(long plid, int pageType) throws SystemException, PortalException {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		setCustomPagePageType(layout, pageType);
	}
	
	public static void setCustomPagePageType(Layout layout, int pageType) throws SystemException, PortalException {
		layout.getExpandoBridge().setAttribute(CustomPageStatics.PAGE_TYPE_CUSTOM_FIELD_NAME, pageType);
	}
}
