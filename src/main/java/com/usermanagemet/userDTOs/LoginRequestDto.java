package com.usermanagemet.userDTOs;

import lombok.Getter;

@Getter
public class LoginRequestDto {
	
	private String email;
    private String password;
}
