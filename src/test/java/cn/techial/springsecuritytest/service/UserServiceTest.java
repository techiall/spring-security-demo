package cn.techial.springsecuritytest.service;

import cn.techial.springsecuritytest.domain.Role;
import cn.techial.springsecuritytest.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void save() {
        Assert.assertEquals(
            userService.save(
                new User().setUsername("1").setPassword("1").setRoles(Collections.singleton(Role.USER))
            ).getUsername(),
            new User().setUsername("1").setPassword("1").setRoles(Collections.singleton(Role.USER)).getUsername()
        );
    }

}
