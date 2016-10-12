package de.unipotsdam.elis.portlet.documentslibrary.action;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.struts.StrutsPortletAction;

import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;

public class CustomSelectFolderAction implements StrutsPortletAction {

	public CustomSelectFolderAction() {
		super();
	}

	@Override
	public void processAction(PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		
	}

	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		OwncloudRepositoryUtil.setProperties(actionRequest, actionResponse);
		originalStrutsPortletAction.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String render(PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {
		return null;
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse) throws Exception {
		OwncloudRepositoryUtil.setProperties(renderRequest, renderResponse);
		return originalStrutsPortletAction.render(portletConfig, renderRequest, renderResponse);
	}

	@Override
	public void serveResource(PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws Exception {
	}

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {		
		OwncloudRepositoryUtil.setProperties(resourceRequest, resourceResponse);
		originalStrutsPortletAction.serveResource(portletConfig, resourceRequest, resourceResponse);
	}
}
