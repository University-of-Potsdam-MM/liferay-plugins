package de.unipotsdam.elis.so.sites.util;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.service.LayoutSetPrototypeLocalServiceUtil;

public class SitesHelper {
	
	public static final String PLE_SYSTEM_TEMPLATE = "pleSystemTemplate";
	
	public static LayoutSetPrototype getPublicPageLayoutSetPrototype(long companyId) throws SystemException{
		for (LayoutSetPrototype lsp : LayoutSetPrototypeLocalServiceUtil.getLayoutSetPrototypes(companyId)){
			if (lsp.getSettingsProperties().containsKey(PLE_SYSTEM_TEMPLATE)){
				return lsp;
			}
		}
		return null;
	}

}
