package org.farm.server.repository;

import org.farm.server.model.entities.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<RatingEntity, Integer> {
    @Query("select re from RatingEntity re where re.farmer.id = :fid")
    List<RatingEntity> findAllByFarmerId(@Param("fid") Integer farmerId);
}
