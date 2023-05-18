package com.allyoucanshop.backend.service;

import com.allyoucanshop.backend.dao.RoleRepository;
import com.allyoucanshop.common.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;

    private static List<Role> rolesInDb;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public void fetchRoles() {
        rolesInDb = repository.findAll();
    }

    public List<Role> getRoles() {
        if (CollectionUtils.isEmpty(rolesInDb)) {
            fetchRoles();
        }
        return rolesInDb;
    }
}
