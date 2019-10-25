package cn.techial.springsecurity;

import cn.techial.springsecurity.config.SecurityProperties;
import cn.techial.springsecurity.config.WeChatProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author techial
 */
@SpringBootApplication
@EnableConfigurationProperties(value = {
    WeChatProperties.class,
    SecurityProperties.class
})
public class SpringSecurityTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityTestApplication.class, args);
    }

}
