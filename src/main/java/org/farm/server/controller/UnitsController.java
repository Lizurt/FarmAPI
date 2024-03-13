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

    /**
     * Gets all units
     *
     * @return all units
     */
    @GetMapping("/get")
    public List<UnitsEntity> getAll() {
        return unitsRepository.findAll();
    }

    /**
     * Saves a unit based on given info
     *
     * @param unitsEntity the unit
     * @return the saved unit
     */
    @PutMapping("/save")
    public UnitsEntity save(@RequestBody UnitsEntity unitsEntity) {
        return unitsRepository.save(unitsEntity);
    }

    /**
     * Deletes a unit based on given id
     *
     * @param unitId the unit id
     */
    @DeleteMapping("/delete/{unitId}")
    public void delete(@PathVariable Integer unitId) {
        unitsRepository.deleteById(unitId);
    }
}
