package com.usermanagemet.userDTOs;

import java.util.Set;

import lombok.Getter;

@Getter
public class RegisterRequestDto {
	private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<String> role;
}

