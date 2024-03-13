package org.farm.server.controller;

import org.farm.server.model.entities.HarvestQuotaEntity;
import org.farm.server.model.requests.SaveHarvestQuotaRequest;
import org.farm.server.repository.HarvestQuotaRepository;
import org.farm.server.service.HarvestQuotaService;
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

    /**
     * Saves a harvest quota based on given quota info
     *
     * @param saveHarvestQuotaRequest the quota info
     * @return a saved harvest quota
     */
    @PutMapping("/save")
    public HarvestQuotaEntity save(@RequestBody SaveHarvestQuotaRequest saveHarvestQuotaRequest) {
        return harvestQuotaService.save(saveHarvestQuotaRequest);
    }

    /**
     * Gets all harvest quotas
     *
     * @return all harvest quotas
     */
    @GetMapping("/get")
    public List<HarvestQuotaEntity> getAll() {
        return harvestQuotaRepository.findAll();
    }

    /**
     * Gets all harvest quotas of a specified farmer during a current day
     *
     * @param farmerId the farmer id
     * @return all relevant (today) quotas for the farmer
     */
    @GetMapping("/get/of-farmer/{id}/relevant")
    public List<HarvestQuotaEntity> getAllRelevantOfFarmer(@PathVariable("id") Integer farmerId) {
        return harvestQuotaService.getAllRelevantOfFarmer(farmerId);
    }

    /**
     * Deletes a quota based on a given id
     *
     * @param harvestQuotaId the id of a quota to delete
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer harvestQuotaId) {
        harvestQuotaRepository.deleteById(harvestQuotaId);
    }
}
