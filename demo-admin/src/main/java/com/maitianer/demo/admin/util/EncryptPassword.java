package com.maitianer.demo.admin.util;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.util.Assert;

/**
 * @Author Chen
 * @Date 2019/8/12 10:38
 */
public class EncryptPassword {

    public static String encryptedPassword(String password) {
        Assert.hasText(password, "加密的密码不能为空！");
        return new Sha256Hash(password).toHex();
    }
}
