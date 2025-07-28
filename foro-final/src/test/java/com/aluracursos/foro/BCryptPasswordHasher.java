package com.aluracursos.foro;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptPasswordHasher {

    public static void main(String[] args) {
        String rawPassword = "123456";
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);
    }
}