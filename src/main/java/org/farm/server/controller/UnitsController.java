package org.farm.server.controller;

import org.farm.server.model.UnitsEntity;
import org.farm.server.repository.UnitsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UnitsController {
    private final UnitsRepository unitsRepository;

    public UnitsController(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    @GetMapping("/units/get-all")
    public List<UnitsEntity> getAll() {
        return unitsRepository.findAll();
    }

    @PutMapping("/units/save")
    public UnitsEntity save(@RequestBody UnitsEntity unitsEntity) {
        return unitsRepository.save(unitsEntity);
    }

    @DeleteMapping("/units/delete/{id}")
    public void delete(@PathVariable int id) {
        unitsRepository.deleteById(id);
    }
}
