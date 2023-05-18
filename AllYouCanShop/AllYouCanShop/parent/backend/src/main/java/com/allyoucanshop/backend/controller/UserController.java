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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Controller
@Slf4j
public class UserController {
    private final UserService userService;

    private final RoleService roleService;

    private final static String PAGE_TITLE = "pageTitle";
    private final static String MSG = "message";

    private final static String ERR_MSG = "errMessage";

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping(path = "/users")
    public String displayAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("listUsers", users);
        return "users";
    }

    @GetMapping(path = "/users/new")
    public String newUser(Model model) {
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        setRolesToModel(model);
        setPageTitleToModel(model, "Create New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String updateUser(User user, RedirectAttributes redirectAttributes) {
        if (user != null) {
            String verb = "saved";
            Long id = user.getId();
            if (id != null) {
                User userInDb = userService.findUserByIdWithoutLock(id);
                if (StringUtils.isEmpty(user.getPassword())) {
                    user.setPassword(userInDb.getPassword());
                }
                if (user.sameUser(userInDb)) {
                    setFlashErrMessageToRedirectAttributes(redirectAttributes, String.format("Nothing to update, user of ID %s remains the same", id));
                    return "redirect:/users";
                }
                verb = "updated";
            }
            User savedUser = userService.save(user);
            if (savedUser != null)
                setFlashMessageToRedirectAttributes(redirectAttributes, String.format("The user of ID %s has been %s successfully!", savedUser.getId(), verb));
        }
        return "redirect:/users";
    }


    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable("id") long id, Model model, RedirectAttributes redirectAttributes) {
        User user = userService.findUserByIdWithoutLock(id);
        if (user != null) {
            model.addAttribute("user", user);
            setRolesToModel(model);
            setPageTitleToModel(model, String.format("Edit User (ID: %s)", id));
            return "user_form";
        }
        setFlashErrMessageToRedirectAttributes(redirectAttributes, String.format("User with ID: %s not exists", id));
        return "redirect:/users";
    }

    @GetMapping("/users/delete/{id}")
    public String removeUser(@PathVariable("id") long id, RedirectAttributes redirectAttributes) {
        boolean succeed = userService.deleteUserById(id);
        if (succeed) {
            setFlashMessageToRedirectAttributes(redirectAttributes, String.format("User with ID: %s successfully removed", id));
        } else {
            setFlashErrMessageToRedirectAttributes(redirectAttributes, String.format("Failed to remove user with ID %s", id));
        }
        return "redirect:/users";
    }


    private void setRolesToModel(Model model) {
        List<Role> listRoles = roleService.getRoles();
        model.addAttribute("listRoles", listRoles);
    }

    private void setPageTitleToModel(Model model, String title) {
        model.addAttribute(PAGE_TITLE, title);
    }

    private void setFlashMessageToRedirectAttributes(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute(MSG, message);
    }

    private void setFlashErrMessageToRedirectAttributes(RedirectAttributes redirectAttributes, String message) {
        redirectAttributes.addFlashAttribute(ERR_MSG, message);
    }
}
