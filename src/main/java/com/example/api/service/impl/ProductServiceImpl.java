package com.example.api.service.impl;

import com.example.api.entity.Product;
import com.example.api.exception.product.ProductFieldsNullException;
import com.example.api.exception.product.ProductNameAlreadyExistsException;
import com.example.api.exception.product.ProductNotFoundException;
import com.example.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public Product saveProduct(Product product) {
        if (existsProductByName(product.getName())) {
            throw new ProductNameAlreadyExistsException("Product with name " + product.getName() + " already exists.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(UUID id, Product updatedProduct) {
        if (updatedProduct.getName() == null && updatedProduct.getDescription() == null && updatedProduct.getPrice() == null) {
            throw new ProductFieldsNullException("At least one field must be provided for update.");
        }

        if (existsProductByName(updatedProduct.getName())) {
            throw new ProductNameAlreadyExistsException("Product with name " + updatedProduct.getName() + " already exists.");
        }

        Product product = getProductById(id);

        if (updatedProduct.getName() != null) product.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) product.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getPrice() != null) product.setPrice(updatedProduct.getPrice());

        return productRepository.save(product);
    }

    public Boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }
}