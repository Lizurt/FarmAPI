package org.farm.server.controller;

import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.responses.ProductionStatisticsResponse;
import org.farm.server.repository.ProductRepository;
import org.farm.server.service.ProductService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;

@RestController
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;


    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    @PutMapping("/product/save")
    public ProductEntity save(@RequestBody ProductEntity productEntity) {
        return productService.saveProduct(productEntity);
    }

    @DeleteMapping("/product/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/product/get-all")
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    @GetMapping("/product/get-all/of-type/{ptid}")
    public List<ProductEntity> getAllProductsOfType(@PathVariable("ptid") Integer productTypeId) {
        return productRepository.getAllProductsOfType(productTypeId);
    }

    @GetMapping("/product/get-all/of-farmer/{fid}")
    public List<ProductEntity> getAllProductsProducedBy(@PathVariable("fid") Integer farmerId) {
        return productRepository.getAllProductsProducedBy(farmerId);
    }

    @GetMapping("/product/get-all/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return productRepository.getAllProductsForPeriod(startDate, endDate);
    }

    @GetMapping("/product/get-all/of-type/{tid}/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsOfTypeForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("tid") Integer typeId
    ) {
        return productRepository.getAllProductsOfTypeForPeriod(startDate, endDate, typeId);
    }

    @GetMapping("/product/get-all/of-farmer/{fid}/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsProducedByForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("fid") Integer farmerId
    ) {
        return productRepository.getAllProductsProducedByForPeriod(startDate, endDate, farmerId);
    }

    @GetMapping("/product/get-statistics")
    public List<ProductionStatisticsResponse> getProductionTypeGrouped() {
        return productRepository.getProductionTypeGrouped();
    }

    @GetMapping("/product/get-statistics/of-type/{ptid}")
    public List<ProductionStatisticsResponse> getProductionOfType(@PathVariable("ptid") Integer productTypeId) {
        return productRepository.getProductionOfType(productTypeId);
    }

    @GetMapping("/product/get-statistics/of-farmer/{fid}")
    public List<ProductionStatisticsResponse> getProductionOfFarmer(@PathVariable("fid") Integer farmerId) {
        return productRepository.getProductionOfFarmer(farmerId);
    }

    @GetMapping("/product/get-statistics/of-period/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedForPeriod(
            @PathVariable("d") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(date, date);
    }

    @GetMapping("/product/get-statistics/of-month/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfMonth(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM") YearMonth date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                date.atDay(1).atStartOfDay(),
                date.atEndOfMonth().plusDays(1).atStartOfDay()
        );
    }

    @GetMapping("/product/get-statistics/of-week/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfWeek(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                date.minusDays(date.getDayOfWeek().getValue() - 1).atStartOfDay(),
                date.plusWeeks(1).minusDays(date.getDayOfWeek().getValue() - 1).atStartOfDay()
        );
    }

    @GetMapping("/product/get-statistics/of-day/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfDay(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay().minusNanos(1)
        );
    }

    @GetMapping("/product/get-statistics/of-period/{sd}/{ed}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(startDate, endDate);
    }

    @GetMapping("/product/get-statistics/of-farmer/{fid}/of-period/{sd}/{ed}")
    public List<ProductionStatisticsResponse> getProductionOfFarmerForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("fid") Integer farmerId
    ) {
        return productRepository.getProductionOfFarmerForPeriod(startDate, endDate, farmerId);
    }
}
