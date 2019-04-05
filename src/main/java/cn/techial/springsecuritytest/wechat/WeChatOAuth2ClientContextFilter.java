package cn.techial.springsecuritytest.wechat;

import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.UserRedirectRequiredException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 扩展 OAuth2ClientContextFilter
 * 重写请求的字段
 * <p>
 * client_id -> appid，添加 #wechat_redirect
 *
 * @author techial
 */
@Component
public class WeChatOAuth2ClientContextFilter extends OAuth2ClientContextFilter {

    private RedirectStrategy redirectStrategy;

    public WeChatOAuth2ClientContextFilter() {
        redirectStrategy = new DefaultRedirectStrategy();
    }

    @Override
    protected void redirectUser(UserRedirectRequiredException e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(e.getRedirectUri());

        e.getRequestParams().put(WeChatAuthorizationConfig.APP_ID, e.getRequestParams().remove(WeChatAuthorizationConfig.CLIENT_ID));

        Map<String, List<String>> map = e.getRequestParams().entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, it -> Collections.singletonList(it.getValue())));
        map.put(WeChatAuthorizationConfig.STATE, Collections.singletonList(e.getStateKey()));

        builder.queryParams(new LinkedMultiValueMap<>(map));

        builder.fragment(WeChatAuthorizationConfig.WE_CHAT_REDIRECT);
        this.redirectStrategy.sendRedirect(request, response, builder.build().encode().toUriString());
    }

    @Override
    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

}

