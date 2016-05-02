package de.unipotsdam.elis.owncloud.tasks;

import java.util.List;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

import de.unipotsdam.elis.owncloud.repository.OwncloudShareCreator;

public class ShareFolderTaskExecutor extends BaseBackgroundTaskExecutor {

	private static Log log = LogFactoryUtil.getLog(ShareFolderTaskExecutor.class);

	@Override
	@SuppressWarnings("unchecked")
	public BackgroundTaskResult execute(BackgroundTask backgroundTask) throws Exception {
		log.debug("execute ShareFolderTaskExecutor");
		User currentUser = UserLocalServiceUtil.getUser(backgroundTask.getUserId());
		List<String> userIds = (List<String>) backgroundTask.getTaskContextMap().get("userIds");
		boolean excludeCurrentUser = (Boolean) backgroundTask.getTaskContextMap().get("excludeCurrentUser");
		long siteGroupId = Long.valueOf((String)backgroundTask.getTaskContextMap().get("siteGroupId"));
		String filePath = (String) backgroundTask.getTaskContextMap().get("filePath");
		for (String userId : userIds) {
			long userIdLong = Long.valueOf(userId).longValue();
			if (!(excludeCurrentUser && userIdLong == currentUser.getUserId())){
				User user = UserLocalServiceUtil.getUser(userIdLong);
				OwncloudShareCreator.createShare(user, siteGroupId, filePath);
			}
		}
		return BackgroundTaskResult.SUCCESS;
	}

}