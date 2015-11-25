package de.unipotsdam.elis.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceWrapper;

public class CustomUserLocalService extends UserLocalServiceWrapper {

	public CustomUserLocalService(UserLocalService userLocalService) {
		super(userLocalService);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void addGroupUser(long groupId, long userId) throws SystemException {
		System.out.println("add user 3");
		// TODO Auto-generated method stub
		super.addGroupUser(groupId, userId);
	}
	
	@Override
	public void addGroupUser(long groupId, User user) throws SystemException {
		System.out.println("add user 2");
		super.addGroupUser(groupId, user);
	}	
}
