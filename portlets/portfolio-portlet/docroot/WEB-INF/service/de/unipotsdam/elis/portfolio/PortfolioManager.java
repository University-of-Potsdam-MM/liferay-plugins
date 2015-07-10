package de.unipotsdam.elis.portfolio;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.service.LayoutPrototypeLocalServiceUtil;

import de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil;

/**
 * This class handles the publishment process of portfolios.
 * 
 * @author Matthias
 *
 */
public class PortfolioManager {

	public static List<LayoutPrototype> getPortfolioPrototypes() throws SystemException {
		List<LayoutPrototype> layoutPrototypes = LayoutPrototypeLocalServiceUtil.getLayoutPrototypes(0,
				LayoutPrototypeLocalServiceUtil.getLayoutPrototypesCount());
		List<LayoutPrototype> result = new ArrayList<LayoutPrototype>();

		// find the portfolio template
		for (LayoutPrototype lp : layoutPrototypes) {
			if (PortfolioStatics.PORTFOLIO_TEMPLATE_NAMES.contains(lp.getName(Locale.GERMAN))) {
				result.add(lp);
			}
		}
		return result;
	}

	public static boolean layoutIsPortfolio(Layout layout) throws PortalException, SystemException {
		return PortfolioLocalServiceUtil.fetchPortfolio(layout.getPlid()) != null;
	}
}
