package org.farm.server.controller;

import org.farm.server.model.entities.RatingEntity;
import org.farm.server.model.requests.SaveRatingRequest;
import org.farm.server.repository.RatingRepository;
import org.farm.server.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/rating")
public class RatingController {
    private final RatingRepository ratingRepository;

    private final RatingService ratingService;

    public RatingController(RatingRepository ratingRepository, RatingService ratingService) {
        this.ratingRepository = ratingRepository;
        this.ratingService = ratingService;
    }

    @PutMapping("/save")
    public RatingEntity save(@RequestBody SaveRatingRequest saveRatingRequest) {
        return ratingService.save(saveRatingRequest);
    }

    @GetMapping("/get")
    public List<RatingEntity> get() {
        return ratingRepository.findAll();
    }

    @GetMapping("/get/{id}")
    public RatingEntity get(@PathVariable("id") Integer id) {
        return ratingRepository.findById(id).orElse(null);
    }

    @GetMapping("/get/of-farmer/{id}")
    public List<RatingEntity> getOfFarmer(@PathVariable("id") Integer id) {
        return ratingRepository.findAllByFarmerId(id);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        ratingRepository.deleteById(id);
    }
}
