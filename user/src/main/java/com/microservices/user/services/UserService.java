package com.microservices.user.services;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservices.user.exceptions.UserAlreadyExistsException;
import com.microservices.user.exceptions.UserNotFoundException;
import com.microservices.user.models.User;
import com.microservices.user.producers.UserProducer;
import com.microservices.user.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserProducer userProducer;

    @Transactional
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail()))
            throw new UserAlreadyExistsException("User already exists with email: " + user.getEmail());

        user = userRepository.save(user);
        userProducer.publishMessageEmail(user);
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));
    }

    public User findById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
