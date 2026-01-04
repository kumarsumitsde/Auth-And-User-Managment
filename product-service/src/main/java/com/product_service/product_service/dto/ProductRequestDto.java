package com.product_service.product_service.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDto {

    private String name;
    private String description;
    private String price;
    private Long quantity;
    private Long ownerId;

}
