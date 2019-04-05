package cn.techial.springsecuritytest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author techial
 */
@RestController
@RequestMapping("/api/home")
public class HomeController {

    @GetMapping
    public String homeGet() {
        return "get index";
    }

    @PostMapping
    public String homePost() {
        return "post index";
    }

}
