package org.farm.server.controller;

import org.farm.server.model.entities.ProductEntity;
import org.farm.server.repository.ProductRepository;
import org.farm.server.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;


    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @GetMapping("/product/get-all")
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @PutMapping("/product/save")
    public ProductEntity save(@RequestBody ProductEntity productEntity) {
        return productService.saveProduct(productEntity);
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }
}
