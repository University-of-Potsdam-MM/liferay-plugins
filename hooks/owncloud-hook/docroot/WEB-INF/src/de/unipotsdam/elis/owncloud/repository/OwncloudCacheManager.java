package de.unipotsdam.elis.owncloud.repository;

import com.liferay.portal.kernel.cache.Lifecycle;
import com.liferay.portal.kernel.cache.ThreadLocalCache;
import com.liferay.portal.kernel.cache.ThreadLocalCacheManager;

public class OwncloudCacheManager {

	public final static String WEBDAV_MODEL_CACHE_NAME = "WebdavModelCache";
	public final static String WEBDAV_CHILDREN_CACHE_NAME = "WebdavChildrenCache";
	public final static String WEBDAV_ERROR_CACHE_NAME = "WebdavError";
	public final static String WEBDAV_PASSWORD_CACHE_NAME = "WebdavPassword";

	public static Object getFromCache(String cacheName, String cacheKey) {
		ThreadLocalCache<Object> threadLocalCache = null;
		if (cacheName.equals(WEBDAV_PASSWORD_CACHE_NAME))
			threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.SESSION, cacheName);
		else
			threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.REQUEST, cacheName);
		return threadLocalCache != null ? threadLocalCache.get(cacheKey) : null;
	}

	public static void putToCache(String cacheName, String cacheKey, Object value) {
		ThreadLocalCache<Object> threadLocalCache = null;
		if (cacheName.equals(WEBDAV_PASSWORD_CACHE_NAME))
			threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.SESSION, cacheName);
		else
			threadLocalCache = ThreadLocalCacheManager.getThreadLocalCache(Lifecycle.REQUEST, cacheName);
		threadLocalCache.put(cacheKey, value);
	}

}
