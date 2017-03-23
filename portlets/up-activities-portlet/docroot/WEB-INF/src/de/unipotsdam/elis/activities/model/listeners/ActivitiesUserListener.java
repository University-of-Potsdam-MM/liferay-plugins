package de.unipotsdam.elis.activities.model.listeners;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;

import de.unipotsdam.elis.activities.extservice.moodle.rest.MoodleRestClient;

public class ActivitiesUserListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {

		try {
			MoodleRestClient.addCampusUpUser(user.getScreenName());
		} catch (Exception e) {
			// just do nothing exception prevents users form being created
		}

	}

}
