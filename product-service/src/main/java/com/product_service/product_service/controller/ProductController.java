package com.product_service.product_service.controller;


import com.product_service.product_service.dto.ProductRequestDto;
import com.product_service.product_service.dto.ProductResponseDto;
import com.product_service.product_service.entity.Product;
import com.product_service.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {


    private final ProductService productService;

    @PostMapping("/create")
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto request, @RequestHeader("X-User-Id") Long userId) {
        return productService.createProduct(request, userId);
    }

    @GetMapping("/all")
    public List getAllProducts() {
        return productService.getAll();
    }

    @GetMapping("myProducts/{ownerId}")
    public List getMyProducts(@PathVariable Long ownerId) {
        return productService.getMyProducts(ownerId);
    }

    @PutMapping("/update/{productId}")
    public ProductResponseDto updateProduct(@PathVariable Long productId ,@RequestBody ProductRequestDto request) {
        return productService.updateProduct(productId, request);
    }

    @DeleteMapping("/delete/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }
}
