package org.farm.server.repository;

import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.responses.ProductionStatisticsResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    @Query("select pe from ProductEntity pe where pe.producedDate between :sd and :ed")
    List<ProductEntity> getAllProductsForPeriod(
            @Param("sd") LocalDateTime startDate,
            @Param("ed") LocalDateTime endDate
    );

    @Query("select pe from ProductEntity pe where pe.productType.id = :ptid and pe.producedDate between :sd and :ed")
    List<ProductEntity> getAllProductsOfTypeForPeriod(
            @Param("sd") LocalDateTime startDate,
            @Param("ed") LocalDateTime endDate,
            @Param("ptid") Integer productTypeId
    );

    @Query("select pe from ProductEntity pe where pe.producedByFarmer.id = :pbid and pe.producedDate between :sd and :ed")
    List<ProductEntity> getAllProductsProducedByForPeriod(
            @Param("sd") LocalDateTime startDate,
            @Param("ed") LocalDateTime endDate,
            @Param("pbid") Integer producedById
    );


    @Query("select pe from ProductEntity pe where pe.productType.id = :ptid")
    List<ProductEntity> getAllProductsOfType(@Param("ptid") Integer productTypeId);

    @Query("select pe from ProductEntity pe where pe.producedByFarmer.id = :pbid")
    List<ProductEntity> getAllProductsProducedBy(@Param("pbid") Integer producedById);

    @Query(
            "select pe.productType.id as productTypeId, sum(pe.amount)  as producedAmount " +
                    "from ProductEntity pe " +
                    "group by pe.productType.id"
    )
    List<ProductionStatisticsResponse> getProductionTypeGrouped();

    @Query(
            "select pe.productType.id as productTypeId, sum(pe.amount) as producedAmount " +
                    "from ProductEntity pe " +
                    "where pe.producedByFarmer.id = :fid " +
                    "group by pe.productType.id"
    )
    List<ProductionStatisticsResponse> getProductionOfFarmer(@Param("fid") Integer farmerId);

    @Query(
            "select pe.productType.id as productTypeId, sum(pe.amount) as producedAmount " +
                    "from ProductEntity pe " +
                    "where pe.productType.id = :ptid " +
                    "group by pe.productType.id"
    )
    List<ProductionStatisticsResponse> getProductionOfType(@Param("ptid") Integer productTypeId);

    @Query(
            "select pe.productType.id as productTypeId, sum(pe.amount) as producedAmount " +
                    "from ProductEntity pe " +
                    "where pe.producedDate between :sd and :ed " +
                    "group by pe.productType.id"
    )
    List<ProductionStatisticsResponse> getProductionTypeGroupedForPeriod(
            @Param("sd") LocalDateTime startDate,
            @Param("ed") LocalDateTime endDate
    );

    @Query(
            "select pe.productType.id as productTypeId, sum(pe.amount) as producedAmount " +
                    "from ProductEntity pe " +
                    "where pe.producedByFarmer.id = :fid " +
                    "and pe.producedDate between :sd and :ed " +
                    "group by pe.productType.id"
    )
    List<ProductionStatisticsResponse> getProductionOfFarmerForPeriod(
            @Param("sd") LocalDateTime startDate,
            @Param("ed") LocalDateTime endDate,
            @Param("fid") Integer farmerId
    );
}
