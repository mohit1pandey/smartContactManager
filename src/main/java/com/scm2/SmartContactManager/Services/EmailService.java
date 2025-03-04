package com.scm2.SmartContactManager.Services;

public interface EmailService {


    public void  sendEmail(String to, String subject, String body);



    // if wnat to send email

    public void sendEmailWihtHtml();

    
}
