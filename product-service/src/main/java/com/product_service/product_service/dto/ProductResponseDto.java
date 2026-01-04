package com.product_service.product_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {

    private String name;
    private String description;
    private String price;
    private Long quantity;
    private Long ownerId;
    private Long createdBy;


}
