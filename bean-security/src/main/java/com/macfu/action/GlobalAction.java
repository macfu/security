package com.macfu.action;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Collection;

@Controller
public class GlobalAction {
	private Logger log = LoggerFactory.getLogger(GlobalAction.class) ;
	@Autowired
	private DefaultKaptcha captchaProducer ;  
	@RequestMapping(value = "/RandomCode") 
	public ModelAndView kaptcha() {
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		HttpSession session = request.getSession();
		response.setHeader("Pragma", "No-cache");	// 不缓存数据
		response.setHeader("Cache-Control", "no-cache");	// 不缓存数据
		response.setDateHeader("Expires", 0);	// 不失效
		response.setContentType("image/jpeg");	// mime类型
		String capText = captchaProducer.createText();// 获取验证码上的文字
		// 将验证码上的文字保存在session中
		session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
		String code = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);
		this.log.info("验证码为:" + code);
		BufferedImage image = this.captchaProducer.createImage(capText);// 图像
		try {
			OutputStream output = response.getOutputStream() ;
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(image, "JPEG", bos);		// 图像输出
			byte[] buf = bos.toByteArray();
			response.setContentLength(buf.length);
			output.write(buf);
			bos.close();
			output.close();
		} catch (Exception e) {}
		return null ;
	}
	
	@RequestMapping("/loginPage")
	public String login() {
		return "login" ;
	}
	@RequestMapping("/logoutPage")
	public String logout() {
		return "logout" ;
	}
	@RequestMapping("/welcomePage")
	public String welcome() {
		// 获取认证对象，该对象保存所有与认证有关的内容（用户信息属于认证的一部分）
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		// 获得一个用户的详情内容，User是UserDetails接口的子类
		UserDetails details = (UserDetails) authentication.getPrincipal() ; // 获得用户详情
		String username = details.getUsername() ; // 获得用户名
		boolean enabled = details.isEnabled() ; // 获取用户的状态
		this.log.info("【用户名】" + username);
		this.log.info("【用户状态】" + enabled);
		// UserDetails里面会保存有全部的授权信息。
		Collection<? extends GrantedAuthority> authorities = details.getAuthorities() ; // 获取授权
		this.log.info("【授权信息】" + authorities);
		return "welcome" ;
	}

	@RequestMapping("/error_403")
	public String errorPage403() { 
		return "error_page_403" ;
	}
	@RequestMapping("/logoffPage")
	public String logoff() { 
		return "logoff" ;
	}
}
