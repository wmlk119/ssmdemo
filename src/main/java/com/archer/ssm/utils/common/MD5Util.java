package com.archer.ssm.utils.common;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;

public class MD5Util {
    private static final String HEX_NUMS_STR = "0123456789ABCDEF";
    private static final Integer SALT_LENGTH = 12;

    private static char[] base64EncodeChars = new char[] { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g',
            'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1',
            '2', '3', '4', '5', '6', '7', '8', '9', '+', '/', };

    private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4,
            5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26,
            27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1,
            -1, -1, -1 };

    /**
     * 32位小写MD5加密
     * @param inputStr
     * @return
     */
    public static String GetMD5By32(String inputStr) {
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.reset();
            messageDigest.update(inputStr.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] byteArray = messageDigest.digest();
        StringBuffer md5StrBuff = new StringBuffer();
        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            } else {
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
            }
        }
        return md5StrBuff.toString();
    }

    /**
     * 16进制大写格式的MD5口令加密
     * @param inputStr
     * @return
     */
    public String GetMD5By16(String inputStr) {
        byte[] pwd = null;
        try {
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            md.update(inputStr.getBytes("UTF-8"));
            byte[] digest = md.digest();
            pwd = new byte[digest.length + SALT_LENGTH];
            System.arraycopy(salt, 0, pwd, 0, SALT_LENGTH);
            System.arraycopy(digest, 0, pwd, SALT_LENGTH, digest.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ByteToHexString(pwd);
    }

    /**
     * 6进制MD5口令解密
     * @param inputStr
     * @param md5Str
     * @return
     */
    public boolean ValidMD5By16(String inputStr, String md5Str) {
        byte[] digest = null;
        byte[] digestInDb = null;
        try {
            byte[] pwdInDb = HexStringToByte(md5Str);
            byte[] salt = new byte[SALT_LENGTH];
            System.arraycopy(pwdInDb, 0, salt, 0, SALT_LENGTH);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            md.update(inputStr.getBytes("UTF-8"));
            digest = md.digest();
            digestInDb = new byte[pwdInDb.length - SALT_LENGTH];
            System.arraycopy(pwdInDb, SALT_LENGTH, digestInDb, 0, digestInDb.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Arrays.equals(digest, digestInDb)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 字节数组格式化为16进制字符串
     * @param b
     * @return
     */
    public String ByteToHexString(byte[] b) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            hexString.append(hex.toUpperCase());
        }
        return hexString.toString();
    }

    /**
     * 16进制字符串转换成字节数组
     * @param hex
     * @return
     */
    public byte[] HexStringToByte(String hex) {
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] hexChars = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (HEX_NUMS_STR.indexOf(hexChars[pos]) << 4 | HEX_NUMS_STR.indexOf(hexChars[pos + 1]));
        }
        return result;
    }

    /**
     *
     * @param str
     * @return
     */
    public byte[] decode(String str) {
        byte[] data = str.getBytes();
        int len = data.length;
        ByteArrayOutputStream buf = new ByteArrayOutputStream(len);
        int i = 0;
        int b1, b2, b3, b4;

        while (i < len) {
            do {
                b1 = base64DecodeChars[data[i++]];
            } while (i < len && b1 == -1);
            if (b1 == -1) {
                break;
            }

            do {
                b2 = base64DecodeChars[data[i++]];
            } while (i < len && b2 == -1);
            if (b2 == -1) {
                break;
            }
            buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));

            do {
                b3 = data[i++];
                if (b3 == 61) {
                    return buf.toByteArray();
                }
                b3 = base64DecodeChars[b3];
            } while (i < len && b3 == -1);
            if (b3 == -1) {
                break;
            }
            buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));
            do {
                b4 = data[i++];
                if (b4 == 61) {
                    return buf.toByteArray();
                }
                b4 = base64DecodeChars[b4];
            } while (i < len && b4 == -1);
            if (b4 == -1) {
                break;
            }
            buf.write((int) (((b3 & 0x03) << 6) | b4));
        }
        return buf.toByteArray();
    }

    /**
     * 设置账户加密规则
     * @param leftStr
     * @param passWord
     * @return
     */
    public static String encodeAccPwd(String leftStr, String passWord) {
        return GetMD5By32(leftStr + "|" + passWord);
    }

    /**
     * 验证AccPwd
     * @param leftStr
     * @param passWord
     * @param targetStr
     * @return
     */
    public static boolean validAccPwd(String leftStr, String passWord, String targetStr){
        return targetStr.equals(encodeAccPwd(leftStr,passWord));
    }

}
