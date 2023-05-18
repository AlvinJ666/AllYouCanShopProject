package com.allyoucanshop.backend.controller.rest;

import com.allyoucanshop.backend.enums.ActionCodes;
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
    public int validateUpdate(@Param("id") Long id, @Param("email") String email) {
        if (id != null) {
            //if id is not null, check if update is possible
            return ActionCodes.getActionCode(userService.allowEdit(id));
        } else {
            //if exists, creating is not allowed
            return ActionCodes.getActionCode(!userService.userExists(email));
        }
    }
}
