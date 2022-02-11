package com.test.alten.productsapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.test.alten.productsapi.api.ProductApi;
import com.test.alten.productsapi.model.ProductDetailDto;
import com.test.alten.productsapi.services.SimilarProductsService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SimilarProductsController implements ProductApi {

    private final SimilarProductsService similarProductsService;

    @Override
    public ResponseEntity<List<ProductDetailDto>> getProductSimilar(@ApiParam(value = "",
        required = true) @PathVariable("productId") String productId) {
        return ResponseEntity.ok(similarProductsService.getSimilarProductsById(productId));
    }
}
