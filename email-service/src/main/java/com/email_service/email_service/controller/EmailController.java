package com.email_service.email_service.controller;


import com.email_service.email_service.dto.EmailRequestDto;
import com.email_service.email_service.dto.EmailResponceDto;
import com.email_service.email_service.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/email")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send")
    public EmailResponceDto sendEmail(@RequestBody EmailRequestDto dto){
            emailService.sendEmail(dto);
            return new EmailResponceDto(
                    "Success",
                    "Email sent successfully",
                    dto.getActivationLink()
            );
    }


}
