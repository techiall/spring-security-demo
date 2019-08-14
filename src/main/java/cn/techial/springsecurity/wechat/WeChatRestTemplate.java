package cn.techial.springsecurity.wechat;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

/**
 * {@link org.springframework.security.oauth2.client.OAuth2RestTemplate#appendQueryParameter}
 * 参考 appendQueryParameter，重写某些字段
 *
 * @author techial
 */
public class WeChatRestTemplate extends OAuth2RestTemplate {

    public WeChatRestTemplate(OAuth2ProtectedResourceDetails resource, OAuth2ClientContext context) {
        super(resource, context);
    }

    @Override
    protected URI appendQueryParameter(URI uri, OAuth2AccessToken accessToken) {
        try {
            String query = uri.getRawQuery();
            String tokenQueryFragment = this.getResource().getTokenName() + "=" + URLEncoder.encode(accessToken.getValue(), "UTF-8");
            if (query == null) {
                query = tokenQueryFragment;
            } else {
                query = query + "&" + tokenQueryFragment;
            }

            String openid = (String) accessToken.getAdditionalInformation().get("openid");
            if (openid != null) {
                String openIdQueryFragment = "openid=" + URLEncoder.encode(openid, "UTF-8");
                query = query + "&" + openIdQueryFragment;
            }

            URI update = new URI(uri.getScheme(), uri.getUserInfo(), uri.getHost(), uri.getPort(),
                uri.getPath(), null, null);

            StringBuilder result = new StringBuilder(update.toString());
            result.append("?");
            result.append(query);
            if (uri.getFragment() != null) {
                result.append("#");
                result.append(uri.getFragment());
            }

            return new URI(result.toString());

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Could not parse URI", e);
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("Could not encode URI", e);
        }
    }

    @Override
    public void setAccessTokenProvider(AccessTokenProvider accessTokenProvider) {
        super.setAccessTokenProvider(accessTokenProvider);
    }

    @Override
    public void setMessageConverters(List<HttpMessageConverter<?>> messageConverters) {
        super.setMessageConverters(messageConverters);
    }

}
