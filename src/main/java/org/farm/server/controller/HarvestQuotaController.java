package org.farm.server.controller;

import org.farm.server.model.entities.HarvestQuotaEntity;
import org.farm.server.model.requests.SaveHarvestQuotaRequest;
import org.farm.server.model.requests.SaveRatingRequest;
import org.farm.server.repository.HarvestQuotaRepository;
import org.farm.server.service.HarvestQuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/harvest-quota")
public class HarvestQuotaController {
    private final HarvestQuotaService harvestQuotaService;

    private final HarvestQuotaRepository harvestQuotaRepository;

    public HarvestQuotaController(HarvestQuotaService harvestQuotaService, HarvestQuotaRepository harvestQuotaRepository) {
        this.harvestQuotaService = harvestQuotaService;
        this.harvestQuotaRepository = harvestQuotaRepository;
    }

    @PutMapping("/save")
    public HarvestQuotaEntity save(@RequestBody SaveHarvestQuotaRequest saveHarvestQuotaRequest) {
        return harvestQuotaService.save(saveHarvestQuotaRequest);
    }

    @GetMapping("/get")
    public List<HarvestQuotaEntity> getAll() {
        return harvestQuotaRepository.findAll();
    }

    @GetMapping("/get/of-farmer/{id}/relevant")
    public List<HarvestQuotaEntity> getAllRelevantOfFarmer(@PathVariable("id") Integer farmerId) {
        return harvestQuotaService.getAllRelevantOfFarmer(farmerId);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer harvestQuotaId) {
        harvestQuotaRepository.deleteById(harvestQuotaId);
    }
}
