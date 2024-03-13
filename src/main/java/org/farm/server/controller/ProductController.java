package org.farm.server.controller;

import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.requests.SaveProductRequest;
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
@RequestMapping("/product")
public class ProductController {
    private final ProductRepository productRepository;
    private final ProductService productService;


    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productRepository = productRepository;
        this.productService = productService;
    }

    /**
     * Saves a product based on given product info
     *
     * @param saveProductRequest the product info
     * @return a saved product entity
     */
    @PutMapping("/save")
    public ProductEntity save(@RequestBody SaveProductRequest saveProductRequest) {
        return productService.saveProduct(saveProductRequest);
    }

    /**
     * Deletes a product based on given id
     *
     * @param id the id of a product to delete
     */
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Integer id) {
        productRepository.deleteById(id);
    }

    /**
     * Gets all products
     *
     * @return all products
     */
    @GetMapping("/get-all")
    public List<ProductEntity> getAll() {
        return productRepository.findAll();
    }

    /**
     * Gets all products of a given type
     *
     * @param productTypeId the type of product
     * @return all products based on given product type
     */
    @GetMapping("/get-all/of-type/{ptid}")
    public List<ProductEntity> getAllProductsOfType(@PathVariable("ptid") Integer productTypeId) {
        return productRepository.getAllProductsOfType(productTypeId);
    }

    /**
     * Gets all products produced by a farmer with a specified id
     *
     * @param farmerId the id of a farmer
     * @return all products broduced by the farmer
     */
    @GetMapping("/get-all/of-farmer/{fid}")
    public List<ProductEntity> getAllProductsProducedBy(@PathVariable("fid") Integer farmerId) {
        return productRepository.getAllProductsProducedBy(farmerId);
    }

    /**
     * Gets all products produced during a given period
     *
     * @param startDate the start of the period
     * @param endDate the end of the period
     * @return all products produced ruring the given period
     */
    @GetMapping("/get-all/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return productRepository.getAllProductsForPeriod(startDate, endDate);
    }

    /**
     * Gets all products of a given type during a specified period
     *
     * @param startDate the start of the period
     * @param endDate the end of the period
     * @param typeId the type of product
     * @return all products of the type during the period
     */
    @GetMapping("/get-all/of-type/{tid}/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsOfTypeForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("tid") Integer typeId
    ) {
        return productRepository.getAllProductsOfTypeForPeriod(startDate, endDate, typeId);
    }

    /**
     * Gets all products produced by a specified farmer during a period
     *
     * @param startDate the start of the period
     * @param endDate the end of the period
     * @param farmerId the farmer id
     * @return all products produced by the farmer during the period
     */
    @GetMapping("/get-all/of-farmer/{fid}/of-period/{sd}/{ed}")
    public List<ProductEntity> getAllProductsProducedByForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("fid") Integer farmerId
    ) {
        return productRepository.getAllProductsProducedByForPeriod(startDate, endDate, farmerId);
    }

    /**
     * Gets sums of all produced products grouped by their types
     *
     * @return sums of all produced products grouped by their types
     */
    @GetMapping("/get-statistics")
    public List<ProductionStatisticsResponse> getProductionTypeGrouped() {
        return productRepository.getProductionTypeGrouped();
    }

    /**
     * Gets sum of all produced products of a specified type
     *
     * @param productTypeId the product type id
     * @return sum of all produced products of a specified type
     */
    @GetMapping("/get-statistics/of-type/{ptid}")
    public List<ProductionStatisticsResponse> getProductionOfType(@PathVariable("ptid") Integer productTypeId) {
        return productRepository.getProductionOfType(productTypeId);
    }

    /**
     * Gets type-grouped sums of products produced by a specified farmer
     *
     * @param farmerId the farmer id
     * @return type-grouped sums of products produced by a specified farmer
     */
    @GetMapping("/get-statistics/of-farmer/{fid}")
    public List<ProductionStatisticsResponse> getProductionOfFarmer(@PathVariable("fid") Integer farmerId) {
        return productRepository.getProductionOfFarmer(farmerId);
    }

    /**
     * Gets type-grouped sums of products produced during a given month
     *
     * @param date the month
     * @return type-grouped sums of products produced during a given month
     */
    @GetMapping("/get-statistics/of-month/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfMonth(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM") YearMonth date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                date.atDay(1).atStartOfDay(),
                date.atEndOfMonth().plusDays(1).atStartOfDay()
        );
    }

    /**
     * Gets type-grouped sums of products produced during a given week specified by a day.
     * For example 2024.03.13 is wednesday, so the week will be 2024.03.11 00:00 - 2024.03.18 00:00
     *
     * @param date the week (day of the week)
     * @return type-grouped sums of products produced during a given week
     */
    @GetMapping("/get-statistics/of-week/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfWeek(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                // start of a week
                date.minusDays(date.getDayOfWeek().getValue() - 1).atStartOfDay(),
                // end of a week
                date.plusWeeks(1).minusDays(date.getDayOfWeek().getValue() - 1).atStartOfDay()
        );
    }

    /**
     * Gets type-grouped sums of products produced during a given day
     *
     * @param date the day
     * @return type-grouped sums of products produced during a given day
     */
    @GetMapping("/get-statistics/of-day/{d}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedOfDay(
            @PathVariable("d") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(
                date.atStartOfDay(),
                date.plusDays(1).atStartOfDay().minusNanos(1)
        );
    }

    /**
     * Gets type-grouped sums of products produced during a given period
     *
     * @param startDate the start of the period
     * @param endDate the end of the period
     * @return type-grouped sums of products produced during a given period
     */
    @GetMapping("/get-statistics/of-period/{sd}/{ed}")
    public List<ProductionStatisticsResponse> getProductionTypeGroupedForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        return productRepository.getProductionTypeGroupedForPeriod(startDate, endDate);
    }

    /**
     * Gets type-grouped sums of products produced by a specified farmer during a given period
     *
     * @param startDate the start of the period
     * @param endDate the end of the period
     * @param farmerId the farmer id
     * @return type-grouped sums of products produced by a specified farmer during a given period
     */
    @GetMapping("/get-statistics/of-farmer/{fid}/of-period/{sd}/{ed}")
    public List<ProductionStatisticsResponse> getProductionOfFarmerForPeriod(
            @PathVariable("sd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @PathVariable("ed") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @PathVariable("fid") Integer farmerId
    ) {
        return productRepository.getProductionOfFarmerForPeriod(startDate, endDate, farmerId);
    }
}
