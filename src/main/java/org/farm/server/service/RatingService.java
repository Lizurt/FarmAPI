package org.farm.server.service;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.RatingEntity;
import org.farm.server.model.requests.SaveRatingRequest;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.ZonedDateTime;

@Service
public class RatingService {
    private final RatingRepository ratingRepository;
    private final FarmerRepository farmerRepository;

    public RatingService(RatingRepository ratingRepository, FarmerRepository farmerRepository) {
        this.ratingRepository = ratingRepository;
        this.farmerRepository = farmerRepository;
    }

    public RatingEntity save(SaveRatingRequest saveRatingRequest) {
        if (saveRatingRequest == null) {
            return null;
        }
        if (saveRatingRequest.getFarmerId() == null) {
            return null;
        }
        FarmerEntity farmerEntity = farmerRepository.findById(saveRatingRequest.getFarmerId()).orElse(null);
        if (farmerEntity == null) {
            return null;
        }
        if (saveRatingRequest.getRating() == null) {
            return null;
        }
        if (saveRatingRequest.getDate() == null) {
            saveRatingRequest.setDate(Date.from(ZonedDateTime.now().toInstant()));
        }
        RatingEntity ratingEntity = new RatingEntity();
        ratingEntity.setRating(saveRatingRequest.getRating());
        ratingEntity.setDate(saveRatingRequest.getDate());
        ratingEntity.setFarmer(farmerEntity);
        return ratingRepository.save(ratingEntity);
    }
}
