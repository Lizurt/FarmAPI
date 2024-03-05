package org.farm.server.controller;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.repository.FarmerRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FarmerController {
    private final FarmerRepository farmerRepository;

    public FarmerController(FarmerRepository farmerRepository) {
        this.farmerRepository = farmerRepository;
    }

    @GetMapping("/farmer/get-all")
    public List<FarmerEntity> getAll() {
        return farmerRepository.findAll();
    }

    @PutMapping("/farmer/save")
    public FarmerEntity save(@RequestBody FarmerEntity farmerEntity) {
        return farmerRepository.save(farmerEntity);
    }

    @DeleteMapping("/farmer/delete/{id}")
    public void delete(@PathVariable Integer id) {
        farmerRepository.deleteById(id);
    }
}
