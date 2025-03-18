package com.example.glovotest.service;
import com.example.glovodb.model.Product;
import com.example.glovodb.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductServiceTest {
    private static ProductRepository productRepository;
    private static ProductService productService;

    @BeforeEach
    void setUp() {
        productRepository = Mockito.mock(ProductRepository.class);
        productService = new ProductService(productRepository);
    }

    @Test
    void addProductTest() {
        Product product = Product.builder()
                .id(1)
                .name("burger")
                .cost(49.99)
                .orderid(123)
                .build();
        Mockito.when(productRepository.save(product)).thenReturn(product);
        Product savedProduct = productService.addProduct(product);
        assertEquals(product, savedProduct);
    }
}