package com.enesderin.softvra_backend.servis;

import com.enesderin.softvra_backend.dto.request.UserRequest;
import com.enesderin.softvra_backend.dto.response.UserResponse;

public interface UserService {

    UserResponse getUser(Long id);
    UserResponse createUser(UserRequest userRequest);
    UserResponse updateUser(Long id,UserRequest userRequest);

}
