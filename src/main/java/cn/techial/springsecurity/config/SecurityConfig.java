package cn.techial.springsecurity.config;

import cn.techial.springsecurity.wechat.WeChatAuthorizationConfig;
import cn.techial.springsecurity.wechat.WeChatOAuth2ClientContextFilter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * @author techial
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    private final WeChatAuthorizationConfig weChatAuthorizationConfig;
    private final WeChatOAuth2ClientContextFilter weChatOAuth2ClientContextFilter;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService, WeChatAuthorizationConfig weChatAuthorizationConfig, WeChatOAuth2ClientContextFilter weChatOAuth2ClientContextFilter) {
        this.userDetailsService = userDetailsService;
        this.weChatAuthorizationConfig = weChatAuthorizationConfig;
        this.weChatOAuth2ClientContextFilter = weChatOAuth2ClientContextFilter;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * WeChat login
         */
        http.addFilterBefore(weChatAuthorizationConfig.weChatFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(weChatOAuth2ClientContextFilter, OAuth2ClientAuthenticationProcessingFilter.class);

        http.formLogin().loginPage("/login").loginProcessingUrl("/api/user/login").permitAll();
        http.authorizeRequests().antMatchers("/api/home**/**").permitAll()
            .mvcMatchers("/api/user/**", "/api/index**/**").authenticated();

        http.exceptionHandling().defaultAuthenticationEntryPointFor(
            new HttpStatusEntryPoint(HttpStatus.NOT_FOUND), new AntPathRequestMatcher("/**"));

        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
    }

}

