package com.spring.amqp;

import com.spring.amqp.entity.EmailDetails;
import com.spring.amqp.impl.EmailServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.stereotype.Component;

@Component
public class RabbitReceiver implements RabbitListenerConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(RabbitReceiver.class);

    private final EmailServiceImpl emailService;

    public RabbitReceiver(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar rabbitListenerEndpointRegistrar) {
    }

    @RabbitListener(queues = "spring.email.queue")//"${spring.rabbitmq.template.default-receive-queue}")
    public void receivedMessage(EmailDetails mail) {
        logger.info("Email Details Received is.. " + mail);
        emailService.sendSimpleMail(mail);
    }
    @RabbitListener(queues = "spring.email.message")//"${spring.rabbitmq.template.default-receive-queue}")
    public void sendRegistrationToken(EmailDetails mail) {
        logger.info("Email Details Received is.. " + mail);
        emailService.sendSimpleMail(mail);
    }
}
