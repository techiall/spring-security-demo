package cn.techial.springsecurity.service;

import cn.techial.springsecurity.dao.UserRepository;
import cn.techial.springsecurity.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author techial
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("user %s not found.", s));
        }
        return user.toUserPrincipal();
    }
}
