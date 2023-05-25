package com.xj.xjapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * @ClassName SignUtils
 * @Description 签名工具
 * @Author 嘻精
 * @Date 2023/5/20 15:51
 * @Version 1.0
 */

public class SignUtils {
    /**
     * 生成签名
     * @param body
     * @param secretKey
     * @return
     */
    public static String genSign(String body, String secretKey) {
        // 用hutool工具创建单向加密
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        // 将传入的map和密钥组合并加密
        String content = body + "." + secretKey;
        // 返回加密后的签名
        return md5.digestHex(content);
    }
}
