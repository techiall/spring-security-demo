package cn.techial.springsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author techial
 */
@ConfigurationProperties("security")
@Component
@Data
public class SecurityProperties {
    private String loginPage = "/login";

    private String loginProcessingUrl = "/api/user/login";

    private String[] antMatchers = {"/api/home**/**"};

    private String[] mvcMatchers = {"/api/user/**", "/api/index**/**"};

}
