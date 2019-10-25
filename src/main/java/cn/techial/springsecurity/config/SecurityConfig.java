package cn.techial.springsecurity.config;

import cn.techial.springsecurity.wechat.WeChatAuthorizationConfig;
import cn.techial.springsecurity.wechat.WeChatOAuth2ClientContextFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * @author techial
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final WeChatAuthorizationConfig weChatAuthorizationConfig;
    private final WeChatOAuth2ClientContextFilter weChatOAuth2ClientContextFilter;
    private final SecurityProperties securityProperties;

    public SecurityConfig(WeChatAuthorizationConfig weChatAuthorizationConfig, WeChatOAuth2ClientContextFilter weChatOAuth2ClientContextFilter, SecurityProperties securityProperties) {
        this.weChatAuthorizationConfig = weChatAuthorizationConfig;
        this.weChatOAuth2ClientContextFilter = weChatOAuth2ClientContextFilter;
        this.securityProperties = securityProperties;
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*
         * WeChat login
         */
        http.addFilterBefore(weChatAuthorizationConfig.weChatFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(weChatOAuth2ClientContextFilter, OAuth2ClientAuthenticationProcessingFilter.class);

        http
            .formLogin()
            .loginPage(securityProperties.getLoginPage())
            .loginProcessingUrl(securityProperties.getLoginProcessingUrl())
            .permitAll()

            .and()

            .authorizeRequests()
            .antMatchers(securityProperties.getAntMatchers())
            .permitAll()
            .mvcMatchers(securityProperties.getMvcMatchers())
            .authenticated();


        http.csrf().disable();
    }

}

