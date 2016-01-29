package de.unipotsdam.elis.dl.startup;

import java.util.List;

import com.liferay.portal.kernel.dao.orm.Criterion;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalServiceUtil;

public class RemoveDLFileTypesStartupAction extends SimpleAction{

	@Override
	public void run(String[] ids) throws ActionException {
		try {
		DynamicQuery query = DynamicQueryFactoryUtil.forClass(DLFileEntryType.class);
		Criterion criterion = null;
		criterion = RestrictionsFactoryUtil.like("fileEntryTypeKey", DLFileEntryTypeConstants.NAME_CONTRACT);
		criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("fileEntryTypeKey", DLFileEntryTypeConstants.NAME_MARKETING_BANNER));
		criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("fileEntryTypeKey", DLFileEntryTypeConstants.NAME_ONLINE_TRAINING));
		criterion = RestrictionsFactoryUtil.or(criterion, RestrictionsFactoryUtil.like("fileEntryTypeKey", DLFileEntryTypeConstants.NAME_SALES_PRESENTATION));
		query.add(criterion);
		List<DLFileEntryType> result = DLFileEntryTypeLocalServiceUtil.dynamicQuery(query);
		for (DLFileEntryType entryType : result){
			DLFileEntryTypeLocalServiceUtil.deleteFileEntryType(entryType);
		}
		} catch (SystemException e) {
			e.printStackTrace();
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
	}

}
