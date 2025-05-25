package com.example.api.service.impl;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl {
    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getUserByID(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        if (existsProductByName(product.getName())) {
            throw new ProductNameAlreadyExistsException("Product with name " + product.getName() + " already exists.");
        }
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID id, Product updateProduct) {
        if (updatedProduct.getName() == null && updatedProduct.getDescription() == null && updatedProduct.getPrice() == null) {
            throw new ProductFieldsNullException("At least one field must be provided for update.");
        }

        if (existsProductByName(updateProduct.getName())) {
            throw new ProductNameAlreadyExistsException("Product with name " + updateProduct.getName() + " already exists.");
        }

        Product product = getProductById(id);

        if (updateProduct.getName() != null) product.setName(updateProduct.getName());
        if (updateProduct.getDescription() != null) product.setDescription(updateProduct.getDescription());

        return productRepository.save(product);
    }

    @Override
    public Boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }
}
