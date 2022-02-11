package com.test.alten.productsapi.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.alten.productsapi.config.FeignConfig;
import com.test.alten.productsapi.model.ProductDetailDto;

@FeignClient(name = "similar-products", url = "${similar.products.url:}", configuration = FeignConfig.class)
public interface SimilarProductsFeignClient {

    @GetMapping("/product/{productId}/similarids")
    List<String> getSimilarIds(@PathVariable("productId") String productId);

    @GetMapping("/product/{productId}")
    ProductDetailDto getProductDetail(@PathVariable("productId") String productId);
}
