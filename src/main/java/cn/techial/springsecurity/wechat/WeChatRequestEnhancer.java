package cn.techial.springsecurity.wechat;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class WeChatRequestEnhancer implements RequestEnhancer {

    @Override
    public void enhance(AccessTokenRequest request, OAuth2ProtectedResourceDetails resource, MultiValueMap<String, String> form, HttpHeaders headers) {
        form.put(WeChatAuthorizationConfig.APP_ID, form.remove(WeChatAuthorizationConfig.CLIENT_ID));
        form.put(WeChatAuthorizationConfig.SECRET, form.remove(WeChatAuthorizationConfig.CLIENT_SECRET));
        log.info("form = {}", form.toSingleValueMap());
    }
}
