package com.scm2.SmartContactManager.Services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.scm2.SmartContactManager.Services.EmailService;


@Service
public class EmailSeviceImpl implements EmailService{

@Autowired 
private JavaMailSender emailSender; // from where it comes


    @Override
    public void sendEmail(String to, String subject, String body) {
       

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(body);
        mailMessage.setFrom("project.test.com");

        emailSender.send(mailMessage);  //try catch for send



    }

    @Override
    public void sendEmailWihtHtml() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendEmailWihtHtml'");
    }

}
