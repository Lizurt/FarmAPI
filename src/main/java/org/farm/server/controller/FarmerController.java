package org.farm.server.controller;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.requests.SaveFarmerRequest;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.service.FarmerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/farmer")
public class FarmerController {
    private final FarmerRepository farmerRepository;

    private final FarmerService farmerService;

    public FarmerController(FarmerRepository farmerRepository, FarmerService farmerService) {
        this.farmerRepository = farmerRepository;
        this.farmerService = farmerService;
    }

    @GetMapping("/get-all")
    public List<FarmerEntity> getAll() {
        return farmerRepository.findAll();
    }

    @PutMapping("/save")
    public FarmerEntity save(@RequestBody @Validated SaveFarmerRequest saveFarmerRequest) {
        return farmerService.saveFarmer(saveFarmerRequest);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer farmerId) {
        farmerRepository.deleteById(farmerId);
    }
}
