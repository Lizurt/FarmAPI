package org.farm.server.repository;

import org.farm.server.model.entities.HarvestQuotaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface HarvestQuotaRepository extends JpaRepository<HarvestQuotaEntity, Integer> {
    @Query("select hqe from HarvestQuotaEntity hqe where hqe.farmer.id = :fid and :d between hqe.startDate and hqe.endDate")
    List<HarvestQuotaEntity> findAllRelevantOfFarmerForDate(
            @Param("fid") Integer farmerId,
            @Param("d") Date startDate
    );
}
