package cn.techial.springsecurity.wechat;

import cn.techial.springsecurity.config.WeChatProperties;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

/**
 * 实现 RequestEnhancer 接口
 * client_id -> appid
 * client_secret -> secret
 *
 * @author techial
 */
@Component
@Log4j2
public class WeChatRequestEnhancer implements RequestEnhancer {
    private final WeChatProperties weChatProperties;

    public WeChatRequestEnhancer(WeChatProperties weChatProperties) {
        this.weChatProperties = weChatProperties;
    }

    @Override
    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) {
        form.put(weChatProperties.getAppId(), form.remove(weChatProperties.getClientId()));
        form.put(weChatProperties.getSecret(), form.remove(weChatProperties.getClientSecret()));
        if (log.isDebugEnabled()) {
            log.debug("form = {}", form.toSingleValueMap());
        }
    }
}
