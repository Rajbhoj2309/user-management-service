package com.usermanagemet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagemet.domain.User;
import com.usermanagemet.repositories.UserRepository;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private UserRepository userRepository;
	@PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
		userRepository.save(user);
        return ResponseEntity.ok("User registered");
    }

}
