package com.test.alten.productsapi.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.alten.productsapi.config.CacheConfig;
import com.test.alten.productsapi.feign.SimilarProductsFeignClient;
import com.test.alten.productsapi.model.ProductDetailDto;
import feign.FeignException;
import feign.RetryableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductDetailService {

    private static final String PRODUCT_NOT_FOUND = "The product with the id: %s does not exists";
    private static final String INTERNAL_SERVER_ERROR = "The product with the id: %s has caused a fatal error";
    private static final String TIMEOUT_ERROR = "Product with id: %s caused a timeout while trying to fetch";

    private final SimilarProductsFeignClient similarProductsFeignClient;

    @Cacheable(cacheNames = CacheConfig.CACHE_PRODUCT_DETAIL, key = "#productId")
    public ProductDetailDto searchProduct(String productId) {
        try {
            return similarProductsFeignClient.getProductDetail(productId);
        } catch (Exception ex) {
            manageExceptions(ex, productId);
        }
        return null;
    }

    private void manageExceptions(Exception exception, String productId) {
        if (exception instanceof RetryableException) {
            log.error(String.format(TIMEOUT_ERROR, productId));
        } else if (exception instanceof FeignException) {
            if (HttpStatus.valueOf(((FeignException) exception).status()).equals(HttpStatus.NOT_FOUND)) {
                log.error(String.format(PRODUCT_NOT_FOUND, productId));
            } else {
                log.error(String.format(INTERNAL_SERVER_ERROR, productId));
            }
        } else {
            log.error(String.format(INTERNAL_SERVER_ERROR, productId));
        }
    }
}
