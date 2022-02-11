package com.test.alten.productsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class ProductsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductsApiApplication.class, args);
    }
}
