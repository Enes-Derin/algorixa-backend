package com.enesderin.softvra_backend.servis.impl;

import com.enesderin.softvra_backend.dto.request.UserRequest;
import com.enesderin.softvra_backend.dto.response.UserResponse;
import com.enesderin.softvra_backend.model.Role;
import com.enesderin.softvra_backend.model.User;
import com.enesderin.softvra_backend.repo.UserRepository;
import com.enesderin.softvra_backend.servis.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public UserResponse getUser(Long id) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(optionalUser.get().getUsername());
            userResponse.setPassword(optionalUser.get().getPassword());
            return userResponse;
        }
        return null;
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        user.setUsername(userRequest.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
        user.setRole(Role.ADMIN);
        this.userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setUsername(userRequest.getUsername());
        userResponse.setPassword(userRequest.getPassword());
        userResponse.setRole(Role.ADMIN);
        return userResponse;
    }



    @Override
    public UserResponse updateUser(Long id, UserRequest userRequest) {
        Optional<User> optionalUser = this.userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUsername(userRequest.getUsername());
            user.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            user.setRole(Role.ADMIN);
            this.userRepository.save(user);
            UserResponse userResponse = new UserResponse();
            userResponse.setUsername(userRequest.getUsername());
            userResponse.setPassword(userRequest.getPassword());
            userResponse.setRole(Role.ADMIN);
            return userResponse;
        }
        return null;
    }
}
