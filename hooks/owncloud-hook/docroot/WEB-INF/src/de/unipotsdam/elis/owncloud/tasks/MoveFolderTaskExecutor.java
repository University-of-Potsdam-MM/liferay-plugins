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
				// TODO: Passwort steht bei SSO nicht zur verfügung
				String folderId = OwncloudRepositoryUtil.getRootFolderIdFromGroupId(group.getGroupId());
				if (OwncloudRepositoryUtil.getWebdavRepositoryAsUser().exists(folderId)) {
					OwncloudRepositoryUtil.getWebdavRepositoryAsUser().move(folderId,
							OwncloudRepositoryUtil.getRootFolderIdFromGroupId(group.getGroupId()));
					log.debug("move: " + folderId + " user: " + PrincipalThreadLocal.getName());
				}
			}
		}
		return BackgroundTaskResult.SUCCESS;
	}

}
