package org.farm.server.repository;

import org.farm.server.model.UnitsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitsRepository extends JpaRepository<UnitsEntity, Integer> {
}
