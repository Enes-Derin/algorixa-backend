package com.enesderin.softvra_backend.controller;

import com.enesderin.softvra_backend.dto.request.UserRequest;
import com.enesderin.softvra_backend.dto.response.UserResponse;

public interface UserController {

    RootEntity<UserResponse> getUser(Long id);
    RootEntity<UserResponse> createUser(UserRequest userRequest);
    RootEntity<UserResponse> updateUser(Long id,UserRequest userRequest);

}
