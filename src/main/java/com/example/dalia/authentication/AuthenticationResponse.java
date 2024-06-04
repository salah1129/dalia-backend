package com.example.dalia.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data  @NoArgsConstructor

public class AuthenticationResponse {
    private String fullName;
    private String email;
    private String token;

    public AuthenticationResponse(String fullName, String username, String token) {
        this.fullName = fullName;
        this.email = username;
        this.token = token;
    }

}