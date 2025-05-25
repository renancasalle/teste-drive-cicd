package com.example.api.service.impl;

import com.example.api.entity.Product;
import com.example.api.exception.product.ProductNotFoundException;
import com.example.api.repository.ProductRepository;
import com.example.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTeste {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Nested
    class GetProductsTest {
        @Test
        void getProducts_ShouldReturnEmptyList_WhenNoProductsExist() {
            when(productRepository.findAll()).thenReturn(Collections.emptyList());

            List<Product> products = productService.getProducts();

            assertThat(products).isEmpty();
            verify(productRepository).findAll();
        }

        @Test
        void getProducts_ShouldReturnListOfProducts_WhenProductsExist() {
            Product product1 = new Product();
            Product product2 = new Product();
            List<Product> expectedProducts = List.of(product1, product2);

            when(productRepository.findAll()).thenReturn(expectedProducts);

            List<Product> products = productService.getProducts();

            assertThat(products).isEqualTo(expectedProducts);
            verify(productRepository).findAll();
        }
    }

    @Nested
    class GetProductByIdTest {
        @Test
        void getProductById_ShouldThrowException_WhenProductNotFound() {
            UUID productId = UUID.randomUUID();
            when(productRepository.findById(productId)).thenReturn(Optional.empty());

            Exception exception = assertThrows(ProductNotFoundException.class, () -> {
                productService.getProductById(productId);
            });

            assertThat(exception.getMessage()).isEqualTo("Product not found with id: " + productId);
            verify(productRepository).findById(productId);
        }
    }

    @Test
    void saveProduct_ShouldSaveProduct_WhenProductIsValid() {
        Product product = new Product();
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);

        assertThat(savedProduct).isEqualTo(product);
        verify(productRepository).save(product);
    }

    @Nested
    class UpdateProductTest {
        @Test
        void updateProduct_ShouldUpdateFields_WhenProductExists() {
            UUID productId = UUID.randomUUID();
            Product existingProduct = new Product();
            Product updatedProduct = new Product();
            updatedProduct.setName("Novo Nome");
            updatedProduct.setDescription("Nova Desc");
            updatedProduct.setPrice(100.0);

            when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
            when(productRepository.save(existingProduct)).thenReturn(existingProduct);

            Product result = productService.updateProduct(productId, updatedProduct);

            assertThat(result).isEqualTo(existingProduct);
            verify(productRepository).findById(productId);
            verify(productRepository).save(existingProduct);
        }
    }

    @Test
    void deleteProduct_ShouldDeleteProduct_WhenProductExists() {
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    @Test
    void deleteProduct_ShouldThrowProductNotFoundException_WhenProductDoesNotExist() {
        UUID productId = UUID.randomUUID();
        when(productRepository.existsById(productId)).thenReturn(false);

        Exception exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.deleteProduct(productId);
        });

        assertThat(exception.getMessage()).isEqualTo("Product not found with id: " + productId);
        verify(productRepository).existsById(productId);
    }

    @Test
    void existsProductByName_ShouldReturnTrue_WhenProductExists() {
        String productName = "Existing Product";
        when(productRepository.existsByName(productName)).thenReturn(true);

        boolean exists = productService.existsProductByName(productName);

        assertThat(exists).isTrue();
        verify(productRepository).existsByName(productName);
    }

    @Test
    void existsProductByName_ShouldReturnFalse_WhenProductDoesNotExist() {
        String productName = "Nonexistent Product";
        when(productRepository.existsByName(productName)).thenReturn(false);

        boolean exists = productService.existsProductByName(productName);

        assertThat(exists).isFalse();
        verify(productRepository).existsByName(productName);
    }
}