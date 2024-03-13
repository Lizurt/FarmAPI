package org.farm.server.service;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.HarvestQuotaEntity;
import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.requests.SaveHarvestQuotaRequest;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.HarvestQuotaRepository;
import org.farm.server.repository.ProductTypeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@Service
public class HarvestQuotaService {
    private final HarvestQuotaRepository harvestQuotaRepository;

    private final FarmerRepository farmerRepository;

    private final ProductTypeRepository productTypeRepository;

    public HarvestQuotaService(
            HarvestQuotaRepository harvestQuotaRepository,
            FarmerRepository farmerRepository,
            ProductTypeRepository productTypeRepository
    ) {
        this.harvestQuotaRepository = harvestQuotaRepository;
        this.farmerRepository = farmerRepository;
        this.productTypeRepository = productTypeRepository;
    }

    public HarvestQuotaEntity save(SaveHarvestQuotaRequest saveHarvestQuotaRequest) {
        if (saveHarvestQuotaRequest.getStartDate() == null) {
            saveHarvestQuotaRequest.setStartDate(Date.from(ZonedDateTime.now().toInstant()));
        }
        if (saveHarvestQuotaRequest.getEndDate() == null) {
            saveHarvestQuotaRequest.setEndDate(
                    // tldr: start date's end of the day
                    Date.from(
                            ZonedDateTime
                                    .ofInstant(
                                            saveHarvestQuotaRequest.getStartDate().toInstant(),
                                            ZoneId.systemDefault()
                                    )
                                    .plusDays(1)
                                    .toLocalDate()
                                    .atStartOfDay(ZoneId.systemDefault())
                                    .minusNanos(1)
                                    .toInstant()
                    )
            );
        }
        FarmerEntity farmerEntity = farmerRepository.findById(saveHarvestQuotaRequest.getFarmerId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        ProductTypeEntity productTypeEntity = productTypeRepository.findById(saveHarvestQuotaRequest.getProductTypeId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        HarvestQuotaEntity harvestQuotaEntity = new HarvestQuotaEntity();
        harvestQuotaEntity.setAmount(saveHarvestQuotaRequest.getAmount());
        harvestQuotaEntity.setFarmer(farmerEntity);
        harvestQuotaEntity.setProductType(productTypeEntity);
        harvestQuotaEntity.setStartDate(saveHarvestQuotaRequest.getStartDate());
        harvestQuotaEntity.setEndDate(saveHarvestQuotaRequest.getEndDate());
        if (saveHarvestQuotaRequest.getId() != null) {
            harvestQuotaEntity.setId(saveHarvestQuotaRequest.getId());
        }
        return harvestQuotaRepository.save(harvestQuotaEntity);
    }

    public List<HarvestQuotaEntity> getAllRelevantOfFarmer(Integer farmerId) {
        Date date = Date.from(ZonedDateTime.now().toInstant());
        return harvestQuotaRepository.findAllRelevantOfFarmerForDate(farmerId, date);
    }
}
