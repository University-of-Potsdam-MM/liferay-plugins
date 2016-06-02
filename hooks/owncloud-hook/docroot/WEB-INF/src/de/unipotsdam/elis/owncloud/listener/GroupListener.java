package de.unipotsdam.elis.owncloud.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;
import de.unipotsdam.elis.webdav.WebdavConfigurationLoader;

public class GroupListener implements ModelListener<Group> {

	@Override
	public void onAfterAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
	}

	@Override
	public void onAfterCreate(Group model) throws ModelListenerException {
		try {
			if (model.isSite()) {
				RepositoryServiceUtil.addRepository(model.getGroupId(),
						PortalUtil.getClassNameId(OwncloudRepository.class.getName()), 0,
						WebdavConfigurationLoader.getRepositoryName(), StringPool.BLANK, PortletKeys.DOCUMENT_LIBRARY,
						new UnicodeProperties(), new ServiceContext());
				OwncloudRepositoryUtil.getWebdavRepositoryAsRoot().createFolder(
						OwncloudRepositoryUtil.getRootFolderIdFromGroupId(model.getGroupId()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onAfterRemove(Group model) throws ModelListenerException {
	}

	@Override
	public void onAfterRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
	}

	@Override
	public void onAfterUpdate(Group model) throws ModelListenerException {
	}

	@Override
	public void onBeforeAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
	}

	@Override
	public void onBeforeCreate(Group model) throws ModelListenerException {
	}

	@Override
	public void onBeforeRemove(Group model) throws ModelListenerException {
	}

	@Override
	public void onBeforeRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
	}

	@Override
	public void onBeforeUpdate(Group model) throws ModelListenerException {
	}

}
