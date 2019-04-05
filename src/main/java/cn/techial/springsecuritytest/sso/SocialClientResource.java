package cn.techial.springsecuritytest.sso;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 第三方登录授权认证
 *
 * @author techial
 */
@Configuration
public class SocialClientResource {

    /**
     * wechat client config
     *
     * @return clientResource
     */
    @Bean
    @ConfigurationProperties("wechat")
    public ClientResources weChat() {
        return new ClientResources();
    }

    /**
     * other client config
     * ...
     */

}
