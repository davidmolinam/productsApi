package com.test.alten.productsapi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.test.alten.productsapi.config.CacheConfig;
import com.test.alten.productsapi.feign.SimilarProductsFeignClient;
import com.test.alten.productsapi.model.ProductDetailDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SimilarProductsService {

    private final SimilarProductsFeignClient similarProductsFeignClient;

    private final ProductDetailService productDetailService;

    @Cacheable(cacheNames = CacheConfig.CACHE_PRODUCT_LIST, key = "#productId", unless = "#result.size() == 0")
    public List<ProductDetailDto> getSimilarProductsById(String productId) {

        List<ProductDetailDto> detailList = new ArrayList<>();
        List<String> productIds = similarProductsFeignClient.getSimilarIds(productId);
        productIds.forEach(id -> addProduct(id, detailList));
        return detailList;
    }

    private void addProduct(String productId, List<ProductDetailDto> detailList) {

        ProductDetailDto productDto = productDetailService.searchProduct(productId);
        if (Objects.nonNull(productDto)) {
            detailList.add(productDto);
        }
    }
}
