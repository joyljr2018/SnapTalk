package com.rjl.service;

import com.rjl.mapper.UsersMapper;
import com.rjl.pojo.Users;
import org.apache.catalina.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @Test
    public void queryUsernameIsExist() {

    }

    @Test
    public void queryUserForLogin() {

    }

    @Test
    public void saveUser() {
        Users user = new Users();
        user.setId("123");
        user.setPassword("123");
        user.setUsername("123");
        Users res = userService.saveUser(user);
        Assert.assertTrue(res.getUsername().equals("123"));

    }
}