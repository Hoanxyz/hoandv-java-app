package com.example.firstproject.service;

import com.example.firstproject.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    ProductEntity createProduct(ProductEntity product);

    ProductEntity updateProduct(Integer id, ProductEntity product);

    ProductEntity getProductById(Integer id);

    List<ProductEntity> getAllProducts();

    void deleteProduct(Integer id);
}
