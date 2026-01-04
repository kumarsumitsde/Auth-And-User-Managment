package com.product_service.product_service.serviceimp;

import com.product_service.product_service.dto.ProductRequestDto;
import com.product_service.product_service.dto.ProductResponseDto;
import com.product_service.product_service.entity.Product;
import com.product_service.product_service.repository.ProductRepository;
import com.product_service.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImp implements ProductService {

    private final ProductRepository repository;

    @Override
    public ProductResponseDto createProduct(ProductRequestDto request, Long userId) {
        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(Long.valueOf(request.getPrice()))
                .quantity(request.getQuantity())
                .ownerId(request.getOwnerId())
                .createdBy(userId) // Assuming you have userId from the context
                .build();

        Product newProduct = repository.save(product);
        return mapToResponce(newProduct);

    }


    @Override
    public List<ProductResponseDto> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponce)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductResponseDto> getMyProducts(Long ownerId) {
        return repository.findByOwnerId(ownerId)
                .stream()
                .map(this::mapToResponce)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto updateProduct(Long productId, ProductRequestDto request) {
        Product product = repository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(Long.valueOf(request.getPrice()));
        product.setQuantity(request.getQuantity());
        Product updatedProduct = repository.save(product);
        return mapToResponce(updatedProduct);
    }

    @Override
    public String deleteProduct(Long productId) {
        Product product = repository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        repository.delete(product);
        return "Product deleted successfully";

    }

    private ProductResponseDto mapToResponce(Product product) {
        ProductResponseDto response = new ProductResponseDto();
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(String.valueOf(product.getPrice()));
        response.setQuantity(Long.valueOf(product.getQuantity()));
        response.setOwnerId(product.getOwnerId());
        response.setCreatedBy(product.getCreatedBy());
        return response;
    }
}
