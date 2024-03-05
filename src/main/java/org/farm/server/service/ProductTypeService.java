package org.farm.server.service;

import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.entities.UnitsEntity;
import org.farm.server.repository.ProductTypeRepository;
import org.farm.server.repository.UnitsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductTypeService {
    private final ProductTypeRepository productTypeRepository;

    private final UnitsRepository unitsRepository;

    public ProductTypeService(ProductTypeRepository productTypeRepository, UnitsRepository unitsRepository) {
        this.productTypeRepository = productTypeRepository;
        this.unitsRepository = unitsRepository;
    }

    public ProductTypeEntity saveProductType(ProductTypeEntity productTypeEntity) {
        UnitsEntity units = productTypeEntity.getUnits();
        if (units.getId() != null) {
            units = unitsRepository.findById(units.getId()).orElse(null);
        } else if (units.getName() != null) {
            units = unitsRepository.findByName(units.getName()).orElse(null);
        } else {
            units = null;
        }
        productTypeEntity.setUnits(units);
        return productTypeRepository.save(productTypeEntity);
    }
}
