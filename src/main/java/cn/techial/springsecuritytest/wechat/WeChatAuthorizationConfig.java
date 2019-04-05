package cn.techial.springsecuritytest.wechat;

import cn.techial.springsecuritytest.sso.SocialClientResource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.token.RequestEnhancer;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Collections;
import java.util.List;

/**
 * https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
 * 微信登录流程
 *
 * @author techial
 */
@Configuration
@EnableOAuth2Client
@Order(200)
public class WeChatAuthorizationConfig {

    public static final String APP_ID = "appid";
    public static final String CLIENT_ID = "client_id";
    public static final String SECRET = "secret";
    public static final String CLIENT_SECRET = "client_secret";
    public static final String STATE = "state";
    public static final String WE_CHAT_REDIRECT = "wechat_redirect";

    /**
     * 微信登录 url
     */
    private static final String WE_CHAT_LOGIN_URL = "/api/user/login/wechat/oauth2";
    private final OAuth2ClientContext context;
    private final RequestEnhancer requestEnhancer;
    private final PrincipalExtractor principalExtractor;
    private final SocialClientResource socialClientResource;


    public WeChatAuthorizationConfig(@Qualifier("oauth2ClientContext") OAuth2ClientContext context, @Qualifier("weChatRequestEnhancer") RequestEnhancer requestEnhancer, @Qualifier("weChatUserPrincipalExtractor") PrincipalExtractor principalExtractor, SocialClientResource socialClientResource) {
        this.context = context;
        this.requestEnhancer = requestEnhancer;
        this.principalExtractor = principalExtractor;
        this.socialClientResource = socialClientResource;
    }

    private static List<HttpMessageConverter<?>> convertMediaTypes() {
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
        return Collections.singletonList(messageConverter);
    }

    private AuthorizationCodeAccessTokenProvider accessTokenProvider() {
        AuthorizationCodeAccessTokenProvider provider = new AuthorizationCodeAccessTokenProvider();
        provider.setAuthorizationRequestEnhancer(requestEnhancer);
        provider.setTokenRequestEnhancer(requestEnhancer);
        provider.setMessageConverters(WeChatAuthorizationConfig.convertMediaTypes());
        return provider;
    }

    public WeChatRestTemplate restTemplate() {
        WeChatRestTemplate template = new WeChatRestTemplate(socialClientResource.weChat().getClient(), context);
        template.setAccessTokenProvider(accessTokenProvider());
        template.setMessageConverters(WeChatAuthorizationConfig.convertMediaTypes());
        return template;
    }

    public OAuth2ClientAuthenticationProcessingFilter weChatFilter() {
        OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(WE_CHAT_LOGIN_URL);
        WeChatRestTemplate template = restTemplate();
        filter.setRestTemplate(template);
        UserInfoTokenServices tokenServices = tokenServices(template);
        filter.setTokenServices(tokenServices);
        return filter;
    }

    private UserInfoTokenServices tokenServices(WeChatRestTemplate template) {
        UserInfoTokenServices tokenServices = new UserInfoTokenServices(
            socialClientResource.weChat().getResource().getUserInfoUri(), socialClientResource.weChat().getResource().getClientId());
        tokenServices.setRestTemplate(template);
        tokenServices.setPrincipalExtractor(principalExtractor);
        return tokenServices;
    }
}
