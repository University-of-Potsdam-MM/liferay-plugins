package de.unipotsdam.elis.owncloud.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.model.User;

public class UserListener implements ModelListener<User>{

	@Override
	public void onAfterAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterAddAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
	}

	@Override
	public void onAfterCreate(User model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterRemove(User model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterRemoveAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
	}

	@Override
	public void onAfterUpdate(User model) throws ModelListenerException {
		System.out.println("update: " + model.getFullName());
		
	}

	@Override
	public void onBeforeAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeCreate(User model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeRemove(User model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBeforeUpdate(User model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

}
