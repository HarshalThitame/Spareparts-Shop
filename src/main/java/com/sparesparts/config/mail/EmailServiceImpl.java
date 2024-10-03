package com.sparesparts.config.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService {


    @Autowired
    private JavaMailSender mailSender;

    @Override
    public String sendOtpEmail(String to) {
        MimeMessage message = mailSender.createMimeMessage();

        System.out.println(to);
        try {
            message.setFrom(new InternetAddress("milkdairymanagementsystem@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);

            Random random = new Random();
            String otp = String.format("%06d", random.nextInt(1000000));


            message.setSubject("Your Otp for ShoNest " + otp);

            String htmlContent = "<h1>OTP :" + otp + " </h1>";
            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
            return otp;
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmailData sendDataToUser(String to, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            message.setFrom(new InternetAddress("milkdairymanagementsystem@gmail.com"));
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(body, "text/html; charset=utf-8");

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        return new EmailData();
    }


}


