package com.email_service.email_service.serviceimp;

import com.email_service.email_service.dto.EmailRequestDto;
import com.email_service.email_service.service.EmailService;
import lombok.AllArgsConstructor;
import org.apache.naming.factory.SendMailFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class Emailserviceimp implements EmailService {

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(EmailRequestDto dto) {
        SimpleMailMessage mail= new SimpleMailMessage();
        mail.setTo(dto.getTo());
        mail.setSubject(dto.getSubject());
        mail.setText(dto.getBody() +
                dto.getActivationLink());


        mailSender.send(mail);
        // Implementation code to send email
    }
}
