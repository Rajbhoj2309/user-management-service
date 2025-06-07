package com.usermanagemet.userDTOs;

import lombok.Getter;

@Getter
public class AuthResponseDto {
    private String token;

    public AuthResponseDto(String token) {
        this.token = token;
    }

    // Getter
}
