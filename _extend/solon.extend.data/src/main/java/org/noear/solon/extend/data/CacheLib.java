package org.noear.solon.extend.data;

import org.noear.solon.annotation.Note;
import org.noear.solon.core.cache.CacheService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CacheLib {
    //
    // XCacheExecutor 对接
    //
    private static Map<String, CacheService> cacheServiceMap = new HashMap<>();


    /**
     * 缓存服务集合；只读
     */
    @Note("缓存服务集合；只读")
    public static Map<String, CacheService> cacheServiceMap() {
        return Collections.unmodifiableMap(cacheServiceMap);
    }

    /**
     * 添加缓存服务
     */
    @Note("添加缓存服务")
    public static void cacheServiceAdd(String name, CacheService cs) {
        cacheServiceMap.put(name, cs);
    }

    @Note("添加缓存服务")
    public static void cacheServiceAddIfAbsent(String name, CacheService cs) {
        cacheServiceMap.putIfAbsent(name, cs);
    }

    /**
     * 获取缓存服务
     */
    @Note("获取缓存服务")
    public static CacheService cacheServiceGet(String name) {
        return cacheServiceMap.get(name);
    }
}
