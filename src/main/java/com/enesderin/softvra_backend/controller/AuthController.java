package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.auth.*;

public interface AuthController {
    RootEntity<RegisterResponse> register(RegisterRequest registerRequest);
    RootEntity<LoginResponse> login(LoginRequest loginRequest);
    RootEntity<RefreshTokenResponse> refreshToken(RefreshTokenRequest refreshTokenRequest);
}
