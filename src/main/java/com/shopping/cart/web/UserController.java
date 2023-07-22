package com.shopping.cart.web;

import com.shopping.cart.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this contains the rest endpoints related to user onboarding
 */

@RestController
@RequestMapping("/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void create() {
        userService.create();
    }

    @PutMapping(value = "/update")
    public void update() {
        userService.update();
    }

    @PostMapping(value = "/login")
    public void login() {
        userService.login();
    }

    @PostMapping(value = "/logout")
    public void logout() {
        userService.logout();
    }
}
