package org.farm.server.controller;

import org.farm.server.model.entities.UnitsEntity;
import org.farm.server.repository.UnitsRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/units")
public class UnitsController {
    private final UnitsRepository unitsRepository;

    public UnitsController(UnitsRepository unitsRepository) {
        this.unitsRepository = unitsRepository;
    }

    @GetMapping("/get-all")
    public List<UnitsEntity> getAll() {
        return unitsRepository.findAll();
    }

    @PutMapping("/save")
    public UnitsEntity save(@RequestBody UnitsEntity unitsEntity) {
        return unitsRepository.save(unitsEntity);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        unitsRepository.deleteById(id);
    }
}
