package org.farm.server.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class ProductServiceConfiguration {
    @Value("${spring.mail.host}")
    private String mailSmtpAddress;

    @Value("${spring.mail.port}")
    private int mailSmtpPort;

    @Value("${spring.mail.password}")
    private String reportSenderPassword;

    @Value("${spring.mail.username}")
    private String reportSenderMail;


    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(mailSmtpAddress);
        mailSender.setPort(mailSmtpPort);

        mailSender.setUsername(reportSenderMail);
        mailSender.setPassword(reportSenderPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

}
