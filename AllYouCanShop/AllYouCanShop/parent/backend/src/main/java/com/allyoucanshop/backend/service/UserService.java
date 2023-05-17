package com.allyoucanshop.backend.service;

import com.allyoucanshop.backend.dao.UserRepository;
import com.allyoucanshop.common.persistence.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        encodeUserPwd(user);
        return userRepository.save(user);
    }

    public void encodeUserPwd(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    public boolean userExists(String email) {
        return userRepository.findUserByEmail(email) != null;
    }

    public User findUserByIdWithLock(long id) {
        return userRepository.findFirstById(id);
    }

    public User findUserByIdWithoutLock(long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void editUser(User user) {

    }
}
