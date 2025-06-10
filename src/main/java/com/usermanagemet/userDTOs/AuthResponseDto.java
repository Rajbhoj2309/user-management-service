package com.usermanagemet.userDTOs;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AuthResponseDto {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> roles;
    
    public AuthResponseDto(String generateNewToken, Long id, String firstName, String lastName, String email) {
		this.token = generateNewToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
	}

    public AuthResponseDto(String accessToken, Long id, String firstName,String lastName, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
      }

	
}
