package de.unipotsdam.elis.custompages;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.service.UserLocalServiceWrapper;

import de.unipotsdam.elis.custompages.service.CustomPageFeedbackLocalServiceUtil;

public class CustomUserLocalServiceWrapper extends UserLocalServiceWrapper {

	public CustomUserLocalServiceWrapper(UserLocalService userLocalService) {
		super(userLocalService);
	}
	
	@Override
	public User deleteUser(User user) throws PortalException, SystemException {
		User deletedUser = super.deleteUser(user);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByUserId(deletedUser.getUserId());
		
		return deletedUser;
	}
	
	@Override
	public User deleteUser(long userId) throws PortalException, SystemException {
		User deletedUser = super.deleteUser(userId);
		
		CustomPageFeedbackLocalServiceUtil.deleteCustomPageFeedbackByUserId(userId);
		
		return deletedUser;
	}

}
