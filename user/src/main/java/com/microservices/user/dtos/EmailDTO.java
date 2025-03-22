package com.microservices.user.dtos;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailDTO {

    private UUID userId;

    private String emailTo;

    private String subject;
    
    private String body;

}
