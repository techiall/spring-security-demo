package cn.techial.springsecurity.wechat;

import cn.techial.springsecurity.dao.UserRepository;
import cn.techial.springsecurity.dao.UserWeChatRepository;
import cn.techial.springsecurity.domain.User;
import cn.techial.springsecurity.domain.UserWeChat;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Configuration;

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
        log.debug(map.toString());
        String str = JSON.toJSONString(map);
        UserWeChat weChat = JSON.parseObject(str, UserWeChat.class);
        if (weChat == null) {
            return null;
        }

        UserWeChat userWeChat = userWeChatRepository.findFirstByOpenId(weChat.getOpenId()).orElse(null);
        if (userWeChat == null) {
            User user = new User().setUsername(weChat.getNickName()).setPassword("")
                .setWeChat(userWeChatRepository.save(weChat));
            return userRepository.save(user).toUserPrincipal();
        }
        User user = userRepository.findByWeChat(userWeChat);
        userRepository.save(user.setWeChat(weChat));
        return user.toUserPrincipal();
    }
}
