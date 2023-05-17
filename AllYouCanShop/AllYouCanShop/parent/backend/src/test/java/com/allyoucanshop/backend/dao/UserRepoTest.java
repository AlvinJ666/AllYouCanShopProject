package com.allyoucanshop.backend.dao;

import com.allyoucanshop.common.persistence.model.Role;
import com.allyoucanshop.common.persistence.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
@Slf4j
public class UserRepoTest {
    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    @Autowired
    public UserRepoTest(UserRepository repository, RoleRepository roleRepository) {
        this.userRepository = repository;
        this.roleRepository = roleRepository;
    }

    @Test
    public void testCreateUsers() {
        Map<String, Role> roles = getRoles();
        User userAlvin = new User("alvin@test.com", "alvin2023", "Alvin", "Jiang", true);
        userAlvin.addRole(roles.get("Admin"));

        User userEditor = new User("editor@test.com", "editor2023", "Editor", "Ed", true);
        userEditor.addRole(roles.get("Editor"));

        User userAssistant = new User("assistant@test.com", "assistant2023", "Assistant", "As", false);
        userAssistant.addRole(roles.get("Assistant"));
        userAssistant.addRole(roles.get("Editor"));

        User userShipper = new User("shipper@test.com", "shipper2023", "Shipper", "Sp", true);
        userShipper.addRole(roles.get("Shipper"));

        User userSalesPerson = new User("salesPerson@test.com", "salesPerson2023", "SalesPerson", "Sps", false);
        userSalesPerson.addRole(roles.get("SalesPerson"));

        User userFishman = new User("fishman@test.com", "fishman2023", "Fishman", "Fm", false);
        userFishman.addRole(roles.get("Admin"));

        List<User> savedUser = userRepository.saveAll(List.of(userAlvin, userEditor, userAssistant, userShipper, userSalesPerson, userFishman));
        savedUser.forEach(user -> assertTrue(user.getId() > 0L));
    }

    private Map<String, Role> getRoles() {
        List<Role> roles = roleRepository.findAll();
        return roles.stream().collect(Collectors.toMap(Role::getName, Function.identity()));
    }

    @Test
    public void testModifyUsers() {
        List<User> users = userRepository.findAll();
        log.info("Before modification: " + users);
        int indexToDel = -1;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if ("Fishman".equals(user.getFirstName())) {
                userRepository.delete(user);
                indexToDel = i;
                continue;
            }
            if (!user.isEnabled()) {
                user.setEnabled(true);
            }
        }
        users.remove(indexToDel);
        userRepository.saveAll(users);
        log.info("After modification: " + userRepository.findAll());
    }

    @Test
    public void testGetUserByEmail() {
        String email = "abc@abc.com";
        User user0 = userRepository.findUserByEmail(email);
        assertNull(user0);

        email = "shipper@test.com";
        User user1 = userRepository.findUserByEmail(email);
        assertNotNull(user1);
    }
}
