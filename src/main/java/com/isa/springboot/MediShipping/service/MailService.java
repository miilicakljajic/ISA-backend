package com.isa.springboot.MediShipping.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    private static final String verifyMessage = """
            Hello, welcome to MediShipping application.
            Please verify your account here: http://localhost:4333/api/auth/""";

    private void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

    public void sendAuthMail(String to, String verifyToken) {
        sendEmail(to, "Please verify your account here.", verifyMessage + verifyToken + ".");
    }
}
