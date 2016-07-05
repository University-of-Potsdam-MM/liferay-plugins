package de.unipotsdam.elis.portlet.documentslibrary.action;

import java.io.FileNotFoundException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.struts.StrutsPortletAction;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.service.RepositoryEntryLocalServiceUtil;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalServiceUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudCache;
import de.unipotsdam.elis.owncloud.repository.OwncloudCacheManager;
import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;

public class CustomStrutsAction implements StrutsPortletAction {

	private static Log _log = LogFactoryUtil.getLog(CustomStrutsAction.class);

	public CustomStrutsAction() {
		super();
	}

	@Override
	public void processAction(PortletConfig portletConfig, ActionRequest actionRequest, ActionResponse actionResponse)
			throws Exception {
	}

	@Override
	public void processAction(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		_log.debug("processAction called");

		createRepositoryIfNotExistent(actionRequest, actionResponse);
		setProperties(actionRequest, actionResponse);

		if (ParamUtil.getString(actionRequest, Constants.CMD, "null").equals("submitPassword")) {
			OwncloudCache.getInstance().putPassword(ParamUtil.getString(actionRequest, "password"));
		}

		originalStrutsPortletAction.processAction(portletConfig, actionRequest, actionResponse);
	}

	@Override
	public String render(PortletConfig portletConfig, RenderRequest renderRequest, RenderResponse renderResponse)
			throws Exception {
		return null;
	}

	@Override
	public String render(StrutsPortletAction originalStrutsPortletAction, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse) throws Exception {
		_log.debug("render called");

		createRepositoryIfNotExistent(renderRequest, renderResponse);
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
		_log.debug("serveResource called");

		createRepositoryIfNotExistent(resourceRequest, resourceResponse);
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
				rep = RepositoryLocalServiceUtil.fetchRepository(dlFolder.getRepositoryId());
		}

		if (rep != null) {
			if (rep.getClassName().equals(OwncloudRepository.class.getName())) {
				isOwncloudRepository = true;
			}
		}

		// TODO: wenn nicht auf true dann wird passwort nicht abgefragt
		request.setAttribute("isOwncloudRepository", true);
	}

	private void createRepositoryIfNotExistent(PortletRequest request, PortletResponse response) throws PortalException, SystemException {
		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		long groupId = OwncloudRepositoryUtil.getCorrectGroup(themeDisplay.getScopeGroupId()).getGroupId();
		if (!hasOwncloudRepository(groupId))
			OwncloudRepositoryUtil.createRepository(groupId);
	}

	private boolean hasOwncloudRepository(long groupId) {
		try {
			Repository repo = RepositoryLocalServiceUtil.fetchRepository(groupId,
					WebdavConfigurationLoader.getRepositoryName(), PortletKeys.DOCUMENT_LIBRARY);
			if (repo != null)
				return repo.getClassName().equals(OwncloudRepository.class.getName());
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return false;
	}

}
