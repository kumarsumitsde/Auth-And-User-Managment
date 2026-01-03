package com.auth_service.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailRequestDto {
    private String to;
    private String subject;
    private String body;
    private String activationLink;
}
