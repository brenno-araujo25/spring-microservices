package com.microservices.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        user = userRepository.save(user);
        userProducer.publishMessageEmail(user);
        return user;
    }

}
