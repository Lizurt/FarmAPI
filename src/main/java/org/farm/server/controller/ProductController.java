package org.farm.server.controller;

import org.farm.server.model.ProductEntity;
import org.farm.server.repository.ProductRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product/get-all")
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @PutMapping("/product/save")
    public ProductEntity save(@RequestBody ProductEntity productEntity) {
        return productRepository.save(productEntity);
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete(@PathVariable int id) {
        productRepository.deleteById(id);
    }
}
