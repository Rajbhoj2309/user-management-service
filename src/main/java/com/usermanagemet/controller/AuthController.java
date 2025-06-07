package com.usermanagemet.controller;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usermanagemet.domain.Role;
import com.usermanagemet.domain.User;
import com.usermanagemet.enums.RoleEnum;
import com.usermanagemet.payload.response.MessageResponse;
import com.usermanagemet.repositories.RoleRepository;
import com.usermanagemet.repositories.UserRepository;
import com.usermanagemet.userDTOs.RegisterRequestDto;
import com.usermanagemet.userDTOs.AuthResponseDto;
import com.usermanagemet.utils.JwtUtilService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody RegisterRequestDto registerRequestDto) {

		if (userRepository.existsByEmail(registerRequestDto.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(registerRequestDto.getFirstName(),registerRequestDto.getLastName(), registerRequestDto.getEmail(),
				passwordEncoder.encode(registerRequestDto.getPassword()));

		Set<String> strRoles = registerRequestDto.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(RoleEnum.ROLE_CUSTOMER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(RoleEnum.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(RoleEnum.ROLE_SUPERADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(RoleEnum.ROLE_CUSTOMER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

//	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody RegisterRequestDto request) throws Throwable {
//		try {
//			authenticationManager.authenticate(
//					new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
//
//			User user = (User) userRepository.findByUsername(request.getUsername())
//					.orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//			String token = jwtService.generateToken(user);
//			return ResponseEntity.ok(new AuthResponseDto(token));
//
//		} catch (Exception ex) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
//		}
//	}

}
