package com.microservices.user.controllers;

import java.util.Map;
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
import com.microservices.user.exceptions.UserAlreadyExistsException;
import com.microservices.user.exceptions.UserNotFoundException;
import com.microservices.user.models.User;
import com.microservices.user.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
@Tag(name = "User")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Create a new user")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "User created",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "409", description = "User already exists with email",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Map.class)))
    })
    @PostMapping
    public ResponseEntity<User> saveUser(
        @RequestBody @Valid UserDTO userDTO
    ) {
        var user = new User();
        BeanUtils.copyProperties(userDTO, user);

        return ResponseEntity.created(null).body(userService.save(user));
    }

    @Operation(summary = "Find all users")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Users found",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = User[].class)))
    })
    @GetMapping
    public ResponseEntity<Iterable<User>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @Operation(summary = "Find user by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found with email",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserNotFoundException.class)))
    })
    @GetMapping("/email")
    public ResponseEntity<User> findByEmail(
        @RequestBody @Valid String email
    ) {
        return ResponseEntity.ok(userService.findByEmail(email));
    }

    @Operation(summary = "Find user by id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User found",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = User.class))),
        @ApiResponse(responseCode = "404", description = "User not found with id",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = UserNotFoundException.class)))
    })
    @GetMapping("/id")
    public ResponseEntity<User> findById(
        @RequestBody @Valid UUID id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }

    @Operation(summary = "Check if user exists by email")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "User exists",
                    content = @Content(mediaType = "application/json", 
                    schema = @Schema(implementation = Boolean.class)))
    })
    @GetMapping("/exists")
    public ResponseEntity<Boolean> existsByEmail(
        @RequestBody @Valid String email
    ) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }

}
