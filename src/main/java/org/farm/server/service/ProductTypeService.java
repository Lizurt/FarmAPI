package org.farm.server.service;

import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.entities.UnitsEntity;
import org.farm.server.model.requests.SaveProductTypeRequest;
import org.farm.server.repository.ProductTypeRepository;
import org.farm.server.repository.UnitsRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    private final UnitsRepository unitsRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository, UnitsRepository unitsRepository) {
        this.productTypeRepository = productTypeRepository;
        this.unitsRepository = unitsRepository;
    }

    public ProductTypeEntity saveProductType(SaveProductTypeRequest saveProductTypeRequest) {
        UnitsEntity units = unitsRepository.findById(saveProductTypeRequest.getUnitsId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST)
        );
        ProductTypeEntity productType = new ProductTypeEntity();
        productType.setName(saveProductTypeRequest.getName());
        productType.setUnits(units);
        productType.setId(saveProductTypeRequest.getId());
        return productTypeRepository.save(productType);
    }
}
