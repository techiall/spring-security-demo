package cn.techial.springsecurity.controller;

import cn.techial.springsecurity.domain.User;
import cn.techial.springsecurity.domain.UserPrincipal;
import cn.techial.springsecurity.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author techial
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public User userInfo(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return userService.findById(userPrincipal.getId()).orElse(null);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public User save(String username, String password) {
        return userService.save(new User().setUsername(username).setPassword(password));
    }


}
