package com.test.alten.productsapi.controllers;

import com.test.alten.productsapi.api.ProductApi;
import com.test.alten.productsapi.model.ProductDetailDto;
import com.test.alten.productsapi.services.SimilarProductsService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SimilarProductsController implements ProductApi {

    private final SimilarProductsService similarProductsService;

    @Override
    public ResponseEntity<List<ProductDetailDto>> getProductSimilar(@ApiParam(value = "",
            required = true) @PathVariable("productId") String productId) {
        List<ProductDetailDto> result = similarProductsService.getSimilarProductsById(productId);
        if (CollectionUtils.isEmpty(result)) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
