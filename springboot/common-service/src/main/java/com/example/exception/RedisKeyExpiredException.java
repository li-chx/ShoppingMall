package com.example.exception;

public class RedisKeyExpiredException extends CustomException {
    public RedisKeyExpiredException(String key, long remainTime) {
        super("A0501", "Redis键已过期或即将过期: " + key + ", 剩余时间: " + remainTime);
    }
}
