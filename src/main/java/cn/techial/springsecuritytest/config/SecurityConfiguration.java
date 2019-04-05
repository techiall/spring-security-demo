package cn.techial.springsecuritytest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
            authorizeRequests()
            .antMatchers("/message/**").permitAll()
            .anyRequest().authenticated()

            .and()

            .formLogin()
            .loginPage("/login")
            .loginProcessingUrl("/user/login")
            .permitAll()
            .failureHandler((httpServletRequest, httpServletResponse, e) -> httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value()))

            .and()

            .logout()
            .logoutUrl("/user/logout")
            .logoutSuccessHandler((httpServletRequest, httpServletResponse, authentication) -> httpServletResponse.setStatus(HttpStatus.OK.value()))
            .permitAll()

            .and()

            .csrf().
            disable();
    }

}

