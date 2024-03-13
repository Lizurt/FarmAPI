package org.farm.server.repository;

import org.farm.server.model.entities.FarmerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends JpaRepository<FarmerEntity, Integer> {
}
