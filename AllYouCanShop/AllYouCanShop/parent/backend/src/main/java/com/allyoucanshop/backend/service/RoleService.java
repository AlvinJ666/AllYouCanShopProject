package com.allyoucanshop.backend.service;

import com.allyoucanshop.backend.dao.RoleRepository;
import com.allyoucanshop.common.persistence.model.Role;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class RoleService {
    private final RoleRepository repository;

    @Getter
    private static List<Role> rolesInDb;

    @Autowired
    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    private void fetchRoles() {
        rolesInDb = repository.findAll();
    }

    public List<Role> getRoles() {
        return CollectionUtils.isEmpty(rolesInDb) ? repository.findAll() : rolesInDb;
    }


}
