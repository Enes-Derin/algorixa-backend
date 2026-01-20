package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.AuthController;
import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.dto.auth.*;
import com.enesderin.softvra_backend.servis.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
@AllArgsConstructor
public class AuthControllerImpl extends RestBaseController implements AuthController {

    private AuthService authService;

    @PostMapping("/register")
    @Override
    public RootEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return ok(authService.register(registerRequest));
    }

    @PostMapping("/login")
    @Override
    public RootEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ok(authService.login(loginRequest));
    }

    @PostMapping("/refreshToken")
    @Override
    public RootEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ok(authService.refreshToken(refreshTokenRequest));
    }
}
