package com.macfu.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration 
public class KaptchaConfig {
	@Bean
	public DefaultKaptcha captchaProducer() {							
		DefaultKaptcha captchaProducer = new DefaultKaptcha();
		Properties properties = new Properties();
		properties.setProperty("kaptcha.border", "yes");				// 边框
		properties.setProperty("kaptcha.border.color", "105,179,90"); 
		properties.setProperty("kaptcha.textproducer.font.color", "red"); 
		properties.setProperty("kaptcha.image.width", "125");			// 宽度
		properties.setProperty("kaptcha.image.height", "45");			// 高度 
		properties.setProperty("kaptcha.textproducer.font.size", "35");	// 大小
		properties.setProperty("kaptcha.session.key", "captcha");	// 属性名称
		properties.setProperty("kaptcha.textproducer.char.length", "4");// 长度
		properties.setProperty("kaptcha.textproducer.font.names", 
			"宋体,楷体,微软雅黑");									// 字体
		Config config = new Config(properties);					// 配置类
		captchaProducer.setConfig(config);						// 保存配置
		return captchaProducer;
	}
}
