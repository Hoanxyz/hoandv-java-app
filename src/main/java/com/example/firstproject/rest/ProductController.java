package com.example.firstproject.rest;

import com.example.firstproject.entity.ProductEntity;
import com.example.firstproject.service.impl.ProductServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductServiceImpl productService;

    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ProductEntity productCreated = this.productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(productCreated);
    }

    @PostMapping("/update")
    public ResponseEntity<ProductEntity> updateProduct(@RequestBody ProductEntity product) {
        ProductEntity productUpdated = this.productService.updateProduct(product.getId(), product);
        return ResponseEntity.ok(productUpdated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable("id") int id) {
        ProductEntity product = this.productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/list-products")
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> listProduct = this.productService.getAllProducts();
        return ResponseEntity.ok(listProduct);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") int id) {
        this.productService.deleteProduct(id);
        return ResponseEntity.notFound().build();
    }
}
