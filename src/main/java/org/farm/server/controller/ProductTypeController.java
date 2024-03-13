package org.farm.server.controller;

import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.requests.SaveProductTypeRequest;
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

    /**
     * Saves a product type based on given product type info
     *
     * @param saveProductTypeRequest the product type info
     * @return the saved product type entity
     */
    @PutMapping("/save")
    public ProductTypeEntity addProductType(@RequestBody SaveProductTypeRequest saveProductTypeRequest) {
        return productTypeService.saveProductType(saveProductTypeRequest);
    }

    /**
     * Deletes a product type with specified id
     *
     * @param id the product type id
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productTypeRepository.deleteById(id);
    }

    /**
     * Gets all product types
     *
     * @return all product types
     */
    @GetMapping("/get-all")
    public List<ProductTypeEntity> getAll() {
        return productTypeRepository.findAll();
    }
}
