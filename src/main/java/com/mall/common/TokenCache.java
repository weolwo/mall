package com.mall.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * 用Guava进行缓存Token
 */
public class TokenCache {
    private static final Logger loger = LoggerFactory.getLogger(TokenCache.class);

    public static final String TOKEN_PREFIX = "token_";

    public static LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .initialCapacity(1000)
            .maximumSize(10000)
            .expireAfterAccess(13, TimeUnit.HOURS)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String arg0) throws Exception {
                //这边重写返回，但key没有命中时返回空"null"，如果直接返回null会报错
                    return "null";
                }

            });

    public static void setKey(String key, String value) {
        loadingCache.put(key, value);
    }

    public static String getKey(String key) {
        String value = null;
        try {
            value = loadingCache.get(key);
            if (value.equals("null")) {
                return null;
            }
        } catch (ExecutionException e) {
            loger.error("获取缓存Token出错", e);
        }
        return value;
    }
}
