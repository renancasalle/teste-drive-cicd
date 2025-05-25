package com.example.api.dto.product;

import com.example.api.entity.Product;

public class ProductMapper {
    public static Product toProduct(ProductRequestDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .build();
    }

    public static Product toProduct(ProductUpdateResquetDto dto) {
        if (dto == null) return null;

        return Product.builder()
                .name(dto.getName())
                .price(dto.getPrice())
                .build();
    }

    public static ProductResponseDto toProductResponseDto(Product product) {
        if (product == null) return null;

        return ProductResponseDto.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
}