package com.example.exception;

public class RedisKeyNotFoundException extends CustomException{
    public RedisKeyNotFoundException(String key) {
        super("A0500", "Redis键不存在: " + key);
    }
}
