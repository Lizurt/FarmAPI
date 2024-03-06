package org.farm.server.repository;

import org.farm.server.model.entities.UnitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitsRepository extends JpaRepository<UnitsEntity, Integer> {
    Optional<UnitsEntity> findByName(String name);
}
