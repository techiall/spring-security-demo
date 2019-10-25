package cn.techial.springsecurity.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author techial
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/info")
    public Object userInfo(@AuthenticationPrincipal Object object) {
        return object;
    }

}
