package cn.techial.springsecurity.wechat;

import cn.techial.springsecurity.dao.UserRepository;
import cn.techial.springsecurity.dao.UserWeChatRepository;
import cn.techial.springsecurity.domain.User;
import cn.techial.springsecurity.domain.UserWeChat;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

/**
 * @author techial
 */
@Configuration
@Slf4j
public class WeChatUserPrincipalExtractor implements PrincipalExtractor {
    private final UserRepository userRepository;
    private final UserWeChatRepository userWeChatRepository;

    public WeChatUserPrincipalExtractor(UserRepository userRepository, UserWeChatRepository userWeChatRepository) {
        this.userRepository = userRepository;
        this.userWeChatRepository = userWeChatRepository;
    }

    @Override
    public Object extractPrincipal(Map<String, Object> map) {
        UserWeChat weChat;
        try {
            String valueAsString = new ObjectMapper().writeValueAsString(map);
            weChat = new ObjectMapper().readValue(valueAsString, UserWeChat.class);
            log.debug("convent success, wechat {}", weChat.toString());
        } catch (IOException e) {
            log.error("convent error", e);
            return null;
        }

        UserWeChat userWeChat = userWeChatRepository.findFirstByOpenId(weChat.getOpenId()).orElse(null);
        userWeChat = userWeChatRepository.save(userWeChat == null ? weChat : weChat.setId(userWeChat.getId()));

        User user = userRepository.findByWeChat(userWeChat).orElse(null);
        if (user != null) {
            return user.toUserPrincipal();
        }

        user = new User().setUsername(weChat.getNickName()).setPassword("").setWeChat(weChat);
        return userRepository.save(user).toUserPrincipal();
    }
}
