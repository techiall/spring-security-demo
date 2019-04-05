package cn.techial.springsecuritytest.service;

import cn.techial.springsecuritytest.dao.UserRepository;
import cn.techial.springsecuritytest.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * @author techial
 */
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save(User user) {
        checkConstraint(user, user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    private void checkConstraint(User user, String username) {
        if ((user == null || !user.getUsername().equals(username)) && userRepository.existsByUsername(username)) {
            throw new UsernameNotFoundException(Objects.requireNonNull(user).getUsername());
        }
    }

}
