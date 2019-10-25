package cn.techial.springsecurity.wechat;

import cn.techial.springsecurity.domain.UserWeChat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

/**
 * @author techial
 */
@Configuration
@Log4j2
public class WeChatUserPrincipalExtractor implements PrincipalExtractor {

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        UserWeChat weChat;
        try {
            String valueAsString = new ObjectMapper().writeValueAsString(map);
            weChat = new ObjectMapper().readValue(valueAsString, UserWeChat.class);
            if (log.isDebugEnabled()) {
                log.debug("convent success, wechat {}", weChat.toString());
            }
        } catch (IOException e) {
            log.error("convent error", e);
            return null;
        }

        log.debug(weChat);
        return weChat;
    }
}
