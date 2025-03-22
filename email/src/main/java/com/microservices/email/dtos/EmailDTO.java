package com.microservices.email.dtos;

import java.util.UUID;

public record EmailDTO(
    UUID userId,
    String emailTo,
    String subject,
    String body
) {

}
