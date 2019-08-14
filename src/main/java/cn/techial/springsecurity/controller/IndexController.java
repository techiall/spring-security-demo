package cn.techial.springsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author techial
 */
@RestController
@RequestMapping("/api/index")
public class IndexController {

    @PostMapping
    public String index() {
        return "post index";
    }

    @GetMapping
    public String indexGet() {
        return "get index";
    }

}
