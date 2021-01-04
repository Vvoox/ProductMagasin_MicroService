package com.example.billingservice.feign;

import com.example.billingservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductRestClient {

    @GetMapping("/products/api/messagezip")
    String getMessage();

    @GetMapping("/products/api/{id}")
    Product getProduct(@PathVariable long id);

    @GetMapping("/products/api")
    PagedModel<Product> pageProducts();
}
