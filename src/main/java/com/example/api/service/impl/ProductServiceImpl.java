package com.example.api.service.impl;

import com.example.api.entity.Product;
import com.example.api.exception.product.ProductNotFoundException;
import com.example.api.repository.ProductRepository;
import com.example.api.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Override
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(UUID id, Product updatedProduct) {
        Product product = getProductById(id);
        if (updatedProduct.getName() != null) product.setName(updatedProduct.getName());
        if (updatedProduct.getDescription() != null) product.setDescription(updatedProduct.getDescription());
        if (updatedProduct.getPrice() != null) product.setPrice(updatedProduct.getPrice());
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsProductByName(String name) {
        return productRepository.existsByName(name);
    }
}