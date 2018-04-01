package com.archer.ssm.utils.common;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 密码工具类
 *
 * @author Administrator
 * @create 2018-03-28 10:33
 */
public class PasswordUtil extends DigestUtils {

    public static String md5Hex(final String data) {
        return DigestUtils.md5DigestAsHex(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     *
     * @param bytes
     * @return
     */
    public static String md5Hex(final byte[] bytes) {
        return DigestUtils.md5DigestAsHex(bytes);
    }

    /**
     * 盐值加密
     * @param source
     * @return
     */
    public static PasswordHash encrypt(Object source) {
        return new PasswordHash(source);
    }

    /**
     * 盐值加密
     * @param source
     * @return
     */
    public static PasswordHash encrypt(Object source, String salt) {
        return new PasswordHash(source, salt);
    }

    /**
     * 盐值加密
     * @param source
     * @return
     */
    public static String encryptString(Object source, String salt) {
        return new PasswordHash(source, salt).getHexEncoded();
    }

    /**
     * 密码校验
     * @param source
     * @param salt
     * @param password
     * @return
     */
    public static boolean verify(Object source, String salt, String password) {
        return password.equals(encryptString(source, salt));

    }


    /**
     * 获取默认密码
     * @return
     */
    public static String getDefaultPassword() {
        return UUIDUtil.getShortUUID();
    }
}
