package com.example.user.utils;



import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.example.exception.RedisKeyExpiredException;
import com.example.exception.RedisKeyNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

//本类为简化 Redis 缓存操作 的工具类

@Slf4j
@Component
public class RedisCache {
    static class InternalClass<T> {
        public T data;
        InternalClass(T data) {
            this.data = data;
        }
    }

    private final StringRedisTemplate stringRedisTemplate;

    public RedisCache(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * 存储普通对象，并设置过期时间
     */
    public void set(String key, Object value, Long time, TimeUnit unit) {
        var obj = new InternalClass<>(value);
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(obj), time, unit);
    }

    /**
     * 存储普通对象，不设置过期时间
     */

    public void set(String key, Object value) {
        stringRedisTemplate.opsForValue().set(key, JSONUtil.toJsonStr(new InternalClass<>(value)));
    }

    /**
     * 获取普通对象，读取字符串，转为指定类型的对象
     */
    public <T> T get(String key, Class<T> type) {
        String json = stringRedisTemplate.opsForValue().get(key);
        return StrUtil.isBlank(json) ? null : ((InternalClass<T>)JSONUtil.toBean(json, InternalClass.class)).data;
    }

    /**
     * 获取列表对象
     */
    public <T> List<T> getList(String key, Class<T> type) {
        String json = stringRedisTemplate.opsForValue().get(key);
        return StrUtil.isBlank(json) ? null : ((InternalClass<List<T>>)JSONUtil.toBean(json, InternalClass.class)).data;
    }

    /**
     * 获取普通对象，键不存在时抛出异常
     */
    public <T> T getOrThrow(String key, Class<T> type) {
        if (!hasKey(key)) {
            throw new RedisKeyNotFoundException(key);
        }
        return get(key, type);
    }

    /**
     * 获取列表对象，键不存在时抛出异常
     */
    public <T> List<T> getListOrThrow(String key, Class<T> type) {
        if (!hasKey(key)) {
            throw new RedisKeyNotFoundException(key);
        }
        return getList(key, type);
    }

    /**
     * 获取带过期检查的对象（检查的是"即将过期"而非"已过期"的键）
     */
    public <T> T getWithExpireCheck(String key, Class<T> type, long minTimeToLive, TimeUnit unit) {
        if (!hasKey(key)) {
            throw new RedisKeyNotFoundException(key);
        }

        Long expireTime = getExpire(key, unit);
        if (expireTime != null && expireTime < minTimeToLive) {
            throw new RedisKeyExpiredException(key, expireTime);
        }

        return get(key, type);
    }

    /**
     * 获取带过期检查的列表（检查的是"即将过期"而非"已过期"的键）
     */
    public <T> List<T> getListWithExpireCheck(String key, Class<T> type, long minTimeToLive, TimeUnit unit) {
        if (!hasKey(key)) {
            throw new RedisKeyNotFoundException(key);
        }

        Long expireTime = getExpire(key, unit);
        if (expireTime != null && expireTime < minTimeToLive) {
            throw new RedisKeyExpiredException(key, expireTime);
        }

        return getList(key, type);
    }


    // 设置过期时间
    public Boolean expire(String key, long timeout, TimeUnit unit) {
        return stringRedisTemplate.expire(key, timeout, unit);
    }

    // 获取剩余过期时间
    public Long getExpire(String key, TimeUnit unit) {
        return stringRedisTemplate.getExpire(key, unit);
    }


    /**
     * 删除key
     */
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    /**
     * 判断key是否存在
     */
    public Boolean hasKey(String key) {
        return stringRedisTemplate.hasKey(key);
    }
}
