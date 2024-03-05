package org.farm.server.service;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.ProductRepository;
import org.farm.server.repository.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final FarmerRepository farmerRepository;


    public ProductService(ProductRepository productRepository, ProductTypeRepository productTypeRepository, FarmerRepository farmerRepository) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.farmerRepository = farmerRepository;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        ProductTypeEntity productTypeEntity = productEntity.getProductType();
        if (productTypeEntity != null) {
            if (productTypeEntity.getId() != null) {
                productTypeEntity = productTypeRepository.findById(productTypeEntity.getId()).orElse(null);

            } else {
                productTypeEntity = null;
            }
        }
        productEntity.setProductType(productTypeEntity);

        FarmerEntity farmerEntity = productEntity.getProducedByFarmer();
        if (farmerEntity != null) {
            if (farmerEntity.getId() != null) {
                farmerEntity = farmerRepository.findById(farmerEntity.getId()).orElse(null);
            } else if (farmerEntity.getEmail() != null) {
                farmerEntity = farmerRepository.findByEmail(farmerEntity.getEmail()).orElse(null);
            } else {
                farmerEntity = null;
            }
        }
        productEntity.setProducedByFarmer(farmerEntity);

        if (productEntity.getProducedDate() == null) {
            productEntity.setProducedDate(new Date());
        }
        return productRepository.save(productEntity);
    }
}
