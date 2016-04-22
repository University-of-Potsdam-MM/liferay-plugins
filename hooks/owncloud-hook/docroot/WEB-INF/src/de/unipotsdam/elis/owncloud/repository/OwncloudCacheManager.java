package de.unipotsdam.elis.owncloud.repository;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;

public class OwncloudCacheManager {

	public final static String WEBDAV_MODEL_CACHE_NAME = "WebdavModelCache";
	public final static String WEBDAV_CHILDREN_CACHE_NAME = "WebdavChildrenCache";

	public static Object getFromCache(String cacheName, String cacheKey) {
		ThreadLocalCache<Object> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.REQUEST,
				cacheName);
		return threadLocalCache != null ? threadLocalCache.get(cacheKey) : null;
	}

	public static void putToCache(String cacheName, String cacheKey, Object value) {
		ThreadLocalCache<Object> threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.REQUEST,
				cacheName);
		threadLocalCache.put(cacheKey, value);
	}

}
