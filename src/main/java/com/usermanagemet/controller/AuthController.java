package com.usermanagemet.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.usermanagemet.security.services.UserDetailsImpl;
import com.usermanagemet.userDTOs.RegisterRequestDto;
import com.usermanagemet.userDTOs.LoginRequestDto;
import com.usermanagemet.userDTOs.AuthResponseDto;
import com.usermanagemet.utils.JwtUtilService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtUtilService jwtUtilService;

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
		
		if (strRoles == null || strRoles.isEmpty()) {
			Role userRole = roleRepository.findByName("ROLE_CUSTOMER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto  ) throws Throwable {
		try {
			Authentication authenticate = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequestDto.getEmail(), loginRequestDto.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authenticate);
			String jwtToken = jwtUtilService.generateToken(authenticate);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authenticate.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream()
			        .map(item -> item.getAuthority())
			        .collect(Collectors.toList());
			return ResponseEntity.ok(new AuthResponseDto(jwtToken,
					userDetails.getId()
					,userDetails.getFirstName()
					,userDetails.getLastName()
					,userDetails.getEmail(),
					roles)
					);

		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + ex.getMessage());
		}
	}
}
