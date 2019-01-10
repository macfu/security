package com.macfu.filter;

import com.google.code.kaptcha.Constants;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: liming
 * @Date: 2019/01/10 11:20
 * @Description: 实现一个具有验证码检测的用户认证处理过滤器
 */
public class ValidatorCodeUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // 验证码输入参数
    private String codeParameter = "code";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // 通过session获取已经生成的验证码的数据信息
        String captcha = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        // 获取输入的验证码信息
        String code = request.getParameter(this.codeParameter);
        String username = super.obtainUsername(request);
        String password = super.obtainPassword(request);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        // 牵扯到后续的验证问题
        super.setDetails(request, authRequest);
        // 如果当前请求的过程之中输入的验证码是null，生成的验证码也同样是null的时候，那么就需要进行错误提示
        if (captcha == null || "".equals(captcha) || code == null || "".equals(code) || !captcha.equalsIgnoreCase(code)) {
            // 设置用户信息
            request.getSession().setAttribute("SPRING_SECURITY_LAST_USERNAME", username);
            throw new AuthenticationServiceException("验证码不正确");
        }
        return super.getAuthenticationManager().authenticate(authRequest);
    }

    // 此名称是可以修改的
    public void setCodeParameter(String codeParameter) {
        this.codeParameter = codeParameter;
    }
}
