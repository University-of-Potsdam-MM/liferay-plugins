package de.unipotsdam.elis.activities.hooks.events;

import java.util.List;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.model.impl.SocialActivityInterpreterImpl;
import com.liferay.portlet.social.service.SocialActivityInterpreterLocalServiceUtil;
import com.liferay.util.portlet.PortletProps;

public class StartupAction extends SimpleAction {

	@Override
	public void run(String[] ids) throws ActionException {
		try {
			doRun();
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	protected void doRun() throws Exception {
		initSocialActivityInterpreters();
	}

	protected void initSocialActivityInterpreters() {
		List<SocialActivityInterpreter> activityInterpreters =
			SocialActivityInterpreterLocalServiceUtil.getActivityInterpreters(
				"SO");

		String[] portletIds = PortletProps.getArray("social.activity.interpreter.portlet.ids");

		for (String portletId : portletIds) {
			Filter filter = new Filter(portletId);

			String activityInterpreterClassName = PortletProps.get(
					"social.activity.interpreter", filter);

			try {
				SocialActivityInterpreter activityInterpreter =
					(SocialActivityInterpreter)
						InstanceFactory.newInstance(
							activityInterpreterClassName);

				activityInterpreter = new SocialActivityInterpreterImpl(
					portletId, activityInterpreter);

				if (activityInterpreters != null) {
					for (SocialActivityInterpreter curActivityInterpreter :
							activityInterpreters) {

						if (ArrayUtil.containsAll(
								activityInterpreter.getClassNames(),
								curActivityInterpreter.getClassNames())) {

							SocialActivityInterpreterLocalServiceUtil.
								deleteActivityInterpreter(
									curActivityInterpreter);

							break;
						}
					}
				}
				
				SocialActivityInterpreterLocalServiceUtil.
					addActivityInterpreter(activityInterpreter);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to add social activity interpreter " +
							activityInterpreterClassName);
				}
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(StartupAction.class);

}
