package com.macfu.config;

import com.macfu.filter.ValidatorCodeUsernamePasswordAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.security.web.session.SimpleRedirectSessionInformationExpiredStrategy;

import javax.sql.DataSource;

/**
 * @Author: liming
 * @Date: 2019/01/10 9:58
 * @Description:s
 */
@Configuration
// 必须追加此注解才表示进行的是SpringSecurity配置
@EnableWebSecurity
// 此时表示要使用相应的注解进行拦截配置，此注解等于之前的启用配置
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private DataSource dataSource;
    @Autowired
    private JdbcTokenRepositoryImpl jdbcTokenRepository;
    @Autowired
    private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
    @Autowired
    private SavedRequestAwareAuthenticationSuccessHandler successHandler;
    @Autowired
    private SimpleUrlAuthenticationFailureHandler failureHandler;
    @Autowired
    private UsernamePasswordAuthenticationFilter authenticationFilter;
    @Autowired
    private LoginUrlAuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.httpBasic().authenticationEntryPoint(this.authenticationEntryPoint);
        http.authorizeRequests()
                .antMatchers("/pages/message/**").access("hasRole('ADMIN')")
                .antMatchers("/welcomePage.action").authenticated()
                .antMatchers("/**").permitAll();
        http.logout()
                .logoutUrl("/mldn-logout")
                .logoutSuccessUrl("/lougoutPage.action")
                .deleteCookies("JESSIONID", "mldn-remember-cookie")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/error_403.action");
        http.rememberMe()
                .rememberMeParameter("remember")
                .key("mldn-lixinghua")
                .tokenValiditySeconds(259200)
                .rememberMeCookieName("mldn-remember-cookie")
                .tokenRepository(this.jdbcTokenRepository);
        http.sessionManagement()
                .invalidSessionUrl("/loginPage.action")
                .maximumSessions(1)
                .expiredSessionStrategy(this.sessionInformationExpiredStrategy);
        http.addFilterBefore(this.authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint getAuthenticationEntryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/loginPage.action");
    }

    @Bean
    public UsernamePasswordAuthenticationFilter getAuthenticationFilter() throws Exception {
        ValidatorCodeUsernamePasswordAuthenticationFilter filter = new ValidatorCodeUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationSuccessHandler(this.successHandler);
        filter.setAuthenticationFailureHandler(this.failureHandler);
        filter.setFilterProcessesUrl("/mldn-logout");
        filter.setUsernameParameter("mid");
        filter.setPasswordParameter("pwd");
        return filter;
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler getSuccessHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler = new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/welcomePage.action");
        return handler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler getFailureHandler() {
        SimpleUrlAuthenticationFailureHandler handler = new SimpleUrlAuthenticationFailureHandler();
        handler.setDefaultFailureUrl("/loginPage.action?error=true");
        return handler;
    }

    @Bean
    public SessionInformationExpiredStrategy getSessionInformationExpiredStrategy() {
        return new SimpleRedirectSessionInformationExpiredStrategy("/logoffPage.action");
    }

    @Bean
    public JdbcTokenRepositoryImpl getJdbcTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(this.dataSource);
        return tokenRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }



    //    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable();
//        http.authorizeRequests().antMatchers("/pages/message/**").access("hasRole('ADMIN')")
//                .antMatchers("/welcomePage.action").authenticated()
//                .antMatchers("/**").permitAll();
//        http.formLogin()
//                .usernameParameter("mid")
//                .passwordParameter("pwd")
//                .successForwardUrl("/welcomePage.action")
//                .loginPage("loginPage.action")
//                .loginProcessingUrl("/mldn-login")
//                .failureForwardUrl("/loginPage.action?error=true")
//                .and()
//                .logout()
//                .logoutUrl("/mldn-logout")
//                .logoutSuccessUrl("/logoutPage.action")
//                .deleteCookies("JESSIONID")
//                .and()
//                .exceptionHandling()
//                .accessDeniedPage("/error_403.action");
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin")
//                .password("{bcrypt}$2a$10$2y7higVhnHCn2L8//r/EVed2zi/LrQ.Y.svV.oeLqUM8xfUx5JWQC")
//                .roles("USER", "ADMIN");
//        auth.inMemoryAuthentication()
//                .withUser("mldn")
//                .password("{bcrypt}$2a$10$vjXs780rO3rF8ZAXuBL4..c9icL4JDvr3sweCIU9y/QWiYlHgbKGa")
//                .roles("SUER");
//    }

}
