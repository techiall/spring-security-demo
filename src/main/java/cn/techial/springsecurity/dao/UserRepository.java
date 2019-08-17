package cn.techial.springsecurity.dao;

import cn.techial.springsecurity.domain.User;
import cn.techial.springsecurity.domain.UserWeChat;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author techial
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @EntityGraph(attributePaths = "roles")
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByWeChat(UserWeChat userWeChat);
}
