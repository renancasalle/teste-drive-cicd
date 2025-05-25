package com.example.api.controller.impl;

import com.example.api.controller.ProductController;
import com.example.api.dto.product.ProductMapper;
import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import com.example.api.entity.Product;
import com.example.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductControllerImpl implements ProductController {

    private final ProductService productService;

    @Override
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<Product> products = productService.getProducts();

        if (products.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<ProductResponseDto> productResponseDtos = products.stream()
                .map(ProductMapper::toProductResponseDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponseDtos);
    }

    @Override
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id) {
        Product product = productService.getProductById(id);
        ProductResponseDto productResponseDto = ProductMapper.toProductResponseDto(product);

        return ResponseEntity.ok(productResponseDto);
    }

    @Override
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        Product product = ProductMapper.toProduct(productRequestDto);
        Product savedProduct = productService.saveProduct(product);
        ProductResponseDto productResponseDto = ProductMapper.toProductResponseDto(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(productResponseDto);
    }

    @Override
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @RequestBody ProductRequestDto productRequestDto) {
        Product product = ProductMapper.toProduct(productRequestDto);
        Product updatedProduct = productService.updateProduct(id, product);
        ProductResponseDto productResponseDto = ProductMapper.toProductResponseDto(updatedProduct);

        return ResponseEntity.ok(productResponseDto);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
