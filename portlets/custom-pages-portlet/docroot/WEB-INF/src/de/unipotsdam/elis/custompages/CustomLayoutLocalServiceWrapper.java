package de.unipotsdam.elis.custompages;

import java.util.List;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceWrapper;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

public class CustomLayoutLocalServiceWrapper extends LayoutLocalServiceWrapper {

	public CustomLayoutLocalServiceWrapper(LayoutLocalService layoutLocalService) {
		super(layoutLocalService);
	}
	
	@Override
	public Layout deleteLayout(Layout layout) throws SystemException {
		Layout deletedLayout = super.deleteLayout(layout);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(deletedLayout.getPlid());
		
		return deletedLayout;
	}
	
	@Override
	public void deleteLayout(Layout layout, boolean updateLayoutSet, ServiceContext serviceContext)
			throws PortalException, SystemException {
		// TODO Auto-generated method stub
		super.deleteLayout(layout, updateLayoutSet, serviceContext);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(layout.getPlid());
	}
	
	@Override
	public void deleteLayout(long groupId, boolean privateLayout, long layoutId, ServiceContext serviceContext)
			throws PortalException, SystemException {
		Layout layout = LayoutLocalServiceUtil.getLayout(groupId, privateLayout, layoutId);
		
		super.deleteLayout(groupId, privateLayout, layoutId, serviceContext);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(layout.getPlid());
	}
	
	@Override
	public Layout deleteLayout(long plid) throws PortalException, SystemException {
		Layout deletedLayout = super.deleteLayout(plid);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(deletedLayout.getPlid());
		
		return deletedLayout;
	}
	
	@Override
	public void deleteLayout(long plid, ServiceContext serviceContext) throws PortalException, SystemException {
		Layout layout = LayoutLocalServiceUtil.getLayout(plid);
		
		super.deleteLayout(plid, serviceContext);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(layout.getPlid());
	}
	
	@Override
	public void deleteLayouts(long groupId, boolean privateLayout, ServiceContext serviceContext)
			throws PortalException, SystemException {
		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(groupId, privateLayout);
		
		super.deleteLayouts(groupId, privateLayout, serviceContext);
		
		for (Layout layout : layouts)
			CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByPlid(layout.getPlid());
	}
}
