package com.allyoucanshop.backend.dao;

import com.allyoucanshop.common.persistence.model.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepoTest {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleRepoTest(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Test
    public void testCreateFirstRole() {
        Role admin = Role.builder().name("Admin").description("Can manage everything").build();
        Role savedRole = roleRepository.save(admin);
        assertTrue(savedRole.getId() > 0);
    }

    @Test
    public void testCreateRoles() {
        List<Role> roles = List.of(
                generateRole("SalesPerson", "Allow manage product price, customers, shipping, orders and sales reports"),
                generateRole("Editor", "Allow manage categories, brands, products, articles and menus"),
                generateRole("Shipper", "View products, orders and update order status"),
                generateRole("Assistant", "Manage customer questions and reviews"));
        roleRepository.saveAll(roles);
    }

    private Role generateRole(String name, String description) {
        return Role.builder().name(name).description(description).build();
    }
}
