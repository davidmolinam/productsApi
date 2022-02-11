package com.test.alten.productsapi.config;

import org.springframework.context.annotation.Bean;

import feign.Retryer;

public class FeignConfig {

    @Bean
    Retryer disableRetry() {
        return Retryer.NEVER_RETRY;
    }
}

