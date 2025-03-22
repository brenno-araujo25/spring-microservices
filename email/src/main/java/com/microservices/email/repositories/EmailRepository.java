package com.microservices.email.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservices.email.models.Email;

public interface EmailRepository extends JpaRepository<Email, UUID> {
    
}
