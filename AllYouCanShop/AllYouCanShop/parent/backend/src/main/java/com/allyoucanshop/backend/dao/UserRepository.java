package com.allyoucanshop.backend.dao;

import com.allyoucanshop.common.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    User findFirstById(long id);
}
