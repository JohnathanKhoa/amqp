package com.spring.amqp.impl;

import com.spring.amqp.entity.EmailDetails;
import com.spring.amqp.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Repository;

import jakarta.mail.internet.MimeMessage;
import java.io.File;

@Repository
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}") private String sender;

    public EmailServiceImpl() {
    }

    // To send a simple email
    public String sendSimpleMail(EmailDetails details) {
        // Try block to check for exceptions
        try {
            // Creating a simple mail message
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(details.getRecipient());
            mailMessage.setText(details.getMsgBody());
            mailMessage.setSubject(details.getSubject());
            // Sending the mail
            javaMailSender.send(mailMessage);
            return "Email sent successfully. From sendSimpleMail";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            return "Error while sending email. From sendSimpleMail";
        }
    }

    // To send an email with attachment
    public String sendMailWithAttachment(EmailDetails details) {
        // Creating a mime message
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper;
        try {
            // Setting multipart as true for attachments to be sent
            mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            // Adding the attachment
            FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(file.getFilename(), file);
            // Sending the mail
            javaMailSender.send(mimeMessage);
            return "Email sent successfully. From sendMailWithAttachment";
        }
        // Catch block to handle the exceptions
        catch (Exception e) {
            System.out.println("Exception: " + e.toString());
            return "Error while sending email. From sendMailWithAttachment";
        }
    }
}