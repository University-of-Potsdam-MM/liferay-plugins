package de.unipotsdam.elis.portlet.documentslibrary.action;

import java.io.FileNotFoundException;
import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.RepositoryModel;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFolderFinderUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.webdav.WebdavFolder;

public class CustomStrutsAction implements StrutsPortletAction {

	public CustomStrutsAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
		System.out.println("keks");

	}

	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		processAction(portletConfig, actionRequest, actionResponse);
		System.out.println("keks");

	}

	@Override
	public String render(PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {
		return null;
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		renderRequest.setAttribute("RepositoryClassName", OwncloudRepository.class.getName());
		setProperties(renderRequest, renderResponse);

		return originalStrutsPortletAction.render(portletConfig, renderRequest, renderResponse);
	}

	@Override
	public void serveResource(PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws Exception {

	}

	@Override
	public void serveResource(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
			ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {
		setProperties(resourceRequest, resourceResponse);

		originalStrutsPortletAction.serveResource(portletConfig, resourceRequest, resourceResponse);

	}

	private void setProperties(PortletRequest request, PortletResponse response) throws PortalException,
			SystemException, FileNotFoundException {
		boolean isOwncloudRepository = false;
		long folderId = ParamUtil.getLong(request, "folderId");
		Repository rep = null;
		
		RepositoryEntry repEntry = RepositoryEntryLocalServiceUtil.fetchRepositoryEntry(folderId);
		if (repEntry != null) {
			rep = RepositoryLocalServiceUtil.getRepository(repEntry.getRepositoryId());
		} else {
			DLFolder dlFolder = DLFolderLocalServiceUtil.fetchDLFolder(folderId);
			if (dlFolder != null)
				rep = RepositoryLocalServiceUtil.getRepository(dlFolder.getRepositoryId());
		}

		if (rep != null) {
			if (rep.getClassName().equals(OwncloudRepository.class.getName())) {
				isOwncloudRepository = true;
			}
		}
		
		request.setAttribute("isOwncloudRepository", isOwncloudRepository);
	}

}
