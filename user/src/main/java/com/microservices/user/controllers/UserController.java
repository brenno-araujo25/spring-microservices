package com.microservices.user.controllers;

import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservices.user.dtos.UserDTO;
import com.microservices.user.models.User;
import com.microservices.user.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> saveUser(
        @RequestBody @Valid UserDTO userDTO
    ) {
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);

        return ResponseEntity.created(null).body(userService.save(user));
    }

    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/email")
    public ResponseEntity<User> findByEmail(
        @RequestBody @Valid String email
    ) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @GetMapping("/id")
    public ResponseEntity<User> findById(
        @RequestBody @Valid UUID id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(
        @RequestBody @Valid String email
    ) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

}
