package de.unipotsdam.elis.owncloud.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudRepository;
import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;
import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;

public class GroupListener implements ModelListener<Group> {

	@Override
	public void onAfterAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterAddAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		if (associationClassName.equals(User.class.getName())) {
			try {
				User user = UserLocalServiceUtil.getUser((Long) associationClassPK);
				long groupId = (Long) classPK;
				OwncloudShareCreator.createShare(user, groupId,
						OwncloudRepositoryUtil.getRootFolderIdFromGroupId(groupId));
			} catch (PortalException e) {
				e.printStackTrace();
			} catch (SystemException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onAfterCreate(Group model) throws ModelListenerException {
	}

	@Override
	public void onAfterRemove(Group model) throws ModelListenerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAfterRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterRemoveAssociation: " + associationClassName + " " + classPK + " "
				+ associationClassPK);

	}

	@Override
	public void onAfterUpdate(Group model) throws ModelListenerException {
		System.out.println("site updated: " + model.getName());

	}

	@Override
	public void onBeforeAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBeforeCreate(Group model) throws ModelListenerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBeforeRemove(Group model) throws ModelListenerException {
		OwncloudRepositoryUtil.getWebdavRepository().createFolder(
				OwncloudRepositoryUtil.getRootFolderIdFromGroupId(model.getGroupId()));
	}

	@Override
	public void onBeforeRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBeforeUpdate(Group model) throws ModelListenerException {
		// TODO Auto-generated method stub

	}

}
