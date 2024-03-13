package org.farm.server.controller;

import org.farm.server.model.entities.RatingEntity;
import org.farm.server.model.requests.SaveRatingRequest;
import org.farm.server.repository.RatingRepository;
import org.farm.server.service.RatingService;
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

    /**
     * Saves a rating with a given info
     *
     * @param saveRatingRequest the rating info
     * @return the saved rating
     */
    @PutMapping("/save")
    public RatingEntity save(@RequestBody SaveRatingRequest saveRatingRequest) {
        return ratingService.save(saveRatingRequest);
    }

    /**
     * Gets all ratings
     *
     * @return all ratings
     */
    @GetMapping("/get")
    public List<RatingEntity> get() {
        return ratingRepository.findAll();
    }

    /**
     * Gets all ratings of a specified farmer
     *
     * @param farmerId the farmer id
     * @return all ratings of the farmer
     */
    @GetMapping("/get/of-farmer/{id}")
    public List<RatingEntity> getOfFarmer(@PathVariable("id") Integer farmerId) {
        return ratingRepository.findAllByFarmerId(farmerId);
    }

    /**
     * Deletes a rating based on a given id
     *
     * @param ratingId the rating id
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable("id") Integer ratingId) {
        ratingRepository.deleteById(ratingId);
    }
}
