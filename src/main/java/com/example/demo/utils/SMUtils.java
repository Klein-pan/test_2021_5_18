package com.example.demo.utils;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SmUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.SM2;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * SMUtils：SM加密工具类
 * @Date 2020-10-29
 * @version 1.0
 */
@Component
@Slf4j
public class SMUtils {

    /**
     * 默认的 sm4加密key
     */
    public static final String DEFAULT_SM4_SECRET_KEY = "zumqspv5eyqy47et";

    /**
     * SM2加密例子
     * @param data 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String toSM2Str(String data) {
        SM2 sm2 = SmUtil.sm2();
        // 公钥加密，私钥解密
        String encryptStr = sm2.encryptBcd(data, KeyType.PublicKey);
        String decryptStr = StrUtil.utf8Str(sm2.decryptFromBcd(encryptStr, KeyType.PrivateKey));
        return encryptStr;
    }

    /**
     * SM3加密
     * @param data 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String toSM3Str(String data) {
        return SmUtil.sm3(data).toUpperCase();
    }

    /**
     * SM4加密
     * @param data 需要加密的字符串
     * @param secretKey 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String toSM4Str(String data,String secretKey) {
        SymmetricCrypto sm4 = SmUtil.sm4(secretKey.getBytes(CharsetUtil.CHARSET_UTF_8));
        return sm4.encryptHex(data, CharsetUtil.CHARSET_UTF_8).toUpperCase();
    }

    public static String toSM4Str(String data) {
        return toSM4Str(data,DEFAULT_SM4_SECRET_KEY);
    }

    /**
     * SM4解密
     * @param data 需要加密的字符串
     * @param secretKey 需要加密的字符串
     * @return 返回加密后的字符串
     */
    public static String parseSM4Str(String data,String secretKey) {
        SymmetricCrypto sm4 = SmUtil.sm4(secretKey.getBytes(CharsetUtil.CHARSET_UTF_8));
        return sm4.decryptStr(data, CharsetUtil.CHARSET_UTF_8);
    }

    public static String parseSM4Str(String data) {
        return parseSM4Str(data,DEFAULT_SM4_SECRET_KEY);
    }


}