package com.sparesparts.config.mail;

public interface EmailService {
    public String sendOtpEmail(String to);

    EmailData sendDataToUser(String to, String subject, String body);
}
