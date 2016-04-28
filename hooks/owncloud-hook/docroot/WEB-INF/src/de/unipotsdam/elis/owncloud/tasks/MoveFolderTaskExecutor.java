package de.unipotsdam.elis.owncloud.tasks;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.owncloud.util.OwncloudRepositoryUtil;

public class MoveFolderTaskExecutor extends BaseBackgroundTaskExecutor {
	
	private static Log log = LogFactoryUtil.getLog(MoveFolderTaskExecutor.class);

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) throws Exception {
		log.debug("execute MoveFolderTaskExecutor");
		User user = UserLocalServiceUtil.getUser(backgroundTask.getUserId());
		for (Group group : user.getGroups()) {
			if (group.isSite()) {
				String folderId = OwncloudRepositoryUtil.getRootFolderIdFromGroupId(group.getGroupId());
				String username = UserLocalServiceUtil.getUser(PrincipalThreadLocal.getUserId()).getLogin();
				String password = PrincipalThreadLocal.getPassword();
				if (OwncloudRepositoryUtil.getWebdavRepository(username, password).exists(folderId)) {
					OwncloudRepositoryUtil.getWebdavRepository(username, password).move(folderId,
							OwncloudRepositoryUtil.getRootFolderIdFromGroupId(group.getGroupId()));
					log.debug("move: " + folderId + " user: " + username);
				}
			}
		}
		return BackgroundTaskResult.SUCCESS;
	}

}
