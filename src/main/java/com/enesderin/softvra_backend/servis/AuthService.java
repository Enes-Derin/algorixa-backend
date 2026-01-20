package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.auth.*;

public interface AuthService {
    RegisterResponse register(RegisterRequest registerRequest);
    LoginResponse login(LoginRequest loginRequest);
    RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest);
}
