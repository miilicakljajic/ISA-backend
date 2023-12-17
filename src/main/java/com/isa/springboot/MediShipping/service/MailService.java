package com.isa.springboot.MediShipping.service;
import com.google.zxing.WriterException;
import com.isa.springboot.MediShipping.bean.EquipmentCollectionAppointment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

@Service
public class MailService {
    @Autowired
    private JavaMailSender mailSender;
    private static final String verifyMessage = """
            Hello, welcome to MediShipping application.
            Please verify your account here: http://localhost:4333/api/auth/""";

    private static final String appointmentMessage = """
            Hello, here is your qr code for your appointment.
            """;

    private void sendEmail(String to, String subject, String body, String qrPath) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(body);
        if(!qrPath.equals(""))
        {
            FileSystemResource file = new FileSystemResource(new File(qrPath));
            helper.addAttachment(file.getFilename(), file);
        }
        mailSender.send(message);
    }

    public void sendAuthMail(String to, String verifyToken) throws MessagingException {
        sendEmail(to, "Please verify your account here.", verifyMessage + verifyToken + ".", "");
    }

    public void sendAppointmentMail(String to, EquipmentCollectionAppointment appointment) throws MessagingException {
        try {
            QrService.generateQRCodeImage(appointment.toString(),300,300, "qr.png");
            sendEmail(to, "New appointment", appointmentMessage, "qr.png");
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}