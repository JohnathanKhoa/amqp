package com.spring.amqp.service;

import com.spring.amqp.entity.EmailDetails;

public interface EmailService {

    String sendSimpleMail(EmailDetails details);

    String sendMailWithAttachment(EmailDetails details);

}
