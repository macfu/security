package com.macfu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: liming
 * @Date: 2019/01/10 9:58
 * @Description:
 */
@Configuration
// 必须追加此注解才表示进行的是SpringSecurity配置
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests().antMatchers("/pages/message/**").access("hasRole('ADMIN')")
                .antMatchers("/welcomePage.action").authenticated()
                .antMatchers("/**").permitAll();
        http.formLogin()
                .usernameParameter("mid")
                .passwordParameter("pwd")
                .successForwardUrl("/welcomePage.action")
                .loginPage("loginPage.action")
                .loginProcessingUrl("/mldn-login")
                .failureForwardUrl("/loginPage.action?error=true")
                .and()
                .logout()
                .logoutUrl("/mldn-logout")
                .logoutSuccessUrl("/logoutPage.action")
                .deleteCookies("JESSIONID")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error_403.action");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("{bcrypt}$2a$10$2y7higVhnHCn2L8//r/EVed2zi/LrQ.Y.svV.oeLqUM8xfUx5JWQC")
                .roles("USER", "ADMIN");
        auth.inMemoryAuthentication()
                .withUser("mldn")
                .password("{bcrypt}$2a$10$vjXs780rO3rF8ZAXuBL4..c9icL4JDvr3sweCIU9y/QWiYlHgbKGa")
                .roles("SUER");
    }
}
