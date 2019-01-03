package com.macfu;

import org.junit.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Author: liming
 * @Date: 2019/1/3 13:53
 * @Description: testPassword
 */
public class TestPasswordEncoder {
    @Test
    public void testPassword() {
        String password = "hello";
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encode = passwordEncoder.encode(password);
        // {bcrypt}$2a$10$K8jYewArl2P3Lg/4UIDibe7BRVkP5pYrnWziqDurmu3s/pGT1YWv2
        System.out.println("加密后密码:" + encode);
        System.out.println("【验证密码是否匹配】" + passwordEncoder.matches(password, encode));
    }
}
