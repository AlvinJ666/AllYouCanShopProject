package com.allyoucanshop.backend.service;

import com.allyoucanshop.backend.dao.UserRepository;
import com.allyoucanshop.common.persistence.model.User;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Getter
    private static boolean needToFetch;

    private static List<User> allUsers;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        needToFetch = true;
    }

    public List<User> fetchAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        encodeUserPwd(user);
        User savedUser = userRepository.save(user);
        needToFetch = true;
        return savedUser;
    }

    public void encodeUserPwd(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public boolean userExists(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public boolean allowEdit(Long id) {
        return findUserByIdWithoutLock(id) != null;
    }

    public User findUserByIdWithLock(long id) {
        return userRepository.findFirstById(id);
    }

    public User findUserByIdWithoutLock(long id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUserById(long id) {
        userRepository.deleteById(id);
        boolean succeed = userRepository.countAllById(id) == 0;
        needToFetch = succeed;
        return succeed;
    }

    public List<User> updateUsersInMemory() {
        if (needToFetch || CollectionUtils.isEmpty(allUsers)) {
            allUsers = fetchAll();
            needToFetch = false;
        }
        return allUsers;
    }

    public List<User> getAllUsers() {
        return updateUsersInMemory();
    }
}
