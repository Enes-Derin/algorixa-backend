package com.enesderin.softvra_backend.controller.impl;

import com.enesderin.softvra_backend.controller.RestBaseController;
import com.enesderin.softvra_backend.controller.RootEntity;
import com.enesderin.softvra_backend.controller.UserController;
import com.enesderin.softvra_backend.dto.request.UserRequest;
import com.enesderin.softvra_backend.dto.response.UserResponse;
import com.enesderin.softvra_backend.servis.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/admin")
@AllArgsConstructor
public class UserControllerImpl extends RestBaseController implements UserController {

    private UserService userService;

    @GetMapping("/{id}")
    @Override
    public RootEntity<UserResponse> getUser(@PathVariable Long id) {
        return ok(userService.getUser(id));
    }

    @PostMapping
    @Override
    public RootEntity<UserResponse> createUser(@Valid @RequestBody UserRequest userRequest) {
        return ok(userService.createUser(userRequest));
    }

    @PutMapping("/{id}")
    @Override
    public RootEntity<UserResponse> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest) {
        return ok(userService.updateUser(id, userRequest));
    }
}
