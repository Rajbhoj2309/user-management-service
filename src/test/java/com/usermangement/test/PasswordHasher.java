package com.usermangement.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHasher {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String rawPassword = "qwerty@12345";
        String hashedPassword = encoder.encode(rawPassword);

        System.out.println("Hashed Password: " + hashedPassword);

        // Verify password
        boolean isMatch = encoder.matches(rawPassword, hashedPassword);
        System.out.println("Password match: " + isMatch);
    }
}

