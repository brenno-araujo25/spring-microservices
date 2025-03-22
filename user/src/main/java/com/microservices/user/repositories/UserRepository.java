package com.microservices.user.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.user.models.User;

public interface UserRepository extends JpaRepository<User, UUID> {
}
