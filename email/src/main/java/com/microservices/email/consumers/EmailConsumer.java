package com.microservices.email.consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.microservices.email.dtos.EmailDTO;
import com.microservices.email.models.Email;
import com.microservices.email.services.EmailService;

@Component
public class EmailConsumer {

    @Autowired
    private EmailService emailService;

    @RabbitListener(queues = "${broker.queue.email.name}")
    public void listenEmailQueue(@Payload EmailDTO emailDTO) {
        var email = new Email();
        BeanUtils.copyProperties(emailDTO, email);
        emailService.sendMail(email);
    }
    
}
