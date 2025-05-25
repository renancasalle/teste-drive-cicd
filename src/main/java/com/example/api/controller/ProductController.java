package com.example.api.controller;

import com.example.api.dto.product.ProductRequestDto;
import com.example.api.dto.product.ProductResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/products")
public interface ProductController {

    @GetMapping
    ResponseEntity<List<ProductResponseDto>> getAllProducts();

    @GetMapping("/{id}")
    ResponseEntity<ProductResponseDto> getProductById(@PathVariable UUID id);

    @PostMapping
    ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto productRequestDto);

    @PutMapping("/{id}")
    ResponseEntity<ProductResponseDto> updateProduct(@PathVariable UUID id, @RequestBody ProductRequestDto productRequestDto);

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteProduct(@PathVariable UUID id);
}
