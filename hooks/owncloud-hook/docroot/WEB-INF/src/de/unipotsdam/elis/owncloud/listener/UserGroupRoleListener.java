package de.unipotsdam.elis.owncloud.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.UserGroupRole;

public class UserGroupRoleListener implements ModelListener<UserGroupRole> {

	@Override
	public void onAfterAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterAddAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
	}

	@Override
	public void onAfterCreate(UserGroupRole model) throws ModelListenerException {
		try {
			System.out.println("onAfterCreate: " + model.getUser().getFullName());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onAfterRemove(UserGroupRole model) throws ModelListenerException {
		try {
			System.out.println("onAfterRemove: " + model.getUser().getFullName());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onAfterRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterRemoveAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
	}

	@Override
	public void onAfterUpdate(UserGroupRole model) throws ModelListenerException {
		try {
			System.out.println("onAfterUpdate: " + model.getUser().getFullName());
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onBeforeAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeCreate(UserGroupRole model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeRemove(UserGroupRole model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeUpdate(UserGroupRole model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

}
