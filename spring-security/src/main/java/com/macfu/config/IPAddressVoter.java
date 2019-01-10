package com.macfu.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.Iterator;

/**
 * @Author: liming
 * @Date: 2019/01/09 16:17
 * @Description: 本地ip地址的投票器
 */
public class IPAddressVoter implements AccessDecisionVoter<Object> {

    private static final String LOCAL_FLAG = "LOCAL_IP";

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        System.out.println("【投票器】attribute" + configAttribute);
        return configAttribute != null && configAttribute.toString().contains(LOCAL_FLAG);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

    @Override
    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> collection) {
        // 如果此时的认证信息不是来自于WEb的认证处理，那么就不在进行各种验证，直接投出反对票
        if (!(authentication.getDetails() instanceof WebAuthenticationDetails)) {
            // 反对
            return AccessDecisionVoter.ACCESS_DENIED;
        }
        // 获取web认证的详情详细内容
        WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails();
        // 获取用户登录ip
        String ip = details.getRemoteAddress();
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            // 获取配置属性
            ConfigAttribute ca = iterator.next();
            // 有指定标记
            if (ca.toString().contains(LOCAL_FLAG)) {
                if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
                    // 赞成
                    return AccessDecisionVoter.ACCESS_GRANTED;
                }
            }
        }
        // 弃权
        return AccessDecisionVoter.ACCESS_ABSTAIN;
    }
}
