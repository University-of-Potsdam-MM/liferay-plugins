package de.unipotsdam.elis.activities.extservice.moodle.schedule;

import java.util.List;

import javax.ws.rs.ClientErrorException;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

import de.unipotsdam.elis.activities.extservice.moodle.rest.MoodleRestClient;

public class SchedulerAdd implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		
		User defaultUser = null;
		
		try {
			defaultUser = UserLocalServiceUtil.getDefaultUser(PortalUtil.getDefaultCompanyId());
		} catch (PortalException | SystemException e) {
			/*
			 * If no default user is set an exception will occur. 
			 * But it's not important, because we won't use the default user anyways.
			 */
		}
		
		// once a day iterate through all users and try to add them to moodle
		// on moodle side no duplicates are created
		try {
			List<User> users = UserLocalServiceUtil.getUsers(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			
			long defaultUserId = 0;
			if (defaultUser != null)
				defaultUserId = defaultUser.getUserId();
			
			for (User user : users) {
				try {
					// do not add default user
					if (defaultUserId != user.getUserId()) {
						MoodleRestClient.addCampusUpUser(user.getScreenName());
					}
				} catch (JSONException | ClientErrorException e) {
//					_log.error(e);
					throw new MessageListenerException(e);
				}
			}
	
		} catch (SystemException e) {
			throw new MessageListenerException(e);
		}
		
	}

}
