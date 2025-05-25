package com.example.api.service;

import com.example.api.entity.Product;

import java.util.List;
import java.util.UUID;

public class ProductService {
    List<Product> getProducts();
    Product getProductById(String id);
    Product saveProduct(Product product);
    Product updateProduct(String id, Product updatedProduct);
    void deleteProduct(String id);
    boolean existsByName(String name);
}
