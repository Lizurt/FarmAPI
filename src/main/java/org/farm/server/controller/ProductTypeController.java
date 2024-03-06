package org.farm.server.controller;

import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.repository.ProductTypeRepository;
import org.farm.server.service.ProductTypeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product-type")
public class ProductTypeController {
    private final ProductTypeRepository productTypeRepository;
    private final ProductTypeService productTypeService;

    public ProductTypeController(ProductTypeRepository productTypeRepository, ProductTypeService productTypeService) {
        this.productTypeRepository = productTypeRepository;
        this.productTypeService = productTypeService;
    }

    @PutMapping("/save")
    public ProductTypeEntity addProductType(@RequestBody ProductTypeEntity productTypeEntity) {
        return productTypeService.saveProductType(productTypeEntity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productTypeRepository.deleteById(id);
    }

    @GetMapping("/get-all")
    public List<ProductTypeEntity> getAll() {
        return productTypeRepository.findAll();
    }
}
