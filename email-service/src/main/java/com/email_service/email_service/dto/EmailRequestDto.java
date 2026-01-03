package com.email_service.email_service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequestDto {
    private String to;
    private String subject;
    private String body;
    private String activationLink;
}
