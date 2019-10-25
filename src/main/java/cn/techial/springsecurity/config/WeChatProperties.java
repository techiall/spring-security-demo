package cn.techial.springsecurity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author techial
 */
@ConfigurationProperties("wechat.config")
@Component
@Data
public class WeChatProperties {

    private String appId = "appid";

    private String clientId = "client_id";

    private String secret = "secret";

    private String clientSecret = "client_secret";

    private String state = "state";

    private String wechatRedirect = "wechat_redirect";

}
