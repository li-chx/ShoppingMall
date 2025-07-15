package com.example.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Date;

/**
 * Token工具类
 */
@Component
public class TokenUtils {

    private static final String TOKEN = "token";

    private static final Logger log = LoggerFactory.getLogger(TokenUtils.class);

    /**
     * 生成token
     */
    public static String createToken(String data, String sign) {
        return JWT.create().withAudience(data) // 将 userId-role 保存到 token 里面,作为载荷
                .withExpiresAt(DateUtil.offsetHour(new Date(), 2)) // 2小时后token过期
                .sign(Algorithm.HMAC256(sign)); // 以 password 作为 token 的密钥
    }

    /**
     * 获取当前登录的用户ID
     */
    public static String getCurrentUserId() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                return userRole.split("-")[0];  // 获取用户id
            }
        } catch (Exception e) {
            log.error("获取当前用户ID出错", e);
        }
        return null;
    }

    /**
     * 获取当前登录的用户角色
     */
    public static String getCurrentUserRole() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String token = request.getHeader(TOKEN);
            if (ObjectUtil.isNotEmpty(token)) {
                String userRole = JWT.decode(token).getAudience().get(0);
                return userRole.split("-")[1];  // 获取角色
            }
        } catch (Exception e) {
            log.error("获取当前用户角色出错", e);
        }
        return null;
    }

    /**
     * 验证token是否有效
     */
    public static boolean verifyToken(String token, String sign) {
        try {
            JWT.require(Algorithm.HMAC256(sign)).build().verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从token中获取用户角色信息
     */
    public static String getUserRoleFromToken(String token) {
        try {
            String userRole = JWT.decode(token).getAudience().get(0);
            return userRole.split("-")[1];
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 从token中获取用户ID
     */
    public static String getUserIdFromToken(String token) {
        try {
            String userRole = JWT.decode(token).getAudience().get(0);
            return userRole.split("-")[0];
        } catch (JWTDecodeException e) {
            return null;
        }
    }
}

