package com.example.api.service;

import com.example.api.entity.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();
    Product getProductById(UUID id);
    Product saveProduct(Product product);
    Product updateProduct(UUID id, Product updatedProduct);
    void deleteProduct(UUID id);
    boolean existsProductByName(String name);


}

