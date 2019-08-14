package cn.techial.springsecurity.dao;

import cn.techial.springsecurity.domain.User;
import cn.techial.springsecurity.domain.UserWeChat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author techial
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "roles")
    User findByUsername(String username);

    boolean existsByUsername(String username);

    User findByWeChat(UserWeChat userWeChat);
}
