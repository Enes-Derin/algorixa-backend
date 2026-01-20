package com.enesderin.softvra_backend.dto.auth;

import com.enesderin.softvra_backend.model.Role;
import lombok.Data;

@Data
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private Role role;
}
