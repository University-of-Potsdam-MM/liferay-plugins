package de.unipotsdam.elis.portfolio.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.portfolio.NoSuchPermissionException;
import de.unipotsdam.elis.portfolio.model.PortfolioPermission;
import de.unipotsdam.elis.portfolio.service.PortfolioPermissionLocalServiceUtil;

/**
 * This class handles the publishment process of portfolios.
 * 
 * @author Matthias
 *
 */
public class PortfolioPermissionManager {

	private static String userIdsString = "userIds";
	private static String plidsString = "plids";
	private static String portfolioString = "Portfolio";

	/**
	 * Publishes a portfolio to a user.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @param userId
	 *            id of the user
	 * @return true if the user was added, else false
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static boolean publishPortfolioToUser(long plid, long userId) throws SystemException {
		// TODO: verhindern, dass man sich selbst hinzufügen kann
		PortfolioPermission pp = PortfolioPermissionLocalServiceUtil.addPortfolioPermission(plid, userId);
		return pp != null;
	}

	/**
	 * Retracts the publishment of a portfolio to a user.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @param userId
	 *            id of the user
	 * @throws NoSuchPermissionException 
	 * @throws SystemException
	 */
	public static void removeUserFromPortfolio(long plid, long userId) throws NoSuchPermissionException, SystemException {
		PortfolioPermissionLocalServiceUtil.deletePortfolioPermission(plid, userId);
	}

	/**
	 * Returns the screen names of the users having access to the portfolio with
	 * the given plid.
	 * 
	 * @param plid
	 *            plid of the portfolio
	 * @return list containing the screen names of the users
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<String> getUserOfPortfolio(long plid) throws PortalException, SystemException {
		//TODO: Nutzer existiert nicht abfangen
		List<String> result = new ArrayList<String>();
		List<PortfolioPermission> pps = PortfolioPermissionLocalServiceUtil.getPortfolioPermissionByPlid(plid);
		for (PortfolioPermission pp : pps){
			result.add(UserLocalServiceUtil.getUser(pp.getUserId()).getScreenName());
		}
		return result;
	}

	/**
	 * Returns all portfolios the current user owns.
	 * 
	 * @return list of portfolios
	 * @throws SystemException
	 */
	public static List<Layout> getPortfoliosOfCurrentUser() throws SystemException {
		// TODO: vielleicht portfolios der Nutzer an den Nutzer hängen damit man
		// nicht so umständlich alle Portfolios suchen muss
		
		List<Layout> result = new ArrayList<Layout>();
		
		// get all page templates
		LayoutPrototype layoutPrototype = getPortfolioPrototype();

		// if there is a portfolio template get all pages build from this
		// template
		if (layoutPrototype != null) {
			List<Layout> portfolios = LayoutLocalServiceUtil.getLayoutsByLayoutPrototypeUuid(layoutPrototype.getUuid());
			PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
			for (Layout layout : portfolios) {
				if (layout.getUserId() == permissionChecker.getUserId()) {
					result.add(layout);
				}
			}
		}
		return result;
	}
	
	public static LayoutPrototype getPortfolioPrototype() throws SystemException{
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());
		LayoutPrototype layoutPrototype = null;

		// find the portfolio template
		for (LayoutPrototype lp : layoutPrototypes) {
			if (lp.getName(Locale.GERMAN).equals(portfolioString)) {
				layoutPrototype = lp;
				break;
			}
		}
		return layoutPrototype;
	}

	/**
	 * Returns all portfolios the current user has access to, besides the ones
	 * he owns.
	 * 
	 * @return list of portfolios
	 * @throws PortalException
	 * @throws SystemException
	 */
	public static List<Layout> getPortfoliosPublishedToCurrentUser() throws PortalException, SystemException {
		PermissionChecker permissionChecker = PermissionThreadLocal.getPermissionChecker();
		User user = permissionChecker.getUser();
		List<Layout> result = new ArrayList<Layout>();
		List<PortfolioPermission> pps = PortfolioPermissionLocalServiceUtil.getPortfolioPermissionByUserId(user.getUserId());
		for (PortfolioPermission pp : pps) {
			Layout layout = LayoutLocalServiceUtil.getLayout(pp.getPlid());
			if (layout != null)
				result.add(layout);
		}
		return result;
	}

}
