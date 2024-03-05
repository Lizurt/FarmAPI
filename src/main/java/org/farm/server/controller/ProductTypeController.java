package org.farm.server.controller;

import org.farm.server.model.ProductTypeEntity;
import org.farm.server.repository.ProductTypeRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductTypeController {
    private final ProductTypeRepository productTypeRepository;

    public ProductTypeController(ProductTypeRepository productTypeRepository) {
        this.productTypeRepository = productTypeRepository;
    }

    @PutMapping("/product-type/save")
    public ProductTypeEntity addProductType(@RequestBody ProductTypeEntity productTypeEntity) {
        return productTypeRepository.save(productTypeEntity);
    }

    @DeleteMapping("/product-type/delete/{id}")
    public void delete(@PathVariable int id) {
        productTypeRepository.deleteById(id);
    }

    @GetMapping("/product-type/get-all")
    public List<ProductTypeEntity> getAll() {
        return productTypeRepository.findAll();
    }
}
