package org.farm.server.service;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

import org.farm.server.model.entities.FarmerEntity;
import org.farm.server.model.entities.ProductEntity;
import org.farm.server.model.entities.ProductTypeEntity;
import org.farm.server.model.responses.ProductionStatisticsResponse;
import org.farm.server.repository.FarmerRepository;
import org.farm.server.repository.ProductRepository;
import org.farm.server.repository.ProductTypeRepository;
import org.farm.server.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductTypeRepository productTypeRepository;
    private final UserRepository userRepository;

    private final FarmerRepository farmerRepository;

    @Value("${app.production.statistics.report.smtp.address}")
    private String mailSmtpAddress;

    @Value("${app.production.statistics.report.smtp.port}")
    private String mailSmtpPort;


    @Value("${app.production.statistics.report.receiver.mail}")
    private String reportReceiverMail;

    @Value("${app.production.statistics.report.sender.mail}")
    private String reportSenderMail;

    @Value("${app.production.statistics.report.sender.password}")
    private String reportSenderPassword;

    public ProductService(ProductRepository productRepository, ProductTypeRepository productTypeRepository, UserRepository userRepository, FarmerRepository farmerRepository) {
        this.productRepository = productRepository;
        this.productTypeRepository = productTypeRepository;
        this.userRepository = userRepository;
        this.farmerRepository = farmerRepository;
    }

    public ProductEntity saveProduct(ProductEntity productEntity) {
        ProductTypeEntity productTypeEntity = productEntity.getProductType();
        if (productTypeEntity != null) {
            if (productTypeEntity.getId() != null) {
                productTypeEntity = productTypeRepository.findById(productTypeEntity.getId()).orElse(null);
            } else {
                productTypeEntity = null;
            }
        }
        productEntity.setProductType(productTypeEntity);

        FarmerEntity farmerEntity = productEntity.getProducedBy();
        if (farmerEntity != null) {
            if (farmerEntity.getId() != null) {
                farmerEntity = farmerRepository.findById(farmerEntity.getId()).orElse(null);
            } else {
                farmerEntity = null;
            }
        }
        productEntity.setProducedBy(farmerEntity);

        if (productEntity.getProducedDate() == null) {
            productEntity.setProducedDate(new Date());
        }
        return productRepository.save(productEntity);
    }

    public StringBuilder createProductionReportForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
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

    @Scheduled(cron = "${app.production.statistics.report.cron}")
    public void createAndSendEverydayReport() {
        LocalDateTime startDate = LocalDateTime.now().toLocalDate().atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now();
        StringBuilder report = createProductionReportForPeriod(startDate, endDate);

        Properties props = new Properties();
        props.put("mail.smtp.host", mailSmtpAddress);
        props.put("mail.smtp.port", mailSmtpPort);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(reportSenderMail, reportSenderPassword);
            }
        });

        Message mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress());
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(reportReceiverMail));
            mimeMessage.setSubject("Scheduled production report");
            mimeMessage.setText(report.toString());
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
