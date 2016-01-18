package de.unipotsdam.elis.owncloud.listener;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ModelListener;

public class GroupListener implements ModelListener<Group> {

	@Override
	public void onAfterAddAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterAddAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
	}

	@Override
	public void onAfterCreate(Group model) throws ModelListenerException {
		System.out.println("site created: " + model.getName());
		
	}

	@Override
	public void onAfterRemove(Group model) throws ModelListenerException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAfterRemoveAssociation(Object classPK, String associationClassName, Object associationClassPK)
			throws ModelListenerException {
		System.out.println("onAfterRemoveAssociation: " + associationClassName + " " + classPK + " " + associationClassPK);
		
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
		// TODO Auto-generated method stub
		
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
