package de.unipotsdam.elis.activities.extservice.moodle.schedule;

import javax.ws.rs.ClientErrorException;
import javax.xml.datatype.DatatypeConfigurationException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.util.GetterUtil;

import de.unipotsdam.elis.activities.ActivitiesAdminPortlet;
import de.unipotsdam.elis.activities.extservice.util.ExtSocialActivityUtil;


public class Scheduler implements MessageListener {

	public void receive(Message arg0) throws MessageListenerException {
		
		if (GetterUtil.getBoolean(ActivitiesAdminPortlet.getMoodleServiceSchedulerActive())) {
			try {
				ExtSocialActivityUtil.requestNewMoodleActivities();
			} catch (Exception e) {
				throw new MessageListenerException(e);
			}
		}
	}
}
