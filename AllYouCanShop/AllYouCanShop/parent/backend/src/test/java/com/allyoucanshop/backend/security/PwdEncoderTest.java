package com.allyoucanshop.backend.security;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class PwdEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawpwd = "Alvin2023";
        String encodedPwd = passwordEncoder.encode(rawpwd);
        log.info(encodedPwd);
        assertTrue(passwordEncoder.matches(rawpwd, encodedPwd));
    }
}
