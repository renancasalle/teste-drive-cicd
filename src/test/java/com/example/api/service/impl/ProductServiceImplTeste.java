package com.example.api.service.impl;

import com.example.api.entity.Product;
import com.example.api.repository.ProductRepository;
import com.example.api.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

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
        void getUsers_ShouldReturnEmptyList_WhenNoProductsExist() {
            when(productRepository.findAll()).thenReturn(Collections.emptyList());

            List<Product> products = productService.getProducts();

            assertThat(products).isEmpty();
            verify(productRepository).findAll();
        }


    }
}
