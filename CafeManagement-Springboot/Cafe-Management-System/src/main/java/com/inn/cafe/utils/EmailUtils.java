package com.inn.cafe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

@Service
public class EmailUtils {

    @Autowired
    private JavaMailSender mailSender;

    public void sendSimpleMessage(String sendTo, String subject, String body, List<String> listOfEmails) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("anastasijayardzen@gmail.com");
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(body);
        if (listOfEmails != null && listOfEmails.size() > 0) {
            message.setCc(getCcArray(listOfEmails));
        }
        mailSender.send(message);
    }

    private String[] getCcArray(List<String> listOfEmails) {
        String[] cc = new String[listOfEmails.size()];
        for (int i = 0; i < listOfEmails.size(); i++) {
            cc[i] = listOfEmails.get(i);
        }
        return cc;
    }

    public void forgotPasswordMessage(String sendTo, String subject, String password) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("anastasijayardzen@gmail.com");
        helper.setTo(sendTo);
        helper.setSubject(subject);
        String htmlMsg = "<p><b>Your Login details for Cafe Management System</b><br><b>Email: </b> \" + sendTo + \" <br><b>Password: </b> \" + password + \"<br><a href=\\\"http://localhost:4200/\\\">Click here to login</a></p>";
        message.setContent(htmlMsg, "text/html");
        mailSender.send(message);
    }

}
