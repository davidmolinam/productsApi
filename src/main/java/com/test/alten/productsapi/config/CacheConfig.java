package com.test.alten.productsapi.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    public static final String CACHE_PRODUCT_LIST = "products_cache";
    public static final String CACHE_PRODUCT_DETAIL = "product_detail_cache";

    @Bean
    public CacheManager concurrentMapCacheManager() {
       return new ConcurrentMapCacheManager(CACHE_PRODUCT_LIST, CACHE_PRODUCT_DETAIL);
    }

}
