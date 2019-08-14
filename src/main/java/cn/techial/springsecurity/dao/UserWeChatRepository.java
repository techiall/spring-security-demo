package cn.techial.springsecurity.dao;

import cn.techial.springsecurity.domain.UserWeChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author techial
 */
@Repository
public interface UserWeChatRepository extends JpaRepository<UserWeChat, Integer> {
    Optional<UserWeChat> findFirstByOpenId(String openId);
}
