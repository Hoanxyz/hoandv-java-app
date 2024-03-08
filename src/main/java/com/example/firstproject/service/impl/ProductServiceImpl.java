package com.example.firstproject.service.impl;

import com.example.firstproject.entity.ProductEntity;
import com.example.firstproject.repository.ProductRepository;
import com.example.firstproject.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        return this.productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(Integer id, ProductEntity newProduct) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);
        if (product != null) {
            return this.productRepository.save(newProduct);
        } else {
            throw new IllegalArgumentException("San pham khong ton tai");
        }
    }

    @Override
    public ProductEntity getProductById(Integer id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);
        if (product != null) {
            return product;
        } else {
            throw new IllegalArgumentException("San pham khong ton tai");
        }
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return this.productRepository.findAll();
    }

    @Override
    public void deleteProduct(Integer id) {
        ProductEntity product = this.productRepository.findById(id).orElse(null);
        if (product != null) {
            this.productRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("San pham khong ton tai");
        }
    }
}
