package com.macfu.config;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;
import java.util.Iterator;

public class IPAddressVoter implements AccessDecisionVoter<Object> {
	private static final String LOCAL_FLAG = "LOCAL_IP" ; // 配置标记
	@Override
	public int vote(Authentication authentication, Object object, 
			Collection<ConfigAttribute> attributes) {
		// 如果此时的认证信息不是来自于WEB的认证处理，那么就不再进行各种验证，直接投出反对票
		if (!(authentication.getDetails() instanceof WebAuthenticationDetails)) {
			return AccessDecisionVoter.ACCESS_DENIED ; // 反对
		}
		// 获取WEB认证详情的信息内容
		WebAuthenticationDetails details = (WebAuthenticationDetails) authentication.getDetails() ;
		String ip = details.getRemoteAddress() ; // 获取用户登录的ip地址
		Iterator<ConfigAttribute> iter = attributes.iterator() ;
		while (iter.hasNext()) {
			ConfigAttribute ca = iter.next() ; 	// 获取配置属性
			if (ca.toString().contains(LOCAL_FLAG)) {	// 有指定标记
				if ("127.0.0.1".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip)) {
					return AccessDecisionVoter.ACCESS_GRANTED ; // 赞成
				}
			}
		}
		return AccessDecisionVoter.ACCESS_ABSTAIN ; // 弃权
	}
	@Override
	public boolean supports(ConfigAttribute attribute) {
		System.out.println("【投票器】attrbite = " + attribute);
		return attribute != null && attribute.toString().contains(LOCAL_FLAG); 
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return true; 
	}

	

}
