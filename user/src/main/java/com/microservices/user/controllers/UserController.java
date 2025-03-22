package com.microservices.user.controllers;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user.dtos.UserDTO;
import com.microservices.user.models.User;
import com.microservices.user.services.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<User> saveUser(
        @RequestBody @Valid UserDTO userDTO
    ) {
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);

        return ResponseEntity.created(null).body(userService.save(user));
    }

}
