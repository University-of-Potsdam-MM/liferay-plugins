package de.unipotsdam.elis.activities.extservice.moodle.schedule;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;  
import com.liferay.portal.kernel.messaging.MessageListenerException;

public class MoodleActivityRequestJob implements MessageListener {

	@Override
	public void receive(Message message) throws MessageListenerException {
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println("keks1:" + dateFormat.format(date));		 
	}

}
