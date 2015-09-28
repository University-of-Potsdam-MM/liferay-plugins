package de.unipotsdam.elis.so.hook.action.startup;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.liferay.compat.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.so.util.LayoutSetPrototypeUtil;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.kernel.util.UnicodeProperties;

import de.unipotsdam.elis.so.sites.util.SitesHelper;

public class CustomStartupAction extends SimpleAction {
	
	public void run(String[] s) {

		try {
		Company company = CompanyLocalServiceUtil.getCompanies().get(0);
        if (SitesHelper.getPublicPageLayoutSetPrototype(company.getCompanyId()) == null){
        	addPublicPageLayoutSetPrototype(company.getCompanyId());
        }
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
	
	private LayoutSetPrototype addPublicPageLayoutSetPrototype(
			long companyId)
		throws Exception {
		
		String name = "Public Workspace Page";

		Map<Locale, String> localeNamesMap = new HashMap<Locale, String>();
		localeNamesMap.put(LocaleUtil.getDefault(), name);

		String description = name;
		boolean active = false;
		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		LayoutSetPrototype layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
				defaultUserId, companyId, localeNamesMap, description, active,
				true, new ServiceContext());

		UnicodeProperties settingsProperties =
			layoutSetPrototype.getSettingsProperties();

		settingsProperties.setProperty(
				SitesHelper.PLE_SYSTEM_TEMPLATE, "true");

		layoutSetPrototype =
			LayoutSetPrototypeLocalServiceUtil.updateLayoutSetPrototype(
				layoutSetPrototype.getLayoutSetPrototypeId(),
				settingsProperties.toString());

		return layoutSetPrototype;
	}

}
