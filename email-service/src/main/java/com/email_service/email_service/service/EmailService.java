package com.email_service.email_service.service;

import com.email_service.email_service.dto.EmailRequestDto;

public interface EmailService {

    void sendEmail(EmailRequestDto dto);
}
