package org.farm.server.service;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.requests.SaveProductRequest;
import org.farm.server.model.responses.ProductionStatisticsResponse;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.ProductRepository;
import org.farm.server.repository.ProductTypeRepository;
import org.farm.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final UserRepository userRepository;

    private final FarmerRepository farmerRepository;

    private final JavaMailSender mailSender;

    @Value("${app.production.statistics.report.receiver.mail}")
    private String reportReceiverMail;

    @Value("${spring.mail.username}")
    private String reportSenderMail;

    public ProductService(ProductRepository productRepository, ProductTypeRepository productTypeRepository, UserRepository userRepository, FarmerRepository farmerRepository, JavaMailSender mailSender) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.userRepository = userRepository;
        this.farmerRepository = farmerRepository;
        this.mailSender = mailSender;
    }

    public ProductEntity saveProduct(SaveProductRequest saveProductRequest) {
        ProductTypeEntity productTypeEntity = productTypeRepository.findById(saveProductRequest.getProductTypeId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        ProductEntity productEntity = new ProductEntity();
        productEntity.setProductType(productTypeEntity);

        FarmerEntity farmerEntity = farmerRepository.findById(saveProductRequest.getFarmerId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );
        productEntity.setProducedBy(farmerEntity);

        if (saveProductRequest.getProducedDate() == null) {
            saveProductRequest.setProducedDate(new Date());
        }
        productEntity.setProducedDate(saveProductRequest.getProducedDate());
        productEntity.setAmount(saveProductRequest.getAmount());
        return productRepository.save(productEntity);
    }

    public StringBuilder createProductionReportForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate == null || endDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        List<ProductionStatisticsResponse> productionStatistics = productRepository.getProductionTypeGroupedForPeriod(
                startDate,
                endDate
        );
        StringBuilder report = new StringBuilder();
        if (productionStatistics.size() == 0) {
            // todo reduce hardcoded text
            report
                    .append("The farm produced nothing during the period between ")
                    .append(startDate)
                    .append(" and ")
                    .append(endDate)
                    .append(".");
            return report;
        }
        List<ProductTypeEntity> productTypes = productTypeRepository.findAll();
        Map<Integer, ProductTypeEntity> productTypesByIds = productTypes.stream().collect(
                Collectors.toMap(ProductTypeEntity::getId, productType -> productType)
        );
        ProductTypeEntity productType;
        report
                .append("The farm produced such products during the period between ")
                .append(startDate)
                .append(" and ")
                .append(endDate)
                .append(":\n\r");
        for (ProductionStatisticsResponse prodStat : productionStatistics) {
            productType = productTypes.get(prodStat.getProductTypeId());
            report
                    .append(prodStat.getProducedAmount())
                    .append(" ")
                    .append(productType.getUnits().getName())
                    .append(" of ")
                    .append(productType.getName())
                    .append(";\n\r");
        }
        return report;
    }

    @Scheduled(cron = "${app.production.statistics.report.cron}", zone = "${app.production.statistics.report.timezone}")
    public void createAndSendEverydayReport() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now();
        StringBuilder report = createProductionReportForPeriod(startDate, endDate);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(reportSenderMail);
        message.setTo(reportReceiverMail);
        message.setSubject("Scheduled production report");
        message.setText(report.toString());
        mailSender.send(message);
    }
}
