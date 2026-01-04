package com.product_service.product_service.service;


import com.product_service.product_service.dto.ProductRequestDto;
import com.product_service.product_service.dto.ProductResponseDto;

import java.util.List;

public interface ProductService {

    public ProductResponseDto createProduct(ProductRequestDto request ,Long userId);


    public List<ProductResponseDto> getAll();

    public List<ProductResponseDto> getMyProducts(Long ownerId);

    public ProductResponseDto updateProduct(Long productId, ProductRequestDto request);

    public String deleteProduct(Long productId);







}
