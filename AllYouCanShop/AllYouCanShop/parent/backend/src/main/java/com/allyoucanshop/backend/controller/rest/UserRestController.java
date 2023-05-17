package com.allyoucanshop.backend.controller.rest;

import com.allyoucanshop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {
    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/users/check_email")
    public int checkDuplicateEmail(@Param("email") String email) {
        return userService.userExists(email) ? 200 : 400;
    }
}
