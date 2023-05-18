package com.allyoucanshop.backend.monitor;

import com.allyoucanshop.backend.service.RoleService;
import com.allyoucanshop.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "db.monitor.enabled", matchIfMissing = true, havingValue = "true")
public class DbMonitor {
    private final RoleService roleService;

    private final UserService userService;

    @Autowired
    public DbMonitor(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @Scheduled(cron = "0 0 */1 * * *")
    public void fetchRoles() {
        roleService.fetchRoles();
    }

    @Scheduled(fixedRate = 60_000)
    public void fetchUsers() {
        userService.updateUsersInMemory();
    }
}
