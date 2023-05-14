package com.allyoucanshop.backend.controller;

import com.allyoucanshop.backend.service.RoleService;
import com.allyoucanshop.backend.service.UserService;
import com.allyoucanshop.common.persistence.model.Role;
import com.allyoucanshop.common.persistence.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path = "/users")
    public String displayAllUsers(Model model) {
        List<User> users = userService.listAll();
        model.addAttribute("listUsers", users);
        return "users";
    }

    @GetMapping(path = "/users/new")
    public String newUser(Model model) {
        User user = new User();
        List<Role> listRoles = roleService.getRoles();
        model.addAttribute("user", user);
        model.addAttribute("listRoles", listRoles);
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user) {
        log.info(user.toString());
        return "redirect:/users";
    }
}
