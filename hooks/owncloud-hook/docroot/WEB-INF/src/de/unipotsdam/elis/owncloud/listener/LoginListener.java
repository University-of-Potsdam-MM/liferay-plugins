package de.unipotsdam.elis.owncloud.listener;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.BackgroundTaskLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import de.unipotsdam.elis.owncloud.tasks.MoveFolderTaskExecutor;

public class LoginListener extends Action {
	
	private static Log log = LogFactoryUtil.getLog(LoginListener.class);

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response) throws ActionException {
		log.debug("run LoginListener");
		try {
			BackgroundTaskLocalServiceUtil.addBackgroundTask(Long.parseLong(request.getRemoteUser()), 0,
					StringPool.BLANK, new String[]{"owncloud-hook"}, MoveFolderTaskExecutor.class, null, new ServiceContext());

		} catch (SystemException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (PortalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
