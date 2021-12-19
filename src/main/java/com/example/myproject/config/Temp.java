package com.example.myproject.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Temp implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;

    public Temp(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
     // System.out.println(passwordEncoder.encode("parola"));
    }
}
