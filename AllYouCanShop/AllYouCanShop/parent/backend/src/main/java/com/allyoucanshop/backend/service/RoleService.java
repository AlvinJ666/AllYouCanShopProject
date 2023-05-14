package com.allyoucanshop.backend.service;

import com.allyoucanshop.backend.dao.RoleRepository;
import com.allyoucanshop.common.persistence.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getRoles() {
        return repository.findAll();
    }
}
