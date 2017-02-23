package de.unipotsdam.elis.activities.model.listeners;

import javax.ws.rs.ClientErrorException;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;

import de.unipotsdam.elis.activities.extservice.moodle.rest.MoodleRestClient;

public class ActivitiesUserListener extends BaseModelListener<User> {

	@Override
	public void onAfterCreate(User user) throws ModelListenerException {

		try {
			// TODO do not add default user, can default user be created?
			MoodleRestClient.addCampusUpUser(user.getScreenName());
		} catch (Exception e) {
			throw new ModelListenerException(e);
		}

	}

}
