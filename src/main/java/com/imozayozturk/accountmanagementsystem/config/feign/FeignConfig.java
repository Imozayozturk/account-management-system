package com.imozayozturk.accountmanagementsystem.config.feign;

import feign.Logger;
import feign.Retryer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {
    @Value("${feign-retry.max-attempts: 1}")
    private Integer retryMaxAttemps;
    @Value("${feign-retry.max-period-ms: 2000}")
    private Long retryMaxPeriodMs;
    @Value("${feign-retry.period-ms: 100}")
    private Long retryPeriodMs;

    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.BASIC;
    }

    @Bean
    public FeignErrorDecoder feignErrorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public Retryer retryer() {
        return new Retryer.Default(retryPeriodMs, retryMaxPeriodMs, retryMaxAttemps);
    }
}