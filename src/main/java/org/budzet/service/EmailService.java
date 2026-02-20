package org.budzet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void wyslijProstyEmail(String doKogo, String temat, String tresc) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(doKogo);
        message.setSubject(temat);
        message.setText(tresc);
        // Opcjonalnie: message.setFrom("twoj-mail@gmail.com");

        mailSender.send(message);
    }
}