package com.example.user.service;

public interface IdentifyingCodeService {
    String getIdentifyingCode(String email, IdentifyingCodeType type, long expireTimeInSeconds);
    boolean testIdentifyingCode(String email, IdentifyingCodeType type, String code );
    static enum IdentifyingCodeType {
        REGISTER,
        PASSWORD_RESET
    }
}